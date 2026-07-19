package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.JsonReader;
import persistence.JsonWriter;

public class JsonWriterTest {
    @Test
    public void testSaveGame() {
        JsonWriter writer = new JsonWriter();

        Player player = new Player("Player", "BC", 50);
        EventLibrary library = new EventLibrary();

        assertDoesNotThrow(() -> {writer.saveGame(player, library,"src/main/data/playerData.json");});
    }

    @Test
    public void testSaveInvalidFile() {
        JsonWriter writer = new JsonWriter();

        Player player = new Player("Player", "BC", 50);
        EventLibrary library = new EventLibrary();

        assertThrows(IOException.class, () -> {writer.saveGame(player, library,"src/main/data/wrongfile.json");});
    }

    @Test
    public void testWriteThenRead() throws IOException {
        JsonWriter writer = new JsonWriter();

        Player player = new Player("Player", "BC", 50);
        EventLibrary library = new EventLibrary();

        writer.saveGame(player, library, "src/main/data/playerData.json");

        JsonReader reader = new JsonReader("src/main/data/playerData.json");

        Player loaded = reader.loadGame("src/main/data/playerData.json");

        assertEquals(player.getPlayerName(),loaded.getPlayerName());

        assertEquals(player.getPlayerAge(),loaded.getPlayerAge());
    }
}
