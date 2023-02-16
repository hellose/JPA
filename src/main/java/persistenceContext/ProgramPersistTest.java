package persistenceContext;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pub.EntityManagerAop;
import pub.EntityTransactionAop;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Entity
@Table(name = "test_entity")
class TestEntity {
	@Id
	private Integer id;
	private String name;
}

//@Id를 직접 셋팅해야하는 Entity의 경우 EntityManager.persist 메서드 테스트
class ProgramPersistTest {

	public static void main(String[] args) {
//		test1();
//		test2();
		test3();
	}

	public static void test1() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			TestEntity entity1 = TestEntity.builder().id(1).name("test1").build();
			em.persist(entity1);
			System.out.println("영속성 컨텍스트에 관리되고 있는 객체인가?: " + em.contains(entity1));

			// 동일 @Id에 해당하는 객체가 이미 영속성 컨텍스트 내부에 존재함 -> Exception발생
//			TestEntity entity2 = TestEntity.builder().id(1).name("test2").build(); 
//			em.persist(entity2);

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

			TestEntity entity1 = TestEntity.builder().id(1).name("test1").build();
			em.persist(entity1);
			// Id 1인 객체가 존재하지만 인자 전달시 영속 상태인 동일한 객체를 넘긴 경우에는 에러 발생하지 않음!!!
			em.persist(entity1);

			TestEntity entity2 = TestEntity.builder().id(2).name("test2").build();
			em.persist(entity2);
			TestEntity noPersist = TestEntity.builder().id(2).name("test2").build();
			// Id 2인 객체가 존재하지만 인자 전달시 비영속 객체를 넘겼기 때문에 에러 발생
			em.persist(noPersist);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		} finally {
			em.close();
			emf.close();
		}
	}

	// persist -> detach -> persist
	public static void test3() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			TestEntity entity1 = TestEntity.builder().id(1).build();
			em.persist(entity1);
			em.detach(entity1);

			// https://www.inflearn.com/questions/304787/detach-%ED%9B%84%EC%97%90-merge-%EC%97%90-%EA%B4%80%ED%95%9C-%EC%A7%88%EB%AC%B8%EC%9E%85%EB%8B%88%EB%8B%A4
			// 여기서 flush나 clear하면 잘되는데 안해주면 에러 -> jpa 강좌 질문 답변 하이버네이트 에러임
			TestEntity entity2 = TestEntity.builder().id(1).build();
			// 여기서 에러 발생
//			em.persist(entity2);

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
