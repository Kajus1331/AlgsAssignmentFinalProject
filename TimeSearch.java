import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class TimeSearch{

    public ArrayList<String> stoptData = new ArrayList<>();

    TimeSearch(String filename) throws FileNotFoundException{
        File file = new File(filename);
	
        if (filename != null) {
            Scanner newLine = new Scanner(file);
            newLine.nextLine();

            while (newLine.hasNextLine()) {
                String dataLine = newLine.nextLine();
                stoptData.add(dataLine);
            }
        } 
    }

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
        System.out.println("HELLO");
        return results;
    }

    


    
}
