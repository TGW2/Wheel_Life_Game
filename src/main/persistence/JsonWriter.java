package persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import model.EventLibrary;
import model.Player;
import model.Event;

public class JsonWriter {

    /**
     * modifies: this
     * effects: save current player's enrolled events, all attributes into json format
     */
    public void saveGame(Player p1, EventLibrary eventLibrary, String filename) throws IOException { 
        try (PrintWriter printWriter = new PrintWriter(filename)) {
            
        } catch (Exception e) {
           
        }
    }

    /**
     * effects: transfor player data into json format
     * @param player the player to convert
     * @return a JSONObject representing the player
     */
    private JSONObject playerToJson(Player player) {
        return null;
    }

    /**
     * effects: all listing events into json format
     * @param eventLibrary the event library containing completed events
     * @return a JSONArray containing all completed events
     */
    private JSONArray eventsToJson(EventLibrary eventLibrary) {
        return null;
    }

}
