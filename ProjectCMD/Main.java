/*
 * Purpose:
 * To allow the user to create, modify, and access patient records.
 * Prescriptions can be added or deleted from individual patient records.
 * If a patient is taking more than 1 prescription, the program will check
 * whether there are any major interactions between them.
 * If a major interaction is detected, the patient file will store a warning.
 * This is intended to allow health-care professionals to:
 *      1. Reassess patient medication management
 *      2. Make sure there are no mistakes, prescriptions are as intended
 *      3. Quick management of multiple patients
 * The program also has a mini-feature that allows the user to search a drug
 * that the program recognizes. If the searched drug is recognized, a list of
 * other drugs that negatively interact with it will be displayed.
 */

import healthrecord.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Program implements MenuOptions {
    private ArrayList<Patient> patients;    // To hold patients
    private Drug[] drugs;                   // Holds information for top 50 drugs

    // Constructor
    Program() {
        // Read in drug data
        ReadFile file = new ReadFile();
        drugs = file.getDrugList();

        // Create and read in patient data
        patients = new ArrayList<Patient>();
        preLoadInfo();
        // Sort patient data
        Collections.sort(patients);
    }

    // Run program
    void run() {
        final int NUM_OPTION = 7;   // Number of menu options
        Scanner scan = new Scanner(System.in);  // To read user input
        int input;  // User menu input

        // Output menu
        System.out.println();
        System.out.println("--------------------");
        System.out.println("        Menu        ");
        System.out.println("--------------------");
        System.out.println("1. View patient");
        System.out.println("2. Add patient");
        System.out.println("3. Edit patient");
        System.out.println("4. Delete patient");
        System.out.println("5. Search drug reference");
        System.out.println("6. Write patient data to file");
        System.out.println("7. Exit\n");
        System.out.print("Enter input: ");

        // Loop until valid input is received
        do {
            input = scan.nextInt();    // Get user input

            if(input < 1 || input > NUM_OPTION) {
                System.out.println("Error: Invalid input.");
                System.out.print("Enter input: ");
            }
        } while(input < 1 || input > NUM_OPTION);

        // Determine the program response to user input
        switch(input) {
            case 1: viewPatient();      // View patient
                    promptEnterKey();
                    run();
                    break;
            case 2: addPatient();       // Add patient
                    promptEnterKey();
                    run();
                    break;
            case 3: editPatient();      // Edit patient
                    promptEnterKey();
                    run();
                    break;
            case 4: deletePatient();    // Delete patient
                    promptEnterKey();
                    run();
                    break;
            case 5: searchDrug();       // Search drug reference
                    promptEnterKey();
                    run();
                    break;
            case 6: fileOut();          // Output patient data to file
                    promptEnterKey();
                    run();
                    break;
            default: System.out.println("Exiting program"); // Program exit
        }
        scan.close();   // Close scanner
    }

    // Add patient
    @Override
    public void addPatient() {
        // To hold first and last names of patients
        String firstName, lastName;

        // Get input from user regarding patient info
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter patient's first name: ");
        firstName = scan.next();
        System.out.print("Enter patient's last name: ");
        lastName = scan.next();

        // Add patient to array list
        patients.add(new Patient(firstName, lastName));
        System.out.println("Patient added.");

        // Sort patient list by last name
        Collections.sort(patients);
    }

    // Edit patient information
    @Override
    public void editPatient() {
        // Get patient to edit
        int in = scanPatient();
        if(in != -1) {
            // Mini menu
            System.out.println();
            System.out.println(patients.get(in-1).getFullName());
            System.out.println("1. Add prescription");
            System.out.println("2. Delete prescription");
            System.out.println("3. Go back to menu");
            System.out.print("Enter input: ");

            Scanner scan = new Scanner(System.in);  // To read user input
            int userInput = scan.nextInt();         // Get user input

            // Add prescription menu
            if(userInput == 1) {
                Boolean found = false;  // Flag to check if prescription is valid
                Drug drug = new Drug(); // Create reference to drug variable

                // Get user input
                System.out.print("Enter prescription: ");
                String strInput = scan.next();

                // Check if drug exists in 'database'
                for(int i=0; i<drugs.length; i++) {
                    if(strInput.equals(drugs[i].getName())) {
                        found = true;
                        drug = drugs[i];
                        break;
                    }
                }

                // If drug exists in database, add the prescription
                if(found) {
                    patients.get(in-1).addPrescription(drug);
                    System.out.println("Prescription '" + drug.getName() + "' added.");
                } else { // Output error if drug does not exist
                    System.out.println("Error: Drug not found in database.");
                }
            } else if(userInput == 2) { // Delete prescription menu
                // Get patient prescriptions
                Drug arr[] = patients.get(in-1).getPrescription();

                // If no prescriptions are found, cancel
                if(arr.length <= 0) {
                    System.out.println("No prescriptions found");
                    return;
                }

                // Output prescription choices to delete
                System.out.println("\nPrescriptions:");
                for(int i=0; i<arr.length; i++) {
                    System.out.println((i+1) + ". " + arr[i].getName());
                }

                // Get user input
                System.out.print("Enter number to delete: ");
                int pInput = scan.nextInt();

                // Validate input and delete if valid
                if(pInput >= 1 && pInput <= arr.length) {
                    patients.get(in-1).deletePrescription(pInput);
                    System.out.println("Prescription deleted.");
                } else {
                    System.out.println("Error: Invalid input.");
                }
            } else if(userInput == 3) { // Go back to menu option
                // Nothing required here
            } else {
                System.out.println("Error: Invalid input.");
            }
        }
    }

    // View patient information
    @Override
    public void viewPatient() {
        int in = scanPatient();
        if(in != -1) {
            System.out.println();
            patients.get(in - 1).showInfo();
        }
        return;
    }

    // Delete patient
    @Override
    public void deletePatient() {
        int in = scanPatient();
        if(in != -1) {
            patients.remove(in - 1);
            System.out.println("Patient record deleted.");
        }
        return;
    }

    // Search drug functionality
    @Override
    public void searchDrug() {
        String userInput;    // Hold user input
        String drugName;     // Hold drug name

        // Get input from user
        Scanner scan = new Scanner(System.in);
        System.out.print("\nEnter drug name: ");
        userInput = scan.next();

        // Search for drug name
        for(int i=0; i<drugs.length; i++) {
            drugName = drugs[i].getName();
            if(drugName.equals(userInput)) {
                System.out.println();
                drugs[i].showInfo();
                return;
            }
        }
        System.out.println("\nDrug '" + userInput + "' not found.");
    }

    // Output patient data to a text file named patient_data.txt
    @Override
    public void fileOut() {
        try {
            String fn = "patient_data.txt"; // File out name
            BufferedWriter writer = new BufferedWriter(new FileWriter(fn));

            for(int i=0; i<patients.size(); i++) {
                // Get patient's name and prescriptions
                String name = patients.get(i).getFullName();
                Drug drug[] = patients.get(i).getPrescription();
                Boolean status = patients.get(i).getWarning();

                // Write name to file
                writer.write("Name: " + name + "\n");

                // Write prescription data to file
                writer.write("Prescriptions: ");
                if(drug.length-1 >= 0) {        // If there are prescriptions
                    for(int j = 0; j < drug.length - 1; j++) {
                        writer.write(drug[j].getName() + ", ");
                    }
                    writer.write(drug[drug.length - 1].getName() + "\n");
                } else {
                    writer.write("None\n"); // If no prescriptions found
                }

                // Write warning to file
                if(status)
                    writer.write("WARNING - Major interaction detected.\n\n");
                else
                    writer.write("No interactions detected.\n\n");
            }
            writer.close();
            System.out.println("Data successfully written to " + fn);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    // Scan in user input when searching for a patient
    private int scanPatient() {
        int input;  // To hold user input (patient number)

        // Make sure patient array has at least 1 patient
        if(patients.size() <= 0) {
            System.out.println("\nNo patients found.");
            input = -1; // Invalid input
        } else {
            // Display patient records
            System.out.println("\nList of patients (LastName, FirstName):");
            for(int i = 0; i < patients.size(); i++) {
                System.out.print((i + 1) + ". ");
                System.out.println(patients.get(i).getLastName() + ", " + patients.get(i).getFirstName());
            }

            // Get user input
            Scanner scan = new Scanner(System.in);
            System.out.print("\nEnter number to view patient: ");
            input = scan.nextInt();
            if(input < 1 || input > patients.size()) { // Validate user input
                System.out.println("Error: Invalid input.");
                input = -1; // Invalid input
            }
        }
        return input;
    }

    // Preload some patient information
    private void preLoadInfo() {
        // Patient 1
        Drug[] arr = { drugs[1], drugs[8] };
        patients.add(new Patient("John", "Doe", arr));

        // Patient 2
        Drug[] arr2 = {drugs[0], drugs[1], drugs[2], drugs[3] };
        patients.add(new Patient("Sally", "Shell", arr2));

        // Patient 3
        Drug[] arr3 = { drugs[15], drugs[30] };
        patients.add(new Patient("Tommy", "Sand", arr3));
    }

    // To prompt user to press a key before displaying the menu again
    private void promptEnterKey(){
        System.out.println();
        System.out.print("Press \"ENTER\" to return to menu");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
}

public class Main {
    public static void main(String[] args) {
        // Create and run program
        Program program = new Program();
        program.run();
    }
}
