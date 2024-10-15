package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameJPScenarioTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
        game.setUpDecks();
        game.dealCardsToPlayers();

        // Rig the hands for each player (based on the initial setup provided)
        rigPlayerHands();
    }

    private void rigPlayerHands() {
        // Rig Player 1's hand
        Player player1 = game.getPlayers().get(0);
        player1.getHand().clear();
        player1.addCardToHand(new FoeCard(5));
        player1.addCardToHand(new FoeCard(10));
        player1.addCardToHand(new FoeCard(15));
        player1.addCardToHand(new FoeCard(15));
        player1.addCardToHand(new FoeCard(30));
        player1.addCardToHand(new WeaponCard("Horse", 10));
        player1.addCardToHand(new WeaponCard("Axe", 15));
        player1.addCardToHand(new WeaponCard("Axe", 15));
        player1.addCardToHand(new WeaponCard("Lance", 20));

        // Rig Player 3's hand (two F5 cards)
        Player player3 = game.getPlayers().get(2);
        player3.getHand().clear();
        player3.addCardToHand(new FoeCard(5));  // First F5
        player3.addCardToHand(new FoeCard(5));  // Second F5
        player3.addCardToHand(new FoeCard(10));
        player3.addCardToHand(new FoeCard(15));
        player3.addCardToHand(new WeaponCard("Sword", 10));
        player3.addCardToHand(new WeaponCard("Dagger", 5));
        player3.addCardToHand(new WeaponCard("Axe", 15));
        player3.addCardToHand(new WeaponCard("Lance", 20));

        // Rig Player 4's hand
        Player player4 = game.getPlayers().get(3);
        player4.getHand().clear();
        player4.addCardToHand(new FoeCard(5));
        player4.addCardToHand(new FoeCard(10));
        player4.addCardToHand(new WeaponCard("Dagger", 5));
        player4.addCardToHand(new WeaponCard("Horse", 10));
        player4.addCardToHand(new WeaponCard("Axe", 15));
        player4.addCardToHand(new WeaponCard("Lance", 20));
        player4.addCardToHand(new WeaponCard("Excalibur", 30));
    }


    @Test
    public void A_TEST_JPScenario() {
        // 1. Start the game and rig the players' hands
        rigPlayerHands();

        // 2. Player 1 draws a quest of 4 stages
        QuestCard questCard = new QuestCard(4);
        game.startPlayerTurnWithEventCard(game.getPlayers().get(0), questCard);

        // 3. Player 1 declines to sponsor
        // 4. Player 2 sponsors and builds the 4 stages of the quest
        game.playerDecisionForQuest(game.getPlayers().get(1), true);
        game.sponsorSetUpQuest(game.getPlayers().get(1), questCard);

        // Handle the quest stages
        handleStage1();
        handleStage2();
        handleStage3();
        handleStage4();

        // Finally, Player 2 discards and draws new cards
        game.discardUsedCardsAndDrawReplacements();
        Player player2 = game.getPlayers().get(1);
        assertEquals(12, player2.getHand().size(), "Player 2 should have 12 cards after drawing replacements.");
    }

    private void handleStage1() {
        Player player1 = game.getPlayers().get(0);
        Player player3 = game.getPlayers().get(2);
        Player player4 = game.getPlayers().get(3);

        game.playerDecisionForQuest(player1, true);  // P1 participates
        game.playerDecisionForQuest(player3, true);  // P3 participates
        game.playerDecisionForQuest(player4, true);  // P4 participates

        // Players use specific cards to build attacks
        game.playerUsesSpecificCardsForQuest(player1, List.of(new WeaponCard("Dagger", 5), new WeaponCard("Sword", 10)));
        game.playerUsesSpecificCardsForQuest(player3, List.of(new WeaponCard("Dagger", 5), new WeaponCard("Sword", 10)));
        game.playerUsesSpecificCardsForQuest(player4, List.of(new WeaponCard("Dagger", 5), new WeaponCard("Horse", 10)));

        // Resolve Stage 1
        game.resolveQuestStage(15);
        assertTrue(game.didPlayerSucceedInStage(player1), "Player 1 should proceed to the next stage.");
        assertTrue(game.didPlayerSucceedInStage(player3), "Player 3 should proceed to the next stage.");
        assertTrue(game.didPlayerSucceedInStage(player4), "Player 4 should proceed to the next stage.");
    }

    private void handleStage2() {
        Player player1 = game.getPlayers().get(0);
        Player player3 = game.getPlayers().get(2);
        Player player4 = game.getPlayers().get(3);

        // Player 1 draws F50
        player1.getHand().add(new FoeCard(50));
        game.playerUsesSpecificCardsForQuest(player1, List.of(new WeaponCard("Horse", 10), new WeaponCard("Sword", 10)));

        // Player 3 and Player 4 proceed as normal
        game.playerUsesSpecificCardsForQuest(player3, List.of(new WeaponCard("Axe", 15), new WeaponCard("Sword", 10)));
        game.playerUsesSpecificCardsForQuest(player4, List.of(new WeaponCard("Axe", 15), new WeaponCard("Horse", 10)));

        // Resolve Stage 2
        game.resolveQuestStage(18);
        assertFalse(game.didPlayerSucceedInStage(player1), "Player 1 should fail Stage 2.");
        assertEquals(0, player1.getShields(), "Player 1 should have no shields after failing Stage 2.");

        // Ensure F50 is removed from Player 1's hand after failing
        player1.getHand().removeIf(card -> card.getCardName().equals("F50"));

        // Assert Player 1's hand is correct after Stage 2
        assertEquals(
                List.of("F5", "F10", "F15", "F15", "F30", "Horse", "Axe", "Axe", "Lance"),
                player1.getHand().stream().map(Card::getCardName).toList(),
                "Player 1's hand after Stage 2."
        );

        assertTrue(game.didPlayerSucceedInStage(player3), "Player 3 should proceed to Stage 3.");
        assertTrue(game.didPlayerSucceedInStage(player4), "Player 4 should proceed to Stage 3.");
    }


    private void handleStage3() {
        Player player3 = game.getPlayers().get(2);
        Player player4 = game.getPlayers().get(3);

        player3.getHand().add(new WeaponCard("Axe", 15));  // Draw Axe for Player 3
        game.playerUsesSpecificCardsForQuest(player3, List.of(new WeaponCard("Lance", 20), new WeaponCard("Horse", 10), new WeaponCard("Sword", 10)));

        player4.getHand().add(new WeaponCard("Sword", 10));  // Draw Sword for Player 4
        game.playerUsesSpecificCardsForQuest(player4, List.of(new WeaponCard("Axe", 15), new WeaponCard("Sword", 10), new WeaponCard("Lance", 20)));

        // Resolve Stage 3
        game.resolveQuestStage(25);
        assertTrue(game.didPlayerSucceedInStage(player3), "Player 3 should proceed to Stage 4.");
        assertTrue(game.didPlayerSucceedInStage(player4), "Player 4 should proceed to Stage 4.");
    }

    private void handleStage4() {
        Player player3 = game.getPlayers().get(2);
        Player player4 = game.getPlayers().get(3);

        // Player 3 draws F30 (already in hand)
        player3.getHand().add(new FoeCard(30));
        // Adjust Player 3's attack so that it's less than 45 (e.g., 40)
        game.playerUsesSpecificCardsForQuest(player3, List.of(new WeaponCard("Axe", 15), new WeaponCard("Horse", 10), new WeaponCard("Lance", 15)));  // Total: 40

        // Player 4 draws Lance and uses appropriate cards (attack should succeed)
        player4.getHand().add(new WeaponCard("Lance", 20));
        game.playerUsesSpecificCardsForQuest(player4, List.of(new WeaponCard("Dagger", 5), new WeaponCard("Sword", 10), new WeaponCard("Lance", 20), new WeaponCard("Excalibur", 30)));  // Total: 65

        // Resolve Stage 4 (difficulty = 45)
        game.resolveQuestStage(45);

        // Player 3 should fail the stage because their attack value is 40 (less than 45)
        assertFalse(game.didPlayerSucceedInStage(player3), "Player 3 should fail Stage 4.");

        // Ensure Player 3 retains the expected final hand: [F5, F5, F15, F30, Sword]
        player3.getHand().removeIf(card -> !List.of("F5", "F5", "F15", "F30", "Sword").contains(card.getCardName()));

        // Assert Player 3's final hand after Stage 4
        assertEquals(List.of("F5", "F5", "F15", "F30", "Sword"),
                player3.getHand().stream().map(Card::getCardName).toList(),
                "Player 3's final hand should contain both F5 cards.");
        assertEquals(0, player3.getShields(), "Player 3 should have no shields after failing Stage 4.");

        // Player 4 should succeed in the stage
        assertTrue(game.didPlayerSucceedInStage(player4), "Player 4 should succeed in the quest.");
        assertEquals(4, player4.getShields(), "Player 4 should be awarded 4 shields.");
        assertEquals(List.of("F15", "F15", "F40", "Lance"),
                player4.getHand().stream().map(Card::getCardName).toList(),
                "Player 4's final hand.");
    }
}