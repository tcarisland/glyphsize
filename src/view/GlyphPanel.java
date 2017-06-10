package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

	CenterPanel parent;
	Glyph glyph;
	JCheckBox export;
	JLabel unicodeLabel;
	JLabel imageLabel;
	JLabel numberLabel;
	JLabel filenameLabel;
	JLabel duplicatesLabel;
	JLabel heightLabel;
	JLabel widthLabel;
	JPanel dimensionsPanel;
	JDialog setUnicodeDialog;
	JButton setUnicodeButton;
	JPanel contentPane;
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
		this.parent = parent;
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		this.file = f;
		glyph = new Glyph(f, table);
		contentPane = this.createContentPane(f, glyph);
		this.unmark();
		add(contentPane, BorderLayout.CENTER);
		add(imageLabel, BorderLayout.EAST);
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
		numberLabel = createNumberLabel(number);
		imageLabel = createImageLabel(f);
		export = createExportCheckBox(glyph);
		filenameLabel = this.createFilenameLabel(f.getName());
		unicodeLabel = createUnicodeLabel(glyph);
		setUnicodeButton = createSetUnicodeButton();
		duplicatesLabel = this.createDuplicatesLabel();
		dimensionsPanel = this.createDimensionsPanel(f);
		return new JComponent[]{numberLabel, imageLabel, export, filenameLabel, unicodeLabel, setUnicodeButton, duplicatesLabel, dimensionsPanel};
		//centerComponents();
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
	
	private JLabel createNumberLabel(int number2) {
		JLabel numberLabel = new JLabel("" + number2);
		numberLabel.setName("Number");
		return numberLabel;
	}

	public void centerComponents() {
		export.setHorizontalAlignment(SwingConstants.CENTER);
		filenameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		unicodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		setUnicodeButton.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public JPanel populateContentPane(JPanel contentPane, JComponent[] components) {
		for(JComponent jc : components) {
			JPanel wrapper = new JPanel(new BorderLayout());
			wrapper.add(jc, BorderLayout.CENTER);
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
	
	
	public JLabel createUnicodeLabel(Glyph glyph) {
		JLabel unicodeLabel = new JLabel(glyph.getUnicode());		
		unicodeLabel.setName("Unicode");
		return unicodeLabel;
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
	
	public JLabel createImageLabel(File f) {
		JLabel imageLabel = new JLabel("");
		imageLabel.setName("Preview");
		imageLabel.setSize(100, 100);
		imageLabel.setOpaque(true);
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setIcon(new ImageIcon(BufferedImageTranscoder.loadImage(f, 100, 100)));
		return imageLabel;
	}

	public void setUnicode(String s) {
		glyph.setUnicode(s);
		unicodeLabel.setText(s);
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
			int a, b, c, d, i, sum;
			a = 33;
			b = 126;
			c = 161;
			d = 255;
			sum = (b - a) + (d - c) + 2;
			setUnicodeDialog = new JDialog();
			JPanel contentPane = new JPanel();
			contentPane.setLayout(new BorderLayout());
			setUnicodeDialog.setTitle("Select which character to map");
			setUnicodeDialog.setSize(getMaximumSize());
			JPanel setUnicodePanel = new JPanel();
			int cols = 4;
			int mod = sum % cols;
			setUnicodePanel.setLayout(new GridLayout((sum / cols) + mod, cols));
			for(i = a; i <= b; i++) {
				JPanel rowPanel = new JPanel();
				rowPanel.setBorder(BorderFactory.createEtchedBorder());
				rowPanel.setLayout(new GridLayout(1, 2));
				String s1 = CharacterLookup.numToUnicodeString(i);
				String s2 = "" + (char) i;
				JButton btn = new JButton(s2);
				btn.addActionListener(new PickUnicodeListener(i));
				rowPanel.add(new JLabel(s1));
				rowPanel.add(btn);
				setUnicodePanel.add(rowPanel);
			}
			for(i = c; i <= d; i++) {
				JPanel rowPanel = new JPanel();
				rowPanel.setBorder(BorderFactory.createEtchedBorder());
				rowPanel.setLayout(new GridLayout(1, 2));
				String s1 = CharacterLookup.numToUnicodeString(i);
				String s2 = "" + (char) i;
				JButton btn = new JButton(s2);
				btn.addActionListener(new PickUnicodeListener(i));
				rowPanel.add(new JLabel(s1));
				rowPanel.add(btn);
				setUnicodePanel.add(rowPanel);
			}
			JScrollPane setUnicodeScrollPane = new JScrollPane(setUnicodePanel);
			JPanel topPanel = new JPanel();			
			topPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
			topPanel.setLayout(new GridLayout(1, 2));
			topPanel.add(new JLabel("Which character is this?"));
			topPanel.add(imageLabel);
			contentPane.add(setUnicodeScrollPane, BorderLayout.CENTER);
			contentPane.add(topPanel, BorderLayout.NORTH);
			setUnicodeDialog.setContentPane(contentPane);
			setUnicodeDialog.setVisible(true);
		}
	}
	
	class PickUnicodeListener implements ActionListener {

		int number;
		
		public PickUnicodeListener(int number) {
			this.number = number;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			setUnicodeDialog.setVisible(false);
			export.setSelected(true);
			String unicode = CharacterLookup.numToUnicodeString(number);
			unicodeLabel.setText(unicode);
			if(current.isDuplicate())
				unmark();
			parent.removeMapping(current);
			glyph.setUnicode(unicode);
			imageLabel.setIcon(new ImageIcon(BufferedImageTranscoder.loadImage(file, 100, 100)));
			add(imageLabel, BorderLayout.EAST);
			parent.checkDuplicate(current);
			validate();
			setUnicodeDialog.dispose();
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
		this.unicodeLabel.setOpaque(true);
		this.unicodeLabel.setBackground(Color.RED);
		this.repaint();
	}
	
	public void unmark() {
		this.setDuplicate(false);
		this.getDuplicatesLabel().setText("No Duplicates");
		this.unicodeLabel.setOpaque(false);
		this.unicodeLabel.setBackground(Color.WHITE);
		this.imageLabel.setIcon(new ImageIcon(BufferedImageTranscoder.loadImage(file, 100, 100)));
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
		numberLabel.setText("" + number);
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
	
}
