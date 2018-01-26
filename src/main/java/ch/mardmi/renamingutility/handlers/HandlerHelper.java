package ch.mardmi.renamingutility.handlers;

import java.util.List;

import ch.mardmi.renamingutility.view.MainFrame;

public class HandlerHelper {
	
	private MainFrame gui;
	
	private static List<Integer> filesSelected;
	
	public HandlerHelper(MainFrame gui) {
		this.gui = gui;
	}
	
	public void execute() {
		DirectoryContentModel model = gui.getDirectoryContentModel();
		model.setPrefix(filesSelected);
	}
	
	public static void setSelectedFiles(List<Integer> filesSelected) {
		HandlerHelper.filesSelected = filesSelected;
	}

}
