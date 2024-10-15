package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private List<Card> hand;
    private int shields;
    private boolean promptedForSponsorship = false;

    public Player(int id) {
        this.id = id;
        this.hand = new ArrayList<>();
        this.shields = 0; // Start with 0 Shields
    }

    public int getId() {
        return id;
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

    public boolean wasPromptedForSponsorship() {
        return promptedForSponsorship;
    }

    public void setPromptedForSponsorship(boolean prompted) {
        this.promptedForSponsorship = prompted;
    }
}
