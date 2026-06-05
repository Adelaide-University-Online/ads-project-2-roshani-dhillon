import java.util.*;
import java.io.*;

/**
 * File: AbstractGraph.java
 * Description: A Java module (abstract class) representing a general Graph data structure.
 * Author: Roshani Dhillon
 * Student ID: a1885921
 * Email ID: a1885921
 * AI Tool Used: N
 * This is my own work as defined by the University's Academic Integrity Policy.
 **/
public abstract class AbstractGraph implements Graph {
    protected boolean directed;
    protected ArrayList<String> vertices;

    public AbstractGraph(ArrayList<String> vertices, boolean directed) {
        this.directed = directed;
        this.vertices = vertices;
    }

    public int getNumV() {
        return vertices.size();
    }

    public boolean isDirected() {
        return directed;
    }

    public ArrayList<String> getVertices() {
        return vertices;
    }

    /** Loads the edges of a graph from the data in an input file.
     * The file should be formatted with the vertex followed by its adjacent vertices (eg V5, V4, V2 - indicates that
     * V4 and V2 are both adjacent to V5).
     * If the graph is undirected, an additional edge will be created to ensure two directional edges (eg V5 will be
     * adjacent to V4 and V2).
     * @param scan The Scanner connected to the data file
     */
    protected void loadEdgesFromFile(Scanner scan) {
        while (scan.hasNextLine()) {
            String[] components = scan.nextLine().split(", ");
            for (int i = 1; i < components.length; i++) {
                insert(new Edge(components[i], components[0]));
                if (!directed) {
                    insert(new Edge(components[0], components[i]));
                }
            }
        }
    }

    /** Creates a graph and loads the data from an input file. The first line of the input file should contain a list
     * of all the vertices (eg V1, V2, V3, V4, V5).
     * The remaining lines should contain the edge data as described under loadEdgesFromFile.
     * @param fileName The name of the file to use for the graph
     * @param isDirected true if this is a directed graph, false otherwise
     */
    protected static Graph createGraph(String fileName, boolean isDirected) {
        try {
            //Get the first line of the file (the complete list of vertices).
            Scanner scan = new Scanner(new File(fileName));
            ArrayList<String> vertices = new ArrayList<>(Arrays.asList(scan.nextLine().split(", ")));

            //Create a new MapGraph object with the information in the file.
            AbstractGraph returnValue = new MapGraph(vertices, isDirected);
            returnValue.loadEdgesFromFile(scan);
            return returnValue;
        }
        catch (FileNotFoundException e) {
            return null;
        }
    }

    /** Compares two objects for equality.
     * @param obj   the reference object with which to compare.
     * @return true if the graphs are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractGraph) {
            AbstractGraph other = (AbstractGraph)obj;
            return directed == other.isDirected() && Objects.equals(vertices, other.getVertices());
        }
        return false;
    }

    /** Returns the hash code for the graph.
     * The hash code depends on the vertices in the graph and the directionality.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(directed, vertices);
    }

    @Override
    public String toString() {
        String result = "Directed: " + directed + "\nVertices: ";
        for (String vertex : vertices) {
            result += vertex + ", ";
        }
        return result.substring(0, result.length() - 2);
    }

    /** Code for the AbstractGraph class inspired by:
     * Koffman, E. B. & Wolfgang, P. A. T. (2016). Data structures: Abstraction and design using Java (3rd ed.). Wiley.
     */
}
