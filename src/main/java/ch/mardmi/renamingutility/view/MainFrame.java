package ch.mardmi.renamingutility.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;

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
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import ch.mardmi.renamingutility.model.DirectoryContentModel;

public class MainFrame extends JFrame {

	private static final int PREFERRED_WIDTH = 10;
	
	private Container container;
	
	// Tabelle mit dem Inhalt des Verzeichnises
	public JTable fileTable;
	
	// Datei Baum
	private JTree fileTree;
	
	// Verzeichnis Modelle (wird für die Darstellung eines Verzeichnises verwendet)
	private DirectoryContentModel directoryModel;

	private JPanel southPanel;

	private JPanel editorPanel;

	public MainFrame(DirectoryContentModel directoryModel) {
		super();
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
	 * Erstellt Status Pane, die zeiegt Anzahl der Dateien und
	 * Selektierte Dataien an. 
	 */
	private void createStatusBar() {
		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		JLabel statusLabel = new JLabel("Some Text");
		statusLabel.setName("statusLabel");
		statusPanel.add(statusLabel);
		southPanel.add(statusPanel);
		
	}

	public DirectoryContentModel getDirectoryModel() {
		return directoryModel;
	}
	
	/**
	 * Erstellung von der Panelle mit dem Verzeichnis Baum und Ordner/Datei Tabelle, die stellt
	 * den Inhalt des gewählten Verzeichnises dar.
	 */
	private void createNavigationPanel() {
		createFileTreeNavigation();
		createFileTable();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(fileTree), new JScrollPane(fileTable));
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);
		container.add(BorderLayout.NORTH, splitPane );
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
		prefixPanel.add(prefixLabel);
		prefixPanel.add(prefixField);
		additionPanel.add(prefixPanel);
		
		// Erstellung von Panel für Suffix Addierung
		JPanel suffixPanel = new JPanel();
		JLabel suffixLabel = new JLabel("Suffix");
		JTextField suffixField = new JTextField(PREFERRED_WIDTH);
		suffixPanel.add(suffixLabel);
		suffixPanel.add(suffixField);
		additionPanel.add(suffixPanel);
		
		// Erstellung von Panel für Text Einfügung
		JPanel insertionPanel = new JPanel();
		JLabel insertLabel = new JLabel("Insert");
		JTextField insertField = new JTextField(PREFERRED_WIDTH);
		insertionPanel.add(insertLabel);
		insertionPanel.add(insertField);
		additionPanel.add(insertionPanel);
		
		// Erstellung von Panel für Text Stelle Eingabe
		JPanel positionPanel = new JPanel();
		positionPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel positionLabel = new JLabel("at pos.");
		SpinnerModel positionModel = new SpinnerNumberModel(0, -100, 100, 1);
		JSpinner positionSpinner = new JSpinner(positionModel);
		positionPanel.add(positionLabel);
		positionPanel.add(positionSpinner);
		additionPanel.add(positionPanel);

		editorPanel.add(additionPanel);
	}
	
	private void createRemoveEditionPanel() {
		JPanel removalPanel = new JPanel();
		removalPanel.setLayout(new BoxLayout(removalPanel, BoxLayout.Y_AXIS));
		removalPanel.setBorder(BorderFactory.createTitledBorder("Remove"));
		
		// Erstellung von CheckBox für die Aktivierung von Remove Option Benutzung
		JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JCheckBox useOption = new JCheckBox();
		checkBoxPanel.add(useOption);
		removalPanel.add(checkBoxPanel);
		
		JPanel spinnerPanel = new JPanel(new GridLayout(0, 4));
		
		JLabel firstNLabel = new JLabel("First n");
		spinnerPanel.add(firstNLabel);
		
		SpinnerModel firstNModel = new SpinnerNumberModel(0, -100, 100, 1);
		JSpinner firstNSpinner = new JSpinner(firstNModel);
		spinnerPanel.add(firstNSpinner);
		
		JLabel lastNLabel = new JLabel("Last n");
		spinnerPanel.add(lastNLabel);
		
		SpinnerModel lastNModel = new SpinnerNumberModel(0, -100, 100, 1);
		JSpinner lastNSpinner = new JSpinner(firstNModel);
		spinnerPanel.add(lastNSpinner);
		
		
		removalPanel.add(spinnerPanel);
		
		editorPanel.add(removalPanel);
		
	}

	private void createFileTable() {
		fileTable = new JTable(directoryModel);	
		fileTable.setName("fileTable");
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
		// TODO Auto-generated method stub
		
	}
	
	
}
