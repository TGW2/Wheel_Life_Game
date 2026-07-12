package model;

//making standard for events in order thay can be standlarized stored in EventLirary
public class Event {
    String eventDescription;
    int minAge;
    int maxAge;
    int sanChange;
    int moodChange;
    int wisdomChange;

    // requires: minAge>=0, maxAge>=minAge
    // modifies: this
    // effects: set up an event, with events description and age range
    public Event(String eventDescription, int minAge, int maxAge,int sanChange,int moodChange) {
        this.eventDescription = eventDescription;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.sanChange = sanChange;
        this.moodChange = moodChange;
    }

    // effects: get events description
    public String getEventDescription() {
        return eventDescription;
    }

    // effects: get min age
    public int getMinAge() {
        return minAge;
    }

    // efects: get max age
    public int getMaxAge() {
        return maxAge;
    }

    public int getSanChange(){
        return sanChange;
    }
    public int getMoodChange(){
        return moodChange;
    }

    // requires: age>=0
    // effects: identify whether can happen at that age
    public boolean canHappenAtAge(int age) {
        return age >= minAge && age <= maxAge;
    }
}