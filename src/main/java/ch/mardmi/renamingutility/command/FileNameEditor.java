package ch.mardmi.renamingutility.command;

/**
 *  Schnittstelle für die Klasse die kapselt, wie Datei umbenennen werden soll
 *  Jede bestimmte Implementierung ändert nur den Dateiname (soll Ohne Pfad und Endung eingegeben werden). 
 *  
 * @author Dmitry Logvinovich
 *
 */
public interface FileNameEditor {
	/**
	 * Implementiert die Funktionalität vom Umbenennen.
	 * 
	 * @param fileName Dateiname ohne Pfad und Endung
	 * @return neuer Dateiname
	 */
	public String editName(String fileName);

}
