package lazy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

import entity.Member;
import entity.Team;
import util.MyUtil;

public class LazyInitializationTest {

	private static final String PERSISTENCE_UNIT_NAME = "test";

	public static void main(String[] args) {
//		eagerWhenForeignKeyIsNull();
//		eagerWhenForeignKeyIsNotNull();
//		lazyWhenForeignKeyIsNull();
		lazyWhenForeignKeyIsNotNull();
	}

	// insert into team values(1, 'team1');
	// insert into member values(1, 'mem1', 1);
	// insert into member values(2, 'mem2', null);

	// fk값 null + 즉시로딩
	public static void eagerWhenForeignKeyIsNull() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			// fk값 null인 경우
			Member memTwo = em.find(Member.class, 2);

			Team memTwoTeam = memTwo.getTeam();
			MyUtil.isNull(memTwoTeam); // null

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	// fk값 존재 + 즉시로딩
	public static void eagerWhenForeignKeyIsNotNull() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			// fk값 존재하는 경우
			Member memOne = em.find(Member.class, 1);

			Team memOneTeam = memOne.getTeam();
			MyUtil.isNull(memOneTeam); // not null

			Team team1 = em.find(Team.class, 1);
			MyUtil.sameIdentityHashCode(memOneTeam, team1); // true

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	// fk값 null + 지연로딩
	// -> 첫번 select member 쿼리시 team_id 값이 null임을 알 수 있기 때문에 Team객체를 null로 셋팅
	// -> getTeam()메서드 호출하나 마나 쿼리는 안나감
	public static void lazyWhenForeignKeyIsNull() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			PersistenceUnitUtil puu = emf.getPersistenceUnitUtil();

			// fk값 null인 경우
			Member memTwo = em.find(Member.class, 2);

			// fk가 null이라 select team을 수행할 필요가 없다
			// -> getTeam()시 역시 쿼리 안나감
			Team memTwoTeam = memTwo.getTeam();
			MyUtil.isNull(memTwoTeam);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	// fk값 존재 + 지연로딩
	public static void lazyWhenForeignKeyIsNotNull() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			PersistenceUnitUtil puu = emf.getPersistenceUnitUtil();

			// fk값 존재하는 경우
			Member memOne = em.find(Member.class, 1);

			// 프록시 객체 참조
			Team memOneTeam = memOne.getTeam();
			MyUtil.isNull(memOneTeam);
			MyUtil.printClass(memOneTeam);
			System.out.println("=>isLoaded: " + puu.isLoaded(memOneTeam));

			System.out.println("=>");
			memOneTeam.getName();

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

}
