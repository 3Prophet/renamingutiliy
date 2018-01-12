import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;

public class MyTree extends JFrame {
	
	private static final int PREFERRED_WIDTH = 10;
	
	Container container;
	
	JScrollPane fileTree;
	JScrollPane fileTable;
	
	/** Gateway to file system view */
    private FileSystemView fileSystemView;
    private JLabel label;
    private File rootDirectory;

	private JPanel southPanel;

	private JPanel editorPanel;
	
    /**
     * Lazy loads expanded directory
     */
    private class DirectoryLazyLoadCommand implements TreeWillExpandListener {

		@Override
		public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
			label.setText("Hey");
			
		}

		@Override
		public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
    private class SelectDirectoryCommand implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent(); 
			lazyLoadSelectedDirectory(node);
		}
		
		private void lazyLoadSelectedDirectory(DefaultMutableTreeNode node) {
			File dir = (File) node.getUserObject();
			File[] files = fileSystemView.getFiles(dir, true);
			for (File file: files) {
				if (file.isDirectory()) {
					node.add(new DefaultMutableTreeNode(file));
				}
			}
		}
    	
    }
    
    private class LazyCommand implements TreeExpansionListener {

		@Override
		public void treeExpanded(TreeExpansionEvent event) {
			label.setText("Hey");
			
		}

		@Override
		public void treeCollapsed(TreeExpansionEvent event) {
			// TODO Auto-generated method stub
			
		}
    	
    }
	
	public MyTree() {
		super();
		setTitle("File Renaming Utility");
		container = getContentPane();
		((JComponent) container).setBorder(new EmptyBorder(2, 2, 2, 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createNavigationPanel();
		createEditorPanel();
		southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		container.add(BorderLayout.SOUTH, southPanel);
		createButtonPanel();
		createStatusBar();
		
		setSize(500, 300);
		setVisible(true);
		
	}
	
	
	private void createEditorPanel() {
		editorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		createAddEditionPanel();
		createRemoveEditionPanel();
		container.add(BorderLayout.CENTER, editorPanel);
		
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


	private void createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton renameButton = new JButton("Rename");
		buttonPanel.add(renameButton);
		southPanel.add(buttonPanel);
	}


	private void createStatusBar() {
		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		JLabel statusLabel = new JLabel("Some Text");
		statusPanel.add(statusLabel);
		southPanel.add(statusPanel);
		
	}


	private void createNavigationPanel() {
		createFileTreeNavigation();
		createFileTable();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                fileTree, fileTable);
splitPane.setOneTouchExpandable(true);
splitPane.setDividerLocation(150);
		container.add(BorderLayout.NORTH, splitPane );
	}


	private void createFileTable() {
		JTable table = new JTable(new DirectoryContentModel(new File("/")));
		
		fileTable = new JScrollPane(table);
		
	}


	private void createFileTreeNavigation() {
		// Root node of the file tree
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		
		// Get gateway to file system
		fileSystemView = FileSystemView.getFileSystemView();
		
		// Getting root directories
		File[] fileSystemRoot = fileSystemView.getRoots();
		
		for (File rootDir: fileSystemRoot) {
			DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootDir);
			rootDirectory = rootDir;
			root.add(rootNode);
			for (File file: fileSystemView.getFiles(rootDir, true)) {
				if (file.isDirectory()) {
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
					rootNode.add(node);
				}
			}
		}
		
		// 
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		
		JTree tree = new JTree(treeModel);
		tree.setRootVisible(false);
		tree.expandRow(0);
		tree.setCellRenderer(new FolderTreeCellRenderer());
		//tree.addTreeWillExpandListener(new DirectoryLazyLoadCommand());
		//tree.addTreeExpansionListener(new LazyCommand());
		tree.addTreeSelectionListener(new SelectDirectoryCommand());
		fileTree = new JScrollPane(tree);
			
	}
	



	public static void main(String[] args) {
		new MyTree();

	}

}

/** A TreeCellRenderer for a Folder. */
class FolderTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;

	private FileSystemView fileSystemView;

    private JLabel label;

    FolderTreeCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean selected,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        File file = (File)node.getUserObject();
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        label.setToolTipText(file.getPath());

        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }
}

/**
class FolderCellRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	private FileSystemView fileSystemView;

    private JLabel label;

    FolderTreeCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean selected,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        File file = (File)node.getUserObject();
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        label.setToolTipText(file.getPath());

        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }

	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}
}


*/



class DirectoryContentModel extends AbstractTableModel {
	
	private String[] columnNames = {"Icon", "Name", "New Name", "Size", "Modified"};
	private List<File> rows;
	private FileSystemView fileSystemView;
	private File path;
	
	public DirectoryContentModel(File path) {
		super();
		fileSystemView = FileSystemView.getFileSystemView();
		rows = new ArrayList<File>();
		this.path = path;
		displayDir(path);
	}
	
	public void displayDir(File dir) {
		rows.clear();
		for (File file: fileSystemView.getFiles(dir, true)) {
			rows.add(file);
		}
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0: return fileSystemView.getSystemIcon(rows.get(rowIndex));
		case 1: return fileSystemView.getSystemDisplayName(rows.get(rowIndex));
		case 2: return fileSystemView.getSystemDisplayName(rows.get(rowIndex));
		case 3: return rows.get(rowIndex).length();
		case 4:	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				return sdf.format(rows.get(rowIndex).lastModified());
		}
		return null;
	}
	
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:return ImageIcon.class;
            case 3:return Long.class;
            //case 4:return Date.class;
            default: return String.class;
        }
    }
	
	@Override
	 public String getColumnName(int col) {
        return columnNames[col].toString();
    }
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
}



