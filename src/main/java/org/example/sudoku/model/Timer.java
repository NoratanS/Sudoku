package org.example.sudoku.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class Timer {
    private Timeline timeline;
    private int TimeInSecs;
    private final Label timerLabel;

    public Timer(Label timerLabel) {
        this.timerLabel = timerLabel;
        this.TimeInSecs = 0;
        InitializeTimer();
    }

    private void InitializeTimer(){
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            TimeInSecs++;
            updateTimerLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }
    private void updateTimerLabel() {
        int minutes = TimeInSecs / 60;
        int seconds = TimeInSecs % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
    public void start(){
        if (timeline != null) {
            timeline.play();
        }
    }
    private void stop(){
        if (timeline != null) {
            timeline.stop();
        }
    }
    public void reset(){
        if (timeline != null) {
            timeline.stop();
        }
        TimeInSecs = 0;
        updateTimerLabel();
    }
    public int getTimeInSecs() {
        return TimeInSecs;
    }


}
