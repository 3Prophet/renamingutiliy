package ch.mardmi.renamingutility.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ch.mardmi.renamingutility.command.AbstractCommand;
import ch.mardmi.renamingutility.handlers.AbstractHandler;
import ch.mardmi.renamingutility.handlers.ActionKey;
import ch.mardmi.renamingutility.handlers.DirectorySelectionHandler;
import ch.mardmi.renamingutility.handlers.TableModelChangeHandler;
import ch.mardmi.renamingutility.handlers.UserActionListener;
import ch.mardmi.renamingutility.model.DirectoryContentModel;
import ch.mardmi.renamingutility.model.StatusModel;
import ch.mardmi.renamingutility.model.FolderTreeCellRenderer;

public class MainFrame extends JFrame {

	private static final int PREFERRED_WIDTH = 10;

	// Action Handlers
	private static Map<ActionKey, Object> handlers;

	private Container container;

	// Tabelle mit dem Inhalt des Verzeichnisses
	private JTable fileTable;

	/**
	 * Erstellt die die tabellarische Ansicht für die Dateien
	 * @return fileTable
	 */
	public JTable getFileTable() {
		return fileTable;
	}

	private JScrollPane fileTreePane;

	// Verzeichnis-Modell
	// (wird für die Darstellung eines Verzeichnisses verwendet)
	private DirectoryContentModel directoryModel;

	private JPanel southPanel;

	private JPanel editorPanel;

	private StatusModel statusModel;

	private ListSelectionModel listSelectionModel;

	private FileSystemView fileSystemView;

	public FileSystemView getFileSystemView() {
		return fileSystemView;
	}

	private File rootDirectory;

	private JTree fileTree;

	private JCheckBox useOptionAddPanel;

	/**
	 * Gibt zurück, ob das AddPanel aktiv ist
	 * @return boolean
	 */
	public boolean getUseOptionAddPanel() {
		return useOptionAddPanel.isSelected();
	}

	private JCheckBox useOptionRemovePanel;

	/**
	 * Gibt zurück, ob das RemovePanel aktiv ist
	 * @return boolean
	 */
	public boolean getUseOptionRemovePanel() {
		return useOptionRemovePanel.isSelected();
	}

	private JTextField prefixField;

	/**
	 * Gibt den Wert aus dem Feld prefixField zurück
	 * @return String
	 */
	public String getPrefixFieldContent() {
		return prefixField.getText();
	}

	private JTextField suffixField;

	/**
	 * Gibt den Wert aus dem Feld suffixField zurück
	 * @return String
	 */
	public String getSuffixFieldContent() {
		return suffixField.getText();
	}

	private JTextField insertField;

	/**
	 * Gibt den Wert aus dem Feld InsertField zurück
	 * @return String
	 */
	public String getInsertFieldContent() {
		return insertField.getText();
	}

	private JSpinner positionSpinner;

	public JSpinner getPositionSpinner() {
		return positionSpinner;
	}

	/**
	 * Gibt den numerischen Wert aus dem Feld positionSpinner zurück
	 * @return int
	 */
	public int getPositionSpinnerValue() {
		return (int) positionSpinner.getValue();
	}

	private JSpinner firstNSpinner;

	public JSpinner getFirstNSpinner() {
		return firstNSpinner;
	}

	/**
	 * Gibt den numerischen Wert aus dem Feld firstNSpinner zurück
	 * @return int
	 */
	public int getFirstSpinnerValue() {
		return (int) firstNSpinner.getValue();
	}

	private JSpinner lastNSpinner;

	public JSpinner getLastSpinner() {
		return lastNSpinner;
	}

	/**
	 * Gibt den numerischen Wert aus dem Feld lastNSpinner zurück
	 * @return int
	 */
	public int getLastSpinnerValue() {
		return (int) lastNSpinner.getValue();
	}

	public StatusModel getStatusModel() {
		return statusModel;
	}

	public void setStatusModel(StatusModel statusModel) {
		this.statusModel = statusModel;
	}

	public MainFrame(DirectoryContentModel directoryModel, StatusModel statusModel) {
		super();
		this.statusModel = statusModel;
		this.directoryModel = directoryModel;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container = this.getContentPane();
		setTitle("Renaming Utility");

		createNavigationPanel();
		createEditorPanel();
		createSouthPanel();
		setSize(700, 600);
		setVisible(true);
	}

