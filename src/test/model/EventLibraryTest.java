package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// import model.EventLibrary;
// import model.Event;
import java.util.ArrayList;

public class EventLibraryTest {
    private ArrayList<Event> allEventsCouldHappen1, allEventsCouldHappen2;
    private EventLibrary allPossibleEvents;
    private EventLibrary eventLibrary, eventLibrary2;
    private Event event;
    private Player p1, p2, p3, p4;

    @BeforeEach
    void runBefore() {
        allPossibleEvents = new EventLibrary();
        eventLibrary = new EventLibrary();
        event = new Event("sleeping", 0, 6, 4, 4, null);
        p1 = new Player("Player1", "Eu", 18);
        p2 = new Player("Player1", "Eu", 22);
        allEventsCouldHappen1 = allPossibleEvents.allPossibleEvents(p1.getPlayerAge());
        allEventsCouldHappen2 = allPossibleEvents.allPossibleEvents(p2.getPlayerAge());
    }

    @Test
    void TestEvents() {
        assertEquals(30, eventLibrary.allEvents());
    }

    @Test
    void TestDescription_Age() {
        assertEquals("sleeping", event.getEventDescription());
        assertEquals(0, event.getMinAge());
        assertEquals(6, event.getMaxAge());
    }

    @Test
    void TestSan_MoodChange() {
        assertEquals(4, event.getSanChange());
        assertEquals(4, event.getMoodChange());
    }

    @Test
    void TestEvents_AgeScale() {
        assertEquals(9, allEventsCouldHappen1.size());
        assertEquals(10, allEventsCouldHappen2.size());
    }

    // to test if prerequiste required events can work successfully when prerequiste
    // is reached
    @Test
    public void testRelationshipUnlocksBrokeUpAndEngaged() {
        Event InAlovership = new Event("In a lovership",22,100,10,10,"In a lovership");
        eventLibrary.addGoneThroughEvents(InAlovership);
        ArrayList<Event> possibleEvents = eventLibrary.allPossibleEvents(23);
        boolean containsBrokeUp = false;
        boolean containsEngaged = false;
        for (Event event : possibleEvents) {
            if (event.getEventCode().equals("Broke up")) {
                containsBrokeUp = true;
            }
            if (event.getEventCode().equals("Engaged")) {
                containsEngaged = true;
            }
        }
        assertTrue(containsBrokeUp);
        assertTrue(containsEngaged);
    }

    @BeforeEach
    void runBefore2() {
        p3 = new Player("Player 3", "location", 80);
        p3.setWisdom(100);
        eventLibrary2 = new EventLibrary();
    }

    // testing achievement program
    @Test
    public void achievementStatus() {
        Event intoMath = new Event("intoMath", 6, 100, 1, 1, "intoMath");
        Event loveInMath = new Event("loveInMath", 6, 100, 2, 2, "loveInMath");
        eventLibrary2.addGoneThroughEvents(intoMath);
        eventLibrary2.addGoneThroughEvents(loveInMath);
        ArrayList<Event> possibleEvents = eventLibrary2.allPossibleEvents(p3.getPlayerAge());
        boolean containsMasterInMath = false;
        for (Event event : possibleEvents) {
            if (event.getEventCode().equals("masterInMath")) {
                containsMasterInMath = true;
            }
        }
        assertTrue(containsMasterInMath);
    }

    @BeforeEach
    void runBefore3() {
        p4 = new Player("Player4", "Eu", 50);
    }

    @Test
    public void playerAttributeTest() {
        assertEquals(30, p4.getPlayerSan());
        assertEquals(30, p4.getPlayerMood());
        assertEquals(60, p4.getPlayerWisdom());
        p4.addPlayerSan(1);
        p4.addPlayerMood(10);
        p4.addWisdom();
        assertEquals(31, p4.getPlayerSan());
        assertEquals(40, p4.getPlayerMood());
        assertEquals(63, p4.getPlayerWisdom());
        p4.reducePlayerSan(10);
        p4.reducePlayerMood(10);
        assertEquals(21, p4.getPlayerSan());
        assertEquals(30, p4.getPlayerMood());
    }

