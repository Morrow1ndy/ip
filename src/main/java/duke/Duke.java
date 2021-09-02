package duke;

import duke.operation.TaskList;
import duke.storage.Storage;
import duke.ui.Ui;
import duke.Parser.Parser;
import duke.exception.DukeException;
import duke.operation.Command;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Duke is the smart assistance to help you track personal tasks.
 * This is the Driver class for Duke application.
 */
public class Duke extends Application {
//	private Image userImage = new Image("/DaUser.png");
//	private Image dukeImage = new Image("/DaDuke.png");
	private ScrollPane scrollPane;
	private VBox dialogContainer;
	private TextField userInput;
	private Button sendButton;
	private AnchorPane mainLayout;
	private Scene scene;

	public static void main(String[] args) {
		Ui ui = new Ui();
		ui.showWelcomeMessage();
		Storage storage = new Storage();
		storage.createFile();

		// ask for user input
		Scanner in = new Scanner(System.in);
		String nextLine = in.nextLine();
		Command nextCommand = Command.getCommandWordFromString(nextLine);
		TaskList taskList = new TaskList();

		while (true) {
			try {
				if (nextCommand == Command.BYE) {
					ui.showByeMessage();
					break;
				} else if (nextCommand == Command.FIND) {
					taskList.findTask(nextLine);
				} else if (nextCommand == Command.TODO) {
					taskList.addTask(nextCommand, nextLine);
				} else if (nextCommand == Command.DEADLINE) {
					taskList.addTask(nextCommand, nextLine);
				} else if (nextCommand == Command.EVENT) {
					taskList.addTask(nextCommand, nextLine);
				} else if (nextCommand == Command.LIST) {
					taskList.printList();
				} else if (nextCommand == Command.DONE) {
					taskList.finishTask(nextLine);
				} else if (nextCommand == Command.DELETE) {
					taskList.deleteTask(nextLine);
				} else {
					Parser.invalidTask();
				}
			} catch (DukeException e) {
				storage.updateLog(e.toString());
			}
			nextLine = in.nextLine();
			nextCommand = Command.getCommandWordFromString(nextLine);
			storage.updateFile(taskList);
		}
		in.close();
	}

	/**
	 * Shows GUI.
	 *
	 * @param stage Stage object.
	 */
	@Override
	public void start(Stage stage) {
		scrollPane = new ScrollPane();
		dialogContainer = new VBox();
		scrollPane.setContent(dialogContainer);

		userInput = new TextField();
		sendButton = new Button("Send");

		mainLayout = new AnchorPane();
		mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

		scene = new Scene(mainLayout);

		stage.setTitle("Duke");
		stage.setResizable(false);
		stage.setMinHeight(600.0);
		stage.setMinWidth(400.0);

		mainLayout.setPrefSize(400.0, 600.0);

		scrollPane.setPrefSize(385, 535);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		scrollPane.setVvalue(1.0);
		scrollPane.setFitToWidth(true);

		dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

		userInput.setPrefWidth(325.0);

		sendButton.setPrefWidth(55.0);

		AnchorPane.setTopAnchor(scrollPane, 1.0);

		AnchorPane.setBottomAnchor(sendButton, 1.0);
		AnchorPane.setRightAnchor(sendButton, 1.0);

		AnchorPane.setLeftAnchor(userInput, 1.0);
		AnchorPane.setBottomAnchor(userInput, 1.0);

		// handle events
		sendButton.setOnMouseClicked(event -> {
			handleUserInput();
		});

		userInput.setOnAction(event -> {
			handleUserInput();
		});

		//Scroll down to the end every time dialogContainer's height changes.
		dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
	 * the dialog container. Clears the user input after processing.
	 */
	private void handleUserInput() {
		Label userTextLabel = new Label(userInput.getText());
		Label dukeTextLabel = new Label(getResponse(userInput.getText()));
		dialogContainer.getChildren().addAll(
//				new DialogBox(userTextLabel, new ImageView(userImage)),
//				new DialogBox(dukeTextLabel, new ImageView(dukeImage))
				new DialogBox(userTextLabel),
				new DialogBox(dukeTextLabel)
		);
		userInput.clear();
	}

	private String getResponse(String userInput) {
		try {
			Path dir = Paths.get(System.getProperty("user.dir") + "/src/main/java/output");
			String dirPathString = dir.toString();
			File logTxt = new File(dirPathString + "/log.txt");
			BufferedReader reader = new BufferedReader(new FileReader(logTxt));
			String outputString = reader.readLine();

			// clear log.txt
			PrintWriter pw = new PrintWriter("log.txt");
			pw.close();

			return outputString;
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	private class DialogBox extends HBox {
		private Label text;
//		private ImageView displayPicture;

		public DialogBox(Label l) {
			text = l;
//			displayPicture = iv;

			text.setWrapText(true);
//			displayPicture.setFitWidth(100.0);
//			displayPicture.setFitHeight(100.0);

			this.setAlignment(Pos.TOP_RIGHT);
//			this.getChildren().addAll(text, displayPicture);
			this.getChildren().addAll(text);
		}

	}
}
