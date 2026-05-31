import java.util.*;

public class MapGraph extends AbstractGraph {
    private Map<String, List<Edge>> edges;

    /** Contructs a map graph with the specified vertices and directionality.
     *
     * @param numV The number of vertices
     * @param vertices A list of all the vertices in the map
     * @param directed The directionality flag
     */
    public MapGraph(int numV, String[] vertices, boolean directed) {
        super(numV, vertices, directed);
        edges = new HashMap<>();
        for (String vertex : vertices) {
            edges.put(vertex, new ArrayList<Edge>());
        }
    }

    /** Determines whether an edge exists.
     *
     * @param source The source vertex
     * @param dest The destination vertex
     * @return true if there is an edge from source to dest
     */
    public boolean isEdge(String source, String dest) {
        try {
            return edges.get(source).contains(new Edge (source, dest));
        }
        catch (NullPointerException e) {  // Throws exception if source is not in the map.
            return false;
        }
    }

    /** Inserts an Edge into the map graph.
     * @param edge The new edge to be inserted
     */
    public void insert(Edge edge) {
        String source = edge.getSource();
        String dest = edge.getDest();

        if (!edges.containsKey(source)) {   //Add new key-value pair if source is not yet a vertex.
            edges.put(source, new ArrayList<>());
        }
        edges.get(source).add(edge);

        if (!isDirected()) {   //Create edge in reverse direction if the map is not directed.
            Edge reverseEdge = new Edge(dest, source);
            edges.get(source).add(reverseEdge);
        }
    }

    /** Creates an iterator object for the list of edges of a source vertex
     * @param source The source vertex
     * @return Iterator object, or null if the source vertex is not in the map
     */
    public Iterator<Edge> edgeIterator(String source) {
        try {
            return edges.get(source).iterator();
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    /** Returns the Edge object between a given source vertex and destination vertex, if it exists in the map.
     * @param source The source vertex
     * @param dest The destination vertex
     * @return The existing Edge object, or null if the edge does not exist
     */
    public Edge getEdge(String source, String dest) {
        try {
            List<Edge> edgeList = edges.get(source);
            int index = edgeList.indexOf(new Edge(source, dest));
            if (index < 0) {
                return null;
            }
            return edgeList.get(index);
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    /** Compares two objects for equality.
     * @param obj   the reference object with which to compare.
     * @return true if the map graphs are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MapGraph) {
            MapGraph other = (MapGraph)obj;
            return numV == other.getNumV() && directed == other.isDirected() &&
                    Objects.equals(edges, other.edges);
        }
        return false;
    }

    /** Returns the hash code for the map graph.
     * The hash code depends on the number of vertices in the graph, the directionality, and the map of edges.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(numV, directed, edges);
    }

    /** A string representation of the map graph, showing all source vertices next to all their destination vertices.
     * @return A string of all source and destination vertices
     */
    @Override
    public String toString() {
        String result = "SOURCE\t\t\t\tDESTINATIONS\n";

        for (String source : edges.keySet()) {
            result += source + "\t\t\t\t" + edges.get(source);
        }
        return result;
    }

    /** Code for the Graph interface inspired by:
     * Koffman, E. B. & Wolfgang, P. A. T. (2016). Data structures: Abstraction and design using Java (3rd ed.). Wiley.
     */
}

