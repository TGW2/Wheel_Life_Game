package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a game event library that stores all events shows on game.
 * And doing logic judgment on selected events.
 */

public class EventLibrary {
    private ArrayList<Event> allEvents;
    private ArrayList<Event> goneThroughEventsInEvent = new ArrayList<>();
    private ArrayList<String> goneThroughEvents = new ArrayList<>();
    private Player p1;

    // modifies:this
    // affects: initialize all events
    public EventLibrary() {
        this.allEvents = new ArrayList<Event>();
        initializeChildhoodEvents();
        initializeElementaryEvents();
        initializeHighSchoolAndUniEvents();
        initializeLongLifeEvents();
        initializeMathEvents();
        initializeMiddleSchoolEvents();
    }

    /**
     * modifies:this.
     * affects: add all events based on different age.
     */

    public void initializeChildhoodEvents() {
        // particular time events
        // at childhood
        allEvents.add(new Event("Hopscotch", 0, 7, 2, 2, 0, "Hopscotch"));
        allEvents.add(new Event("A childhood friend", 0, 7, 2, 2, 0, "A childhood friend"));
        allEvents.add(new Event("Bedwetting", 0, 7, -2, -2, 0, "Bedwetting"));
        allEvents.add(new Event("Looking to be an adult", 0, 7, 1, 1, 0, "Looking to be an adult"));
        allEvents.add(new Event("Lying to parents", 0, 7, -3, -3, 0, "Lying to parents"));

    }

    public void initializeElementaryEvents() {
        // elementary school
        allEvents.add(new Event("Taking class", 7, 12, -2, -2, 0, "Taking class"));
        allEvents.add(new Event("Playing with friends", 7, 12, 4, 3, 0, "Playing with friends"));
        allEvents.add(new Event("Forgot doing HW", 7, 12, 0, -4, 0, "Forgot doing HW"));
        allEvents.add(new Event("Playing sports", 7, 12, 2, 4, 0, "Playing sports"));
        allEvents.add(new Event("Learning new friends", 7, 12, 3, 2, 0, "Learning new friends"));

    }

    public void initializeMiddleSchoolEvents() {
        // middle school
        allEvents.add(new Event("Violate school rules", 13, 15, 0, -3, 0, "Violate school rules"));
        allEvents.add(new Event("Have a partner", 13, 15, 6, 9, 0, "Have a partner"));
        allEvents.add(new Event("Bad final exam", 13, 15, -5, -7, 0, "Bad final exam"));
        allEvents.add(new Event("disappointed to life", 13, 15, -100, -100, 0, "disappointed to life"));

    }

    public void initializeHighSchoolAndUniEvents() {
        // high school & Uni
        allEvents.add(new Event("Join a club you like", 16, 22, 2, 2, 0, "Join a club you like"));
        allEvents.add(new Event("Skipping class", 16, 22, 1, 4, 0, "Skipping class"));
        allEvents.add(new Event("Get a part time job", 16, 22, 5, 3, 0, "Get a part time job"));

    }

    public void initializeLongLifeEvents() {
        // long life events
        Event inAlovership = new Event("In a lovership", 22, 100, 10, 10, 0, "In a lovership");
        allEvents.add(inAlovership);

        Event brokeUp = new Event("Broke up", 22, 100, -5, -5, 0, "Broke up");
        brokeUp.addPrerequisite("In a lovership");
        allEvents.add(brokeUp);

        Event engaged = new Event("Engaged", 22, 100, 2, 2, 0, "Engaged");
        engaged.addPrerequisite("In a lovership");
        allEvents.add(engaged);

        Event getMarried = new Event("Get married -- Congurates!", 22, 100, 15, 15, 0, "Get married");
        getMarried.addPrerequisite("Engaged");
        allEvents.add(getMarried);

        Event divorced = new Event("Divorced -- Sry to hear that", 22, 100, -8, -8, 0, "Divorced");
        divorced.addPrerequisite("Get married");
        allEvents.add(divorced);

        allEvents.add(new Event("Eating", 0, 100, 2, 4, 0, "Eating"));
        allEvents.add(new Event("Sleeping", 0, 100, 4, 4, 0, "Sleeping"));
        allEvents.add(new Event("Crying", 0, 100, -1, -5, 0, "Crying"));
        allEvents.add(new Event("Hugging", 0, 100, 5, 10, 0, "Hugging"));
        allEvents.add(new Event("Laughing", 0, 100, 1, 1, 0, "Laughing"));

    }

    public void initializeMathEvents() {
        // math longlife specialization
        Event intoMath = new Event("intoMath", 6, 100, 1, 1, 0, "intoMath");
        allEvents.add(intoMath);

        Event loveInMath = new Event("loveInMath", 6, 100, 2, 2, 0, "loveInMath");
        loveInMath.addPrerequisite("intoMath");
        allEvents.add(loveInMath);

        Event masterInMath = new Event("masterInMath", 6, 100, 8, 8, 0, "masterInMath");
        masterInMath.addPrerequisite("loveInMath");
        allEvents.add(masterInMath);
    }

