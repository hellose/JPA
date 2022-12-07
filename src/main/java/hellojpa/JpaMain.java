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
			
			//member는 준영속 상태가됨 (영속상태에서 비영속상태가 된경우 준영속이라 부름)
			//영속성 컨텍스트에서 member만 준영속 상태로 변경
			em.detach(member);
			
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
