package ui;

import model.BloodBank;
import model.BloodDonor;
import model.BloodType;
import model.Patient;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Blood donation management system application
public class BloodDonationApp {
    public static final int AMOUNT_PER_PERSON = 450;
    public static final String JSON_STORE = "./data/bloodbank.json";
    private Scanner input;
    private BloodBank bloodBank;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: run the blood donation application
    public BloodDonationApp() {
        input = new Scanner(System.in);
        bloodBank = new BloodBank("Vancouver");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
//        runBloodDonation();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
//    private void runBloodDonation() {
//        boolean keepGoing = true;
//        String command;
//
//        init();
//
//        while (keepGoing) {
//            salutation();
//            command = input.next();
//            command = command.toUpperCase();
//
//            if (command.equals("Q")) {
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//        }
//
//        System.out.println("\nThank you for choosing blood service!");
//        System.out.println("Have a nice day!");
//    }

    // EFFECT: displays the salutation and a list of available characters to the user
    private void salutation() {
        System.out.println("\nWelcome to blood service!");
        System.out.println("Please choose your character: ");
        System.out.println("D -> Blood Donor");
        System.out.println("P -> Patient");
        System.out.println("M -> Manager");
        System.out.println("S -> Save blood bank information to file");
        System.out.println("L -> Load blood bank information from file");
        System.out.println("Q -> Quit");
    }


    // MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("D")) {
            doDonation();

        } else if (command.equals("P")) {
            System.out.println(useBlood());
        } else if (command.equals("M")) {
            verifyIdentity();
        } else if (command.equals("S")) {
            saveBloodBank();
        } else if (command.equals("L")) {
            loadBloodBank();
        } else {
            System.out.println("You have to select a character...");
        }
    }

    // MODIFIES: this
    // EFFECTS: collect the donor's information, check whether the donor is eligible
    //          if eligible, let the donor donate the blood, else let the donor leave
    private void doDonation() {
        System.out.println("Please enter your name: ");
        String name = input.next();
        System.out.println("Please enter your age: ");
        int age = input.nextInt();
        System.out.println("Please enter your weight(kg): ");
        double weight = input.nextDouble();
        System.out.println("Please enter your blood type(A, B, AB, or O): ");
        BloodType bloodType = BloodType.valueOf(input.next());

        // set a donor according to the input
        BloodDonor d = new BloodDonor(name, age, weight, bloodType);

        //if the donor is eligible, then do the donation
        if (BloodBank.isEligible(d)) {
            System.out.println("Congrats! You are eligible.");
            bloodBank.donateBlood(d);
            System.out.println("Thank you for donating blood!");
            System.out.println("Here is your donor card:");
            generateDonorCard(d);

        // if the donor is not eligible, then let the donor leave
        } else {
            System.out.println("Sorry, you are not eligible.");
            System.out.println("Thanks for your kindness!");
            System.out.println("Come here next time.");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes the blood bank
    public void init() {
        bloodBank = new BloodBank("Vancouver");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays a donor card according to the donor's personal information
    public void generateDonorCard(BloodDonor d) {
        System.out.println("\n******************************");
        System.out.println("[DONOR CARD]");
        System.out.println("NAME: " + d.getName());
        System.out.println("AGE: " + d.getAge());
        System.out.println("AMOUNT: " + AMOUNT_PER_PERSON + "ml");
        System.out.println("******************************");
    }


    //MODIFIES: this
    // EFFECTS: collect the patient's information, use the blood accordingly
    public String useBlood() {
        System.out.println("Please enter your name: ");
        String name = input.next();
        System.out.println("Please enter your blood type(A, B, AB, or O): ");
        BloodType bloodType = BloodType.valueOf(input.next());
        System.out.println("Please enter your required amount(ml): ");
        int requiredAmount = input.nextInt();
        System.out.println("Are you in urgent need? (true/ false)");
        boolean isUrgent = input.nextBoolean();
        Patient p = new Patient(name, bloodType, requiredAmount, isUrgent);
        return (bloodBank.useBlood(p));
    }

    // EFFECTS: check the identity of the manager using a security code
    public void verifyIdentity() {
        System.out.println("Please enter the security code: ");
        String code = input.next();
        if ("trustnaturalrecursion".equals(code)) {
            System.out.println("CORRECT ANSWER!");
            manageBloodBank();
        } else {
            System.out.println("WRONG ANSWER!");
        }

    }

    // EFFECTS: displays the basic information of the blood bank to the verified manager
    public void manageBloodBank() {
        System.out.println("\n***************");
        displayInventory();
        displayDonorList();
        displayPatientWaitList();
        displayUrgentPatientWaitList();
        System.out.println("***************");
    }


    // EFFECTS: displays the blood inventory of the blood bank
    public void displayInventory() {
        System.out.println("The total amount of Type A blood is: " + bloodBank.getTotalAmountOfTypeA());
        System.out.println("The total amount of Type B blood is: " + bloodBank.getTotalAmountOfTypeB());
        System.out.println("The total amount of Type AB blood is: " + bloodBank.getTotalAmountOfTypeAB());
        System.out.println("The total amount of Type O blood is: " + bloodBank.getTotalAmountOfTypeO());
    }

    // EFFECTS: displays the donor list with each donor's name and blood type on it
    public void displayDonorList() {
        System.out.println("\nHere is the donor list: ");
        if (bloodBank.getDonorList().isEmpty()) {
            System.out.println("empty");
        } else {
            for (int i = 0; i < bloodBank.getDonorList().size(); i++) {
                System.out.println("Name: " + bloodBank.getDonorList().get(i).getName()
                        + " Blood Type: " + bloodBank.getDonorList().get(i).getBloodType());
            }
        }
    }

    // EFFECTS: displays the normal patient wait list with each patient's name, blood type, and required  amount
    public void displayPatientWaitList() {
        System.out.println("\nHere is the normal patient wait list: ");
        if (bloodBank.getPatientWaitList().isEmpty()) {
            System.out.println("empty");
        } else {
            for (int i = 0; i < bloodBank.getPatientWaitList().size(); i++) {
                System.out.println("Name: " + bloodBank.getPatientWaitList().get(i).getName()
                        + " Blood Type: " + bloodBank.getPatientWaitList().get(i).getBloodType()
                        + " Required Amount: " + bloodBank.getPatientWaitList().get(i).getRequiredAmount());
            }
        }
    }

    // EFFECTS: displays the urgent patient wait list with each patient's name, blood type, and required  amount
    public void displayUrgentPatientWaitList() {
        System.out.println("\nHere is the urgent patient wait list: ");
        if (bloodBank.getUrgentPatientWaitList().isEmpty()) {
            System.out.println("empty");
        } else {
            for (int i = 0; i < bloodBank.getUrgentPatientWaitList().size(); i++) {
                System.out.println("Name: " + bloodBank.getUrgentPatientWaitList().get(i).getName()
                        + " Blood Type: " + bloodBank.getUrgentPatientWaitList().get(i).getBloodType()
                        + " Required Amount: " + bloodBank.getUrgentPatientWaitList().get(i).getRequiredAmount());
            }
        }
    }

    // EFFECTS: saves the bloodbank info to file
    public void saveBloodBank() {
        try {
            jsonWriter.open();
            jsonWriter.write(bloodBank);
            jsonWriter.close();
//            System.out.println("Saved " + bloodBank.getLocation() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads blood bank info from file
    public void loadBloodBank() {
        try {
            bloodBank = jsonReader.read();
            System.out.println("Loaded " + bloodBank.getLocation() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public BloodBank getBloodBank() {
        return bloodBank;
    }






}






