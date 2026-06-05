import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepthFirstSearchTest {

    @Test
    void getFinishOrder() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        DepthFirstSearch dfs = new DepthFirstSearch(graph);
        assertArrayEquals(new String[]{"G", "E", "C", "I", "F", "H", "D", "A", "B"}, dfs.getFinishOrder());
    }

    @Test
    void getDiscoveryOrder() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        DepthFirstSearch dfs = new DepthFirstSearch(graph);
        assertArrayEquals(new String[]{"A", "C", "E", "G", "D", "F", "I", "H", "B"}, dfs.getDiscoveryOrder());
    }

    @Test
    void testEquals() {
        MapGraph graph1 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        DepthFirstSearch dfs1 = new DepthFirstSearch(graph1);

        MapGraph graph2 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        DepthFirstSearch dfs2 = new DepthFirstSearch(graph2);

        assertEquals(dfs1, dfs1);
        assertEquals(dfs1, dfs2);

        //Add new vertex to graph2 and redo DFS.
        graph2.insert(new Edge("I", "J"));
        dfs2 = new DepthFirstSearch(graph2);
        assertNotEquals(dfs1, dfs2);
    }

    @Test
    void testHashCode() {
        MapGraph graph1 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        DepthFirstSearch dfs1 = new DepthFirstSearch(graph1);

        MapGraph graph2 = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        DepthFirstSearch dfs2 = new DepthFirstSearch(graph2);

        assertEquals(dfs1.hashCode(), dfs1.hashCode());
        assertEquals(dfs1.hashCode(), dfs2.hashCode());

        graph2.insert(new Edge("I", "J"));
        dfs2 = new DepthFirstSearch(graph2);
        assertNotEquals(dfs1.hashCode(), dfs2.hashCode());
    }

    @Test
    void testToString() {
        MapGraph graph = (MapGraph)AbstractGraph.createGraph("src/main/text/test.txt", true);
        DepthFirstSearch dfs = new DepthFirstSearch(graph);
        String result = graph.toString() + "---------Depth-First Search---------\n" +
                "Finish Order: G, E, C, I, F, H, D, A, B\nDiscovery Order: A, C, E, G, D, F, I, H, B";
    }
}