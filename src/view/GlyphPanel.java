package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controller.BufferedImageTranscoder;
import model.Glyph;
import model.CharacterLookup;

public class GlyphPanel extends JPanel implements Comparable<GlyphPanel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2238092138443824374L;

	CenterPanel centerPanel;
	Glyph glyph;
	JCheckBox export;
	
	JLabel mappingLabel;
	JLabel imageLabel;
	JLabel filenameLabel;
	JLabel duplicatesLabel;
	JLabel heightLabel;
	JLabel widthLabel;
	JLabel numberLabel;
	
	JDialog setUnicodeDialog;
	JButton setUnicodeButton;

	JPanel contentPane;
	JPanel imagePanel;
	JPanel dimensionsPanel;

	File file;
	static int counter = 0;
	int number;
	int viewbox[];
	boolean isDuplicate;
	GlyphPanel current;
	
	/**
	 * Create the panel.
	 */
	public GlyphPanel(CenterPanel parent, File f, CharacterLookup table) {
		number = counter++;
		isDuplicate = false;
		current = this;
		this.centerPanel = parent;
		this.setLayout(new BorderLayout());
		this.file = f;
		glyph = new Glyph(f, table);
		contentPane = this.createContentPane(f, glyph);
		this.unmark();
		this.populateGlyphPanel(contentPane, imagePanel);
	}
	
	public static void resetCounter() {
		counter = 0;
	}
	
	public void populateGlyphPanel(JPanel contentPane, JPanel imagePanel) {
		add(contentPane, BorderLayout.CENTER);
		add(imagePanel, BorderLayout.EAST);
	}
	
	public JPanel createContentPane(File f, Glyph glyph) {
		JPanel contentPane = new JPanel();
		contentPane = new JPanel();
		JComponent components[] = this.createComponents(f, glyph);
		contentPane.setLayout(new GridLayout(1, components.length));
		contentPane = this.populateContentPane(contentPane, components);
		return contentPane;
	}
	
	public JComponent[] createComponents(File f, Glyph glyph) {
		numberLabel = this.createNumberLabel();
		imagePanel = createImagePanel(f);
		export = createExportCheckBox(glyph);
		filenameLabel = this.createFilenameLabel(f.getName());
		mappingLabel = createMappingLabel(glyph);
		setUnicodeButton = createSetUnicodeButton();
		duplicatesLabel = this.createDuplicatesLabel();
		dimensionsPanel = this.createDimensionsPanel(f);
		return new JComponent[]{numberLabel, export, filenameLabel, mappingLabel, setUnicodeButton, duplicatesLabel, dimensionsPanel};
	}
	
	private JLabel createNumberLabel() {
		numberLabel = new JLabel("" + this.getNumber());
		Font labelFont = numberLabel.getFont();
		numberLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, labelFont.getSize()*2));
		numberLabel.setName("Number");
		return numberLabel;
	}

	private JPanel createDimensionsPanel(File f) {
		viewbox = this.getGlyph().getViewbox();
		dimensionsPanel = new JPanel(new GridLayout(1, 1));
		JLabel l = new JLabel("" + viewbox[2] + " X " + viewbox[3] + " px");
		l.setHorizontalAlignment(SwingConstants.CENTER);
		dimensionsPanel.add(l);
		dimensionsPanel.setName("Dimensions");
		return dimensionsPanel;
	}

	public void centerComponents() {
		export.setHorizontalAlignment(SwingConstants.CENTER);
		filenameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mappingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		setUnicodeButton.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public JPanel populateContentPane(JPanel contentPane, JComponent[] components) {
		for(JComponent jc : components) {
			JPanel wrapper = new JPanel(new GridLayout(1, 1));
			if(jc instanceof JLabel) {
				JLabel l = (JLabel) jc;
				l.setHorizontalAlignment(SwingConstants.CENTER);
			}
			wrapper.add(jc);
			wrapper.setBorder(BorderFactory.createTitledBorder(jc.getName()));
			contentPane.add(wrapper);
		}
		return contentPane;
	}

	public JLabel createFilenameLabel(String filename) {
		this.filenameLabel = new JLabel(filename);
		filenameLabel.setName("Filename");
		return filenameLabel;
	}
	
	public JLabel createDuplicatesLabel() {
		JLabel duplicatesLabel = new JLabel("No duplicates");
		duplicatesLabel.setName("Duplicates");
		return duplicatesLabel;
	}
	
	public JButton createSetUnicodeButton() {
		JButton setUnicodeButton = new JButton("Set Mapping");
		setUnicodeButton.setName("Set Mapping");
		setUnicodeButton.addActionListener(new SetUnicodeListener());
		return setUnicodeButton;
	}
	
	
	public JLabel createMappingLabel(Glyph glyph) {
		char character = (char) glyph.getUnicodeNumber();
		JLabel mappingLabel = new JLabel("" + glyph.getUnicode() + " " + character);		
		mappingLabel.setName("Character Mapping");
		return mappingLabel;
	}
	
	public JCheckBox createExportCheckBox(Glyph glyph) {
		JCheckBox export = new JCheckBox();
		export.setName("Export");
		if(glyph.isUnicodeDefined())
			export.setSelected(true);
		else
			export.setSelected(false);
		return export;
	}
	
	public JPanel createImagePanel(File f) {
		imagePanel = new JPanel();
		imagePanel.setBorder(BorderFactory.createTitledBorder("Preview"));
		imageLabel = this.createImageLabel(f);
		imagePanel.add(imageLabel);
		return imagePanel;
	}
	
	public JLabel createImageLabel(File f) {
		imageLabel = new JLabel("");
		imageLabel.setName("Preview");
		imageLabel.setSize(100, 100);
		imageLabel.setOpaque(true);
		imageLabel.setBackground(Color.WHITE);
		updateImageLabel(f);
		return imageLabel;
	}
	
	public void updateImageLabel(File f) {
		BufferedImage image = BufferedImageTranscoder.loadImage(f, 100, 100);
		if(image != null)
			imageLabel.setIcon(new ImageIcon(image));
		else
			imageLabel.setText("?");
	}

	public void setUnicode(String s) {
		glyph.setUnicode(s);
		mappingLabel.setText(s);
	}
	
	public boolean isSelected() {
		return export.isSelected();
	}
	
	/**
	 * @return the glyph
	 */
	public Glyph getGlyph() {
		return glyph;
	}

	/**
	 * @param glyph the glyph to set
	 */
	public void setGlyph(Glyph glyph) {
		this.glyph = glyph;
	}

	class SetUnicodeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			int cl[] = new int[]{33, 126, 161, 255};
			setUnicodeDialog = new JDialog();
			setUnicodeDialog.setSize(800, 800);
			JPanel contentPane = new JPanel(new BorderLayout());			
			setUnicodeDialog.setTitle("Select which character to map");
			ButtonGroup alternatives = new ButtonGroup();
			JScrollPane centerPanel = this.createCenterPanel(alternatives, cl);
			JPanel topPanel = this.createTopPanel(alternatives);
			contentPane.add(centerPanel, BorderLayout.CENTER);
			contentPane.add(topPanel, BorderLayout.NORTH);
			setUnicodeDialog.setContentPane(contentPane);
			setUnicodeDialog.setVisible(true);
		}
		
		public JScrollPane createCenterPanel(ButtonGroup alternatives, int cl[]) {
			int sum = cl[1] - cl[0] + cl[3] - cl[2];
			JPanel setUnicodePanel = new JPanel();
			setUnicodePanel.setLayout(new GridLayout((sum / 4) + (sum % 4), 4));
			for(int i = cl[0]; i <= cl[3]; i++) {
				if(i == (cl[1] + 1))
					i = cl[2];
				JPanel rowPanel = new JPanel(new GridLayout(1, 1));
				JRadioButton btn = new JRadioButton("" + (char) i + " " + CharacterLookup.numToUnicodeString(i));
				alternatives.add(btn);
				rowPanel.add(btn);
				setUnicodePanel.add(rowPanel);
			}
			JScrollPane setUnicodeScrollPane = new JScrollPane(setUnicodePanel);
			return setUnicodeScrollPane;
		}
		
		public JPanel createTopPanel(ButtonGroup alternatives) {
			JPanel topPanel = new JPanel();			
			JButton btnSelect = new JButton("Select");
			topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			topPanel.add(btnSelect);
			topPanel.setLayout(new GridLayout(1, 4));
			topPanel.add(current.createImageLabel(current.getFile()));
			topPanel.add(new JLabel("Which character is this?"));
			btnSelect.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Enumeration<AbstractButton> buttons = alternatives.getElements();
					int c = 0;
					while(buttons.hasMoreElements()) {
						JRadioButton btn = (JRadioButton) buttons.nextElement();
						if(btn.isSelected())
							c = (int) btn.getText().charAt(0);
					}
					if(c == current.getGlyph().getUnicodeNumber()) {
						setUnicodeDialog.dispose();
						return;
					}
					setUnicodeDialog.setVisible(false);
					export.setSelected(true);					
					if(current.isDuplicate()) {
						unmark();
					}
					centerPanel.removeMapping(current);
					current.getGlyph().setUnicode(CharacterLookup.numToUnicodeString(c));
					mappingLabel.setText(CharacterLookup.numToUnicodeString(c) + " " + (char) c);
					centerPanel.checkDuplicate(current);
					current.updateImageLabel(current.getFile());
					validate();
					setUnicodeDialog.dispose();
				}
			});
			return topPanel;
		}
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	public void markDuplicate() {
		this.setDuplicate(true);
		this.mappingLabel.setOpaque(true);
		this.mappingLabel.setBackground(Color.RED);
		this.repaint();
	}
	
	public void unmark() {
		this.setDuplicate(false);
		this.getDuplicatesLabel().setText("No Duplicates");
		this.mappingLabel.setOpaque(false);
		this.mappingLabel.setBackground(Color.WHITE);
		this.updateImageLabel(file);
		this.repaint();
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public int compareTo(GlyphPanel o) {
		return this.getNumber() - o.getNumber();
	}



	/**
	 * @return the duplicatesLabel
	 */
	public JLabel getDuplicatesLabel() {
		return duplicatesLabel;
	}



	/**
	 * @param duplicatesLabel the duplicatesLabel to set
	 */
	public void setDuplicatesLabel(JLabel duplicatesLabel) {
		this.duplicatesLabel = duplicatesLabel;
	}



	/**
	 * @return the isDuplicate
	 */
	public boolean isDuplicate() {
		return isDuplicate;
	}



	/**
	 * @param isDuplicate the isDuplicate to set
	 */
	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	/**
	 * @return the imagePanel
	 */
	public JPanel getImagePanel() {
		return imagePanel;
	}

	/**
	 * @param imagePanel the imagePanel to set
	 */
	public void setImagePanel(JPanel imagePanel) {
		this.imagePanel = imagePanel;
	}

	/**
	 * @return the imageLabel
	 */
	public JLabel getImageLabel() {
		return imageLabel;
	}

	/**
	 * @param imageLabel the imageLabel to set
	 */
	public void setImageLabel(JLabel imageLabel) {
		this.imageLabel = imageLabel;
	}
	
}
