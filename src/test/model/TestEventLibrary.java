package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.EventLibrary;
import model.Event;

public class TestEventLibrary {
    private Event event;

    @BeforeEach
    void runBefore() {
        event = new Event("sleeping", 0, 6,4,4);
    }

    @Test
    void TestDescription_Age() {
        assertEquals("sleeping", event.getEventDescription());
        assertEquals(0,event.getMinAge());
        assertEquals(6,event.getMaxAge());
    }
    @Test
    void TestSan_MoodChange(){
        assertEquals(4,event.getSanChange());
        assertEquals(4,event.getSanChange());

    }
}
