package view;

import model.CharTable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class CenterPanel extends JPanel {

		
	/**
	 * 
	 */
	private static final long serialVersionUID = -5726614048951072812L;

	MainPanel mainPanel;
	JFrame parent;
	JPanel fileListArea;
	JScrollPane fileListScrollPane;
	HashMap<String, ArrayList<GlyphPanel>> mappedCharacters;
	int oldFiles;
	
	/**
	 * Create the panel.
	 * @param parent 
	 * @param mainPanel 
	 */
	public CenterPanel(MainPanel mainPanel, JFrame parent) {
		mappedCharacters = new HashMap<String, ArrayList<GlyphPanel>>();
		int bw = 10;
		oldFiles = 0;
		this.setBorder(new EmptyBorder(bw, bw, bw, bw));

		this.mainPanel = mainPanel;
		this.parent = parent;
		this.setLayout(new BorderLayout());

		fileListArea = new JPanel();
		fileListScrollPane = new JScrollPane(fileListArea);
		this.add(fileListScrollPane,  BorderLayout.CENTER);
	}
	
	public void removeCharacters() {
		mappedCharacters = new HashMap<String, ArrayList<GlyphPanel>>();
		fileListArea.removeAll();
	}
	
	public GlyphPanel[] getGlyphPanels() {
		Component components[] = fileListArea.getComponents();
		ArrayList<GlyphPanel> gp = new ArrayList<GlyphPanel>();
		for(Component c : components)
			if(c instanceof GlyphPanel)
				gp.add((GlyphPanel) c);
		return gp.toArray(new GlyphPanel[gp.size()]);
	}

	public void updateFileListArea(File files[]) {
		fileListScrollPane.invalidate();		
		fileListArea.invalidate();
		fileListArea.removeAll();
		CharTable lookup = new CharTable("ISO 8859-1");
		if(files.length != 0) {
			fileListArea.setLayout(new GridLayout(files.length, 1));
			for(File f : files) {
				GlyphPanel gp = new GlyphPanel(this, f, lookup);
				checkDuplicate(gp);
				if(gp.getGlyph().isUnicodeDefined()) {
					if(mappedCharacters.get(gp.getGlyph().getUnicode()) == null) {
						ArrayList<GlyphPanel> mappedList = new ArrayList<GlyphPanel>();
						mappedList.add(gp);
						mappedCharacters.put(gp.getGlyph().getUnicode(), mappedList);
					} else {
						mappedCharacters.get(gp.getGlyph().getUnicode()).add(gp);
					}	
				} 
				fileListArea.add(gp);
			}
		}
		fileListArea.validate();
		fileListScrollPane.validate();
		repaint();
	}

	public void removeMapping(GlyphPanel gp) {
		if(mappedCharacters.containsKey(gp.getGlyph().getUnicode())) {
			ArrayList<GlyphPanel> panels = mappedCharacters.get(gp.getGlyph().getUnicode());
			for(int i = 0; i < panels.size(); i++) {
				GlyphPanel g = panels.get(i);
				if(g.getNumber() == gp.getNumber()) {
					panels.remove(i);
					break;
				}
			}
			if(panels.size() < 2) {
				for(int i = 0; i < panels.size(); i++) {
					panels.get(i).unmark();
				}
			}
			if(panels.isEmpty()) {
				mappedCharacters.remove(gp.getGlyph().getUnicode());
			}
		}
	}
	
	public void checkDuplicate(GlyphPanel gp) {
		if(mappedCharacters.containsKey(gp.getGlyph().getUnicode())) {
			mappedCharacters.get(gp.getGlyph().getUnicode()).add(gp);
			String dupes = "";
			for(GlyphPanel g : mappedCharacters.get(gp.getGlyph().getUnicode())) {
				dupes += g.getNumber() + " ";
			}
			for(GlyphPanel g : mappedCharacters.get(gp.getGlyph().getUnicode())) {
				g.markDuplicate();
				g.getDuplicatesLabel().setText(dupes);
			}
		}
	}

	/**
	 * @return the mappedCharacters
	 */
	public HashMap<String, ArrayList<GlyphPanel>> getMappedCharacters() {
		return mappedCharacters;
	}

	/**
	 * @param mappedCharacters the mappedCharacters to set
	 */
	public void setMappedCharacters(HashMap<String, ArrayList<GlyphPanel>> mappedCharacters) {
		this.mappedCharacters = mappedCharacters;
	}
	
}
