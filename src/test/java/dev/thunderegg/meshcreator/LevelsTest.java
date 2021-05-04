package dev.thunderegg.meshcreator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dev.thunderegg.CornerNeighbor;
import dev.thunderegg.Edge;
import dev.thunderegg.EdgeNeighbor;
import dev.thunderegg.Neighbor;
import dev.thunderegg.Orthant;
import dev.thunderegg.Patch;
import dev.thunderegg.Side;

class LevelsTest {

	@Test
	void TestSinglePatch2d() {
		Forest forest = new Forest(2);
		Levels levels = new Levels(forest);
		assertEquals(1, levels.getNumLevels());
		assertEquals(1, levels.getLevel(0).size());
		assertEquals(forest, levels.getForest());
		// check the patch
		Patch p = levels.getLevel(0).get(0);
		assertTrue(p.nbrs.isEmpty());
		Node node = forest.getNode(0);
		assertEquals(node.getId(), p.id);
		assertEquals(0, p.rank);
		assertEquals(-1, p.parent_id);
		assertEquals(-1, p.parent_rank);
		assertEquals(4, p.child_ids.length);
		assertEquals(4, p.child_ranks.length);
		for (int i = 0; i < 4; i++) {
			assertEquals(-1, p.child_ids[0]);
			assertEquals(-1, p.child_ranks[0]);
		}
		assertEquals(2, p.lengths.length);
		assertEquals(2, p.starts.length);
		for (int i = 0; i < 2; i++) {
			assertEquals(1, p.lengths[0]);
			assertEquals(0, p.starts[0]);
		}
		assertEquals(0, p.nbrs.size());
	}

	@Test
	void TestSinglePatch3d() {
		Forest forest = new Forest(3);
		Levels levels = new Levels(forest);
		assertEquals(1, levels.getNumLevels());
		assertEquals(1, levels.getLevel(0).size());
		// check the patch
		Patch p = levels.getLevel(0).get(0);
		assertTrue(p.nbrs.isEmpty());
		Node node = forest.getNode(0);
		assertEquals(node.getId(), p.id);
		assertEquals(0, p.rank);
		assertEquals(-1, p.parent_id);
		assertEquals(-1, p.parent_rank);
		assertEquals(8, p.child_ids.length);
		assertEquals(8, p.child_ranks.length);
		for (int i = 0; i < 8; i++) {
			assertEquals(-1, p.child_ids[i]);
			assertEquals(-1, p.child_ranks[i]);
		}
		assertEquals(3, p.lengths.length);
		assertEquals(3, p.starts.length);
		for (int i = 0; i < 3; i++) {
			assertEquals(1, p.lengths[i]);
			assertEquals(0, p.starts[i]);
		}
		assertEquals(0, p.nbrs.size());
	}

