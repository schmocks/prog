package tplan.app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasse für einen einzelnen Termin mit fester Start- und Endzeit.
 *
 *
 *@author Stephan
 *
 */

public class Task implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Category cat;
	String tname;
	long tstartTime;
	long tendTime;
	ArrayList <Event> event = new ArrayList<Event>();

	
	/**
	 * to String
	 * 
	 * @return info
	 */
	
	public String toString(){
		Event [] a = getEvents();
		String events = "";
		for(int i = 0; i<a.length;i++){
			events+=a[i];			
		}
		return " +- Task: " + tname + "\n" + events;
	}
	
	
	
	
	
	/**
	 * Konstruktor
	 * 
	 * @param cat Kategorie 
	 * @param name Name	
	 * @param startTime Start
	 * @param duration Dauer
	 */
	
	Task(Category cat, String name, long startTime, long duration){
		long endTime=startTime+duration;
		this.cat=cat; this.tname=name; this.tstartTime=startTime; this.tendTime=endTime;
		event.add(new Event(cat, name, startTime, endTime));
		
	}
	
	


	/**
	 * @return Name des Ereignisses
	 */
	
	public String getName() {
		return tname;
	}

	/**
	 * @return Kategorie, zu der dieser Task (unmittelbar) gehört
	 */
	public Category getCategory() {
		return cat;
	}
/**
 * Kategorie setzen
 * @param cat Kategorie
 */
	public void setCat(Category cat) {
		this.cat = cat;
	}

/**
 * Name setzen
 * @param tname Name
 */



	public void setTname(String tname) {
		this.tname = tname;
	}

/**
 * Startzeit setzt
 * @param tstartTime Startzeit
 */



	public void setTstartTime(long tstartTime) {
		this.tstartTime = tstartTime;
	}

/**
 * Endzeit setzen
 * @param tendTime Endzeit
 */



	public void setTendTime(long tendTime) {
		this.tendTime = tendTime;
	}





	/**
	 * @return Startzeitpunkt des Tasks (in Millisekunden seit 1.1.1970)
	 */
	public long getStartTime() {
		return tstartTime;
	}

	/**
	 * @return Endzeitpunkt des Tasks (in Millisekunden seit 1.1.1970)
	 */
	public long getEndTime() {
		return tendTime;
	}
	
	/**
	 * Liefert ein Array aller Events dieses Tasks
	 * 
	 * @return Array 
	 */
	
	public Event [] getEvents(){
		Event [] a = event.toArray(new Event[event.size()]);
		return a;
	}

}
