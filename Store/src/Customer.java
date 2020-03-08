import java.util.*;

public class Customer {
	String nume;
	ShoppingCart cosC;
	WishList lista;
	Collection<Notification> notificari = new ArrayList<Notification>();
	
	public Customer(String n, int b) {
		nume = n;
		cosC.buget = b;
	}
	
	public Customer() {
		nume = null;
		cosC.buget = 0;
	}
}