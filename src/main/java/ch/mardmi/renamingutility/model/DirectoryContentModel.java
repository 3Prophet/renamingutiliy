package ch.mardmi.renamingutility.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;

/**
 * Eine Modelle für die Datei Tabelle. Datei Tabelle
 * stellt Inhalt des Verzeichnisses dar. Dafür werden 5
 * Spalten benutzt: "Icon", "Name", "New Name", "Size", "Modified".
 * In der Spalte "New Name" wird einen hipotetischen neuen Name der
 * übernomenden Datei gezeigt.
 *
 */
public class DirectoryContentModel extends AbstractTableModel {

	private String[] columnNames = {"Icon", "Name", "New Name", "Size", "Modified"};
	
	// Ihnalt des Verzeichnises
	private List<File> files;
	
	private List<File> selectedFiles;
	
	private FileSystemView fileSystemView;
	
	/**
	 * @param dir Pfad zum Verzeichnis, dessen Inhalt dargestellt werden soll.
	 */
	public DirectoryContentModel(File dir) {
		super();
		fileSystemView = FileSystemView.getFileSystemView();
		files = new ArrayList<File>();
		displayDir(dir);
	}
	
	public List<File> getSelectedFiles() {
		return selectedFiles;
	}

	public void setSelectedFiles(List<File> selectedFiles) {
		this.selectedFiles = selectedFiles;
	}

	/**
	 * @param dir Pfad zum Verzeichnis, dessen Inhalt dargestellt werden soll.
	 */
	public void displayDir(File dir) {
		files.clear();
		for (File file: fileSystemView.getFiles(dir, true)) {
			if (file.isFile()) {
				files.add(file);
			}
		}
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return files.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0: return fileSystemView.getSystemIcon(files.get(rowIndex));
		case 1: return fileSystemView.getSystemDisplayName(files.get(rowIndex));
		case 2: return fileSystemView.getSystemDisplayName(files.get(rowIndex));
		case 3: return files.get(rowIndex).length();
		case 4:	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				return sdf.format(files.get(rowIndex).lastModified());
		}
		return null;
	}
	
	/**
	 * Klasse jeder Spalte
	 */
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
