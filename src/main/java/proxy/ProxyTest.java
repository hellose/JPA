package proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

import org.hibernate.Hibernate;

import entity.Member;
import util.MyUtil;

/*
create table member (id integer not null, name varchar(255), team_id varchar(255), primary key(id));
create table team (id integer not null, name varchar(255), primary key(id));
alter table member add constraint member_team_id_fk foreign key (team_id) references team(id);
insert into team values(1, 'team1');
insert into member values(1, 'mem1', 1);
 */

class ProxyTest {

	private static final String PERSISTENCE_UNIT_NAME = "test";

	public static void main(String[] args) {
//		sameTypeClassTest1();
//		sameTypeClassTest2();
//		lazyInitializationError();
//		checkProxyInstanceInnerEntityLoaded();
//		checkIsLoadedMethodAlsoValidToEntity();
		forceInitialization();
	}

	/*
	 * 조회된 특정 pk에 대한 객체는 둘다 엔티티 클래스를 상속한 프록시 객체이거나 or 둘다 엔티티 객체여야함
	 * 
	 */
	public static void sameTypeClassTest1() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			// 프록시 방법으로 조회
			Member refMem = em.getReference(Member.class, 1);
			MyUtil.printClass(refMem);

			System.out.println("=>");

			// find를 통해 조회했지만 위에서 1번 pk에 조회된 프록시 객체가 존재하므로 프록시 객체를 리턴
			// but 프록시 객체지만 find메서드를 사용했기 때문에 바로 쿼리가 나감
			Member findMem = em.find(Member.class, 1);
			MyUtil.printClass(findMem);

			MyUtil.sameIdentityHashCode(refMem, findMem);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	public static void sameTypeClassTest2() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			// 엔티티
			Member findMem = em.find(Member.class, 1);
			MyUtil.printClass(findMem);

			System.out.println("=>");

			// 위에서 find를 통해 1차 캐시에 둔 상태이므로 동일한 Entity객체 리턴
			Member refMem = em.getReference(Member.class, 1);
			MyUtil.printClass(refMem);

			MyUtil.sameIdentityHashCode(refMem, findMem);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	/*
	 * 프록시 객체의 내부 엔티티 객체 초기화 요청시 준영속 상태의 엔티티이거나, 영속성 컨텍스트가 닫혔거나, 영속성 컨텍스트를 비우면 Lazy
	 * 초기화 오류 밠생
	 */
	public static void lazyInitializationError() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Member refMem = em.getReference(Member.class, 1);
			MyUtil.printClass(refMem);

			System.out.println("=>");

			// 프록시 객체 내부의 엔티티 객체 셋팅(초기화) 요청은 영속성 컨텍스트에게 요청이 일어남

			// 준영속 상태로 변경하고
			em.detach(refMem);
			// 다음의 두가지 경우도 동일한 결과가 나타남
//			em.clear();
//			em.close();

			// 내부 엔티티 객체 셋팅 요청하면 에러 발생 -> LazyInitializationException
			refMem.getName();

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	/*
	 * 프록시 객체의 내부 엔티티가 초기화된 상태인지 확인하는법
	 * 
	 */
	public static void checkProxyInstanceInnerEntityLoaded() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Member refMem = em.getReference(Member.class, 1);

			PersistenceUnitUtil puu = emf.getPersistenceUnitUtil();
			System.out.println("=>" + puu.isLoaded(refMem));
			refMem.getName();
			System.out.println("=>" + puu.isLoaded(refMem));

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	/*
	 * 프록시 클래스가아닌 일반 엔티티도 PersistenceUnitUtil의 isLoad메서드를 사용가능한지 테스트
	 * 
	 */
	public static void checkIsLoadedMethodAlsoValidToEntity() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Member findMem = em.find(Member.class, 1);

			PersistenceUnitUtil puu = emf.getPersistenceUnitUtil();

			MyUtil.printClass(findMem);
			// 프록시 객체가 아닌 일반 엔티티 객체에도 isLoaded 메서드 사용가능
			System.out.println("=>" + puu.isLoaded(findMem));

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	/*
	 * 프록시 객체 강제 초기화
	 *
	 */
	public static void forceInitialization() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Member refMem = em.getReference(Member.class, 1);
			MyUtil.printClass(refMem);

			PersistenceUnitUtil puu = emf.getPersistenceUnitUtil();
			System.out.println("=>" + Hibernate.isInitialized(refMem)); // Hibernate 제공
			System.out.println("=>" + puu.isLoaded(refMem)); // JPA 표준

			Hibernate.initialize(refMem); // Hibernate에서 제공(JPA표준에서 제공하는 강제 초기화는 없음)
			System.out.println("=>" + Hibernate.isInitialized(refMem));
			System.out.println("=>" + puu.isLoaded(refMem));

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
