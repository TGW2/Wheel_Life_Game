package model;

import java.util.*;
//stores all events and have methods making dicisions based on player's currrent age
public class EventLibrary {
    private ArrayList<Event> allEvents;

    // modifies:this
    // affects: initialize all events
    public EventLibrary() {
        this.allEvents = new ArrayList<Event>();
        initializeAllEvents();
    }

    // modifies:this
    // affects: add all events based on different age
    public void initializeAllEvents() {
        // at childhood
        allEvents.add(new Event("Eating", 0, 6,2,4));
        allEvents.add(new Event("Sleeping", 0, 6,4,4));
        allEvents.add(new Event("Crying", 0, 6,-1,-5));
        allEvents.add(new Event("Hugging", 0,6,5,10));
        allEvents.add(new Event("Laughing", 0, 6,1,1));
        // elementary school
        allEvents.add(new Event("Taking class", 7, 12,-2,-2));
        allEvents.add(new Event("Playing with friends", 7, 12,4,3));
        allEvents.add(new Event("Forgot doing HW", 7, 12,0,-4));
        allEvents.add(new Event("Playing sports", 7, 12,2,4));
        allEvents.add(new Event("Learning new friends", 7, 12,3,2));
        // middle school
        allEvents.add(new Event("Learning new friends", 13, 15,3,2));
        allEvents.add(new Event("Violate school rules", 13, 15,0,-3));
        allEvents.add(new Event("Have a partner", 13, 15,6,9));
        allEvents.add(new Event("Bad final exam", 13, 15,-5,-7));
        allEvents.add(new Event("disappointed to life", 13, 15,-100,-100));
    }

    // get all possible events based on age
    // requires: age>=0
    // effects: return all events occur at my age
    public ArrayList<Event> allPossibleEvents(int age) {
        ArrayList<Event> possibleEvents = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.canHappenAtAge(age))
                possibleEvents.add(event);
        }
        return possibleEvents;
    }

    // pick one event from allPossibleEvents
    // requires: age>0
    // effects: return one of those possible events, otherwise return null
    public Event spingWheelForAge(int age) {
        ArrayList<Event> wheelEvents = allPossibleEvents(age);

        if (wheelEvents.isEmpty())
            return null;

        Random random = new Random();
        int randomIndex = random.nextInt(wheelEvents.size());
        return wheelEvents.get(randomIndex);
    }

    //effects: get all number events from list
    public int allEvents(){
        return allEvents.size();
    }
}
