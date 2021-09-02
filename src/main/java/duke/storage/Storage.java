package duke.storage;

import duke.operation.Task;
import duke.operation.TaskList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This is the Storage class to load and save tasks in file.
 */
public class Storage {
	private Path dir = Paths.get(System.getProperty("user.dir") + "/src/main/java/output");
	String dirPathString = dir.toString();

	/**
	 * Creates duke.txt to contain task list, if not exists.
	 */
	public void createFile() {
		if (!Files.exists(dir)) {
			try {
				Files.createDirectories(dir);
			} catch (IOException e) {
				System.out.println("Unexpected IO error occurred.");
			}
		}
		try {
			String dukePath = dirPathString + "/duke.txt";
			String logPath = dirPathString + "/log.txt";
			File dukeFile = new File(dukePath);
			File logFile = new File(logPath);
			dukeFile.createNewFile();
			logFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Unexpected IO error occurred.");
		}
	}

	/**
	 * Updates task list inside duke.txt.
	 */
	public void updateFile(TaskList storeRoom) {
		try {
			String path = dirPathString + "/duke.txt";
			FileWriter fileWriter = new FileWriter(path);
			for (Task task : storeRoom.getTaskList()) {
				fileWriter.write(task.toString());
				fileWriter.write("\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Unexpected IO error occurred");
		}
	}

	public void updateLog(String newLog) {
		try {
			String path = dirPathString + "/log.txt";
			FileWriter fileWriter = new FileWriter(path);
			fileWriter.write(newLog);
			fileWriter.write("\n");
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Unexpected IO error occurred");
		}
	}
}
