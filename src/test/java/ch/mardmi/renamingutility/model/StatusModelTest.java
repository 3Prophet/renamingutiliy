package ch.mardmi.renamingutility.model;

import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import ch.mardmi.renamingutility.handlers.StatusModelListener;
import ch.mardmi.renamingutility.model.StatusModel;

public class StatusModelTest {
	
	private StatusModel  statusModel;
	
	@Before
	public void setUp() throws Exception {
		statusModel = new StatusModel();
	}
	
	/**
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void addingChangeListenerIncreasesNumberOfListeners() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		
		StatusModelListener listener = mock(StatusModelListener.class);
		
		statusModel.addChangeListener(listener);
		
		Class<?> modelClass = statusModel.getClass();
		Field listenersField = modelClass.getDeclaredField("listeners");
		listenersField.setAccessible(true);
		
		List<StatusModelListener> listeners = (List<StatusModelListener>) 
						listenersField.get(statusModel);
		assertThat(listeners.size(), is(1));
	}
	
	@Test
	public void callingRowsSelectedMethodTriggersStatusModelListener() {
		StatusModelListener listener = mock(StatusModelListener.class);
		statusModel.addChangeListener(listener);
		statusModel.rowsSelected(2);
		verify(listener).statusModelChanged(statusModel);
	}
	
	@Test
	public void callingfilesInDirectoryChangedMethodTriggersStatusModelListener() {
		StatusModelListener listener = mock(StatusModelListener.class);
		statusModel.addChangeListener(listener);
		statusModel.filesInDirectoryChanged(2);
		verify(listener).statusModelChanged(statusModel);
	}
	
	@Test
	public void toStringTest() {
		statusModel.rowsSelected(2);
		statusModel.filesInDirectoryChanged(2);
		assertThat(statusModel.toString(), is("2 Files(2 Selected)"));
	}
}
