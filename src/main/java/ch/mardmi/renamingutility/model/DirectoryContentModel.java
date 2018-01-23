package ch.mardmi.renamingutility.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;

/**
 * Ein Modell für die Datei-Tabelle. Diese stellt den Inhalt des
 * Verzeichnisses dar. Dafür werden 5 Spalten verwendet:
 * "Icon", "Name", "New Name", "Size", "Modified".
 * In der Spalte "New Name" wird ein hipotetischer neuer Name der
 * umbenannten Datei angezeigt.
 *
 */
public class DirectoryContentModel extends AbstractTableModel {

	private String[] columnNames = {"Icon", "Name", "New Name", "Size", "Modified"};
	
	// Verzeichnisinhalt
	//private List<File> files;
	
	private List<FileState> files;
	
	//private List<File> selectedFiles;
	
	private FileSystemView fileSystemView;
	
	/**
	 * @param dir Verzeichnispfad, dessen Inhalt dargestellt werden soll.
	 */
	public DirectoryContentModel(File dir) {
		super();
		fileSystemView = FileSystemView.getFileSystemView();
		//files = new ArrayList<File>();
		files = new ArrayList<FileState>();
		displayDir(dir);
	}
	/**
	/**
	 * @return selectedFiles Liste der ausgewählten Dateien
	 */
	/**
	public List<File> getSelectedFiles() {
		return selectedFiles;
	}*/

	/**
	 * @param selectedFiles Setze Datei-Auswahl
	 */
	/**
	public void setSelectedFiles(List<File> selectedFiles) {
		this.selectedFiles = selectedFiles;
	}*/

	/**
	 * @param dir Pfad zum Verzeichnis, dessen Inhalt dargestellt werden soll.
	 */
	public void displayDir(File dir) {
		files.clear();
		for (File file: fileSystemView.getFiles(dir, true)) {
			if (file.isFile()) {
				files.add(new FileState(file));
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
		case 0: return fileSystemView.getSystemIcon(files.get(rowIndex).getCurrentFileState());
		case 1: return fileSystemView.getSystemDisplayName(files.get(rowIndex).getCurrentFileState());
		case 2: return fileSystemView.getSystemDisplayName(files.get(rowIndex).getNewFileState());
		case 3: return files.get(rowIndex).getCurrentFileState().length();
		case 4:	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				return sdf.format(files.get(rowIndex).getCurrentFileState().lastModified());
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
