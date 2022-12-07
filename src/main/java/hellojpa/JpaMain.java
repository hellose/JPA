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

			Member member1 = new Member(150L, "A");
			Member member2 = new Member(160L, "B");
			
			// 영속
			em.persist(member1);
			em.persist(member2);
			
			System.out.println("============");
			
			// 쓰기 지연 SQL 저장소에 존재하는 쿼리 flush되고 DB commit됨
			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
