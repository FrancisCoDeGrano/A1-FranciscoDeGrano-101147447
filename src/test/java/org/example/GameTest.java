package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Francisco De Grano
 * Student Number: 101147447
 */

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();  // This will initialize the game
        game.setUpDecks();  // Set Up the adventure and event decks
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

}
