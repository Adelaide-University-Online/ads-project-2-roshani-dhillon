import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class MapGraphTest {

    @Test
    void getNumV() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        assertEquals(9, graph.getNumV());

        //Add new edge with one new vertex.
        graph.insert(new Edge("B", "J"));
        assertEquals(10, graph.getNumV());

        //Add new edge with two new vertices.
        graph.insert(new Edge("X", "Y"));
        assertEquals(12, graph.getNumV());
    }

    @Test
    void isDirected() {
        MapGraph graph1 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        MapGraph graph2 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", false);

        assertTrue(graph1.isDirected());
        assertFalse(graph2.isDirected());
    }

    @Test
    void getVertices() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        List<String> vertices = new ArrayList<>(List.of("A", "B", "C", "D", "E", "F", "G", "H", "I"));
        assertEquals(vertices, graph.getVertices());

        //Add one new vertex "J".
        graph.insert(new Edge("B", "J"));
        vertices.add("J");
        assertEquals(vertices, graph.getVertices());
    }

    @Test
    void isEdge() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        assertTrue(graph.isEdge("C", "E"));
        assertFalse(graph.isEdge("I", "A"));
    }

    @Test
    void insert() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        List<String> vertices = new ArrayList<>(List.of("A", "B", "C", "D", "E", "F", "G", "H", "I"));

        //Insert using existing vertices.
        graph.insert(new Edge("B", "D"));
        assertEquals(vertices, graph.getVertices());

        //Insert one new vertex.
        graph.insert(new Edge("I", "J"));
        vertices.add("J");
        assertEquals(vertices, graph.getVertices());

        //Insert two new vertices.
        graph.insert(new Edge("X", "Y"));
        vertices.add("X");
        vertices.add("Y");
        assertEquals(vertices, graph.getVertices());
    }

    @Test
    void edgeIterator() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        List<Edge> expected = new ArrayList<>(List.of(new Edge("A", "C"), new Edge("A", "D")));
        Iterator<Edge> itr = graph.edgeIterator("A");

        assertEquals(expected.get(0), itr.next());
        assertEquals(expected.get(1), itr.next());
        assertFalse(itr.hasNext());
    }

    @Test
    void getEdge() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        assertEquals(new Edge("H", "F"), graph.getEdge("H", "F"));
        assertEquals(null, graph.getEdge("B", "I"));
    }

    @Test
    void getPrerequisites() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        List<String> prereqOfD = new ArrayList<>(List.of("A"));

        assertEquals(prereqOfD, graph.getPrerequisites("D"));

        //Add new prerequisite for D.
        graph.insert(new Edge("B", "D"));
        prereqOfD.add("B");
        assertEquals(prereqOfD, graph.getPrerequisites("D"));

        //Test course with no prerequisites.
        assertEquals(new ArrayList<>(), graph.getPrerequisites("A"));
    }

    @Test
    void testEquals() {
        MapGraph graph1 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        MapGraph graph2 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        MapGraph graph3 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        graph3.insert(new Edge("B", "D"));

        assertEquals(graph1, graph1);
        assertEquals(graph1, graph2);
        assertNotEquals(graph1, graph3);
    }

    @Test
    void testHashCode() {
        MapGraph graph1 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        MapGraph graph2 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        MapGraph graph3 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        graph3.insert(new Edge("B", "D"));

        assertEquals(graph1.hashCode(), graph1.hashCode());
        assertEquals(graph1.hashCode(), graph2.hashCode());
        assertNotEquals(graph1.hashCode(), graph3.hashCode());
    }

    @Test
    void testToString() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        String result = "COURSE  <-  PREREQUISITES\n------------------------\n" +
                "A\nB\nC  <-  A\nD  <-  A\nE  <-  C\nF  <-  D, H\nG  <-  E, F\nH  <-  D\nI  <-  F\n";
        assertEquals(result, graph.toString());
    }
}