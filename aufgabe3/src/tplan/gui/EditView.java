package tplan.gui;


import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import tplan.app.Category;
import tplan.app.DateTime;
import tplan.app.PeriodicTask;
import tplan.app.TimePlanner;
/**
 * 
 * 
 * @author Stephan
 *
 */
public class EditView extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton save, close;
	TimePlanner model;
	private JTable table;
	private int i;
	java.util.Date stp = null;
    java.util.Date etp = null;
	Category ocat;
	String oname;
	private final int xx = 700, yy = 103;
    /**
	 * 
	 * @param m Model
	 * @param i index des Events
	 * 
	 */
	
	public EditView(TimePlanner m, int i) {
		super(m.getTask(i).getName() + " editieren");
		this.i = i;
		model = m;
		getContentPane().setLayout(new BorderLayout());
		JPanel button = new JPanel(new GridLayout(1, 2));
		save = new JButton ("Speichern");
		close = new JButton("Abbrechen");
		button.add(save); save.addActionListener(this.new Controller());
		button.add(close); close.addActionListener(this.new Controller());
		getContentPane().add(table(), BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.SOUTH);
		pack();
		setSize(xx, yy);setResizable(false);setVisible(true);
		setLocationRelativeTo(null);
		
		
	}
	

	/**
	 * Erzeugt Tabelle
	 * 
	 * @return Tabelle
	 */
	private JScrollPane table(){
		table = new JTable(deftable());
        table.setColumnSelectionAllowed(false);
		JScrollPane jsp = new JScrollPane(table);
		jsp.setPreferredSize(getPreferredSize());
		table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		table.setRowSelectionAllowed(false);
		table.setFocusable(true);
		return jsp;
	}
	
	/**
	 * Erzeugt defaultable
	 * 
	 * @return defaulttable
	 */
	
	private DefaultTableModel deftable(){
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		PeriodicTask a = (PeriodicTask) model.getTask(i);
				oname = a.getName();
				ocat = a.getCategory();
				Vector<String> row = new Vector<String>();
				row.add(a.getName());
				row.add(a.getCategory().getName());
				row.add(DateTime.dateTime(a.getStartTime()));
				row.add(String.valueOf((a.getDuration()/DateTime.ONEMINUTE)));
				row.add(DateTime.dateTime(a.getEndTime()));
				row.add(String.valueOf(a.getDelta()/DateTime.ONEHOURE));
				data.add(row);
			Vector<String> title = new Vector<String>();
			title.add("Name"); title.add("Kategorie");
			title.add("Start"); title.add("Dauer in Minuten"); title.add("Ende");
			title.add("Abstand Stunden");
			DefaultTableModel dft = new DefaultTableModel(data, title);
			
			return dft;
	}
	
	
	/**
	 * Update Methde
	 * @param o Objekt
	 * @param arg Objekt
	 */

	public void update(Observable o, Object arg) {
		
		
	}

	
	
/**
 * Controller
 * 
 * @author Stephan
 *
 */
	public class Controller implements ActionListener {
/**
 * Action
 * 
 * 
 * @param e ActionEvent
 */
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == close){
			setVisible(false);
		}	
		if (e.getSource() == save){
			 Category cat;
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		     try {
		     stp = dateFormat.parse((String) table.getValueAt(0, 2));
		     etp = dateFormat.parse((String) table.getValueAt(0, 4));
		     } catch (Exception ex) {
		         return;
		     }
		     long du = (Integer.parseInt((String) table.getValueAt(0, 3)))*DateTime.ONEMINUTE;
		     long de = (Integer.parseInt((String) table.getValueAt(0, 5)))*DateTime.ONEHOURE;
		     long st = stp.getTime();
		     long et = etp.getTime();
		     String nt = (String) table.getValueAt(0, 0);
		     PeriodicTask a = (PeriodicTask) model.getTask(i);
		     a.setTstartTime(st);
		     a.setEnd(et);
		     a.setName(nt);
		     a.setDuration(du);
		     a.setDelta(de);
		     try {
				cat = (Category) model.checkCat((String) table.getValueAt(0, 1));}
		     catch (Exception e1) {return;}
		     if(a.getCategory()!=cat){
		    	 a.setCat(cat);model.removeTask(ocat, oname);
		    	 try {
					model.addPeriodicTask(cat, nt, st, a.getDuration(), a.getDelta(), et);
				} catch (Exception e1) {
					return;
				}
		    	 }
		     
		    setVisible(false);
			model.save();
		    model.notifyObservers();
			
		}
	}
	
	}
	}


