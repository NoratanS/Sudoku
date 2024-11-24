package org.example.sudoku.view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.sudoku.controller.SudokuController;
import org.example.sudoku.model.SudokuBoard;

import java.net.URL;

public class MainView {
    public void start(Stage primaryStage) {
        GridPane boardGrid = new GridPane();
        int[][] sampleBoard = SudokuBoard.loadSampleBoard();
        SudokuBoard board = new SudokuBoard(sampleBoard);
        SudokuController controller = new SudokuController(board, boardGrid);


        VBox layout = new VBox(10, boardGrid);
        VBox.setVgrow(boardGrid, Priority.ALWAYS);

        Scene scene = new Scene(layout, 600, 600);

        URL cssUrl = getClass().getResource("/org/example/sudoku/styles.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("CSS file has been loaded: " + cssUrl);
        } else {
            System.out.println("CSS file not found. Check path.");
        }


        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();
    }
}
