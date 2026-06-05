import java.util.*;


/**
 * File: MapGraph.java
 * Description: A Java module representing a map graph data structure.
 * Author: Roshani Dhillon
 * Student ID: a1885921
 * Email ID: a1885921
 * AI Tool Used: N
 * This is my own work as defined by the University's Academic Integrity Policy.
 **/
public class MapGraph extends AbstractGraph {
    private Map<String, List<Edge>> edges;

    /** Contructs a map graph with the specified vertices and directionality.
     *
     * @param vertices A list of all the vertices in the map
     * @param directed The directionality flag
     */
    public MapGraph(ArrayList<String> vertices, boolean directed) {
        super(vertices, directed);
        edges = new HashMap<>();
        for (String vertex : vertices) {
            edges.put(vertex, new ArrayList<Edge>());
        }
    }

    public boolean isEdge(String source, String dest) {
        try {
            return edges.get(source).contains(new Edge (source, dest));
        }
        catch (NullPointerException e) {  // Throws exception if source is not in the map.
            return false;
        }
    }

    public void insert(Edge edge) {
        String source = edge.getSource();
        String dest = edge.getDest();

        if (!edges.containsKey(source)) {   //Add new key-value pair if source is not yet a vertex.
            edges.put(source, new ArrayList<>());
        }

        if (!vertices.contains(source)) {  //Add source to vertices list if not already in it.
            vertices.add(source);
        }

        if (!vertices.contains(dest)) {  //Add dest to vertices list if not already in it.
            vertices.add(dest);
        }

        edges.get(source).add(edge);

        if (!isDirected()) {   //Create edge in reverse direction if the map is not directed.
            Edge reverseEdge = new Edge(dest, source);
            if (!edges.containsKey(dest)) {
                edges.put(dest, new ArrayList<Edge>());
            }
            edges.get(dest).add(reverseEdge);
        }
    }

    public Iterator<Edge> edgeIterator(String source) {
        try {
            return edges.get(source).iterator();
        }
        catch (NullPointerException e) {
            return Collections.emptyIterator();
        }
    }

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

    /** Get all prerequisites for a particular course.
     * @param course the course
     * @return a list of all the prerequisites
     */
    public List<String> getPrerequisites(String course) {
        List<String> prerequisites = new ArrayList<>();
        for (String source : vertices) {
            Iterator<Edge> itr = edgeIterator(source);
            while (itr.hasNext()) {
                if(Objects.equals(itr.next().getDest(), course)) {
                    prerequisites.add(source);
                }
            }
        }
        return prerequisites;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MapGraph) {
            MapGraph other = (MapGraph)obj;
            return super.equals(obj) && Objects.equals(edges, other.edges);
        }
        return false;
    }

    /** Returns the hash code for the map graph.
     * The hash code depends on the vertices in the graph, the directionality, and the map of edges.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return super.hashCode() + edges.hashCode();
    }

    /** A string representation of the map graph, showing all source vertices next to all their destination vertices.
     * @return A string of all source and destination vertices
     */
    @Override
    public String toString() {
        String result = "COURSE  <-  PREREQUISITES\n------------------------\n";

        for (String course : vertices) {
            result += course;
            List<String> prerequisites = getPrerequisites(course);
            if (!prerequisites.isEmpty()) {
                result += "  <-  " + prerequisites.toString().substring(1,prerequisites.toString().length()-1) + "\n";
            } else {
                result += "\n";
            }
        }

        return result;
    }

    /** Code for the MapGraph class inspired by:
     * Koffman, E. B. & Wolfgang, P. A. T. (2016). Data structures: Abstraction and design using Java (3rd ed.). Wiley.
     */
}

