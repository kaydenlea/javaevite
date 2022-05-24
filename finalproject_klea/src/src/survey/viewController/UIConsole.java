package src.survey.viewController;

import java.util.ArrayList;

/**
 * UI is a utility class for ITP 265 that helps provide a friendly way to read input from
 * a user and verify that the input is correct.
 *
 * @author Kendra Walther
 * Email: kwalther@usc.edu
 */

public class UIConsole extends Helper implements UI {


    /**
     * Short-cut helper method for print
     *
     * @param output: The Object to be printed
     */
    public void print(Object o) {
        System.out.println(o.toString());
    }

    /**
     * Short-cut helper method that prints a String with a series of stars around it.
     *
     * @param output: The String to be printed
     */
    public void printPretty(String output) {
        System.out.println("***********************************************************************************************");
        System.out.println(output);
        System.out.println("***********************************************************************************************");
    }

    /**
     * Short-cut helper method that prints a List with a series of stars around it.
     *
     * @param output: The List to be printed
     */
    public void printPretty(ArrayList list) {
        System.out.println("***********************************************************************************************");
        for (Object o : list) {
            System.out.println(o);
        }
        System.out.println("***********************************************************************************************");
    }


}