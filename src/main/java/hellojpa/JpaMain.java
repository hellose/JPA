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

			Member member = em.find(Member.class, 150L);
			member.setName("변경");

			// 영속성 컨텍스트를 통으로 날림
			em.clear();

			// 컨텍스트가 비워졌기 때문에 1차 캐시에 없어서 select 쿼리가 다시 나감
			em.find(Member.class, 150L);

			System.out.println("============");

			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
