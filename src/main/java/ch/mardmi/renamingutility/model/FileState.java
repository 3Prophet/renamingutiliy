package ch.mardmi.renamingutility.model;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import ch.mardmi.renamingutility.command.FileNameEditor;
import ch.mardmi.renamingutility.view.MainFrame;

/**
 * Implementiert Zustand für die Datei Tabelle Einträge,
 * deren Modelle ist mithilfe von
 * @link {ch.mardmi.renamingutility.model.DirectoryContentModel}
 * implementiert
 * 
 * @author Dmitry Logvinovich
 *
 */
public class FileState {
	
	/**
	 * Aktueller Dateiname
	 */
	private Path currentFileState;
	
	/**
	 * (Potenziell)Neuer Dateiname
	 */
	private Path newFileState;
	
	/**
	 * Während der Initialisierung Phase sind aktueller und 
	 * neuer Dateinamen gleichgesetzt
	 * 
	 * @param currentFileState Aktuelle Dateiname
	 */
	public FileState(File currentFileState) {
		this.currentFileState = currentFileState.toPath();
		this.newFileState = this.currentFileState;
	}
	
	/**
	 * Alten Dateinamen auslesen
	 * @return dateiname 
	 */
	public File getCurrentFileState() {
		return currentFileState.toFile();
	}
	
	/** 
	 * Neuen Dateinamen 'auslesen'
	 * @return
	 */
	public File getNewFileState() {
		return newFileState.toFile();
	}
	
	/**
	 * Datei umbenennen
	 * @throws Exception
	 */
	public void changeNewFileState() throws Exception {
		if (!(currentFileState.compareTo(newFileState) == 0)) {
			
			Files.move(currentFileState, currentFileState.resolveSibling(newFileState));
			currentFileState = newFileState;
			
		}
	}
	
	/** 
	 * Dateinamen zurücksetzen
	 */
	public void resetFileState() {
		newFileState = currentFileState;
	}
	
	/**
	 * Aktualisiert currentFileState mit dem Resultat von Nameaenderung
	 * bei Dateiname Editoren.
	 * 
	 * @param editors
	 */
	public void changeState(List<FileNameEditor> editors) {
		String filePath =  currentFileState.getParent().toString();
		String fileName = FilenameUtils.getBaseName(currentFileState.toAbsolutePath().toString());
		String extension = FilenameUtils.getExtension(currentFileState.toAbsolutePath().toString());
		
		for (FileNameEditor e: editors) {
			fileName = e.editName(fileName);
		}
		if (!extension.isEmpty()) {
			fileName += "."+ extension;
		}
		newFileState = Paths.get(filePath, fileName);
	}
}
