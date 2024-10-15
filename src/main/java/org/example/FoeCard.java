package org.example;

public class FoeCard extends Card {
    private int value;

    public FoeCard(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getCardName() {
        return "F" + value;  // Return something like "F5", "F10", etc.
    }
}
