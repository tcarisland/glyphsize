package controller;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.GlyphPanel;
import view.NorthPanel;

public class ResizeItemsListener implements ActionListener {

	
	private NorthPanel northPanel;
	private JFrame grandparent;

	public ResizeItemsListener(NorthPanel northPanel, JFrame grandparent) {
		this.northPanel = northPanel;
		this.grandparent = grandparent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File dir = northPanel.getDir();
		GlyphPanel panels[] = northPanel.getMainPanel().getCenterPanel().getGlyphPanels();
		if(dir == null) {
			JOptionPane.showMessageDialog(grandparent, "No directory selected");
		} else if(panels.length == 0) {
			JOptionPane.showMessageDialog(grandparent, "No SVG files available");
		}else {
			String glyphSizeString = northPanel.getGlyphSizeTextField().getText();
			int glyphSize = 0;
			if(glyphSizeString.length() != 0)
				glyphSize = Integer.parseInt(glyphSizeString);
			if(glyphSize == 0) {
				JOptionPane.showMessageDialog(grandparent, "No glyph size supplied");
			} else {
				File destDir = northPanel.getDestDir();
				for(GlyphPanel p : panels) {
					File f = p.getFile();
					String destFileName = destDir.getAbsolutePath() + "/" + f.getName();
					SVGFunctions.replaceViewbox(f, destFileName, glyphSize);
				}
				JDialog finished = new JDialog();
				finished.setSize(500, 500);
				finished.setLayout(new GridLayout(2, 1));
				JButton openFolder = new JButton("Open Directory");
				openFolder.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						finished.setVisible(false);
						finished.dispose();
						try {
							Desktop.getDesktop().open(northPanel.getDestDir());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				JButton exitButton = new JButton("Exit");
				exitButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						finished.setVisible(false);
						finished.dispose();
					}
				});
				finished.add(exitButton);
				finished.add(openFolder);
				finished.setVisible(true);
			}
		}
	}	
	
}
