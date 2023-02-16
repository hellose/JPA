package persistenceContext;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.SequenceGenerator;
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

//@Id에 시퀀스 전략을 사용하는 Entity의 경우 EntityManager.persist 메서드 테스트
class ProgramPersistTest2 {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			SequenceStrategyTestEntity entity1 = SequenceStrategyTestEntity.builder().name("test1").build();
			//db에서 시퀀스값 조회 후 id 필드에 셋팅함  
			em.persist(entity1);
			//id필드가 셋팅되어있음을 확인 가능
			System.out.println(entity1);

			// @Id 전략을 사용할때 pk를 직접 셋팅함 -> Exception 발생 
//			SequenceStrategyTestEntity entity2 = SequenceStrategyTestEntity.builder().id(1).name("test2").build(); 
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

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sequence_strategy_test_entity")
@SequenceGenerator(name = "sequence_strategy_test_entity_id_generator", allocationSize = 1, initialValue = 1, sequenceName = "sequence_strategy_test_entity_pk_sequence")
class SequenceStrategyTestEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_strategy_test_entity_id_generator")
	private Integer id;
	private String name;
}