package org.example;

public class QuestCard extends Card {
    private int numberOfStages;

    public QuestCard(int numberOfStages) {
        this.numberOfStages = numberOfStages;
    }

    public int getNumberOfStages() {
        return numberOfStages;
    }

    @Override
    public String getCardName() {
        return "Quest with " + numberOfStages + " stages";  // Provide a meaningful name for the quest
    }
}
