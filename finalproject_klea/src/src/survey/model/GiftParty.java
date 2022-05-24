package src.survey.model;
/**
 * Final Project Evite Code
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class GiftParty extends Party { //gift party subclass
    private String gift;

    public GiftParty(String partyName, LocalDate partyDate, Host host, ArrayList<String> guestList, String gift) {
        super(partyName, partyDate, host, guestList);
        this.gift = gift;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getGiftList() {
        return gift;
    }

    public void setGiftList(Map<Guest, String> giftList) {
        this.gift = gift;
    }

    public String getClassString() {
        return "GiftParty";
    } //gets the class as a string when needed

    @Override
    public String toString() { //override string
        return this.getHost().getName() + "'s Gift Party with " + this.getGift() + " themed gifts";
    }

    public String toFileString() {

        String str = this.getClassString() + "," + this.getPartyName() + "," + this.getPartyDate() + "," + this.getHost().getEmail() +
                "," + this.getHost().getName() + "," + this.getHost().getPassword() + "," + this.getHost().isMember() + ","
                + this.getGiftList();
        for (String guest : this.getGuestList()) {
            str += "," + guest;

        }
        return (str);
    }

}
