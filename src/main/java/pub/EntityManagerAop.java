package pub;
import javax.persistence.EntityManager;

public class EntityManagerAop {
	private EntityManager em;

	public EntityManagerAop(EntityManager em) {
		this.em = em;
	}

	public <T> T find(Class<T> clz, Object primaryKey) {
		this.print("before find");
		T t = em.find(clz, primaryKey);
		print("after find");
		return t;
	}

	public void persist(Object obj) {
		print("before persist");
		em.persist(obj);
		print("after persist");
	}

	public void flush() {
		print("before flush");
		em.flush();
		print("after flush");
	}

	public void detach(Object obj) {
		print("before detach");
		em.detach(obj);
		print("after detach");
	}

	public void remove(Object obj) {
		print("before remove");
		em.remove(obj);
		print("after remove");
	}

	public void clear() {
		print("before clear");
		em.clear();
		print("after clear");
	}

	public void close() {
		print("before close");
		em.close();
		print("after close");
	}
	
	public <T> T merge(T t) {
		print("before merge");
		T merged = em.merge(t);
		print("after merge");
		return merged;
	}
	
	public boolean contains(Object obj) {
		return em.contains(obj);
	}

	public EntityTransactionAop getTransaction() {
		return new EntityTransactionAop(em.getTransaction());
	}

	public EntityManager getEntityManager() {
		return em;
	}

	public void print(String str) {
		System.out.println("=====> " + str);
	}
}
