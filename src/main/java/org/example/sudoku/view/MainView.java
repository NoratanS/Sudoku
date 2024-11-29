package org.example.sudoku.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import org.example.sudoku.controller.SudokuController;
import org.example.sudoku.model.SudokuBoard;
import org.example.sudoku.model.Timer;


import java.net.URL;

public class MainView {
    private SudokuController controller;
    private SudokuBoard board;
    private Timer timer;

    public void start(Stage primaryStage) {
        GridPane boardGrid = new GridPane();
        int[][] sampleBoard = SudokuBoard.loadSampleBoard();
        board = new SudokuBoard(sampleBoard);
        controller = new SudokuController(board, boardGrid);

        VBox controlPanel = createControlPanel();

        HBox layout = new HBox(20, boardGrid, controlPanel);
        HBox.setHgrow(boardGrid, Priority.ALWAYS);
        HBox.setHgrow(controlPanel, Priority.NEVER);

        Scene scene = new Scene(layout, 700, 600);

        URL cssUrl = getClass().getResource("/org/example/sudoku/styles.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("CSS file has been loaded: " + cssUrl);
        } else {
            System.out.println("CSS file not found. Check path.");
        }

        controller.resizeBoard();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();

        timer.start();

        layout.widthProperty().addListener((obs, oldVal, newVal) -> controller.resizeBoard());
        layout.heightProperty().addListener((obs, oldVal, newVal) -> controller.resizeBoard());
    }

    private VBox createControlPanel() {
        VBox controlPanel = new VBox(20);
        controlPanel.setStyle("-fx-padding: 15; -fx-alignment: top-center;");
        controlPanel.setPrefWidth(150);

        // Timer
        Label timerLabel = new Label("00:00");
        timerLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        timer = new Timer(timerLabel);

        // Przycisk "New Game"
        Button newGameButton = new Button("New Game");
        newGameButton.setMaxWidth(Double.MAX_VALUE);
        newGameButton.setOnAction(e -> {
            board.loadSampleBoard();
            controller.reloadBoard();
            timer.reset();
            timer.start();
        });

        // Panel cyfr w układzie 3x3
        GridPane digitPanel = new GridPane();
        digitPanel.setHgap(5);
        digitPanel.setVgap(5);
        digitPanel.setStyle("-fx-padding: 10; -fx-alignment: center;");

        for (int i = 1; i <= 9; i++) {
            Button digitButton = new Button(String.valueOf(i));
            digitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            digitButton.setOnAction(e -> controller.setDigit(Integer.parseInt(digitButton.getText())));
            digitPanel.add(digitButton, (i - 1) % 3, (i - 1) / 3);
        }

        controlPanel.getChildren().addAll(timerLabel,newGameButton, digitPanel);
        return controlPanel;
    }
}
