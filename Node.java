import java.util.ArrayList;

class Node {
	ArrayList<Edge> nodeEdges = new ArrayList<>();
	int name;

	Node(int name) {
		this.name = name;
	}

	// adds an edge
	void addEdge(Edge edge) {
		nodeEdges.add(edge);
	}

	// sorts
	public int compareTo(Node temp) {
		return this.name - temp.name;
	}
}