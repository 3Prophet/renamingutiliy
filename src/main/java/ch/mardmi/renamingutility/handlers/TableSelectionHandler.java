package ch.mardmi.renamingutility.handlers;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Gibt eine Liste von Indices von ausgewählten Datei-Tabelle Reichen an HandlerHelper weiter
 * {@see ch.mardmi.renamingutility.handlers.HandlerHelper#setSelectedRowIndices(int[])},
 * wenn einige Reichen von Datei-Tabelle ausgewählt werden.
 * 
 * @author Dmitry Logvinovich
 */
public class TableSelectionHandler extends AbstractHandler implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		if (!e.getValueIsAdjusting()) {
			int selectedRowsCount = gui.getFileTable().getSelectedRowCount();
			gui.getStatusModel().rowsSelected(selectedRowsCount);
			
			HandlerHelper.setSelectedRowIndices(gui.getFileTable().getSelectedRows());
		}
	}

}
