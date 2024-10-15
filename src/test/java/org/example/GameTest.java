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
        game.dealCardsToPlayers();
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

    @Test
    public void RESP_8_test_1_handleProsperityEvent() {
        game.dealCardsToPlayers();
        // Ensure all players start with 12 cards
        for (Player player : game.getPlayers()) {
            assertEquals(12, player.getHand().size(), "Each player should start with 12 cards");
        }

        // Simulate drawing the "Prosperity" event card
        EventCard prosperityCard = new EventCard("Prosperity");
        game.handleEventCard(prosperityCard, null);  // We don't need to pass a specific player

        // Assert: Each player should now have 14 cards
        for (Player player : game.getPlayers()) {
            assertEquals(14, player.getHand().size(), "Each player should have 14 cards after Prosperity event");
        }

        // Assert: If a player exceeds 12 cards, they need to trim (handled later in UC-03)
    }

    // INTEGRATION TESTING BEGINS
    @Test
    public void RESP_9_Test_1_drawQuestCardAndInitiateQuest() {
        // Assume it's Player 1's turn and they draw a Quest card
        Player currentPlayer = game.getPlayers().get(0);
        QuestCard questCard = new QuestCard(3);  // A quest with 3 stages
        game.startPlayerTurnWithEventCard(currentPlayer, questCard);  // High-level method to handle the player's turn

        // Assert: The game should transition to the quest phase
        assertTrue(game.isQuestInitiated(), "A quest should be initiated when a Quest card is drawn");

        // Assert: The quest should be initialized with the correct number of stages
        assertNotNull(game.getCurrentQuest(), "A quest should be initialized");
        assertEquals(3, game.getCurrentQuest().getNumberOfStages(), "Quest should have 3 stages as specified by the Quest card");

        // Assert: Ensure that the quest phase is ready to start
        assertTrue(game.isQuestPhaseStarted(), "Quest phase should be ready to start after a Quest card is drawn");
    }

    @Test
    public void RESP_10_Test_1_promptPlayersForQuestSponsorship() {
        // Assume it's Player 1's turn and they draw a Quest card
        Player currentPlayer = game.getPlayers().get(0);
        QuestCard questCard = new QuestCard(3);  // A quest with 3 stages
        game.startPlayerTurnWithEventCard(currentPlayer, questCard);  // Simulate player turn

        // Assert: The game should prompt each player for sponsorship
        assertTrue(game.isSponsorshipPrompted(), "The game should prompt players for sponsorship when a quest is initiated");

        // Assert: Ensure that all players are prompted to sponsor the quest
        for (Player player : game.getPlayers()) {
            assertTrue(player.wasPromptedForSponsorship(), "Player should be prompted for quest sponsorship");
        }
    }

    @Test
    public void RESP_11_Test_1_sponsorSetsUpValidQuest() {
        // Assume Player 1 agrees to sponsor the quest
        Player sponsor = game.getPlayers().get(0);
        QuestCard questCard = new QuestCard(3);  // A quest with 3 stages
        game.startPlayerTurnWithEventCard(sponsor, questCard);  // Initiates quest

        // Simulate the sponsor setting up a valid quest
        boolean questSetupSuccessful = game.sponsorSetUpQuest(sponsor, questCard);

        // Assert: The sponsor should successfully set up the quest
        assertTrue(questSetupSuccessful, "The sponsor should successfully set up a valid quest");

        // Assert: The quest should be marked as ready after setup
        assertTrue(game.isQuestReady(), "The quest should be ready after the sponsor sets it up");
    }

    @Test
    public void RESP_12_Test_1_playersDecideToParticipateOrWithdraw() {
        // Assume Player 1 agrees to sponsor the quest
        Player sponsor = game.getPlayers().get(0);
        QuestCard questCard = new QuestCard(3);  // A quest with 3 stages
        game.startPlayerTurnWithEventCard(sponsor, questCard);  // Initiates quest

        // Simulate the sponsor setting up the quest
        game.sponsorSetUpQuest(sponsor, questCard);

        // Simulate players deciding to participate or withdraw
        Player player2 = game.getPlayers().get(1);
        Player player3 = game.getPlayers().get(2);
        Player player4 = game.getPlayers().get(3);

        // Player 2 decides to participate
        game.playerDecisionForQuest(player2, true);  // true means participate
        // Player 3 decides to withdraw
        game.playerDecisionForQuest(player3, false);  // false means withdraw
        // Player 4 decides to participate
        game.playerDecisionForQuest(player4, true);

        // Assert: Player 2 and Player 4 should be eligible for the quest
        assertTrue(game.isPlayerEligibleForQuest(player2), "Player 2 should be eligible for the quest");
        assertTrue(game.isPlayerEligibleForQuest(player4), "Player 4 should be eligible for the quest");

        // Assert: Player 3 should not be eligible for the quest
        assertFalse(game.isPlayerEligibleForQuest(player3), "Player 3 should not be eligible for the quest after withdrawing");
    }

    @Test
    public void RESP_13_Test_1_discardUsedCardsAndDrawReplacements() {
        game.dealCardsToPlayers();
        // Assume Player 1 agrees to sponsor the quest
        Player sponsor = game.getPlayers().get(0);
        QuestCard questCard = new QuestCard(3);  // A quest with 3 stages
        game.startPlayerTurnWithEventCard(sponsor, questCard);  // Initiates quest

        // Simulate the sponsor setting up the quest
        game.sponsorSetUpQuest(sponsor, questCard);

        // Simulate players using some cards during the quest
        Player player2 = game.getPlayers().get(1);
        game.playerUsesCardsForQuest(player2, 3);  // Player 2 uses 3 cards
        Player player4 = game.getPlayers().get(3);
        game.playerUsesCardsForQuest(player4, 2);  // Player 4 uses 2 cards

        // Discard used cards and allow players to draw replacements
        game.discardUsedCardsAndDrawReplacements();

        // Assert: Players should have their hand size restored to 12 cards
        assertEquals(12, player2.getHand().size(), "Player 2 should have 12 cards after drawing replacements");
        assertEquals(12, player4.getHand().size(), "Player 4 should have 12 cards after drawing replacements");

        // Assert: The used cards should be in the discard pile
        assertEquals(5, game.getDiscardPile().size(), "There should be 5 cards in the discard pile (3 from Player 2, 2 from Player 4)");
    }

    @Test
    public void RESP_14_Test_1_resolveQuestStage() {
        // Assume Player 1 agrees to sponsor the quest
        Player sponsor = game.getPlayers().get(0);
        QuestCard questCard = new QuestCard(3);  // A quest with 3 stages
        game.startPlayerTurnWithEventCard(sponsor, questCard);  // Initiates quest

        // Simulate the sponsor setting up the quest
        game.sponsorSetUpQuest(sponsor, questCard);

        // Simulate players participating in the quest
        Player player2 = game.getPlayers().get(1);
        Player player4 = game.getPlayers().get(3);

        game.playerDecisionForQuest(player2, true);  // Player 2 participates
        game.playerDecisionForQuest(player4, true);  // Player 4 participates

        // Simulate players setting up their attack for the first quest stage
        game.playerSetsUpAttack(player2, 20);  // Player 2 has an attack value of 20
        game.playerSetsUpAttack(player4, 15);  // Player 4 has an attack value of 15

        // Assume the difficulty of the current quest stage is 18
        boolean stageResolved = game.resolveQuestStage(18);

        // Assert: Player 2 should succeed, Player 4 should fail
        assertTrue(game.didPlayerSucceedInStage(player2), "Player 2 should succeed in the stage");
        assertFalse(game.didPlayerSucceedInStage(player4), "Player 4 should fail in the stage");

        // Assert: Player 2 should advance to the next stage
        assertTrue(game.isPlayerEligibleForNextStage(player2), "Player 2 should advance to the next stage");

        // Assert: Player 4 should be eliminated from further quest participation
        assertFalse(game.isPlayerEligibleForNextStage(player4), "Player 4 should be eliminated from the quest");
    }

    @Test
    public void RESP_15_Test_1_awardShieldsUponQuestCompletion() {
        // Assume Player 1 agrees to sponsor the quest
        Player sponsor = game.getPlayers().get(0);
        QuestCard questCard = new QuestCard(3);  // A quest with 3 stages
        game.startPlayerTurnWithEventCard(sponsor, questCard);  // Initiates quest

        // Simulate the sponsor setting up the quest
        game.sponsorSetUpQuest(sponsor, questCard);

        // Simulate players participating in the quest
        Player player2 = game.getPlayers().get(1);
        Player player4 = game.getPlayers().get(3);

        game.playerDecisionForQuest(player2, true);  // Player 2 participates
        game.playerDecisionForQuest(player4, true);  // Player 4 participates

        // Simulate players setting up their attack for all 3 quest stages
        game.playerSetsUpAttack(player2, 20);  // Player 2 has an attack value of 20
        game.playerSetsUpAttack(player4, 15);  // Player 4 has an attack value of 15

        // Assume the difficulty of all quest stages is 18
        game.resolveQuestStage(18);  // Stage 1 resolved
        game.resolveQuestStage(18);  // Stage 2 resolved
        game.resolveQuestStage(18);  // Stage 3 resolved

        // Award shields to the players who successfully completed the quest
        game.awardShieldsForQuestCompletion(questCard);

        // Assert: Player 2 should be awarded 3 shields (one per stage)
        assertEquals(3, player2.getShields(), "Player 2 should be awarded 3 shields for completing the quest");

        // Assert: Player 4 should not be awarded shields (since they failed in one of the stages)
        assertEquals(0, player4.getShields(), "Player 4 should not be awarded shields as they failed a stage");

        // Assert: The quest should be marked as completed
        assertTrue(game.isQuestCompleted(), "The quest should be marked as completed after all stages are resolved");
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);  // Restore System.out after test
    }


}
