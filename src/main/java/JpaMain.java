import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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

			// setter 관례로 인해 정의된 메서드를 호출하여 의도를 나타앰
			team.addMember(member);

			em.persist(member);

//			em.flush();
//			em.clear();

			// 확인
			Team teamA = em.find(Team.class, 1L);
			for (Member m : teamA.getMembers()) {
				System.out.println("멤버: " + m.getMemberId() + ", " + m.getMemberName());
			}

			tx.commit();

		} catch (Exception e) {
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}

	}

}