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

    // Access first name only
    public String getFirstName() {
        return fName;
    }

    // Access last name only
    public String getLastName() {
        return lName;
    }

    // Access first and last name of person
    public String getFullName() {
        return fName + " " + lName;
    }

    // Access last name and first name (For comparison purposes)
    String getLastFirstName() {
        return lName + fName;
    }
}

// Patient class
public class Patient extends Person implements DisplayInfo, Comparable<Patient> {
    private Drug[] prescriptions;   // List of prescriptions patient is taking
    private Boolean warning;        // Major interaction between prescriptions

    // Constructor: Takes first and last name
    public Patient(String f, String l) {
        super(f, l);
        prescriptions = new Drug[0];    // Create empty array
        warning = false;
    }

    // Constructor 2: Takes first & last name and prescription/drug list
    public Patient(String f, String l, Drug[] arr) {
        super(f, l);
        prescriptions = arr;
        checkInteraction(); // Set warning
    }

    // Check patient's prescription list for interactions
    private void checkInteraction() {
        for(int i=0; i<prescriptions.length; i++) {
            for(int j=0; j<prescriptions[i].getInteractionsSize(); j++) {
                for(int k=0; k<prescriptions.length; k++) {
                    if(prescriptions[i].getInteraction(j).equals(prescriptions[k].getName())) {
                        warning = true; // Major interaction found
                        return;
                    }
                }
            }
        }
        warning = false;    // No major interaction found
    }

    // Access patient warning status
    public Boolean getWarning() {
        return warning;
    }

    // Return array of prescriptions
    public Drug[] getPrescription() {
        return prescriptions;
    }

    // Add a prescription
    public void addPrescription(Drug d) {
        // Create a new array with 1 element more than original
        Drug newArray[] = new Drug[prescriptions.length+1];
        // Fill new array
        for(int i=0; i<prescriptions.length; i++) {
            newArray[i] = prescriptions[i];
        }
        newArray[newArray.length-1] = d;    // Add new prescription
        prescriptions = newArray;   // Change reference

        // Check for drug interactions
        checkInteraction();
    }

    // Delete a prescription from array, parameter: array element number
    public void deletePrescription(int n) {
        n -= 1; // Get correct corresponding element number

        // Create a new array with 1 element less than original
        Drug newArray[] = new Drug[prescriptions.length-1];
        // Fill first half of new array
        for(int i=0; i<n; i++) {
            newArray[i] = prescriptions[i];
        }
        // Fill second half of new array
        for(int i=n; i<newArray.length; i++) {
            newArray[i] = prescriptions[i+1];
        }
        prescriptions = newArray;   // Change reference

        // Update drug interactions
        checkInteraction();
    }

    // Print list of prescriptions patient is taking
    private void printPrescriptions() {
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
    private void printWarning() {
        // Output interaction warning if toggled on
        if(warning)
            System.out.println("WARNING - Major interaction detected");
        else
            System.out.println("None detected");
    }

    // Show patient information
    @Override
    public void showInfo() {
        // Print name
        System.out.print("Patient name: " + getFullName() + "\n");
        // Print prescriptions
        System.out.print("Prescription: ");
        printPrescriptions();
        // Print warnings
        System.out.print("Interactions: ");
        printWarning();
    }

    // Sort custom order by last name first, first name second
    @Override
    public int compareTo(Patient o) {
        return this.getLastFirstName().compareTo(o.getLastFirstName());
    }
}
