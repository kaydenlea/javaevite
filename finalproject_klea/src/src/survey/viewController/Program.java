package src.survey.viewController;

/**
 * Final Project Evite Code
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/

import src.survey.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Program {//main program
    public static final String USER_FILE = "users.txt";
    Helper helper = new Helper();
    private Guest currentUser;
    private UI ui;
    private Map<String, Guest> userDatabase; //email to User (or subclass) of the program
    private ArrayList<Party> allParties;


    public Program() {

        ui = new UIConsole();
        userDatabase = new HashMap<>();
        currentUser = null;
        allParties = new ArrayList<>();
        userDatabase = LoadData.readInUsers(USER_FILE); //READS IN ALL THE USER DATA
        ;
    }

    public static void main(String[] args) {
        Program program = new Program();
        program.run();
    }

    public void run() {
        ui.print("Welcome to your party program");
        boolean quit = false;
        while (!quit) {
            // loop until quit
            Menu option = null;
            if (ui instanceof UIConsole) {
                Menu.printMenuOptions();//prints all the options for the user
                if (currentUser == null) {
                    ui.print("No User is logged in");//gives status of user login
                } else {
                    ui.print("Current User is: " + currentUser.getName());
                }
                int choice = ui.inputInt("", 1, Menu.getNumOptions());
                option = Menu.getOption(choice);
            }
            switch (option) {//goes through all the options
                case LOGIN:
                    login();
                    break;
                case LOGOUT:
                    logout();
                    break;
                case CREATE_PARTY:
                    if (currentUser == null) {
                        login();
                        createParty();
                    } else {
                        createParty();
                    }
                    break;
                case MEMBER_SIGNUP:
                    if (currentUser == null) {
                        login();
                        memberShip();
                    } else {
                        memberShip();
                    }
                    break;
                case VIEW_PARTIES:
                    if (currentUser == null) {
                        login();
                        printPartyList();
                    } else {
                        printPartyList();
                    }
                    break;
                case RSVP:
                    if (currentUser == null) {
                        login();
                        rsvp();
                    } else {
                        rsvp();

                    }
                    break;
                case RSVP_STATUS:
                    if (currentUser == null) {
                        login();
                        rsvpStatus();
                    } else {
                        rsvpStatus();

                    }
                    break;
                case END:
                    quit = true;
                    if (currentUser == null) {
                        break;
                    } else {
                        saveData();
                        break;
                    }

            }
        }
        ui.print("Goodbye");

    }

    private void memberShip(){
        boolean choice = helper.inputYesNo("Would you like to sign up to be a member for 5 dollar?: ");
        if (choice){
            System.out.println("Thank you, you are now a member");
            currentUser.setMember(true);
        }
        else{
            System.out.println("No problem!");
        }
    }

    private void rsvpStatus() {
        //method that prints back out what the status of each guest is in the specified party
        String hostEmail = ui.inputWord("What is the host's email?: ");
        ArrayList<Party> partyList = LoadData.readPartyFile(hostEmail);
        int index = 0;
        for (Party party : partyList) {
            System.out.println(index + ": " + party.getPartyName());
            index += 1;
        }
        int userChoice = helper.inputInt("Which party would you like to check?: ", 0, partyList.size() - 1);
        String fileName = hostEmail + "." + partyList.get(userChoice).getPartyName() + ".txt";
        Map<String, Boolean> guestMap = LoadData.readRSVPFiles(fileName);
        for (String email : guestMap.keySet()) {
            String rsvpStatStr = "";
            if (guestMap.get(email).equals(false)) {
                rsvpStatStr = "Not RSVPed";
            } else {
                rsvpStatStr = "RSVPed";
            }
            System.out.println(email + ": " + rsvpStatStr);
        }
    }

    private void createRSVPFiles() { //creates files for guest RSVP status to be stored
        for (Party party : allParties) {
            ArrayList<String> guestList = party.getGuestList();

                try (FileOutputStream fos = new FileOutputStream(currentUser.getEmail() + "." + party.getPartyName() + ".txt");
                     PrintWriter out = new PrintWriter(fos)) {
                    for (String email : guestList) {
                        out.println(email + ",false");
                        System.out.println("Saved "+ email);
                    }
                } catch (FileNotFoundException e) {
                    new File(currentUser.getEmail() + "." + party.getPartyName() + ".txt");
                    System.err.println("File not found, problem saving data... " + currentUser.getEmail() + "." + party.getPartyName() + ".txt");
                } catch (IOException e) {
                    System.err.println("Some other IO Problem happened while saving data... " + e);
                } // files are closed automatically during try-with-resource

                System.out.println("Saved status to: " + currentUser.getEmail() + "." + party.getPartyName() + ".txt");
            }

        }



    private void rsvp() {//method that changes the rsvp status when called of the current user
        Map<String, Boolean> guestMap = new HashMap<>();
        String hostEmail = ui.inputWord("What is the host's email?: "); //based on host email to get file
        ArrayList<Party> partyList = LoadData.readPartyFile(hostEmail);
        int index = 0;
        for (Party party : partyList) {
            System.out.println(index + ": " + party.getPartyName());
            index += 1;
        }
        int userChoice = helper.inputInt("Which party would you like to RSVP for?: ", 0, partyList.size() - 1);
        String fileName = hostEmail + "." + partyList.get(userChoice).getPartyName() + ".txt"; //creates a file based on the party to hold the rsvp
        guestMap = LoadData.readRSVPFiles(fileName);
        guestMap.replace(currentUser.getEmail(), true);
        for (String email : guestMap.keySet()) {
            try (FileOutputStream fos = new FileOutputStream(fileName);
                 PrintWriter out = new PrintWriter(fos)) {
                out.println(email + "," + guestMap.get(email));
            } catch (FileNotFoundException e) {
                new File(fileName);
                System.err.println("File not found, problem saving data... " + fileName);
            } catch (IOException e) {
                System.err.println("Some other IO Problem happened while saving data... " + e);
            } // files are closed automatically during try-with-resource
        }
        if(guestMap.get(currentUser.getEmail()).equals(true)){
            System.out.println("Successfully RSVPed");
        }
        else{
            System.out.println("Could not rsvp for this guest");
        }
        saveData();
    }

    private void printPartyList() {
        int count = 0;
        String fileName = currentUser.getEmail() + ".txt"; //creates file for parties based on user email to store
        if (allParties.size() == 0){
            boolean choice = helper.inputYesNo("You have no parties created, would you like to create one?: " );
            if (choice){
                createParty();
            }
            else{
                System.out.println("No problem!");
            }
        }
        else {
            for (Party p : allParties) {
                System.out.println(count + ": " + p.toString());
                count += 1;
                try (FileOutputStream fos = new FileOutputStream(fileName);
                     PrintWriter out = new PrintWriter(fos)) {
                    for (Party party : allParties) {
                        if (party instanceof FoodParty) {//gives different to String method based on type of party
                            out.println(party.toFileString());

                        } else if (party instanceof GiftParty) {
                            out.println(party.toFileString());

                        }
                    }
                } catch (FileNotFoundException e) {
                    new File(currentUser.getEmail() + ".txt");
                    System.err.println("File not found, problem saving data... " + fileName);
                } catch (IOException e) {
                    System.err.println("Some other IO Problem happened while saving data... " + e);
                } // files are closed automatically during try-with-resource

            }
        }
    }

    private void createParty() {
        //creates new party for user
        if (login(currentUser.getEmail())) {

            PartyMenu.printMenuOptions();
            PartyMenu option = null;
            if (ui instanceof UIConsole) { //gets user input based on menu for party options
                int choice = ui.inputInt(">", 1, PartyMenu.getNumOptions());
                ui.print("You picked " + choice + " which is: " + PartyMenu.getOption(choice));
                option = PartyMenu.getOption(choice);
            }
            //gets input for each of the needed variables to construct
            String name = ui.inputWord("Party Name: ");
            int month = ui.inputInt("What month(1-12)", 1, 12);
            int day = ui.inputInt("What day of the month(1-31): ", 1, 31);
            int year = ui.inputInt("What year?: ", 0 , 9999);
            LocalDate date = LocalDate.of(year, month, day);
            String password = currentUser.getPassword();//puts them as host
            Host host = new Host(currentUser.getEmail(), currentUser.getName(), password, currentUser.isMember());
            ArrayList<String> guestList = new ArrayList<>();
            int guestAmount = ui.inputInt("How many guests would you like to have?: ");
            if (!currentUser.isMember() && guestAmount>10){
                System.out.println("Sorry you must be a member to have more than 10 guests");
                memberShip();
                if (!currentUser.isMember()){
                    guestAmount = ui.inputInt("How many guests would you like to have?: ", 1, 10);
                }
                else{
                    guestAmount = ui.inputInt("How many guests would you like to have?: ");
                }
            }
            for (int i = 0; i < guestAmount; i++) {
                String email = ui.inputLine("Input guest email");
                guestList.add(email);
            }
            switch (option) {//constructs based on different options
                case FOOD_PARTY:
                    String food = ui.inputLine("What kind of food will you have?: ");
                    FoodParty newFoodParty = new FoodParty(name, date, host, guestList, food);
                    allParties.add(newFoodParty);
                    break;

                case GIFT_PARTY:
                    String gift = ui.inputLine("What kind of gift will you have?: ");
                    GiftParty newGiftParty = new GiftParty(name, date, host, guestList, gift);
                    allParties.add(newGiftParty);
                    break;

            }
            createRSVPFiles(); //creates rsvp files after party is created so that it can be saved
            saveData();
        }

    }

    private void saveParties() {//saves parties to file
        String fileName = currentUser.getEmail() + ".txt";
            try (FileOutputStream fos = new FileOutputStream(fileName);
                 PrintWriter out = new PrintWriter(fos)) {
                for (Party party : allParties) {
                    if (party instanceof FoodParty) {//calls to fileString method in party classes
                        out.println(party.toFileString());

                    } else if (party instanceof GiftParty) {
                        out.println(party.toFileString());

                    }
                }
            } catch (FileNotFoundException e) { //creates new file if it doesnt exist already
                new File(fileName);
                System.err.println("File not found, problem saving data... " + fileName);
            } catch (IOException e) {
                System.err.println("Some other IO Problem happened while saving data... " + e);
            } // files are closed automatically during try-with-resource

            System.out.println("Saved parties to: " + fileName);

    }

    private void saveData() {//saves user data method
        //calls save parties method to make sure parties are saved
        saveParties();
        //command-shift -o -- organizes imports (add needed imports to top of program)

        try (FileOutputStream fos = new FileOutputStream(USER_FILE);
             PrintWriter out = new PrintWriter(fos)) {
            // want to write each USER to the file
            out.println("type,email,full name,password,"); // simple file format
            for (Guest user : userDatabase.values()) { // using the values() VIEW from the map
                out.println(user.toFileString());
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found, problem saving data... " + USER_FILE);
        } catch (IOException e) {
            System.err.println("Some other IO Problem happened while saving data... " + e);
        } // files are closed automatically during try-with-resource

        System.out.println("Saved users to: " + USER_FILE);


    }

    private void logout() {
        if (currentUser == null) {
            ui.print("No one was logged in, so nothing to log out");
        } else { //sets user to null to log out
            ui.print("Thanks for using the system, " + currentUser.getName());
            currentUser = null;
        }

    }

    private void login() {
        if (currentUser == null) { // do login
            String email = ui.inputWord("Enter email ");
            if (this.userDatabase.containsKey(email)) { //there is an existing account
                boolean loggedIn = login(email); //checking passsword
                if (loggedIn) {
                    currentUser = userDatabase.get(email);
                    allParties = LoadData.readPartyFile(currentUser.getEmail());
                    ui.print("Welcome back, " + currentUser.getName());
                } else {
                    ui.print("Sorry, incorrect login information.");
                    //TODO should would we lock them out?
                }
            } else { // we have a new user to register
                System.out.println("No user registered under this email");
                boolean choice = helper.inputYesNo("Would you like to register for an account?");
                if (choice){
                    createNewUser(email);
                }
                else{
                    login();
                }

            }
        } else {
            ui.print("Sorry, someone else is already logged in");
            //alternative could have been log currentUser out and allow new person to log in
        }


    }

    private void createNewUser(String email) {//creates new user when user doesnt exist
        ui.print("Let's get you registered!");

        String name = ui.inputLine("Enter full name: ");
        String password = ui.inputLine("Enter a password: ");
        String type = ui.inputWord("Enter type of account you want, free or paid"); // based on polymorphic types
        Guest u = null; // start off with reference to a user.
        //Make actual user types
        if (type.equalsIgnoreCase("free")) { //membership based
            u = new Guest(email, name, password, false);
        } else if (type.equalsIgnoreCase("paid")) {
            u = new Guest(email, name, password, true);
            // sometimes we need additional data to create subclass types
        }
        userDatabase.put(email, u);
        currentUser = u; // "logs in" this person as the current User

    }

    private boolean login(String email) {//method to get whether log in is successful
        boolean success = false;
        Guest u = userDatabase.get(email); // get the User object
        String pwordGuess = ui.inputLine("Enter Password> ");
        success = u.checkPassword(pwordGuess);
        return success;
    }


}
