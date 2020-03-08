import java.io.*;
import java.nio.*;
import java.time.LocalDate;
import java.util.*;

public class Test {

	public static void main(String args[]) throws FileNotFoundException, IOException {
		Store magazin = new Store();
		File store = new File("store.txt");
		File customers =  new File("customers.txt");
		File events = new File("events.txt");
		
		//Primul fisier, cel cu departamentele
		Scanner fileScanner = new Scanner(store);
		Department d = new Department();
		String B = "BookDepartment";
		String M = "MusicDepartment";
		String S = "SoftwareDepartment";
		String V = "VideoDepartment";
		int i = 0;
		magazin.nume = fileScanner.nextLine();					//Numele magazinului
		while ( fileScanner.hasNextLine() == true ) {
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			lineScanner.useDelimiter(";|\\n");

			if ( lineScanner.hasNextInt() == true ) {
				int nr = lineScanner.nextInt();
			}
			else if ( lineScanner.hasNext() == true ) {
				String nume = lineScanner.next();
				if ( nume.compareTo(B) == 0 || nume.compareTo(M) == 0 || nume.compareTo(S) == 0 || nume.compareTo(V) == 0) {
					if ( i == 0) {								//Daca este primul departament gasit incep constructia lui
						d.nume = nume;
						d.id = lineScanner.nextInt();
						i++;
					}
					else {										//Altfel, trebuie sa adaug departamentul de dinainte si sa incepep
						magazin.departamente.add(d);					//constructia urmatorului
						d = new Department();
						d.nume = nume;
						d.id = lineScanner.nextInt();
						i++;
					}
				}
				else {
					int id = lineScanner.nextInt();				//Daca este un produs, stochez datele despre el si il adaug in departament
					float pret = lineScanner.nextFloat();
					Item item = new Item(nume, id, pret);
					d.produse.add(item);
				}
			}
			lineScanner.close();
		}
		fileScanner.close();
		
		fileScanner = new Scanner(customers);
		int nr1 = fileScanner.nextInt();						//Nr de clienti
		while ( fileScanner.hasNextLine() == true) {
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);			//Datele despre clienti:
			lineScanner.useDelimiter(";|\\n");					
			String nume = lineScanner.next();					//Nume
			int buget = lineScanner.nextInt();					//Buget
			String ch = lineScanner.next();						//Strategie
			Customer c = new Customer (nume, buget);
			magazin.enter(c);									//Adaug clientul
			lineScanner.close();
		}
		fileScanner.close();
		
