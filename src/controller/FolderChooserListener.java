package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import view.NorthPanel;

public class FolderChooserListener implements ActionListener {

	JFrame grandparent;
	NorthPanel northpanel;	
	
	public FolderChooserListener(NorthPanel parent, JFrame grandparent) {
		this.northpanel = parent;
		this.grandparent = grandparent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		northpanel.getMainPanel().resetCharacters();
		JLabel lblSelectAFolder = northpanel.getLblSelectAFolder();
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setCurrentDirectory(new java.io.File("~"));
		folderChooser.setDialogTitle("Choose Directory");
		folderChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		folderChooser.setAcceptAllFileFilterUsed(true);
		if(folderChooser.showOpenDialog(grandparent) == JFileChooser.APPROVE_OPTION) {
				File dir;
				if(folderChooser.getSelectedFile().isDirectory()) {
					dir = folderChooser.getSelectedFile();
				} else {
					dir = folderChooser.getCurrentDirectory();
				}
				northpanel.setDir(dir);
				String dirname = dir.getAbsolutePath() + "/resized";
				File destDir = new File(dirname);
				destDir.mkdir();
				northpanel.setDestDir(destDir);

				File[] fileList = dir.listFiles();
				ArrayList<File> updatedFileList = new ArrayList<File>();
				for(File f : fileList)
					if(f.toString().toLowerCase().endsWith(".svg"))
						updatedFileList.add(f);
				File[] updatedFileListArray = updatedFileList.toArray(new File[updatedFileList.size()]);
				northpanel.setFiles(updatedFileListArray);
				northpanel.getLblFilesFoundNumber().setText("" + fileList.length);
				northpanel.getLblSvgFilesFoundNumber().setText("" + updatedFileList.size());
				northpanel.getMainPanel().updateFileList(updatedFileListArray);
		} else {
			lblSelectAFolder.setText("No Directory Selected");
		}
	}

}
