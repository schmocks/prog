package tplan.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.IOException;
import java.util.Calendar;

import org.junit.Test;

import tplan.app.*;

public class TestAufgabe3 {

	public static final long ONEHOUR = 60*60*1000;
	public static final long ONEDAY  = 24*ONEHOUR;
	public static final long ONEYEAR = 365*ONEDAY;

	@Test
	public void testSaveLoad() throws IOException, Exception {
		{
			TimePlanner tp = new TimePlanner();
			Category a = tp.addMainCategory("Alles", Color.RED);
			Category p = a.addSubCategory("Programmieren");
			Category s = a.addSubCategory("Sport");
			assertTrue(tp.addTask(p, "AL", getMillis(2013, 03, 20, 8, 30), ONEDAY));
			assertTrue(tp.addPeriodicTask(p, "A", getMillis(2013, 03, 1, 9, 0), 4*ONEHOUR, ONEDAY, getMillis(2013,04,1,0,0)));
			assertTrue(tp.addYearlyTask(s, "Geburtstag", getMillis(2000, 03, 10, 0, 0), getMillis(2040, 12, 31, 0, 0)));			
			tp.save("tp.bin");
		}
		//--------------------------------------------------
		{
			TimePlanner tp = new TimePlanner();
			tp.load("tp.bin");			
			Event[] events = tp.getEvents( getMillis(2010, 01, 1, 0, 0)-ONEHOUR, 5*ONEYEAR);		
			System.out.println("Anzahl: " + events.length);
			assertEquals(1+31+5,events.length);		
			for (int i=0;i<events.length;++i)
				for (int j=i+1; j<events.length;++j)
					assertTrue( events[i]!=events[j] );
			events = tp.getEvents( getMillis(2011, 03, 10, 11, 00) );
			assertEquals(1,events.length);
			assertEquals("Geburtstag",events[0].getName());	
		}
	}

	
	@Test
	public void testMainAndSub() throws Exception {		
		TimePlanner tp = new TimePlanner();		
		tp.addMainCategory("Alles", Color.RED);
		Category[] main = tp.getMainCategories();
		assertTrue("Ungültige Anzahl Hauptkaterorien", 
				main.length == 1);
		assertTrue("Name der Hauptkaterorie falsch",
				main[0].getName().equals("Alles"));
				
		main[0].addSubCategory("Programmieren");
		assertTrue("Ungültiges Ereignis in der Hauptkaterorie", 				
				tp.getEvents(getMillis(2010,1,1, 8,30),10*ONEYEAR).length == 0);
		//------------------------------------------------
		Category[] sub = main[0].getSubCategories();
		assertTrue("Ungültige Anzahl Unterkaterorien", 
				sub.length == 1);
		assertTrue("Name der Unterkaterorie falsch",
				sub[0].getName().equals("Programmieren"));
	}
	
	
	@Test (expected=Exception.class)
	public void testMainCategory() throws Exception {		
		TimePlanner tp = new TimePlanner();
		tp.addMainCategory("Alles", Color.RED);
		tp.addMainCategory("Alles", Color.RED);
	}
	
	
	@Test (expected=Exception.class)
	public void testSubCategory() throws Exception {		
		TimePlanner tp = new TimePlanner();
		Category a  = tp.addMainCategory("Alles", Color.RED);
		a.addSubCategory("Programmieren");
		a.addSubCategory("Programmieren");		
	}
	

	public static long getMillis(int year,int month,int day, int hour, int min) {
		final Calendar cal = Calendar.getInstance();		
		cal.clear();
		cal.set(year, month - 1, day, hour, min, 0);
		return cal.getTimeInMillis();		
	}

}