		fileScanner = new Scanner(events);
		int j = 0, id = 0, did = 0, iid = 0;
		float pret = 0;
		String tip = null, nume = null, wl = "WishList";
		Item item = new Item();
		Notification n = new Notification();
		Customer client = new Customer();
		//Pentru output
		PrintWriter output = new PrintWriter("output.txt","UTF-8");
		int nr2 = fileScanner.nextInt();
		while ( fileScanner.hasNextLine() == true) {
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			lineScanner.useDelimiter(";|\\n");
			String eveniment = lineScanner.next();				//Numele evenimentului este primul bloc al fiecarei linii
																		//si avem 11 cazuri, tratate cu switch
			switch(eveniment) {
				case "addItem":									//Daca adaug un produs, primesc id-ul, tip-ul listei in care
					id = lineScanner.nextInt();							//urmeaza sa fie adaugat si numele clientului
					tip = lineScanner.next();
					nume = lineScanner.next();					
					for (i = 0; i < magazin.departamente.size(); i++) {
						d = magazin.departamente.get(i);		//Astfel, trebuie sa gasesc produsului dupa id, cautand departamentul
						for (j = 0; j < d.produse.size(); j++) {		//si apoi produsul, pe care il memorez
							item = d.produse.get(j);
							if ( item.id == id )
								break;
						}
					}
					for (i = 0; i < magazin.clienti.size(); j++) {
						client = magazin.clienti.get(i);		//Trebuie sa gasesc si clientul, iar cand il gasesc adaug produsul
						if ( nume.compareTo(client.nume) == 0 ) {		//fie in WishList, fie in ShopppingCart si actualizez lista clientilor
							if ( tip.compareTo(wl) == 0 ) {						//si implicit si WishList-ul si ShoppingCart-ul lor
								client.lista.add(item);
								magazin.clienti.remove(i);
								magazin.clienti.add(i, client);
								break;
							}
							else {
								client.cosC.add(item);
								magazin.clienti.remove(i);
								magazin.clienti.add(i, client);
								break;
							}
						}
					}
					break;
				case "delItem":									//Daca sterg un produs, primesc id-ul, tip-ul listei din care
					id = lineScanner.nextInt();							//urmeaza sa fie sters si numele clientului
					tip = lineScanner.next();
					nume = lineScanner.next();
					for (i = 0; i < magazin.departamente.size(); i++) {
						d = magazin.departamente.get(i);		//Astfel, trebuie sa gasesc produsului dupa id, cautand departamentul
						for (j = 0; j < d.produse.size(); j++) {		//si apoi produsul, pe care il memorez
							item = d.produse.get(j);
							if ( item.id == id )
								break;
						}
					}
					for (i = 0; i < magazin.clienti.size(); j++) {
						client = magazin.clienti.get(i);		//Trebuie sa gasesc si clientul, iar cand il gasesc sterg produsul
						if ( nume.compareTo(client.nume) == 0 ) {		//fie din WishList, fie din ShopppingCart si actualizez lista clientilor
							if ( tip.compareTo(wl) == 0 ) {						//si implicit si WishList-ul si ShoppingCart-ul lor
								client.lista.remove(item);
								magazin.clienti.remove(i);
								magazin.clienti.add(i, client);
								break;
							}
							else {
								client.cosC.remove(item);
								magazin.clienti.remove(i);
								magazin.clienti.add(i, client);
								break;
							}
						}
					}
					break;
				case "addProduct":								//Daca adaug un produs intr-un departament, primesc id-ul departamentului,
					did = lineScanner.nextInt();						//id-ul produsului, pretul si numele sau
					iid = lineScanner.nextInt();
					pret = lineScanner.nextFloat();
					nume = lineScanner.next();
					item = new Item(nume,iid,pret);				//Gasesc departamentul folosindu-ma de o functie ce il cauta dupa id si
					d = magazin.getDepartment(did);						//adaug produsul
					d.produse.add(item);								
					n.dataa = LocalDate.now();					//Observatorii din lista de observatori a departamentului vor primi
					n.idProdus = iid;									//o notificare despre adaugarea produsului, ce contine data, id-ul
					n.idDepartament = did;										//produsului si cel al departamentului
					n.tip = Notification.NotificationType.ADD;
					d.notifyAllObservers(n);
					magazin.departamente.remove(i);				//Actualizez lista cu departamente si implicit cea cu produse
					magazin.departamente.add(i, d);
					break;
				case "modifyProduct":							//Daca modific pretul unui produs dintr-un departament, primesc id-ul
					did = lineScanner.nextInt();						//departamentului, id-ul produsului si noul sau pret
					iid = lineScanner.nextInt();
					pret = lineScanner.nextFloat();
					d = magazin.getDepartment(did);				//Gasesc departamentul folosindu-ma de o functie ce il cauta dupa id
					for (j = 0; j < d.produse.size(); j++) {
						item = d.produse.get(j);				//Trebuie sa gasesc si produsul, iar cand il gasesc ii modific pretul
						if ( item.id == iid ) {								//folosindu-ma de o functie special creata
							item.modifyP(pret);
							n.dataa = LocalDate.now();			//Observatorii din lista de observatori a departamentului vor primi
							n.idProdus = iid;								//o notificare despre  produsului, ce contine data, id-ul
							n.idDepartament = did;									//produsului si cel al departamentului
							n.tip = Notification.NotificationType.MODIFY;
							d.notifyAllObservers(n);
							d.produse.remove(j);				//Actualizez lista cu departamente si implicit cea cu produse
							d.produse.add(j, item);
						}
						magazin.departamente.remove(i);
						magazin.departamente.add(i, d);
						break;
					}
					break;
				case "delProduct":								//Daca ster un produs intr-un departament, primesc doar id-ul produsului
					iid = lineScanner.nextInt();
					for (i = 0; i < magazin.departamente.size(); i++) {
						d = magazin.departamente.get(i);		//Astfel, caut produsul in fiecare departament, pana cand il gasesc si il sterg
						did = d.getId();
						for (j = 0; j < d.produse.size(); j++) {
							item = d.produse.get(j);
							if ( item.id == iid ) {
								d.produse.remove(j);
								break;
							}
						}										//Produsul trebuie sters si din cosul de cumparator al clientilor
						for (j = 0; j < d.clienti.size(); j++) {
							client = d.clienti.get(i);			//Parcurg lista de clienti a magazinului, iar in cazul in care produsul
							if ( client.cosC.contains(item) == true )		//se afla in cosul vreunuia dintre ei il sterg
								client.cosC.remove(item);
						}
						n.dataa = LocalDate.now();				//Observatorii din lista de observatori a departamentului vor primi
						n.idProdus = iid;								//o notificare despre  produsului, ce contine data, id-ul
						n.idDepartament = did;										//produsului si cel al departamentului
						n.tip = Notification.NotificationType.REMOVE;
						d.notifyAllObservers(n);
						magazin.departamente.remove(i);			//Actualizez lista cu departamente si implicit cea cu produse
						magazin.departamente.add(i, d);
					}
					break;
				case "getItem":									//Nu am facut bonusul cu strategii, deci getItem nu este relevanta
					break;
				case "getItems":								//Intoarce fie WishList-ul, fie ShoppingCart-ul unui client, al carui nume
					tip = lineScanner.next();							//este dat
					nume = lineScanner.next();
					for (i = 0; i < magazin.clienti.size(); j++) {
						client = magazin.clienti.get(i);		//Parcurg lista de clienti a magazinului pana gasesc clientul respectiv
						if ( nume.compareTo(client.nume) == 0 ) {
							if ( tip.compareTo(wl) == 0 ) {		//Aflu daca tipul de lista cerut este WishList sau ShoppingCart
								ItemList lista = client.lista;			//si afisez in fisierul de output ce este cerut
								output.println(lista + "\n");
								break;
							}
							else {
								ItemList cosC = client.cosC;
								output.println(cosC + "\n");
								break;
							}
						}
					}
					break;	
				case "getTotal":								//Intoarce totalul WishList-ului sau al ShoppingCart-ului unui client
					tip = lineScanner.next();							//al carui nume este dat
					nume = lineScanner.next();
					for (i = 0; i < magazin.clienti.size(); j++) {
						client = magazin.clienti.get(i);		//Parcurg lista de clienti a magazinului pana gasesc clientul respectiv
						if ( nume.compareTo(client.nume) == 0 ) {
							if ( tip.compareTo(wl) == 0 ) {		//Aflu daca tipul de lista cerut este WishList sau ShoppingCart
								double s = client.lista.getTotalPrice();			
								output.println(s + "\n");		//Si afisez in fisierul de output totalul cerut
								break;
							}
							else {
								double s = client.cosC.getTotalPrice();
								output.println(s + "\n");
								break;
							}
						}
					}
					break;
				case "accept":									//Aplica anumite reduceri, in functie de tipul departamentului
					did = lineScanner.nextInt();				//Este dat id-ul departamentului si numele clientului care va
					nume = lineScanner.next();							//beneficia de ogerta
					d = magazin.getDepartment(did);				//Gasesc departamentul folosindu-ma de o functie ce il cauta dupa id
					for (j = 0; j < d.clienti.size(); j++) {
						client = d.clienti.get(j);				//Trebuie sa gasesc si clientul, iar cand il gasesc aplic functia
						if ( nume.compareTo(client.nume) == 0 ) {		//special creata, accept()
							d.accept(client.cosC);
							d.clienti.remove(j);
							d.clienti.add(j, client);
							break;
						}
						magazin.departamente.remove(i);			//Actualizez lista cu departamente si implicit cea cu clienti
						magazin.departamente.add(i, d);
						break;
					}
					break;
				case "getObservers":							//Se da id-ul unui departament si trebuie sa afisez lista sa 
					did = lineScanner.nextInt();						//cu observatori
					d = magazin.getDepartment(did);				//Gasesc departamentul folosindu-ma de o functie ce il cauta dupa is
					List<Customer> observatori = d.observatori;			//si afisez lista cu observatori
					output.println(observatori + "\n");
					break;
				case "getNotifications":						//Se da numele unui client si trebuie sa afisez colectia sa
					nume = lineScanner.next();							//de notificari
					for (i = 0; i < magazin.clienti.size(); i++) {
						client = magazin.clienti.get(i);		//Parcurg lista de clienti a magazinului pentru a gasi 
						if ( nume.compareTo(client.nume) == 0 ) {		//clientul respectiv si afisez colectia sa de notificari
							Collection<Notification> notificari = client.notificari;
							output.println(notificari + "\n");
							break;
						}
					}
					break;
			}
			lineScanner.close();
		}
		fileScanner.close();
	}
	//Si cam atat ^.^
}