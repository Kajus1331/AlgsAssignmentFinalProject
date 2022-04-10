import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TimeSearch{

    public ArrayList<String> stoptData = new ArrayList<>();

    // takes in the file and stores the relevant data
    TimeSearch(String filename) throws FileNotFoundException{
        File file = new File(filename);
	
        if (filename != null) {
            try (Scanner newLine = new Scanner(file)) {
                newLine.nextLine();

                while (newLine.hasNextLine()) {
                    String dataLine = newLine.nextLine();
                    stoptData.add(dataLine);
                }
            }
        } 
    }

    // returns all of the stops with the the same time as the input
    public ArrayList<String> Search(String userTime){
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < stoptData.size(); i++){
            String toString = stoptData.get(i);
            toString = toString.replace("\\s", "");
            String[] splitString = toString.split(",");
            String time = splitString[1];
            if (time.equals(userTime)){
                results.add(toString);
            }
        }
        return results;
    }
}
