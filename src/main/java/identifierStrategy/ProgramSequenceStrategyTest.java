package identifierStrategy;

import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pub.EntityManagerAop;
import pub.EntityTransactionAop;

@Getter
@Setter
@SequenceGenerator(name = "test_sequence_generator", allocationSize = 1, initialValue = 1, sequenceName = "test_sequence")
@ToString
//@Entity
class Sequence {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_sequence_generator")
	private Integer id;
	private String col;
}

class ProgramSequenceStrategyTest {

	public static void main(String[] args) {
//		test1();
		test2();
	}

	// sequence 전략
	// persist시 db에서 시퀀스 값 조회 후 영속 객체에 셋팅함
	public static void test1() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			Sequence s = new Sequence();
			// persist시 db에서 시퀀스를 가져와 s의 id필드에 셋팅
			em.persist(s);
			System.out.println("영속 객체: " + s.toString());
			em.flush();

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	// 시퀀스 가져온뒤 db반영하지 않는 경우 중간 시퀀스는 건너뛰어버림
	public static void test2() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			Sequence temp = new Sequence();
			em.persist(temp);
			System.out.println("시퀀스만 땡겨오고 객체 준영속으로 변경할 것임: " + temp.toString());
			em.detach(temp);
			em.clear();

			Sequence s = new Sequence();
			em.persist(s);
			System.out.println("영속상태에서 db저장할 객체: " + s.toString());
			em.flush();

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
