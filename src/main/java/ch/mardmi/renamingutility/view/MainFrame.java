package ch.mardmi.renamingutility.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;

import ch.mardmi.renamingutility.model.DirectoryContentModel;

public class MainFrame extends JFrame {
	
	private Container container;
	
	// Tabelle mit dem Inhalt des Verzeichnises
	public JTable fileTable;
	
	// Datei Baum
	private JTree fileTree;
	
	// Verzeichnis Modelle (wird für die Darstellung eines Verzeichnises verwendet)
	private DirectoryContentModel directoryModel;

	private JPanel southPanel;

	public MainFrame(DirectoryContentModel directoryModel) {
		super();
		this.directoryModel = directoryModel;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container = this.getContentPane();
		setTitle("Renaming Utility");
		
		createNavigationPanel();
		southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		container.add(BorderLayout.SOUTH, southPanel);
		
		createStatusPane();
		
		setVisible(true);
	}
	
	/**
	 * Erstellt Status Pane, die zeiegt Anzahl der Dateien und
	 * Selektierte Dataien an. 
	 */
	private void createStatusPane() {
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

	private void createFileTable() {
		fileTable = new JTable(directoryModel);	
		fileTable.setName("fileTable");
	}

	private void createFileTreeNavigation() {
		// TODO Auto-generated method stub
		
	}
	
	
}