    /**
     * get all possible events based on age.
     * requires: age>=0
     * effects: return all events occur at my age
     */

    public ArrayList<Event> allPossibleEvents(int age) {
        ArrayList<Event> possibleEvents = new ArrayList<>();

        for (Event event : allEvents) {
            if (event.canHappenAtAge(age) && checkPrerequistieEligible(event)) {
                possibleEvents.add(event);
            }
        }
        return possibleEvents;
    }

    /**
     * effects: for some events have prerequistie,
     * do the check if they are eligible to add into possible events.
     */
    public boolean checkPrerequistieEligible(Event event) {
        ArrayList<String> preRequisties = event.getPrerequisites();
        for (String eventCode : preRequisties) {
            if (!goneThroughEventsInString().contains(eventCode)) {
                return false;
            }
            if (eventCode.equals("masterInMath") && p1.getWisdom() <= 85) {
                return false;
            }
        }
        return true;
    }

    /**
     * modifies: goneThroughEvents.
     * effects: do alternation for some events' consquences.
     */
    public void prerequistieEventAlternation() {
        if (goneThroughEvents.contains("Broke up")) {
            removedAllEventsByCode("Broke up");
        }
        if (goneThroughEvents.contains("Divorced")) {
            removedAllEventsByCode("Divorced");
        }
        if (goneThroughEvents.contains("loveInMath")) {
            removedAllEventsByCode("loveInMath");
        }
        if (goneThroughEvents.contains("masterInMath")) {
            removedAllEventsByCode("masterInMath");
        }

    }

    // effects: remove an event and all of its removed prerequisites from both
    // the string list and the event list so they stay consistent.
    private void removedAllEventsByCode(String code) {
        switch (code) {
            case "Broke up":
                removeBoth("In a lovership");
                removeBoth("Engaged");
                removeBoth("Get married");
                removeBoth("Broke up");
                break;
            case "Divorced":
                removeBoth("In a lovership");
                removeBoth("Engaged");
                removeBoth("Get married");
                removeBoth("Divorced");
                break;
            case "loveInMath":
                removeBoth("intoMath");
                break;
            case "masterInMath":
                removeBoth("loveInMath");
                break;
            default:
                break;
        }
    }

    private void removeBoth(String code) {
        goneThroughEvents.remove(code);
        goneThroughEventsInEvent.removeIf(e -> e.getEventCode().equals(code));
    }

    // effects: get all selected events
    public ArrayList<String> goneThroughEventsInString() {
        return goneThroughEvents;
    }

    public ArrayList<Event> goneThroughEventsInEvent() {
        return goneThroughEventsInEvent;
    }

    /**
     * effects: add & recording all gone through evemts.
     * modifies: addGoneThroughEvents.
     * requires: events never selected or add to this list before,
     * or not in this list(can be deleted).
     */

    public ArrayList<String> addGoneThroughEventsInString(Event event) {
        String code = event.getEventCode();
        if (!goneThroughEvents.contains(code)
                && !containsEventCode(goneThroughEventsInEvent, code)) {
            goneThroughEvents.add(code);
            goneThroughEventsInEvent.add(event);
        }
        return goneThroughEvents;
    }

    public ArrayList<Event> addGoneThroughEventInEvent(Event event) {
        String code = event.getEventCode();
        if (!goneThroughEvents.contains(code)
                && !containsEventCode(goneThroughEventsInEvent, code)) {
            goneThroughEventsInEvent.add(event);
            goneThroughEvents.add(code);
        }
        return goneThroughEventsInEvent;
    }

    private boolean containsEventCode(List<Event> events, String code) {
        for (Event e : events) {
            if (e.getEventCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sync eventInString and eventInEvents
     * @param event this.
     */
    public void addGoneThroughEvent(Event event) {
        if (event == null) {
            return;
        }
        if (!goneThroughEvents.contains(event.getEventCode())) {
            goneThroughEvents.add(event.getEventCode());
            goneThroughEventsInEvent.add(event);
        }
    }

    /**
     * modifies: pick one event from allPossibleEvents and return to main program.
     * requires: age>0
     * effects: return one of those possible events, otherwise return null
     */

    public Event spingWheelForAge(int age) {
        ArrayList<Event> wheelEvents = allPossibleEvents(age);

        if (wheelEvents.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(wheelEvents.size());
        addGoneThroughEvent(wheelEvents.get(randomIndex));
        return wheelEvents.get(randomIndex);
    }

    // effects: get all number events from list
    public int allEvents() {
        return allEvents.size();
    }

    /**
     * effects: at the end of each round do every attributes check.
     */
    public void roundCheck(Player p1) {
        if (p1.getPlayerAge() % 10 == 0) {
            p1.addWisdom();
        }
        if (p1.getPlayerMood() <= 0 || p1.getPlayerSan() <= 0) {
            System.out.println("You have tried best of your life, but unfortunatly your life is over.");
            System.exit(0);
        }
    }
}
