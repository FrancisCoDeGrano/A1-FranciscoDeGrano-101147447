package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private int shields;

    public Player() {
        this.hand = new ArrayList<>();
        this.shields = 0; // Start with 0 Shields
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getShields() {
        return shields;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }
}
