package ch.mardmi.renamingutility.handlers;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ch.mardmi.renamingutility.view.MainFrame;

/**
 * Aktualiziert Status Modelle, wann einige Reichen von Datei Tabelle selektiert werden.
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
