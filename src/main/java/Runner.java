import javax.swing.*;
import java.util.*;

/**
 * File: Runner.java
 * Description: A Java module running the code for the OptiTime tool which identifies the optimal order in which to
 * study courses in a particular degree.
 * Author: Roshani Dhillon
 * Student ID: a1885921
 * Email ID: a1885921
 * AI Tool Used: N
 * This is my own work as defined by the University's Academic Integrity Policy.
 **/
public class Runner {

    public static void main(String[] args) {
        //Get file name and location (in text folder).
        String fileName = JOptionPane.showInputDialog("Enter the file name");
        String filePath = "src/main/text/" + fileName;

        //Create map graph using file path.
        MapGraph prerequisiteGraph = (MapGraph)AbstractGraph.createGraph(filePath, true);

        //Initialise choice variable.
        int choice = 0;

        //If initial file does not exist, keep asking user for the file name until it is valid
        //or the user decides to quit.
        while (prerequisiteGraph == null) {
            choice = JOptionPane.showOptionDialog(null, "File not found", "File not found",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new String[] {"Re-enter file name", "Quit"}, "Re-enter file name");
            if (choice == 0) {  //Choice is to reenter file name.
                fileName = JOptionPane.showInputDialog("Enter the file name");
                filePath = "src/main/text/" + fileName;
                prerequisiteGraph = (MapGraph)AbstractGraph.createGraph(filePath, true);
            } else {  //Choice is to quit.
                break;
            }
        }

        if (choice != 1) {  //If user did not choose to quit.
            //Get the number of courses the user can study concurrently each term.
            int numCourses = getNumberOfCourses();

            //Do a depth-first search of the graph.
            DepthFirstSearch dfs = new DepthFirstSearch(prerequisiteGraph);
            String[] finishOrder = dfs.getFinishOrder();

            //Reverse DFS into a queue (topological sort).
            //This will put the courses in the estimated correct order of study.
            Queue<String> queue = new ArrayDeque<>();
            for (int i = finishOrder.length - 1; i > -1; i--) {
                queue.add(finishOrder[i]);
            }

            //Get order in which to study courses.
            List<List<String>> finalResult = orderCourses(prerequisiteGraph, queue, numCourses,
                    new ArrayList<>(), new ArrayList<>(List.of(new ArrayList<>())));

            //Print results.
            int year = 0;
            int term = 5;
            for (List<String> termCourses : finalResult) {
                if (term == 5) {
                    System.out.println("-------------- YEAR " + ++year + " --------------");
                    term = 1;
                }
                termCourses.removeIf(item -> Objects.equals(item, null));
                System.out.print("Term " + term++ + ": " +
                        termCourses.toString().substring(1, termCourses.toString().length()-1) + "\n");
            }
        }

        //Quit program.
    }

    /** Prompts the user to enter the number of courses they can study concurrently each study period.
     * @return the number of courses the user can study concurrently
     */
    public static int getNumberOfCourses() {
        String numCoursesStr = JOptionPane.showInputDialog("Enter the number of courses you can study concurrently");
        try {
            int numCoursesInt = Integer.parseInt(numCoursesStr);  //Will throw exception if user didn't enter a number.
            if (numCoursesInt < 1) {
                throw new NumberFormatException();
            }
            return numCoursesInt;
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid positive number");
            return getNumberOfCourses();
        }
    }

    /** Organises a graph of courses into study periods depending on their prerequisites and the number of courses
     * able to be studied concurrently.
     * @param graph the MapGraph object containing all the course/prerequisite data
     * @param queue a queue for the order in which to place the courses
     * @param maxPerTerm the maximum number of courses that can be studied concurrently per study period
     * @param completed a list of courses that have been studied previously
     * @param result the list of lists in which to put the organised courses
     * @return a list of lists containing the organised courses
     */
    public static List<List<String>> orderCourses(MapGraph graph, Queue<String> queue, int maxPerTerm,
                                                  List<String> completed, List<List<String>> result) {
        int checked = 0;  //Track how many courses are checked each rotation.
        int prevChecked = -1;  //Store how many courses were checked last rotation.

        while (checked != prevChecked && !queue.isEmpty()) { //"checked != prevChecked" to avoid an infinite loop.
            //Reset variables.
            prevChecked = checked;
            checked = 0;

            //Repeat for the number of courses in the queue at the start of the rotation.
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                //Store the next vertex from the queue.
                String vertex = queue.poll();

                //Get the vertex's prerequisites.
                List<String> prerequisites = graph.getPrerequisites(vertex);

                //If the course doesn't have prerequisites or all prerequisites were completed in a previous study
                //period, add the course to the next available study period.
                if (prerequisites.isEmpty() || completed.containsAll(prerequisites)) {
                    if (result.getLast().size() < maxPerTerm) {
                        result.getLast().add(vertex);
                    } else {
                        result.add(new ArrayList<>(List.of(vertex)));
                    }

                    //If the most recent term is filled up, mark all courses in that term as completed.
                    if (result.getLast().size() == maxPerTerm) {
                        completed.addAll(result.getLast());
                    }
                } else {  //Else, if the course cannot yet be studied, add back into the queue for later action.
                    queue.add(vertex);
                }
                //Course has been checked, so increase checked by 1.
                checked++;
            }
        }

        //While loop has ended, meaning the queue is empty or all vertices have been checked.
        if (!queue.isEmpty()) {
            //There are still courses in the queue that need to be studied.
            //The most recent term has not been filled up, but none of these courses can be studied in that term because
            //one of their prerequisites is being studied in that term.
            //So, the term need to be marked as completed.
            completed.addAll(result.getLast());
            int nullsToAdd = maxPerTerm - result.getLast().size();
            for (int i = 0; i < nullsToAdd; i++) {
                result.getLast().add(null);

            }

            //Recall the method to deal with all courses left in the queue.
            return orderCourses(graph, queue, maxPerTerm, completed, result);
        }

        //There is nothing left in the queue, so the result can be returned.
        return result;
    }
}

