import java.util.*;

public class Department {

	String nume;
	List<Item> produse = new ArrayList<Item>();
	List<Customer> clienti = new ArrayList<Customer>();
	List<Customer> observatori = new ArrayList<Customer>();
	int id;
	
	public Department(){
		nume = null;
		id = 0;
	}
	
	//enter(Customer) - clientul a cumpărat cel putin un produs din departamentn
	public void enter(Customer client) {
		int i = 0, ok1 = 0, ok2 = 0;
		for (i = 0; i < clienti.size(); i++) {
			Customer c = clienti.get(i);
			if (c == client) {
				ok1 = 1;
				break;
			}
		}
		for (i = 0; i < produse.size(); i++) {
			Item p = produse.get(i);
			if (client.cosC.contains(p) == false) {
				ok2 = 1;
				break;
			}
		}
		if (ok1 == 0 && ok2 == 0)
			clienti.add(client);
	}
	
	//exit(Customer) - clientul s-a hotărât că nu mai doreste să cumpere vreodată vreun produs din departament
	public void exit(Customer client) {
		int i = 0, pozitie = 0, ok = 0;
		for (pozitie = 0; pozitie < clienti.size(); pozitie++) {
			Customer c = clienti.get(pozitie);
			if (c == client) 
				break;
		}
		for (i = 0; i < produse.size(); i++) {
			Item p = produse.get(i);
			if (client.cosC.contains(p) == true) {
				ok = 1;
				break;
			}
		}
		if (ok == 0)
			clienti.remove(pozitie);
	}
	
	//getCustomers() - întoarce toti clientii care au cumpărat cel putin un produs din departament
	public List<Customer> getCustomers() {
		return clienti;
	}
	
	//getId() - întoarce ID-ul departamentului
	public int getId() {
		return id;
	}
	
	//addItem(Item) - adaugă un item în produsele departamentului
	public void addItem(Item item) {
		produse.add(item);
	}
	
	//getItems() - întoarce produsele departamentului
	public List<Item> getItems(){
		return produse;
	}
	
	//addObserver(Customer) - metodă apelată când un client adaugă în WishList un produs din departament
	public void addObserver(Customer observator) {
		int i = 0, ok1 = 0, ok2 = 0, ok3 = 0;
		for (i = 0; i < clienti.size(); i++) {
			Customer c = clienti.get(i);
			if (c == observator) {
				ok1 = 1;
				break;
			}
		}
		for (i = 0; i < observatori.size(); i++) {
			Customer o = observatori.get(i);
			if (o == observator) {
				ok2 = 1;
				break;
			}
		}
		for (i = 0; i < produse.size(); i++) {
			Item p = produse.get(i);
			if (observator.lista.contains(p) == false) {
				ok3 = 1;
				break;
			}
		}
		if (ok1 == 0 && ok2 == 0 && ok3 == 0)
			observatori.add(observator);
	}
	
	//removeObserver(Customer) - metodă apelată când un client nu are în WishList nici măcar un produs din departament
	public void removeObserver(Customer observator) {
		int i = 0, pozitie = 0, ok = 0;
		for (pozitie = 0; pozitie < observatori.size(); pozitie++) {
			Customer o = observatori.get(i);
			if (o == observator)
				break;
		}
		for (i = 0; i < produse.size(); i++) {
			Item p = produse.get(i);
			if (observator.lista.contains(p) == true) {
				ok = 1;
				break;
			}
		}
		if (ok == 0)
			observatori.remove(pozitie);
	}
	
	//notifyAllObservers(Notification) - metodă apelată dacă este adăugat sau eliminat un produs din departament
	public void notifyAllObservers(Notification n) {
		int i = 0;
		for (i = 0; i < observatori.size(); i++) {
			Customer o = observatori.get(i);
			o.notificari.add(n);
		}
	}
	
	//accept(ShoppingCart) - depinde de departament
	public void accept(ShoppingCart cosC) {
		String B = "BookDepartment";
		String M = "MusicDepartment";
		String S = "SoftwareDepartment";
		String V = "VideoDepartment";
		double reducere = 0, max = 1, min = 100000, sd = 0;
		double s = cosC.getTotalPrice();
		int i = 0;
		for (i = 0; i < produse.size(); i++) {
			Item p = produse.get(i);
			if (p.pret < min)
				min = p.pret;
			if (p.pret > max)
				max = p.pret;
		}
		for (i = 0; i < produse.size(); i++) {
			Item p = produse.get(i);
			if ( cosC.contains(p) == true ) {
				if ( B.compareTo(nume) == 0 )
					reducere = reducere - 0.1 * p.pret;
				if ( M.compareTo(nume) == 0 )
					reducere = reducere + 0.1 * p.pret;
				if ( S.compareTo(nume) == 0 && cosC.buget < min )
					reducere = reducere - 0.2 * p.pret;
				if ( V.compareTo(nume) == 0 ) {
					reducere = reducere + 0.05 * p.pret;
					sd = sd + p.pret;
				}
			}
		}
		if ( V.compareTo(nume) == 0 && sd > max ) 
			reducere = reducere - 0.15 * sd;
		s = s + reducere;
		cosC.buget = s;
	}
}
