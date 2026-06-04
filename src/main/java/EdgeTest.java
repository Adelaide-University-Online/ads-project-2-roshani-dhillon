import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * File: EdgeTest.java
 * Description: A Java test module for the Edge class.
 * Author: Roshani Dhillon
 * Student ID: a1885921
 * Email ID: a1885921
 * AI Tool Used: N
 * This is my own work as defined by the University's Academic Integrity Policy.
 **/
class EdgeTest {

    @Test
    void getDest() {
        Edge edge = new Edge("A", "B");
        assertEquals("B", edge.getDest());
    }

    @Test
    void getSource() {
        Edge edge = new Edge("A", "B");
        assertEquals("A", edge.getSource());
    }

    @Test
    void testEquals() {
        Edge edge1 = new Edge("A", "B");
        Edge edge2 = new Edge("B", "A");
        Edge edge3 = new Edge("A", "B");

        assertNotEquals(edge1, edge2);
        assertEquals(edge1, edge3);
    }

    @Test
    void testHashCode() {
        Edge edge1 = new Edge("A", "B");
        Edge edge2 = new Edge("B", "A");
        Edge edge3 = new Edge("A", "B");

        assertNotEquals(edge1.hashCode(), edge2.hashCode());
        assertEquals(edge1.hashCode(), edge3.hashCode());
    }

    @Test
    void testToString() {
        Edge edge = new Edge("A", "B");
        assertEquals("Source: A\nDestination: B", edge.toString());
    }
}