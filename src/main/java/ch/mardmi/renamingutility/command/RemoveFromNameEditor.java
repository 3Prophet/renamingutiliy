package ch.mardmi.renamingutility.command;

/**
 * Implementiert Entfernung von bestimmten Symbolen von einem Dateiname.
 * Es darf N Symbolen von Anfang oder Ende des Dateinamens entfernt werden.
 * 
 * @author Dmitry Logvinovich
 *
 */
public class RemoveFromNameEditor extends AbstractFileNameEditor {
	
	/**
	 * Setzt wie viele Symbole wird von Anfang des Dateinamens entfernt.
	 */
	private int firstNrOfSymbols;
	
	/**
	 * Setzt wie viele Symbole wird von Ende des Dateinamens entfernt.
	 */
	private int lastNrOfSymbols;
	
	/**
	 * @param selected Definiert ob den Editor angewendet wird
	 * @param firstNrOfSymbols So viele Symbole wird von Anfang des Daentfernt
	 * @param lastNrOfSymbols So viele Symbole wird von Ende des Namens entfernt 
	 */
	public RemoveFromNameEditor(boolean selected, int firstNrOfSymbols, int lastNrOfSymbols) {
		super(selected);
		if (!selected) {
			return;
		}
		this.firstNrOfSymbols = firstNrOfSymbols;
		this.lastNrOfSymbols = lastNrOfSymbols;
	}
	
	/**
	 * {@see ch.mardmi.renamingutility.command.AbstractFileNameEditor#resultOfEdition(String)}
	 */
	@Override
	protected String resultOfEdition(String fileName) {
		return fileName.substring(firstNrOfSymbols, fileName.length() - lastNrOfSymbols);
	}

}
