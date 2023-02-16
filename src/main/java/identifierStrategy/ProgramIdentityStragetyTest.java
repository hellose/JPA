package identifierStrategy;

import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pub.EntityManagerAop;
import pub.EntityTransactionAop;

//@Entity
@Getter
@Setter
class Identity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String col;
}

//@Entity
@Getter
@Setter
@AllArgsConstructor
class Manually {

	@Id
	private Integer id;
}

class ProgramIdentityStragetyTest {

	public static void main(String[] args) {
//		test1();
		test2();
	}

	// Identity 전략 -> db에 insert쿼리를 날릴 때 pk가 할당되기 때문에 insert쿼리가 바로 나갈 수 밖에없음
	// 이때 영속성 컨텍스트도 같이 flush 된다.
	public static void test1() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			Manually m = new Manually(1);
			em.persist(m);

			Identity i = new Identity();
			// Manully 엔티티의 insert쿼리가 나가는 것을 통해 영속성 컨텍스트가 flush됨을 확인가능
			em.persist(i);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	public static void test2() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			Identity i = new Identity();
			em.persist(i);
			em.flush();
			
			//sequence전략은 @Id를 비워야하지만 현재는 영속 객체를 인자로 전달했기 때문에 에러 발생 안함
			em.persist(i);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

}