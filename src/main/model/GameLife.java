package model;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * gamelife is used for gamepanel to call method and return string content as
 * it easire for eventoutput
 */
public class GameLife {
    private static final String DEFAULT_FILE_PATH = "src/main/data/dataStorage.json";

    private Player player;
    private EventLibrary eventLibrary;
    private final JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String filePath;
    private int lastEventAge;

    /**
     * Creates a new game with a fresh player and event library.
     */
    public GameLife() {
        this("player1", "BC", 0);
    }

    /**
     * Creates a new game, allowing an explicit starting age. Useful if a
     * setup screen ever wants to customise the start age.
     *
     * @param name       the player's name
     * @param birthplace the player's birthplace
     * @param age        the player's starting age
     */
    public GameLife(String name, String birthplace, int age) {
        this.player = new Player(name, birthplace, age);
        this.eventLibrary = new EventLibrary();
        this.filePath = DEFAULT_FILE_PATH;
        this.jsonWriter = new JsonWriter();
        this.jsonReader = new JsonReader(filePath);
        this.lastEventAge = age;
    }

    /**
     * Spins the wheel once: picks a random event possible at the player's
     * current age, applies its attribute changes, records it, and ages the
     * player by one year.
     *
     * @return a human-readable message describing what happened
     */
    public String spinOnce() {
        int age = player.getPlayerAge();

        Event event = eventLibrary.spingWheelForAge(age);

        if (event == null) {
            lastEventAge = age;
            player.addAge();
            return "No events are available at age " + age + ". You grow one year older.";
        }

        lastEventAge = age;
        player.getConditionChanged(event);
        player.addAge();

        StringBuilder sb = new StringBuilder();
        sb.append("\nToday you are : ").append(age).append(" years old")
                .append("\nYou get : " + event.getEventDescription()).append("\n");
        sb.append("Your mood value is: ").append(player.getPlayerMood())
                .append("\nYour san value is:").append(player.getPlayerSan());
        return sb.toString();
    }

    /**
     * @return the current player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * 
     * @return description about player current status
     */
    public String getCurrentPlayerStatusForGui() {

        StringBuilder sb = new StringBuilder();
        sb.append("\nToday you are : ").append(lastEventAge).append(" years old")
                .append("\nYou're in : " + getPlayer().getLocation()).append("\n");
        sb.append("Your mood value is: ").append(getPlayer().getPlayerMood())
                .append("\nYour san value is:").append(getPlayer().getPlayerSan());
        sb.append("\nAchievement: ").append(getPlayer().achievementMade().toString());

        return sb.toString();
    }

    /**
     * @return the current event library
     */
    public EventLibrary getEventLibrary() {
        return eventLibrary;
    }

    /**
     * Builds a multi-line text report of every event experienced so far.
     *
     * @return the experienced events as text, or a placeholder if none yet
     */
    public String getExperiencedEventsText() {
        List<Event> goneThrough = eventLibrary.goneThroughEventsInEvent();

        if (goneThrough.isEmpty()) {
            return "You have not experienced any events yet.";
        }

        StringBuilder sb = new StringBuilder();
        for (Event event : goneThrough) {
            sb.append("\n- ").append(event.getEventDescription()).append("");
        }
        return sb.toString().trim();
    }

    /**
     * Saves the current player and experienced events to the default file.
     *
     * @throws IOException if the file cannot be written
     */
    public void saveGame() throws IOException {
        saveGame(filePath);
    }

    /**
     * Saves the current player and experienced events to the given file.
     *
     * @param path the file to write to
     * @throws IOException if the file cannot be written
     */
    public void saveGame(String path) throws IOException {
        jsonWriter.saveGame(player, eventLibrary, path);
    }

    /**
     * Loads the player and experienced events from the default file,
     * replacing the current game state.
     *
     * @throws IOException if the file cannot be read
     */
    public void loadGame() throws IOException {
        loadGame(filePath);
    }

    /**
     * Loads the player and experienced events from the given file,
     * replacing the current game state.
     *
     * @param path the file to read from
     * @throws IOException if the file cannot be read
     */
    public void loadGame(String path) throws IOException {
        this.filePath = path;
        this.jsonReader = new JsonReader(path);

        EventLibrary loadedLibrary = new EventLibrary();
        this.player = jsonReader.loadGame(path, loadedLibrary);
        this.eventLibrary = loadedLibrary;

        this.lastEventAge = Math.max(0, this.player.getPlayerAge() - 1);
    }
}
