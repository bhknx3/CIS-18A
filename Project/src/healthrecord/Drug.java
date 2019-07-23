package healthrecord;

public class Drug implements DisplayInfo {
    private String name;
    private String[] interactions;

    public Drug() {
        name = "";
        interactions = null;
    }

    Drug(String n, String[] i) {
        name = n;
        interactions = i;
    }

    public void setName(String n) {
        name = n;
    }

    public void setInteract(String[] a) {
        interactions = a;
    }

    public String getInteraction(int e) {
        if(e >= 0 && e < interactions.length)
            return interactions[e];
        return null;
    }

    public int getInteractionsSize() {
        return interactions.length;
    }

    public String getName() {
        return name;
    }

    public void printInteract() {
        System.out.println("Major interactions with:");
        for(int i=0; i<interactions.length; i++) {
            System.out.print(interactions[i] + " ");
        }
    }

    @Override
    public void showInfo() {
        System.out.println(name);
        printInteract();
    }
}