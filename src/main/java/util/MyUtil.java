package util;

public class MyUtil {

	public static void sameIdentityHashCode(Object first, Object second) {
		System.out.println(
				"=>same identityhashcode: " + (System.identityHashCode(first) == System.identityHashCode(second)));
	}

	public static void printClass(Object obj) {
		System.out.println("=>getClass: " + obj.getClass().toString());
	}

	public static void isNull(Object obj) {
		if (obj == null) {
			System.out.println("=>null");
		} else {
			System.out.println("=>not null");
		}
	}

}
