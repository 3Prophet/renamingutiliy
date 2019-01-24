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

/**
 * Tests für Status Modelle {@link ch.mardmi.renamingutility.model.StatusModel}
 * 
 * @author Dmitry Logvinovich
 *
 */
public class StatusModelTest {
	
	/**
	 * Staus Modelle Einheit die getestet wird
	 */
	private StatusModel  statusModel;
	
	@Before
	public void setUp() throws Exception {
		statusModel = new StatusModel();
	}
	
	/**
	 * Test für  {@link ch.mardmi.renamingutility.model.StatusModel#addChangeListener(StatusModelListener)}
	 * Es wird getestet, dass Addierung des Beobachters die Anzahl von Beobachters mit 1 incrementiert.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void addingChangeListenerIncreasesNumberOfListeners() throws Exception {
		
		StatusModelListener listener = mock(StatusModelListener.class);
		
		statusModel.addChangeListener(listener);
		
		Class<?> modelClass = statusModel.getClass();
		Field listenersField = modelClass.getDeclaredField("listeners");
		listenersField.setAccessible(true);
		
		List<StatusModelListener> listeners = (List<StatusModelListener>) 
						listenersField.get(statusModel);
		assertThat(listeners.size(), is(1));
	}
	
	/**
	 * Testet, dass bei der Anzahl der selektierten Dateien Veränderung (z.B. wird eine neue Datei
	 * selektiert),
	 * werden die Beobachter Notifiziert, d.h. 
	 * {@link ch.mardmi.renamingutility.handlers.StatusModelListener#statusModelChanged(StatusModel)}
	 * wird aufgeruft.
	 */
	@Test
	public void callingRowsSelectedMethodTriggersStatusModelListener() {
		StatusModelListener listener = mock(StatusModelListener.class);
		statusModel.addChangeListener(listener);
		statusModel.rowsSelected(2);
		verify(listener).statusModelChanged(statusModel);
	}
	
	/**
	 * Testet, dass bei der Anzahl der Deteien Veränderung (z.B. Oeffnen von neuem Verzeichnis),
	 * werden die Beobachter Notifiziert, d.h. 
	 * {@link ch.mardmi.renamingutility.handlers.StatusModelListener#statusModelChanged(StatusModel)}
	 * wird aufgeruft.
	 */
	@Test
	public void callingfilesInDirectoryChangedMethodTriggersStatusModelListener() {
		StatusModelListener listener = mock(StatusModelListener.class);
		statusModel.addChangeListener(listener);
		statusModel.filesInDirectoryChanged(2);
		verify(listener).statusModelChanged(statusModel);
	}
	
	/**
	 * Testet, dass {@link ch.mardmi.renamingutility.model.StatusModel} Methode  
	 * eine richtige Meldung augibt (Meldung Format: N Files(M Selected))
	 */
	@Test
	public void toStringTest() {
		statusModel.rowsSelected(2);
		statusModel.filesInDirectoryChanged(2);
		assertThat(statusModel.toString(), is("2 Files(2 Selected)"));
	}
}
