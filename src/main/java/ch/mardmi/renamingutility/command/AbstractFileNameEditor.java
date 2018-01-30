package ch.mardmi.renamingutility.command;

/**
 * Implementiert die gemeinsame Funktionalität aller Editoren.
 * 
 * @author Dmitry Logvinovich
 *
 */
public abstract class AbstractFileNameEditor implements FileNameEditor {
	
	/**
	 * Definiert ob der entsprechenden Editor angewendet wird.
	 */
	protected boolean selected;
	
	/**
	 *  Konstruktor, wo definiert wird ob den Editor angewendet wird.
	 *  
	 * @param selected Parameter der definiert of den Editor angewendet wird
	 */
	protected AbstractFileNameEditor(boolean selected) {
		if (!selected) {
			return;
		}
		this.selected = selected;
	}
	
	/**
	 * Gibt den unveränderten Dateiname zurück, wenn den Editor ist nicht angewendet,
	 * sonst das Resultat der Nameänderung, die mithilfe von {@link #resultOfEdition(String) resultOfEdition} Methode
	 * implementiert ist.
	 */
	@Override
	public String editName(String fileName) {
		if (!selected) {
			return fileName;
		}
		return resultOfEdition(fileName);
	}
	
	/**
	 * Implementiert Nameänderung Funktionalität.
	 * 
	 * @param fileName Dateiname, den verändert werden soll
	 * @return Einen neuen Dateiname
	 */
	protected abstract String resultOfEdition(String fileName);
}
