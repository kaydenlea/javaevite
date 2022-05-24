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

public abstract class Party implements FilePrintable { //main class for parties

    protected String partyName;
    protected LocalDate partyDate;
    private Host host;
    private ArrayList<String> guestList;
    private Map<String, Boolean> guestMap;

    public Party(String partyName, LocalDate partyDate, Host host, ArrayList<String> guestList) {
        this.partyName = partyName;
        this.partyDate = partyDate;
        this.host = host;
        this.guestList = guestList;

    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public LocalDate getPartyDate() {
        return partyDate;
    }

    public void setPartyDate(LocalDate partyDate) {
        this.partyDate = partyDate;
    }

    public ArrayList<String> getGuestList() {
        return guestList;
    }

    public void setGuestList(ArrayList<String> guestList) {
        this.guestList = guestList;
    }

    public String getClassString() {
        return "Party";
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

