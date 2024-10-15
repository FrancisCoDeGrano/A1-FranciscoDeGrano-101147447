package org.example;

public class EventCard extends Card {
    private String eventName;

    public EventCard(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
