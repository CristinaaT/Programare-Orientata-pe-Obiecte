import java.util.*;

abstract class ItemList {
	Node head;
	
	//Nodul
	public static class Node {
		Item item;
		Node prev;
		Node next;
		
		public Node(Item i) {
			item = i;
			next = null;
			prev = null;
		}
		
		public Node() {
			item = null;
			next = null;
			prev = null;
		}
		
	}
	
	//Iteratorul
	public class ItemIterator implements ListIterator<Item> {
		Node node;
		
		public ItemIterator(Node n) {
			node = n;
		}
		
		//Adauga un nod care contine produsul i, dupa nodul curent
		public void add(Item i) {
			Node tmp = node.next;
			Node n = new Node(i);
			n.prev = node;
			node.next = n;
			n.next = tmp;
			tmp.prev = n;
		}

		//Verifica daca mai exista un produs dupa cel actual
		public boolean hasNext() {
			if (node.next != null)
				return true;
			else return false;
		}

		//Verifica daca exista un produs inaintea celui actual
		public boolean hasPrevious() {
			if (node.prev != null)
				return true;
			else return false;
		}

		//Returneaza produsul urmator
		public Item next() {
			if (node.next != null)
				return node.next.item;
			else return null;
		}
		
		//Returneaza nodul urmator
		public Node urm() {
			if (node.next != null)
				return node.next;
			else return null;
		}

		public int nextIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		//Returneaza produsul anterior
		public Item previous() {
			if (node.prev != null)
				return node.prev.item;
			else return null;
		}

		public int previousIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		//Sterge nodul actual
		public void remove() {
			Node tmp = node.prev;
			node = node.next;
			node.prev = tmp;
		}

		//Seteaza produsul nodului actual cu produsul dat
		public void set(Item i) {
			node.item = i;
		}
		
	}
	
	//Comparatorul
	public class Comparare implements Comparator<Node> {
		public int compare(Node node1, Node node2) {
			if (node1.item.pret > node2.item.pret)
				return 1;
			else if (node1.item.pret < node2.item.pret)
				return -1;
			else {
				String nume1 = node1.item.nume;
				String nume2 = node2.item.nume;
				return nume1.compareTo(nume2);
			}
		}
	}
	
	//Functii
	//add(Item) - adauga Item in lista
	public boolean add(Item i) {
		if (contains(i) == true)							//Verific daca nu cumva exista deja produsul in lista
			return false;
		else {
			Node n = new Node(i);
			ItemIterator it = new ItemIterator(head);		//Ma folosesc de un iterator pentru a parcurge lista
			while (it.hasNext() == true) {
				if (i.pret < it.node.item.pret) {			//Compar pretul produsului dat cu cel al produsului 
					Node tmp = it.node.prev;						//produsului de la nodul curent, iar daca este
					tmp.next = n;											//mai mic, aici adaug noul nod
					it.node.prev = n;
					n.prev = tmp;
					n.next = it.node;
				}
				else if (i.pret == it.node.item.pret) {		//Daca preturile sunt egale, produsele trebuie sa fie in
					String nume1 = i.nume;							//ordine alfabetica, astfel ca le compar numele
					String nume2 = it.node.item.nume;
					int ans = nume1.compareTo(nume2);
					if (ans > 0) {							//Daca produsul care trebuie adaugat este inainte, din
						Node tmp = it.node.prev;					//punct de vedere alfabetic, il adaug
						tmp.next = n;
						it.node.prev = n;
						n.prev = tmp;
						n.next = it.node;	
					}
					else it.add(i);							//Altfel, ne folosim de functia add
				}
				else it.node = it.urm();
			}
		}
		return true;
	}
	
	//addAll()
	
	
	//getItem(index) - returneaza Item-ul cu index-ul indicat
	public Item getItem(int index) {
		int i = 1;
		ItemIterator it = new ItemIterator(head);			//Ma folosesc de un iterator pentru a parcurge lista
		while(it.hasNext() == true) {
			if (i == index)
				return it.node.item;						//Returnez produsul de la index-ul dat
			else {
				i++;										//Incrementez i
				it.node = it.urm();							//Comtinui cautarea
			}
		}
		if (i == index)										//Daca index-ul este chiar ultima pozitie, returnez produsul
			return it.node.item;
		else return null;									//Altfel, index-ul era mai mare decat lungimea listei
	}
	
	
	//getNode(index) - returneaza nodul cu index-ul indicat
	public Node getNode(int index) {
		int i = 1;
		ItemIterator it = new ItemIterator(head);			//Ma folosesc de un iterator pentru a parcurge lista
		while(it.hasNext() == true) {
			if (i == index)
				return it.node;								//Returnez nodul de la index-ul dat
			else {
				i++;										//Incrementez i
				it.node = it.urm();							//Comtinui cautarea
			}
		}
		if (i == index)										//Daca index-ul este chiar ultima pozitie, returnez nodul
			return it.node;
		else return null;									//Altfel, index-ul era mai mare decat lungimea listei
	}
	
	//indexOf(Item) - returneaza index-ul Item-ului indicat
	public int indexOf(Item i) {
		ItemIterator it = new ItemIterator(head);			//Ma folosesc de un iterator pentru a parcurge lista
		int index = 1;
		while (it.hasNext() == true) {
			if (it.node.item == i)							
				return index;								//Returnez index-ul produsului dat
			else {
				index++;									//Incrementez index-ul
				it.node = it.urm();							//Comtinui cautarea
			}
		}
		if (it.node.item == i)								//Daca produsul este chiar pe ultima pozitie, returnez index-ul
			return index;
		else return -1;										//Altfel, produsul nu face parte din lista
	}
	
