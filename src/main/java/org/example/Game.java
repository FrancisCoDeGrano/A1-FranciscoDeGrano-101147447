package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private boolean questInitiated;
    private boolean questPhaseStarted;
    private boolean sponsorshipPrompted;
    private boolean questReady;

    private Quest currentQuest;
    private Card lastDrawnEventCard;

    private List<Card> adventureDeck;
    private List<Card> eventDeck;
    private List<Card> discardPile = new ArrayList<>();
    private List<Player> players;
    private List<Player> eligiblePlayers = new ArrayList<>();


    public Game(){
        players = new ArrayList<>();
        // Initialize the 4 players
        for (int i = 0; i < 4; i++) {
            players.add(new org.example.Player(i+1));
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
        for (org.example.Player player : players) {
            for (int i = 0; i < 12; i++) {
                player.addCardToHand(adventureDeck.remove(0));  // Deal the top card
            }
        }
    }

    public List<org.example.Player> checkForWinners() {
        List<org.example.Player> winners = new ArrayList<>();

        // Check each player for 7 or more shields
        for (org.example.Player player : players) {
            if (player.getShields() >= 7) {
                winners.add(player);
            }
        }

        return winners;
    }

    public void displayWinnersAndTerminate() {
        List<org.example.Player> winners = checkForWinners();

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

    public Card drawEventCard(org.example.Player currentPlayer) {
        // Draw the top card from the event deck
        if (!eventDeck.isEmpty()) {
            return eventDeck.remove(0);  // Draw and remove the top card
        }
        return null;  // Return null if the deck is empty (though this shouldn't happen in normal play)
    }

    public Card getLastDrawnEventCard() {
        return lastDrawnEventCard;
    }

    public void handleEventCard(EventCard card, org.example.Player currentPlayer) {
        // Handle the event based on its type
        switch (card.getEventName()) {
            case "Plague":
                // Plague: Current player loses 2 shields
                currentPlayer.setShields(currentPlayer.getShields() - 2);
                break;
            case "Queen’s favor":
                // Queen’s favor: Current player draws 2 adventure cards
                for (int i = 0; i < 2; i++) {
                    if (!adventureDeck.isEmpty()) {
                        currentPlayer.addCardToHand(adventureDeck.remove(0));
                    }
                }
                break;

            case "Prosperity":
                // Prosperity: All players draw 2 adventure cards
                for (org.example.Player player : players) {
                    for (int i = 0; i < 2; i++) {
                        if (!adventureDeck.isEmpty()) {
                            player.addCardToHand(adventureDeck.remove(0));
                        }
                    }
                }
                break;

            default:
                // Handle other events as necessary
                System.out.println("Unknown event: " + card.getEventName());
                break;
        }
    }


    public List<Card> getAdventureDeck() {
        return adventureDeck;
    }

    public List<Card> getEventDeck() {
        return eventDeck;
    }

    public List<org.example.Player> getPlayers() {
        return players;
    }

    public void startPlayerTurnWithEventCard(org.example.Player currentPlayer, Card eventCard) {
        // Simulate drawing a card and handling it
        this.lastDrawnEventCard = eventCard;

        // If it's a Quest card, initiate the quest
        if (eventCard instanceof QuestCard) {
            initiateQuest((QuestCard) eventCard);
        }
    }

    public void initiateQuest(QuestCard questCard) {
        // Initialize the quest
        this.currentQuest = new Quest(questCard.getNumberOfStages());
        this.questInitiated = true;

        // Start the quest phase
        startQuestPhase();
        promptForSponsorship();
    }

    public void startQuestPhase() {
        this.questPhaseStarted = true;
        System.out.println("Quest phase started with " + currentQuest.getNumberOfStages() + " stages.");
    }

    public boolean isQuestInitiated() {
        return this.questInitiated;
    }

    public boolean isQuestPhaseStarted() {
        return this.questPhaseStarted;
    }

    public Quest getCurrentQuest() {
        return this.currentQuest;
    }

    // Method to prompt players for quest sponsorship
    public void promptForSponsorship() {
        for (org.example.Player player : players) {
            promptPlayerForSponsorship(player);
        }

        // Set the flag to indicate that players have been prompted
        this.sponsorshipPrompted = true;
    }

    // Simulate prompting each player for sponsorship
    private void promptPlayerForSponsorship(org.example.Player player) {
        System.out.println("Player " + player.getId() + ", do you want to sponsor the quest?");
        player.setPromptedForSponsorship(true);  // Simulate the prompt
    }

    public boolean isSponsorshipPrompted() {
        return this.sponsorshipPrompted;
    }

    // Method to allow the sponsor to set up a valid quest
    public boolean sponsorSetUpQuest(Player sponsor, QuestCard questCard) {
        // Logic for setting up a valid quest (simulating card selection for each stage)
        System.out.println("Player " + sponsor.getId() + " is setting up the quest...");

        // Simulate the sponsor setting up a valid quest (you can expand this logic)
        if (sponsorHasValidCardsForQuest(sponsor, questCard)) {
            this.questReady = true;  // Mark the quest as ready if setup is successful
            return true;  // Indicate successful setup
        }

        return false;  // Return false if setup fails
    }

    // Simulated check for valid cards for the quest setup
    private boolean sponsorHasValidCardsForQuest(Player sponsor, QuestCard questCard) {
        // This method can check if the sponsor has valid cards for each stage of the quest
        // For now, we'll assume they always have valid cards for simplicity
        return true;
    }

    public boolean isQuestReady() {
        return this.questReady;
    }

    // Method to handle a player's decision to participate or withdraw
    public void playerDecisionForQuest(Player player, boolean participate) {
        if (participate) {
            eligiblePlayers.add(player);  // Add player to eligible list if they participate
            System.out.println("Player " + player.getId() + " has decided to participate in the quest.");
        } else {
            System.out.println("Player " + player.getId() + " has decided to withdraw from the quest.");
        }
    }

    // Check if a player is eligible for the quest
    public boolean isPlayerEligibleForQuest(Player player) {
        return eligiblePlayers.contains(player);
    }

    // Method to simulate a player using cards for a quest stage
    public void playerUsesCardsForQuest(Player player, int numberOfCardsUsed) {
        // Simulate the player using the top cards from their hand
        List<Card> usedCards = new ArrayList<>();
        for (int i = 0; i < numberOfCardsUsed; i++) {
            if (!player.getHand().isEmpty()) {
                usedCards.add(player.getHand().remove(0));  // Remove the card from the player's hand
            }
        }
        discardPile.addAll(usedCards);  // Add used cards to the discard pile
    }

    // Method to discard used cards and draw replacements for players
    public void discardUsedCardsAndDrawReplacements() {
        for (Player player : players) {
            // Calculate how many cards the player needs to draw to have 12 cards again
            int cardsNeeded = 12 - player.getHand().size();

            // Draw replacement cards from the adventure deck
            for (int i = 0; i < cardsNeeded; i++) {
                if (!adventureDeck.isEmpty()) {
                    player.addCardToHand(adventureDeck.remove(0));  // Draw a card from the deck
                }
            }
        }
    }
    public List<Card> getDiscardPile() {
        return discardPile;
    }


}
