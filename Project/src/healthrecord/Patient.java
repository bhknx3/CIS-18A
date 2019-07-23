package healthrecord;

// Person class
class Person {
    protected String fName;
    protected String lName;

    Person(String f, String l) {
        fName = f;
        lName = l;
    }

    // Print name
    public void printName() {
        System.out.println(fName + " " + lName);
    }
}

// Patient class
public class Patient extends Person implements DisplayInfo {
    private Drug[] prescriptions;
    private Boolean warning;

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
            for(int i = 0; i < prescriptions.length - 1; i++) {
                System.out.print(prescriptions[i].getName() + ", ");
            }
            System.out.println(prescriptions[prescriptions.length-1].getName());
        }
    }

    // Print interaction warning
    void printWarning() {
        if(prescriptions != null)
            checkInteraction();

        if(warning)
            System.out.println("WARNING: Major interaction detected");
        else
            System.out.println("None detected");
    }

    public void checkInteraction() {
        for(int i=0; i<prescriptions.length; i++) {
            for(int j=0; j<prescriptions[i].getInteractionsSize(); j++) {
                for(int k=0; k<prescriptions.length; k++) {
                    if( prescriptions[i].getInteraction(j).equals(prescriptions[k].getName()) ) {
                        warning = true;
                        return;
                    }
                }
            }
        }
        warning = false;
    }
}
