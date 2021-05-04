package dev.thunderegg;

/**
 * Represents the neighbor of a patch
 * 
 * @author Scott Aiton
 */
public class EdgeNeighbor {
	/**
	 * The edge of the patch that the neighbor is on
	 */
	public Edge edge;
	/**
	 * The type of neighbor
	 */
	public String type;
	/**
	 * The ids of the neighboring patches
	 */
	public int[] ids;
	/**
	 * The ranks of the neighboring patches
	 */
	public int[] ranks;
	/**
	 * The orthant of the neighboring patch
	 */
	public Orthant orth_on_coarse;

	/**
	 * Create new Neighbor
	 * 
	 * @param edge           the edge that the neighbor is on
	 * @param type           the type of neighbor can be "coarse", "fine", or
	 *                       "normal"
	 * @param ids            the ids of the neighbors
	 * @param ranks          the ranks of the neighbors
	 * @param orth_on_coarse the orthant on the neighbor if "coarse", null otherwise
	 */
	public EdgeNeighbor(Edge edge, String type, int[] ids, int[] ranks, Orthant orth_on_coarse) {
		this.edge = edge;
		this.type = type;
		this.ids = ids;
		this.ranks = ranks;
		this.orth_on_coarse = orth_on_coarse;
	}
}
