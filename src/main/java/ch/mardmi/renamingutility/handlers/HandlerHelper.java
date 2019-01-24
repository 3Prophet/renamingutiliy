package ch.mardmi.renamingutility.handlers;

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
 * 
 * @author Dmitry Logvinovich
 */
public class HandlerHelper {
	
	/**
	 * Eine liste von Indizen von selektierten Dateien.
	 */
	private static List<Integer> filesSelected = Arrays.asList();
	
	/**
	 * Singleton Instanz von HandlerHelper die alle Listener die bei Benutzer eingaben
	 * aufrufen und auf diese weise gleich auf die Benutzer eingaben reagieren
	 */
	private static HandlerHelper helper;
	
	/**
	 * Konfiguriert Datei Editoren {@see ch.mardmi.renamingutility.command.FileNameEditor}
	 * und ruft  {@link ch.mardmi.renamingutility.model.DirectoryContentModel#changeFileStates(List, List)},
	 * die wird Datei Umbenennung durchführen.
	 * 
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
	
	/**
	 * Soll aufgeruft werden nach dem {@see javax.swing.table.AbstractTableModel#fireTableDataChanged()}
	 * ausgeführ wird, da die Letzte Methode anuliert Datei Selection in JTable.
	 * 
	 * @param selectedTableRows Eine liste von Indizen von selektierten Dateien
	 * @param gui GBO fenster
	 */
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
	
	/**
	 * Gibt Indizen von gewählten Dateine weiter.
	 * @return
	 */
	public static List<Integer> getFilesSelected() {
		return filesSelected;
	}
	
	/**
	 * Methode die HandlerHelper Singleton weitergibt. 
	 * @return
	 */
	public static HandlerHelper getHelper() {
		if (helper == null) {
			helper = new HandlerHelper();
		}
		return helper;
	}

}
