package ch.mardmi.renamingutility.handlers;

import java.io.File;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class DirectorySelectionHandler extends AbstractHandler implements TreeSelectionListener {

	/**
	 * Selektiertes Verzeichniss
	 */
	private File selectedDir;

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent(); 
		selectedDir = (File) node.getUserObject();
		lazyLoadSelectedDirectory(node);
		gui.getDirectoryModel().displayDir(selectedDir);
	}

	private void lazyLoadSelectedDirectory(DefaultMutableTreeNode node) {
		File[] files = gui.getFileSystemView().getFiles(selectedDir, true);
		for (File file: files) {
			if (file.isDirectory()) {
				node.add(new DefaultMutableTreeNode(file));
			}
		}
	} 
}
