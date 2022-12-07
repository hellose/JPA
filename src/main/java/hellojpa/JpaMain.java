package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.HibernateException;

public class JpaMain {
	public static void main(String[] args) {
		// EntityManagerFactory - 애플리케이션당 하나
		// persistence.xml에 선언되어 있는 persistence-unit 값 전달
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		// EntityManager - 하나의 작업 진행을 하기위해 얻고 작업 후 닫아야함
		EntityManager em = emf.createEntityManager();

		// code
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			// insert 작업
//			Member member = new Member(1L, "HelloA");
//			em.persist(member);

			// select 작업
//			Member findMember = em.find(Member.class, 1L);
//			System.out.println(findMember.toString());

			// delete 작업
//			Member find = em.find(Member.class, 1L);
//			em.remove(find);

			// update 작업
			// update시 em.persist()는 필요 x
//			Member findMember = em.find(Member.class, 1L);
//			findMember.setName("HelloJPA");
			// find를 통해 가져온 객체(em.find에 해당)는 트랜잭션 commit시에
			// 객체의 필드 변경을 알아서 체크하여 자동으로 update 쿼리를 추가로 넣어준다.

			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
