package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.HibernateException;

public class JpaMain {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			Member member = new Member();

			System.out.println("================");
			// IDENTITY전략 - 자동으로 설정될 값을 모르기 떄문에 db에 쿼리를 날려서 값을 셋팅하고 셋팅된 값을 받아오게됨
			em.persist(member);
			System.out.println("id: " + member.getId());
			System.out.println("================");

			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();

		} finally {
			em.close();

		}

		emf.close();
	}

}
