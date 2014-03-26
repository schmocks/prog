package tplan.app;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;



/**
 * Klasse für einen Zeitplaner, der Aufgaben und die daraus entstehenden
 * Ereignisse verwaltet.
 *
 *@author Stephan
 */

public class TimePlanner extends Observable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8107891757024015685L;
	ArrayList<Category> maincat = new ArrayList<Category>();
	private long zeit;
	private boolean knut = false;

	/**
	 * 
	 * toString Methode
	 * 
	 * @return Komplette Auflistung des Inhalts des Timeplanners
	 * 
	 */
	
	public String toString(){
		String main = "";
		if(getMainCategories().length==0){return "empty";}
		Category[] a = getMainCategories();
		for(int i = 0; i<a.length; i++){
			main+=a[i].getName()+"\n";
			for(int j = 0; j<a[i].getSubCategories().length;j++){
			main+=a[i].getSubCategories()[j];
			}
		}
		return main;
		
	}
	
	
	/**
	 * Prüft einen Zeitraum auf vorliegende Aufgaben / Ereignisse.
	 * 
	 * @param timepoint
	 *            Anfangszeitpunkt (in Millisekunden seit 1.1.1970)
	 * @param duration
	 *            Zeitdauer (in Millisekunden)
	 * @return true, wenn im angegebenen Zeitraum kein Ereignis liegt.
	 */
	public boolean isFree(long timepoint, long duration) {
		if(getEvents(timepoint, duration).length==0){return true;}
		return false;
	}

	/**
	 * @return Array aller Hauptkategorien (also solche, die nicht in einer
	 *         anderen Kategorie zusammengefasst sind). Liefert ggf. Array der
	 *         Länge 0.
	 */
	public Category[] getMainCategories() {
		Category [] a = maincat.toArray(new Category[maincat.size()]);
		return a;
	}

	/**
	 * Fügt eine neue Kategorie hinzu.
	 * 
	 * @param name
	 *            Name der Kategorie
	 * @param color
	 *            Farbe für diese Kategorie (und alle Unterkategorien)
	 * @return neue Kategorie, wenn der Name noch nicht existiert, null sonst
	 * @throws Exception 
	 */
	public Category addMainCategory(String name, Color color) throws Exception{
		Category a = new Category(name, color);
		Category [] b = maincat.toArray(new Category[maincat.size()]);
		for(int i = 0; i < b.length; i++){
			if(b[i].getName()==name){throw new Exception("Name bereits vorhanden");}
			}
		maincat.add(a);		
		return a;
	}

	/**
	 * Liefert alle Ereignisse, die zum gegebenen Zeitpunkt aktiv sind (also der
	 * vorgegebene Zeitpunkt liegt zwischen Anfangs- und Endzeitpunkt des
	 * Ereignisses).
	 * 
	 * @param timepoint
	 *            Zeitpunkt (in Millisekunden seit 1.1.1970)
	 * @return Array mit einem Eintrag für jedes einzelne Ereignis zu diesem
	 *         Zeitpunkt. Liefert ggf. ein Array der Länge 0.
	 */
	public Event[] getEvents(long timepoint) {
		ArrayList<Event> active = new ArrayList<Event>();
		Category[] mc = getMainCategories();
		for(int mci = 0; mci<mc.length;mci++){
				Task[] sct = searchTasks(mc[mci]);
					// Suchschleife Tasks
					for(int scti = 0; scti<sct.length; scti++){
						// Events des Tasks werden geholt
						Event[] sce = sct[scti].getEvents();
						// Events des gefragten Zeitraums werden gesucht
						for(int scei = 0; scei<sce.length; scei++){
							if(sce[scei].getStartTime()<=timepoint&&
							   sce[scei].getEndTime()>=timepoint){
								// Zutreffende Events werden in die Liste geschrieben
								active.add(sce[scei]);
							}
						}
					}
				}
		
		return active.toArray(new Event[active.size()]);
		
	}

	/**
	 * Liefert alle Ereignisse, die in einem gegebenem Zeitraum aktiv sind (also
	 * das vorgegebene Zeitintervall mit dem Zeitintervall des Ereignisses einen
	 * nicht-leeren Schnitt hat).
	 * 
	 * @param timepoint
	 *            Zeitpunkt (in Millisekunden seit 1.1.1970)
	 * @param duration
	 *            Dauer (in Millisekunden)
	 * @return Array mit einem Eintrag für jedes einzelne Ereignisinnerhalb des
	 *         angegebenen Zeitraums. Liefert ggf. ein Array der Länge 0.
	 */
	public Event[] getEvents(long timepoint, long duration) {
		ArrayList<Event> active = new ArrayList<Event>();
		Category[] mc = getMainCategories();
		long end = timepoint + duration;
		for(int mci = 0; mci<mc.length;mci++){
			Task[] sct = searchTasks(mc[mci]);
				// Suchschleife Tasks
				for(int scti = 0; scti<sct.length; scti++){
				// Events des Tasks werden geholt
					Event[] sce = sct[scti].getEvents();
					// Events des gefragten Zeitraums werden gesucht
					for(int scei = 0; scei<sce.length; scei++){
						if((sce[scei].getStartTime()>=timepoint&&
						   sce[scei].getEndTime()<=end)||
						   (sce[scei].getStartTime()<=timepoint&&
						   sce[scei].getEndTime()>=timepoint)||
						   (sce[scei].getStartTime()<=end&&
						   sce[scei].getEndTime()>=end)){active.add(sce[scei]);
						   }
						}
					}
				}
				
		return active.toArray(new Event[active.size()]);
		}

	/**
	 * Fügt eine neue Aufgabe hinzu.
	 * 
	 * @param cat
	 *            Kategorie, der die Aufgabe hinzugefügt werden soll.
	 * @param name
	 *            Bezeichnung der Aufgabe
	 * @param start
	 *            Startzeitpunkt der Aufgabe (in Millisekunden seit 1.1.1970).
	 * @param dauer
	 *            Dauer der Aufgabe (in Millisekunden)
	 * @return true, wenn Anlegen der Aufgabe erfolgreich war; false, wenn die
	 *         Kategorie schon Unterkategorien enthält und daher keine Aufgaben
	 *         aufnehmen darf.
	 * @throws Exception 
	 */
	public boolean addTask(Category cat, String name, long start, long dauer) throws Exception{
		if((cat.getSubCategories()).length==0){
			checkName(cat, name);
			cat.task.add(new Task(cat, name, start, dauer));
			return true;
		}
		return false;
	}

	/**
	 * Entfernt eine Aufgabe mit der angegebenen Bezeichnung.
	 * 
	 * @param cat
	 *            Kategorie, aus der die Aufgabe entfernt werden soll.
	 * @param name
	 *            Bezeichnung der Aufgabe
	 * @return true, wenn Aufgabe gelöscht werden konnte (false wenn keine
	 *         Aufgabe mit diesem Namen gefunden wurde)
	 */
	public boolean removeTask(Category cat, String name) {
		Task [] a = cat.task.toArray(new Task[cat.task.size()]);
		ArrayList<Task> remove = new ArrayList<Task>();
		for(int i = 0; i<a.length; i++){
			if(!a[i].getName().equals(name)){remove.add(a[i]);}
		}
		if(cat.task.equals(remove)){return false;}
		cat.task=remove;
		return true;
	}

	/**
	 * 
	 * Durchsucht eine Kategorie nach Tasks, falls Unterkategorien vorhanden sind werden
	 * diese durchsucht. Rekursiv
	 * 
	 * @param cat Name der Kategory die durchsucht werden soll
	 * 
	 * @return Array aller untergeordneten Tasks dieser Kategorie
	 */
	public static Task[] searchTasks(Category cat){
		if(cat.getSubCategories().length==0){
			return cat.getTasks();
				}
		else{
			Category[] a = cat.getSubCategories();
			ArrayList<Task> tasks = new ArrayList<Task>();
			for(int i = 0; i<a.length; i++){
				for(int j = 0; j<searchTasks(a[i]).length; j++){
					tasks.add((searchTasks(a[i]))[j]);
				}
			}
			return tasks.toArray(new Task[tasks.size()]);
		}
				
	}

	/**
	 * 
	
	 * Vergleicht den Namen mit den Namen der Tasks dieser Kategorie
	 * 
	 * @param cat Name der Kategorie
	 * @param name Name des neuen Tasks
	 * @throws Exception falls Name schon vorhanden
	 */
	public void checkName(Category cat, String name) throws Exception{
		Task[] a = searchTasks(cat);
		for(int t = 0; t<a.length; t++){
			if(a[t].getName()==name){throw new Exception("Name bereits vorhanden");}
		}
	}

	/**

	 * Fügt einer Kategorie eine neue Ganztages-Aufgabe hinzu.
	 * 
	 * @param cat
	 *            Kategorie, der die Aufgabe hinzugefügt werden soll.
	 * @param name
	 *            Bezeichnung der Aufgabe
	 * @param start
	 *            ein Zeitpunkt in Millisekunden seit 1.1.1970; die Aufgabe
	 *            erstreckt sich dann über den ganzen Tag, der diese
	 *            Millisekunde enthält
	 * @return true, wenn Anlegen der Aufgabe erfolgreich war; false, wenn die
	 *         Kategorie schon Unterkategorien enthält und daher keine Aufgaben
	 *         aufnehmen darf.
	 * @throws Exception 
	 */
	public boolean addTask(Category cat, String name, long start) throws Exception {
		if((cat.getSubCategories()).length==0){
			checkName(cat, name);
			long duration = DateTime.endOfDay(start)-DateTime.beginOfDay(start);
			cat.task.add(new Task(cat, name, DateTime.beginOfDay(start), duration));
			return true;
		}
		return false;
	}

	/**
	 * Fügt einer Kategorie eine Serien-Aufgabe hinzu.
	 * 
	 * @param cat
	 *            Kategorie, der die Aufgabe hinzugefügt werden soll.
	 * @param name
	 *            Bezeichnung des Aufgabe
	 * @param start
	 *            Startzeitpunkt der Aufgabe (in Millisekunden seit 1.1.1970)
	 * @param duration
	 *            Dauer der Aufgabe (in Millisekunden)
	 * @param delta
	 *            Abstand zwischen zwei Ereignissen zu dieser Aufgabe (in
	 *            Millisekunden). Eine Aufgabe, die sich jeden Tag wiederholt,
	 *            bekommt hier also den Wert 24*60*60*1000.
	 * @param end
	 *            Zeitpunkt, ab dem keine Wiederholungen mehr stattfinden (in
	 *            Millisekunden seit 1.1.1970)
	 * @return true, wenn Anlegen der Aufgabe erfolgreich war; false, wenn die
	 *         Kategorie schon Unterkategorien enthält und daher keine Aufgaben
	 *         aufnehmen darf.
	 * @throws Exception 
	 */
	public boolean addPeriodicTask(Category cat, String name, long start,
			long duration, long delta, long end) throws Exception {
		if((cat.getSubCategories()).length==0){
			checkName(cat, name);
			cat.task.add(new PeriodicTask(cat, name, start, duration, delta, end));
			return true;
		}
		return false;
	}

	/**
	 * Fügt einer Kategorie eine neue Ganztages-Aufgabe hinzu, die sich jedes
	 * Jahr wiederholt.
	 * 
	 * @param cat
	 *            Kategorie, der die Aufgabe hinzugefügt werden soll.
	 * @param name
	 *            Bezeichnung der Aufgabe
	 * @param start
	 *            ein Zeitpunkt in Millisekunden seit 1.1.1970; die Aufgabe
	 *            erstreckt sich dann über den ganzen Tag, der diese
	 *            Millisekunde enthält (in jedem Jahr bis zum Endzeitpunkt)
	 * @param end
	 *            Zeitpunkt, ab dem keine Wiederholungen mehr stattfinden (in
	 *            Millisekunden seit 1.1.1970)
	 * @return true, wenn Anlegen der Aufgabe erfolgreich war; false, wenn die
	 *         Kategorie schon Unterkategorien enthält und daher keine Aufgaben
	 *         aufnehmen darf.
	 * @throws Exception 
	 */
	public boolean addYearlyTask(Category cat, String name, long start, long end) throws Exception {
		if((cat.getSubCategories()).length==0){
			checkName(cat, name);
			cat.task.add(new YearlyTask(cat, name, start, end));
			return true;
		}
		return false;
	}

	/**
	 * Fügt einer Kategorie eine Wochentags-Aufgabe hinzu, die sich jeden Monat
	 * am ersten Montag (oder Dienstag, etc.) wiederholt.
	 * 
	 * @param cat
	 *            Kategorie, der die Aufgabe hinzugefügt werden soll.
	 * @param name
	 *            Bezeichnung des Termins
	 * @param start
	 *            ein Zeitpunkt in Millisekunden seit 1.1.1970, der auf einen
	 *            der ersten sieben Tage des Monats fällt; die Aufgabe erstreckt
	 *            sich dann über den ganzen Tag, der diese Millisekunde enthält
	 *            (in jedem Jahr bis zum Endzeitpunkt). Aus diesem
	 *            Startzeitpunkt ergibt sich implizit auch der Wochentag.
	 * @param end
	 *            Zeitpunkt, ab dem keine Wiederholungen mehr stattfinden (in
	 *            Millisekunden seit 1.1.1970)
	 * @return true, wenn Anlegen der Aufgabe erfolgreich war; false, wenn die
	 *         Kategorie schon Unterkategorien enthält und daher keine Aufgaben
	 *         aufnehmen darf oder das Datum nicht an den ersten sieben Tagen
	 *         eines Monats lag.
	 * @throws Exception 
	 */
	public boolean addFirstWeekdayTask(Category cat, String name, long start, 
									long end) throws Exception {
		if(cat.getSubCategories().length==0){
			checkName(cat, name);
			cat.task.add(new FirstWeekdayTask(cat, name, start, end));
		}
		return false;
	}

	/** 
	 * Laden des Zeitplans. 
	 * 
	 * @param filename Name der Datei
	 * 
	 * @throws IOException Datei nicht gefunden
	 * */
	@SuppressWarnings("unchecked")
	public void load(String filename) throws IOException{
		try{ObjectInputStream i = new ObjectInputStream(new FileInputStream(filename));
			maincat = (ArrayList<Category>) i.readObject();
			i.close();
				}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Speichern des Zeitplans. 
	 * 
	 *  * @param filename Name der Datei
	 * 
	 * @throws IOException Datei nicht gefunden
	 * */
	public void save(String filename) throws IOException {
		ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(filename));
		s.writeObject(maincat);
		s.close();
	}
	
	/**
	 * eingestelltes Datum in Millisekunden umrechnen
	 * 
	 * @param t tag 
	 * @param m monat
	 * @param j jahr
	 */
	public void setDate(int t, int m, int j) {
		zeit=(DateTime.toMillis(j, m, t));
		
	}
	
	/**
	 * Datum in Stringform für Titelleiste der GUI
	 * 
	 * @return Datum oder Zeitraum als String
	 */
	public String getDate(){
		String t = String.valueOf(DateTime.getDay(zeit));
		String m = String.valueOf(DateTime.getMonth(zeit));
		String j = String.valueOf(DateTime.getYear(zeit));
		String x = t + "." + m + "." + j;
		if(!knut){
			return "am " + x;
			}
		else{
			long zeit2 = DateTime.nextWeek(zeit);
			String tt = String.valueOf(DateTime.getDay(zeit2));
			String mm = String.valueOf(DateTime.getMonth(zeit2));
			String jj = String.valueOf(DateTime.getYear(zeit2));
			String xx = tt + "." + mm + "." + jj;
			return "vom " + x + " bis " + xx;
		}
	}

	/**
	 * Merker für Tages/Wochenansicht setzen
	 * 	
	 */
	public void setK(){
		if(!knut){
			knut=true;
			setChanged();
			return;
			
		}
		else{
			knut=false;
			setChanged();
			return;
			}	
	}
	
	/**
	 * Liefert Merker Tages/Wochenansicht
	 * @return setk Merker
	 */
	public boolean getK(){
		return knut;
	}
	
	/**
	 * Liefert Events im gewünschtem Zeitraum
	 * 
	 * @return Events im Zeitraum
	 */
	public Event[] showEvents(){
		if(!getK()){long tag = DateTime.endOfDay(zeit)-DateTime.beginOfDay(zeit);
			Event[] a = getEvents(DateTime.beginOfDay(zeit), tag);
			return a;
			}
		else{
			long woche = DateTime.endOfDay(DateTime.nextWeek(zeit))-DateTime.beginOfDay(zeit);
			Event[] a = getEvents(DateTime.beginOfDay(zeit), woche);
			return a;
		}
	}
	
	
	/**
	 * Löscht den Task über ein Event
	 * 
	 * @param z index des zu löschenden Events
	 */
	public void deleteTask(int z){
		Event [] b = showEvents();
		removeTask(b[z].getCategory(), b[z].getName());
		setChanged();
		
	}
	
	
	
	/**
	 * Kontrolliert ob es sich um einen periodischen Task
	 * handeltz
	 * 
	 * @param i Zeilennummer in der Tabelle
	 * @return true wenn periodischer Task
	 * 
	 */
	public boolean checkTask(int i){
		Event [] b = showEvents();
		Task[] c = b[i].getCategory().getTasks();
		for( int j = 0; j<c.length; j++){
			if(b[i].getName()==c[j].getName()&&c[j] instanceof PeriodicTask){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Liefert zu einem Event den zugehörigen Task
	 * 
	 * @param i index des Events
	 * @return den Task 
	 */
	public Task getTask(int i){
		Event [] b = showEvents();
		Task[] c = b[i].getCategory().getTasks();
		for( int j = 0; j<c.length; j++){
			if(b[i].getName()==c[j].getName()){return (Task) c[j];}
			}
		return null;
		}
	
	/**
	 * 
	 * Prüft ob der Name der Kategorie schon vorhanden ist,
	 * wenn ja wir diese Kategorie zurückgeliefert falls nein
	 * wird eine Hauptkategorie sowie eine Unterkategorie mit diesem
	 * Namen erzeugt
	 * 
	 * @param name Name der Kategorie
	 * @return Kategorie mit dem Namen
	 * @throws Exception tritt nicht ein
	 */
	public Category checkCat(String name) throws Exception{
		Category[] a = getMainCategories();
		ArrayList<Category> list = new ArrayList<Category>();
		for(int ai = 0; ai < a.length; ai++){
			list.add(a[ai]);
			Task [] b = searchTasks(a[ai]);
			for(int bi = 0; bi<b.length; bi++){
				list.add(b[bi].getCategory());
			}
		}
		Category [] c = list.toArray(new Category[list.size()]);
		for(int ci = 0; ci<c.length; ci++){
			if(c[ci].getName().equals(name)){return c[ci];}
			}
		Category main = addMainCategory(name, Color.GREEN).addSubCategory(name);
		return main;
	}
	
	/**
	 * gibt die eingestellte Zeit zurück
	 * 
	 * @return Zeit 
	 * 
	 */
	public long getZeit(){
		return zeit;
	}
	
	
	/**
	 * Observer
	 * 
	 */
	
	
	public void save(){
		setChanged();
	}
	
}
