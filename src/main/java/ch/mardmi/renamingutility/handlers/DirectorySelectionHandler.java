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
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					gui.getFileTree().setEnabled(false);
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
					selectedDir = (File) node.getUserObject();
					lazyLoadSelectedDirectory(node);
					gui.getDirectoryModel().displayDir(selectedDir);
					gui.getFileTree().setEnabled(true);
				}
			}).start();		
	}
	
	/**
	 * Lazy Loadung von Verzeichniss Inhalt. 
	 * 
	 * @param node Eine selektierte Knote
	 */
	private void lazyLoadSelectedDirectory(DefaultMutableTreeNode node) {
		// Um eine Knote mehrmals mit der Kinder nicht einzupflegen
		if (node.getChildCount() != 0) {
			return;
		}
		File[] files = gui.getFileSystemView().getFiles(selectedDir, true);
		for (File file : files) {
			if (file.isDirectory()) {
				node.add(new DefaultMutableTreeNode(file));
			}
		}
	}
}
