package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import view.CenterPanel;
import view.GlyphPanel;
import view.MainPanel;

public class WriteScriptListener implements ActionListener {

	MainPanel mainPanel;
	
	public WriteScriptListener(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CenterPanel centerPanel = mainPanel.getCenterPanel();
		HashMap<String, ArrayList<GlyphPanel>> mapping = centerPanel.getMappedCharacters();
		if(mapping != null && mapping.size() != 0) {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Select Script Location");
			int retval = chooser.showSaveDialog(mainPanel.getParent());
			String name = JOptionPane.showInputDialog("What is the name of the font?");
			if(retval == JFileChooser.APPROVE_OPTION) {
				try {
					String filepath = chooser.getSelectedFile().getAbsolutePath();
					PrintWriter script = new PrintWriter(new File(filepath + ".pe"));
					script.println("#!/usr/local/bin/fontforge");
					script.println("New()");
					script.println("ScaleToEm(" + mainPanel.getNorthPanel().getGlyphSizeTextField().getText()+ ")");
					Set<String> keys = mapping.keySet();
					for(String s : keys) {
						GlyphPanel gp = mapping.get(s).get(0);
						if(gp != null) {
							String path = mainPanel.getNorthPanel().getLblDestinationDirectoryPath().getText() + "/" + gp.getFile().getName();
							int number = gp.getGlyph().getUnicodeNumber();
							script.println("Select(" + number + ")");
							script.println("Import(\"" + path +"\")");
						}
					}
					script.println("SetFontNames(\"" + name + "\")");
					script.println("Save(\"" + name + ".sfd\")");
					script.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			} 
		} else {					
			JOptionPane.showMessageDialog(mainPanel.getParent(), "There are no mapped characters to script!");
		}
	}
}
