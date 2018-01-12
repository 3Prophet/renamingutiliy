package ch.mardmi.renamingutility;

import java.nio.file.Paths;

import javax.swing.JFrame;

import ch.mardmi.renamingutility.model.DirectoryContentModel;
import ch.mardmi.renamingutility.view.MainFrame;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{
    public static void main( String[] args ) {
       new MainFrame(new DirectoryContentModel(Paths.get("").toFile()));
    }

}
