package ch.mardmi.renamingutility.handlers;

import ch.mardmi.renamingutility.view.MainFrame;

/**
 * Haltet Refernz zu MainFrame component. Auf diese Weise werden 
 * Attributen von MainFrame für die Objekten der Klassen die von
 * dieser Klasse vererben zugreifbar.
 * 
 * @author Dmitry Logvinovich
 *
 */
public class AbstractHandler {
	
	/**
	 * Referenz zu MainFrame
	 */
	protected static MainFrame gui;
	
	/**
	 * @param gui GBO die für alle Handlers zugreifbar werden soll.
	 */
	public static void setGUI(MainFrame gui) {
		AbstractHandler.gui = gui;
	}
	
}
