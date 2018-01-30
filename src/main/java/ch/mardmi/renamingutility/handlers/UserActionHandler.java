package ch.mardmi.renamingutility.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Wird aufgerufen, wenn  Benutzer mit den Komponenten von Editor Platte interagiert.
 * 
 * @author Dmitry Logvinovich
 *
 */
public class UserActionHandler extends AbstractHandler implements KeyListener, ChangeListener, ActionListener {
	
	/**
	 * Wird aufgerufen when Benutzer mit Spinner von Editor Platten interagiert.
	 */
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
	
	/**
	 * Wird aufgerufen wenn Benutzer etwas in Text Felder eintippt.
	 */
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
	
	/**
	 * Wird aufgerufen when Check Boxes von Editor Platten selktiert oder deselektiert werden.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						HandlerHelper.getHelper().execute(gui);
						
					}					
				}).start();
		
	}

}
