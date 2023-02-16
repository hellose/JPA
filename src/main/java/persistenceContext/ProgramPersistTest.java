package persistenceContext;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pub.EntityManagerAop;
import pub.EntityTransactionAop;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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

}
