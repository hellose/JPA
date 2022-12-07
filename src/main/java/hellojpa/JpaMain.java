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
			//EntityTransaction이 commit되면 플러시가 발생
			// -> 변경 감지, 수정된 엔티티 쓰기 지연 SQL 저장소에 등록 -> 실제 db commit
			
			//직접 플러시를 쓸일은 별로 없지만 알아놔야 좋음
			
			//직접 호출하는 경우 - em.flush()
			//자동 호출되는 경우 - em.commit() 또는 JPQL쿼리실행
			
			Member member = new Member(200L, "member200");
			em.persist(member);
			
			//이렇게 할 경우 여기서 insert쿼리가 나감
			//중요한 것은 flush()시 1차 캐시가 날라가는게 아니라 최신 정보상태를 유지하고 있음
			em.flush();
			
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
