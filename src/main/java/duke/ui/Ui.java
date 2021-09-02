package duke.ui;

import duke.Duke;
import duke.storage.Storage;


/**
 * This is the Display class to handle UI of Duke.
 */
public class Ui {
	private Storage storage = new Storage();

	/**
	 * Prints welcome message.
	 */
	public void showWelcomeMessage() {
		String welcomeString = "____________________________________________________________\n"
				+ "Yo! Duke here...on behalf of Yang Yuzhao.\n"
				+ "What do ya want from me?\n"
				+ "____________________________________________________________\n";
		storage.updateLog(welcomeString);
	}

	/**
	 * Prints bye message.
	 */
	public void showByeMessage() {
		String byeString = "____________________________________________________________\n"
				+ "Duke out! Wake me up when ya need me again:)\n"
				+ "____________________________________________________________\n";
		storage.updateLog(byeString);
	}
}
