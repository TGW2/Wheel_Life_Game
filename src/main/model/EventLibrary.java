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
    private final Random random = new Random();
    private String lastEventCode;

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
        allEvents.add(new Event("You turn a chalk path into an entire kingdom.", 0, 7, 2, 2, 1, "Hopscotch"));
        allEvents.add(new Event("A shy hello becomes your first real friendship.", 0, 7, 3, 4, 1, "A childhood friend"));
        allEvents.add(new Event("A small nighttime accident feels like the end of the world.", 0, 7, -2, -2, 1, "Bedwetting"));
        allEvents.add(new Event("You try on grown-up shoes and imagine who you might become.", 0, 7, 2, 2, 2, "Looking to be an adult"));
        allEvents.add(new Event("A tiny lie grows heavier every time you remember it.", 0, 7, -3, -3, 1, "Lying to parents"));

    }

    public void initializeElementaryEvents() {
        // elementary school
        allEvents.add(new Event("A patient teacher shows you how questions open doors.", 7, 12, -1, 1, 4, "Taking class"));
        allEvents.add(new Event("The afternoon disappears in games with friends.", 7, 12, 4, 5, 1, "Playing with friends"));
        allEvents.add(new Event("You remember the homework precisely one minute too late.", 7, 12, -1, -4, 1, "Forgot doing HW"));
        allEvents.add(new Event("Your team loses, but you discover that you love the game.", 7, 12, 3, 4, 2, "Playing sports"));
        allEvents.add(new Event("You sit beside someone new at lunch.", 7, 12, 3, 3, 2, "Learning new friends"));

    }

    public void initializeMiddleSchoolEvents() {
        // middle school
        allEvents.add(new Event("You break a rule to impress the wrong crowd.", 13, 15, -2, -3, 2, "Violate school rules"));
        allEvents.add(new Event("A friendship becomes something tender and uncertain.", 13, 15, 5, 7, 2, "Have a partner"));
        allEvents.add(new Event("A red mark on a final exam shakes your confidence.", 13, 15, -5, -6, 3, "Bad final exam"));
        allEvents.add(new Event("For a difficult season, the future feels very far away.", 13, 15, -8, -9, 4, "disappointed to life"));

    }

    public void initializeHighSchoolAndUniEvents() {
        // high school & Uni
        allEvents.add(new Event("You find your people in a club nobody else understands.", 16, 22, 4, 5, 3, "Join a club you like"));
        allEvents.add(new Event("You skip class for a perfect, reckless afternoon.", 16, 22, 1, 4, -2, "Skipping class"));
        allEvents.add(new Event("Your first paycheque feels impossibly important.", 16, 22, 3, 4, 3, "Get a part time job"));

    }

    public void initializeLongLifeEvents() {
        // long life events
        Event inAlovership = new Event("You meet someone who makes ordinary days luminous.", 22, 100, 8, 9, 2, "In a lovership");
        allEvents.add(inAlovership);

        Event brokeUp = new Event("A relationship ends. The silence afterward teaches you its shape.", 22, 100, -6, -7, 4, "Broke up");
        brokeUp.addPrerequisite("In a lovership");
        allEvents.add(brokeUp);

        Event engaged = new Event("Under a familiar sky, you promise to build a future together.", 22, 100, 6, 8, 2, "Engaged");
        engaged.addPrerequisite("In a lovership");
        allEvents.add(engaged);

        Event getMarried = new Event("You marry surrounded by the people who carried you here.", 22, 100, 10, 12, 3, "Get married");
        getMarried.addPrerequisite("Engaged");
        allEvents.add(getMarried);

        Event divorced = new Event("You choose separate roads, taking grief and gratitude with you.", 22, 100, -8, -9, 6, "Divorced");
        divorced.addPrerequisite("Get married");
        allEvents.add(divorced);

        allEvents.add(new Event("A shared meal tastes like belonging.", 0, 100, 2, 4, 1, "Eating"));
        allEvents.add(new Event("You finally give yourself permission to rest.", 0, 100, 4, 3, 1, "Sleeping"));
        allEvents.add(new Event("You cry without apology, and breathe easier afterward.", 0, 100, 1, -2, 2, "Crying"));
        allEvents.add(new Event("A long hug says what words cannot.", 0, 100, 5, 7, 1, "Hugging"));
        allEvents.add(new Event("Laughter catches the whole room by surprise.", 0, 100, 3, 5, 1, "Laughing"));

    }

    public void initializeMathEvents() {
        // math longlife specialization
        Event intoMath = new Event("A puzzle reveals the hidden music of numbers.", 6, 100, 1, 2, 5, "intoMath");
        allEvents.add(intoMath);

        Event loveInMath = new Event("You lose track of time chasing an elegant proof.", 6, 100, 2, 3, 6, "loveInMath");
        loveInMath.addPrerequisite("intoMath");
        allEvents.add(loveInMath);

        Event masterInMath = new Event("Years of curiosity crystallize into genuine mastery.", 6, 100, 7, 8, 10, "masterInMath");
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
        }
        if ("masterInMath".equals(event.getEventCode()) && p1 != null && p1.getWisdom() <= 85) {
            return false;
        }
        return true;
    }

    public void setPlayer(Player player) {
        this.p1 = player;
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
            EventLog.getInstance().logEvent(new Event4("Event added: "+event.getEventDescription()));
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
            EventLog.getInstance().logEvent(new Event4("Event added: " + event.getEventDescription()));
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
        if (wheelEvents.size() > 1 && lastEventCode != null) {
            wheelEvents.removeIf(event -> lastEventCode.equals(event.getEventCode()));
        }
        Event selected = wheelEvents.get(random.nextInt(wheelEvents.size()));
        lastEventCode = selected.getEventCode();
        addGoneThroughEvent(selected);
        return selected;
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
            EventLog.getInstance().logEvent(new Event4("The journey reached its final chapter."));
        }
    }
}
