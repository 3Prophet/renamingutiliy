package ch.mardmi.renamingutility.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTable;

import ch.mardmi.renamingutility.model.DirectoryContentModel;
import ch.mardmi.renamingutility.view.MainFrame;

/**
 * Singleton von HandlerHelper die alle Listener die bei Benutzer eingaben
 * aufrufen und auf diese weise gleich auf die Benutzer eingaben reagieren
 */
public class HandlerHelper {
	
	private static List<Integer> filesSelected;
	
	/**
	 * Singleton Instanz von HandlerHelper die alle Listener die bei Benutzer eingaben
	 * aufrufen und auf diese weise gleich auf die Benutzer eingaben reagieren
	 */
	private static HandlerHelper helper;
	
	/**
	 * Aktionen in der Anzeige ausführen
	 * @param gui Aktuellen Status der Oberfläche (inkl. aller Objekte)
	 */
	public void execute(MainFrame gui) {
		
		DirectoryContentModel model = gui.getDirectoryModel();
		List<Integer>selectedFiles =  filesSelected;
		/**int [] filesAsArray = gui.getFileTable().getSelectedRows();
		filesSelected = new ArrayList<Integer>();
		
		for (int i = 0; i < filesAsArray.length; i++) {
			filesSelected.add(filesAsArray[i]);
		}*/
		
		
		if (gui.getUseOptionAddPanel()) {
			if (!gui.getPrefixFieldContent().isEmpty()) {
				
				model.setPrefix(filesSelected, gui.getPrefixFieldContent(), gui);
				
			} 
			
			if (gui.getSuffixFieldContent().isEmpty()) {
				// do nothing
			} else {
				//model.setSuffix(filesSelected, gui.getSuffixFieldContent());
			}
			
			if (gui.getInsertFieldContent().isEmpty()) {
				// do nothing
			} else {
				//model.setInsert(filesSelected, gui.getInsertFieldContent());
			}
		} else {
			model.resetFileState();
		}
		reselect(selectedFiles, gui);
		
		if (gui.getUseOptionRemovePanel()) {
			if ((Integer) (gui.getFirstNSpinner().getValue()) != 0) {
				model.removeFirst(filesSelected, gui.getFirstSpinnerValue());
			}
			
			if ((Integer) (gui.getLastSpinner().getValue()) != 0 ) {
				model.removeLast(filesSelected, gui.getLastSpinnerValue());
			}

		}
		
	}
	
	public void reselect(List<Integer> selectedTableRows, MainFrame gui) {
		JTable fileTable = gui.getFileTable();
		for (int i: selectedTableRows) {
			fileTable.setRowSelectionInterval(i, i);
		}
	}
	
	/**
	 * Setzen Indizen von selektierten Reichen.
	 * @param indices Indizen von Reichen die in Datei Tabelle selektiert sind
	 */
	public static void setSelectedRowIndices(int[] indices) {
		HandlerHelper.filesSelected = Arrays.stream(indices).boxed().collect(Collectors.toList());
	}
	
	/**
	 * Methode die HandlerHelper Singleton ausgibt 
	 * @return
	 */
	public static HandlerHelper getHelper() {
		if (helper == null) {
			helper = new HandlerHelper();
		}
		return helper;
	}

}
