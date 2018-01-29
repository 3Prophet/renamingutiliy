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
 * 
 * @author Dmitry Logvinovich
 *
 */
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
		if (!(currentFileState.compareTo(newFileState) == 0)) {
			
			Files.createFile(newFileState);
			Files.move(currentFileState, newFileState);
			currentFileState = newFileState;
			
		}
	}
	
	/** 
	 * neuen Dateinamen zurücksetzen
	 */
	public void resetFileState() {
		newFileState = currentFileState;
	}

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
