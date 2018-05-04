package gui;

import java.awt.TextField;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import elevator.ElevatorImp;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import simulator.Simulator;

public class ElevatorApplication extends Application implements Observer {

	private static final int FLOOR_COUNT = 30;

	private Label floors[];
	Simulator simu;
	private int targetFloor;
	private int currentFloor = 0;
	private int powerUsed = 0;
	private Text powerUsedText;
	private TextFlow infoField;
	private BorderPane mainPane;

	private Queue<List<Integer>> queue = new LinkedList<>();

	@Override
	public void init() throws Exception {
		super.init();
		simu = new Simulator(this);
		floors = new Label[FLOOR_COUNT];
		for (int i = 0; i < FLOOR_COUNT; i++) {
			floors[i] = new Label();
			floors[i].setId("empty");
		}
		floors[0].setId("elevator");
	}

	@Override
	public void start(Stage primaryStage) {
		GridPane gridfloor = new GridPane();
		for (int i = FLOOR_COUNT - 1; i >= 0; i--) {
			gridfloor.add(floors[i], 0, (FLOOR_COUNT - 1) - i);
		}
		infoField = new TextFlow();
		powerUsedText = new Text("Power used: " + powerUsed);
		infoField.getChildren().add(powerUsedText);
		mainPane = new BorderPane();
		mainPane.setMinWidth(500);
		mainPane.setLeft(gridfloor);
		mainPane.setCenter(infoField);
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add("elevator.css");
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode()) {
				elevatorAnime.stop();
				primaryStage.hide();
			}
		});
		primaryStage.setScene(scene);
		primaryStage.setTitle("Elevator Simulator");
		primaryStage.show();
		simu.start();
		elevatorAnime.start();
	}

	AnimationTimer elevatorAnime = new AnimationTimer() {
		private static final long SECOND = 1000000000l;
		private static final long NORMAL_SPEED = SECOND / 6;
		private static final long SLOW_SPEED = SECOND / 2;
		private long checkTime = NORMAL_SPEED;
		private long lastUpdate = 0;
		private int step;
		private boolean newTurn = true;

		@Override
		public void handle(long now) {

			if (queue.isEmpty() || now - lastUpdate < checkTime) {
				return;
			}
			lastUpdate = now;
			currentFloor = queue.peek().get(0);
			targetFloor = queue.peek().get(1);
			powerUsed = queue.peek().get(2);
			System.out.println("Running");
			floors[currentFloor].setId("current");
			if (currentFloor > 0) {
				floors[currentFloor - 1].setId("empty");
			}
			if (currentFloor < FLOOR_COUNT - 1) {
				floors[currentFloor + 1].setId("empty");
			}
			if (currentFloor != targetFloor) {
				floors[targetFloor].setId("target");
			}
			infoField.getChildren().clear();
			powerUsedText = new Text("Power used: " + powerUsed);
			infoField.getChildren().add(powerUsedText);
			mainPane.setCenter(infoField);
			queue.poll();

			if (queue.isEmpty()) {
				this.stop();
			}
		};

	};

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if (arg != null && arg instanceof ArrayList<?>) {
			queue.add((ArrayList<Integer>) arg);
			if (queue.size() >= 1) {
				elevatorAnime.start();
			}
		}
	}
}