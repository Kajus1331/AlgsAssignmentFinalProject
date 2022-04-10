import java.util.ArrayList;

public class DijkstraSP {

    ArrayList<Double> distTo = new ArrayList<>();
    ArrayList<Edge> edgeTo = new ArrayList<>();
    ArrayList <Boolean> checkedNodes = new ArrayList<>();

    int startNode = 0, endNode = 0, startingNodeIndex = 0, endingNodeIndex = 0;

    Digraph diGraph;

    /**
     * Computes a shortest-paths tree from the source vertex to the ending vertex in 
     * the Digraph
     * 
     * @param graph the Digraph
     * @param S the starting node
     * @param E the ending node
     */
    DijkstraSP(Digraph graph, int S, int E){
        startNode = S;
        endNode = E;
        diGraph = graph;

        // set list to default 
        for (int i=0;i<diGraph.nodes.length;i++){
            distTo.add(9999999.0);
            edgeTo.add(null);
            checkedNodes.add(false);
        } 

        distTo.set(startNode, 0.0);


        for (int i=0;i<diGraph.nodes.length;i++){
            double defaultDist = 9999999.0;
            int vertex = 0;

            for (int j = 0; j < diGraph.nodes.length; j++){
                if (!checkedNodes.get(j) && (distTo.get(j) < defaultDist)){
                    vertex = j;
                    defaultDist = distTo.get(j);
                }
            }
            relax(vertex,diGraph);
            checkedNodes.set(vertex,true);
        }
        startingNodeIndex = diGraph.getNodeIndex(startNode);
        endingNodeIndex = diGraph.getNodeIndex(endNode);
    }

    // relax the vertex
    private void relax(int vertex, Digraph graph){
        for (int i = 0; i < graph.nodes[vertex].nodeEdges.size();i++){    		
            Edge edge = graph.nodes[vertex].nodeEdges.get(i);
            int id = diGraph.getNodeIndex(edge.to.name);

            if (distTo.get(id) > distTo.get(vertex) + edge.weight){
                distTo.set(id, distTo.get(vertex) + edge.weight);
                edgeTo.set(id, edge);
            }
        }
    }

    // returns the path that is the shortes between the two nodes
    public ArrayList<Edge> getPath(){
        ArrayList<Edge> pathEdges = new ArrayList<Edge>();

        if ((startingNodeIndex==0)||(endingNodeIndex==0)) {
            return null;
        }

        Edge startEdge = edgeTo.get(endingNodeIndex);

        while (true){
            pathEdges.add(startEdge);
            if (startEdge.from.name==startNode){
                break;
            }
            else{
                if (edgeTo.get(diGraph.getNodeIndex(startEdge.from.name))!=null){
                    startEdge = edgeTo.get(diGraph.getNodeIndex(startEdge.from.name));
                }
                else{
                    return null;
                }
            }
        }
        return pathEdges; 

    }

}
