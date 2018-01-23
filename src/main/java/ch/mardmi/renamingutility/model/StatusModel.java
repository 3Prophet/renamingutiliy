package ch.mardmi.renamingutility.model;

import java.util.ArrayList;
import java.util.List;

import ch.mardmi.renamingutility.handlers.StatusModelListener;

/**
 * Status-Modell für die Anzeige des Verzeichnis-Baumes
 * sowie des Inhaltes für das ausgewählte Verzeichnis
 * @author Dmitry Logvinovich
 *
 */
public class StatusModel {
	
	private List<StatusModelListener> listeners;
	
	/**
	 * Anzahl der Dateien im ausgewählten Ordner
	 */
	private int filesInDir;
	
	/**
	 * Anzahl ausgewählter Zeilen/Dateien
	 */
	private int rowsSelected;
	
	public StatusModel() {
		listeners = new ArrayList<StatusModelListener>();
		filesInDir = 0;
		rowsSelected = 0;
	}
	
	/**
	 * 
	 * @param listener Relevante Listener zum Modell
	 */
	public void addChangeListener(StatusModelListener listener) {
		listeners.add(listener);	
	}

	/**
	 * 
	 * @param nrFiles Anzahl Dateien im Verzeichnis
	 */
	public void filesInDirectoryChanged(int nrFiles) {
		filesInDir = nrFiles;
		notifyListeners();
	}

	/**
	 * 
	 * @param selectedRowsCount Anzahl ausgewählte Dateien im Verzeichnis
	 */
	public void rowsSelected(int selectedRowsCount) {
		rowsSelected = selectedRowsCount;
		notifyListeners();
	}
	
	@Override
	public String toString() {
		return "" + filesInDir + " Files(" + rowsSelected + " Selected)"; 
	}

	private void notifyListeners() {
		for (StatusModelListener l: listeners) {
			l.statusModelChanged(this);
		}
	}

	public List<StatusModelListener> getListeners() {
		return listeners;
	}

}
