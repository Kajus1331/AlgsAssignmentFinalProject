import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;

public class Digraph{
	Node[] nodes;
	ArrayList<Transfers> transfersTimes = new ArrayList<Transfers>();
	ArrayList<Edge> edges = new ArrayList<>();

    /**
     * 
     * @param stop_times File containing the stop_times.txt
     * @param stops File containing the stops.txt
     * @param transfers File containing the transfers.txt
     * @throws FileNotFoundException
     */
    Digraph(File stop_times, File stops, File transfers) throws FileNotFoundException{

        ArrayList<Integer> busStopId = new ArrayList<>();

        Scanner stopTimes = new Scanner(stop_times);
        Scanner busStops = new Scanner(stops);
        Scanner busTransfers = new Scanner(transfers);

        boolean quit = false;

        stopTimes.nextLine();
        busStops.nextLine();
        busTransfers.nextLine();

        int curTrip = Integer.MAX_VALUE;
        int curStop = Integer.MAX_VALUE;
        int lastTrip = Integer.MAX_VALUE;
        int lastStop = Integer.MAX_VALUE;

        while(stopTimes.hasNextLine()){
            // formatting each line of the input file
            String newLine = stopTimes.nextLine();
            newLine = newLine.trim();
            String[] splitLine = newLine.split(",");

            lastTrip = curTrip;
			lastStop = curStop;
            curTrip = Integer.parseInt(splitLine[0]);
            curStop = Integer.parseInt(splitLine[3]);

            if (curTrip==lastTrip) transfersTimes.add(new Transfers(lastStop, curStop,1,0));
        }

        while(busStops.hasNextLine()){
            // formatting each line of the input file
            String newLine = busStops.nextLine();
            newLine = newLine.trim();
            String[] splitLine = newLine.split(",");
            int ID = Integer.parseInt(splitLine[0]);

            busStopId.add(ID);
        }

        nodes = new Node[busStopId.size()];

		for (int i = 0; i < busStopId.size(); i++) {
			nodes[i] = new Node(i);
        	nodes[i].name = busStopId.get(i);
		}

        Arrays.sort(nodes, Node::compareTo);

        while(busTransfers.hasNextLine()){
            String newLine = busTransfers.nextLine();
            newLine = newLine.trim();
            String[] splitLine = newLine.split(",");
            
            int lineLength = splitLine.length;
            if (lineLength == 3){
                transfersTimes.add(new Transfers(Integer.parseInt(splitLine[0]),
						Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2]),0));
            }
            else {
                transfersTimes.add(new Transfers(Integer.parseInt(splitLine[0]),
						Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2]),
                        Integer.parseInt(splitLine[3])));
            }
        }

        stopTimes.close();
        busStops.close();
        busTransfers.close();

        Collections.sort(transfersTimes, Transfers::compareTo);
		Collections.sort(transfersTimes, Transfers::compareFrom);

        int transferIndex = 0;
        for (int i = 0; i < nodes.length; i++){
            if (quit) break;
            else {
                lastStop = Integer.MAX_VALUE;
                boolean exit = false;

                while (!exit){
                    if (transferIndex >= transfersTimes.size()){
                        quit = true;
                        break;
                    }

                    if ((transfersTimes.get(transferIndex).from == nodes[i].name)) {
                        if (transfersTimes.get(transferIndex).to != lastStop){
                            float transferWeight = Integer.MAX_VALUE;
                            // adds the weights that are given 
                            switch (transfersTimes.get(transferIndex).transType){
                                case 0:
                                    transferWeight = 2.0f;
                                    break;
                                case 1:
                                    transferWeight = 1.0f;
                                    break;
                                case 2:
                                    transferWeight = (transfersTimes.get(transferIndex).minTime / 100);  
                                default:
                                    break;      
                            }

							for (int k = 0; k < nodes.length; k++) {
								if (nodes[k].name == transfersTimes.get(transferIndex).to) {
                                    Edge newEdge = new Edge(nodes[i], nodes[k], transferWeight);
							        nodes[i].addEdge(newEdge);
							        edges.add(newEdge);
                                    lastStop = transfersTimes.get(transferIndex).to;
								}
							}
                        }
                        transferIndex++;
                    } else if (transfersTimes.get(transferIndex).from < nodes[i].name){
                        transferIndex++;
                    } else if (transfersTimes.get(transferIndex).from > nodes[i].name) {
						exit = true;
					}
                }
            }
        }
    }

    public int getNodeIndex(int label)
	{
        for (int i = 0; i < this.nodes.length; i++){
            if (this.nodes[i].name == label) return i;
        }
        return -1;
	}
}
