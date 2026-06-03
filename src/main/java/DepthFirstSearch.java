import java.util.*;

public class DepthFirstSearch {
    private Graph graph;
    private Map<String, Boolean> visited;
    private String[] discoveryOrder;
    private String[] finishOrder;
    private int discoverIndex = 0;
    private int finishIndex = 0;

    public DepthFirstSearch(MapGraph graph) {
        this.graph = graph;
        int n = graph.getNumV();
        discoveryOrder = new String[n];
        finishOrder = new String[n];
        visited = new HashMap<>();

        for (String vertex : graph.getVertices()) {
            visited.put(vertex, false);
        }

        for (String vertex : visited.keySet()) {
            if (!visited.get(vertex)) {
                depthFirstSearch(vertex);
            }
        }
    }

    /** Performs a depth-first search of a map graph starting from a given vertex.
     * @param current The index of the current start vertex from a list of vertices.
     */
    private void depthFirstSearch(String current) {
        visited.put(current, true);
        discoveryOrder[discoverIndex++] = current;

        Iterator<Edge> itr = graph.edgeIterator(current);
        while (itr.hasNext()) {
            String neighbour = itr.next().getDest();
            if (!visited.get(neighbour)) {
                depthFirstSearch(neighbour);
            }
        }
        finishOrder[finishIndex++] = current;
    }

    /** Returns the order in which the vertices were completed (the direct result of the depth-first search).
     * @return the finish order
     */
    public String[] getFinishOrder() {
        return finishOrder;
    }

    /** Returns the order in which the vertices were discovered (before they were completed).
     * @return the discovery order
     */
    public String[] getDiscoveryOrder() {
        return discoveryOrder;
    }

    /** Code for the Graph interface inspired by:
     * Koffman, E. B. & Wolfgang, P. A. T. (2016). Data structures: Abstraction and design using Java (3rd ed.). Wiley.
     */
}
