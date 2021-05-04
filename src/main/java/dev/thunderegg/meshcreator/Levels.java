package dev.thunderegg.meshcreator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import dev.thunderegg.CornerNeighbor;
import dev.thunderegg.Edge;
import dev.thunderegg.EdgeNeighbor;
import dev.thunderegg.Neighbor;
import dev.thunderegg.Orthant;
import dev.thunderegg.Patch;
import dev.thunderegg.Side;

public class Levels {
	public ArrayList<ArrayList<Patch>> levels;
	public Forest forest;

	public Levels(Forest forest) {

		this.forest = forest;
		int max_level = forest.getMaxLevel();
		int curr_level = max_level;
		levels = new ArrayList<ArrayList<Patch>>(max_level + 1);

		levels.add(extractLevel(curr_level, max_level));
		curr_level--;

		// rest of the levels
		while (curr_level >= 0) {
			levels.add(extractLevel(curr_level, max_level));
			curr_level--;
		}

	}

	private void setPatchExcludingNeighbors(Patch patch, Node node, int curr_level, int max_level) {
		patch.id = node.getId();
		if (node.getLevel() < curr_level) {
			patch.parent_id = node.getId();
			patch.parent_rank = 0;
			if (curr_level < max_level) {
				patch.child_ids[0] = node.getId();
				patch.child_ranks[0] = 0;
			}
		} else {
			if (node.hasParent()) {
				patch.parent_id = node.getParentId();
				patch.parent_rank = 0;
				Node parent = forest.getNode(node.getParentId());
				for (Orthant o : Orthant.getValuesForDimension(node.getDimension())) {
					if (node.getId() == parent.getChildId(o)) {
						patch.orth_on_parent = o;
						break;
					}
				}
			}
			if (node.hasChildren()) {
				for (Orthant o : Orthant.getValuesForDimension(node.getDimension())) {
					patch.child_ids[o.getIndex()] = node.getChildId(o);
					patch.child_ranks[o.getIndex()] = 0;
				}
			} else if (curr_level < max_level) {
				patch.child_ids[0] = node.getId();
				patch.child_ranks[0] = 0;
			}
		}
		for (int i = 0; i < node.getDimension(); i++) {
			patch.starts[i] = node.getStart(i);
			patch.lengths[i] = node.getLength(i);
		}
	}

