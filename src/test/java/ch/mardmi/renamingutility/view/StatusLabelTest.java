package ch.mardmi.renamingutility.view;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import ch.mardmi.renamingutility.model.StatusModel;

public class StatusLabelTest {
	
	private StatusLabel label;
	
	private StatusModel model;

	@Before
	public void setUp() throws Exception {
		model = new StatusModel();
		label = StatusLabel.createStatusLabel(model);
	}
	
	@Test
	public void callingRowsSelectedMethodOnStatusModelChangesDisplayedTextOfStatusLabel() {
		model.rowsSelected(2);
		assertThat(label.getText(), is("0 Files(2 Selected)"));
	}
	
	@Test
	public void callingfilesInDirectoryChangedMethodOnStatusModelChangesDisplayedTextOfStatusLabel() {
		model.filesInDirectoryChanged(2);
		assertThat(label.getText(), is("2 Files(0 Selected)"));
	}
	
	@Test
	public void callingfilesRowsSelectedAndInDirectoryChangedMethodOnStatusModelChangesDisplayedValueOfStatusLabel() {
		model.rowsSelected(2);
		model.filesInDirectoryChanged(2);
		assertThat(label.getText(), is("2 Files(2 Selected)"));
	}
}
