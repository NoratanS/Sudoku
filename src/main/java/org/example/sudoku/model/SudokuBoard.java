package org.example.sudoku.model;
import java.io.*;
import java.util.Random;
public class SudokuBoard {
    private Cell[][] board;

    public SudokuBoard(int[][] initialValues) {
        board = new Cell[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col] = new Cell(initialValues[row][col]);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public static SudokuBoard loadSampleBoard() {
        int[][] data;
        try {
            String filePath = "Sudoku\\src\\main\\resources\\\\org\\example\\sudoku\\sudoku.csv";
            Random rand = new Random();
            int random = rand.nextInt(countLinesInFile(filePath));
            data = readRandomGridFromCsv(filePath, random); // Wczytanie losowego wiersza z pliku
        } catch (IOException e) {
            e.printStackTrace();
            data = new int[0][0];
        }
        return new SudokuBoard(data);
    }
    public static int[][] readRandomGridFromCsv(String filePath, int randomNumber) throws IOException {

        int randomRow = randomNumber;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentRow = 0;

            // Odczytanie pliku aż do losowego wiersza
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(";"); // Podzielenie wiersza w kolumny
                String firstColumn = columns[0];
                if (currentRow == randomRow) {
                    // Kiedy trafimy na wylosowany wiersz zwracamy go jako plansze 9x9
                    return parseLineToGrid(firstColumn);
                }
                currentRow++;
            }
        }
        throw new IOException("Nie udało się odczytać losowego wiersza z pliku.");
    }
    public static int[][] readSolutionToRandomGridFromCsv(String filePath, int randomNumber) throws IOException {
        int randomRow = randomNumber;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentRow = 0;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(";");
                String firstColumn = columns[1];
                if (currentRow == randomRow) {
                    return parseLineToGrid(firstColumn);
                }
            }
        }
        throw new IOException("Nie udało się znaleść rozwiązania do planszy.");
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
