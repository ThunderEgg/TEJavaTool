package dev.thunderegg;

/**
 * Represents the neighbor of a patch
 * 
 * @author Scott Aiton
 */
public class CornerNeighbor {
	/**
	 * The corner of the patch that the neighbor is on
	 */
	public Orthant corner;
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
	 * Create new Neighbor
	 * 
	 * @param corner the corner that the neighbor is on
	 * @param type   the type of neighbor can be "coarse", "fine", or "normal"
	 * @param ids    the ids of the neighbors
	 * @param ranks  the ranks of the neighbors
	 */
	public CornerNeighbor(Orthant corner, String type, int[] ids, int[] ranks) {
		this.corner = corner;
		this.type = type;
		this.ids = ids;
		this.ranks = ranks;
	}
}
