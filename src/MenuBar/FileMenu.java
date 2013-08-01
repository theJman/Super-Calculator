package MenuBar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import BasicCalculator.CalculatorFrame;
import BasicCalculator.CalculatorManager;
import FunctionStuff.Function;
import FunctionStuff.SaveFile;

public class FileMenu extends JMenu {
	private static final long serialVersionUID = -7472611753786188478L;
	
	private JMenuItem miClearFormulas;
	private JMenuItem miClearMem;
	//menu items
	private JMenuItem miOpen;
	private JMenuItem miSave;
	private CalcMenuBar menuBar;
	/**
	 * Creates a new File Menu with menu items and actions
	 */
	public FileMenu(final CalcMenuBar menuBar){
		setText("File");
		setBackground(Color.gray);
		this.menuBar = menuBar;
		//init items
		miOpen = new JMenuItem("Open");
		miOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openPressed();
			}
		});
		add(miOpen);
		miSave = new JMenuItem("Save");
		miSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				savePressed();
			}
		});
		add(miSave);
		miClearFormulas = new JMenuItem("Clear Functions");
		miClearFormulas.setToolTipText("Erases all User Functions");
		miClearFormulas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to erase all user created functions?", "Are you sure?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					System.out.println("Removing Functions");
					Function.getFunctions().clear();
					System.out.println(Function.getFunctions());
					menuBar.updateFunctions();
				}
				
			}
		});
		add(miClearFormulas);
		miClearMem = new JMenuItem("Clear Variables");
		miClearMem.setToolTipText("Erases all User craeted variables");
		miClearMem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to erase all user created variables?", "Are you sure?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					System.out.println("Removing Variables");
					CalculatorManager.getMemDict().clear();
					menuBar.updateFunctions();
				}
				
			}
		});
		add(miClearMem);
		
	}
	/**
	 * Called when the open menu item is pressed
	 */
	private void openPressed(){
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter extentions = new FileNameExtensionFilter("SuperCalcSave", "scalcsave");
		fileChooser.setFileFilter(extentions);
		int retunedVal = fileChooser.showOpenDialog(this);
		File file = null;
		if(retunedVal == JFileChooser.APPROVE_OPTION){
			file = fileChooser.getSelectedFile();
		}
		//only continue if a file is selected
		if(file != null){
			SaveFile saveFile = null;
			try {
				//read in file
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				saveFile = (SaveFile) input.readObject();
				input.close();
				CalculatorFrame.setMemDict(saveFile.getMemDict());
				Function.setFunctions(saveFile.getFunctions());
				menuBar.updateFunctions();
				System.out.println("Open Success");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * Called when the save menu item is pressed
	 */
	private void savePressed(){
		
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter extentions = new FileNameExtensionFilter("SuperCalcSave", "scalcsave");
		fileChooser.setFileFilter(extentions);
		int retunedVal = fileChooser.showSaveDialog(this);
		File file = null;
		if(retunedVal == JFileChooser.APPROVE_OPTION){
			file = fileChooser.getSelectedFile();
			//make sure file has the right extention
			if(file.getAbsolutePath().contains(".scalcsave"))
				file = new File(file.getAbsolutePath());
			else
				file = new File(file.getAbsolutePath()+".scalcsave");
		}
		//only continue if a file is selected
		if(file != null){	
			try {
				//read in file
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				output.writeObject(SaveFile.getSaveFile());
				output.close();
				System.out.println("Save Success");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
