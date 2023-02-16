package pub;
import javax.persistence.EntityTransaction;

public class EntityTransactionAop {
	private EntityTransaction tx;

	public EntityTransactionAop(EntityTransaction tx) {
		this.tx = tx;
	}

	public void begin() {
		print("before begin");
		tx.begin();
		print("after begin");
	}

	public void commit() {
		print("before commit");
		tx.commit();
		print("after commit");
	}

	public void rollback() {
		print("before rollback");
		tx.rollback();
		print("after rollback");
	}

	public EntityTransaction getTransaction() {
		return tx;
	}

	public void print(String str) {
		System.out.println("=====> " + str);
	}
}
