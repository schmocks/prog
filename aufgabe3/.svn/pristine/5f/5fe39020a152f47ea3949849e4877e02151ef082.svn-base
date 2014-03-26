package tplan.gui;


import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Graphics;

import java.util.Observable;
import java.util.Observer;


import javax.swing.JFrame;



import tplan.app.Category;
import tplan.app.DateTime;
import tplan.app.Event;
import tplan.app.TimePlanner;

/**
 * Klasse der Diagrammdarstellung
 * 
 * @author Stephan
 *
 */

public class GraphView extends JFrame implements Observer {



	private static final long serialVersionUID = -8840343388903623096L;
	private final int hoehe = 700;
	private final int breite = 24*49;
	TimePlanner model;
	final String[] tage = {"Sonntag", "Montag", "Dienstag", "Mittwoch", 
					 "Donnerstag", "Freitag", "Samstag"};
	final int zehn = 10, vizw = 24, vizi = 40, fuvi = 45, nevi = 49, sezi = 60;
	final int zwan = 20, fufi = 50;
	
	
	/**
	 * Konstruktor der Diagrammdarstellung
	 * 
	 * @param m TimePLanner aktuell
	 * 
	 */
	
	public GraphView(TimePlanner m){
		super("Tagesansicht " + m.getDate());
		model = m;
		getContentPane().setLayout(new BorderLayout());
		pack();
		setSize(breite, hoehe);setResizable(false);setVisible(true);
		setLocationRelativeTo(null);setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		getContentPane().setBackground(Color.white);
		
		
	}
	/**
	 * Zeichnet Tabelle
	 * 
	 * @param g Graphik
	 * 
	 */
	
	
	public void drawTable(Graphics g){
		super.paint(g);
		int j= DateTime.getWeekDay(model.getZeit())-1;
		int z= 0;
		
		
		if(!model.getK()){
			for(int i=0;i<zehn;i++){
				g.setColor(Color.BLACK);
				if(i>0) {g.drawString(String.valueOf(i)+":00", i*nevi-8, vizi);}
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(i*nevi, fuvi, i*nevi, hoehe);
			}
			for(int i=zehn;i<vizw;i++){
				g.setColor(Color.BLACK);
				g.drawString(String.valueOf(i)+":00", i*nevi-(zehn+5), vizi);
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(i*nevi, fuvi, i*nevi, hoehe);
			}
			setTitle("Stundenansicht " + model.getDate());
			g.drawLine(0, fuvi, breite, fuvi);
		}
		String[] akt = new String[7];
		for(int i = j;i<7;i++, z++) {akt[z]=tage[i];}
		for(int i = 0;i<j;i++, z++) {akt[z]=tage[i];}
		if(model.getK()){
			for (int i=0;i<7;i++) {
				g.setColor(Color.BLACK);
				g.drawString(akt[i], sezi+(i*vizw*7), vizi);				
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(i*vizw*7, 0, i*vizw*7, hoehe);
			}
			g.drawLine(0, fuvi, breite, fuvi);
			setTitle("Tagesansicht " + model.getDate());
		}
			
	}
	
	
	
	/**
	 * Zeichne Tagesansicht
	 * 
	 * @param g Graphic
	 */
	
	public void drawDaily(Graphics g){
		int zei = 1;
		Event[] a = model.showEvents();
		String name = a[0].getName();
		Category cat = a[0].getCategory();
		int merk = 0;
		
		for(int i = 0; i<a.length;i++){
			if(name==a[i].getName()&&cat==a[i].getCategory()){
				int red = a[i].getCategory().getColor().getRed();
				int green = a[i].getCategory().getColor().getGreen();
				int blue = a[i].getCategory().getColor().getBlue();
				int ol = (int) ((a[i].getStartTime()-model.getZeit())/(DateTime.ONEDAY/breite));
				int ur = (int) ((a[i].getEndTime()-model.getZeit())/(DateTime.ONEDAY/breite));
				g.setColor(new Color(red, green, blue, sezi+fufi));
				g.fillRect(ol, fufi*(zei), ur-ol, zwan);
				g.setColor(Color.BLACK);
				g.drawString(a[i].getName(), ol+5 , (fufi*(zei))+zehn+5);
				g.setColor(new Color(red, green, blue, sezi));
				if(merk==0){g.fillRect(0, (fufi*(zei))-5, breite, fufi); merk++;}
			}
			
			if(i+1!=a.length){
				if(name!=a[i+1].getName()||cat!=a[i+1].getCategory()){
					cat=a[i+1].getCategory();name=a[i+1].getName();zei++;merk--;
				}
			}
			
		
		}
	}
	
	/**
	 * 
	 * Zeichne Wochenansicht
	 * 
	 * @param g Graphic
	 */
	public void drawWeekly(Graphics g){
		int zei = 1;
		Event[] a = model.showEvents();
		String name = a[0].getName();
		Category cat = a[0].getCategory();
		int merk = 0;
		
		for(int i = 0; i<a.length;i++){
			if(name==a[i].getName()&&cat==a[i].getCategory()){
				int ol = (int) ((a[i].getStartTime()-model.getZeit())/(DateTime.ONEDAY*7/breite));
				int ur = (int) ((a[i].getEndTime()-model.getZeit())/(DateTime.ONEDAY*7/breite));
				int red = a[i].getCategory().getColor().getRed();
				int green = a[i].getCategory().getColor().getGreen();
				int blue = a[i].getCategory().getColor().getBlue();
				g.setColor(new Color(red, green, blue, sezi+fufi));
				g.fillRect(ol, fufi*(zei), ur-ol, zwan);
				g.setColor(Color.BLACK);
				g.drawString(a[i].getName(), ol+5 , (fufi*(zei))+zehn+5);
				g.setColor(new Color(red, green, blue, sezi));
				if(merk==0){g.fillRect(0, (fufi*(zei))-5, breite, fufi); merk++;}
			}
			
			if(i+1!=a.length){
				if(name!=a[i+1].getName()||cat!=a[i+1].getCategory()){
					cat=a[i+1].getCategory();name=a[i+1].getName();zei++;merk--;
				}
			}
			
		
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawTable(g);
		
		if(!model.getK()){
			drawDaily(g);
		}
		if(model.getK()){
			drawWeekly(g);
		}
		
		
		
		
		
	}
	/**
	 * 
	 * Observer
	 * 
	 * @param o Ojekt
	 * @param arg Argument
	 * 
	 */
	public void update(Observable o, Object arg) {
		setTitle("Termine " + model.getDate());
		repaint();
		

	}

}
