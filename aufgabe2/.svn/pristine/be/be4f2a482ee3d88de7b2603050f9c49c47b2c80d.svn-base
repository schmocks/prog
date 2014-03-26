package tplan.app;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasse für einen Zeitplaner, der Aufgaben und die daraus entstehenden
 * Ereignisse verwaltet.
 *
 *@author Stephan
 */

public class TimePlanner {
	
	ArrayList<Category> maincat = new ArrayList<Category>();

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
	 */
	public Category addMainCategory(String name, Color color) {
		Category a = new Category(name, color);
		Category [] b = maincat.toArray(new Category[maincat.size()]);
		for(int i = 0; i < b.length; i++){
			if(b[i].getName()==name){return null;}
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
	 */
	public boolean addTask(Category cat, String name, long start, long dauer) {
		if((cat.getSubCategories()).length==0){
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
	 * Durchsucht eine Kategorie nach Tasks, falls Unterkategorien vorhanden werden
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
	
	// ////////////////////////////////////////////////////////////
	// /////// ERST AB AUFGABE 2 //////////////////////////////////
	// ////////////////////////////////////////////////////////////

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
	 */
	public boolean addTask(Category cat, String name, long start) {
		if((cat.getSubCategories()).length==0){
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
	 */
	public boolean addPeriodicTask(Category cat, String name, long start,
			long duration, long delta, long end) {
		if((cat.getSubCategories()).length==0){
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
	 */
	public boolean addYearlyTask(Category cat, String name, long start, long end) {
		if((cat.getSubCategories()).length==0){
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
	 */
	public boolean addFirstWeekdayTask(Category cat, String name, long start, long end) {
		if(cat.getSubCategories().length==0){
			cat.task.add(new FirstWeekdayTask(cat, name, start, end));
		}
		return false;
	}

	// ////////////////////////////////////////////////////////////
	// /////// ERST AB AUFGABE 3 //////////////////////////////////
	// ////////////////////////////////////////////////////////////

	
	
	/** Laden des Zeitplans. 
	 * 
	 * @throws IOException ex
	 * @param filename name
	 * */
	public void load(String filename) throws IOException {
	}

	/** Speichern des Zeitplans. 
	 * 
	 * @throws IOException ex
	 * @param filename name
	 * */
	public void save(String filename) throws IOException {
	}
	

}
