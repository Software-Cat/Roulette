package io.github.softwarecat;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OutcomeTest {

    @Test
    public void winAmount() {
        Outcome outcome = new Outcome("Name", 2);

        assertEquals(4, outcome.winAmount(2));
    }

    @Test
    public void testEquals() {
        Outcome outcome1 = new Outcome("Name 1", 1);
        Outcome outcome2 = new Outcome("Name 1", 1);
        Outcome outcome3 = new Outcome("Name 2", 2);

        assertEquals(outcome1, outcome2);
        assertNotEquals(outcome1, outcome3);
        assertNotEquals(outcome2, outcome3);
    }

    @Test
    public void testHashCode() {
        Outcome outcome1 = new Outcome("Name 1", 1);
        Outcome outcome2 = new Outcome("Name 1", 1);
        Outcome outcome3 = new Outcome("Name 2", 2);
    }
}