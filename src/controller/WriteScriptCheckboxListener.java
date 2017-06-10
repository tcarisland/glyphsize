package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class WriteScriptCheckboxListener implements ActionListener {

	JButton btnResizeItems;
	JCheckBox chckbxCreateScript;
	
	public WriteScriptCheckboxListener(JCheckBox chckbxCreateScript, JButton btnResizeItems) {
		this.chckbxCreateScript = chckbxCreateScript;
		this.btnResizeItems = btnResizeItems;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JCheckBox box = (JCheckBox) e.getSource();
		if(box.isSelected()) {
			btnResizeItems.setText("Resize and Execute Script");;
		} else if(chckbxCreateScript.isSelected()) {
			btnResizeItems.setText("Resize and Write Script");
		} else {
			btnResizeItems.setText("Resize Items");
		}
	}

}
