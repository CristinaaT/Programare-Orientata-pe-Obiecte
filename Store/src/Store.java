import java.util.*;

public class Store {

	String nume;
	List<Customer> clienti = new ArrayList<Customer>();
	List<Department> departamente = new ArrayList<Department>();
	
	public Store() {
		nume = null;
	}
	
	//enter(Customer) - clientul a intrat în magazin
	//Parcurg lista de clienti sa vad daca clientul este in mnagazin. In caz contrar, il adaug
	public void enter(Customer client) {
		int i = 0, ok = 0;
		for (i = 0; i < clienti.size(); i++) {
			Customer c = clienti.get(i);
			if (c == client)
				ok = 1;
		}
		if (ok == 0)
			clienti.add(client);
	}
	
	//exit(Customer) - clientul a iesit din magazin
	//Parcurg lista de clienti sa vad daca clientul este in magazin. In caz afirmativ, il sterg
	public void exit(Customer client) {
		int i = 0;
		for (i = 0; i < clienti.size(); i++) {
			Customer c = clienti.get(i);
			if (c == client) {
				clienti.remove(i);
				break;
			}
		}
	}
	
	
	//getShoppingCart(Double) - întoarce un obiect de tip ShoppingCart
	public ShoppingCart getShoppingCart(double buget) {
		ShoppingCart cosC = new ShoppingCart();
		cosC.buget = buget;
		return cosC;
		
	}
	
	//getCustomers() - întoarce toti clientii care sunt în magazin la acel moment
	public List<Customer> getCustomers(){
		return clienti;
	}
	
	//getDepartments() - întoarce departamentele magazinului respectiv
	public List<Department> getDepartments(){
		return departamente;
	}
	
	//addDepartment(Department) - adaugă un departament
	public void addDepartment(Department d) {
		departamente.add(d);
	}
	
	//getDepartment(Integer) - returnează departamentul ce are ID-ul indicat
	//Parcurg lista de departamente  pentru a gasi departamentul cu id-ul indicat
	public Department getDepartment(int id) {
		int i = 0;
		for (i = 0; i < departamente.size(); i++) {
			Department d = departamente.get(i);
			if (id == d.id)
				return d;
			}
		return null;
	}
	
}
