package ch.mardmi.renamingutility.handlers;

import ch.mardmi.renamingutility.model.StatusModel;

/**
 * Schnittstelle für die Status Modelle Listener.
 * 
 * @author Dlogvinovich Dmitry
 *
 */
public interface StatusModelListener {
	/**
	 * Implementiert Aktion nach der StatusModel Veränderung.
	 * @param statusModel
	 */
	public void statusModelChanged(StatusModel statusModel);
}
