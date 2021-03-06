package tplan.test;

import java.awt.Color;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import tplan.app.*;

public class TestAufgabe2Z {

	public static final long ONEHOUR = 60 * 60 * 1000;
	public static final long ONEDAY = 24 * ONEHOUR;
	public static final long ONEYEAR = 365 * ONEDAY;

	/**
	 * Pr�ft, ob die Ereignisse zur periodischen Aufgabe passen.
	 * @throws DupplicateNameException
	 */
	@Test(timeout = 1000)
	public void testPeriodicTask() throws Exception {
		TimePlanner tp = new TimePlanner();
		Category c = tp.addMainCategory("Alles", Color.BLUE);
		Category cc = c.addSubCategory("Testkategorie");
		tp.addPeriodicTask(cc,"Testtask", getMillis(2013, 1, 8, 8, 0), 6 * ONEHOUR, 2 * ONEDAY, getMillis(2013, 1, 18, 0, 1));

		Event[] events = tp.getEvents(getMillis(2013, 1, 1, 0, 1), getMillis(2013, 1, 20, 0, 1));
		// 8.1. 10.1. 12.1. 14.1. 16.1.
		Assert.assertEquals(5, events.length);

		int i = 0;
		for (Event event : events) {
			Assert.assertEquals("Name des Events unterscheidet sich von dem Namen beim Anlegen des Events", "Testtask", event.getName());
			Assert.assertEquals("Der Kategoriename eines Events unterscheidet sich von dem Kategorienamen beim Anlegen des Events", "Testkategorie", event.getCategory().getName());
			// 8.1. 10.1. 12.1. 14.1. 16.1. Start: jeweils um 8Uhr 
			Assert.assertEquals("Die Startzeit eines Events unterscheidet sich von der Startzeit beim Anlegen des Events", getMillis(2013, 1, 8, 8, 0) + i * 2 * ONEDAY, event.getStartTime());
			// 8.1. 10.1. 12.1. 14.1. 16.1. Ende: jeweils um 14Uhr
			Assert.assertEquals("Die Endzeit eines Events unterscheidet sich von der Endzeit beim Anlegen des Events", getMillis(2013, 1, 8, 8, 0) + 6 * ONEHOUR + i * 2 * ONEDAY, event.getEndTime());
			++i;
		}
	}

	/**
	 * Pr�ft, ob die Endzeit einer Kategorie auch nach dem L�schen von Aufgaben erhalten bleibt.
	 * @throws DupplicateNameException
	 */
	@Test(timeout = 1000)
	public void testCategoryLength() throws Exception {
		TimePlanner tp = new TimePlanner();
		Category alles = tp.addMainCategory("Alles", Color.BLUE);
		Category cc = alles.addSubCategory("Test");
		tp.addPeriodicTask(cc,"Montagsaufgabe", getMillis(2013, 01, 07, 0, 1), ONEDAY, 7 * ONEDAY, getMillis(2013, 02, 23, 13, 59));
		tp.addPeriodicTask(cc,"Dienstagsaufgabe", getMillis(2013, 01, 15, 0, 1), ONEDAY, 7 * ONEDAY, getMillis(2013, 02, 28, 17, 58));
		tp.addPeriodicTask(cc,"Mittwochsaufgabe", getMillis(2013, 01, 16, 0, 1), ONEDAY, 7 * ONEDAY, getMillis(2013, 02, 15, 21, 56));

		Assert.assertEquals("Die Endzeit der Kategorie ist falsch", getMillis(2013, 02, 28, 17, 58), cc.getEndTime());
		tp.removeTask(cc,"Dienstagsaufgabe");
		Assert.assertEquals("Die Endzeit der Kategorie ist falsch", getMillis(2013, 02, 23, 13, 59), cc.getEndTime());
		tp.removeTask(cc,"Montagsaufgabe");
		Assert.assertEquals("Die Endzeit der Kategorie ist falsch",getMillis(2013, 02, 15, 21, 56), cc.getEndTime());
	}

	public static long getMillis(int year, int month, int day, int hour, int min) {
		final Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month - 1, day, hour, min, 0);
		return cal.getTimeInMillis();
	}
}