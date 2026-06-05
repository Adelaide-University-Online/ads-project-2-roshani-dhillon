import java.util.*;

/**
 * File: DepthFirstSearch.java
 * Description: A Java module designed to conduct a depth-first search on a map graph.
 * Author: Roshani Dhillon
 * Student ID: a1885921
 * Email ID: a1885921
 * AI Tool Used: N
 * This is my own work as defined by the University's Academic Integrity Policy.
 **/
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

        //Populate visited map with all vertices set to false.
        for (String vertex : graph.getVertices()) {
            visited.put(vertex, false);
        }

        //Conduct DFS for all vertices (to ensure any disconnected vertices are also visited).
        for (String vertex : visited.keySet()) {
            if (!visited.get(vertex)) {
                depthFirstSearch(vertex);
            }
        }
    }

    /** Performs a depth-first search of a map graph starting from a given vertex.
     * @param current The index of the current start vertex from a list of vertices
     */
    private void depthFirstSearch(String current) {
        //Mark current vertex as visited.
        visited.put(current, true);

        //Add current vertex to discovery order.
        discoveryOrder[discoverIndex++] = current;

        //Create Iterator for vertex's edges (to get adjacent vertices).
        Iterator<Edge> itr = graph.edgeIterator(current);

        //Conduct DFS on any/all adjacent vertices if not yet visited.
        while (itr.hasNext()) {
            String neighbour = itr.next().getDest();
            if (!visited.get(neighbour)) {
                depthFirstSearch(neighbour);
            }
        }

        //DFS for vertex (and any adjacent vertices) is done. Mark as finished.
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

    /** Compares two objects to determine equality.
     * @param obj   the reference object with which to compare.
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DepthFirstSearch) {
            DepthFirstSearch other = (DepthFirstSearch)obj;
            return Objects.equals(graph, other.graph) && Arrays.equals(discoveryOrder, other.discoveryOrder) &&
                    Arrays.equals(finishOrder, other.finishOrder);
        }
        return false;
    }

    /** Returns the hash code of the object.
     * The hash code depends on the graph, discovery order, and finish order.
     * @return the hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(graph, Arrays.hashCode(discoveryOrder), Arrays.hashCode(finishOrder));
    }

    /** A string representation of the depth-first search object.
     * @return a String representation of the object, showing the graph structure, finish order, and discovery order.
     */
    @Override
    public String toString() {
        return graph.toString() + "---------Depth-First Search---------\n" + "Finish Order: " +
                finishOrder.toString().substring(1, finishOrder.toString().length()-1) + "\nDiscovery Order: " +
                discoveryOrder.toString().substring(1, discoveryOrder.toString().length()-1);
    }

    /** Code for the DepthFirstSearch interface inspired by:
     * Koffman, E. B. & Wolfgang, P. A. T. (2016). Data structures: Abstraction and design using Java (3rd ed.). Wiley.
     */
}
