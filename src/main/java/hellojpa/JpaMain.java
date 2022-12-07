package hellojpa;

import java.util.List;

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
			// JPA는 객체를 중심으로 쿼리 작성하는 미묘한 차이가 있음
//			List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();
			List<Member> result = em.createQuery("select m from Member as m", Member.class).setFirstResult(5)
					.setMaxResults(8).getResultList();
			// persistence.xml의 hibernate.dialect를 다른 db로 바꾸면 자동으로 해당 db에 맞는 쿼리를 날려준다.

			for (Member member : result) {
				System.out.println(member.toString());
			}
			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
