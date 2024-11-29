module org.example.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.sudoku to javafx.fxml;
    exports org.example.sudoku;
}