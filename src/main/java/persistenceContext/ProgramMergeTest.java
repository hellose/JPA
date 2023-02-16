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
@Table(name = "merge_test_entity")
class MergeTestEntity {
	@Id
	private Integer id;
	private String column1;
	private String column2;
}

//EntityManager의 merge 메서드 테스트
//준영속 상태 - 영속상태를 한번 거친뒤 비영속으로 바뀐 상태 -> 비영속이이면서 @Id는 반드시 채워져있는 객체
class ProgramMergeTest {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			MergeTestEntity entity1 = MergeTestEntity.builder().id(1).column1("col1").column2("col2").build();
			em.persist(entity1);

			MergeTestEntity noPersist = MergeTestEntity.builder().id(1).build();
			
			// 현재의 경우 - 1차 캐시에 @Id가 1인 entity1이 존재하므로 entity1에 noPersist의 필드값들을 덮어씌운뒤 entity1 리턴
			// 1차 캐시에 없는 경우 - db에서 조회 후 객체를 만든 뒤 객체에 noPersist의 필드값들을 덮어씌운뒤 해당 객체 리턴
			// pk를 제외한 모든 필드 null인 상태 -> merge시 모든 컬럼 null로 덮어씌어져 버림
			MergeTestEntity merged = em.merge(noPersist);
			System.out.println("entity1 == merged: " + (entity1 == merged));
			System.out.println("merged.toString(): " + merged.toString());
			

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

//merge시 db에서 조회하는 경우
class ProgramMergeTest2 {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			//merge 테스트를 위한 엔티티 저장 
			MergeTestEntity m = MergeTestEntity.builder().id(1).column1("col1").column2("col2").build();
			em.persist(m);
			//insert쿼리 db 반영
			em.flush();
			//영속성 컨텍스트 날림
			em.clear();
			m = null;
			
			MergeTestEntity t = MergeTestEntity.builder().id(1).column1("컬럼1").column2("컬럼2").build();
			MergeTestEntity merged = em.merge(t);
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

//merge - 마지막 스냅샷 때의 필드 값과 다른 것들만 update에 반영
class ProgramMergeTest3 {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			MergeTestEntity entity1 = MergeTestEntity.builder().id(1).column1("col1").column2("col2").build();
			em.persist(entity1);

			MergeTestEntity noPersist = MergeTestEntity.builder().id(1).column1("col1").column2("col2").build();
			MergeTestEntity merged = em.merge(noPersist);
			
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


//TODO - 정확히 이해필요
//merge - 인자에 준영속(id가 존재하는 비영속)대신 영속 객체를 넘기는 경우
class ProgramMergeTest4 {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManagerAop em = new EntityManagerAop(emf.createEntityManager());
		EntityTransactionAop tx = em.getTransaction();

		try {
			tx.begin();

			MergeTestEntity entity1 = MergeTestEntity.builder().id(1).column1("col1").column2("col2").build();
			em.persist(entity1);
			
			entity1.setColumn1("컬럼1");
			
			MergeTestEntity noPersist = MergeTestEntity.builder().id(1).column1("컬럼1").column2("col2").build();
			em.merge(noPersist);
			
			
			
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





