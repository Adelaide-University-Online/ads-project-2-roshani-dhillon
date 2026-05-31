import javax.swing.*;
import java.io.File;

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
                prerequisiteGraph = (MapGraph)AbstractGraph.createGraph(fileName, true);
            } else {
                break;
            }
        }

        if (choice != 1) {
            int numCourses = getNumberOfCourses();

            System.out.println(prerequisiteGraph);

            //Do analysis.
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

}
