package ch.mardmi.renamingutility.command;

/**
 * Implementiert einer oder mehrerer der folgenden Funktionen:
 * Addierung von Prefix, Suffix, Einführung von Symbolen im Dateiname.
 * 
 * 
 * @author Dmitry Logvinovich
 *
 */
public class AddToNameEditor extends AbstractFileNameEditor {
	
	/**
	 * Prefix für den Dateiname
	 */
	private String prefix;
	
	/**
	 * Suffix für den Dateiname
	 */
	private String suffix;
	
	/**
	 * Symbole, die im Dateiname eingefügt werden sollen
	 */
	private String insert;
	
	/**
	 * Stelle im Dateiname wo die Symbole eingefügt werden sollen
	 */
	private int position;
	
	/**
	 * Definiert, ob Objekt der Klasse die Berechtigung für Datei Unbenennung hat
	 */
	private boolean selected;
	
	
	public AddToNameEditor( 
							boolean selected,
							String prefix,
							String suffix, 
							String insert, 
							int position) {
		super(selected);
		if (!selected) {
			return;
		}
		
		this.prefix = prefix;
		this.suffix = suffix;
		this.insert = insert;
		this.position = position;
	}
	

	@Override
	protected String resultOfEdition(String fileName) {
		StringBuilder sb = new StringBuilder(fileName);
		sb.insert(0, prefix).append(suffix).insert(position, insert);
		return sb.toString();
	}

}
