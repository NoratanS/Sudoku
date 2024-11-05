package org.example.sudoku.controller;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.example.sudoku.model.*;


public class SudokuController {
    private SudokuBoard board;
    private GridPane boardGrid;

    public SudokuController(SudokuBoard board, GridPane boardGrid) {
        this.board = board;
        this.boardGrid = boardGrid;
        initializeBoard();
    }

    private void initializeBoard() {
        boardGrid.getStyleClass().add("grid");

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField cellField = new TextField();
                cellField.getStyleClass().add("cell");

                int value = board.getCell(i,j).getValue();
                if (value != 0) {
                    cellField.setText(String.valueOf(value));
                    cellField.setEditable(false);
              } else {
                    cellField.setEditable(true);
                    addInputRestriction(cellField);
                }
                boardGrid.add(cellField, j, i);
            }
        }
    }

    private void addInputRestriction(TextField cellField) {
        cellField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[1-9]?")) {
                cellField.setText(oldValue);
            }
        });
    }
}
