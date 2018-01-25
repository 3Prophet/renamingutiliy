package ch.mardmi.renamingutility.handlers;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.mardmi.renamingutility.model.DirectoryContentModel;


/**
 * @author Martin Herzog
 * Hier werden die Listener für die Eingabefelder ausprogrammiert
 */
public class UserActionListener extends AbstractHandler implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (gui.getUseOptionAddPanel()) {
			
			// hypothetischen Dateinamen aufbereiten
//			gui.getPrefixFieldContent();
//			gui.getSuffixFieldContent();
//			gui.getInsertFieldContent();
			
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

}