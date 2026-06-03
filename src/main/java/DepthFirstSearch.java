import java.util.*;

public class DepthFirstSearch {
    private Graph graph;
    private boolean[] visited;
    private String[] discoveryOrder;
    private String[] finishOrder;
    private int discoverIndex = 0;
    private int finishIndex = 0;
    private String[] vertices;

    public DepthFirstSearch(MapGraph graph) {
        this.graph = graph;
        int n = graph.getNumV();
        visited = new boolean[n];
        discoveryOrder = new String[n];
        finishOrder = new String[n];
        vertices = graph.getVertices();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                depthFirstSearch(i);
            }
        }
    }

    /** Performs a depth-first search of a map graph starting from a given vertex.
     * @param current The index of the current start vertex from a list of vertices.
     */
    private void depthFirstSearch(int current) {
        visited[current] = true;
        discoveryOrder[discoverIndex++] = vertices[current];

        Iterator<Edge> itr = graph.edgeIterator(vertices[current]);
        while (itr.hasNext()) {
            String neighbour = itr.next().getDest();
            int neighbourIndex = Arrays.asList(vertices).indexOf(neighbour);
            if (!visited[neighbourIndex]) {
                depthFirstSearch(neighbourIndex);
            }
        }
        finishOrder[finishIndex++] = vertices[current];
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
