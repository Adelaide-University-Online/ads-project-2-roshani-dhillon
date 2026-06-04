import java.util.*;

/**
 * File: Graph.java
 * Description: A Java interface defining the blueprint of a Graph data structure.
 * Author: Roshani Dhillon
 * Student ID: a1885921
 * Email ID: a1885921
 * AI Tool Used: N
 * This is my own work as defined by the University's Academic Integrity Policy.
 **/
public interface Graph {
    /** Return the number of vertices.
     * @return The number of vertices
     */
    int getNumV();

    /** Determine whether this is a directed graph.
     * @return true if this is a directed graph
     */
    boolean isDirected();

    /** Returns a list of all vertices in the graph.
     * @return list of vertices
     */
    ArrayList<String> getVertices();

    /** Insert a new edge into the graph.
     * @param edge The new edge
     */
    void insert(Edge edge);

    /** Determine whether an edge exists.
     * @param source The source vertex
     * @param dest The destination vertex
     * @return true if there is an edge from source to dest
     */
    boolean isEdge(String source, String dest);

    /** Get the edge between two vertices.
     * @param source The source vertex
     * @param dest The destination vertex
     * @return The Edge between these two vertices or null if there is no edge
     */
    Edge getEdge(String source, String dest);

    /** Return an iterator to the edges connected to a given vertex.
     * @param source The source vertex
     * @return An Interator<Edge> to the vertices connected to source
     */
    Iterator<Edge> edgeIterator(String source);

    /** Code for the Graph interface inspired by:
     * Koffman, E. B. & Wolfgang, P. A. T. (2016). Data structures: Abstraction and design using Java (3rd ed.). Wiley.
     */
}
