package ch.mardmi.renamingutility.handlers;

import java.io.File;
import java.util.Iterator;
import java.util.List;

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
//			System.out.println("Feldinhalt von prefixField ist: " + gui.getPrefixFieldContent());
//			System.out.println("Feldinhalt von suffixField ist: " + gui.getSuffixFieldContent());
//			System.out.println("Feldinhalt von insertField ist: " + gui.getInsertFieldContent());
			
			// hypotheitschen Dateinamen aufbereiten
			doPrefixAction(gui.getPrefixFieldContent());
			doSuffixAction(gui.getSuffixFieldContent());
			doInsertAction(gui.getInsertFieldContent());
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	
	private void doPrefixAction(String String) {
		if (String.length() > 0) {
			//String am Anfang des Dateinamens anfügen
			System.out.println("Dateiname-Prefix: " + gui.getPrefixFieldContent());
		}
	}
	private void doSuffixAction(String String) {
		if (String.length() > 0) {
			//String am Ende des Dateinamens anfügen
			System.out.println("Dateiname-Suffix: " + gui.getSuffixFieldContent());
		}
	}
	private void doInsertAction(String String) {
		if (String.length() > 0) {
			//String an der gewünschten Position des Dateinamens einfügen
			System.out.println("Dateiname-Insert: " + gui.getInsertFieldContent());
		}
	}
}