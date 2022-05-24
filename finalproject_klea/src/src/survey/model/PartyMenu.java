package src.survey.model;
/**
 * Final Project Evite Code
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public enum PartyMenu { //party menu enum for different types of parties
    //working on adding more party types
    GIFT_PARTY("Gift Party"),
    FOOD_PARTY("Food Party"),

    ;

    private String description;

    PartyMenu(String description) {
        this.description = description;
    }

    public static PartyMenu matchParty(String m) {
        PartyMenu menu = null;
        m = m.replace(" ", "_").replace("\"", "");

        for (PartyMenu x : PartyMenu.values()) {
            if (x.toString().equalsIgnoreCase(m)) {
                menu = x;
            }
        }
        if (menu == null) {
            System.err.println("Couldn't match string genre " + m + " with existing constants");
        }
        return menu;
    }

    public static int getNumOptions() {
        return PartyMenu.values().length;
    }

    public static PartyMenu getOption(int num) {
        return PartyMenu.values()[num - 1];
    }

    public static String getMenuOptions() { //gets the option printed
        String prompt = "*****\t Party System Menu\t*****";

        for (PartyMenu m : PartyMenu.values()) { //array from the enum
            prompt += "\n" + (m.ordinal() + 1) + ": " + m.getDisplayString();
        }
        prompt += "\n**********************************************\n";
        return prompt;
    }

    public static void printMenuOptions() {
        String prompt = getMenuOptions();
        System.out.println(prompt);
    }

    public static Collection<PartyMenu> matchPartyMenuCSVList(String gList) { //returns collection of options for partymenu
        //gList may be ONE or more items (comma separated list)
        Collection<PartyMenu> partyList = new ArrayList<>(); //start with empty list
        // turn string into list
        Scanner gS = new Scanner(gList);
        gS.useDelimiter(",");
        while (gS.hasNext()) {
            String next = gS.next(); // grabs a genre as a string
            PartyMenu g = PartyMenu.matchParty(next.strip());
            partyList.add(g); //TODO decide if UNKNOWN should go in collection or not
        }
        return partyList;
    }

    public String getDisplayString() {
        return this.description;
    }
}
