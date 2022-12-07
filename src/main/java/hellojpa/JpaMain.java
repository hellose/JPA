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

			// 비영속
//			Member member = new Member(4L, "csh");

			// 영속 - 영속성 컨텍스트에 담음
//			em.persist(member);

			// 준영속 상태 - 영속성 컨텍스트에서 분리
//			em.detach(member);
			
			// 삭제
//			em.remove(member);
			
			// commit()시 영속성 컨텍스트에 담긴 것들이 쿼리로 날라감
			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
