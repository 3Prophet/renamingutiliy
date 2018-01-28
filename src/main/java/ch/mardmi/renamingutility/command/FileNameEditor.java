package ch.mardmi.renamingutility.command;

/**
 *  Schnittstelle für die Klasse die kapselt, wie Datei unbenennen werden soll
 *  Jede bestimmte Klasse ändert nur Dateiname (soll Ohne Pfad und Endung eingegeben werden). 
 *  
 * @author Dmitry Logvinovich
 *
 */
public interface FileNameEditor {
	/**
	 * Unbenennt Datei
	 * 
	 * @param fileName Dateiname ohne Pfad und Endung
	 * @return neuer Dateiname
	 */
	public String editName(String fileName);

}
