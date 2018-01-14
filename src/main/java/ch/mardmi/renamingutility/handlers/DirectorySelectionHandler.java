package ch.mardmi.renamingutility.handlers;

import java.io.File;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class DirectorySelectionHandler extends AbstractHandler implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent(); 
			lazyLoadSelectedDirectory(node);
		}
		
		private void lazyLoadSelectedDirectory(DefaultMutableTreeNode node) {
			File dir = (File) node.getUserObject();
			File[] files = gui.getFileSystemView().getFiles(dir, true);
			for (File file: files) {
				if (file.isDirectory()) {
					node.add(new DefaultMutableTreeNode(file));
				}
			}
		} 
}
