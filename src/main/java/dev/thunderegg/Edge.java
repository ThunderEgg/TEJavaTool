package dev.thunderegg;

/**
 * Represents the edges of a cube
 */
public class Edge {

    /**
     * The value of the enum
     */
    private int val;

    /**
     * Create edge with value
     * 
     * @param i the value
     */
    public Edge(int i) {
        val = i;
    }

    /**
     * Bottom South edge
     * 
     * @return the edge
     */
    public static Edge BS() {
        return new Edge(0);
    }

    /**
     * Top North edge
     * 
     * @return the edge
     */
    public static Edge TN() {
        return new Edge(1);
    }

    /**
     * Bottom North edge
     * 
     * @return the edge
     */
    public static Edge BN() {
        return new Edge(2);
    }

    /**
     * Top South edge
     * 
     * @return the edge
     */
    public static Edge TS() {
        return new Edge(3);
    }

    /**
     * Bottom West edge
     * 
     * @return the edge
     */
    public static Edge BW() {
        return new Edge(4);
    }

    /**
     * Top East edge
     * 
     * @return the edge
     */
    public static Edge TE() {
        return new Edge(5);
    }

    /**
     * Bottom East edge
     * 
     * @return the edge
     */
    public static Edge BE() {
        return new Edge(6);
    }

    /**
     * Top West edge
     * 
     * @return the edge
     */
    public static Edge TW() {
        return new Edge(7);
    }

    /**
     * South West edge
     * 
     * @return the edge
     */
    public static Edge SW() {
        return new Edge(8);
    }

    /**
     * North East edge
     * 
     * @return the edge
     */
    public static Edge NE() {
        return new Edge(9);
    }

    /**
     * South East edge
     * 
     * @return the edge
     */
    public static Edge SE() {
        return new Edge(10);
    }

    /**
     * North West edge
     * 
     * @return the edge
     */
    public static Edge NW() {
        return new Edge(11);
    }

    /**
     * Get the number of edges for a dimension
     * 
     * @param dimension the dimension
     * @return the number of edges
     */
    public static int getNumEdgesForDimension(int dimension) {
        return dimension == 3 ? 12 : 0;
    }

    /**
     * Get all the values of a dimension
     * 
     * @param dimension the dimension
     * @return the values
     */
    public static Edge[] getValuesForDimension(int dimension) {
        if (dimension == 3) {
            Edge[] edges = new Edge[12];
            for (int i = 0; i < 12; i++) {
                edges[i] = new Edge(i);
            }
            return edges;
        } else {
            return new Edge[0];
        }
    }

    public String toString() {
        String str = new String();
        if (this.equals(Edge.BS())) {
            str = "BS";
        } else if (this.equals(Edge.TN())) {
            str = "TN";
        } else if (this.equals(Edge.BN())) {
            str = "BN";
        } else if (this.equals(Edge.TS())) {
            str = "TS";
        } else if (this.equals(Edge.BW())) {
            str = "BW";
        } else if (this.equals(Edge.TE())) {
            str = "TE";
        } else if (this.equals(Edge.BE())) {
            str = "BE";
        } else if (this.equals(Edge.TW())) {
            str = "TW";
        } else if (this.equals(Edge.SW())) {
            str = "SW";
        } else if (this.equals(Edge.NE())) {
            str = "NE";
        } else if (this.equals(Edge.SE())) {
            str = "SE";
        } else if (this.equals(Edge.NW())) {
            str = "NW";
        } else {
            str = "INVALID VALUE: " + val;
        }
        return str;
    }

    /**
     * Get Edge from string
     * 
     * @param string the string
     * @return the edge
     */
    public static Edge fromString(String string) {
        Edge edge = null;
        if (string.equals("BS")) {
            edge = Edge.BS();
        } else if (string.equals("TN")) {
            edge = Edge.TN();
        } else if (string.equals("BN")) {
            edge = Edge.BN();
        } else if (string.equals("TS")) {
            edge = Edge.TS();
        } else if (string.equals("BW")) {
            edge = Edge.BW();
        } else if (string.equals("TE")) {
            edge = Edge.TE();
        } else if (string.equals("BE")) {
            edge = Edge.BE();
        } else if (string.equals("TW")) {
            edge = Edge.TW();
        } else if (string.equals("SW")) {
            edge = Edge.SW();
        } else if (string.equals("NE")) {
            edge = Edge.NE();
        } else if (string.equals("SE")) {
            edge = Edge.SE();
        } else if (string.equals("NW")) {
            edge = Edge.NW();
        } else {
            throw new IllegalArgumentException();
        }
        return edge;
    }

    /**
     * Get the edge on the opposite side
     * 
     * @return the edge on the opposite side
     */
    public Edge getOpposite() {
        return new Edge(val ^ 0b1);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Edge)) {
            return false;
        }

        Edge s = (Edge) o;

        return s.val == val;
    }

    /**
     * Get the index value for this edge
     * 
     * @return the index value
     */
    public int getIndex() {
        return val;
    }
}
