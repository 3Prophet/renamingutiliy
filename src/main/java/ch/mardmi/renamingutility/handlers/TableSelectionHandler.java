package ch.mardmi.renamingutility.handlers;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Aktualisiert Status-Modelle, wenn einige Reichen von Datei-Tabelle ausgewählt werden.
 * 
 * {@link ch.mardmi.renamingutility.model.StatusModel}
 *
 */
public class TableSelectionHandler extends AbstractHandler implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int selectedRowsCount = gui.getFileTable().getSelectedRowCount();
		gui.getStatusModel().rowsSelected(selectedRowsCount);
	}

}
