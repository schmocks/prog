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
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		this.cat=cat; this.tname=name; this.tstartTime=startTime; this.duration=duration;
		this.delta=delta; this.end=end;
		write();
		
	}
		
	
	/**
	 * Schreibt/Aktualisiert die Events
	 * 
	 * 
	 */
	
	private void write(){
		event.clear();
		long endtime;
		long startTime=tstartTime;
		while(startTime+duration<end){
			endtime=startTime+duration;
			event.add(new Event(cat, tname, startTime, endtime));
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
	 * Setzt den Namen
	 * @param name des Tasks
	 */
	public void setName(String name){
		this.tname=name;
		write();
	}

	/**
	 * @return Kategorie, zu der dieser Task (unmittelbar) gehört
	 */
	public Category getCategory() {
		return cat;
	}

	/**
	 * Kategorie setzen	
	 * @param ncat Kategorie
	 */
	public void setCategory(Category ncat){
		this.cat=ncat;
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
	 * 
	 * @return Zeitspanne zwischen 2 Ereignissen
	 * 
	 */
	public long getDelta(){
		return delta;
	}
	
	/**
	 * 
	 * 
	 * @return Dauer eines Ereignis
	 */
	public long getDuration(){
		return duration;
	}
	

	/**
	 * Setzt Startzeit
	 * 
	 * @param tstartTime Startzeit
	 */


	public void setTstartTime(long tstartTime) {
		this.tstartTime = tstartTime;
		write();
	}

	/**
	 * Setzt dauer eines Ereignisses
	 * 
	 * @param duration Dauer
	 */



	public void setDuration(long duration) {
		this.duration = duration;
		write();
	}


/**
 * setzt Abstand zwischen 2 Ereignissen
 * 
 * @param delta Abstand
 */


	public void setDelta(long delta) {
		this.delta = delta;
		write();
	}


/**
 * Setzt Endzeit
 * 
 * @param end Endzeit
 */


	public void setEnd(long end) {
		this.end = end;
		write();
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
