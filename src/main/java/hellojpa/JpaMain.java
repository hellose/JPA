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

			Member member1001 = new Member(1001L, "member1001");
			Member member1002 = new Member(1002L, "member1002");
			Member member1003 = new Member(1003L, "member1003");

			em.persist(member1001);
			em.persist(member1002);
			em.persist(member1003);

			// JPQL 쿼리 실행시 flush()가 자동으로 호출되는 이유
			// <- 쓰기 지연된 내용이 모두 DB에 반영해야 DB쿼리 호출시 정확한 정보을 얻어올수 있기 떄문에
			List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();

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
