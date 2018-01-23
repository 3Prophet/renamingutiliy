package ch.mardmi.renamingutility.command;

/**
 * Schnittstelle die alle Befehle implementieren sollen
 */
public interface Command {
	
	/**
	 * Befehl ausführen
	 */
	public void execute();
	
	/**
	 * Configuration vom Befehl löschen
	 */
	public void reset();
}
