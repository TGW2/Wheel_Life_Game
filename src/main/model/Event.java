package model;

import java.util.ArrayList;

/**
 * Represents a game event that can affect the player's attributes.
 * Events are standardized and stored in the EventLibrary for current play.
 */

public class Event {
    String eventDescription;
    int minAge;
    int maxAge;
    int sanChange;
    int moodChange;
    int wisdomChange;
    String eventCode;
    ArrayList<String> prerequisites;

    /**
     * requires: minAge>=0, maxAge>=minAge.
     * modifies: this.
     * effects: set up an event, with events description and age range.
     */

    public Event(String eventDescription, int minAge, int maxAge,
            int sanChange, int moodChange, String eventCode) {
        this.eventDescription = eventDescription;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.sanChange = sanChange;
        this.moodChange = moodChange;
        this.eventCode = eventCode;
        this.prerequisites = new ArrayList<>();
    }

    // effects: get events description
    public String getEventDescription() {
        return eventDescription;
    }

    // effects: add prerequisties to a String list in order for those events need
    // prerequisties to check to unlock events
    // modifies: prerequisites
    public void addPrerequisite(String prerequisiteCode) {
        prerequisites.add(prerequisiteCode);
    }

    // effects: return all prerequisties
    // modifies: prerequisites
    public ArrayList<String> getPrerequisites() {
        return prerequisites;
    }

    // effects: get each events' event code in order to store prerequistie
    public String getEventCode() {
        return eventCode;
    }

    // effects: get min age
    public int getMinAge() {
        return minAge;
    }

    // efects: get max age
    public int getMaxAge() {
        return maxAge;
    }

    public int getSanChange() {
        return sanChange;
    }

    public int getMoodChange() {
        return moodChange;
    }

    // requires: age>=0
    // effects: identify whether can happen at that age
    public boolean canHappenAtAge(int age) {
        return age >= minAge && age <= maxAge;
    }
}