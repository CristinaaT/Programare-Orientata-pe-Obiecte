public class Item {
	String nume;
	int id;
	float pret;
	
	public Item (String n, int i, float p) {
		nume = n;
		id = i;
		pret = p;
	}
	
	public Item() {
		nume = null;
		id = 0;
		pret = 0;
	}
	
	//Modific pretul produsului
	public void modifyP (float p) {
		pret = p;
	}
}