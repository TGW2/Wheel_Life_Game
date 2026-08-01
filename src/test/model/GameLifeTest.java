package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class GameLifeTest {

    private GameLife gameLife;

    @BeforeEach
    void runBefore() {
        gameLife = new GameLife("player", "BC", 10);
    }

    @Test
    void testDefaultConstructor() {
        GameLife defaultGame = new GameLife();

        assertNotNull(defaultGame.getPlayer());
        assertNotNull(defaultGame.getEventLibrary());
        assertEquals(0, defaultGame.getPlayer().getPlayerAge());
    }

    @Test
    void testConstructorWithArguments() {
        assertEquals(10, gameLife.getPlayer().getPlayerAge());
        assertEquals("BC", gameLife.getPlayer().getLocation());
        assertNotNull(gameLife.getEventLibrary());
    }

    @Test
    void testSpinOnce() {
        int ageBefore = gameLife.getPlayer().getPlayerAge();

        String result = gameLife.spinOnce();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(ageBefore + 1, gameLife.getPlayer().getPlayerAge());
    }

    @Test
    void testGetPlayer() {
        Player player = gameLife.getPlayer();

        assertNotNull(player);
        assertSame(player, gameLife.getPlayer());
    }

    @Test
    void testGetCurrentPlayerStatusForGui() {
        String result = gameLife.getCurrentPlayerStatusForGui();

        assertTrue(result.contains("10 years old"));
        assertTrue(result.contains("BC"));
        assertTrue(result.contains("Achievement"));
    }

    @Test
    void testGetEventLibrary() {
        EventLibrary library = gameLife.getEventLibrary();

        assertNotNull(library);
        assertSame(library, gameLife.getEventLibrary());
    }

    @Test
    void testGetExperiencedEventsText() {
        String result = gameLife.getExperiencedEventsText();

        assertEquals(
                "You have not experienced any events yet.",
                result);
    }

    @Test
    void testSaveGameWithPath(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("game.json");

        gameLife.saveGame(path.toString());

        assertTrue(path.toFile().exists());
    }

    @Test
    void testLoadGameWithPath(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("game.json");

        gameLife.spinOnce();
        int expectedAge = gameLife.getPlayer().getPlayerAge();

        gameLife.saveGame(path.toString());

        GameLife loadedGame = new GameLife("Other", "Other", 100);
        loadedGame.loadGame(path.toString());

        assertEquals(expectedAge, loadedGame.getPlayer().getPlayerAge());
    }

    @Test
    void testSaveGameDefaultPath() throws IOException {
        gameLife.saveGame();

        assertDoesNotThrow(() -> gameLife.saveGame());
    }

    @Test
    void testLoadGameDefaultPath() throws IOException {
        gameLife.saveGame();

        assertDoesNotThrow(() -> gameLife.loadGame());
        assertNotNull(gameLife.getPlayer());
    }
}