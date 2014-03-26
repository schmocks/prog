package tplan.gui;


import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.*;
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

import tplan.app.Event;
import tplan.app.DateTime;
import tplan.app.TimePlanner;
/**
 * 
 * 
 * @author Stephan
 *
 */
public class TableView extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton change, de, ed;
	TimePlanner model;
	private final int yy = 230, xx = 500;
	private JTable table;
	private Vector<Vector<String>> data = new Vector<Vector<String>>();
	
	/**
	 * 
	 * @param m Model
	 * 
	 */
	
	public TableView(TimePlanner m) {
		super("Terminkalender " + m.getDate());
		model = m;
		getContentPane().setLayout(new BorderLayout());
		JPanel button = new JPanel(new GridLayout(1, 3));
		change = new JButton ("Wochenansicht");
		de = new JButton("Aufgabe löschen");
		ed = new JButton("Aufgabe bearbeiten");
		button.add(change); change.addActionListener(this.new Controller());
		button.add(de); de.addActionListener(this.new Controller());
		button.add(ed); ed.addActionListener(this.new Controller());
		getContentPane().add(button, BorderLayout.NORTH);
		getContentPane().add(table(), BorderLayout.CENTER);
		pack();
		setSize(xx, yy);setResizable(false);setVisible(true);toFront();
		setLocationRelativeTo(null);setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		
	}
	
	
	

	
	
	
/**
 * Konstruktor Tabelle
 * @return Tabelle
 */
	private JScrollPane table(){
		table = new JTable(deftable()){
			private static final long serialVersionUID = 6738844632488030201L;
			public boolean isCellEditable(int x, int y){
				return false;
				}
			};
        table.setColumnSelectionAllowed(false);
		JScrollPane jsp = new JScrollPane(table);
		jsp.setPreferredSize(getPreferredSize());
		table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		table.setRowSelectionAllowed(true);
		table.setFocusable(false);
		return jsp;
	}
	/**
	 * Erzeugt Tabelle	
	 * 
	 * @return Tabelle
	 */
	private DefaultTableModel deftable(){
		data.removeAllElements();
		Event[] a = model.showEvents();
			for(int i = 0; i<a.length;i++){
				Vector<String> row = new Vector<String>();
				row.add(a[i].getName());
				row.add(a[i].getCategory().getName());
				row.add(DateTime.dateTime(a[i].getStartTime()));
				row.add(DateTime.dateTime(a[i].getEndTime()));
				data.add(row);
				}
			Vector<String> title = new Vector<String>();
			title.add("Name"); title.add("Kategorie");
			title.add("Start"); title.add("Ende");
			DefaultTableModel dft = new DefaultTableModel(data, title);
			
			return dft;
	}
	
	/**
	 * 
	 * @param o Objekt welches beobachtet wird
	 * @param arg argument
	 * 
	 */
	public void update(Observable o, Object arg) {
		TimePlanner m = (TimePlanner)o;
		m.showEvents();
		table.setModel(deftable());	
			
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
 * @param e Event
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == change) {
			model.setK();
			if(model.getK()){
				change.setText("Tagesansicht");
			}
			else{change.setText("Wochenansicht");}
			setTitle("Terminkalender " + model.getDate());		
			model.notifyObservers();		
		}	
		if (e.getSource() == de){
			if(table.getSelectedRow()<0){return;}
			model.deleteTask(table.getSelectedRow());
			model.notifyObservers();
			}
		if (e.getSource() == ed){
			
			if(table.getSelectedRow()<0){return;}
			if(!model.checkTask(table.getSelectedRow())){return;}
			model.addObserver(new EditView(model, table.getSelectedRow()));
		}
	}	
	}
	
}

