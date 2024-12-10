package org.example.sudoku.model;
import java.io.*;
import java.util.Random;
public class SudokuBoard {
    private Cell[][] board;
    private Cell[][] solutionBoard;

    public SudokuBoard(int[][] initialValues, int[][] solutionBoard) {
        this.board = new Cell[9][9];
        this.solutionBoard = new Cell[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                this.board[row][col] = new Cell(initialValues[row][col]);
                this.solutionBoard[row][col] = new Cell(initialValues[row][col]);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }
    public Cell getSolutionCell(int row, int col) {
        return solutionBoard[row][col];
    }

    public static SudokuBoard loadBoard() {
        int[][] initialBoard;
        int[][] solutionBoard;
        try {
            String filePath = "src/main/resources/org/example/sudoku/sudoku.csv";
            Random rand = new Random();
            int random = rand.nextInt(countLinesInFile(filePath));
            initialBoard = readRandomGridFromCsv(filePath, random,0);
            solutionBoard = readRandomGridFromCsv(filePath,random, 1);
        } catch (IOException e) {
            e.printStackTrace();
            initialBoard = new int[9][9];
            solutionBoard = new int[9][9];
        }
        return new SudokuBoard(initialBoard,solutionBoard);
    }
    public static int[][] readRandomGridFromCsv(String filePath, int randomNumber, int columnNumber) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentRow = 0;

            // Odczytanie pliku aż do losowego wiersza
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(";"); // Podzielenie wiersza w kolumny
                if (currentRow == randomNumber) {
                    // Kiedy trafimy na wylosowany wiersz zwracamy go jako plansze 9x9
                    return parseLineToGrid(columns[columnNumber]);
                }
                currentRow++;
            }
        }
        throw new IOException("Nie udało się odczytać losowego wiersza z pliku.");
    }
    public static int[][] parseLineToGrid(String line) {
        if (line.length() != 81) {
            System.out.println(line.length());
            throw new IllegalArgumentException("Wiersz musi zawierać dokładnie 81 cyfr!");
        }
        int[][] grid = new int[9][9];
        for (int i = 0; i < 81; i++) {
            grid[i / 9][i % 9] = Character.getNumericValue(line.charAt(i));
        }
        return grid;
    }
    public static int countLinesInFile(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int lines = 0;
            while (br.readLine() != null) {
                lines++;
            }
            return lines;
        }
    }
}
