package tplan.gui;

import java.awt.Color;


import tplan.app.*;

/**
 * 
 * 
 * @author Stephan
 *
 */
public class App {
	/**
	 * 
	 *Main Methode 
	 * 
	 * @param args Main
	 * @throws Exception falls Timeplanner schon vorhanden
	 */

	public static void main(String[] args) throws Exception {
		
		
		/*Hier werden verschiedene Einträge in den TimePlanner eingetragen die immer um 
		 *das aktuelle Datum liegen*/
		
		
		final int jahr = DateTime.getYear(DateTime.currentTime());
		final int monat = DateTime.getMonth(DateTime.currentTime());
		final int tag = DateTime.getDay(DateTime.currentTime());
		TimePlanner tp = new TimePlanner();
		Category a = tp.addMainCategory("Blau", Color.BLUE);
		Category h = tp.addMainCategory("Rot", Color.RED);
		Category b = a.addSubCategory("Eins");
		Category c = h.addSubCategory("Zwei");
		Category m = tp.addMainCategory("Gelb", Color.YELLOW);
		long zx = DateTime.toMillis(jahr, monat, tag);
		tp.addPeriodicTask(b, "Murmeltier", zx+2*4*DateTime.ONEHOURE, 
				DateTime.ONEHOURE, DateTime.ONEDAY, zx+DateTime.ONEDAY*5-1);
		tp.addTask(b, "Blaueins", zx+DateTime.ONEDAY);
		tp.addTask(b, "Blauzwo", zx+DateTime.ONEDAY);
		tp.addTask(b, "Blaudrei", zx+DateTime.ONEDAY);
		tp.addTask(b, "Blauvier", zx+DateTime.ONEDAY);
		tp.addTask(c, "HUHU", zx);
		tp.addTask(c, "HUHU1", zx+DateTime.ONEDAY);
		tp.addTask(c, "HUHU2", zx+2*DateTime.ONEDAY);
		tp.addTask(c, "HUHU3", zx+3*DateTime.ONEDAY);
		tp.addTask(c, "HUHU4", zx+4*DateTime.ONEDAY);
		tp.addTask(m, "GELB", DateTime.toMillis(jahr, monat, tag+1));
		System.out.print(tp);
		
		
		
		
		DateView v1 = new DateView(tp);
		tp.addObserver(v1);
		
	}

}
