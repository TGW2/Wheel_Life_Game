package persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;

import model.Player;
import model.Event;
import model.EventLibrary;

public class JsonReader {
    private String source;
    Player p1;
    Event event;

    public JsonReader(String source) {
        this.source = source;
    }

    /**
     * effects: read file and create rebuild player, integrate all player's
     * attribute, and events.
     * 
     * @param filename path of the file to read
     * @return the player reconstructed from the saved file
     * @throws IOException if an error occurs while reading the file
     */
    public Player loadGame(String filename, EventLibrary eventLibrary) throws IOException {
        String jsonData = readFile(filename);
        JSONObject gameJson = new JSONObject(jsonData);
        JSONObject playerJson = gameJson.getJSONObject("Player");
        Player player = loadPlayer(playerJson);
        loadEvents(eventLibrary, gameJson);
        return player;
    }

    /**
     * read json file and convert into String.
     * 
     * @param filename path of the file to read
     * @return contents of the file as a string
     * @throws IOException if an error occurs while reading the file
     */
    private String readFile(String filename) throws IOException {
        return Files.readString(Path.of(filename));
    }

    /**
     * effects: read player attributes.
     */
    private Player loadPlayer(JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        String birthplace = jsonObject.getString("Birthplace");
        int age = jsonObject.getInt("Age");
        int wisdom = jsonObject.getInt("Wisdom");
        int san = jsonObject.getInt("San");
        int mood = jsonObject.getInt("Mood");
        Player player = new Player(name, birthplace, age);
        player.setWisdom(wisdom);
        player.setPlayerMood(mood);
        player.setPlayerSan(san);
        player.achievementMade();
        return player;
    }

    /**
     * modifies: eventLibrary
     * effects loading all events and add to player object
     * 
     * @param eventLibrary eventLibrary to which the events are added
     * @param jsonObject   object containing the saved events
     */
    private void loadEvents(EventLibrary eventLibrary, JSONObject jsonObject) {
        JSONArray events = jsonObject.getJSONArray("Gone Through Events");
        for (int i = 0; i < events.length(); i++) {
            JSONObject eventJson = events.getJSONObject(i);
            Event event = loadEvent(eventJson);
            eventLibrary.addGoneThroughEventInEvent(event);
        }
    }

    /**
     * modifies: event
     * effects: add single events
     * 
     * @param eventJson JSON object containing event information
     * @return event constructed from the JSON data
     */
    private Event loadEvent(JSONObject eventJson) {
        String eventDescription = eventJson.getString("eventDescription");
        int minAge = eventJson.getInt("minAge");
        int maxAge = eventJson.getInt("maxAge");
        int sanChange = eventJson.getInt("sanChange");
        int moodChange = eventJson.getInt("moodChange");
        int wisdomChange = eventJson.getInt("wisdomChange");
        String eventCode = eventJson.getString("eventCode");
        JSONArray prerequistieJson = eventJson.getJSONArray("Prerequistie");
        ArrayList<String> prerequisites = new ArrayList<>();
        for (int i = 0; i < prerequistieJson.length(); i++) {
            prerequisites.add(prerequistieJson.getString(i));
        }
        Event event = new Event(eventDescription, minAge, maxAge, sanChange, moodChange, wisdomChange, eventCode);
        for (String p : prerequisites) {
            event.addPrerequisite(p);
        }

        return event;
    }
}
