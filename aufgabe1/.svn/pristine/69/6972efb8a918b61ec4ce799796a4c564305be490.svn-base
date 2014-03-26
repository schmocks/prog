package tplan.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.Calendar;

import org.junit.Test;

import tplan.app.*;

public class TestAufgabe2 {

	public static final long ONEHOUR = 60*60*1000;
	public static final long ONEDAY = 24*ONEHOUR;
	public static final long ONEYEAR = 365*ONEDAY;
		
//	// bisher
//	@Test
//	public void testKategorieDauer() {  
//		TimePlanner tp = new TimePlanner();
//		Category a = tp.addMainCategory("Alles", Color.RED);
//		Category p = a.addSubCategory("Programmieren");
//		Category s = a.addSubCategory("Sport");
//		assertTrue(tp.addTask(p, "AL", getMillis(2013, 03, 20, 8, 30), ONEDAY));
//		assertTrue(tp.addPeriodicTask(p, "A", getMillis(2013, 03, 1, 9, 0), 4*ONEHOUR, ONEDAY, getMillis(2013,03,29,0,0)));
//		assertTrue(tp.addYearlyTask(s, "Geburtstag", getMillis(2000, 03, 10, 0, 0), getMillis(2040, 12, 31, 0, 0)));
//
//		assertEquals(p.getStartTime(), getMillis(2013, 03, 1, 9, 0) );
//		assertEquals(p.getEndTime(), getMillis(2013, 03, 28, 9, 0)+4*ONEHOUR );
//		assertEquals(s.getStartTime(), getMillis(2000, 03, 10, 0, 0) );
//		assertEquals(s.getEndTime(), getMillis(2040, 03, 11, 0, 0)-1 );
//	}
	
	// besprochen: Start/Endzeiten aus Start/Endzeiten der Aufgaben
	@Test
	public void testKategorieDauer() throws Exception {  
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Alles", Color.RED);
		Category p = a.addSubCategory("Programmieren");
		Category s = a.addSubCategory("Sport");
		assertTrue(tp.addTask(p, "AL", getMillis(2013,03,02, 8,30), ONEDAY));
		assertTrue(tp.addPeriodicTask(p, "A", getMillis(2013,03,02, 9,00), 4*ONEHOUR, ONEDAY, getMillis(2013,03,29, 0,0)));
		assertTrue(tp.addYearlyTask(s, "Geburtstag", getMillis(2000,03,10, 0, 0), getMillis(2040,12,31, 0,0)));

		System.out.println(tp);
		
		assertEquals(p.getStartTime(), getMillis(2013,03,02, 8,30) );
		assertEquals(p.getEndTime(), getMillis(2013,03,29, 0,0) );
		assertEquals(s.getStartTime(), getMillis(2000,03,10, 0,0) );
		assertEquals(s.getEndTime(), getMillis(2040,12,31, 0,0) );
	}
	
	@Test
	public void testGetEvents() throws Exception {  
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Alles", Color.RED);
		Category p = a.addSubCategory("Programmieren");
		Category s = a.addSubCategory("Sport");
		assertTrue(tp.addTask(p, "AL", getMillis(2013, 03, 20, 8, 30), ONEDAY));
		assertTrue(tp.addPeriodicTask(p, "A", getMillis(2013, 03, 1, 9, 0), 4*ONEHOUR, ONEDAY, getMillis(2013,03,29,0,0)));
		assertTrue(tp.addYearlyTask(s, "Geburtstag", getMillis(2000, 03, 10, 0, 0), getMillis(2040, 12, 31, 0, 0)));
		
		Event[] events = tp.getEvents( getMillis(2010, 01, 1, 0, 0)-ONEHOUR, 5*ONEYEAR);
		assertEquals(1+28+5,events.length);
		for (int i=0;i<events.length;++i)
			for (int j=i+1; j<events.length;++j)
				assertTrue( events[i]!=events[j] );

		events = tp.getEvents( getMillis(2011, 03, 10, 11, 00) );
		assertEquals(1,events.length);
		assertEquals("Geburtstag",events[0].getName());

	}
	
	@Test
	public void testStartEndEvent() throws Exception {
		//    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4
		// A  |---------------------------|
		// B          |-------------------|
		// C  |-------------|
		// D  |-|
		// E                      |-|
		// ?              *                   1. abgefragter Zeitpunkt
		// ?       |------------|             2. abgefragter Zeitraum
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Alles", Color.RED);
		assertTrue(tp.addTask(a, "A", getMillis(2013, 03, 01, 0, 0), 14*ONEHOUR));
		assertTrue(tp.addTask(a, "B", getMillis(2013, 03, 01, 4, 0), 10*ONEHOUR));
		assertTrue(tp.addTask(a, "C", getMillis(2013, 03, 01, 0, 0), 07*ONEHOUR));
		assertTrue(tp.addTask(a, "D", getMillis(2013, 03, 01, 0, 0), 01*ONEHOUR));
		assertTrue(tp.addTask(a, "E", getMillis(2013, 03, 01,10, 0), 01*ONEHOUR));

		Event[] events = tp.getEvents(getMillis(2013, 03, 01, 6, 0) );  //Geändert
		assertEquals(3,events.length);

		events = tp.getEvents(getMillis(2013, 03, 01, 2,30), 6*ONEHOUR+ONEHOUR/2 );
		assertEquals(3,events.length);
	}
	
	public static long getMillis(int year,int month,int day, int hour, int min) {
		final Calendar cal = Calendar.getInstance();		
		cal.clear();
	    cal.set(year, month - 1, day, hour, min, 0);
	    return cal.getTimeInMillis();		
	}
	
}
