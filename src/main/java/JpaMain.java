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
			Team team = new Team(1L, "TeamA");
			em.persist(team);

			// TEAM_ID 외래키를 코드로 직접 셋팅 -> 개발자의 실수로 이상한 값이 들어갈 가능성 존재
			Member member = new Member(1L, "member1", team.getTeamId());
			em.persist(member);

			Long teamId = member.getTeamId();
			Team findTeam = em.find(Team.class, teamId);
			System.out.println("Team 정보: " + findTeam);

			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}

	}

}