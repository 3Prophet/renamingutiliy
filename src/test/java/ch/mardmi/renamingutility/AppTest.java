package ch.mardmi.renamingutility;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 
 * Ende zu Ende Testen von der Application
 *
 */
@Ignore
public class AppTest {
	
	private static final Path testPath = TestFactory.getTestDirectory();
	private List<String> targetFilenames = new ArrayList<>(Arrays.asList("file1.txt", "file2.txt")); 
	private ApplicationRunner application = new ApplicationRunner(testPath);
	
	/**
	 * Erstellung von Test Ordner und Zieldateien file1.txt und file2.txt und
	 * Application Starten.
	 * @throws IOException
	 */
	@Before
	public void setUp() throws IOException {
		Files.createDirectory(testPath);
		for (String fname: targetFilenames) {
			Path file = Paths.get(testPath.toString(), fname);
			Files.createFile(file);
		}
		application.start();	
	}
	
	/**
	 * Löschung von Test Ordner und Slissen von Application.
	 * @throws IOException 
	 */
	@After
	public void tearDown() throws IOException {
		application.stop();
		
		for (String fname: targetFilenames) {
			Path file = Paths.get(testPath.toString(), fname);
			Files.delete(file);
		}
		Files.delete(testPath);
	}
	/**
	@Test
	public void selectingFilesInDirectoryTriggersTableSelectionHadler() throws InterruptedException {
		TableSelectionHandler selectionHandler = mock(TableSelectionHandler.class);
		Map<ActionKey, Object> handlers = new HashMap<ActionKey, Object>();
		handlers.put(ActionKey.TABLE_SELECTION_HANDLER, selectionHandler);
		application.setHandlers(handlers);
		application.hasStarted();
		application.selectFilesInADirectory();
		verify(selectionHandler, times(1)).valueChanged(any());
		
	}
	
	
	@Test
	public void selectingFilesInDirectoryInitiatesStatusModelChange() throws InterruptedException {
		StatusModel statusModel = mock(StatusModel.class);
		application.setStatusModel(statusModel);
		application.hasStarted();
		application.selectFilesInADirectory();
		verify(statusModel).rowsSelected(2);
	}*/
	
	/**
	 * Bestimmt, dass when 2 Reihe von Dateitabelle selektiert sind,
	 * enthaltet Status Pane Text "2 Selected"
	 * @throws InterruptedException
	 */
	@Test
	public void selectingFilesInDirectoryChangesTextInStatusPanel() throws InterruptedException {
		application.hasStarted();
		application.selectFilesInADirectory();
		application.messageInAStatusBarDisplaysNumberOfSelectedFiles();
	}
	
	@Test
	public void openingApplicationCausesProvidedDirectorySelectionInDirectoryTree() throws InterruptedException {
		application.hasStarted();
		application.providedDirectorySelected();
	}
	
	@Test
	public void endToEndTest() throws InterruptedException {
		
		application.hasStarted();
		application.numberOfFilesInADirectoryIsShown();
		
		application.selectFilesInADirectory();
		application.numberOfFilesAndNumberOfSelectedFilesIsShown();
		application.enterPrefixToBeAddedToSelectedFiles();
		application.displaysNewNamesNextToOldNames();
		application.renameSelectedFiles();
		application.displaysRenamedFiles();
	}
	
	
	
	
}