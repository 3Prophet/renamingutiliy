package ch.mardmi.renamingutility.view;

import javax.swing.JLabel;

import ch.mardmi.renamingutility.handlers.StatusModelListener;
import ch.mardmi.renamingutility.model.StatusModel;

/**
 * Label um Gesamtanzahl von Dateien im Ordner und Anzahlt der selektierten Dateien zu zeigen,
 * z.B.  2 Files(1 Selected)
 */
public class StatusLabel extends JLabel implements StatusModelListener {
	
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
