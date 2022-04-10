class Edge {
	Node from, to;
	double weight;
	
	Edge(Node from, Node to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	// returns string
	public String toString() {
		return this.from.name+","+this.to.name+","+this.weight;
    }
}