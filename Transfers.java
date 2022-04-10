public class Transfers
{
    int from, to, transType, minTime;

    Transfers (int from, int to, int transType, int minTime){
        this.from = from;
        this.to = to;
        this.transType = transType;
        this.minTime = minTime;
    }

    public int compareFrom(Transfers transfers) {
        return this.from - transfers.from;
    }
    
    public int compareTo(Transfers transfers) { 
    	return this.to - transfers.to; }
    }
