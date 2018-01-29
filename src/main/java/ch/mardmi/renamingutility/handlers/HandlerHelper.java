package ch.mardmi.renamingutility.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTable;

import ch.mardmi.renamingutility.command.AddToNameEditor;
import ch.mardmi.renamingutility.command.FileNameEditor;
import ch.mardmi.renamingutility.command.RemoveFromNameEditor;
import ch.mardmi.renamingutility.model.DirectoryContentModel;
import ch.mardmi.renamingutility.view.MainFrame;

/**
 * Singleton von HandlerHelper die alle Listener die bei Benutzer eingaben
 * aufrufen und auf diese weise gleich auf die Benutzer eingaben reagieren
 */
public class HandlerHelper {
	
	private static List<Integer> filesSelected = Arrays.asList();
	
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

		List<Object> additionConfiguration = gui.getAdditionPanelConfiguration();
		List<Object> removalConfiguration = gui.getRemovePanelConfiguration();
		
		FileNameEditor addToNameEditor = new AddToNameEditor( (boolean) additionConfiguration.get(0),
							(String) additionConfiguration.get(1),
							(String) additionConfiguration.get(2),
							(String) additionConfiguration.get(3),
							(Integer) additionConfiguration.get(4)
				);
		FileNameEditor removeFromNameEditor = new RemoveFromNameEditor(
									(boolean) removalConfiguration.get(0),
									(Integer) removalConfiguration.get(1),
									(Integer) removalConfiguration.get(2)
									);
		
		List<FileNameEditor> fileNameEditors = Arrays.asList(
															addToNameEditor,
															removeFromNameEditor);
		model.changeFileStates(filesSelected, fileNameEditors);
		reselect(selectedFiles, gui);

	}
	
	public void reselect(List<Integer> selectedTableRows, MainFrame gui) {
		JTable fileTable = gui.getFileTable();
		for (int i: selectedTableRows) {
			fileTable.getSelectionModel().addSelectionInterval(i, i);
		}
	}
	
	/**
	 * Setzen Indizen von selektierten Reichen.
	 * @param indices Indizen von Reichen die in Datei Tabelle selektiert sind
	 */
	public static void setSelectedRowIndices(int[] indices) {
		HandlerHelper.filesSelected = Arrays.stream(indices).boxed().collect(Collectors.toList());
	}
	
	public static List<Integer> getFilesSelected() {
		return filesSelected;
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
