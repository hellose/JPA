package hellojpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.HibernateException;

public class JpaMain {
	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//		EntityManager em = emf.createEntityManager();

//		EntityTransaction tx = em.getTransaction();
//		tx.begin();

		try {

		

//			tx.commit();

		} catch (HibernateException e) {
//			tx.rollback();
		} finally {
//			em.close();
		}

		emf.close();
	}
}
