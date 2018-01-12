package ch.mardmi.renamingutility;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestFactory {

	public static Path getTestDirectory() {
		return Paths.get("src", "test", "resources");
	}
}
