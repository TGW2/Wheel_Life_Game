package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void runBefore() {
        player = new Player("Player", "Eu", 50);
    }

    @Test
    void testConstructor() {
        assertEquals(50, player.getPlayerAge());
        assertEquals(30, player.getPlayerSan());
        assertEquals(30, player.getPlayerMood());
        assertEquals(60, player.getPlayerWisdom());
        assertEquals("Eu", player.getPlayerlocation());
        assertTrue(player.achievementMade().isEmpty());
    }

    @Test
    void testAddAndReduceSan() {
        player.addPlayerSan(5);
        assertEquals(35, player.getPlayerSan());
        player.reducePlayerSan(10);
        assertEquals(25, player.getPlayerSan());
    }

    @Test
    void testAddAndReduceMood() {
        player.addPlayerMood(8);
        assertEquals(38, player.getPlayerMood());
        player.reducePlayerMood(3);
        assertEquals(35, player.getPlayerMood());
    }

    @Test
    void testAddAge() {
        player.addAge();
        assertEquals(51, player.getPlayerAge());
    }

    @Test
    void testAddWisdom() {
        player.addWisdom();
        assertEquals(63, player.getPlayerWisdom());
    }

    @Test
    void testSetWisdom() {
        player.setWisdom(90);
        assertEquals(90, player.getPlayerWisdom());
        assertEquals(90, player.getWisdom());
    }

    @Test
    void testConditionChangedPositive() {
        Event event = new Event("Good event", 0, 100, 5, 7, 0, "good");
        player.getConditionChanged(event);
        assertEquals(35, player.getPlayerSan());
        assertEquals(37, player.getPlayerMood());
    }

    @Test
    void testConditionChangedNegative() {
        Event event = new Event("Bad event", 0, 100, -8, -10, 0, "bad");
        player.getConditionChanged(event);
        assertEquals(22, player.getPlayerSan());
        assertEquals(20, player.getPlayerMood());
    }

    @Test
    void testAddOneAchievement() {
        player.addAchieveMent("Math Master");
        assertEquals(1, player.achievementMade().size());
        assertEquals("Math Master", player.achievementMade().get(0));
    }

    @Test
    void testAddMultipleAchievements() {
        player.addAchieveMent("Math Master");
        player.addAchieveMent("Life Master");
        assertEquals(2, player.achievementMade().size());
        assertEquals("Math Master", player.achievementMade().get(0));
        assertEquals("Life Master", player.achievementMade().get(1));
    }

    @Test
    void testToString() {
        String result = player.toString();
        assertTrue(result.contains("50"));
        assertTrue(result.contains("Eu"));
        assertTrue(result.contains("30"));
    }
}
