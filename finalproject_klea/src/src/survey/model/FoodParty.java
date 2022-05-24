package src.survey.model;
/**
 * Final Project Evite Code
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/

import java.time.LocalDate;
import java.util.ArrayList;

public class FoodParty extends Party { //party subclass

    private String Food;


    public FoodParty(String partyName, LocalDate partyDate, Host host, ArrayList<String> guestList, String food) {
        super(partyName, partyDate, host, guestList);
        this.Food = food;//different variable
    }

    public String getFood() {
        return this.Food;
    }

    public void setFood(String food) {
        this.Food = food;
    }

    public String getClassString() {
        return "FoodParty";
    }

    @Override
    public String toString() {
        return this.getHost().getName() + "'s Food Party with " + getFood();
    }

    public String toFileString() { //gets to file string
        String str = this.getClassString() + "," + this.getPartyName() + "," + this.getPartyDate() + "," + this.getHost().getEmail() +
                "," + this.getHost().getName() + "," + this.getHost().getPassword() + "," + this.getHost().isMember();
        for (String guest : this.getGuestList()) {
            str += "," + guest;

        }
        return (str);
    }

}
