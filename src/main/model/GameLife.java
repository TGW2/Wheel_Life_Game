package model;

import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.IOException;
import java.util.List;

/** Coordinates one complete run of Life Wheel. */
public class GameLife {
    private static final String DEFAULT_FILE_PATH = "src/main/data/playerData.json";
    public static final int FINAL_AGE = 90;
    private Player player;
    private EventLibrary eventLibrary;
    private final JsonWriter jsonWriter;
    private String filePath;
    private Event lastEvent;

    public GameLife() {
        this("Wanderer", "Somewhere beneath the same sky", 0);
    }

    public GameLife(String name, String birthplace, int age) {
        player = new Player(name, birthplace, age);
        eventLibrary = new EventLibrary();
        eventLibrary.setPlayer(player);
        filePath = DEFAULT_FILE_PATH;
        jsonWriter = new JsonWriter();
        updateAchievements();
    }

    public String spinOnce() {
        if (isGameOver()) {
            return getEndingText();
        }
        int age = player.getPlayerAge();
        lastEvent = eventLibrary.spingWheelForAge(age);
        if (lastEvent != null) {
            player.getConditionChanged(lastEvent);
        }
        player.addAge();
        if (player.getPlayerAge() % 10 == 0) {
            player.addWisdom();
        }
        updateAchievements();
        if (lastEvent == null) {
            return "Age " + age + " — A quiet year passes. Even stillness leaves a mark.";
        }
        return "Age " + age + " — " + lastEvent.getEventDescription()
                + "\n" + lastEvent.getImpactSummary();
    }

    public Player getPlayer() { return player; }
    public Event getLastEvent() { return lastEvent; }
    public EventLibrary getEventLibrary() { return eventLibrary; }

    public boolean isGameOver() {
        return player.getPlayerAge() >= FINAL_AGE || player.getPlayerMood() <= 0 || player.getPlayerSan() <= 0;
    }

    public double getLifeProgress() {
        return Math.min(1.0, player.getPlayerAge() / (double) FINAL_AGE);
    }

    public String getLifeChapter() {
        int age = player.getPlayerAge();
        if (age < 7) { return "FIRST LIGHT"; }
        if (age < 13) { return "WIDER WORLDS"; }
        if (age < 23) { return "BECOMING"; }
        if (age < 45) { return "MAKING A MARK"; }
        if (age < 68) { return "DEEP ROOTS"; }
        return "GOLDEN HOUR";
    }

    public int getLegacyScore() {
        return player.getWisdom() * 4 + player.getPlayerMood() * 3 + player.getPlayerSan() * 3
                + eventLibrary.goneThroughEventsInEvent().size() * 12;
    }

    public String getEndingTitle() {
        if (player.getPlayerSan() <= 0) { return "A Flame Spent Brightly"; }
        if (player.getPlayerMood() <= 0) { return "The Long Winter"; }
        if (getLegacyScore() >= 900) { return "A Life in Full Colour"; }
        if (player.getWisdom() >= 80) { return "The Quiet Sage"; }
        return "One Story Among the Stars";
    }

    public String getEndingText() {
        return getEndingTitle() + "\n\nAt age " + player.getPlayerAge() + ", " + player.getPlayerName()
                + " leaves behind " + eventLibrary.goneThroughEventsInEvent().size()
                + " defining memories and a legacy score of " + getLegacyScore()
                + ". No life is perfect. Every life is singular.";
    }

    public String getCurrentPlayerStatusForGui() {
        return player.getPlayerName() + " · " + player.getPlayerAge() + " years old · " + getLifeChapter()
                + "\nFrom " + player.getLocation() + "\nSpirit " + player.getPlayerSan()
                + " · Joy " + player.getPlayerMood() + " · Insight " + player.getWisdom()
                + "\nLegacy " + getLegacyScore() + " · Achievements " + player.achievementMade().size();
    }

    public String getExperiencedEventsText() {
        List<Event> events = eventLibrary.goneThroughEventsInEvent();
        if (events.isEmpty()) { return "You have not experienced any events yet."; }
        StringBuilder text = new StringBuilder();
        for (int i = events.size() - 1; i >= 0; i--) {
            Event event = events.get(i);
            text.append("• ").append(event.getEventDescription()).append("\n  ")
                    .append(event.getCategory()).append(" · ").append(event.getImpactSummary()).append("\n\n");
        }
        return text.toString().trim();
    }

    public void saveGame() throws IOException { saveGame(filePath); }
    public void saveGame(String path) throws IOException { jsonWriter.saveGame(player, eventLibrary, path); }
    public void loadGame() throws IOException { loadGame(filePath); }

    public void loadGame(String path) throws IOException {
        filePath = path;
        EventLibrary loadedLibrary = new EventLibrary();
        player = new JsonReader(path).loadGame(path, loadedLibrary);
        eventLibrary = loadedLibrary;
        eventLibrary.setPlayer(player);
        lastEvent = null;
        updateAchievements();
    }

    private void updateAchievements() {
        int age = player.getPlayerAge();
        if (age >= 1) { player.addAchieveMent("First Turn"); }
        if (age >= 18) { player.addAchieveMent("Coming of Age"); }
        if (age >= 50) { player.addAchieveMent("Half a Century"); }
        if (player.getWisdom() >= 85) { player.addAchieveMent("Old Soul"); }
        if (player.getPlayerMood() >= 90) { player.addAchieveMent("Radiant Heart"); }
        if (age >= FINAL_AGE) { player.addAchieveMent("A Life Remembered"); }
    }
}
