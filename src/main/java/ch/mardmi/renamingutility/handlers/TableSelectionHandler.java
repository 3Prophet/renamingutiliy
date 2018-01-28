package ch.mardmi.renamingutility.handlers;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import ch.mardmi.renamingutility.model.DirectoryContentModel;

/**
 * Aktualisiert Status-Modelle, wenn einige Reichen von Datei-Tabelle ausgewählt werden.
 * 
 * {@link ch.mardmi.renamingutility.model.StatusModel}
 *
 */
public class TableSelectionHandler extends AbstractHandler implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		if (!e.getValueIsAdjusting()) {
			int selectedRowsCount = gui.getFileTable().getSelectedRowCount();
			gui.getStatusModel().rowsSelected(selectedRowsCount);
			
			HandlerHelper.setSelectedRowIndices(gui.getFileTable().getSelectedRows());
			//HandlerHelper.getHelper().execute(gui);
			/*
			int[] selection = gui.getFileTable().getSelectedRows();
			for (int i = 0; i < selection.length; i++) {
				System.out.println(selection[i]);
			}
			System.out.println(selection.length);*/
		}
	}

}
