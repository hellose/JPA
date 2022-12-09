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
			
			// 연관관계의 주인에 값을 설정
			// setter내부에 반대방향에 해당하는 코드 추가되어있음
			member.setTeam(team);
			em.persist(member);
			
			//주석 처리해도 List 필드에 멤버 저장되어있기 때문에 정상적으로 확인됨
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