package ch.mardmi.renamingutility.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RenameHandler extends AbstractHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						List<Integer> selectedFiles = HandlerHelper.getFilesSelected();
						gui.getDirectoryModel().rename();
						gui.resetPanels();
						HandlerHelper.getHelper().reselect(selectedFiles, gui);
					}
				}).start();
	}

}
