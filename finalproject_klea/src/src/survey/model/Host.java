package src.survey.model;
/**
 * Final Project Evite Code
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/

import java.util.ArrayList;

public class Host extends Guest { //extends from guess

    public Host(String email, String name, String password, boolean member, ArrayList<Party> partyArrayList) {
        super(email, name, password, member, partyArrayList);
    }

    //two different constructors
    public Host(String email, String name, String password, boolean member) {
        super(email, name, password, member);

    }
}
