package ch.mardmi.renamingutility.handlers;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import ch.mardmi.renamingutility.model.DirectoryContentModel;

/**
 * Aktualisiert StatusModel mit dem Anzahl von gewählten Dateien, wenn die letzte augewählt werden.
 *  
 * @author Dmitry Logvinovich
 *
 */
public class TableModelChangeHandler extends AbstractHandler implements TableModelListener {

	@Override
	public void tableChanged(TableModelEvent e) {
		DirectoryContentModel model = (DirectoryContentModel) e.getSource();
		gui.getStatusModel().filesInDirectoryChanged(model.getRowCount());
	}
}
