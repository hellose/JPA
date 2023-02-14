package pub;

import javax.persistence.EntityTransaction;

public class EntityTransactionAop {

	private EntityTransaction tx;

	public EntityTransactionAop(EntityTransaction tx) {
		this.tx = tx;
	}

	public void begin() {
		this.print("before begin");
		this.tx.begin();
		this.print("after begin");
	}

	public void commit() {
		this.print("before commit");
		this.tx.commit();
		this.print("after commit");
	}

	public void rollback() {
		this.print("before rollback");
		this.tx.rollback();
		this.print("after rollback");
	}

	public EntityTransaction getTransaction() {
		return this.tx;
	}

	public void print(String str) {
		System.out.println("=====> " + str);
	}

}
