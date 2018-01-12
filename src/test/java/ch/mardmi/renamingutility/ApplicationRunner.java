package ch.mardmi.renamingutility;

import java.nio.file.Path;

import org.assertj.swing.data.TableCell;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;

import ch.mardmi.renamingutility.model.DirectoryContentModel;
import ch.mardmi.renamingutility.view.MainFrame;

/**
 * Die Klasse interagiert mit der Applikation über ihre GBO.
 * Sie wird von den Testen benutzt um eine Benutzer intaraktion
 * mit 
 *
 */
public class ApplicationRunner {
	
	// Application Runner interagiert mit GBO über FrameFixture Instanz 
	private FrameFixture window;
	
	
	private Path testPath;
	
	private DirectoryContentModel directoryModel;
	
	public ApplicationRunner(Path testPath) {
		window = null;
		this.testPath = testPath;
		
	}
	
	public void start() {
		
		MainFrame frame = GuiActionRunner.execute(() -> new MainFrame(new DirectoryContentModel(
				testPath.toFile())));
		window = new FrameFixture(frame);
		window.show();
	}
	
	public void hasStarted() throws InterruptedException {
		window.requireTitle("Renaming Utility");
	}
	
	public void setWindow(FrameFixture window) {
		this.window = window;
	}
	
	public void stop() {
		window.cleanUp();
	}

	public void selectFilesInADirectory() {
		window.table("fileTable").selectRows(0, 1);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

	public void numberOfFilesInADirectoryIsShown() {
		window.label("statusLabel").requireText("2 Files(0 Selected)");
	}

	public void numberOfFilesAndNumberOfSelectedFilesIsShown() {
		window.label("statusLabel").requireText("2 Files(2 Selected)");
		
	}

	public void enterPrefixToBeAddedToSelectedFiles() {
		window.textBox("prefixField").enterText("new");	
	}

	public void displaysNewNamesNextToOldNames() {
		window.table("fileTable").valueAt(
				TableCell.row(0).column(2)).equals("newfile1.txt");
		
		window.table("fileTable").valueAt(
				TableCell.row(1).column(2)).equals("newfile2.txt");
		
		window.table("fileTable").valueAt(
				TableCell.row(0).column(1)).equals("file1.txt");
		
		window.table("fileTable").valueAt(
				TableCell.row(1).column(1)).equals("file2.txt");
		
	}

	public void renameSelectedFiles() {
		window.button("renameButton").click();
	}

	public void displaysRenamedFiles() {
		window.table("fileTable").valueAt(
				TableCell.row(0).column(2)).equals("newfile1.txt");
		
		window.table("fileTable").valueAt(
				TableCell.row(1).column(2)).equals("newfile2.txt");
		
		window.table("fileTable").valueAt(
				TableCell.row(0).column(1)).equals("newfile1.txt");
		
		window.table("fileTable").valueAt(
				TableCell.row(1).column(1)).equals("newfile2.txt");
		
	}

}
