package org.example.sudoku.model;

public class Cell {
    private int value;
    private boolean isEditable;

    public Cell(int value) {
        this.value = value;
        this.isEditable = value == 0; // 0 for editable, anything else is predefined so not editable
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (this.isEditable) {
            this.value = value;
        }
    }
    public boolean isEditable() {
        return isEditable;
    }
}
