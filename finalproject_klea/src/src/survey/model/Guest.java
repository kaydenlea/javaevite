/**
 * Final Project Evite Code
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/
package src.survey.model;

import java.util.ArrayList;

public class Guest extends User { //main subclass

    private ArrayList<Party> partyArrayList;

    //adds in list of for parties guest is part of
    public Guest(String email, String name, String password, boolean member, ArrayList<Party> partyArrayList) {
        super(email, name, password, member);
        this.partyArrayList = partyArrayList;
    }

    public Guest(String email, String name, String password, boolean member) {
        super(email, name, password, member);

    }

    public ArrayList<Party> getPartyArrayList() {
        return partyArrayList;
    }

    public void setPartyArrayList(ArrayList<Party> partyArrayList) {
        this.partyArrayList = partyArrayList;
    }

    public void addParty(Party p) {
        partyArrayList.add(p);
    }


}
