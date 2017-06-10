package view;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3845633522635847677L;
	JFrame parent;
	private CenterPanel centerPanel;
	private NorthPanel northPanel;
	
	/**
	 * Create the panel.
	 */
	public MainPanel(JFrame parent) {
		this.parent = parent;
		this.setLayout(new BorderLayout());
		centerPanel = new CenterPanel(this, parent);
		northPanel = new NorthPanel(this, parent);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
	}

	public void updateFileList(File[] fileList) {
		centerPanel.updateFileListArea(fileList);
	}

	/**
	 * @return the centerPanel
	 */
	public CenterPanel getCenterPanel() {
		return centerPanel;
	}

	/**
	 * @param centerPanel the centerPanel to set
	 */
	public void setCenterPanel(CenterPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	/**
	 * @return the northPanel
	 */
	public NorthPanel getNorthPanel() {
		return northPanel;
	}

	/**
	 * @param northPanel the northPanel to set
	 */
	public void setNorthPanel(NorthPanel northPanel) {
		this.northPanel = northPanel;
	}

}
