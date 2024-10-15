package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();  // This will initialize the game
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
}
