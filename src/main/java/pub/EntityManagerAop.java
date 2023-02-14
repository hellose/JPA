package pub;

import javax.persistence.EntityManager;

public class EntityManagerAop {

	private EntityManager em;

	public EntityManagerAop(EntityManager em) {
		this.em = em;
	}

	public void persist(Object obj) {
		this.print("before persist");
		em.persist(obj);
		this.print("after persist");
	}

	public void flush() {
		this.print("before flush");
		em.flush();
		this.print("after flush");
	}

	public void detach(Object obj) {
		this.print("before detach");
		em.detach(obj);
		this.print("after detach");
	}

	public void remove(Object obj) {
		this.print("before remove");
		em.remove(obj);
		this.print("after remove");
	}

	public void clear() {
		this.print("before clear");
		em.clear();
		this.print("after clear");
	}

	public void close() {
		this.print("before close");
		em.close();
		this.print("after close");
	}

	public EntityTransactionAop getTransaction() {
		return new EntityTransactionAop(em.getTransaction());
	}

	public EntityManager getEntityManager() {
		return this.em;
	}

	public void print(String str) {
		System.out.println("=====> " + str);
	}

}
