package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class ScriptCheckboxListener implements ActionListener {

	JCheckBox chckbxExecuteScript;
	JButton runButton;
	
	public ScriptCheckboxListener(JCheckBox chckbxExecuteScript, JButton runButton) {
		this.chckbxExecuteScript = chckbxExecuteScript;
		this.runButton = runButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JCheckBox writeScript = (JCheckBox) e.getSource();
		if(writeScript.isSelected()) {
			this.chckbxExecuteScript.setEnabled(true);
			runButton.setText("Resize and Write Script");
		} else {
			runButton.setText("Resize Items");
			this.chckbxExecuteScript.setSelected(false);
			this.chckbxExecuteScript.setEnabled(false);
		}
	}

}
