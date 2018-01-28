package ch.mardmi.renamingutility.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearActionHandler extends AbstractHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						gui.resetPanels();
						HandlerHelper.getHelper().execute(gui);
					}
				});
	}
}
