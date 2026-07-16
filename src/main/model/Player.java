package model;

// import javax.swing.*;
// import java.awt.*;
import java.util.ArrayList;

/**
 * Player.
 */

public class Player {
    String playerName;
    String locationInEarth;
    int playerAge;
    int playerWisdom;
    int playerSan;
    int playerMood;
    EventLibrary eventlibrary;
    ArrayList<String> achievementMade;

    /**
     * effects: structure player basic information, attributes.
     * modifies: this
     */

    public Player(String name, String location, int age) {
        this.playerName = name;
        this.locationInEarth = location;
        eventlibrary = new EventLibrary();
        playerAge = age;
        playerWisdom = 60;
        playerSan = 30;
        playerMood = 30;
    }

    public void addPlayerSan(int san) {
        playerSan += san;
    }

    public void reducePlayerSan(int san) {
        playerSan -= san;
    }

    public void addPlayerMood(int mood) {
        playerMood += mood;
    }

    public void reducePlayerMood(int mood) {
        playerMood -= mood;
    }

    public void addAge() {
        playerAge++;
    }

    public void addWisdom() {
        playerWisdom += 3;
    }

    public int getPlayerMood() {
        return playerMood;
    }

    public int getPlayerSan() {
        return playerSan;
    }

    public int getPlayerAge() {
        return playerAge;
    }

    public int getPlayerWisdom() {
        return playerWisdom;
    }

    public String getPlayerlocation() {
        return locationInEarth;
    }

    public void setWisdom(int wisdom) {
        playerWisdom = wisdom;
    }

    public int getWisdom() {
        return playerWisdom;
    }

    // modifies: this
    // effects: change the value of San and Mood based on enrolled events
    public void getConditionChanged(Event event) {
        playerSan += event.getSanChange();
        playerMood += event.getMoodChange();
    }
    // effects: to show player image(should done at phase 2)
    // public static Image test1;
    // static {
    // test1 = new ImageIcon("src/main/assest/test1.jpeg").getImage();
    // }

    // effects: for some selected events will have achievements, this is to recored
    // and add
    // the events player achieved, can check in 'check status'
    // modifies: ArrayList<String> achievementMade
    public void addAchieveMent(String achievement) {
        achievementMade.add(achievement);
    }

    // efffects: recorded achievements
    public ArrayList<String> achievementMade() {
        return achievementMade;
    }

    /**
     * toString method.
     * effects: telling the basic attributes current player is.
     */
    public String toString() {
        return "Today you are " + getPlayerAge() + " years old  You're in "
                + getPlayerlocation() + " right now" + "\n Your mood value is: "
                + getPlayerMood() + " Your san value is:" + getPlayerSan();
    }
}
