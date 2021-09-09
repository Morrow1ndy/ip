package duke.operation;

import duke.exception.DukeException;
import duke.parser.Parser;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This is the TaskList class to contain task list and handle operations.
 */
public class TaskList {
	protected ArrayList<Task> taskList;
	private final Parser parser = new Parser();

	public TaskList() {
		this.taskList = new ArrayList<>();
	}

	public TaskList(ArrayList<Task> list) {
		this.taskList = list;
	}

	public ArrayList<Task> getTaskList() {
		return taskList;
	}

	/**
	 * Adds tasks to the storeroom.
	 *
	 * @param command   command name
	 * @param inputLine input string
	 * @throws DukeException
	 */
	public String addTask(Command command, String inputLine) throws DukeException {
		Task task;
		String guiOutput;
		switch (command) {
		case TODO: {
			parser.firstWordAndDescriptionCheck(inputLine, "todo");
			task = ToDo.splitToDO(inputLine);
			this.taskList.add(task);
			guiOutput = printAddTask(taskList.size(), task);
			break;
		}
		case DEADLINE: {
			parser.firstWordAndDescriptionCheck(inputLine, "deadline");
			task = Deadline.splitDeadline(inputLine);
			taskList.add(task);
			guiOutput = printAddTask(taskList.size(), task);
			break;
		}
		case EVENT: {
			parser.firstWordAndDescriptionCheck(inputLine, "event");
			task = Event.splitEvent(inputLine);
			taskList.add(task);
			guiOutput = printAddTask(taskList.size(), task);
			break;
		}
		default: {
			throw new DukeException(parser.getNotUnderstoodMessage());
		}
		}
		return guiOutput;
	}


	/**
	 * Marks a task as finished.
	 *
	 * @param nextLine input string
	 */
	public String finishTask(String nextLine) {
		int intValue = Integer.parseInt(nextLine.replaceAll("[^0-9]", ""));
		Task doneTask = this.taskList.get(intValue - 1);
		doneTask.doneTask();
		this.taskList.set(intValue - 1, doneTask);
		return printDoneTask(doneTask);
	}

	/**
	 * Prints task list.
	 */
	public void printList() {
		System.out.println("____________________________________________________________\n"
				+ "Here are the tasks in your list:");
		int counter = 1;
		for (Task taskForLoop : taskList) {
			System.out.println(counter
					+ "."
					+ taskForLoop);
			counter++;
		}
		System.out.println("____________________________________________________________\n");
	}

	/**
	 * Returns task list as String.
	 */
	public String getTaskListAsString() {
		Collections.sort(this.taskList);
		String message = "Here are the tasks in your list:\n";
		int counter = 1;
		for (Task taskForLoop : taskList) {
			message += counter
					+ "."
					+ taskForLoop
					+ "\n";
			counter++;
		}
		return message;
	}

	/**
	 * Prints filtered task list after FIND command.
	 */
	public void printFilteredList() {
		System.out.println("____________________________________________________________\n"
				+ "Here are the tasks found by your keyword in your list:");
		int counter = 1;
		for (Task taskForLoop : taskList) {
			System.out.println(counter
					+ "."
					+ taskForLoop);
			counter++;
		}
		System.out.println("____________________________________________________________\n");
	}

	/**
	 * Returns filtered task list.
	 */
	public String getFilteredTaskListAsString() {
		String output = "Here are the tasks found by your keyword in your list:\n";
		int counter = 1;
		for (Task taskForLoop : taskList) {
			output += counter
					+ "."
					+ taskForLoop
					+ "\n";
			counter++;
		}
		return output;
	}

	/**
	 * Deletes a task from the storeroom.
	 *
	 * @param nextLine input string
	 */
	public String deleteTask(String nextLine) {
		int intValue = Integer.parseInt(nextLine.replaceAll("[^0-9]", ""));
		Task taskToDelete = taskList.get(intValue - 1);
		taskList.remove(intValue - 1);
		return printDeleteTask(taskList.size(), taskToDelete);
	}

	/**
	 * Prints the task that is marked done.
	 */
	public String printDoneTask(Task doneTask) {
		String message = "Nice! I've marked this task as done:\n  "
				+ doneTask;
		System.out.println("____________________________________________________________\n"
				+ message
				+ "\n"
				+ "____________________________________________________________\n");
		return message;
	}

	/**
	 * Prints the message when a task is added.
	 *
	 * @param size current size of the storeroom
	 */
	public String printAddTask(int size, Task task) {
		String message = "Got it. I've added this task:\n  "
				+ task
				+ "\n"
				+ "Now you have "
				+ size
				+ " tasks in the list.";
		System.out.println("____________________________________________________________\n"
				+ message
				+ "\n"
				+ "____________________________________________________________\n");
		return message;
	}

	/**
	 * Prints the message when a task is deleted.
	 *
	 * @param size current size of the storeroom
	 */
	public String printDeleteTask(int size, Task task) {
		String message = "Noted. I've removed this task:\n  "
				+ task
				+ "\n"
				+ "Now you have "
				+ size
				+ " tasks in the list.";
		System.out.println("____________________________________________________________\n"
				+ message
				+ "\n"
				+ "____________________________________________________________\n");
		return message;

	}

	/**
	 * Find tasks by keyword in the task list.
	 *
	 * @param nextLine input String
	 */
	public TaskList findTask(String nextLine) throws DukeException {
		ArrayList<Task> filteredArrayList = new ArrayList<>();
		String keyword = nextLine.substring(5);
		if (taskList.size() == 0) {
			throw new DukeException("OOP!!! List is empty right now.");
		}
		for (Task task : taskList) {
			if (task.description.contains(keyword)) {
				filteredArrayList.add(task);
			}
		}
		return new TaskList(filteredArrayList);
	}
}
