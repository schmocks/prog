package tplan.app;

/**
 * Klasse für einen einzelnen Termin mit fester Start- und Endzeit.
 *
 *
 *@author Stephan
 *
 */

public class Event {
	
	
	Category cat;
	String ename;
	long estartTime;
	long eendTime;
	
/**
 * to String
 * 
 * @return Info
 * 
 */
	
	public String toString(){
		return "  +- Event: "+ename + " " + DateTime.dateTime(estartTime) 
				+ " bis " +DateTime.dateTime(eendTime) +"\n";
	}
	/**
	 * Konstruktor
	 * 
	 * @param cat Uebergeordnete Kategorie
	 * @param name Name des Events
	 * @param startTime Startzeit
	 * @param endTime Endzeit
	 *
	 */
	
	Event(Category cat, String name, long startTime, long endTime){
		this.cat=cat; this.ename=name; this.estartTime=startTime; this.eendTime=endTime;
	}
	
	
	/**
	 * @return Name des Ereignisses
	 */
	
	public String getName() {
		return ename;
	}

	/**
	 * @return Kategorie, zu der dieses Ereignis (unmittelbar) gehört
	 */
	public Category getCategory() {
		return cat;
	}

	/**
	 * @return Startzeitpunkt des Ereignisses (in Millisekunden seit 1.1.1970)
	 */
	public long getStartTime() {
		return estartTime;
	}

	/**
	 * @return Endzeitpunkt des Ereignisses (in Millisekunden seit 1.1.1970)
	 */
	public long getEndTime() {
		return eendTime;
	}

}
