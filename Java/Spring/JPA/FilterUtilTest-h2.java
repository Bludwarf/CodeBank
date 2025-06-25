package service.tools.filters;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static service.util.RSQLJPAUtilTest.initRSQLCommonSupport;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@EnableJpaRepositories(basePackages = {"io.github.perplexhub.rsql.repository.jpa", "service.tools.filters"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class FilterUtilTest {

    private final AccountRepo accountRepo;
    private final EntityManager entityManager;
    private final GroupRepo groupRepo;
    private final GroupRightRepo groupRightRepo;
    private final GroupTypeRepo groupTypeRepo;
    private final UserRepo userRepo;

    @BeforeEach
    void setUp() {
        initRSQLCommonSupport(entityManager);
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
        val dao = new UserDao();
        userRepo.save(dao);

        val filterUtil = FilterUtil.get(filter, false);
        val spec = filterUtil.<UserDao>build();

        val all = userRepo.findAll(spec);

        assertThat(all).containsExactly(dao);
    }

    private static Stream<Arguments> build_distinctJoin() {
        return Stream.of(
                Arguments.of("type.code==FICHIER", 10),
                Arguments.of("type.code=='FICHIER'", 10),
                Arguments.of("groupRights.user.id==2657", 10),
                Arguments.of("groupRights.user.id==2657;groupRights.admin==true", 5),
                Arguments.of("accounts.id==20289", 6),
                Arguments.of("(((accounts.id==20289),(groupRights.user.id==2657;groupRights.admin==true)))", 9),
                Arguments.of("(type.code==FICHIER);((((type.code=='FICHIER');(((accounts.id==20289),(groupRights.user.id==2657;groupRights.admin==true))))))", 6) // Problème détecté par l'ano 18634 : le DISTINCT n'est pas pris en compte
        );
    }

    @ParameterizedTest
    @MethodSource
    void build_distinctJoin(final String filter, final int expectedSize) {
        val spec = getGroupDaoSpecification(filter, true);
        build_distinctJoin(spec, expectedSize);
    }

    private static Stream<Arguments> build_distinctJoinWithoutHints() {
        return Stream.of(
                Arguments.of("type.code==FICHIER", 10),
                Arguments.of("type.code=='FICHIER'", 10),
                Arguments.of("groupRights.user.id==2657", 10),
                Arguments.of("groupRights.user.id==2657;groupRights.admin==true", 5),
                Arguments.of("accounts.id==20289", 6),
                Arguments.of("(((accounts.id==20289),(groupRights.user.id==2657;groupRights.admin==true)))", 8),
                Arguments.of("(type.code==FICHIER);((((type.code=='FICHIER');(((accounts.id==20289),(groupRights.user.id==2657;groupRights.admin==true))))))", 5) // Problème détecté par l'ano 18634 : le DISTINCT n'est pas pris en compte
        );
    }

    @ParameterizedTest
    @MethodSource
    void build_distinctJoinWithoutHints(final String filter, final int expectedSize) {
        val spec = getGroupDaoSpecification(filter, false);
        build_distinctJoin(spec, expectedSize);
    }

    private void build_distinctJoin(final Specification<GroupDao> spec, final int expectedSize) {
        val factory = new GroupFactory();

        // Groupe sans account
        factory.createGroup(4373, true, AccountsMode.NONE, GroupRightMode.ADMIN);

        // Groupes avec account
        factory.createGroup(2508, true, AccountsMode.OTHERS, GroupRightMode.ADMIN);
        factory.createGroup(2743, true, AccountsMode.OTHERS, GroupRightMode.ADMIN);

        // Groupes non admin
        factory.createGroup(1, true, AccountsMode.OTHERS, GroupRightMode.NONE);
        factory.createGroup(2, false, AccountsMode.OTHERS, GroupRightMode.NONE);
        factory.createGroup(3, false, AccountsMode.NONE, GroupRightMode.NONE);
        factory.createGroup(4, false, AccountsMode.NONE, GroupRightMode.MEMBER);
        factory.createGroup(5, false, AccountsMode.OTHERS, GroupRightMode.MEMBER);
        factory.createGroup(6, true, AccountsMode.NONE, GroupRightMode.NONE);
        factory.createGroup(7, true, AccountsMode.NONE, GroupRightMode.MEMBER);
        factory.createGroup(8, true, AccountsMode.OTHERS, GroupRightMode.MEMBER);

        // Accounts contenant celui de l'utilisateur
        factory.createGroup(9, false, AccountsMode.USER, GroupRightMode.NONE);
        factory.createGroup(10, false, AccountsMode.USER, GroupRightMode.MEMBER);
        factory.createGroup(11, false, AccountsMode.USER, GroupRightMode.ADMIN);
        factory.createGroup(12, true, AccountsMode.USER, GroupRightMode.NONE);
        factory.createGroup(13, true, AccountsMode.USER, GroupRightMode.MEMBER);
        factory.createGroup(14, true, AccountsMode.USER, GroupRightMode.ADMIN);

        val all = groupRepo.findAll(spec, Pageable.ofSize(Math.max(expectedSize, 1)));

        assertThat(all).hasSize(expectedSize);
    }

    private enum AccountsMode {
        /**
         * Aucun accounts
         */
        NONE,

        /**
         * Que des accounts autres que l'utilisateur
         */
        OTHERS,

        /**
         * Accounts contenant l'utilisateur
         */
        USER
    }

    private enum GroupRightMode {
        NONE, ADMIN, MEMBER
    }

    private class GroupFactory {
        private final UserDao user = userRepo.save(UserDao.builder().id(2657).build());
        private final AccountDao userAccount = accountRepo.save(AccountDao.builder().id(20289).build());
        private final AccountDao otherAccount1 = accountRepo.save(AccountDao.builder().id(1).build());
        private final AccountDao otherAccount2 = accountRepo.save(AccountDao.builder().id(2).build());
        private final AccountDao otherAccount3 = accountRepo.save(AccountDao.builder().id(3).build());
        private final GroupTypeDao type = groupTypeRepo.save(GroupTypeDao.builder()
                .id(1)
                .code("FICHIER")
                .build());
        private final GroupTypeDao otherType = groupTypeRepo.save(GroupTypeDao.builder()
                .id(2)
                .code("OTHER")
                .build());

        void createGroup(final int id, final boolean typeFICHIER, final AccountsMode accountsMode, final GroupRightMode groupRightMode) {
            val group = GroupDao.builder()
                    .id(id)
                    .type(typeFICHIER ? type : otherType)
                    .accounts(switch (accountsMode) {
                        case NONE -> null;
                        case OTHERS -> List.of(otherAccount1, otherAccount2, otherAccount3);
                        case USER -> List.of(userAccount, otherAccount1, otherAccount2, otherAccount3);
                    })
                    .build();
            groupRepo.save(group);

            if (groupRightMode != GroupRightMode.NONE) {
                val groupRight = groupRightRepo.save(GroupRightDao.builder()
                        .id(group.getId())
                        .group(group)
                        .admin(groupRightMode == GroupRightMode.ADMIN)
                        .user(user)
                        .build());
                group.setGroupRights(new HashSet<>(List.of(groupRight)));
                groupRepo.save(group);
            }
        }

    }

    private static Specification<GroupDao> getGroupDaoSpecification(final String filter, final boolean withHints) {
        val filterUtil = FilterUtil.get(filter, true);
        if (withHints) {
            filterUtil.setJoinHints(Map.of(
                    "GroupDao.accounts", JoinType.LEFT,
                    "GroupRightDao.user", JoinType.LEFT
            ));
        }
        return filterUtil.build();
    }

}
