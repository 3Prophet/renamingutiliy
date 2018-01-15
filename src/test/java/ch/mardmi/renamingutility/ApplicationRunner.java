package ch.mardmi.renamingutility;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.assertj.swing.data.TableCell;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;

import ch.mardmi.renamingutility.handlers.ActionKey;
import ch.mardmi.renamingutility.handlers.TableSelectionHandler;
import ch.mardmi.renamingutility.model.DirectoryContentModel;
import ch.mardmi.renamingutility.model.StatusModel;
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
	
	MainFrame frame;
	
	private DirectoryContentModel directoryModel;
	
	public ApplicationRunner(Path testPath) {
		window = null;
		this.testPath = testPath;
	}
	
	public void start() {
		Map<ActionKey, Object> handlers = new HashMap<ActionKey, Object>();
    	
    	TableSelectionHandler tableHandler = new TableSelectionHandler();
    	
    	handlers.put(ActionKey.TABLE_SELECTION_HANDLER, tableHandler);
    	
		MainFrame frame = GuiActionRunner.execute(() -> 
		MainFrame.createMainFrame(new DirectoryContentModel(testPath.toFile()), 
	     		   new StatusModel(), handlers));
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
			Thread.sleep(1000);
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

	public void setStatusModel(StatusModel statusModel) {
		frame.setStatusModel(statusModel);	
	}

	public void messageInAStatusBarDisplaysNumberOfSelectedFiles() {
		assertThat(window.label("statusLabel").text(), containsString("2 Selected"));
	}

	public void providedDirectorySelected() {
		window.tree("fileTree").requireSelection(
				TestFactory.getTestDirectory().toAbsolutePath().toString());
	}
	
	

}
