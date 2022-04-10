import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;


public class Path {
	Digraph inputGraph;
	ArrayList <Edge> edges = new ArrayList <Edge>();

	Path(File fileStopT, File fileStop, File fileTransfers) throws FileNotFoundException {
		inputGraph=new Digraph(fileStopT, fileStop, fileTransfers);
	}
	
	// returns the total amount of stops
	public int stopsTotal(int S,int E) {
		DijkstraSP path=new DijkstraSP(inputGraph,S,E);
		edges = path.getPath();
		return edges.size();
	}
	
	// return the all of the stops between both stops
	public String[] listStops(int S,int E) {
		
		DijkstraSP path=new DijkstraSP(inputGraph,S,E);
		edges=path.getPath();

		String[] test = {""};
				
		if(edges != null) {
			test=new String[edges.size()];
			for(int i = 0 ; i < edges.size() ; i++) {
				test[i] = (edges.get(i).toString());
			}
		}
		return test;
	}
}