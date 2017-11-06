package view;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.ScriptCheckboxListener;
import controller.WriteScriptCheckboxListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class NorthPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1931505285368286964L;
	
	private JTextField glyphSizeTextField;

	private JLabel lblSelectAFolder;
	private JLabel lblFilesFoundNumber;
	private JLabel lblDestinationDirectoryPath;
	private JLabel lblSvgFilesFoundNumber;
	
	private File dir;
	private File destDir;
	private File[] files;

	private MainPanel mainPanel;
	private JLabel lblGlyphSize;
	private JPanel directoryPanel;
	private JPanel glyphSizePanel;
	private JPanel panel;
	private JCheckBox chckbxCreateScript;
	private JCheckBox chckbxExecuteScript;

	/**
	 * Create the panel.
	 * @param mainPanel 
	 */
	public NorthPanel(MainPanel mainPanel, JFrame grandparent) {
		setLayout(new GridLayout(3, 1, 0, 0));
		int bw = 10;
		this.setBorder(new EmptyBorder(bw, bw, bw ,bw));
		this.setMainPanel(mainPanel);
		
		directoryPanel = new JPanel();
		add(directoryPanel);
		directoryPanel.setLayout(new GridLayout(2, 2, 0, 0));
		for(JLabel l : createLabels()) {
			l.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			directoryPanel.add(l);
		}
		
		glyphSizePanel = new JPanel();
		add(glyphSizePanel);
		glyphSizePanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblGlyphSize = new JLabel("Glyph Size:");
		glyphSizePanel.add(lblGlyphSize);
		lblGlyphSize.setHorizontalAlignment(SwingConstants.CENTER);
		glyphSizeTextField = new JTextField("2048");
		glyphSizePanel.add(glyphSizeTextField);
		glyphSizeTextField.setHorizontalAlignment(SwingConstants.CENTER);
		glyphSizeTextField.setColumns(10);
		
		panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		chckbxCreateScript = new JCheckBox("Create Script");
		panel.add(chckbxCreateScript);
		

		chckbxExecuteScript = new JCheckBox("Execute Script");
		chckbxExecuteScript.setEnabled(false);
		chckbxCreateScript.addActionListener(new ScriptCheckboxListener(chckbxExecuteScript, this.getMainPanel().getSouthPanel().getBtnResizeItems()));
		chckbxExecuteScript.addActionListener(new WriteScriptCheckboxListener(chckbxCreateScript, this.getMainPanel().getSouthPanel().getBtnResizeItems()));
		panel.add(chckbxExecuteScript);
	}
	
	public JLabel[] createLabels() {
		lblSelectAFolder = new JLabel("No Folder Selected");
		lblFilesFoundNumber = new JLabel("0");
		lblDestinationDirectoryPath = new JLabel("No Folder Selected");
		lblSvgFilesFoundNumber = new JLabel("0");
		return new JLabel[]{lblSelectAFolder, lblFilesFoundNumber, lblDestinationDirectoryPath, lblSvgFilesFoundNumber};
	}
	
	/**
	 * @return the lblSelectAFolder
	 */
	public JLabel getLblSelectAFolder() {
		return lblSelectAFolder;
	}
	/**
	 * @param lblSelectAFolder the lblSelectAFolder to set
	 */
	public void setLblSelectAFolder(JLabel lblSelectAFolder) {
		this.lblSelectAFolder = lblSelectAFolder;
	}

	public File[] getFiles() {
		return files;
	}
	public void setFiles(File[] files) {
		this.files = files;
	}
	/**
	 * @return the lblFilesFoundNumber
	 */
	public JLabel getLblFilesFoundNumber() {
		return lblFilesFoundNumber;
	}
	/**
	 * @param lblFilesFoundNumber the lblFilesFoundNumber to set
	 */
	public void setLblFilesFoundNumber(JLabel lblFilesFoundNumber) {
		this.lblFilesFoundNumber = lblFilesFoundNumber;
	}

	/**
	 * @return the lblSvgFilesFoundNumber
	 */
	public JLabel getLblSvgFilesFoundNumber() {
		return lblSvgFilesFoundNumber;
	}
	/**
	 * @param lblSvgFilesFoundNumber the lblSvgFilesFoundNumber to set
	 */
	public void setLblSvgFilesFoundNumber(JLabel lblSvgFilesFoundNumber) {
		this.lblSvgFilesFoundNumber = lblSvgFilesFoundNumber;
	}
	public MainPanel getMainPanel() {
		return mainPanel;
	}
	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	public File getDir() {
		return dir;
	}
	public void setDir(File dir) {
		this.dir = dir;
		try {
			lblSelectAFolder.setText("Source Directory: " + dir.getCanonicalPath().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public File getDestDir() {
		return destDir;
	}
	public void setDestDir(File destDir) {
		this.destDir = destDir;
		try {
			lblDestinationDirectoryPath.setText(destDir.getCanonicalPath().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	//059280
	//9fbq5h97ei8
	//192.168.8.1
	/**
	 * @return the lblDestinationDirectoryPath
	 */
	public JLabel getLblDestinationDirectoryPath() {
		return lblDestinationDirectoryPath;
	}
	/**
	 * @param lblDestinationDirectoryPath the lblDestinationDirectoryPath to set
	 */
	public void setLblDestinationDirectoryPath(JLabel lblDestinationDirectoryPath) {
		this.lblDestinationDirectoryPath = lblDestinationDirectoryPath;
	}
	/**
	 * @return the glyphSizeTextField
	 */
	public JTextField getGlyphSizeTextField() {
		return glyphSizeTextField;
	}
	/**
	 * @param glyphSizeTextField the glyphSizeTextField to set
	 */
	public void setGlyphSizeTextField(JTextField glyphSizeTextField) {
		this.glyphSizeTextField = glyphSizeTextField;
	}

}
