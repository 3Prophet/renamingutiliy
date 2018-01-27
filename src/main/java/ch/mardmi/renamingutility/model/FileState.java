package ch.mardmi.renamingutility.model;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

import ch.mardmi.renamingutility.view.MainFrame;

public class FileState {
	
	private Path currentFileState;
	
	private Path newFileState;
	
	// Path hat eine Methode getFilename
	// sowie Path.getParent() um das Verzeichnis auszulesen
	
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
		if (currentFileState.compareTo(newFileState) == 0)
		currentFileState = newFileState;
		Files.move(currentFileState, newFileState);
	}
	
	/** 
	 * neuen Dateinamen zurücksetzen
	 */
	public void resetFileState() {
		newFileState = currentFileState;
	}
	
	/**
	 * Dateiname um Prefix ergänzen
	 * @param prefix
	 */
	synchronized public void setPrefix(String prefix) {
		newFileState = Paths.get(
				currentFileState.getParent().toString(), prefix + currentFileState.getFileName().toString());
	}
	
	/**
	 * Dateinamen um Suffix ergänzen (nicht die Extension!)
	 * @param suffix
	 */
	public void setSuffix(String suffix) {
		String filePath =  currentFileState.getParent().toString();
		
		String fileName = FilenameUtils.getBaseName(currentFileState.toAbsolutePath().toString());
		String extension = FilenameUtils.getExtension(currentFileState.toAbsolutePath().toString());
		
		String suffixedName = fileName + "suffix";
		if (!extension.isEmpty()) {
			suffixedName += "."+ extension;
		}
		newFileState = Paths.get(filePath, suffixedName);
	}
	
	/**
	 * Dateinamen an der angegebenen Position ergänzen
	 * @param insert
	 */
	public void setInsert(String insert) {
		
	}
	
	/**
	 * Die ersten n Zeichen im Dateinamen entfernen
	 * @param remove
	 */
	public void removeFirst(int remove) {
		
	}
	
	/**
	 * Die letzten n Zeichen im Dateinamen entfernen
	 * @param remove
	 */
	public void removeLast(int remove) {
		
	}
}
