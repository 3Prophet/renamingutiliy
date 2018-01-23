package ch.mardmi.renamingutility.handlers;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Martin Herzog
 * Hier werden die Listener für die Spinner-Felder ausprogrammiert
 */
public class SpinnerActionListener extends AbstractHandler implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {
		
		// Spinner des Add-Panels verarbeiten, wenn das Panel aktiv ist
		if (gui.getUseOptionAddPanel()) {
			if (e.getSource() == gui.getPositionSpinner()) {
				System.out.println("der Spinner im Add-Feld wurde angeklickt");
				System.out.println("der Spinner-Wert ist: " + gui.getPositionSpinnerValue());
			}
		}

		// Spinner des Remove-Panels verarbeiten, wenn das Panel aktiv ist
		if (gui.getUseOptionRemovePanel()) {
			if (e.getSource() == gui.getFirstNSpinner()) {
				System.out.println("der firstNSpinner im Remove-Feld wurde angeklickt");
				System.out.println("der Spinner-Wert ist: " + gui.getFirstSpinnerValue());
			} else if (e.getSource() == gui.getLastSpinner()) {
				System.out.println("der lastNSpinner im Remove-Feld wurde angeklickt");
				System.out.println("der Spinner-Wert ist: " + gui.getLastSpinnerValue());
			}
		}
	}
}
