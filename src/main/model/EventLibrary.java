package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a game event library that stores all events shows on game.
 * And doing logic judgment on selected events.
 */

public class EventLibrary {
  private ArrayList<Event> allEvents;
  private ArrayList<String> goneThroughEvents = new ArrayList<>();
  private Player p1;

  // modifies:this
  // affects: initialize all events
  public EventLibrary() {
    this.allEvents = new ArrayList<Event>();
    initializeAllEvents();
  }

  /**
   * modifies:this.
   * affects: add all events based on different age.
   */

  public void initializeAllEvents() {
    // particular time events
    // at childhood
    allEvents.add(new Event("Hopscotch", 0, 7, 2, 2, "Hopscotch"));
    allEvents.add(new Event("A childhood friend", 0, 7, 2, 2, "A childhood friend"));
    allEvents.add(new Event("Bedwetting", 0, 7, -2, -2, "A childhood friend"));
    allEvents.add(new Event("Looking to be an adult", 0, 7, 1, 1, "A childhood friend"));
    allEvents.add(new Event("Lying to parents", 0, 7, -3, -3, "A childhood friend"));
    // elementary school
    allEvents.add(new Event("Taking class", 7, 12, -2, -2, "Taking class"));
    allEvents.add(new Event("Playing with friends", 7, 12, 4, 3, "Playing with friends"));
    allEvents.add(new Event("Forgot doing HW", 7, 12, 0, -4, "Forgot doing HW"));
    allEvents.add(new Event("Playing sports", 7, 12, 2, 4, "Playing sports"));
    allEvents.add(new Event("Learning new friends", 7, 12, 3, 2, "Learning new friends"));
    // middle school
    allEvents.add(new Event("Violate school rules", 13, 15, 0, -3, "Violate school rules"));
    allEvents.add(new Event("Have a partner", 13, 15, 6, 9, "Have a partner"));
    allEvents.add(new Event("Bad final exam", 13, 15, -5, -7, "Bad final exam"));
    allEvents.add(new Event("disappointed to life", 13, 15, -100, -100, "disappointed to life"));
    // high school & Uni
    allEvents.add(new Event("Join a club you like", 16, 22, 2, 2, "Join a club you like"));
    allEvents.add(new Event("Skipping class", 16, 22, 1, 4, "Skipping class"));
    allEvents.add(new Event("Get a part time job", 16, 22, 5, 3, "Get a part time job"));

    // long life events
    Event inAlovership = new Event("In a lovership", 22, 100, 10, 10, "In a lovership");
    allEvents.add(inAlovership);

    Event brokeUp = new Event("Broke up", 22, 100, -5, -5, "Broke up");
    brokeUp.addPrerequisite("In a lovership");
    allEvents.add(brokeUp);

    Event engaged = new Event("Engaged", 22, 100, 2, 2, "Engaged");
    engaged.addPrerequisite("In a lovership");
    allEvents.add(engaged);

    Event getMarried = new Event("Get married -- Congurates!", 22, 100, 15, 15, "Get married");
    getMarried.addPrerequisite("Engaged");
    allEvents.add(getMarried);

    Event divorced = new Event("Divorced -- Sry to hear that", 22, 100, -8, -8, "Divorced");
    divorced.addPrerequisite("Get married");
    allEvents.add(divorced);

    allEvents.add(new Event("Eating", 0, 100, 2, 4, "Eating"));
    allEvents.add(new Event("Sleeping", 0, 100, 4, 4, "Sleeping"));
    allEvents.add(new Event("Crying", 0, 100, -1, -5, "Crying"));
    allEvents.add(new Event("Hugging", 0, 100, 5, 10, "Hugging"));
    allEvents.add(new Event("Laughing", 0, 100, 1, 1, "Laughing"));

    // math longlife specialization
    Event intoMath = new Event("intoMath", 6, 100, 1, 1, "intoMath");
    allEvents.add(intoMath);

    Event loveInMath = new Event("loveInMath", 6, 100, 2, 2, "loveInMath");
    loveInMath.addPrerequisite("intoMath");
    allEvents.add(loveInMath);

    Event masterInMath = new Event("masterInMath", 6, 100, 8, 8, "masterInMath");
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
      if (!goneThroughEvents().contains(eventCode)) {
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
      goneThroughEvents.remove("In a lovership");
      goneThroughEvents.remove("Engaged");
      goneThroughEvents.remove("Get married");
      goneThroughEvents.remove("Broke up");
    }
    if (goneThroughEvents.contains("Divorced")) {
      goneThroughEvents.remove("In a lovership");
      goneThroughEvents.remove("Engaged");
      goneThroughEvents.remove("Get married");
      goneThroughEvents.remove("Divorced");
    }
    if (goneThroughEvents.contains("loveInMath")) {
      goneThroughEvents.remove("intoMath");
    }
    if (goneThroughEvents.contains("masterInMath")) {
      goneThroughEvents.remove("loveInMath");
    }

  }

  // effects: get all selected events
  public ArrayList<String> goneThroughEvents() {
    return goneThroughEvents;

  }

  /**
   * effects: add & recording all gone through evemts.
   * modifies: addGoneThroughEvents.
   * requires: events never selected or add to this list before,
   * or not in this list(can be deleted).
   */

  public ArrayList<String> addGoneThroughEvents(Event event) {
    if (!goneThroughEvents.contains(event.getEventCode())) {
      goneThroughEvents.add(event.getEventCode());
    }
    return goneThroughEvents;
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
    addGoneThroughEvents(wheelEvents.get(randomIndex));
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
