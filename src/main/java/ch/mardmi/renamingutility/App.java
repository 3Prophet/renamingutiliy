package ch.mardmi.renamingutility;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import ch.mardmi.renamingutility.handlers.ActionKey;
import ch.mardmi.renamingutility.handlers.DirectorySelectionHandler;
import ch.mardmi.renamingutility.handlers.SpinnerActionListener;
import ch.mardmi.renamingutility.handlers.TableSelectionHandler;
import ch.mardmi.renamingutility.handlers.UserActionListener;
import ch.mardmi.renamingutility.model.DirectoryContentModel;
import ch.mardmi.renamingutility.model.StatusModel;
import ch.mardmi.renamingutility.view.MainFrame;

/**
 *  @author Dmitry Logvinovich & Martin Herzog
 *  Diese Klasse startet das Renaming Utility
 */
public class App extends JFrame {
	
    public static void main( String[] args ) {
    	
    	Map<ActionKey, Object> handlers = new HashMap<ActionKey, Object>();
    	
    	handlers.put(ActionKey.TABLE_SELECTION_HANDLER, new TableSelectionHandler());
    	handlers.put(ActionKey.DIRECTORY_SELECTION_HANDLER, new DirectorySelectionHandler());
    	handlers.put(ActionKey.INPUT_LISTENER, new UserActionListener());
    	handlers.put(ActionKey.SPINNER_LISTENER, new SpinnerActionListener());
    	
    	MainFrame.createMainFrame(new DirectoryContentModel(Paths.get("/").toFile()), 
     		   new StatusModel(), handlers);
    }
}
