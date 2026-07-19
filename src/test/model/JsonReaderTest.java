package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.*;

public class JsonReaderTest {
    EventLibrary eventLibrary;

    @Test
    public void testLoadGameNoEvents() {
        JsonReader reader = new JsonReader("src/main/data/dataStorage.json.json");
        eventLibrary = new EventLibrary();
        try {
            Player player = reader.loadGame("src/main/data/dataStorage.json",eventLibrary);
            assertEquals("a", player.getPlayerName());
            assertEquals("aa", player.getLocation());
            assertEquals(14, player.getPlayerAge());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testLoadGameWithEvents() {
        JsonReader reader = new JsonReader("src/main/data/dataStorage.json.json");
        eventLibrary = new EventLibrary();
        try {
            Player player = reader.loadGame("src/main/data/dataStorage.json",eventLibrary);
            assertEquals("a", player.getPlayerName());
            assertEquals("aa", player.getLocation());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testLoadGameFileDoesNotExist() {
        eventLibrary = new EventLibrary();
        JsonReader reader = new JsonReader("src/main/data/playerData.json");
        assertThrows(IOException.class, () -> {
            reader.loadGame("a/b/c.json",eventLibrary);
        });
    }
}
