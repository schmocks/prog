package tplan.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.Test;

import tplan.app.*;


public class TestAufgabe1Z {

	public static final long ONEHOUR = 60*60*1000;
	public static final long ONEDAY = 24*ONEHOUR;
	
	// wie TestAufgabe1.testGetEvents, aber Sport-Ereigniss liegen noch eine Ebene tiefer
	@Test(timeout = 1000)
	public void testGetDeepEvents() throws Exception {  
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Alles", Color.RED);
		Category p = a.addSubCategory("Programmieren");
		Category s = a.addSubCategory("Sport");
		Category q = s.addSubCategory("Squash");
		assertTrue(tp.addTask(p, "Aufgabe 1", getMillis(2013, 03, 20, 8, 30), 2*ONEDAY));
		assertTrue(tp.addTask(p, "Aufgabe 2", getMillis(2013, 04, 20, 8, 30), ONEDAY));
		assertTrue(tp.addTask(q, "Squash 1", getMillis(2013, 03, 18, 8, 30), ONEHOUR));
		assertTrue(tp.addTask(q, "Squash 2", getMillis(2013, 03, 20, 10, 30), ONEHOUR));
		
		Event[] events = tp.getEvents( getMillis(2013, 03, 20, 8, 30)-ONEHOUR, 35*ONEDAY);
		//
		
		for(int i = 0; i<events.length; i++){
			System.out.println(events[i].getName());
		}
		assertEquals(3,events.length);
		assertNotNull(events[0]);
		assertNotNull(events[1]);
		assertNotNull(events[2]);
		String[] erwartet = { "Aufgabe 1", "Aufgabe 2", "Squash 2" };
		String[] erhalten = { events[0].getName(), events[1].getName(), events[2].getName() };
		Arrays.sort(erhalten); Arrays.sort(erwartet);
		for (int i=0;i<erwartet.length;++i) {
			assertEquals(erwartet[i], erhalten[i]);
		}

		events = tp.getEvents( getMillis(2013, 03, 20, 11, 00) );
		assertEquals(2,events.length);
		assertNotNull(events[0]);
		assertNotNull(events[1]);
		assertTrue( !events[0].getName().equals(events[1].getName()) );
		assertTrue( events[0].getName().equals("Aufgabe 1") || events[0].getName().equals("Squash 2") );
		assertTrue( events[1].getName().equals("Aufgabe 1") || events[1].getName().equals("Squash 2") );

	}

	// wie TestAufgabe1.testGetEvents, aber SportEreignisse haben eigene Hauptkategorie
	@Test(timeout = 1000)
	public void testGetShallowEvents() throws Exception {  
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Alles", Color.RED);
		Category p = a.addSubCategory("Programmieren");
		Category s = tp.addMainCategory("Sport", Color.BLUE);
		assertTrue(tp.addTask(p, "Aufgabe 1", getMillis(2013, 03, 20, 8, 30), 2*ONEDAY));
		assertTrue(tp.addTask(p, "Aufgabe 2", getMillis(2013, 04, 20, 8, 30), ONEDAY));
		assertTrue(tp.addTask(s, "Squash 1", getMillis(2013, 03, 18, 8, 30), ONEHOUR));
		assertTrue(tp.addTask(s, "Squash 2", getMillis(2013, 03, 20, 10, 30), ONEHOUR));
		
		Event[] events = tp.getEvents( getMillis(2013, 03, 20, 8, 30)-ONEHOUR, 35*ONEDAY);
		assertEquals(3,events.length);
		assertNotNull(events[0]);
		assertNotNull(events[1]);
		assertNotNull(events[2]);
		String[] erwartet = { "Aufgabe 1", "Aufgabe 2", "Squash 2" };
		String[] erhalten = { events[0].getName(), events[1].getName(), events[2].getName() };
		Arrays.sort(erhalten); Arrays.sort(erwartet);
		for (int i=0;i<erwartet.length;++i) {
			assertEquals(erwartet[i], erhalten[i]);
		}

		events = tp.getEvents( getMillis(2013, 03, 20, 11, 00) );
		assertEquals(2,events.length);
		assertNotNull(events[0]);
		assertNotNull(events[1]);
		assertTrue( !events[0].getName().equals(events[1].getName()) );
		assertTrue( events[0].getName().equals("Aufgabe 1") || events[0].getName().equals("Squash 2") );
		assertTrue( events[1].getName().equals("Aufgabe 1") || events[1].getName().equals("Squash 2") );

	}
	
