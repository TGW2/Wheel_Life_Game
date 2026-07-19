package persistence;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import model.EventLibrary;
import model.Player;
import model.Event;

public class JsonWriter {

    private ArrayList<Event> goneThroughEvents;

    /**
     * modifies: this
     * effects: save current player's enrolled events, all attributes into json
     * format
     */
    public void saveGame(Player player, EventLibrary eventLibrary, String filename) throws IOException {
        JSONObject json = new JSONObject();
        json.put("Player", playerToJson(player));
        json.put("Gone Through Events", eventsToJson(eventLibrary));
        try (PrintWriter printWriter = new PrintWriter(filename)) {
            printWriter.print(json.toString(4));
        }
        System.out.println("Successed!");
    }

    /**
     * effects: transfor player data into json format
     * 
     * @param player the player to convert
     * @return a JSONObject representing the player
     */
    private JSONObject playerToJson(Player player) {
        JSONObject json = new JSONObject();
        json.put("Name", player.getPlayerName());
        json.put("Birthplace", player.getLocation());
        json.put("Age", player.getPlayerAge());
        json.put("Wisdom", player.getWisdom());
        json.put("San", player.getPlayerSan());
        json.put("Mood", player.getPlayerMood());

        JSONArray achievements = new JSONArray();
        for (String achievement : player.achievementMade()) {
            achievements.put(achievement);
        }
        json.put("AchievementMade",achievements);
        return json;
    }

    /**
     * effects: all listing events into json format
     * 
     * @param eventLibrary the event library containing completed events
     * @return a JSONArray containing all completed events
     */
    private JSONArray eventsToJson(EventLibrary eventLibrary) {
        
        JSONArray jsonArray = new JSONArray();
        
        goneThroughEvents = eventLibrary.goneThroughEventsInEvent();

        for (Event eachEvent : goneThroughEvents) {
            JSONObject json = new JSONObject();
            json.put("eventDescription",eachEvent.getEventDescription());
            json.put("minAge", eachEvent.getMinAge());
            json.put("maxAge", eachEvent.getMaxAge());
            json.put("sanChange", eachEvent.getSanChange());
            json.put("moodChange", eachEvent.getMoodChange());
            json.put("wisdomChange", eachEvent.getWisdomChange());
            json.put("eventCode", eachEvent.getEventCode());
            JSONArray jsonArrayPrerequistie = new JSONArray();
            for (String prerequisites : eachEvent.getPrerequisites()) {
                jsonArrayPrerequistie.put(prerequisites);
            }
            json.put("Prerequistie",jsonArrayPrerequistie);
            jsonArray.put(json);
        }
        return jsonArray;
    }

}
