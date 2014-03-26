package tplan.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.Test;

import tplan.app.*;

public class TestAufgabe1 {

	public static final long ONEHOUR = 60*60*1000;
	public static final long ONEDAY = 24*ONEHOUR;
	
	@Test
	public void testAnlegenKategorien() throws Exception {
		TimePlanner tp = new TimePlanner();
		Category s = tp.addMainCategory("Studium", Color.RED);
		Category h = tp.addMainCategory("Freizeit", Color.GREEN);
		Category p = s.addSubCategory("Programmieren");
		
		assertEquals(s.getName(), "Studium");
		assertEquals(h.getName(), "Freizeit");
		assertEquals(p.getName(), "Programmieren");

		Category[] cat = tp.getMainCategories();
		assertEquals(2, cat.length);
		assertTrue(cat[0].getName().equals("Studium") || cat[1].getName().equals("Studium"));
		assertTrue(cat[0].getName().equals("Freizeit") || cat[1].getName().equals("Freizeit"));
		
		cat = s.getSubCategories();
		assertEquals(1, cat.length);
		assertTrue(cat[0].getName().equals("Programmieren"));

		cat = h.getSubCategories();
		assertEquals(0, cat.length);
	}

	@Test
	public void testAnlegenAufgabe() throws Exception {  
		TimePlanner tp = new TimePlanner();
		Category s = tp.addMainCategory("Studium", Color.RED);
		Category h = tp.addMainCategory("Freizeit", Color.GREEN);
		Category p = s.addSubCategory("Programmieren");

		testAnlegen(tp, h);
		testAnlegen(tp, p);
	}

	private void testAnlegen(TimePlanner tp,Category c) throws Exception {
		long termin = getMillis(2013, 2, 15, 0, 0);
		long dauer = 15*60*1000;
		assertTrue(tp.isFree(termin, 10000));
		tp.addTask(c,"Laborabgabe", termin, dauer );
		assertFalse(tp.isFree(termin, 10000));
		
		System.out.println(tp.isFree(termin, 10000));
		
		assertTrue(tp.isFree(termin-10001, 10000));
		assertFalse(tp.isFree(termin+dauer-5000, 10000));
		assertTrue(tp.isFree(termin+dauer+1, 10000));
		Event[] e = tp.getEvents(termin);
		assertEquals(1,e.length);
		assertEquals("Laborabgabe", e[0].getName());
		assertEquals(c, e[0].getCategory());
		assertEquals(termin, e[0].getStartTime());
		assertEquals(termin+dauer, e[0].getEndTime());		
		tp.removeTask(c,"Laborabgabe");		
	}
	
	@Test
	public void testAusschlussAufgabeUnterkategorie() throws Exception {  
		TimePlanner tp = new TimePlanner();
		Category s = tp.addMainCategory("Studium", Color.RED);
		Category p = s.addSubCategory("Programmieren");
		assertFalse(tp.addTask(s,"Aufgabe 1 loesen", getMillis(2013, 03, 12, 8, 30), ONEHOUR*5));
		assertTrue(tp.addTask(p,"Aufgabe 1 loesen", getMillis(2013, 03, 12, 8, 30), ONEHOUR*5));
		assertNull(p.addSubCategory("Aufgabe 1"));
	}
	
	@Test
	public void testKategorieDauer() throws Exception {  
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Alles", Color.RED);
		Category p = a.addSubCategory("Programmieren");
		Category s = a.addSubCategory("Sport");
		assertTrue(tp.addTask(p, "Aufgabe 1", getMillis(2013, 03, 20, 8, 30), ONEHOUR));
		assertTrue(tp.addTask(p, "Aufgabe 2", getMillis(2013, 04, 20, 8, 30), ONEHOUR));
		assertTrue(tp.addTask(s, "Joggen", getMillis(2013, 03, 18, 8, 30), ONEHOUR));
		assertEquals(p.getStartTime(),getMillis(2013, 03, 20, 8, 30));
		assertEquals(p.getEndTime(), getMillis(2013, 04, 20, 8, 30)+ONEHOUR);
		assertEquals(s.getStartTime(), getMillis(2013, 03, 18, 8, 30));
		assertEquals(s.getEndTime(), getMillis(2013, 03, 18, 8, 30)+ONEHOUR);
		//--------------------------------------------
		System.out.println(tp);
	}
	
	@Test
	public void testGetEvents() throws Exception {  
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Alles", Color.RED);
		Category p = a.addSubCategory("Programmieren");
		Category s = a.addSubCategory("Sport");
		assertTrue(tp.addTask(p, "Aufgabe 1", getMillis(2013, 03, 20, 8, 30), 2*ONEDAY));
		assertTrue(tp.addTask(p, "Aufgabe 2", getMillis(2013, 04, 20, 8, 30), ONEDAY));
		assertTrue(tp.addTask(s, "Joggen 1", getMillis(2013, 03, 18, 8, 30), ONEHOUR));
		assertTrue(tp.addTask(s, "Joggen 2", getMillis(2013, 03, 20, 10, 30), ONEHOUR));
		
		Event[] events = tp.getEvents( getMillis(2013, 03, 20, 8, 30)-ONEHOUR, 35*ONEDAY);
		
		System.out.println(p.getTasks().length);
		
		
		
		assertEquals(3,events.length);
		assertNotNull(events[0]);
		assertNotNull(events[1]);
		assertNotNull(events[2]);
		String[] erwartet = { "Aufgabe 1", "Aufgabe 2", "Joggen 2" };
		String[] erhalten = { events[0].getName(), events[1].getName(), events[2].getName() };
		Arrays.sort(erhalten); Arrays.sort(erwartet);
		for (int i=0;i<erwartet.length;++i)
			assertEquals(erwartet[i], erhalten[i]);

		events = tp.getEvents( getMillis(2013, 03, 20, 11, 00) );
		assertEquals(2,events.length);
		assertNotNull(events[0]);
		assertNotNull(events[1]);
		assertTrue( !events[0].getName().equals(events[1].getName()) );
		assertTrue( events[0].getName().equals("Aufgabe 1") || events[0].getName().equals("Joggen 2") );
		assertTrue( events[1].getName().equals("Aufgabe 1") || events[1].getName().equals("Joggen 2") );

	}
	
	public static long getMillis(int year,int month,int day, int hour, int min) {
		final Calendar cal = Calendar.getInstance();		
		cal.clear();
	    cal.set(year, month - 1, day, hour, min, 0);
	    return cal.getTimeInMillis();		
	}
	
}
