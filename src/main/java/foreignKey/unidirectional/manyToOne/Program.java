package foreignKey.unidirectional.manyToOne;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pub.EntityManagerAop;
import pub.EntityTransactionAop;

class Program {

	public static void main(String[] args) {

		// EntityManagerFactory <- SessionFactory <- SessionFactoryImpl
		
		// (SessionFactory)
		// thread safe한 Database 도메인 모델
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		// EntityManager <- Session <- SessionImpl
		
		// (Session)
		// single-threaded
		// JDBC Connection wrapper
		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		
		// EntityTransaction <- Transaction <- TransactionImpl
		// (Transaction)
		// JDBC 또는 JTA의 transaction 역할
		// single-threaded
		EntityTransactionAop tx = em.getTransaction();

		tx.begin();

		try {
//			Team t1 = new Team();
//			t1.setTeamId(1L);
//			t1.setTeamName("team1");
//			em.persist(t1);
			
			Member m1 = new Member();
			m1.setMemberId(1L);
			m1.setMemberName("mem1");
			m1.setTeam(null);
			em.persist(m1);
			
			em.clear();
			
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
