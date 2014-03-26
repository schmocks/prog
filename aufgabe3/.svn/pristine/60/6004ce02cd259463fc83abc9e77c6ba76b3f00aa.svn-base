package tplan.app;

import java.util.ArrayList;

/**
 * Klasse für einen sich jährlich wiederholenden Ganztägigen Task
 * zB Geburtstag.
 *
 *
 *@author Stephan
 *
 */

public class YearlyTask extends Task{
	
	
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
	 * @param end end
	 */
	
	YearlyTask(Category cat, String name, long startTime, long end){
		super(cat, name, startTime, end);
		this.cat=cat; this.tname=name; this.tstartTime=startTime; this.tendTime=end;
		while(startTime<end){
		event.add(new Event(cat, name, DateTime.beginOfDay(startTime), 
				DateTime.endOfDay(startTime)));
		startTime=DateTime.nextYear(startTime);
		}
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
