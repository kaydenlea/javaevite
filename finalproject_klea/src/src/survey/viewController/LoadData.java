/**
 * Final Project Evite Code
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/
package src.survey.viewController;
/**
 * Final Project Evite Code
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/

import src.survey.model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoadData { //made to specifically read in data
    public static Map<String, Guest> readInUsers(String userFile) { //gets all the users
        // Reading a File From SCRATCH
        boolean success = false;
        int times = 0;
        Map<String, Guest> myDatabase = new HashMap<>(); // make an empty map
        do {
            try (FileInputStream fis = new FileInputStream(userFile);
                 Scanner sc = new Scanner(fis)) {
                //First: get each line and echo it.
                String header = sc.nextLine();
                while (sc.hasNextLine()) { // for files,keep going while there are still lines in the file
                    String line = sc.nextLine();
                    Guest u = parseUser(line);
                    if (u != null) {
                        myDatabase.put(u.getEmail(), u); // add to database
                    } else {
                        System.out.println("Ignoring line of bad data: " + line);
                    }
                }
                success = true;
            } catch (FileNotFoundException e) {
                System.err.println("Couldn't find file: " + e);
                success = false;
                // file name was bad....
                Scanner input = new Scanner(System.in);
                System.out.print("Enter new file name >"); // hard-coding (!MVC)
                userFile = input.nextLine();
                times++; // keep track of things
                //e.printStackTrace();
            } catch (IOException e) {
                System.err.println("IO Exception Couldn't find file: " + e);
                e.printStackTrace();
            }
        } while (!success && times < 3);

        return myDatabase;
    }

    private static Guest parseUser(String line) { //parses user
        Scanner sc = new Scanner(line);
        boolean typeB = false;
        sc.useDelimiter(",");
        // "type\temail\tfull name\tpassword\t")
        Guest u = null;

        if (sc.hasNext()) {
            String type = sc.next();
            if (type.equalsIgnoreCase("false")) {
                typeB = false;
            } else {
                typeB = true;
            }
            String email = sc.next();
            String name = sc.next();
            String pword = sc.next();
            //int age = sc.nextInt(); // EXCEPTION of InputMismatchException could occur, let's HANDLE it.
            //TODO: check type and make correct
            u = new Guest(email, name, pword, typeB);
        }
        return u;
    }

    public static void main(String[] args) {
        Map<String, Guest> map = readInUsers(Program.USER_FILE);
        //Map<String, User> map = readInUsers("users.tsv");

        System.out.println("Program still works, here is map: " + map);
        try {
            int[] nums = {1, 2, 3};
            System.out.println(nums[5]);
        } catch (Exception e) {
            System.err.println("an exception happened, which i will ignore." + e);
        }
        System.out.println("Program still going??");
    }

    public static ArrayList<Party> readPartyFile(String email) { //reads in party files for informatin
        // TODO Auto-generated method stub
        ArrayList<Party> partyList = new ArrayList<>(); //creates array list for the people
        try (FileInputStream fis = new FileInputStream(email + ".txt");
             Scanner scanner = new Scanner(fis)) {
            if (scanner.hasNext()) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine(); //parses people to the array list
                    Party p = parseStringIntoParty(line);
                    partyList.add(p);
                }
            }


        } catch (FileNotFoundException e) {
            System.err.println("File not found, problem saving data... " + email + ".txt");
        } catch (IOException e) {
            System.err.println("Some other IO Problem happened while saving data... " + e);
        } // files are closed automatically during try-with-resource
        return partyList;
    }

    private static Party parseStringIntoParty(String line) { //gets each of the features of the party from file
        Party party = null; //gets the people from the line
        //one line of data from survey data
        try {
            Scanner ls = new Scanner(line);
            ls.useDelimiter(",");
            String type = ls.next(); //timestamp
            String name = ls.next();
            String dateStr = ls.next();
            String hostEmail = ls.next();
            String hostName = ls.next();
            String password = ls.next();
            boolean isMember = ls.nextBoolean();
            String item = ls.next();
            ArrayList<String> guestList = new ArrayList<>();
            while (ls.hasNext()) {
                guestList.add(ls.next());
            }
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
            Host host = new Host(hostEmail, hostName, password, isMember);
            if (type.equalsIgnoreCase("GiftParty")) {
                party = new GiftParty(name, date, host, guestList, item);
            } else {
                party = new FoodParty(name, date, host, guestList, item);
            }

        } catch (Exception e) {
            System.err.println("Error reading line of file: " + line + "\nerror; " + e);
        }
        return party;

    }

    public static Map<String, Boolean> readRSVPFiles(String fileName) { //reads in rsvp files
        Map<String, Boolean> guestMap = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(fileName);
             Scanner scanner = new Scanner(fis)) {
            if (scanner.hasNext()) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Scanner ls = new Scanner(line);
                    ls.useDelimiter(",");
                    String email = ls.next();
                    Boolean status = ls.nextBoolean();
                    guestMap.put(email, status);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found, problem saving data... " + fileName);
        } catch (IOException e) {
            System.err.println("Some other IO Problem happened while saving data... " + e);
        } // files are closed automatically during try-with-resource
        return guestMap;

    }
}