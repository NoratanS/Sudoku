package org.example.sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.sudoku.view.MainView;

public class App extends Application {

    @Override
    public void start(Stage primarSstage) {
        new MainView().start(primarSstage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
