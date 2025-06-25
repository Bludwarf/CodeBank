package service.tools.filters;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.val;
import org.hibernate.query.sqm.tree.select.SqmSelectStatement;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.NoSuchElementException;

import static service.util.RSQLJPAUtilTest.initRSQLCommonSupport;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Surcharge de {@link SimpleJpaRepository} pour vérifier les requêtes HQL envoyées à l'{@link EntityManager}
 *
 * @apiNote Exemple d'utilisation dans un test :
 *
 * <pre>{@code
 * @DataJpaTest
 * @EnableJpaRepositories(repositoryBaseClass = SimpleJpaRepositorySpy.class)
 * class MaClasseTest {
 *     private final GroupRepo groupRepo;
 *
 *     @BeforeEach
 *     void setUp() {
 *         SimpleJpaRepositorySpy.setUp();
 *     }
 *
 *      @Test
 *      void test() {
 *          groupRepo.findAll();
 *
 *          val query = SimpleJpaRepositorySpy.captureQuery();
 *          assertThat(query).matches("select distinct (\\w+) from ((?:\\w+\\.)+)GroupDao \\1 .+");
 *      }
 *
 * }</pre>
 */
class SimpleJpaRepositorySpy<T, ID> extends SimpleJpaRepository<T, ID> {
    private static EntityManager entityManagerSpy;

    public SimpleJpaRepositorySpy(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager) {
        super(entityInformation, getEntityManagerSpy(entityManager));
        initRSQLCommonSupport(requireEntityManagerSpy());
    }

    private synchronized static EntityManager getEntityManagerSpy(final EntityManager entityManager) {
        if (entityManagerSpy == null) {
            entityManagerSpy = spy(entityManager);
        }
        return entityManagerSpy;
    }

    private static EntityManager requireEntityManagerSpy() {
        if (entityManagerSpy == null) {
            throw new NoSuchElementException();
        }
        return entityManagerSpy;
    }

    public static void setUp() {
        Mockito.reset(entityManagerSpy);
    }

    public static String captureQuery() {
        val entityManager = requireEntityManagerSpy();
        val criteriaQueryArgumentCaptor = ArgumentCaptor.forClass(CriteriaQuery.class);

        verify(entityManager).createQuery(criteriaQueryArgumentCaptor.capture());
        val query = (SqmSelectStatement<?>) criteriaQueryArgumentCaptor.getValue();
        /*
         * Exemple :
         * select distinct alias_1246329279 from service.tools.filters.GroupDao alias_1246329279
         * join alias_1246329279.type alias_2056109041
         * left join alias_1246329279.accounts alias_1907170571
         * left join alias_1246329279.groupRights alias_1402302648
         * where alias_2056109041.code = FICHIER and alias_2056109041.code = FICHIER and (alias_1907170571.id = 20289 or alias_1402302648.user.id = 2657 and alias_1402302648.admin = true)
         */
        return query.toHqlString();
    }

}