	private ArrayList<Patch> extractLevel(int curr_level, int max_level) {
		ArrayList<Patch> patches = new ArrayList<Patch>();
		Queue<Integer> q2 = new LinkedList<Integer>();
		Set<Integer> visited2 = new HashSet<Integer>();

		Node finest_node = forest.getRootNode();
		while (finest_node.hasChildren() && finest_node.getLevel() < curr_level) {
			finest_node = forest.getNode(finest_node.getChildId(Orthant.SW()));
		}
		q2.add(finest_node.getId());

		while (!q2.isEmpty()) {
			Node curr = forest.getNode(q2.remove());
			visited2.add(curr.getId());

			Patch patch = new Patch(curr.getDimension());
			setPatchExcludingNeighbors(patch, curr, curr_level, max_level);

			Node parent = forest.getNode(curr.getParentId());
			for (Side s : Side.getValuesForDimension(curr.getDimension())) {
				Node neighbor = forest.getNode(curr.getNbrId(s));
				if (!curr.hasNbr(s) && curr.hasParent() && parent.hasNbr(s)) {
					// coarser nbr
					patch.nbrs.add(getCoarserNbr(curr, s));

					int next_id = parent.getNbrId(s);
					if (!q2.contains(next_id) && !visited2.contains(next_id)) {
						q2.add(next_id);
					}
				} else if (curr.getLevel() < curr_level && curr.hasNbr(s) && neighbor.hasChildren()) {
					// finer nbr
					patch.nbrs.add(getFinerNbr(curr, s));

					int next_id = neighbor.getChildId(Orthant.SW());
					if (!q2.contains(next_id) && !visited2.contains(next_id)) {
						q2.add(next_id);
					}
				} else if (curr.hasNbr(s)) {
					// normal nbr
					patch.nbrs.add(getNormalNbr(curr, s));

					int next_id = curr.getNbrId(s);
					if (!q2.contains(next_id) && !visited2.contains(next_id)) {
						q2.add(next_id);
					}
				}
			}
			for (Edge e : Edge.getValuesForDimension(curr.getDimension())) {
				Node neighbor = forest.getNode(curr.getNbrId(e));
				if (edgeIsOnOrthant(e, patch.orth_on_parent) && !curr.hasNbr(e) && curr.hasParent()
						&& parent.hasNbr(e)) {
					// coarser nbr
					patch.edge_nbrs.add(getCoarserNbr(curr, e));

					int next_id = parent.getNbrId(e);
					if (!q2.contains(next_id) && !visited2.contains(next_id)) {
						q2.add(next_id);
					}
				} else if (curr.getLevel() < curr_level && curr.hasNbr(e) && neighbor.hasChildren()) {
					// finer nbr
					patch.edge_nbrs.add(getFinerNbr(curr, e));

					int next_id = neighbor.getChildId(Orthant.SW());
					if (!q2.contains(next_id) && !visited2.contains(next_id)) {
						q2.add(next_id);
					}
				} else if (curr.hasNbr(e)) {
					// normal nbr
					patch.edge_nbrs.add(getNormalNbr(curr, e));

					int next_id = curr.getNbrId(e);
					if (!q2.contains(next_id) && !visited2.contains(next_id)) {
						q2.add(next_id);
					}
				}
			}
			for (Orthant c : Orthant.getValuesForDimension(curr.getDimension())) {
				Node neighbor = forest.getNode(curr.getNbrId(c));
				if (c.equals(patch.orth_on_parent) && !curr.hasNbr(c) && curr.hasParent() && parent.hasNbr(c)) {
					// coarser nbr
					patch.corner_nbrs.add(getCoarserNbr(curr, c));

					int next_id = parent.getNbrId(c);
					if (!q2.contains(next_id) && !visited2.contains(next_id)) {
						q2.add(next_id);
					}
				} else if (curr.getLevel() < curr_level && curr.hasNbr(c) && neighbor.hasChildren()) {
					// finer nbr
					patch.corner_nbrs.add(getFinerNbr(curr, c));

					int next_id = neighbor.getChildId(Orthant.SW());
					if (!q2.contains(next_id) && !visited2.contains(next_id)) {
						q2.add(next_id);
					}
				} else if (curr.hasNbr(c)) {
					// normal nbr
					patch.corner_nbrs.add(getNormalNbr(curr, c));

					int next_id = curr.getNbrId(c);
					if (!q2.contains(next_id) && !visited2.contains(next_id)) {
						q2.add(next_id);
					}
				}
			}
			patches.add(patch);
		}
		return patches;
	}

	private boolean edgeIsOnOrthant(Edge e, Orthant o) {
		Set<Side> o_sides = new HashSet<Side>();
		if (o != null) {
			for (Side s : o.getExteriorSides()) {
				o_sides.add(s);
			}
		}
		Set<Side> e_sides = new HashSet<Side>();
		for (Side s : e.getSides()) {
			e_sides.add(s);
		}
		return o_sides.containsAll(e_sides);
	}

	private Neighbor getNormalNbr(Node node, Side s) {
		int[] ids = new int[1];
		int[] ranks = new int[1];
		ids[0] = node.getNbrId(s);
		ranks[0] = 0;
		return new Neighbor(s, "NORMAL", ids, ranks, null);
	}

	private EdgeNeighbor getNormalNbr(Node node, Edge e) {
		int[] ids = new int[1];
		int[] ranks = new int[1];
		ids[0] = node.getNbrId(e);
		ranks[0] = 0;
		return new EdgeNeighbor(e, "NORMAL", ids, ranks, null);
	}

	private CornerNeighbor getNormalNbr(Node node, Orthant c) {
		int[] ids = new int[1];
		int[] ranks = new int[1];
		ids[0] = node.getNbrId(c);
		ranks[0] = 0;
		return new CornerNeighbor(c, "NORMAL", ids, ranks);
	}

