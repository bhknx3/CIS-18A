package healthrecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFile {
    private Drug drugs[];   // Hold drugs

    // Constructor
    public ReadFile() {
        readFileContent();
    }

    // Accessor to drug list
    public Drug[] getDrugList() {
        return drugs;
    }

    // Read in data file 'drugs_data.txt' containing drug information
    // File contains top 50 drugs used and their major interactions with each other
    void readFileContent() {
        try {
            String file = "drugs_data.txt";
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<String> list = new ArrayList<String>();
            int counter = 0;

            // Read in number of drugs in data file
            String line = br.readLine();
            final int SIZE = Integer.parseInt(line);

            // Create an array of drugs/prescriptions
            drugs = new Drug[SIZE];
            for(int i=0; i<SIZE; i++) {
                drugs[i] = new Drug();  // Construct objects
            }

            // Read in file contents
            while((line = br.readLine()) != null) {
                if(!line.equals("")) {
                    // Get drug name and save
                    line = line.trim();
                    drugs[counter].setName(line);   // Set name of drug

                    // Get drug interaction list and save
                    line = br.readLine().trim();
                    list.addAll(Arrays.asList(line.split("\\s*,\\s*")));    // Get list of items separated by comma
                    String[] array = new String[list.size()];   // Construct array of specific size
                    array = list.toArray(array);                // Convert list to array
                    drugs[counter].setInteract(array);          // Set array reference

                    list.clear(); // Clear list to hold next set of interactions
                    counter++;  // Go to next object in drug array
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}