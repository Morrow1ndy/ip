package duke.operation;

/**
 * This is Command class to contain command strings to separate
 * corresponding operations accordingly.
 */
public enum Command {
	TODO("todo"),
	DEADLINE("deadline"),
	EVENT("event"),
	LIST("list"),
	DONE("done"),
	DELETE("delete"),
	CLEAR("clear"),
	FIND("find"),
	BYE("bye"),
	INVALID_COMMAND("");

	private String commandWord;

	Command(String commandWord) {
		this.commandWord = commandWord;
	}

	/**
	 * Returns Command enum.
	 *
	 * @param input input String of a line
	 * @return Command enum
	 */
	public static Command getCommandWordFromString(String input) {
		String firstWord = input.split(" ", 2)[0];
		switch (firstWord) {
		case "todo": {
			return TODO;
		}
		case "deadline": {
			return DEADLINE;
		}
		case "event": {
			return EVENT;
		}
		case "list": {
			return LIST;
		}
		case "done": {
			return DONE;
		}
		case "delete": {
			return DELETE;
		}
		case "bye": {
			return BYE;
		}
		default: {
			return INVALID_COMMAND;
		}
		}
	}
}

