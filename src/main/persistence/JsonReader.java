package persistence;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

import model.Player;
import model.Event;

public class JsonReader {
    private String source;
    Player p1;
    Event event;

    public JsonReader(String source) {
        this.source = source;
    }

    /**
     * effects: read file and create rebuild player, integrate all player's attribute, and events.
     * @param filename path of the file to read
     * @return the player reconstructed from the saved file
     * @throws IOException if an error occurs while reading the file
     */
    public Player loadGame(String filename) throws IOException {
        return p1;
    }

    /**
     * read json file and convert into String.
     * @param filename path of the file to read
     * @return contents of the file as a string
     * @throws IOException if an error occurs while reading the file
     */
    private String readFile(String filename) throws IOException {
        return null;
    }

    /**
     * effects: read player attributes.
     */
    private Player loadPlayer(JSONObject jsonObject) {
        return null;
    }

    /**
     * modifies: Player p1.
     * effects loading all events and add to player object
     * @param player player to which the events are added
     * @param jsonObject JSON object containing the saved events
     */
    private void loadEvents(Player player, JSONObject jsonObject){
        
    }

    /**
     * modifies: event
     * effects: add single events
     * @param eventJson JSON object containing event information
     * @return event constructed from the JSON data
     */
    private Event loadEvent(JSONObject eventJson) {
        return null;
    }
}
