package duke;

import javafx.application.Application;

/**
 * This is the launcher class to workaround classpath issues.
 */
public class Launcher {
	public static void main(String[] args) {
		Application.launch(Duke.class, args);
	}
}