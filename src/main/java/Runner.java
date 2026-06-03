import javax.swing.*;
import java.util.*;

/**
* File: filename.java
* Description: A brief description of this Java module.
* Author: Steve Jobs
* Student ID: 12345678
* Email ID: jobst007
* AI Tool Used: Y/N (This includes all AI Tools e.g. ChatGPT, Microsoft or Github Copiliot etc... Please leave blank if you do not wish to share this information)
* This is my own work as defined by
*    the University's Academic Integrity Policy.
**/
public class Runner {
    
    public static void main(String[] args) {
        System.out.println("Welcome to ADS Assignment Starter!");
        System.out.println("This is a basic Java project template.");
        System.out.println("You can modify this file to implement your assignment requirements.");

        String fileName = JOptionPane.showInputDialog("Enter the file name");
        String filePath = "src/main/text/" + fileName;

        MapGraph prerequisiteGraph = (MapGraph)AbstractGraph.createGraph(filePath, true);
        int choice = 0;

        while (prerequisiteGraph == null) {
            choice = JOptionPane.showOptionDialog(null, "File not found", "File not found",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new String[] {"Re-enter file name", "Quit"}, "Re-enter file name");
            if (choice == 0) {
                fileName = JOptionPane.showInputDialog("Enter the file name");
                filePath = "src/main/text/" + fileName;
                prerequisiteGraph = (MapGraph)AbstractGraph.createGraph(filePath, true);
            } else {
                break;
            }
        }

        if (choice != 1) {  //If user did not choose to quit.
            int numCourses = getNumberOfCourses();

            //Do a depth-first search.
            DepthFirstSearch dfs = new DepthFirstSearch(prerequisiteGraph);
            String[] finishOrder = dfs.getFinishOrder();

            //Reverse DFS into a queue (topological sort).
            Queue<String> queue = new ArrayDeque<>();
            for (int i = finishOrder.length - 1; i > -1; i--) {
                queue.add(finishOrder[i]);
            }

            //Get order in which to study courses.
            List<List<String>> finalResult = orderCourses(prerequisiteGraph, queue, numCourses,
                    new ArrayList<>(), new ArrayList<>(List.of(new ArrayList<>())));

            //Print results.
            System.out.println(finalResult);
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
                List<String> prerequisites = new ArrayList<>();

                //Get all prerequisites for the vertex.
                for (String source : graph.getVertices()) {
                    Iterator<Edge> itr = graph.edgeIterator(source);
                    while (itr.hasNext()) {
                        if(Objects.equals(itr.next().getDest(), vertex)) {
                            prerequisites.add(source);
                        }
                    }
                }

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
            for (int i = 0; i < maxPerTerm - result.getLast().size(); i++) {
                result.getLast().add(null);
            }
            return orderCourses(graph, queue, maxPerTerm, completed, result);
        }
        return result;
    }
}

