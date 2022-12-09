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
			member.setMemberName("member1");
			em.persist(member);

			Team team = new Team();
			team.setTeamName("TeamA");
			// 연관관계의 주인이 아닌 Team객체의 members필드에 대입했을 때
			team.getMembers().add(member);
			em.persist(team);

			em.flush();
			em.clear();

			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}

	}

}