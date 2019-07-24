package healthrecord;

// Person class
class Person {
    protected String fName; // First name
    protected String lName; // Last name

    // Constructor
    Person(String f, String l) {
        fName = f;
        lName = l;
    }

    // Print name
    public void printName() {
        System.out.println(fName + " " + lName);
    }

    public String getName() {
        return fName + " " + lName;
    }
}

// Patient class
public class Patient extends Person implements DisplayInfo {
    private Drug[] prescriptions;   // List of prescriptions patient is taking
    private Boolean warning;        // Major interaction between prescriptions

    // Constructor: Takes first and last name
    public Patient(String f, String l) {
        super(f, l);
        prescriptions = null;
        warning = false;
    }

    // Constructor 2: Takes first & last name and prescription/drug list
    public Patient(String f, String l, Drug[] arr) {
        super(f, l);
        prescriptions = arr;
        checkInteraction();
    }

    // Show patient information
    @Override
    public void showInfo() {
        // Print name
        System.out.print("Patient name: ");
        printName();
        // Print prescriptions
        System.out.print("Prescription: ");
        printPrescriptions();
        // Warnings
        System.out.print("Interactions: ");
        printWarning();
    }

    // Print list of prescriptions patient is taking
    void printPrescriptions() {
        // If no prescription, output "None"
        if(prescriptions == null || prescriptions.length <= 0) {
            System.out.println("None");
        } else {
            // Else print prescription list separated by commas
            for(int i=0; i < prescriptions.length-1; i++) {
                System.out.print(prescriptions[i].getName() + ", ");
            }
            System.out.println(prescriptions[prescriptions.length-1].getName());
        }
    }

    // Print interaction warning
    void printWarning() {
        // Check for interactions
        if(prescriptions != null)
            checkInteraction();

        // Output interaction warning if toggled on
        if(warning)
            System.out.println("WARNING - Major interaction detected");
        else
            System.out.println("None detected");
    }

    // Check patient's prescription list for interactions
    public void checkInteraction() {
        for(int i=0; i<prescriptions.length; i++) {
            for(int j=0; j<prescriptions[i].getInteractionsSize(); j++) {
                for(int k=0; k<prescriptions.length; k++) {
                    if( prescriptions[i].getInteraction(j).equals(prescriptions[k].getName()) ) {
                        warning = true; // Major interaction found
                        return;
                    }
                }
            }
        }
        warning = false;    // No major interaction found
    }

    // Add a prescription
    public void addPrescription(Drug d) {
        Drug newArray[] = new Drug[prescriptions.length+1];
        for(int i=0; i<prescriptions.length; i++) {
            newArray[i] = prescriptions[i];
        }
        newArray[newArray.length-1] = d;
        prescriptions = newArray;
    }

    // Return array of prescriptions
    public Drug[] getPrescription() {
        return prescriptions;
    }

    // Delete a prescription from array, parameter: array element number
    public void deletePrescription(int n) {
        n -= 1; // Get correct corresponding element number

        Drug newArray[] = new Drug[prescriptions.length-1];
        for (int i=0; i<n; i++) {
            newArray[i] = prescriptions[i];
        }
        for (int i=n; i<newArray.length; i++) {
            newArray[i] = prescriptions[i+1];
        }

        prescriptions = newArray;
    }
}