	@Test
	void TestQuadUniform2d() {
		Forest forest = new Forest(2);
		double[] coord = { 0.5, 0.5 };
		forest.refineAt(coord);
		Levels levels = new Levels(forest);
		assertEquals(2, levels.getNumLevels());
		// check coarser level
		{
			assertEquals(1, levels.getLevel(1).size());
			// check the coarser patch
			Patch p = levels.getLevel(1).get(0);
			assertTrue(p.nbrs.isEmpty());
			Node node = forest.getNode(0);
			assertEquals(node.getId(), p.id);
			assertEquals(0, p.rank);
			assertEquals(node.getParentId(), p.parent_id);
			assertEquals(-1, p.parent_rank);
			assertEquals(4, p.child_ids.length);
			assertEquals(4, p.child_ranks.length);
			for (Orthant o : Orthant.getValuesForDimension(2)) {
				assertEquals(node.getChildId(o), p.child_ids[o.getIndex()]);
				assertEquals(0, p.child_ranks[o.getIndex()]);
			}
			assertEquals(2, p.lengths.length);
			assertEquals(2, p.starts.length);
			for (int i = 0; i < 2; i++) {
				assertEquals(1, p.lengths[i]);
				assertEquals(0, p.starts[i]);
			}
			assertEquals(0, p.nbrs.size());
			assertEquals(0, p.edge_nbrs.size());
			assertEquals(0, p.corner_nbrs.size());
		}
		// check finer level
		{
			assertEquals(4, levels.getLevel(0).size());
			Patch parent = levels.getLevel(1).get(0);
			// check the southwest patch
			{
				Patch p = getChildPatch(levels, 0, parent, Orthant.SW());
				Node node = forest.getNode(p.id);
				assertEquals(node.getId(), p.id);
				assertEquals(0, p.rank);
				assertEquals(node.getParentId(), p.parent_id);
				assertEquals(0, p.parent_rank);
				assertEquals(4, p.child_ids.length);
				assertEquals(4, p.child_ranks.length);
				for (Orthant o : Orthant.getValuesForDimension(2)) {
					assertEquals(node.getChildId(o), p.child_ids[o.getIndex()]);
					assertEquals(-1, p.child_ranks[o.getIndex()]);
				}
				assertEquals(2, p.lengths.length);
				assertEquals(2, p.starts.length);
				for (int i = 0; i < 2; i++) {
					assertEquals(node.getLength(i), p.lengths[i]);
					assertEquals(node.getStart(i), p.starts[i]);
				}

				assertEquals(2, p.nbrs.size());

				Neighbor east = getNeighbor(p, Side.EAST());
				assertEquals("NORMAL", east.type);
				assertEquals(1, east.ids.length);
				assertEquals(node.getNbrId(Side.EAST()), east.ids[0]);
				assertEquals(1, east.ranks.length);
				assertEquals(0, east.ranks[0]);

				Neighbor north = getNeighbor(p, Side.NORTH());
				assertEquals("NORMAL", north.type);
				assertEquals(1, north.ids.length);
				assertEquals(node.getNbrId(Side.NORTH()), north.ids[0]);
				assertEquals(1, north.ranks.length);
				assertEquals(0, north.ranks[0]);

				assertEquals(0, p.edge_nbrs.size());

				assertEquals(1, p.corner_nbrs.size());

				CornerNeighbor ne = getNeighbor(p, Orthant.NE());
				assertEquals("NORMAL", ne.type);
				assertEquals(1, ne.ids.length);
				assertEquals(node.getNbrId(Orthant.NE()), ne.ids[0]);
				assertEquals(1, ne.ranks.length);
				assertEquals(0, ne.ranks[0]);

			}
		}
	}

