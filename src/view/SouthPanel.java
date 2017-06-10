package view;

import javax.swing.JPanel;

import controller.FolderChooserListener;
import controller.ResizeItemsListener;
import controller.WriteScriptListener;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class SouthPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3876461461631808997L;
	private MainPanel mainPanel;
	private JButton btnWriteScript;
	private JButton btnResizeItems;
	private JFrame parentFrame;
	private JButton btnSelectDirectory;

	/**
	 * Create the panel.
	 * @param parent 
	 */
	public SouthPanel(MainPanel mainPanel, JFrame parent) {
		setLayout(new GridLayout(0, 3, 0, 0));
		this.mainPanel = mainPanel;
		this.setParentFrame(parent);
		btnWriteScript = new JButton("Write Script");
		btnSelectDirectory = new JButton("Select Directory");
		add(btnSelectDirectory);
		add(btnWriteScript);
		btnResizeItems = new JButton("Resize Items");
		add(btnResizeItems);
	}

	public void setActionListeners() {
		this.btnWriteScript.addActionListener(new WriteScriptListener(mainPanel));
		this.btnSelectDirectory.addActionListener(new FolderChooserListener(mainPanel.getNorthPanel(), mainPanel.getParentFrame()));
		this.btnResizeItems.addActionListener(new ResizeItemsListener(mainPanel.getNorthPanel(), mainPanel.getParentFrame()));
	}
	
	/**
	 * @return the btnWriteScript
	 */
	public JButton getBtnWriteScript() {
		return btnWriteScript;
	}

	/**
	 * @param btnWriteScript the btnWriteScript to set
	 */
	public void setBtnWriteScript(JButton btnWriteScript) {
		this.btnWriteScript = btnWriteScript;
	}

	/**
	 * @return the btnResizeItems
	 */
	public JButton getBtnResizeItems() {
		return btnResizeItems;
	}

	/**
	 * @param btnResizeItems the btnResizeItems to set
	 */
	public void setBtnResizeItems(JButton btnResizeItems) {
		this.btnResizeItems = btnResizeItems;
	}

	/**
	 * @return the mainPanel
	 */
	public MainPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * @param mainPanel the mainPanel to set
	 */
	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JFrame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

}
