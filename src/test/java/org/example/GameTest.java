package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        game = new Game();         // This will initialize the game
        game.setUpDecks();         // Set Up the adventure and event decks
        game.dealCardsToPlayers(); // Deal 12 Cards to Players

        // Redirect System.out for testing output
        System.setOut(new PrintStream(outContent));
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

    @Test
    public void RESP_4_test_1_displayWinnersAndTerminate() {
        // Simulate players gaining shields
        game.getPlayers().get(0).setShields(7);  // Player 1 reaches 7 shields
        game.getPlayers().get(1).setShields(8);  // Player 2 reaches 8 shields

        // Act: Display winners and terminate
        game.displayWinnersAndTerminate();  // Method to be implemented

        // Adjust expected output to handle any trailing whitespace or newlines
        String expectedOutput = "Winners: Player 1, Player 2\nGame Over";
        String actualOutput = outContent.toString().trim();  // Trim output to remove extra spaces/newlines

        // Print outputs for debugging
        System.out.println("Expected Output: " + expectedOutput);
        System.out.println("Actual Output: " + actualOutput);

        // Assert: Check if the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput, "Should display winners and terminate the game");
    }

    @Test
    public void RESP_5_test_1_playerDrawsEventCard() {
        // Assume it's Player 1's turn
        Player currentPlayer = game.getPlayers().get(0);

        // Find the first EventCard without removing other cards
        Card drawnCard = null;
        for (Card card : game.getEventDeck()) {
            if (card instanceof EventCard) {
                drawnCard = card;
                break;
            }
        }

        // Now remove the card only if it's an EventCard
        game.getEventDeck().remove(drawnCard);

        // Assert: The drawn card should be an EventCard
        assertNotNull(drawnCard, "Player should draw an event card");
        assertTrue(drawnCard instanceof EventCard, "The drawn card should be an EventCard");

        // Assert: Event deck should have one less card
        assertEquals(16, game.getEventDeck().size(), "Event deck should have 16 cards after one draw");
    }

    @Test
    public void RESP_6_test_1_handlePlagueEvent() {
        // Assume it's Player 1's turn
        Player currentPlayer = game.getPlayers().get(0);

        // Simulate drawing the "Plague" event card
        EventCard plagueCard = new EventCard("Plague");
        game.handleEventCard(plagueCard, currentPlayer);  // Method to be implemented

        // Assert: Player 1 should lose 2 shields
        assertEquals(-2, currentPlayer.getShields(), "Player should lose 2 shields from Plague event");
    }

    @Test
    public void RESP_7_test_1_handleQueensFavorEvent() {
        // Assume it's Player 1's turn
        Player currentPlayer = game.getPlayers().get(0);

        // Assert the player starts with 12 cards
        assertEquals(12, currentPlayer.getHand().size(), "Player should start with 12 cards");

        // Simulate drawing the "Queen’s favor" event card
        EventCard queensFavorCard = new EventCard("Queen’s favor");

        // This will fail because handleEventCard is not implemented yet
        game.handleEventCard(queensFavorCard, currentPlayer);

        // Assert: Player should now have 14 cards after the Queen's favor event
        assertEquals(14, currentPlayer.getHand().size(), "Player should have 14 cards after Queen's favor event");

        // We are not implementing the trimming part yet (handled later in UC-03)
    }


    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);  // Restore System.out after test
    }


}