	/**
	 * Erstellt Status Pane, welche die Anzahl der Dateien und 
	 * der selektierten Dateien anzeigt.
	 */
	private void createStatusBar() {
		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		StatusLabel statusLabel = StatusLabel.createStatusLabel(statusModel);
		statusLabel.setName("statusLabel");
		statusPanel.add(statusLabel);
		southPanel.add(statusPanel);
	}

	public DirectoryContentModel getDirectoryModel() {
		return directoryModel;
	}

	/**
	 * Erstellung der Panels mit dem Verzeichnisbaum und Ordner-/Datei-
	 * Tabelle, welche den Inhalt des gewählten Verzeichnisses darstellt.
	 */
	private void createNavigationPanel() {
		createFileTreeNavigation();
		createFileTable();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(fileTreePane),
				new JScrollPane(fileTable));
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);
		container.add(BorderLayout.NORTH, splitPane);
	}

	private void createEditorPanel() {
		editorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		createAddEditionPanel();
		createRemoveEditionPanel();
		container.add(BorderLayout.CENTER, editorPanel);
	}

	private void createAddEditionPanel() {
		JPanel additionPanel = new JPanel();
		additionPanel.setLayout(new BoxLayout(additionPanel, BoxLayout.Y_AXIS));
		additionPanel.setBorder(BorderFactory.createTitledBorder("Add"));

		// CheckBox erstellen, wird für die Aktivierung des Add-Panels verwendet
		JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		useOptionAddPanel = new JCheckBox();
		checkBoxPanel.add(useOptionAddPanel);
		additionPanel.add(checkBoxPanel);

		// Panel für das Eingabefeld 'Prefix' erstellen
		JPanel prefixPanel = new JPanel();
		JLabel prefixLabel = new JLabel("Prefix");
		prefixField = new JTextField(PREFERRED_WIDTH);
		prefixPanel.add(prefixLabel);
		prefixPanel.add(prefixField);
		additionPanel.add(prefixPanel);

		// Panel für das Eingabefeld 'Suffix' erstellen
		JPanel suffixPanel = new JPanel();
		JLabel suffixLabel = new JLabel("Suffix");
		suffixField = new JTextField(PREFERRED_WIDTH);
		suffixPanel.add(suffixLabel);
		suffixPanel.add(suffixField);
		additionPanel.add(suffixPanel);

		// Panel für das Eingabefeld 'Insert' erstellen
		JPanel insertionPanel = new JPanel();
		JLabel insertLabel = new JLabel("Insert");
		insertField = new JTextField(PREFERRED_WIDTH);
		insertionPanel.add(insertLabel);
		insertionPanel.add(insertField);
		additionPanel.add(insertionPanel);

		// Panel für den Spinner 'Position' erstellen
		JPanel positionPanel = new JPanel();
		positionPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel positionLabel = new JLabel("at pos.");
		SpinnerModel positionModel = new SpinnerNumberModel(0, -100, 100, 1);
		positionSpinner = new JSpinner(positionModel);
		positionPanel.add(positionLabel);
		positionPanel.add(positionSpinner);
		additionPanel.add(positionPanel);

		editorPanel.add(additionPanel);
	}

	private void createRemoveEditionPanel() {
		JPanel removalPanel = new JPanel();
		removalPanel.setLayout(new BoxLayout(removalPanel, BoxLayout.Y_AXIS));
		removalPanel.setBorder(BorderFactory.createTitledBorder("Remove"));

		// CheckBox erstellen, wird für die Aktivierung des Remove-Panels verwendet
		JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		useOptionRemovePanel = new JCheckBox();
		checkBoxPanel.add(useOptionRemovePanel);
		removalPanel.add(checkBoxPanel);

		JPanel spinnerPanel = new JPanel(new GridLayout(0, 4));

		// Panel für den Spinner 'First n' erstellen
		JLabel firstNLabel = new JLabel("First n");
		spinnerPanel.add(firstNLabel);

		SpinnerModel firstNModel = new SpinnerNumberModel(0, -100, 100, 1);
		firstNSpinner = new JSpinner(firstNModel);
		spinnerPanel.add(firstNSpinner);

		// Panel für den Spinner 'Last n' erstellen
		JLabel lastNLabel = new JLabel("Last n");
		spinnerPanel.add(lastNLabel);

		SpinnerModel lastNModel = new SpinnerNumberModel(0, -100, 100, 1);
		lastNSpinner = new JSpinner(lastNModel);
		spinnerPanel.add(lastNSpinner);

		removalPanel.add(spinnerPanel);

		editorPanel.add(removalPanel);
	}

	private void createFileTable() {
		fileTable = new JTable(directoryModel);
		directoryModel.addTableModelListener(new TableModelChangeHandler());
		fileTable.setName("fileTable");
		listSelectionModel = fileTable.getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	private void createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton renameButton = new JButton("Rename");
		buttonPanel.add(renameButton);
		southPanel.add(buttonPanel);
	}

	private void createSouthPanel() {
		southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		container.add(BorderLayout.SOUTH, southPanel);
		createButtonPanel();
		createStatusBar();
	}

	private void createFileTreeNavigation() {
		// Wurzelknoten vom Dateisystem
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();

		// Zugang zum Dateisystem
		fileSystemView = FileSystemView.getFileSystemView();

		// Wurzelverzeichnis übergeben
		File[] fileSystemRoot = fileSystemView.getRoots();

		for (File rootDir : fileSystemRoot) {
			DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootDir);
			rootDirectory = rootDir;
			root.add(rootNode);
			for (File file : fileSystemView.getFiles(rootDir, true)) {
				if (file.isDirectory()) {
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
					rootNode.add(node);
				}
			}
		}

		DefaultTreeModel treeModel = new DefaultTreeModel(root);

		fileTree = new JTree(treeModel);
		fileTree.setName("fileTree");

		fileTree.setRootVisible(false);
		fileTree.setSelectionRow(0);
		fileTree.expandRow(0);
		fileTree.setCellRenderer(new FolderTreeCellRenderer());
		// tree.addTreeWillExpandListener(new DirectoryLazyLoadCommand());
		// tree.addTreeExpansionListener(new LazyCommand());

		fileTreePane = new JScrollPane(fileTree);
	}

	public void setHandlers(Map<ActionKey, Object> handlers) {
		listSelectionModel
				.addListSelectionListener((ListSelectionListener) handlers.get(ActionKey.TABLE_SELECTION_HANDLER));
		fileTree.addTreeSelectionListener(
				(DirectorySelectionHandler) handlers.get(ActionKey.DIRECTORY_SELECTION_HANDLER));
		prefixField.getDocument().addDocumentListener(
				(UserActionListener) handlers.get(ActionKey.INPUT_LISTENER));
		suffixField.getDocument().addDocumentListener(
				(UserActionListener) handlers.get(ActionKey.INPUT_LISTENER));
		insertField.getDocument().addDocumentListener(
				(UserActionListener) handlers.get(ActionKey.INPUT_LISTENER));
		positionSpinner.addChangeListener(
				(ChangeListener) handlers.get(ActionKey.SPINNER_LISTENER));
		firstNSpinner.addChangeListener(
				(ChangeListener) handlers.get(ActionKey.SPINNER_LISTENER));
		lastNSpinner.addChangeListener(
				(ChangeListener) handlers.get(ActionKey.SPINNER_LISTENER));
	}

	/**
	 * Factory Methode für GBO Erstellung
	 * 
	 * @param dirModel
	 *            Das Model für die Ordner-Tabelle
	 * @param statusModel
	 *            Das Model für die Statuszeile
	 * @param handlers
	 *            Action Listeners für verschiedene Komponenten
	 * @return GBO von der Anwendung
	 */
	public static MainFrame createMainFrame(DirectoryContentModel dirModel, StatusModel statusModel,
			Map<ActionKey, Object> handlers) {
		MainFrame frame = new MainFrame(dirModel, statusModel);
		AbstractHandler.setGUI(frame);
		frame.setHandlers(handlers);
		AbstractCommand.setDirectoryModel(dirModel);
		return frame;
	}
}
