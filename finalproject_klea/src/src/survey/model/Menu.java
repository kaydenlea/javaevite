/**
 * Final Project Evite Code
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/

package src.survey.model;

public enum Menu {//main menu with the different options
    LOGIN("Log in or Create Account"),
    LOGOUT("Log out"),
    CREATE_PARTY("Create a party"),
    MEMBER_SIGNUP("Become a member"),
    VIEW_PARTIES("Show parties you created"),
    RSVP("RSVP to a party"),
    RSVP_STATUS("Check guest's RSVP status"),
    END("End Program"),
    ;

    private String description;

    private Menu(String description) {
        this.description = description;
    }

    public static Menu matchMenu(String m) {
        Menu menu = null;
        m = m.replace("-", "_").replace("\"", "");

        for (Menu x : Menu.values()) {
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
        return Menu.values().length;
    }

    public static Menu getOption(int num) {
        return Menu.values()[num - 1];
    }

    public static String getMenuOptions() {
        String prompt = "*****\t Evite System Menu\t*****";

        for (Menu m : Menu.values()) { //array from the enum
            prompt += "\n" + (m.ordinal() + 1) + ": " + m.getDisplayString();
        }
        prompt += "\n**********************************************\n";
        return prompt;
    }

    public static void printMenuOptions() {
        String prompt = getMenuOptions();
        System.out.println(prompt);
    }

    public String getDisplayString() {
        return this.description;
    }
}
