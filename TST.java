import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** class Node **/
class tstNode {
	
    char data;
    boolean isEnd;
    tstNode left, middle, right; 

    /** Constructor **/
    public tstNode(char data)
    {
        this.data = data;
        this.isEnd = false;
        this.left = null;
        this.middle = null;
        this.right = null;

    }        

}

public class TST {
    
    public static tstNode root;
    public ArrayList<String> al; //a list holding the search result, used in traverse function

    /** Constructor **/
    public TST()
    {
        root = null;
    }
    
    /** function to insert for a word **/
    public static void insert(String word)
    {
        root = insert(root, word.toCharArray(), 0);
    }

    /** function to insert for a word **/
    public static tstNode insert(tstNode r, char[] word, int ptr)
    {
        if (r == null)  r = new tstNode(word[ptr]);

        if (word[ptr] < r.data)   
    	{
        	r.left = insert(r.left, word, ptr);
    	}
        else if (word[ptr] > r.data)
        {
            r.right = insert(r.right, word, ptr);
        }
        else
        {
            if (ptr + 1 < word.length)
                r.middle = insert(r.middle, word, ptr + 1);
            else
                r.isEnd = true;
        }
        return r;

    }


    /** function to search for a word **/
    public ArrayList<String> search(String word)
    {
        return search(root, word.toCharArray(), 0);
    }

    /** function to search for a word **/
    public ArrayList<String> search(tstNode r, char[] word, int ptr)
    {
        if (r == null) return new ArrayList<String>();

        if (word[ptr] < r.data)
        {
            return search(r.left, word, ptr);
        }
        else if (word[ptr] > r.data)
        {
            return search(r.right, word, ptr);
        }
        else
        {
            if (r.isEnd && ptr == word.length - 1)
            {
            	al = new ArrayList<String>();
            	String s = new String(word);
            	al.add(s);
            	return al;
            }
            else if (ptr == word.length - 1)
            {
            	al = new ArrayList<String>();
            	String s = new String(word);
            	traverse(r.middle,s);
            	return al;
            }
            else
            {
                return search(r.middle, word, ptr + 1);
            }
        }        
    }
    

    /** function to traverse tree **/
    public void traverse(tstNode r, String str)
    {
        if (r != null)
        {
            traverse(r.left, str);

            str = str + r.data;

            if (r.isEnd) al.add(str);

            traverse(r.middle, str);

            str = str.substring(0, str.length() - 1);

            traverse(r.right, str);
            
        }
    }    
  
    public void initTst(String filename) throws FileNotFoundException {

    	String [] keywords= {"WB","NB","SB","EB","FLAGSTOP"};
        
        //Get the Data && insert to the tree
        File file = new File(filename);

		try (Scanner busStops = new Scanner(file)) {
            busStops.nextLine();
            while(busStops.hasNextLine()) {       
                	
                // splits every line from the .txt
            	String newLine = busStops.nextLine(); 
                String[] newLineSplit = newLine.split(","); 
            	String[] stopNameSplit = newLineSplit[2].split(" ");

                // if the stopname contains a key word it is moved to the
                // back of the stop nmae
            	if(Arrays.asList(keywords).contains(stopNameSplit[0])){
                    String[] test = new String[stopNameSplit.length];
                    for (int i = 0; i < stopNameSplit.length-1; i++){
                        test[i] = stopNameSplit[i+1];
                    }
                    test[stopNameSplit.length-1] = stopNameSplit[0];
                    String convert = String.join(" ", test);
                    newLineSplit[2] = convert;
            	}	

                // organises the string for output
                String organisedData = newLineSplit[0];
            	newLineSplit[0] = newLineSplit[2];
            	newLineSplit[2] = newLineSplit[1];
            	newLineSplit[1] = organisedData;
            	organisedData = String.join(" - ", newLineSplit);
                insert(organisedData); 
            }
        }
    }
}
