package ch.mardmi.renamingutility.model;

import java.util.ArrayList;
import java.util.List;

import ch.mardmi.renamingutility.handlers.StatusModelListener;

public class StatusModel {
	
	private List<StatusModelListener> listeners;
	
	/**
	 * Anzahl der Dateien im Ordner
	 */
	private int filesInDir;
	
	/**
	 * Anzahl von gewählten Reihen
	 */
	private int rowsSelected;
	
	public StatusModel() {
		listeners = new ArrayList<StatusModelListener>();
		filesInDir = 0;
		rowsSelected = 0;
	}
	
	public void addChangeListener(StatusModelListener listener) {
		listeners.add(listener);	
	}

	public void filesInDirectoryChanged(int nrFiles) {
		filesInDir = nrFiles;
		notifyListeners();

	}

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
