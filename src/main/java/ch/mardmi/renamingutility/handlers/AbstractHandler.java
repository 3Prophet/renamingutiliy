package ch.mardmi.renamingutility.handlers;

import ch.mardmi.renamingutility.view.MainFrame;

public class AbstractHandler {
	
	protected static MainFrame gui;
	
	public static void setGUI(MainFrame gui) {
		AbstractHandler.gui = gui;
	}
	
}