	private Neighbor getFinerNbr(Node node, Side s) {
		int[] ids = new int[Orthant.getNumOrthantsForDimension(node.getDimension()) / 2];
		int[] ranks = new int[ids.length];
		int index = 0;
		Node nbr = forest.getNode(node.getNbrId(s));
		for (Orthant o : Orthant.GetValuesOnSide(node.getDimension(), s.getOpposite())) {
			ids[index] = nbr.getChildId(o);
			ranks[index] = 0;
			index++;
		}
		return new Neighbor(s, "FINE", ids, ranks, null);
	}

	private EdgeNeighbor getFinerNbr(Node node, Edge e) {
		int[] ids = new int[Orthant.getNumOrthantsForDimension(node.getDimension()) / 4];
		int[] ranks = new int[ids.length];
		int index = 0;
		Node nbr = forest.getNode(node.getNbrId(e));
		for (Orthant o : Orthant.GetValuesOnEdge(node.getDimension(), e.getOpposite())) {
			ids[index] = nbr.getChildId(o);
			ranks[index] = 0;
			index++;
		}
		return new EdgeNeighbor(e, "FINE", ids, ranks, null);
	}

	private CornerNeighbor getFinerNbr(Node node, Orthant c) {
		int[] ids = new int[1];
		int[] ranks = new int[ids.length];
		Node nbr = forest.getNode(node.getNbrId(c));
		ids[0] = nbr.getChildId(c.getOpposite());
		ranks[0] = 0;
		return new CornerNeighbor(c, "FINE", ids, ranks);
	}

	private Neighbor getCoarserNbr(Node node, Side s) {
		int[] ids = new int[1];
		int[] ranks = new int[1];
		Node parent = forest.getNode(node.getParentId());
		ids[0] = parent.getNbrId(s);
		ranks[0] = 0;
		Orthant orth_on_coarse = null;
		for (Orthant o : Orthant.GetValuesOnSide(node.getDimension(), s)) {
			if (node.getId() == parent.getChildId(o)) {
				orth_on_coarse = o.collapseOnAxis(s.getAxis());
				break;
			}
		}
		return new Neighbor(s, "COARSE", ids, ranks, orth_on_coarse);
	}

	private EdgeNeighbor getCoarserNbr(Node node, Edge e) {
		int[] ids = new int[1];
		int[] ranks = new int[1];
		Node parent = forest.getNode(node.getParentId());
		ids[0] = parent.getNbrId(e);
		ranks[0] = 0;
		Orthant orth_on_coarse = null;
		Orthant[] orths = Orthant.GetValuesOnEdge(3, e);
		for (int i = 0; i < orths.length; i++) {
			if (node.getId() == parent.getChildId(orths[i])) {
				orth_on_coarse = new Orthant(1, i);
				break;
			}
		}
		return new EdgeNeighbor(e, "COARSE", ids, ranks, orth_on_coarse);

	}

	private CornerNeighbor getCoarserNbr(Node node, Orthant c) {
		int[] ids = new int[1];
		int[] ranks = new int[1];
		Node parent = forest.getNode(node.getParentId());
		ids[0] = parent.getNbrId(c);
		ranks[0] = 0;
		return new CornerNeighbor(c, "COARSE", ids, ranks);
	}

	public ArrayList<Patch> getLevel(int level) {
		return levels.get(level);
	}

	public void changeRankAt(double[] coord, int level, int rank) {
		Patch patch_to_change = null;
		for (Patch p : getLevel(level)) {
			if (p.coordIsInside(coord)) {
				patch_to_change = p;
				break;
			}
		}
		if (patch_to_change != null) {
			patch_to_change.rank = rank;
			for (Patch p : getLevel(level)) {
				p.updateNbrRankFor(patch_to_change.id, rank);
			}
			if (level + 1 < levels.size()) {
				for (Patch p : getLevel(level + 1)) {
					p.updateChildRankFor(patch_to_change.id, rank);
				}
			}
			if (level - 1 >= 0) {
				for (Patch p : getLevel(level - 1)) {
					p.updateParentRankFor(patch_to_change.id, rank);
				}
			}
		}
	}

	public Forest getForest() {
		return forest;
	}

	public int getNumLevels() {
		return levels.size();
	}
}
