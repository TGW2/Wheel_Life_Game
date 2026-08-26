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
    private String category;

    /**
     * requires: minAge>=0, maxAge>=minAge.
     * modifies: this.
     * effects: set up an event, with events description and age range.
     */

    public Event(String eventDescription, int minAge, int maxAge,
            int sanChange, int moodChange, int wisdomChange, String eventCode) {
        this.eventDescription = eventDescription;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.sanChange = sanChange;
        this.moodChange = moodChange;
        this.eventCode = eventCode;
        this.wisdomChange = wisdomChange;
        this.prerequisites = new ArrayList<>();
        this.category = inferCategory(eventCode);
    }

    /** A compact label used by the journal and wheel. */
    public String getCategory() {
        return category;
    }

    public Event withCategory(String value) {
        category = value == null ? "LIFE" : value.toUpperCase();
        return this;
    }

    public String getImpactSummary() {
        return formatImpact("SPIRIT", sanChange) + "  "
                + formatImpact("JOY", moodChange) + "  "
                + formatImpact("INSIGHT", wisdomChange);
    }

    private String formatImpact(String label, int value) {
        return label + " " + (value >= 0 ? "+" : "") + value;
    }

    private String inferCategory(String code) {
        String value = code == null ? "" : code.toLowerCase();
        if (value.contains("math") || value.contains("class") || value.contains("exam")) {
            return "MIND";
        }
        if (value.contains("love") || value.contains("marri") || value.contains("friend")
                || value.contains("hug") || value.contains("partner")) {
            return "BONDS";
        }
        if (value.contains("job") || value.contains("club")) {
            return "PURPOSE";
        }
        return "LIFE";
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

    public int getWisdomChange() {
        return wisdomChange;
    }

    // requires: age>=0
    // effects: identify whether can happen at that age
    public boolean canHappenAtAge(int age) {
        return age >= minAge && age <= maxAge;
    }
}
