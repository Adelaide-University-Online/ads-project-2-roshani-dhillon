import java.util.*;
import java.io.*;

public abstract class AbstractGraph implements Graph {
    protected int numV;
    protected boolean directed;
    protected String[] vertices;

    public AbstractGraph(int numV, String[] vertices, boolean directed) {
        this.numV = numV;
        this.directed = directed;
        this.vertices = vertices;
    }

    /** Returns the number of vertices in the graph.
     * @return number of vertices
     */
    public int getNumV() {
        return numV;
    }

    /** Determine if the graph is directed.
     * @return true if the graph is directed
     */
    public boolean isDirected() {
        return directed;
    }

    /** Returns a list of all vertices in the graph.
     * @return list of vertices
     */
    public String[] getVertices() {
        return vertices;
    }

    /** Loads the edges of a graph from the data in an input file.
     * The file should be formatted with the vertex followed by its adjacent vertices (eg V5, V4, V2 - indicates that
     * V4 and V2 are both adjacent to V5).
     * If the graph is directed, an additional edge will be created to ensure two directional edges (eg V5 will be
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
    public static Graph createGraph(String fileName, boolean isDirected) {
        try {
            //Get the first line of the file (the complete list of vertices).
            Scanner scan = new Scanner(new File(fileName));
            String[] vertices = scan.nextLine().split(", ");

            //Create a new MapGraph object with the information in the file.
            AbstractGraph returnValue = new MapGraph(vertices.length, vertices, isDirected);
            returnValue.loadEdgesFromFile(scan);
            return returnValue;
        }
        catch (FileNotFoundException e) {
            return null;
        }
    }

    /** Code for the Edge class inspired by:
     * Koffman, E. B. & Wolfgang, P. A. T. (2016). Data structures: Abstraction and design using Java (3rd ed.). Wiley.
     */
}
