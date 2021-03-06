package tplan.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tplan.app.DateTime;
import tplan.app.TimePlanner;

/**
 * 
 * 
 * @author Stephan
 *
 */

public class DateView extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField t, m, j;
	JButton ok;
	TimePlanner model;
	String ti = String.valueOf(DateTime.getDay(DateTime.currentTime()));
	String mi = String.valueOf(DateTime.getMonth(DateTime.currentTime()));
	String ji = String.valueOf(DateTime.getYear(DateTime.currentTime()));
	private final int xx = 150, yy = 100;/**
	 * 
	 * @param x Model
	 */
	
	public DateView(TimePlanner x) {
		super("Datum");
		model = x;
		getContentPane().setLayout(new BorderLayout());
		JPanel input = new JPanel(new GridLayout(2, 3));
		t = new JTextField(ti);
		m = new JTextField(mi);
		j = new JTextField(ji);
		ok = new JButton("OK");
		input.add(new JLabel("     Tag"));
		input.add(new JLabel("  Monat"));
		input.add(new JLabel("   Jahr"));
		input.add(t);input.add(m);input.add(j);
		//tutton.add(text, BorderLayout.SOUTH);tutton.add(button, BorderLayout.SOUTH);
		getContentPane().add(input, BorderLayout.NORTH);
		getContentPane().add(ok, BorderLayout.SOUTH); ok.addActionListener(this.new Controller());
		pack();
		setSize(xx, yy);
		setLocationRelativeTo(null);
		setAlwaysOnTop(false);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	
/**
 * 
 * @param o xx
 * @param arg xx
 * 
 */
	public void update(Observable o, Object arg) {
			   
	}


	/**
	 * Action Listener
	 */
	
	public class Controller implements ActionListener {
/**
 * action performed 
 * 
 * @param e Event
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			String date = t.getText()+"."+m.getText()+"."+j.getText();
			try{SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				dateFormat.setLenient(false);
				dateFormat.parse(date);
				try{model.setDate(Integer.parseInt(t.getText()), Integer.parseInt(m.getText()),
					Integer.parseInt(j.getText()));
					model.notifyObservers();
					setVisible(false);
					model.addObserver(new GraphView(model));
					model.addObserver(new TableView(model));
					}
				catch(NumberFormatException ex){
					return;
					}
				}
			catch(ParseException ex){
				return;
				}
			
			
			}		
		}	
		}
	}
	
	
 // View
