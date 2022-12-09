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

			// 팀 두개 생성
			Team teamA = new Team();
			teamA.setTeamName("TeamA");
			em.persist(teamA);
			em.flush();

			Team teamB = new Team();
			teamB.setTeamName("TeamB");
			em.persist(teamB);
			em.flush();

			Member member = new Member();
			member.setMemberName("member1");
			member.setTeam(teamA);
			em.persist(member);
			em.flush();

			// Member의 팀 변경
			member.setTeam(em.find(Team.class, 2L));
			em.flush();
			System.out.println(member.getTeam());

			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}

	}

}