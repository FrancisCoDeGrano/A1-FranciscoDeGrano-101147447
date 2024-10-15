package org.example;

public class WeaponCard extends Card {
    private String type;
    private int value;

    public WeaponCard(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getCardName() {
        return type;  // Return the weapon type (e.g., "Sword", "Lance", "Axe", etc.)
    }
}
