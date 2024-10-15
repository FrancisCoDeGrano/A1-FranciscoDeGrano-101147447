package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Francisco De Grano
 * Student Number: 101147447
 *
 * GameTest - Test Suite for the game :)
 */

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();         // This will initialize the game
        game.setUpDecks();         // Set Up the adventure and event decks
        game.dealCardsToPlayers(); // Deal 12 Cards to Players
    }

    @Test
    public void RESP_1_test_1_setUpAdventureAndEventDecks() {
        game.setUpDecks();  // Method to be implemented

        // Check the number of cards in each deck and distribution of card types
        assertNotNull(game.getAdventureDeck());
        assertEquals(100, game.getAdventureDeck().size());

        assertNotNull(game.getEventDeck());
        assertEquals(17, game.getEventDeck().size());
    }

    @Test
    public void RESP_2_test_1_distribute12CardsToEachPlayer() {
        // Act
        game.dealCardsToPlayers();  // This method needs to be implemented

        // Assert that each player has 12 cards
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getHand(), "Player's hand should be initialized");
            assertEquals(12, player.getHand().size(), "Each player should have 12 cards");
        }

        // Assert that the adventure deck has been reduced by 48 cards (4 players * 12 cards each)
        assertEquals(52, game.getAdventureDeck().size(), "Adventure deck should have 52 cards left after dealing");
    }

    @Test
    public void RESP_3_test_1_determineWinnersAfterTurn() {
        // Simulate players gaining shields
        game.getPlayers().get(0).setShields(7);  // Player 1 reaches 7 shields
        game.getPlayers().get(1).setShields(5);  // Player 2 has 5 shields
        game.getPlayers().get(2).setShields(8);  // Player 3 reaches 8 shields
        game.getPlayers().get(3).setShields(4);  // Player 4 has 4 shields

        // Act: Check if any player(s) have won
        List<Player> winners = game.checkForWinners();  // Method to be implemented

        // Assert: Player 1 and Player 3 should be winners
        assertNotNull(winners, "Winners list should not be null");
        assertEquals(2, winners.size(), "There should be 2 winners");
        assertTrue(winners.contains(game.getPlayers().get(0)), "Player 1 should be a winner");
        assertTrue(winners.contains(game.getPlayers().get(2)), "Player 3 should be a winner");
    }


}
