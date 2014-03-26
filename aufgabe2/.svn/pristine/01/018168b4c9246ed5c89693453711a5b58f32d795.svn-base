package tplan.app;

import java.util.ArrayList;

/**
 * Klasse für einen periodischen Task.
 *
 *
 *@author Stephan
 *
 */

public class PeriodicTask extends Task{
	
	
	Category cat;
	String tname;
	long tstartTime;
	long duration;
	long delta;
	long end;
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
	 * @param end endzeitpunkt
	 * @param delta Zeitspannen zwischen einzelnen Ereignissen
	 * @param duration dauer eines Ereignisses
	 */
	
	PeriodicTask(Category cat, String name, long startTime, long duration, long delta, long end){
		super(cat, name, startTime, end-startTime);
		long endtime;
		this.cat=cat; this.tname=name; this.tstartTime=startTime; this.duration=duration;
		this.delta=delta; this.end=end;
		while(startTime<end){
			endtime=startTime+duration;
			event.add(new Event(cat, name, startTime, endtime));
			startTime+=delta;
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
		return end;
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
