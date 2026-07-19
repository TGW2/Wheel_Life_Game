package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.*;

public class JsonReaderTest {
    @Test
    public void testLoadGameNoEvents() {
        JsonReader reader = new JsonReader("src/main/data/playerData.json");
        try {
            Player player = reader.loadGame("src/main/data/playerData.json");
            assertEquals("Player", player.getPlayerName());
            assertEquals("BC", player.getLocation());
            assertEquals(50, player.getPlayerAge());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testLoadGameWithEvents() {
        JsonReader reader =
                new JsonReader("src/main/data/playerData.json");
        try {
            Player player =
                    reader.loadGame("src/main/data/playerData.json");
            assertEquals("Player", player.getPlayerName());
            assertEquals("BC", player.getLocation());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testLoadGameFileDoesNotExist() {
        JsonReader reader = new JsonReader("src/main/data/playerData.json");
        assertThrows(IOException.class, () -> {reader.loadGame("src/main/data/playerData.json");});
    }
}