	@Test
	void TestOctUniform3d() {
		Forest forest = new Forest(3);
		double[] coord = { 0.5, 0.5, 0.5 };
		forest.refineAt(coord);
		Levels levels = new Levels(forest);
		assertEquals(2, levels.getNumLevels());
		// check coarser level
		{
			assertEquals(1, levels.getLevel(1).size());
			// check the coarser patch
			Patch p = levels.getLevel(1).get(0);
			assertTrue(p.nbrs.isEmpty());
			Node node = forest.getNode(0);
			assertEquals(node.getId(), p.id);
			assertEquals(0, p.rank);
			assertEquals(node.getParentId(), p.parent_id);
			assertEquals(-1, p.parent_rank);
			assertEquals(8, p.child_ids.length);
			assertEquals(8, p.child_ranks.length);
			for (Orthant o : Orthant.getValuesForDimension(3)) {
				assertEquals(node.getChildId(o), p.child_ids[o.getIndex()]);
				assertEquals(0, p.child_ranks[o.getIndex()]);
			}
			assertEquals(3, p.lengths.length);
			assertEquals(3, p.starts.length);
			for (int i = 0; i < 3; i++) {
				assertEquals(1, p.lengths[i]);
				assertEquals(0, p.starts[i]);
			}
			assertEquals(0, p.nbrs.size());
			assertEquals(0, p.edge_nbrs.size());
			assertEquals(0, p.corner_nbrs.size());
		}
		// check finer level
		{
			assertEquals(8, levels.getLevel(0).size());
			Patch parent = levels.getLevel(1).get(0);
			// check the bottom-south-west patch
			{
				Patch p = getChildPatch(levels, 0, parent, Orthant.BSW());
				Node node = forest.getNode(p.id);
				assertEquals(node.getId(), p.id);
				assertEquals(0, p.rank);
				assertEquals(node.getParentId(), p.parent_id);
				assertEquals(0, p.parent_rank);
				assertEquals(8, p.child_ids.length);
				assertEquals(8, p.child_ranks.length);
				for (Orthant o : Orthant.getValuesForDimension(3)) {
					assertEquals(node.getChildId(o), p.child_ids[o.getIndex()]);
					assertEquals(-1, p.child_ranks[o.getIndex()]);
				}
				assertEquals(3, p.lengths.length);
				assertEquals(3, p.starts.length);
				for (int i = 0; i < 3; i++) {
					assertEquals(node.getLength(i), p.lengths[i]);
					assertEquals(node.getStart(i), p.starts[i]);
				}

				assertEquals(3, p.nbrs.size());

				Neighbor east = getNeighbor(p, Side.EAST());
				assertEquals("NORMAL", east.type);
				assertEquals(1, east.ids.length);
				assertEquals(node.getNbrId(Side.EAST()), east.ids[0]);
				assertEquals(1, east.ranks.length);
				assertEquals(0, east.ranks[0]);

				Neighbor north = getNeighbor(p, Side.NORTH());
				assertEquals("NORMAL", north.type);
				assertEquals(1, north.ids.length);
				assertEquals(node.getNbrId(Side.NORTH()), north.ids[0]);
				assertEquals(1, north.ranks.length);
				assertEquals(0, north.ranks[0]);

				Neighbor top = getNeighbor(p, Side.TOP());
				assertEquals("NORMAL", top.type);
				assertEquals(1, top.ids.length);
				assertEquals(node.getNbrId(Side.TOP()), top.ids[0]);
				assertEquals(1, top.ranks.length);
				assertEquals(0, top.ranks[0]);

				assertEquals(3, p.edge_nbrs.size());

				EdgeNeighbor tn = getNeighbor(p, Edge.TN());
				assertEquals("NORMAL", tn.type);
				assertEquals(1, tn.ids.length);
				assertEquals(node.getNbrId(Edge.TN()), tn.ids[0]);
				assertEquals(1, tn.ranks.length);
				assertEquals(0, tn.ranks[0]);

				EdgeNeighbor te = getNeighbor(p, Edge.TE());
				assertEquals("NORMAL", te.type);
				assertEquals(1, te.ids.length);
				assertEquals(node.getNbrId(Edge.TE()), te.ids[0]);
				assertEquals(1, te.ranks.length);
				assertEquals(0, te.ranks[0]);

				EdgeNeighbor ne = getNeighbor(p, Edge.NE());
				assertEquals("NORMAL", ne.type);
				assertEquals(1, ne.ids.length);
				assertEquals(node.getNbrId(Edge.NE()), ne.ids[0]);
				assertEquals(1, ne.ranks.length);
				assertEquals(0, ne.ranks[0]);

				assertEquals(1, p.corner_nbrs.size());

				CornerNeighbor tne = getNeighbor(p, Orthant.TNE());
				assertEquals("NORMAL", tne.type);
				assertEquals(1, tne.ids.length);
				assertEquals(node.getNbrId(Orthant.TNE()), tne.ids[0]);
				assertEquals(1, tne.ranks.length);
				assertEquals(0, tne.ranks[0]);
			}
		}
	}

