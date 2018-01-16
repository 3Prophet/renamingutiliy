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

import ch.mardmi.renamingutility.handlers.AbstractHandler;
import ch.mardmi.renamingutility.handlers.ActionKey;
import ch.mardmi.renamingutility.handlers.DirectorySelectionHandler;
import ch.mardmi.renamingutility.handlers.TableModelChangeHandler;
import ch.mardmi.renamingutility.model.DirectoryContentModel;
import ch.mardmi.renamingutility.model.StatusModel;
import ch.mardmi.renamingutility.model.FolderTreeCellRenderer;

public class MainFrame extends JFrame {

	private static final int PREFERRED_WIDTH = 10;

	// Action Handlers
	private static Map<ActionKey, Object> handlers;

	private Container container;

	// Tabelle mit dem Inhalt des Verzeichnises
	private JTable fileTable;

	public JTable getFileTable() {
		return fileTable;
	}

	private JScrollPane fileTreePane;

	// Verzeichnis Modelle (wird für die Darstellung eines Verzeichnises
	// verwendet)
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
	 * Erstellt Status Pane, die zeiegt Anzahl der Dateien und Selektierte
	 * Dataien an.
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
	 * Erstellung von der Panelle mit dem Verzeichnis Baum und Ordner/Datei
	 * Tabelle, die stellt den Inhalt des gewählten Verzeichnises dar.
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

		// Erstellung von CheckBox für die Aktivierung von Add Option Benutzung
		JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JCheckBox useOption = new JCheckBox();
		checkBoxPanel.add(useOption);
		additionPanel.add(checkBoxPanel);

		// Erstellung von Panel für Prefix Addierung
		JPanel prefixPanel = new JPanel();
		JLabel prefixLabel = new JLabel("Prefix");
		JTextField prefixField = new JTextField(PREFERRED_WIDTH);
		// ActionListener implementieren, um auf Eingaben zu reagieren
		prefixField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (useOption.isEnabled()) {
//					ActionsAdd.actionAddDoSomething1();
				}
			}
		});
		prefixPanel.add(prefixLabel);
		prefixPanel.add(prefixField);
		additionPanel.add(prefixPanel);

		// Erstellung von Panel für Suffix Addierung
		JPanel suffixPanel = new JPanel();
		JLabel suffixLabel = new JLabel("Suffix");
		JTextField suffixField = new JTextField(PREFERRED_WIDTH);
		// ActionListener implementieren, um auf Eingaben zu reagieren
		suffixField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (useOption.isEnabled()) {
					//ActionsAdd.actionAddDoSomething1();
				}
			}
		});
		suffixPanel.add(suffixLabel);
		suffixPanel.add(suffixField);
		additionPanel.add(suffixPanel);

		// Erstellung von Panel für Text Einfügung
		JPanel insertionPanel = new JPanel();
		JLabel insertLabel = new JLabel("Insert");
		JTextField insertField = new JTextField(PREFERRED_WIDTH);
		// ActionListener implementieren, um auf Eingaben zu reagieren
		insertField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (useOption.isEnabled()) {
//					ActionsAdd.actionAddDoSomething1();
				}
			}
		});
		insertionPanel.add(insertLabel);
		insertionPanel.add(insertField);
		additionPanel.add(insertionPanel);

		// Erstellung von Panel für Text Stelle Eingabe
		JPanel positionPanel = new JPanel();
		positionPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel positionLabel = new JLabel("at pos.");
		SpinnerModel positionModel = new SpinnerNumberModel(0, -100, 100, 1);
		JSpinner positionSpinner = new JSpinner(positionModel);
		// ChangeListener (anstelle ActionListener) implementieren, um auf
		// Eingaben zu reagieren
		positionSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (useOption.isEnabled()) {
					//ActionsAdd.actionAddDoSomething1();
				}
			}
		});
		positionPanel.add(positionLabel);
		positionPanel.add(positionSpinner);
		additionPanel.add(positionPanel);

		editorPanel.add(additionPanel);
	}

	private void createRemoveEditionPanel() {
		JPanel removalPanel = new JPanel();
		removalPanel.setLayout(new BoxLayout(removalPanel, BoxLayout.Y_AXIS));
		removalPanel.setBorder(BorderFactory.createTitledBorder("Remove"));

		// Erstellung von CheckBox für die Aktivierung von Remove Option
		// Benutzung
		JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JCheckBox useOption = new JCheckBox();
		checkBoxPanel.add(useOption);
		removalPanel.add(checkBoxPanel);

		JPanel spinnerPanel = new JPanel(new GridLayout(0, 4));

		JLabel firstNLabel = new JLabel("First n");
		spinnerPanel.add(firstNLabel);

		SpinnerModel firstNModel = new SpinnerNumberModel(0, -100, 100, 1);
		JSpinner firstNSpinner = new JSpinner(firstNModel);
		// ChangeListener (anstelle ActionListener) implementieren, um auf
		// Eingaben zu reagieren
		firstNSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (useOption.isEnabled()) {
					//ActionsAdd.actionAddDoSomething1();
				}
			}
		});
		spinnerPanel.add(firstNSpinner);

		JLabel lastNLabel = new JLabel("Last n");
		spinnerPanel.add(lastNLabel);

		SpinnerModel lastNModel = new SpinnerNumberModel(0, -100, 100, 1);
		JSpinner lastNSpinner = new JSpinner(lastNModel);
		// ChangeListener (anstelle ActionListener) implementieren, um auf
		// Eingaben zu reagieren
		lastNSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (useOption.isEnabled()) {
					//ActionsAdd.actionAddDoSomething1();
				}
			}
		});
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
		// Wurzel Knoten vom Dateibaum
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();

		// Zugang zu Dateisystem
		fileSystemView = FileSystemView.getFileSystemView();

		// Bekommen von Wurzelverzeichnisse
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
	}

	/**
	 * Factory Metode für GBO Erstellung
	 * 
	 * @param dirModel
	 *            Das Model für die Ordnertabelle
	 * @param statusModel
	 *            Das Model für die Statuszeile
	 * @param handlers
	 *            Action Listeners für vershiedene Komponenten
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
