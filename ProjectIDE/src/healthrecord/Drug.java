package healthrecord;

public class Drug implements DisplayInfo {
    private String name;            // Name of drug
    private String[] interactions;  // Array of other drugs that majorly interact negatively

    // Constructor
    public Drug() {
        name = "";
        interactions = null;
    }

    // Set drug name
    void setName(String n) {
        name = n;
    }

    // Set interactions list
    void setInteract(String[] a) {
        interactions = a;
    }

    // Return a specific interaction drug based on the array element
    String getInteraction(int e) {
        if(e >= 0 && e < interactions.length)
            return interactions[e];
        return null;
    }

    // Get size of interaction array
    int getInteractionsSize() {
        return interactions.length;
    }

    // Name accessor
    public String getName() {
        return name;
    }

    // Print all drugs in interactions list
    void printInteract() {
        System.out.print("Major interactions with: ");
        for(int i=0; i<interactions.length-1; i++) {
            System.out.print(interactions[i] + ", ");
        }
        System.out.println(interactions[interactions.length-1]);
    }

    // To display all drug information
    @Override
    public void showInfo() {
        System.out.println(name);
        printInteract();
    }
}
