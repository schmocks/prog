package tplan.app;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasse für eine Aufgaben-Kategorie, der weitere Unterkategorien zugeordnet
 * werden können.
 * 
 * @author Stephan
  */



public class Category implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String cname;
Color cfarbe;
ArrayList<Category> subcat = new ArrayList<Category>();
ArrayList<Task> task = new ArrayList<Task>();


/**
 * 
 * to String
 * @return info
 * 
 */
	
	public String toString(){
		String tasks = "";
		Task[] a = getTasks();
		if(getSubCategories().length==0){
			for(int i = 0; i<getTasks().length; i++){
				tasks+=a[i];
			}
		}
		if(getSubCategories().length!=0){
			tasks+="  ";
			for(int j = 0;j<getSubCategories().length;j++){
				tasks+=getSubCategories()[j];
			}
		}
		return "+- "+cname + "\n" + tasks;
		
	}
	/**
	 * Konstruktor
	 * @param cname Name der Kategorie	
	 * @param cfarbe Farbe der Kategorie
	 * 
	 */


	Category(String cname, Color cfarbe){
		this.cname=cname; this.cfarbe=cfarbe;
	}
	
	/**
	 * @return Name der Kategorie
	 */
	
	public String getName() {
		return cname;
	}

	/**
	 * @return Farbe der Kategorie für graphische Darstellung
	 */
	public Color getColor() {
		return cfarbe;
	}

	/**
	 * Fügt dieser Kategorie eine neue Unter-Kategorie hinzu.
	 * 
	 * @param name Name der Unterkategorie
	 * 
	 * @return neu erzeugte Kategorie. null, wenn die Kategorie schon Aufgaben
	 *         enthält und daher keine Kategorien mehr aufnehmen darf.
	 * 
	 * @throws Exception Name schon vorhanden         
	 *         
	 */
	public Category addSubCategory(String name) throws Exception{
		if(task.isEmpty()){
			Category a = new Category(name, getColor());
			Category[] b = getSubCategories();
			for(int i = 0; i<b.length; i++){
				if(b[i].getName()==name){throw new Exception("Name bereits vorhanden");}}
				subcat.add(a);
				return a;
			}
		
		return null;
	}

	/**
	 * @return Array aller untergeordneten Kategorien (ggf. Array der Länge 0).
	 */
	public Category[] getSubCategories() {
		Category [] a = subcat.toArray(new Category[subcat.size()]);
		return a;
	}

	/**
	 * @return Beginn der Lebenszeit dieser Kategorie (in Millisekunden seit
	 *         1.1.1970). Der Startzeitpunkt dieser Kategorie ergibt sich aus
	 *         dem frühesten Startzeitpunktes aller Aufgaben in dieser
	 *         Kategorie. Gibt es keine Ereignisse, liefert die Methode 0.
	 */
	public long getStartTime() {
		Task[] a = task.toArray(new Task[task.size()]);
		long s = Long.MAX_VALUE;
		for(int i = 0; i<a.length; i++){
			if(a[i].getStartTime()<s){s=a[i].getStartTime();}
		}
		return s;
	}

	/**
	 * @return Ende der Lebenszeit dieser Kategorie (in Millisekunden seit
	 *         1.1.1970). Der Endzeitpunkt dieser Kategorie ergibt sich aus dem
	 *         spätesten Endzeitpunktes aller Aufgaben in dieser Kategorie. Gibt
	 *         es keine Ereignisse, liefert die Methode 0.
	 */
	public long getEndTime() {
		Task[] a = task.toArray(new Task[task.size()]);
		long e = Long.MIN_VALUE;
		for(int i = 0; i<a.length; i++){
			if(a[i].getEndTime()>e){e=a[i].getEndTime();}
		}
		return e;
	}
	/**
	 * Liefert ein Array der Tasks dieser Kategorie
	 * 
	 * @return Array aller Tasks
	 *
	 */
	
	public Task [] getTasks(){
		Task[] a = task.toArray(new Task[task.size()]);
		return a;
	}

}