	@Test
	void TestRefinedSE2d() {
		Forest forest = new Forest(2);
		double[] coord = { 0.75, 0.25 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		Levels levels = new Levels(forest);
		assertEquals(3, levels.getNumLevels());
		assertEquals(1, levels.getLevel(2).size());
		assertEquals(4, levels.getLevel(1).size());
		assertEquals(7, levels.getLevel(0).size());

		Patch root = levels.getLevel(2).get(0);

		Patch lv1_sw_patch = getChildPatch(levels, 1, root, Orthant.SW());
		assertEquals(root.id, lv1_sw_patch.parent_id);
		assertEquals(0, lv1_sw_patch.parent_rank);
		assertEquals(lv1_sw_patch.id, lv1_sw_patch.child_ids[0]);
		assertEquals(0, lv1_sw_patch.child_ranks[0]);
		for (int i = 1; i < 4; i++) {
			assertEquals(-1, lv1_sw_patch.child_ids[i]);
			assertEquals(-1, lv1_sw_patch.child_ranks[i]);
		}

		Patch lv0_sw_patch = getChildPatch(levels, 0, root, Orthant.SW());
		assertEquals(lv0_sw_patch.id, lv0_sw_patch.parent_id);
		assertEquals(0, lv0_sw_patch.parent_rank);
		for (int i = 0; i < 4; i++) {
			assertEquals(-1, lv0_sw_patch.child_ids[i]);
			assertEquals(-1, lv0_sw_patch.child_ranks[i]);
		}

		// check fine neighbor
		Patch lv1_se_patch = getChildPatch(levels, 1, root, Orthant.SE());
		Neighbor fine_nbr = getNeighbor(lv0_sw_patch, Side.EAST());
		assertEquals("FINE", fine_nbr.type);
		assertEquals(2, fine_nbr.ids.length);
		assertEquals(lv1_se_patch.child_ids[0], fine_nbr.ids[0]);
		assertEquals(lv1_se_patch.child_ids[2], fine_nbr.ids[1]);
		assertEquals(2, fine_nbr.ranks.length);
		assertEquals(0, fine_nbr.ranks[0]);
		assertEquals(0, fine_nbr.ranks[1]);
		assertEquals(null, fine_nbr.orth_on_coarse);

		// check coarse neighbor
		Patch lv0_se_sw_south = getChildPatch(levels, 0, lv1_se_patch, Orthant.SW());
		assertEquals(1, lv0_se_sw_south.corner_nbrs.size());
		Neighbor coarse_nbr = getNeighbor(lv0_se_sw_south, Side.WEST());
		assertEquals("COARSE", coarse_nbr.type);
		assertEquals(1, coarse_nbr.ids.length);
		assertEquals(lv0_sw_patch.id, coarse_nbr.ids[0]);
		assertEquals(1, coarse_nbr.ranks.length);
		assertEquals(0, coarse_nbr.ranks[0]);
		assertEquals(Orthant.Lower(), coarse_nbr.orth_on_coarse);

		// check fine corner neighbor
		Patch lv0_nw_patch = getChildPatch(levels, 0, root, Orthant.NW());
		CornerNeighbor fine_corner_nbr = getNeighbor(lv0_nw_patch, Orthant.SE());
		assertEquals("FINE", fine_corner_nbr.type);
		assertEquals(1, fine_corner_nbr.ids.length);
		assertEquals(lv1_se_patch.child_ids[2], fine_corner_nbr.ids[0]);
		assertEquals(1, fine_corner_nbr.ranks.length);
		assertEquals(0, fine_corner_nbr.ranks[0]);

		// check coarse neighbor
		Patch lv0_se_nw_south = getChildPatch(levels, 0, lv1_se_patch, Orthant.NW());
		assertEquals(2, lv0_se_nw_south.corner_nbrs.size());
		CornerNeighbor coarse_corner_nbr = getNeighbor(lv0_se_nw_south, Orthant.NW());
		assertEquals("COARSE", coarse_corner_nbr.type);
		assertEquals(1, coarse_corner_nbr.ids.length);
		assertEquals(lv0_nw_patch.id, coarse_corner_nbr.ids[0]);
		assertEquals(1, coarse_corner_nbr.ranks.length);
		assertEquals(0, coarse_corner_nbr.ranks[0]);
	}

	@Test
	void TestRefinedBSE3d() {
		Forest forest = new Forest(3);
		double[] coord = { 0.75, 0.25, 0.25 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		Levels levels = new Levels(forest);
		assertEquals(3, levels.getNumLevels());
		assertEquals(1, levels.getLevel(2).size());
		assertEquals(8, levels.getLevel(1).size());
		assertEquals(15, levels.getLevel(0).size());

		Patch root = levels.getLevel(2).get(0);

		Patch lv1_bsw_patch = getChildPatch(levels, 1, root, Orthant.BSW());
		assertEquals(root.id, lv1_bsw_patch.parent_id);
		assertEquals(0, lv1_bsw_patch.parent_rank);
		assertEquals(lv1_bsw_patch.id, lv1_bsw_patch.child_ids[0]);
		assertEquals(0, lv1_bsw_patch.child_ranks[0]);
		for (int i = 1; i < 8; i++) {
			assertEquals(-1, lv1_bsw_patch.child_ids[i]);
			assertEquals(-1, lv1_bsw_patch.child_ranks[i]);
		}

		Patch lv0_bsw_patch = getChildPatch(levels, 0, root, Orthant.BSW());
		Patch lv0_tsw_patch = getChildPatch(levels, 0, root, Orthant.TSW());
		assertEquals(lv0_bsw_patch.id, lv0_bsw_patch.parent_id);
		assertEquals(0, lv0_bsw_patch.parent_rank);
		for (int i = 0; i < 8; i++) {
			assertEquals(-1, lv0_bsw_patch.child_ids[i]);
			assertEquals(-1, lv0_bsw_patch.child_ranks[i]);
		}

		// check fine neighbor
		Patch lv1_bse_patch = getChildPatch(levels, 1, root, Orthant.BSE());
		Neighbor fine_nbr = getNeighbor(lv0_bsw_patch, Side.EAST());
		assertEquals("FINE", fine_nbr.type);
		assertEquals(4, fine_nbr.ids.length);
		assertEquals(lv1_bse_patch.child_ids[0], fine_nbr.ids[0]);
		assertEquals(lv1_bse_patch.child_ids[2], fine_nbr.ids[1]);
		assertEquals(lv1_bse_patch.child_ids[4], fine_nbr.ids[2]);
		assertEquals(lv1_bse_patch.child_ids[6], fine_nbr.ids[3]);
		assertEquals(4, fine_nbr.ranks.length);
		assertEquals(0, fine_nbr.ranks[0]);
		assertEquals(0, fine_nbr.ranks[1]);
		assertEquals(0, fine_nbr.ranks[2]);
		assertEquals(0, fine_nbr.ranks[3]);
		assertEquals(null, fine_nbr.orth_on_coarse);

		// check coarse neighbor
		Patch lv0_bse_bsw_south = getChildPatch(levels, 0, lv1_bse_patch, Orthant.BSW());
		Neighbor coarse_nbr = getNeighbor(lv0_bse_bsw_south, Side.WEST());
		assertEquals("COARSE", coarse_nbr.type);
		assertEquals(1, coarse_nbr.ids.length);
		assertEquals(lv0_bsw_patch.id, coarse_nbr.ids[0]);
		assertEquals(1, coarse_nbr.ranks.length);
		assertEquals(0, coarse_nbr.ranks[0]);
		assertEquals(Orthant.SW(), coarse_nbr.orth_on_coarse);

		// check edge fine neighbor
		EdgeNeighbor fine_edge_nbr = getNeighbor(lv0_tsw_patch, Edge.BE());
		assertEquals("FINE", fine_edge_nbr.type);
		assertEquals(2, fine_edge_nbr.ids.length);
		assertEquals(lv1_bse_patch.child_ids[4], fine_edge_nbr.ids[0]);
		assertEquals(lv1_bse_patch.child_ids[6], fine_edge_nbr.ids[1]);
		assertEquals(2, fine_edge_nbr.ranks.length);
		assertEquals(0, fine_edge_nbr.ranks[0]);
		assertEquals(0, fine_edge_nbr.ranks[1]);
		assertEquals(null, fine_edge_nbr.orth_on_coarse);

		// check edge coarse neighbor
		Patch lv0_bse_tsw_south = getChildPatch(levels, 0, lv1_bse_patch, Orthant.TSW());
		EdgeNeighbor coarse_edge_nbr = getNeighbor(lv0_bse_tsw_south, Edge.TW());
		assertEquals("COARSE", coarse_edge_nbr.type);
		assertEquals(1, coarse_edge_nbr.ids.length);
		assertEquals(lv0_tsw_patch.id, coarse_edge_nbr.ids[0]);
		assertEquals(1, coarse_edge_nbr.ranks.length);
		assertEquals(0, coarse_edge_nbr.ranks[0]);
		assertEquals(Orthant.Lower(), coarse_edge_nbr.orth_on_coarse);

		// check fine corner neighbor
		Patch lv0_tnw_patch = getChildPatch(levels, 0, root, Orthant.TNW());
		CornerNeighbor fine_corner_nbr = getNeighbor(lv0_tnw_patch, Orthant.BSE());
		assertEquals("FINE", fine_corner_nbr.type);
		assertEquals(1, fine_corner_nbr.ids.length);
		assertEquals(lv1_bse_patch.child_ids[6], fine_corner_nbr.ids[0]);
		assertEquals(1, fine_corner_nbr.ranks.length);
		assertEquals(0, fine_corner_nbr.ranks[0]);

		// check coarse neighbor
		Patch lv0_bse_tnw_south = getChildPatch(levels, 0, lv1_bse_patch, Orthant.TNW());
		CornerNeighbor coarse_corner_nbr = getNeighbor(lv0_bse_tnw_south, Orthant.TNW());
		assertEquals("COARSE", coarse_corner_nbr.type);
		assertEquals(1, coarse_corner_nbr.ids.length);
		assertEquals(lv0_tnw_patch.id, coarse_corner_nbr.ids[0]);
		assertEquals(1, coarse_corner_nbr.ranks.length);
		assertEquals(0, coarse_corner_nbr.ranks[0]);
	}

	@Test
	void Test2RefinedSE2d() {
		Forest forest = new Forest(2);
		double[] coord = { 0.8, 0.2 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		forest.refineAt(coord);
		Levels levels = new Levels(forest);
		assertEquals(4, levels.getNumLevels());
		assertEquals(1, levels.getLevel(3).size());
		assertEquals(4, levels.getLevel(2).size());
		assertEquals(7, levels.getLevel(1).size());
		assertEquals(10, levels.getLevel(0).size());

		Patch root = levels.getLevel(3).get(0);

		Patch lv2_sw_patch = getChildPatch(levels, 2, root, Orthant.SW());
		assertEquals(root.id, lv2_sw_patch.parent_id);
		assertEquals(0, lv2_sw_patch.parent_rank);
		assertEquals(lv2_sw_patch.id, lv2_sw_patch.child_ids[0]);
		assertEquals(0, lv2_sw_patch.child_ranks[0]);
		for (int i = 1; i < 4; i++) {
			assertEquals(-1, lv2_sw_patch.child_ids[i]);
			assertEquals(-1, lv2_sw_patch.child_ranks[i]);
		}

		Patch lv1_sw_patch = getChildPatch(levels, 1, root, Orthant.SW());
		assertEquals(lv1_sw_patch.id, lv1_sw_patch.parent_id);
		assertEquals(0, lv1_sw_patch.parent_rank);
		assertEquals(lv2_sw_patch.id, lv2_sw_patch.child_ids[0]);
		assertEquals(0, lv2_sw_patch.child_ranks[0]);
		for (int i = 1; i < 4; i++) {
			assertEquals(-1, lv2_sw_patch.child_ids[i]);
			assertEquals(-1, lv2_sw_patch.child_ranks[i]);
		}

	}

	@Test
	void TestChangeRankAtLevel2RefinedSE2d() {
		Forest forest = new Forest(2);
		double[] coord = { 0.75, 0.25 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		Levels levels = new Levels(forest);
		levels.changeRankAt(coord, 2, 1);

		Patch root = levels.getLevel(2).get(0);
		assertEquals(1, root.rank);
		for (int i = 0; i < 4; i++) {
			assertEquals(0, root.child_ranks[i]);
		}
		for (Orthant o : Orthant.getValuesForDimension(2)) {
			Patch child = getChildPatch(levels, 1, root, o);
			assertEquals(1, child.parent_rank);
		}
	}

	@Test
	void TestChangeRankAtLevel1RefinedSE2d() {
		Forest forest = new Forest(2);
		double[] coord = { 0.75, 0.25 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		Levels levels = new Levels(forest);
		double[] ref_coord = { 0.25, 0.25 };
		levels.changeRankAt(ref_coord, 1, 1);

		Patch root = levels.getLevel(2).get(0);
		assertEquals(1, root.child_ranks[0]);
		Patch lv1_sw_child = getChildPatch(levels, 1, root, Orthant.SW());
		assertEquals(1, lv1_sw_child.rank);
		assertEquals(0, lv1_sw_child.parent_rank);
		assertEquals(0, lv1_sw_child.child_ranks[0]);
		Patch lv0_sw_child = getChildPatch(levels, 0, root, Orthant.SW());
		assertEquals(1, lv0_sw_child.parent_rank);

		Patch lv1_se_child = getChildPatch(levels, 1, root, Orthant.SE());
		assertEquals(1, getNeighbor(lv1_se_child, Side.WEST()).ranks[0]);

		Patch lv1_nw_child = getChildPatch(levels, 1, root, Orthant.NW());
		assertEquals(1, getNeighbor(lv1_nw_child, Side.SOUTH()).ranks[0]);
	}

	private Neighbor getNeighbor(Patch p, Side side) {
		for (Neighbor n : p.nbrs) {
			if (n.side.equals(side)) {
				return n;
			}
		}
		return null;
	}

	private EdgeNeighbor getNeighbor(Patch p, Edge edge) {
		for (EdgeNeighbor n : p.edge_nbrs) {
			if (n.edge.equals(edge)) {
				return n;
			}
		}
		return null;
	}

	private CornerNeighbor getNeighbor(Patch p, Orthant corner) {
		for (CornerNeighbor n : p.corner_nbrs) {
			if (n.corner.equals(corner)) {
				return n;
			}
		}
		return null;
	}

	private Patch getChildPatch(Levels levels, int level, Patch parent, Orthant o) {
		for (Patch p : levels.levels.get(level)) {
			if (p.id == parent.child_ids[o.getIndex()]) {
				return p;
			}

		}
		return null;
	}
}
