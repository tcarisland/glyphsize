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
	NorthPanel parent;	
	
	public FolderChooserListener(NorthPanel parent, JFrame grandparent) {
		this.parent = parent;
		this.grandparent = grandparent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JLabel lblSelectAFolder = parent.getLblSelectAFolder();
		// TODO Auto-generated method stub
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
				parent.setDir(dir);
				String dirname = dir.getAbsolutePath() + "/resized";
				File destDir = new File(dirname);
				destDir.mkdir();
				parent.setDestDir(destDir);

				File[] fileList = dir.listFiles();
				ArrayList<File> updatedFileList = new ArrayList<File>();
				for(File f : fileList)
					if(f.toString().toLowerCase().endsWith(".svg"))
						updatedFileList.add(f);
				File[] updatedFileListArray = updatedFileList.toArray(new File[updatedFileList.size()]);
				parent.setFiles(updatedFileListArray);
				parent.getLblFilesFoundNumber().setText("" + fileList.length);
				parent.getLblSvgFilesFoundNumber().setText("" + updatedFileList.size());
				parent.getMainPanel().updateFileList(updatedFileListArray);
		} else {
			lblSelectAFolder.setText("No Directory Selected");
		}
	}

}
