package ch.mardmi.renamingutility.view;

import javax.swing.JLabel;

import ch.mardmi.renamingutility.handlers.StatusModelListener;
import ch.mardmi.renamingutility.model.StatusModel;

/**
 * Label um Gesamtanzahl von Dateien im Ordner und Anzahl der selektierten Dateien anzuzeigen,
 * z.B.  2 Files(1 Selected)
 */
public class StatusLabel extends JLabel implements StatusModelListener {
	
	/**
	 * 
	 * @param model Statuszeile für die Anzeige zurückgeben 
	 */
	public StatusLabel(StatusModel model) {
		super(model.toString());
	}

	@Override
	public void statusModelChanged(StatusModel statusModel) {
		setText(statusModel.toString());
		repaint();
	}
	
	public static StatusLabel createStatusLabel(StatusModel model) {
		StatusLabel label = new StatusLabel(model);
		model.addChangeListener(label);
		return label;
	}
}
