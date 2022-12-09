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

			// 저장
			Team team = new Team();
			team.setTeamName("TeamA");
			em.persist(team);

			Member member = new Member();
			member.setMemberName("member1");
			member.setTeam(team);
			em.persist(member);

			// 조회
			Member findMember = em.find(Member.class, member.getMemberId());
			Team findTeam = findMember.getTeam();
			System.out.println("findTeam: " + findTeam);
			
			//여기서 member, team inert 쿼리 나감
			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}

	}

}