package org.example.sudoku.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.example.sudoku.model.*;

import java.util.Objects;


public class SudokuController {
    private SudokuBoard board;
    private GridPane boardGrid;
    private TextField selectedCell;

    public SudokuController(SudokuBoard board, GridPane boardGrid) {
        this.board = board;
        this.boardGrid = boardGrid;
        initializeBoard(board);
    }

    private void initializeBoard(SudokuBoard board) {
        boardGrid.getStyleClass().add("grid");

        boardGrid.getColumnConstraints().clear();
        boardGrid.getRowConstraints().clear();

        for (int i = 0; i < 9; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.ALWAYS);
            boardGrid.getColumnConstraints().add(colConstraints);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            boardGrid.getRowConstraints().add(rowConstraints);
        }

        boardGrid.getChildren().clear(); // Usuń stare komórki (ważne przy reload)

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cellField = new TextField();
                cellField.getStyleClass().add("cell");
                cellField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                int value = board.getCell(row,col).getValue();
                if (value != 0) {
                    cellField.setText(String.valueOf(value));
                    cellField.setEditable(false);
                } else {
                    cellField.setEditable(true);
                    addInputRestriction(cellField);
                }

                int finalRow = row;
                int finalCol = col;
                cellField.setOnMouseClicked(event -> {
                    handleCellClick(finalRow, finalCol, cellField);
                });


                if (row % 3 == 0 && col % 3 == 0) {
                    cellField.getStyleClass().add("segment-border-top-left-corner");
                }

                if (row % 3 == 2 && col % 3 == 0) {
                    cellField.getStyleClass().add("segment-border-bottom-left-corner");
                }

                if (row % 3 == 0 && col % 3 == 2) {
                    cellField.getStyleClass().add("segment-border-top-right-corner");
                }

                if (row % 3 == 2 && col % 3 == 2) {
                    cellField.getStyleClass().add("segment-border-bottom-right-corner");
                }

                if (row % 3 == 1 && col % 3 == 0) {
                    cellField.getStyleClass().add("segment-border-left");
                }

                if (row % 3 == 1 && col % 3 == 2) {
                    cellField.getStyleClass().add("segment-border-right");
                }

                if (row % 3 == 0 && col % 3 == 1) {
                    cellField.getStyleClass().add("segment-border-top");
                }

                if (row % 3 == 2 && col % 3 == 1) {
                    cellField.getStyleClass().add("segment-border-bottom");
                }

                boardGrid.add(cellField, col, row);
            }
        }
        resizeBoard();
    }

    private void addInputRestriction(TextField cellField) {
        cellField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[1-9]?")) {
                cellField.setText(oldValue);
            }
        });
    }

    public void resizeBoard() {
        double cellWidth = boardGrid.getWidth() / 9;
        double cellHeight = boardGrid.getHeight() / 9;

        for (var node : boardGrid.getChildren()) {
            if (node instanceof TextField) {
                TextField cellField = (TextField) node;
                cellField.setPrefSize(cellWidth, cellHeight);
            }
        }
    }

    private void resetHighlights() {
        for (var node : boardGrid.getChildren()) {
            node.getStyleClass().removeAll("highlighted-cell", "selected-cell");
        }
    }

    private void highlightRow(int row) {
        for (var node : boardGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row) {
                node.getStyleClass().add("highlighted-cell");
            }
        }
    }

    private void highlightColumn(int col) {
        for (var node : boardGrid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col) {
                node.getStyleClass().add("highlighted-cell");
            }
        }
    }

    private void highlightSegment(int row, int col) {
        int segmentRowStart = (row / 3) * 3;
        int segmentColStart = (col / 3) * 3;

        for (var node : boardGrid.getChildren()) {
            int nodeRow = GridPane.getRowIndex(node);
            int nodeCol = GridPane.getColumnIndex(node);

            if (nodeRow >= segmentRowStart && nodeRow < segmentRowStart + 3 &&
                    nodeCol >= segmentColStart && nodeCol < segmentColStart + 3) {
                node.getStyleClass().add("highlighted-cell");
            }
        }
    }

    private void highlightMatches(String value) {
        for (var node : boardGrid.getChildren()) {
            TextField cellField = (TextField) node;
            if (cellField.getText().equals(value) && !Objects.equals(cellField.getText(), "")) {
                cellField.getStyleClass().add("selected-cell");
            }
        }
    }

    public void reloadBoard(SudokuBoard newBoard) {
        boardGrid.getChildren().clear();
        initializeBoard(newBoard);
        resizeBoard();
        selectedCell = null;
    }

    public void setDigit(int digit) {
        if (selectedCell != null && selectedCell.isEditable()) {
            selectedCell.setText(String.valueOf(digit));
            int row = GridPane.getRowIndex(selectedCell);
            int col = GridPane.getColumnIndex(selectedCell);
        }
    }

    private void handleCellClick(int row, int col, TextField cellField) {
        resetHighlights();
        highlightRow(row);
        highlightColumn(col);
        highlightSegment(row, col);
        highlightMatches(cellField.getText());
        cellField.getStyleClass().add("selected-cell");

        selectedCell = cellField;
    }
}
