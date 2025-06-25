package service.tools.filters;

import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaRepositories(repositoryBaseClass = SimpleJpaRepositorySpy.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class FilterUtilTest {

    private final GroupRepo groupRepo;

    @BeforeEach
    void setUp() {
        SimpleJpaRepositorySpy.setUp();
    }

    private static Stream<Arguments> build_withoutFilter() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(" ")
        );
    }

    @ParameterizedTest
    @MethodSource
    void build_withoutFilter(@Nullable final String filter) {
        val query = captureQuery(filter, false, false);
        assertThat(query).matches("select (\\w+) from ((?:\\w+\\.)+)GroupDao \\1");
    }

    // Point d'attention levé par l'ano 18634 : on peut encapsuler une chaîne avec des quotes ou non
    @ParameterizedTest
    @ValueSource(strings = {
            "type.code==FICHIER",
            "type.code=='FICHIER'",
            "type.code==\"FICHIER\""
    })
    void build_quoteOrNotQuote(final String filter) {
        val query = captureQuery(filter, false, false);
        assertThat(query).endsWith(".code = FICHIER");
    }

    // Problème détecté par l'ano 18634 : le DISTINCT n'est pas pris en compte
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void build_18634WithDistinct(final boolean withHints) {
        val query = capture18634Query(true, withHints);
        assertThat(query).matches("select distinct (\\w+) from ((?:\\w+\\.)+)GroupDao \\1 .+");
    }

    // Problème détecté par l'ano 18634 : le DISTINCT n'est pas pris en compte
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void build_18634WithoutDistinct(final boolean withHints) {
        val query = capture18634Query(false, withHints);
        assertThat(query).matches("select (\\w+) from ((?:\\w+\\.)+)GroupDao \\1 .+");
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void build_18634WithJoinHints(final boolean distinct) {
        val query = capture18634Query(distinct, true);
        assertThat(query)
                .containsPattern(" left join (\\w+)\\.accounts ")
                .containsPattern(" left join (\\w+)\\.groupRights ")
        ;
    }

    private String capture18634Query(final boolean distinct, final boolean withHints) {
        return captureQuery("(type.code==FICHIER);((((type.code=='FICHIER');(((accounts.id==20289),(groupRights.user.id==2657;groupRights.admin==true))))))", distinct, withHints);
    }

    private String captureQuery(@Nullable final String filter, final boolean distinct, final boolean withHints) {
        val spec = build(filter, distinct, withHints);

        groupRepo.findAll(spec);

        return SimpleJpaRepositorySpy.captureQuery();
    }

    private static Specification<GroupDao> build(@Nullable final String filter, final boolean distinct, final boolean withHints) {
        val filterUtil = FilterUtil.get(filter, distinct);
        if (withHints) {
            filterUtil.setJoinHints(Map.of(
                    "GroupDao.accounts", JoinType.LEFT,
                    "GroupRightDao.user", JoinType.LEFT
            ));
        }
        return filterUtil.build();
    }

}
