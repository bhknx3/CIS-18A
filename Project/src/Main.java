import healthrecord.*;
import java.util.ArrayList;
import java.util.Scanner;

class Program {
    ArrayList<Patient> patients;    // To hold patients
    Drug drugs[];                   // Holds information for top 50 drugs

    Program() {
        // Read in drug data
        ReadFile file = new ReadFile();
        drugs = file.getDrugList();

        // Create and read in patient data
        patients = new ArrayList<Patient>();
        preLoadInfo();
    }

    // Output menu
    void showMenu() {
        Scanner scan = new Scanner(System.in);  // To read user input
        int input;  // User input

        // Output menu
        System.out.println();
        System.out.println("--------------------");
        System.out.println("\t\tMenu");
        System.out.println("--------------------");
        System.out.println("1. Add patient");
        System.out.println("2. Edit patient");
        System.out.println("3. View patient");
        System.out.println("4. Exit\n");
        System.out.print("Enter input: ");

        // Get valid input from menu
        do {
            input = scan.nextInt();	// Get user input

            if(input < 1 || input > 4) {
                System.out.println("Error: Invalid input.");
            }
        } while (input < 1 || input > 4);

        // Determine the program response to user input
        switch(input) {
            case 1: addPatient();
                    showMenu();
                    break;
            case 2: System.out.println("test2");
                    showMenu();
                    break;
            case 3: viewPatient();
                    showMenu();
                    break;
            //case 4: viewDrug();
            //        showMenu();
            //        break;
            default: System.out.println("Exiting program");
        }
        scan.close();   // Close scanner
    }

    // Add patient to information
    void addPatient() {
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

    void viewPatient() {
        if(patients.size() <= 0) {
            System.out.println("No patients found.");
        } else {
            // Display patient records
            System.out.println("List of patients:");
            for(int i=0; i<patients.size(); i++) {
                System.out.print((i+1) + ". ");
                patients.get(i).printName();
            }

            // Allow user to choose a patient record to view
            Scanner scan = new Scanner(System.in);  // To read user input
            int input;

            System.out.print("\nEnter number to view patient: ");
            do {
                input = scan.nextInt();	// Get user input

                if(input < 1 || input > patients.size()) {
                    System.out.println("Error: Invalid input.");
                }
            } while (input < 1 || input > patients.size());

            patients.get(input-1).showInfo();
        }
    }

    // Preload some patient information
    void preLoadInfo() {
        //Patient 1
        Drug arr[] = { drugs[1], drugs[8] };
        patients.add(new Patient("John", "Doe", arr));

        // Patient 2
        Drug arr2[] = {drugs[0], drugs[1], drugs[2], drugs[3] };
        patients.add(new Patient("Sally", "Shell", arr2));
    }

    void viewDrug() {

    }
}

public class Main {
    public static void main(String[] args) {
        Program menu = new Program();
        menu.showMenu();
    }
}
