package ch.mardmi.renamingutility.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.mardmi.renamingutility.model.DirectoryContentModel;
import ch.mardmi.renamingutility.view.MainFrame;

public class HandlerHelper {
	
	private static List<Integer> filesSelected;
	
	public static void execute(MainFrame gui) {
		DirectoryContentModel model = gui.getDirectoryModel();
		int [] filesAsArray = gui.getFileTable().getSelectedRows();
		filesSelected = new ArrayList<Integer>();
		
		for (int i = 0; i < filesAsArray.length; i++) {
			filesSelected.add(filesAsArray[i]);
		}
		
		if (gui.getUseOptionAddPanel()) {
			if (gui.getPrefixFieldContent().isEmpty()) {
				// do nothing
			} else {
				model.setPrefix(filesSelected, gui.getPrefixFieldContent());
			}
			
			if (gui.getSuffixFieldContent().isEmpty()) {
				// do nothing
			} else {
				model.setSuffix(filesSelected, gui.getSuffixFieldContent());
			}
			
			if (gui.getInsertFieldContent().isEmpty()) {
				// do nothing
			} else {
				model.setInsert(filesSelected, gui.getInsertFieldContent());
			}
		}
		
		if (gui.getUseOptionRemovePanel()) {
			if ((Integer) (gui.getFirstNSpinner().getValue()) == 0) {
				// do nothing
			} else {
				
			}
			
			if ((Integer) (gui.getLastSpinner().getValue()) == 0 ) {
				// do nothing
			} else {
				
			}

		}
		
	}
	
	public static void setSelectedFiles(List<Integer> filesSelected) {
		HandlerHelper.filesSelected = filesSelected;
	}

}
