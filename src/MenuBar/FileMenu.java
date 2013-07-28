package MenuBar;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileMenu extends JMenu {
	private static final long serialVersionUID = -7472611753786188478L;
	
	private JMenuItem miClearFormulas;
	private JMenuItem miClearMem;
	//menu items
	private JMenuItem miOpen;
	private JMenuItem miSave;

	/**
	 * Creates a new File Menu with menu items and actions
	 */
	public FileMenu(){
		setText("File");
		setBackground(Color.gray);
		
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
		
		
	}
	/**
	 * Called when the open menu item is pressed
	 */
	private void openPressed(){
		//TODO
	}
	/**
	 * Called when the save menu item is pressed
	 */
	private void savePressed(){
		//TODO
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter extentions = new FileNameExtensionFilter("Shape Sets", "shps");
		fileChooser.setFileFilter(extentions);
		int retunedVal = fileChooser.showSaveDialog(this);
		File file = null;
		if(retunedVal == JFileChooser.APPROVE_OPTION){
			file = fileChooser.getSelectedFile();
			//make sure file has the right extention
			file = new File(file.getAbsolutePath()+".shps");
		}
		//only continue if a file is selected
		if(file != null){
			
			try {
				//read in file
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				//output.writeObject(drawPanel.getShapeSet());
				output.close();
				System.out.println("Save Success");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
