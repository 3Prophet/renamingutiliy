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
		
		if (gui.addPanel(selected)) {
			if (gui.getPrefix().notEmpty()) {
				model.setPrefix(filesSelected);
			}
			if gui.getSuffix.. {
				model.setSufix(filesSelecte);
			}
			
			
			
			
		}
		
		if (gui.removePanelSelected() {
			if 
		})
	}
	
	public static void setSelectedFiles(List<Integer> filesSelected) {
		HandlerHelper.filesSelected = filesSelected;
	}

}