	// prüft, ob nicht nur Start/Ende einer Kategorie für isFree herangezogen werden (sondern auf Ereignisse geprüft wird).
	@Test(timeout = 1000)
	public void testUeberlappung() throws Exception {
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Haupt", Color.RED);
		Category p = a.addSubCategory("Unter");
		assertTrue(tp.addTask(p, "Task 0", getMillis(2013, 1, 1, 0, 30), 15*ONEDAY));
		for (int i=2;i<11;++i) {
			assertTrue(tp.addTask(p, "Task "+i, getMillis(2013, 1, i, 8, 00), 3*ONEHOUR));
		}
		
		assertEquals(4,tp.getEvents(getMillis(2013, 1, 4, 7, 00), 3*ONEDAY).length); // drei komplett enthalten + 1
		assertEquals(5,tp.getEvents(getMillis(2013, 1, 4, 8, 30), 3*ONEDAY).length); // vier angeschnitten + 1
		assertEquals(3,tp.getEvents(getMillis(2013, 1, 1, 7, 00), 3*ONEDAY).length); // ersten zwei  + 1
		assertEquals(3,tp.getEvents(getMillis(2013, 1, 9, 7, 00), 3*ONEDAY).length); // letzten zwei + 1
	}

	// prüft, ob nicht nur Start/Ende einer Kategorie für isFree herangezogen werden (sondern auf Ereignisse geprüft wird).
	@Test(timeout = 1000)
	public void testIsFree() throws Exception {
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Party", Color.RED);
		Category p = a.addSubCategory("Sylvesterparty");
		assertTrue(tp.addTask(p, "Sylvester 2012", getMillis(2012, 12, 31, 20, 00), 6*ONEHOUR));
		assertTrue(tp.addTask(p, "Sylvester 2013", getMillis(2013, 12, 31, 20, 00), 6*ONEHOUR));
		assertTrue(tp.isFree(getMillis(2013, 6, 10, 20, 00), 6*ONEHOUR));
	}
	
	// prüft, ob removeTask auch bei zusammengesetzten Namen funktioniert
	@Test(timeout = 1000)
	public void testRemoveTask() throws Exception {
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Party", Color.RED);
		Category p = a.addSubCategory("Sylvesterparty");
		for (int i=2012;i<2014;++i) {
			assertTrue(tp.addTask(p, "Sylvester "+i, getMillis(i, 12, 31, 20, 00), 6*ONEHOUR));
		}
		Event[] e = tp.getEvents(getMillis(2013, 12, 31, 22, 00), 2*ONEHOUR);
		assertEquals(1, e.length);
		tp.removeTask(p,"Sylvester 2013");
		e = tp.getEvents(getMillis(2013, 12, 31, 22, 00), 2*ONEHOUR);
		assertEquals(0, e.length);
	}

	@Test(timeout = 1000)
	public void testToString() throws Exception {
		TimePlanner tp = new TimePlanner();
		assertTrue("TimePlanner besitzt keine toString-Methode", 
				tp.getClass().getMethod("toString").getDeclaringClass() != Object.class);
		assertTrue("TimePlanner.toString()liefert null", 
				tp.toString() != null);
		assertTrue("TimePlanner.toString().length() == 0", 
				tp.toString().length() != 0);
		//----------------------------------------------------
		Category cat = tp.addMainCategory("Alles", Color.RED);
		assertTrue("Category besitzt keine toString-Methode", 
				cat.getClass().getMethod("toString").getDeclaringClass() != Object.class);
		assertTrue("Category.toString()liefert null", 
				cat.toString() != null);
		assertTrue("Category.toString().length() == 0", 
				cat.toString().length() != 0);
		//----------------------------------------------------
		assertTrue(tp.addTask(cat, "Aufgabe 1", getMillis(2013, 03, 20, 8, 30), 2*ONEDAY));		
		Event[] events = tp.getEvents( getMillis(2013, 03, 20, 8, 30));
		assertEquals(1,events.length);
		assertNotNull(events[0]);	
		assertTrue("Event besitzt keine toString-Methode", 
				events[0].getClass().getMethod("toString").getDeclaringClass() != Object.class);
		assertTrue("Event.toString()liefert null", 
				events[0].toString() != null);
		assertTrue("Event.toString().length() == 0", 
				events[0].toString().length() != 0);
	}
	
	
	public static long getMillis(int year,int month,int day, int hour, int min) {
		final Calendar cal = Calendar.getInstance();		
		cal.clear();
	    cal.set(year, month - 1, day, hour, min, 0);
	    return cal.getTimeInMillis();		
	}
	
}
