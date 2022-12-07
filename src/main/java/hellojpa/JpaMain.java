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
			
			//db에서 조회
			Member findMember1 = em.find(Member.class, 101L);
			//영속성 컨텍스트에서 조회
			Member findMember2 = em.find(Member.class, 101L);

			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
