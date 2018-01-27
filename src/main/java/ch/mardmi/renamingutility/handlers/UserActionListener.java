package ch.mardmi.renamingutility.handlers;

//import java.util.logging.Handler;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * @author Martin Herzog
 * Hier werden die Listener für die Eingabefelder ausprogrammiert
 */
public class UserActionListener extends AbstractHandler implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (gui.getUseOptionAddPanel()) {
			System.out.println("Feldinhalt von prefixField ist: " + gui.getPrefixFieldContent());
			System.out.println("Feldinhalt von suffixField ist: " + gui.getSuffixFieldContent());
			System.out.println("Feldinhalt von insertField ist: " + gui.getInsertFieldContent());

			HandlerHelper.execute(gui);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if (gui.getUseOptionAddPanel()) {
			System.out.println("Feldinhalt von prefixField ist: " + gui.getPrefixFieldContent());
			System.out.println("Feldinhalt von suffixField ist: " + gui.getSuffixFieldContent());
			System.out.println("Feldinhalt von insertField ist: " + gui.getInsertFieldContent());

			HandlerHelper.execute(gui);
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if (gui.getUseOptionAddPanel()) {
			System.out.println("Feldinhalt von prefixField ist: " + gui.getPrefixFieldContent());
			System.out.println("Feldinhalt von suffixField ist: " + gui.getSuffixFieldContent());
			System.out.println("Feldinhalt von insertField ist: " + gui.getInsertFieldContent());

			HandlerHelper.execute(gui);
		}
	}

}