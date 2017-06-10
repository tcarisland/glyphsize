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
	private JFrame parentFrame;
	private CenterPanel centerPanel;
	private NorthPanel northPanel;
	private SouthPanel southPanel;
	
	/**
	 * Create the panel.
	 */
	public MainPanel(JFrame parentFrame) {
		this.setParentFrame(parentFrame);
		this.setLayout(new BorderLayout());
		setCenterPanel(new CenterPanel(this, parentFrame));
		setSouthPanel(new SouthPanel(this, parentFrame));
		setNorthPanel(new NorthPanel(this, parentFrame));
		southPanel.setActionListeners();
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel,  BorderLayout.SOUTH);
	}

	public void resetCharacters() {
		GlyphPanel.resetCounter();
		this.getCenterPanel().removeCharacters();
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

	/**
	 * @return the parentFrame
	 */
	public JFrame getParentFrame() {
		return parentFrame;
	}

	/**
	 * @param parentFrame the parentFrame to set
	 */
	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public SouthPanel getSouthPanel() {
		return southPanel;
	}

	public void setSouthPanel(SouthPanel southPanel) {
		this.southPanel = southPanel;
	}

}
