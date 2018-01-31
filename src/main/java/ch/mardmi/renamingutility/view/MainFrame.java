package ch.mardmi.renamingutility.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
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
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ch.mardmi.renamingutility.handlers.AbstractHandler;
import ch.mardmi.renamingutility.handlers.ActionKey;
import ch.mardmi.renamingutility.handlers.TableModelChangeHandler;
import ch.mardmi.renamingutility.model.DirectoryContentModel;
import ch.mardmi.renamingutility.model.StatusModel;
import ch.mardmi.renamingutility.model.FolderTreeCellRenderer;

public class MainFrame extends JFrame {
	
	/**
	 * Breite von Text Felder
	 */
	private static final int PREFERRED_WIDTH = 10;
	
	/**
	 * Damit wird JTable's Reiche Höhe multipliziert um Datei Symbole umzufassen
	 */
	private static final double TABLE_HEIGHT_RATIO = 1.3;
	
	/**
	 * Damit wird die breite von die erste Datei Tabelle Spalete (zeigt Datei Symbol) incrementiert 
	 */
	private static final int COLUMN_WIDTH_INCREMENT = 10;

	/**
	 *  Action Handlers die mit der Widgets verbunden sind
	 */
	private static Map<ActionKey, Object> handlers;

	private Container container;

	/**
	 *  Tabelle die den Inhalt des Verzeichnisses Anzeigt
	 */
	private JTable fileTable;

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
		System.out.println(useOptionAddPanel.isSelected());
		return useOptionAddPanel.isSelected();
	}

	private JCheckBox useOptionRemovePanel;

	private JTextField prefixField;

	private JTextField suffixField;

	private JTextField insertField;

	private JSpinner positionSpinner;

	private JSpinner firstNSpinner;

	private JSpinner lastNSpinner;

	private JButton clearButton;

	private JButton renameButton;

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

		setSize(900, 720);

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
		JScrollPane fileTablePane = new JScrollPane(fileTable);
		fileTablePane.getViewport().setBackground(Color.WHITE);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(fileTreePane),
				fileTablePane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);
		container.add(BorderLayout.NORTH, splitPane);
	}

	private void createEditorPanel() {
		/**
		editorPanel = new JPanel();
		GroupLayout layout = new GroupLayout(editorPanel);
		editorPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JPanel addPanel = createAddEditionPanel();
		JPanel removePanel = createRemoveEditionPanel();
		
		layout.setHorizontalGroup(
				   layout.createSequentialGroup()
				      .addComponent(addPanel)
				      .addComponent(removePanel));
		
		layout.setVerticalGroup(
				   layout.createSequentialGroup()
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				           .addComponent(addPanel)
				           .addComponent(removePanel)
				          ));*/
		 
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

		JLabel positionLabel = new JLabel("at pos.");
		SpinnerModel positionModel = new SpinnerNumberModel(0, 0, 100, 1);
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

		SpinnerModel firstNModel = new SpinnerNumberModel(0, 0, 100, 1);
		firstNSpinner = new JSpinner(firstNModel);
		spinnerPanel.add(firstNSpinner);

		// Panel für den Spinner 'Last n' erstellen
		JLabel lastNLabel = new JLabel("Last n");
		spinnerPanel.add(lastNLabel);

		SpinnerModel lastNModel = new SpinnerNumberModel(0, 0, 100, 1);
		lastNSpinner = new JSpinner(lastNModel);
		spinnerPanel.add(lastNSpinner);

		removalPanel.add(spinnerPanel);

		editorPanel.add(removalPanel);
	}

	private void createFileTable() {
		fileTable = new JTable(directoryModel);
		directoryModel.addTableModelListener(new TableModelChangeHandler());
		fileTable.setRowHeight( (int)(fileTable.getRowHeight()*TABLE_HEIGHT_RATIO) );
		fileTable.setName("fileTable");
		fileTable.setShowGrid(false);
		fileTable.setAutoCreateRowSorter(true);
		listSelectionModel = fileTable.getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		TableColumn tableColumn = fileTable.getColumnModel().getColumn(0);
        JLabel label = new JLabel( (String)tableColumn.getHeaderValue() );
        Dimension preferred = label.getPreferredSize();
        int width = (int)preferred.getWidth() + COLUMN_WIDTH_INCREMENT;
        
        tableColumn.setPreferredWidth(width);
        tableColumn.setMaxWidth(width);
        tableColumn.setMinWidth(width);
		
		
		
		
	}

	private void createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		clearButton = new JButton("Clear Input");
		buttonPanel.add(clearButton);

		renameButton = new JButton("Rename");
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
	
	public List<Object> getAdditionPanelConfiguration() {
		return Arrays.asList(useOptionAddPanel.isSelected(),
							prefixField.getText(),
							suffixField.getText(),
							insertField.getText(),
							positionSpinner.getModel().getValue());					
	}
	
	public List<Object> getRemovePanelConfiguration() {
		return Arrays.asList(useOptionRemovePanel.isSelected(),
							firstNSpinner.getModel().getValue(),
							lastNSpinner.getModel().getValue());
	}
	
	/**
	 * Löschen Inhalt von aller Felder von Edition Panels
	 */
	public void resetPanels() {
		resetAddEditionPanel();
		resetRemoveEditionPanel();
	}
	
	/**
	 * Löschen Inhalt von aller Felder von Remove Panel
	 */
	private void resetRemoveEditionPanel() {
		this.firstNSpinner.getModel().setValue(0);
		this.lastNSpinner.getModel().setValue(0);
	}

	/**
	 * Löschen Inhalt von aller Felder von Add Panel
	 */
	private void resetAddEditionPanel() {
		suffixField.setText("");
		prefixField.setText("");
		insertField.setText("");
		positionSpinner.getModel().setValue(0);
	}

	public void setHandlers(Map<ActionKey, Object> handlers) {
		listSelectionModel
				.addListSelectionListener((ListSelectionListener) handlers.get(ActionKey.TABLE_SELECTION_HANDLER));
		fileTree.addTreeSelectionListener(
				(TreeSelectionListener) handlers.get(ActionKey.DIRECTORY_SELECTION_HANDLER));
		prefixField.addKeyListener(
				(KeyListener) handlers.get(ActionKey.INPUT_HANDLER));
		suffixField.addKeyListener(
				(KeyListener) handlers.get(ActionKey.INPUT_HANDLER));
		insertField.addKeyListener(
				(KeyListener) handlers.get(ActionKey.INPUT_HANDLER));
		positionSpinner.addChangeListener(
				(ChangeListener) handlers.get(ActionKey.INPUT_HANDLER));
		firstNSpinner.addChangeListener(
				(ChangeListener) handlers.get(ActionKey.INPUT_HANDLER));
		lastNSpinner.addChangeListener(
				(ChangeListener) handlers.get(ActionKey.INPUT_HANDLER));
		useOptionAddPanel.addActionListener((ActionListener) handlers.get(ActionKey.INPUT_HANDLER));
		useOptionRemovePanel.addActionListener((ActionListener) handlers.get(ActionKey.INPUT_HANDLER));
		clearButton.addActionListener((ActionListener) handlers.get(ActionKey.CLEAR_EDITOR_PANELS_HANDLER));
		renameButton.addActionListener((ActionListener) handlers.get(ActionKey.RENAME_HANDLER));
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
		return frame;
	}
}
