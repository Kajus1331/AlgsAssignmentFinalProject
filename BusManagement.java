import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

class BusManagement {
	static TimeSearch timeTripInfo;
	static TST busStopInfo;
	static Path shortestPathInfo;

    public static void main(String[] args) throws FileNotFoundException {
        initData();
        ui();
    }

    private static void initData() throws FileNotFoundException {	
		// read in data
		timeTripInfo = new TimeSearch("stop_times.txt");

		busStopInfo = new TST();
		busStopInfo.initTst("stops.txt");

		File fileStopT = new File("stop_times.txt");
		File fileStop = new File("stops.txt");
		File fileTransfers = new File("transfers.txt");
			
		try {
			shortestPathInfo = new Path(fileStopT, fileStop, fileTransfers);
        } catch (FileNotFoundException e) {
        }
		
	}

    public static void ui() {
		// generates user interface
		boolean quit = false;
        try (Scanner input = new Scanner(System.in)) {
			while(!quit){
				System.out.println();
				System.out.println("Shortest Path(A)   Bus Stop Search(B)    Arrival Time(C)  Exit(Q)");
				String userInput = input.nextLine();
            	if (userInput.equals("A")){
					boolean finished = false;
					while (!finished){
						System.out.println("Enter first bus stop");
                		String busStop1 = input.nextLine();
                		System.out.println("Enter second bus stop");
                		String busStop2 = input.nextLine();
						if (isValidPath(busStop1, busStop2)) {
							finished = true;
							ShortestPath(busStop1, busStop2);
						}
						else {
							System.out.println("Input needs to be an integer thats not greater than 100000");
						}
					}
            	}
            	else if (userInput.equals("B")){
					boolean finished = false;
					while (!finished){
						System.out.println("Enter Bus Stop");
						String busStop = input.nextLine();
						if (isValidBus(busStop)){
							finished = true;
							SearchStops(busStop);
						}
						else {
							System.out.println("Input needs to be a valid bus stop name");
						}
					}
            	}
            	else if (userInput.equals("C")){
					boolean finished = false;
					while(!finished){
						System.out.println("Enter Arrival Time in hh:mm:ss ");
						String arrivalTime = input.nextLine();
						if (isValidTime(arrivalTime)){
							finished = true;
							SearchTime(arrivalTime);
						}
						else{
							System.out.println("Input needs to be in the form hh:mm:ss");
						}
					}
            	}
				else if (userInput.equals("Q")){
					quit = true;
				}
				else {
					System.out.println("Please enter a valid input");
				}
			}
        }
	}

	// outputs the shortest path to user
	private static void ShortestPath(String stop1, String stop2) {
		
		int busStop1, busStop2, stopCnt;
		String busStops[];
		
		String stopList[] = {};
			
		busStop1 = Integer.parseInt(stop1);
		busStop2 = Integer.parseInt(stop2);
		
		if((stopCnt = shortestPathInfo.stopsTotal(busStop1, busStop2)) > 0) {
			stopList = shortestPathInfo.listStops(busStop1, busStop2);
		}

        int stopNumber = 1;
		System.out.println();
        System.out.println("STOP NUMBER    From    To    Cost");
		System.out.println();
        for (int i = stopCnt; i > 0; i--){
            String output = stopList[i-1];
            busStops = output.split(",");	
            System.out.println("----" + stopNumber + "----------" + busStops[0] 
                + "---" + busStops[1] + "----" + busStops[2]);
            stopNumber++;
        }
	}

	// outputs the bus stops to the user
    private static void SearchStops(String stopInput) {
		String[] busStops;
		ArrayList<String> allStops=busStopInfo.search(stopInput.toUpperCase());
		System.out.println();
		System.out.println("StopID   Stop Code          Stop Name                      Stop Desc        									 ");
		System.out.println();

        for (int i = 0; i < allStops.size(); i++){
            busStops = allStops.get(i).split(" - ");
            System.out.println(busStops[1] + "----" + busStops[2] + "----" + busStops[0] + "----" + 
                busStops[3] + "----" + busStops[4] + "----" + busStops[5] + "----" + busStops[6]);
        }
	}


	// outputs the times for busses to the user
	private static void SearchTime(String timeInput) {
		ArrayList<String> allStops = timeTripInfo.Search(timeInput);
		System.out.println();
		System.out.println("Trip ID    Arrival     Departure   stopID StopSeq         Shape dist travelled");
		System.out.println();
        for (int i = 0; i < allStops.size(); i++){
			System.out.println(allStops.get(0).replace(",", "----"));
        }
	}

	public static boolean isValidPath(String str1, String str2){
		if (!isNumeric(str1) || !isNumeric(str2)){
			return false;
		}

		int a = Integer.parseInt(str1);
		int b = Integer.parseInt(str2);

		if (a > 100000 || b > 100000){
			return false;
		}

		if (a < 0 || b < 0){
			return false;
		}
		return true;
		
	}

	public static boolean isNumeric(String str){
		try{
			Integer.parseInt(str);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}

	public static boolean isValidBus(String str1){
		if (isNumeric(str1)){
			return false;
		}

		if (str1.length() > 45){
			return false;
		}

		return true;
		
	}

	public static boolean isValidTime(String str1){

		char[] ch = str1.toCharArray();
		int count = 0;
		for (char a : ch) {
			if (a == ':'){
				count++;
			}
		}
		if (count != 2){
			return false;
		}

		String hour = "";
		String min = "";
		String sec = "";

		String[] split = str1.split(":"); 
		hour = split[0];
		min = split[1];
		sec = split[2];
		if (!isNumeric(hour) || !isNumeric(min) || !isNumeric(sec) ){
			return false;
		}

		int hr = Integer.parseInt(hour);
		int mn = Integer.parseInt(min);
		int sc = Integer.parseInt(sec);

		if ((hr > 23 || hr < 0) || (mn > 59 || mn < 0) || (sc > 59 || sc < 0)){
			return false;
		}

		return true;
	}
}