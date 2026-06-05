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

    public static int getNumberOfCourses() {
        String numCoursesStr = JOptionPane.showInputDialog("Enter the number of courses you can study concurrently");
        try {
            int numCoursesInt = Integer.parseInt(numCoursesStr);
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

    public static List<List<String>> orderCourses(MapGraph graph, Queue<String> queue, int maxPerTerm,
                                                  List<String> completed, List<List<String>> result) {
        int checked = 0;  //Track how many courses are checked each rotation.
        int prevChecked = -1;  //Store how many courses were checked last rotation.

        while (checked != prevChecked && !queue.isEmpty()) {
            prevChecked = checked;
            checked = 0;

            for (int i = 0; i < queue.size(); i++) {
                String vertex = queue.poll();
                List<String> prerequisites = graph.getPrerequisites(vertex);

                if (prerequisites.isEmpty() || completed.containsAll(prerequisites)) {
                    if (result.getLast().size() < maxPerTerm) {
                        result.getLast().add(vertex);
                    } else {
                        result.add(new ArrayList<>(List.of(vertex)));
                    }

                    if (result.getLast().size() == maxPerTerm) {
                        completed.addAll(result.getLast());
                    }
                } else {
                    queue.add(vertex);
                }
                checked++;
            }
        }

        //While loop has ended meaning the queue is empty or all vertices have been checked.
        if (!queue.isEmpty()) {
            completed.addAll(result.getLast());
            int nullsToAdd = maxPerTerm - result.getLast().size();
            for (int i = 0; i < nullsToAdd; i++) {
                result.getLast().add(null);

            }
            return orderCourses(graph, queue, maxPerTerm, completed, result);
        }
        return result;
    }
}

