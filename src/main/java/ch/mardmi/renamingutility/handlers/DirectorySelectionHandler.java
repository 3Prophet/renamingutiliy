package ch.mardmi.renamingutility.handlers;

import java.io.File;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Wird aufgeruft, wenn ein Verzeichniss in JTree selektiert wird. 
 * Macht Ladung von Verzeichniss Ihnalt und aktualisiert {@link ch.mardmi.renamingutility.model.DirectoryContentModel},
 * so dass die entsprechende JTable Inhalt vom selektierten Verzeichniss anzeigt.
 * 
 * @author Dmitry Logvinovich
 *
 */
public class DirectorySelectionHandler extends AbstractHandler implements TreeSelectionListener {

	/**
	 * Pfad zu dem selektierten Verzeichnis.
	 */
	private File selectedDir;
	
	/**
	 * Aufgeruft, wenn ein Verzeichniss in JTree Selektiert wird. 
	 * Macht Ladung von Verzeichniss Ihnalt und aktualisiert {@link ch.mardmi.renamingutility.model.DirectoryContentModel},
	 * so dass die entsprechende JTable Inhalt vom selektierten Verzeichniss anzeigt.
	 * 
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
		selectedDir = (File) node.getUserObject();
		lazyLoadSelectedDirectory(node);
		gui.getDirectoryModel().displayDir(selectedDir);
	}
	
	/**
	 * Lazy Loadung von Verzeichniss Inhalt. 
	 * 
	 * @param node Eine selektierte Knote
	 */
	private void lazyLoadSelectedDirectory(DefaultMutableTreeNode node) {
		File[] files = gui.getFileSystemView().getFiles(selectedDir, true);
		for (File file : files) {
			if (file.isDirectory()) {
				node.add(new DefaultMutableTreeNode(file));
			}
		}
	}
}
