package ch.mardmi.renamingutility.model;

import java.io.File;
import java.nio.file.Path;

public class FileState {
	
	private Path currentFileState;
	
	private Path newFileState;
	
	public FileState(File currentFileState) {
		this.currentFileState = currentFileState.toPath();
		this.newFileState = this.currentFileState;
	}
	
	public File getCurrentFileState() {
		return currentFileState.toFile();
	}
	
	public File getNewFileState() {
		return newFileState.toFile();
	}
	
	public void changeNewFileState() {
		// TO-DO:
	}
	
	public void resetFileState() {
		newFileState = currentFileState;
	}
}