    @Test
    void testPrerequisiteEventAlternationBrokeUp() {
        Event inAlovership = new Event("In a lovership", 0, 100, 0, 0, "In a lovership");
        Event engaged = new Event("Engaged", 0, 100, 0, 0, "Engaged");
        Event married = new Event("Married", 0, 100, 0, 0, "Get married");
        Event brokeUp = new Event("Broke up", 0, 100, 0, 0, "Broke up");

        eventLibrary.addGoneThroughEvents(inAlovership);
        eventLibrary.addGoneThroughEvents(engaged);
        eventLibrary.addGoneThroughEvents(married);
        eventLibrary.addGoneThroughEvents(brokeUp);

        eventLibrary.prerequistieEventAlternation();

        assertFalse(eventLibrary.goneThroughEvents().contains("In a lovership"));
        assertFalse(eventLibrary.goneThroughEvents().contains("Engaged"));
        assertFalse(eventLibrary.goneThroughEvents().contains("Get married"));
        assertFalse(eventLibrary.goneThroughEvents().contains("Broke up"));
    }

    @Test
    void testPrerequisiteEventAlternationDivorced() {
        eventLibrary.addGoneThroughEvents(new Event("", 0, 100, 0, 0, "In a lovership"));
        eventLibrary.addGoneThroughEvents(new Event("", 0, 100, 0, 0, "Engaged"));
        eventLibrary.addGoneThroughEvents(new Event("", 0, 100, 0, 0, "Get married"));
        eventLibrary.addGoneThroughEvents(new Event("", 0, 100, 0, 0, "Divorced"));

        eventLibrary.prerequistieEventAlternation();

        assertFalse(eventLibrary.goneThroughEvents().contains("In a lovership"));
        assertFalse(eventLibrary.goneThroughEvents().contains("Engaged"));
        assertFalse(eventLibrary.goneThroughEvents().contains("Get married"));
        assertFalse(eventLibrary.goneThroughEvents().contains("Divorced"));
    }

    @Test
    void testPrerequisiteEventAlternationLoveInMath() {
        eventLibrary.addGoneThroughEvents(new Event("", 0, 100, 0, 0, "intoMath"));
        eventLibrary.addGoneThroughEvents(new Event("", 0, 100, 0, 0, "loveInMath"));

        eventLibrary.prerequistieEventAlternation();

        assertFalse(eventLibrary.goneThroughEvents().contains("intoMath"));
        assertTrue(eventLibrary.goneThroughEvents().contains("loveInMath"));
    }

    @Test
    void testPrerequisiteEventAlternationMasterInMath() {
        eventLibrary.addGoneThroughEvents(new Event("", 0, 100, 0, 0, "loveInMath"));
        eventLibrary.addGoneThroughEvents(new Event("", 0, 100, 0, 0, "masterInMath"));
        eventLibrary.prerequistieEventAlternation();
        assertFalse(eventLibrary.goneThroughEvents().contains("loveInMath"));
        assertTrue(eventLibrary.goneThroughEvents().contains("masterInMath"));
    }

    @Test
    void testSpinWheelForAgeReturnsEvent() {
        Event result = eventLibrary.spingWheelForAge(10);
        assertNotNull(result);
        assertTrue(result.canHappenAtAge(10));
        assertTrue(eventLibrary.goneThroughEvents().contains(result.getEventCode()));
    }

    @Test
    void testSpinWheelForAgeReturnsNull() {
        Event result = eventLibrary.spingWheelForAge(101);
        assertNull(result);
        assertTrue(eventLibrary.goneThroughEvents().isEmpty());
    }

    @Test
    void testRoundCheckAtAgeMultipleOfTen() {
        Player player = new Player("Player", "Eu", 20);
        eventLibrary.roundCheck(player);
        assertEquals(63, player.getPlayerWisdom());
    }

    @Test
    void testRoundCheckAtAgeNotMultipleOfTen() {
        Player player = new Player("Player", "Eu", 21);
        eventLibrary.roundCheck(player);
        assertEquals(60, player.getPlayerWisdom());
    }
}
