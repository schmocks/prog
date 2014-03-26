package tplan.app;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author M.Gründel
 */
public class DateTime {
	
	/** Instanz der Calendar-Klasse.   */
	private static Calendar cal = Calendar.getInstance();
	
	/** Eine Sekunde in Millisekunden. */
	public static final long ONESECOND = 1000;
	
	/** Eine Minute in Millisekunden.  */
	public static final long ONEMINUTE = 60 * ONESECOND;
	
	/** Eine Stunde in Millisekunden.  */
	public static final long ONEHOURE  = 60 * ONEMINUTE;
	
	/** Ein Tag in Millisekunden.      */
	public static final long ONEDAY    = 24 * ONEHOURE;	
	
	/**
	 * Setzt die Zeitmarke auf den Tagsanfang.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return Zeitpunkt zu Beginn des Tages: long.
	 */
	public static long beginOfDay(long millis) {
		cal.clear();
		cal.setTimeInMillis(millis);		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		return cal.getTimeInMillis();
	}
	
	/**
	 * Setzt die Zeitmarke auf den Tagsende.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return Zeitpunkt zum Ende des Tages: long.
	 */
	public static long endOfDay(long millis) {
		cal.clear();
		cal.setTimeInMillis(millis);		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
		cal.set(Calendar.MILLISECOND, 
				cal.get(Calendar.MILLISECOND) - 1);
		return cal.getTimeInMillis();
	}
	
	/**
	 * Setzt die Zeitmarke auf den nächten Tag zur gleichen
	 * Uhrzeit.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return nächten Tag zur gleichen Uhrzeit: long.
	 */
	public static long nextDay(long millis) {
		cal.clear();
		cal.setTimeInMillis(millis);				
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);		
		return cal.getTimeInMillis();
	}
	
	/**
	 * Setzt die Zeitmarke auf den nächsten Monat am gleichen Tag
	 * und zur gleichen Uhrzeit.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return Zeitmarke auf den nächsten Monat am gleichen Tag,
	 * und Uhrzeit: long.
	 */
	public static long nextMonth(long millis) {
		cal.clear();
		cal.setTimeInMillis(millis);				
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);		
		return cal.getTimeInMillis();
	}
	
	/**
	 * Setzt die Zeitmarke auf das nächste Jahr zum gleichen Tag
	 * und Monat und zur gleichen Uhrzeit.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return Zeitmarke auf das nächste Jahr zum gleichen Tag,
	 * Monat und Uhrzeit: long.
	 */
	public static long nextYear(long millis) {
		cal.clear();
		cal.setTimeInMillis(millis);				
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);		
		return cal.getTimeInMillis();
	}
	
	/**
	 * Liefert den Wochentag zurück.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return den Wochentag: int.
	 */
	public static int getWeekDay(long millis) {
		cal.clear();
		cal.setTimeInMillis(millis);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
		
	/**
	 * Liefert den Tag zurück.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return der Tag im Monat: int.
	 */
	public static int getDay(long millis) {
		cal.clear();
		cal.setTimeInMillis(millis);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Liefert den Monat zurück.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return den Monat: int.
	 */
	public static int getMonth(long millis) {
		cal.clear();
		cal.setTimeInMillis(millis);
		return cal.get(Calendar.MONTH);
	}
	
	/**
	 * Liefert das Jahr zurück.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return das Jahr: int.
	 */
	public static int getYear(long millis) {
		cal.clear();
		cal.setTimeInMillis(millis);
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * Setzt das Jahr auf den angegebenen Wert.
	 * @param millis - Zeit in Millisekunden: long.
	 * @param year - das Jahr: int.
	 * @return Datum in Millisekunden mit dem setzten Jahr: long.
	 */
	public static long setYear(long millis, int year) {
		cal.clear();
		cal.setTimeInMillis(millis);
		cal.set(Calendar.YEAR, year);
		return cal.getTimeInMillis();
	}
	
	/**
	 * Liefert den ersten Tag im Monat des angegebenen Wochentags
	 * zurück.
	 * @param millis - Zeit in Millisekunden: long.
	 * @param weekDay - der Wochentag (Sonntag=0, Montag=1, usw.): int.
	 * @return ersten Tag im Monat des angegebenen Wochentags: long.
	 */
	public static long firstMonthDay(long millis, int weekDay) {
		cal.clear();
		cal.setTimeInMillis(millis);					
		cal.set(Calendar.DATE, 1);	
		cal.getTimeInMillis();
		cal.set(Calendar.DAY_OF_WEEK, weekDay);
		if (cal.get(Calendar.DATE) > 7) {
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 7);
		}
		return cal.getTimeInMillis();
	}
	
	/**
	 * Liefert Datum und Zeit als String zurück.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return Datum und Zeit als String: String.
	 */
	public static String dateTime(long millis) {
		cal.setTimeInMillis(millis);
		return String.format("%tF %tR", cal, cal);
	}
	
	/**
	 * Liefert das Datum String zurück.
	 * @param millis - Zeit in Millisekunden: long.
	 * @return Datum als String: String.
	 */
	public static String dateOnly(long millis) {
		cal.setTimeInMillis(millis);
		return String.format("%tF", cal);
	}
	
	/**
	 * Wandelt eine Zeitangabe (Millisekunden) in Tage, Stunden
	 * Minuten und Sekunden um. 
	 * @param millis - Zeit in Millisekunden: long.
	 * @return Zeitangabe in Tage, Stunden und Minuten
	 * (Format: DDD/HH:MM:SS) 
	 */
	public static String dhms(long millis) {
		long d = millis / ONEDAY;
		millis %= ONEDAY;
		long h = millis / ONEHOURE;
		millis %= ONEHOURE;
		long m = millis / ONEMINUTE;
		millis %= ONEMINUTE;
		long s = millis / ONESECOND;
		return String.format("%03d/%02d:%02d:%02d", d, h, m, s);
	}
	
	/**
	 * Liefert den Wochentag als String, der am angegeben Zeitpunkt vorliegt.
	 * @param millis Zeit in Millisekunden
	 * @return Wochentag-Name
	 */
	public static String theWeekday(long millis) {
		cal.clear();
		cal.setTimeInMillis(millis);
		return cal.getDisplayName(Calendar.DAY_OF_WEEK, 0, 
				Locale.GERMAN);
	}
	
	/**
	 * Wandelt die Angabe von Jahr, Monat und Tag im Millisekunden
	 * um.
	 * @param year - das Jahr: int.
	 * @param month - der Monath: int.
	 * @param day - der Tag: int.
	 * @return Jahr, Monat und Tag im Millisekunden: long.
	 */
	public static long toMillis(int year, int month, int day) {
		cal.clear();			
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);		
		return cal.getTimeInMillis(); 
	}
	
	/**
	 * Beispiel für die Nutzung von DateTime und der
	 * Calendar-Klasse.
	 * @param args - keine Parameter benötigt.
	 */
	public static void main(String[] args) {
		final int year = 2013;
		final int day = 24;
		final int hour = 12;
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(year, Calendar.MARCH, day, hour, 0, 0);		
		long millis = c.getTimeInMillis();
		System.out.println(theWeekday(millis) + " " + dateTime(millis));		
		millis = firstMonthDay(millis, Calendar.MONDAY);
		System.out.println(theWeekday(millis) + " " + dateTime(millis));
	
	}

}
