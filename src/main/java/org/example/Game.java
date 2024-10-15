package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Card> adventureDeck;
    private List<Card> eventDeck;
    private List<Player> players;

    public Game(){
        players = new ArrayList<>();
        // Initialize the 4 players
        for (int i = 0; i < 4; i++) {
            players.add(new Player());
        }
    }

    public void setUpDecks() {
        adventureDeck = new ArrayList<>();
        eventDeck = new ArrayList<>();

        // Populate adventure deck with 50 Foe cards
        for (int i = 0; i < 8; i++) adventureDeck.add(new FoeCard(5));   // Foe value 5
        for (int i = 0; i < 7; i++) adventureDeck.add(new FoeCard(10));  // Foe value 10
        for (int i = 0; i < 8; i++) adventureDeck.add(new FoeCard(15));  // Foe value 15
        for (int i = 0; i < 7; i++) adventureDeck.add(new FoeCard(20));  // Foe value 20
        for (int i = 0; i < 7; i++) adventureDeck.add(new FoeCard(25));  // Foe value 25
        for (int i = 0; i < 4; i++) adventureDeck.add(new FoeCard(30));  // Foe value 30
        for (int i = 0; i < 4; i++) adventureDeck.add(new FoeCard(35));  // Foe value 35
        for (int i = 0; i < 2; i++) adventureDeck.add(new FoeCard(40));  // Foe value 40
        for (int i = 0; i < 2; i++) adventureDeck.add(new FoeCard(50));  // Foe value 50
        adventureDeck.add(new FoeCard(70));                              // Foe value 70

        // Populate adventure deck with 50 Weapon cards
        for (int i = 0; i < 6; i++) adventureDeck.add(new WeaponCard("Dagger", 5));     // Dagger
        for (int i = 0; i < 12; i++) adventureDeck.add(new WeaponCard("Horse", 10));    // Horse
        for (int i = 0; i < 16; i++) adventureDeck.add(new WeaponCard("Sword", 10));    // Sword
        for (int i = 0; i < 8; i++) adventureDeck.add(new WeaponCard("Battle-axe", 15));// Battle-axe
        for (int i = 0; i < 6; i++) adventureDeck.add(new WeaponCard("Lance", 20));     // Lance
        for (int i = 0; i < 2; i++) adventureDeck.add(new WeaponCard("Excalibur", 30)); // Excalibur

        // Populate event deck with 12 Quest cards and 5 Event cards
        for (int i = 0; i < 3; i++) eventDeck.add(new QuestCard(2));  // Quest with 2 stages
        for (int i = 0; i < 4; i++) eventDeck.add(new QuestCard(3));  // Quest with 3 stages
        for (int i = 0; i < 3; i++) eventDeck.add(new QuestCard(4));  // Quest with 4 stages
        for (int i = 0; i < 2; i++) eventDeck.add(new QuestCard(5));  // Quest with 5 stages

        eventDeck.add(new EventCard("Plague"));
        eventDeck.add(new EventCard("Queen’s favor"));
        eventDeck.add(new EventCard("Queen’s favor"));
        eventDeck.add(new EventCard("Prosperity"));
        eventDeck.add(new EventCard("Prosperity"));
    }

    public void dealCardsToPlayers() {
        // Distribute 12 cards to each player
        for (Player player : players) {
            for (int i = 0; i < 12; i++) {
                player.addCardToHand(adventureDeck.remove(0));  // Deal the top card
            }
        }
    }

    public List<Player> checkForWinners() {
        List<Player> winners = new ArrayList<>();

        // Check each player for 7 or more shields
        for (Player player : players) {
            if (player.getShields() >= 7) {
                winners.add(player);
            }
        }

        return winners;
    }

    public void displayWinnersAndTerminate() {
        List<Player> winners = checkForWinners();

        if (winners.isEmpty()) {
            System.out.println("No winners this time.");
        } else {
            System.out.print("Winners: ");
            for (int i = 0; i < winners.size(); i++) {
                System.out.print("Player " + (players.indexOf(winners.get(i)) + 1));
                if (i < winners.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

        System.out.println("Game Over");
    }

    public List<Card> getAdventureDeck() {
        return adventureDeck;
    }

    public List<Card> getEventDeck() {
        return eventDeck;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
