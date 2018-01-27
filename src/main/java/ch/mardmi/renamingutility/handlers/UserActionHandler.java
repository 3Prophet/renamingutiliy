package ch.mardmi.renamingutility.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UserActionHandler extends AbstractHandler implements KeyListener, ChangeListener {



	@Override
	public void stateChanged(ChangeEvent e) {
		new Thread(
				new Runnable() {

					@Override
					public void run() {
						HandlerHelper.getHelper().execute(gui);
						
					}
					
				}).start();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		new Thread(
				new Runnable() {

					@Override
					public void run() {
						HandlerHelper.getHelper().execute(gui);
						
					}
					
				}).start();
		
	}

}
