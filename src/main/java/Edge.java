import java.util.*;

public class Edge {
    private String dest;
    private String source;

    public Edge(String source, String dest) {
        this.source = source;
        this.dest = dest;
    }

    /** Get the destination vertex of the edge.
     * @return The destination vertex
     */
    public String getDest() {
        return dest;
    }

    /** Get the source vertex of the edge.
     * @return The source vertex
     */
    public String getSource() {
        return source;
    }

    /** Compare two edges for equality.
     *  Edges are equal if their source and destination values are the same.
     * @param obj   the reference object with which to compare.
     * @return true if the edges are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            return Objects.equals(source, ((Edge)obj).getSource()) &&
                    Objects.equals(dest, ((Edge)obj).getDest());
        }
        return false;
    }

    /** Returns the hash code for the edge.
     * The hash code depends on the source and destination.
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, dest);
    }

    @Override
    public String toString() {
        return "Source: " + source + "\nDestination: " + dest;
    }

    /** Code for the Edge class inspired by:
     * Koffman, E. B. & Wolfgang, P. A. T. (2016). Data structures: Abstraction and design using Java (3rd ed.). Wiley.
     */
}
