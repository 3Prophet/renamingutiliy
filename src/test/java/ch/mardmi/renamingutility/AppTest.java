package ch.mardmi.renamingutility;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.mardmi.renamingutility.view.MainFrame;

import static org.hamcrest.MatcherAssert.assertThat;

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
	 * Scenario:
	 * 
	 * @throws InterruptedException
	 */
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