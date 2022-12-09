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

			Team team = new Team();
			team.setTeamName("TeamA");
			em.persist(team);

			Member member = new Member();
			member.setMemberName("member1");
			member.setTeam(team);
			em.persist(member);

			// flush(), clear()해야 양방향 관계 맵핑 테스트를 정확히 확인할 수 있음
			em.flush();
			em.clear();

			Member findMember = em.find(Member.class, member.getMemberId());
			List<Member> members = findMember.getTeam().getMembers();

			for (Member m : members) {
				System.out.println("멤버: " + m.getMemberId() + ", " + m.getMemberName());
			}

			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}

	}

}