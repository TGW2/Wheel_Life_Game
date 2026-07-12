package model;

import javax.swing.*;
import java.awt.*;

public class Player {
    String playerName;
    String locationInEarth;
    int playerAge;
    int playerWisdom;
    int playerSan;
    int playerMood;
    EventLibrary eventlibrary;

    public Player(String name, String location) {
        this.playerName = name;
        this.locationInEarth = location;
        eventlibrary = new EventLibrary();
        playerAge = 1;
        playerWisdom = 60;
        playerSan = -10;
        playerMood = -10;
       
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

    public int getPlayerMood() {
        return playerMood;
    }

    public int getPlayerSan() {
        return playerSan;
    }

    public int getPlayerAge() {
        return playerAge;
    }

    public String getPlayerlocation() {
        return locationInEarth;
    }

    public void wisdomCheck() {
        if (playerAge % 10 == 0 && playerAge != 0) {
            playerWisdom++;
        }
    }

    //modifies: this
    //effects: change the value of San and Mood based on enrolled events
    public void getConditionChanged(Event event) {
        playerSan += event.getSanChange();
        playerMood += event.getMoodChange();
        // if (playerSan > 100)
        //     playerSan = 100;
        // if (playerSan < 0) {
        //     playerSan = 0;
            
        // }
        // if (playerMood > 100)
        //     playerMood = 100;
        // if (playerMood < 0) {
        //     playerMood = 0;
        //     playerSan = 0;

        // }
    }

    public static Image test1;
    static {
        test1 = new ImageIcon("src/main/assest/test1.jpeg").getImage();
    }

    public String toString() {
        return "Today you are " + getPlayerAge() + " years old  You're in " + getPlayerlocation() + " right now"
                + "\n Your mood value is: " + getPlayerMood() + " Your san value is:" + getPlayerSan();
    }
}
