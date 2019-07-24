import healthrecord.*;
import java.util.ArrayList;
import java.util.Scanner;

interface MenuOptions {
    void viewPatient();
    void addPatient();
    void editPatient();
    void deletePatient();
    void searchDrug();
}

class Program implements MenuOptions {
    private ArrayList<Patient> patients;    // To hold patients
    private Drug drugs[];                   // Holds information for top 50 drugs

    Program() {
        // Read in drug data
        ReadFile file = new ReadFile();
        drugs = file.getDrugList();

        // Create and read in patient data
        patients = new ArrayList<Patient>();
        preLoadInfo();
    }

    // Output menu
    void run() {
        final int NUM_OPTION = 6;   // Number of menu options

        Scanner scan = new Scanner(System.in);  // To read user input
        int input;  // User input

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
        System.out.println("6. Exit\n");
        System.out.print("Enter input: ");

        // Get valid input from menu
        do {
            input = scan.nextInt();	// Get user input

            if(input < 1 || input > NUM_OPTION) {
                System.out.println("Error: Invalid input.");
            }
        } while (input < 1 || input > NUM_OPTION);

        // Determine the program response to user input
        switch(input) {
            case 1: viewPatient();
                    promptEnterKey();
                    run();
                    break;
            case 2: addPatient();
                    run();
                    break;
            case 3: editPatient();
                    run();
                    break;
            case 4: deletePatient();
                    run();
                    break;
            case 5: searchDrug();
                    run();
                    break;
            default: System.out.println("Exiting program");
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
    }

    // Edit patient information
    @Override
    public void editPatient() {
        // Get patient to edit
        int in = scanPatient();
        if (in != -1) {
            // Mini menu
            System.out.println();
            System.out.println(patients.get(in-1).getName());
            System.out.println("1. Add prescription");
            System.out.println("2. Delete prescription");
            System.out.println("3. Go back to menu");
            System.out.print("Enter input: ");

            Scanner scan = new Scanner(System.in);  // To read user input
            int userInput = scan.nextInt();    // Get user input

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
                // Output prescription choices to delete
                for (int i=0; i<arr.length; i++) {
                    System.out.println((i+1) + ". " + arr[i].getName());
                }

                int pInput = scan.nextInt();    // Get user input

                if(pInput >= 1 && pInput <= arr.length) {
                    patients.get(in-1).deletePrescription(pInput);
                } else {
                    System.out.println("Error: Invalid input");
                }


            } else if(userInput == 3) {
                return;
            } else {
                System.out.println("Error: Invalid user.");
                return;
            }
        }
    }

    // View patient information
    @Override
    public void viewPatient() {
        int in = scanPatient();
        if (in != -1) {
            System.out.println();
            patients.get(in - 1).showInfo();
        }
        return;
    }

    // Delete patient
    @Override
    public void deletePatient() {
        int in = scanPatient();
        if (in != -1)
            patients.remove(in-1);
        return;
    }

    // Scan in user input when searching for a patient
    int scanPatient() {
        int input;  // To hold user input (patient number)

        // Make sure patient array has at least 1 patient
        if (patients.size() <= 0) {
            System.out.println("\nNo patients found.");
            input = -1; // Invalid input
        } else {
            // Display patient records
            System.out.println("\nList of patients:");
            for (int i = 0; i < patients.size(); i++) {
                System.out.print((i + 1) + ". ");
                patients.get(i).printName();
            }

            // Allow user to choose a patient record to view
            Scanner scan = new Scanner(System.in);  // To read user input
            System.out.print("\nEnter number to view patient: ");
            input = scan.nextInt();    // Get user input
            if (input < 1 || input > patients.size()) {
                System.out.println("Error: Invalid input.");
                input = -1; // Invalid input
            }
        }
        return input;
    }

    // Preload some patient information
    void preLoadInfo() {
        // Patient 1
        Drug arr[] = { drugs[1], drugs[8] };
        patients.add(new Patient("John", "Doe", arr));

        // Patient 2
        Drug arr2[] = {drugs[0], drugs[1], drugs[2], drugs[3] };
        patients.add(new Patient("Sally", "Shell", arr2));

        // Patient 3
        Drug arr3[] = { drugs[15], drugs[30] };
        patients.add(new Patient("Tommy", "Sand", arr3));
    }

    // Search drug functionality
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
            if (drugName.equals(userInput)) {
                System.out.println();
                drugs[i].showInfo();
                return;
            }
        }
        System.out.println("\nDrug '" + userInput + "' not found.");
    }

    public void promptEnterKey(){
        System.out.println();
        System.out.print("Press \"ENTER\" to return to menu");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
}

public class Main {
    public static void main(String[] args) {
        Program start = new Program();
        start.run();
        start.addPatient();
    }
}