	//indexOf(Node) - returneaza index-ul nodului indicat
	public int indexOf(Node n) {
		ItemIterator it = new ItemIterator(head);			//Ma folosesc de un iterator pentru a parcurge lista
		int index = 1;
		while (it.hasNext() == true) {
			if (it.node == n)
				return index;								//Returnez index-ul nodului dat
			else {
				index++;									//Incrementez index-ul
				it.node = it.urm();							//Comtinui cautarea
			}
		}
		if (it.node == n)									//Daca nodul este chiar ultimul, returnez index-ul
			return index;
		else return -1;										//Altfel, nodul nu face parte din lista
	}
	
	
	//contains(Item) - verifica daca lista contine Item-ul indicat
	public boolean contains(Item i) {
		ItemIterator it = new ItemIterator(head);			//Ma folosesc de un iterator pentru a parcurge lista		
		while (it.hasNext() == true) {
			if (it.node.item == i)							
				return true;								//Daca lista contine produsul, returnez true
			else it.node = it.urm();						//Altfel, continui
			}
		if (it.node.item == i)								//Daca produsul este chiar pe ultima pozitie, returnez true
			return  true;
		else return false;									//Altfel returnez false - produsul nu exista in lista
	}
	
	
	//contains(Node) - verifica daca lista contine nodul indicat
	public boolean contains(Node n) {
		ItemIterator it = new ItemIterator(head);			//Ma folosesc de un iterator pentru a parcurge lista
		while (it.hasNext() == true) {
			if (it.node == n)								//Daca lista contine nodul, returnez true
				return true;
			else it.node = it.urm();						//Altfel, continui
		}
		if (it.node == n)									//Daca nodul este chiar ultimul, returnez true
			return true;
		else return false;									//Altfel returnez false - nodul nu exista in lista
	}
	
	
	//remove(index) - sterge nodul de la index-ul indicat si returneaza Item-ul respectiv
	public Item remove(int index) {
		ItemIterator it = new ItemIterator(head);			//Ma folosesc de un iterator pentru a parcurge lista
		int i = 0;
		while (it.hasNext() == true) {
			if (i == index)									//Ies din instructiunea while daca am gasit nodul
				break;												//care contine produsul dat
			else {
				i++;										//Altfel inrementez i si continui cautare
				it.node = it.urm();
			}
		}
		if (i == index) {
			Item item = it.node.item;
			Node tmpP = it.node.prev;
			Node tmpN = it.node.next;
			tmpN.prev = tmpP;
			tmpP.next = tmpN;
			return item;									//Sterg nodul si returnez produsul
		}
		else return null;									//Altfel, produsul nu exista in lista
	}
	
	
	//remove(Item) - sterge nodul ce contine Item-ul respectiv
	public boolean remove(Item i) {
		ItemIterator it = new ItemIterator(head);			//Ma folosesc de un iterator pentru a parcurge lista
		while (it.hasNext() == true) {
			if (it.node.item == i)							//Ies din instructiunea while daca am gasit nodul
				break;												//care contine produsul dat
			else it.node = it.urm();						//Altfel continui cautare
		}
		if (it.node.item == i) {							
			Node tmpP = it.node.prev;						//Abia acum, cand scriu comentarile mi-am dat seama
			Node tmpN = it.node.next;								//ca am o functie pentru a sterge un nod
			tmpN.prev = tmpP;												//dar avand in vedere ca nu ruleaza,
			tmpP.next = tmpN;														//nu stiu cat de mult conteaza :))
			return true;									//Sterg nodul si returnez true
		}
		else return false;									//Altfel, nodul nu exista in lista si returnez false
	}
	
	//removeAll()
	
	
	//isEmpty() - verifica daca lista este goala
	public boolean isEmpty() {
		if (head.item == null && head.next == null)			//Verifica daca capul liste si urmatorul sunt goale
			return true;									//In caz afirmativ, returneaza true, deci lista este goala
		else return false;									//In caz negativ, returneaza false
	}
	
	
	//listIterator(index)
	public ListIterator<Item> listIterator(int index) {
		int i = 1;
		ItemIterator it = new ItemIterator(head);		//Ma folosesc de un iterator pentru a parcurge lista
		while(it.hasNext() == true) {
			if (i == index)
				return it;								//Returnez iteratorul de la index-ul dat
			else {
				i++;									//Incrementez i si continui cautarea
				it.node = it.urm();
			}
		}
		if (i == index)									//Daca index-ul este ultima pozitie, returnez iteratorul
			return it;
		else return null;								//Alfel, index-ul este mai mare decat lungimea listei
	}
	
	
	//listIterator()
	
	
	//getTotalPrice() - calculeaza pretul total al item-urilor din lista
	public double getTotalPrice() {
		ItemIterator it = new ItemIterator(head);		//Ma folosesc de un iterator pentru a parcurge lista
		double s = 0;
		while(it.hasNext() == true) {
			s = s + it.node.item.pret;					//Adaug pretul fiecarui produs din lista la suma
			it.node = it.urm();	
		}
		return s;										//Returnez suma
	}
	
}
