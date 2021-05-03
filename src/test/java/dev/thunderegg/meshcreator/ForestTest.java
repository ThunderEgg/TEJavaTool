package dev.thunderegg.meshcreator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import dev.thunderegg.Edge;
import dev.thunderegg.Orthant;
import dev.thunderegg.Side;

class ForestTest {

	private Set<Side> set(Side[] sides) {
		Set<Side> ret = new HashSet<Side>();
		for (Side s : sides) {
			ret.add(s);
		}
		return ret;
	}

	private Set<Side> intersection(Side[] sides1, Side[] sides2) {
		Set<Side> s1 = set(sides1);
		s1.retainAll(set(sides2));
		return s1;
	}

	@Test
	void DefaultConstructor() {
		Forest forest = new Forest(2);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertEquals(forest.getMaxLevel(), 0);
	}

	@Test
	void RefineAtGood() {
		Forest forest = new Forest(2);
		double[] coord = { .5, .5 };
		forest.refineAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 1);

		Node root = forest.getNode(0);
		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		Node sw = forest.getNode(root.getChildId(Orthant.SW()));
		Node se = forest.getNode(root.getChildId(Orthant.SE()));
		Node nw = forest.getNode(root.getChildId(Orthant.NW()));
		Node ne = forest.getNode(root.getChildId(Orthant.NE()));

		// SW child
		assertEquals(sw.getId(), root.getChildId(Orthant.SW()));
		assertTrue(sw.hasParent());
		assertEquals(root.getId(), sw.getParentId());
		assertFalse(sw.hasChildren());

		assertFalse(sw.hasNbr(Side.WEST()));
		assertTrue(sw.hasNbr(Side.EAST()));
		assertFalse(sw.hasNbr(Side.SOUTH()));
		assertTrue(sw.hasNbr(Side.NORTH()));

		assertEquals(se.getId(), sw.getNbrId(Side.EAST()));
		assertEquals(nw.getId(), sw.getNbrId(Side.NORTH()));

		assertFalse(sw.hasNbr(Orthant.SW()));
		assertFalse(sw.hasNbr(Orthant.SE()));
		assertFalse(sw.hasNbr(Orthant.NW()));
		assertTrue(sw.hasNbr(Orthant.NE()));

		assertEquals(ne.getId(), sw.getNbrId(Orthant.NE()));

		// SE child
		assertEquals(se.getId(), root.getChildId(Orthant.SE()));
		assertTrue(se.hasParent());
		assertEquals(root.getId(), se.getParentId());
		assertFalse(se.hasChildren());

		assertTrue(se.hasNbr(Side.WEST()));
		assertFalse(se.hasNbr(Side.EAST()));
		assertFalse(se.hasNbr(Side.SOUTH()));
		assertTrue(se.hasNbr(Side.NORTH()));

		assertEquals(sw.getId(), se.getNbrId(Side.WEST()));
		assertEquals(ne.getId(), se.getNbrId(Side.NORTH()));

		assertFalse(se.hasNbr(Orthant.SW()));
		assertFalse(se.hasNbr(Orthant.SE()));
		assertTrue(se.hasNbr(Orthant.NW()));
		assertFalse(se.hasNbr(Orthant.NE()));

		assertEquals(nw.getId(), se.getNbrId(Orthant.NW()));

		// NW child
		assertEquals(nw.getId(), root.getChildId(Orthant.NW()));
		assertTrue(nw.hasParent());
		assertEquals(root.getId(), nw.getParentId());
		assertFalse(nw.hasChildren());

		assertFalse(nw.hasNbr(Side.WEST()));
		assertTrue(nw.hasNbr(Side.EAST()));
		assertTrue(nw.hasNbr(Side.SOUTH()));
		assertFalse(nw.hasNbr(Side.NORTH()));

		assertEquals(ne.getId(), nw.getNbrId(Side.EAST()));
		assertEquals(sw.getId(), nw.getNbrId(Side.SOUTH()));

		assertFalse(nw.hasNbr(Orthant.SW()));
		assertTrue(nw.hasNbr(Orthant.SE()));
		assertFalse(nw.hasNbr(Orthant.NW()));
		assertFalse(nw.hasNbr(Orthant.NE()));

		assertEquals(se.getId(), nw.getNbrId(Orthant.SE()));

		// NE child
		assertEquals(ne.getId(), root.getChildId(Orthant.NE()));
		assertTrue(ne.hasParent());
		assertEquals(root.getId(), ne.getParentId());
		assertFalse(ne.hasChildren());

		assertTrue(ne.hasNbr(Side.WEST()));
		assertFalse(ne.hasNbr(Side.EAST()));
		assertTrue(ne.hasNbr(Side.SOUTH()));
		assertFalse(ne.hasNbr(Side.NORTH()));

		assertEquals(nw.getId(), ne.getNbrId(Side.WEST()));
		assertEquals(se.getId(), ne.getNbrId(Side.SOUTH()));

		assertTrue(ne.hasNbr(Orthant.SW()));
		assertFalse(ne.hasNbr(Orthant.SE()));
		assertFalse(ne.hasNbr(Orthant.NW()));
		assertFalse(ne.hasNbr(Orthant.NE()));

		assertEquals(sw.getId(), ne.getNbrId(Orthant.SW()));
	}

	@Test
	void RefineAtGood3D() {
		Forest forest = new Forest(3);
		double[] coord = { .5, .5, .5 };
		forest.refineAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 1);

		Node root = forest.getNode(0);
		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		Node bsw = forest.getNode(root.getChildId(Orthant.BSW()));
		Node bse = forest.getNode(root.getChildId(Orthant.BSE()));
		Node bnw = forest.getNode(root.getChildId(Orthant.BNW()));
		Node bne = forest.getNode(root.getChildId(Orthant.BNE()));
		Node tsw = forest.getNode(root.getChildId(Orthant.TSW()));
		Node tse = forest.getNode(root.getChildId(Orthant.TSE()));
		Node tnw = forest.getNode(root.getChildId(Orthant.TNW()));
		Node tne = forest.getNode(root.getChildId(Orthant.TNE()));

		// BSW child
		assertEquals(bsw.getId(), root.getChildId(Orthant.BSW()));
		assertTrue(bsw.hasParent());
		assertEquals(root.getId(), bsw.getParentId());
		assertFalse(bsw.hasChildren());

		assertFalse(bsw.hasNbr(Side.WEST()));
		assertTrue(bsw.hasNbr(Side.EAST()));
		assertFalse(bsw.hasNbr(Side.SOUTH()));
		assertTrue(bsw.hasNbr(Side.NORTH()));
		assertFalse(bsw.hasNbr(Side.BOTTOM()));
		assertTrue(bsw.hasNbr(Side.TOP()));

		assertEquals(bse.getId(), bsw.getNbrId(Side.EAST()));
		assertEquals(bnw.getId(), bsw.getNbrId(Side.NORTH()));
		assertEquals(tsw.getId(), bsw.getNbrId(Side.TOP()));

		assertFalse(bsw.hasNbr(Edge.BS()));
		assertTrue(bsw.hasNbr(Edge.TN()));
		assertFalse(bsw.hasNbr(Edge.BN()));
		assertFalse(bsw.hasNbr(Edge.TS()));
		assertFalse(bsw.hasNbr(Edge.BW()));
		assertTrue(bsw.hasNbr(Edge.TE()));
		assertFalse(bsw.hasNbr(Edge.BE()));
		assertFalse(bsw.hasNbr(Edge.TW()));
		assertFalse(bsw.hasNbr(Edge.SW()));
		assertTrue(bsw.hasNbr(Edge.NE()));
		assertFalse(bsw.hasNbr(Edge.SE()));
		assertFalse(bsw.hasNbr(Edge.NW()));

		assertEquals(tnw.getId(), bsw.getNbrId(Edge.TN()));
		assertEquals(tse.getId(), bsw.getNbrId(Edge.TE()));
		assertEquals(bne.getId(), bsw.getNbrId(Edge.NE()));

		assertFalse(bsw.hasNbr(Orthant.BSW()));
		assertFalse(bsw.hasNbr(Orthant.BSE()));
		assertFalse(bsw.hasNbr(Orthant.BNW()));
		assertFalse(bsw.hasNbr(Orthant.BNE()));
		assertFalse(bsw.hasNbr(Orthant.TSW()));
		assertFalse(bsw.hasNbr(Orthant.TSE()));
		assertFalse(bsw.hasNbr(Orthant.TNW()));
		assertTrue(bsw.hasNbr(Orthant.TNE()));

		assertEquals(tne.getId(), bsw.getNbrId(Orthant.TNE()));

		// BSE child
		assertEquals(bse.getId(), root.getChildId(Orthant.BSE()));
		assertTrue(bse.hasParent());
		assertEquals(root.getId(), bse.getParentId());
		assertFalse(bse.hasChildren());

		assertTrue(bse.hasNbr(Side.WEST()));
		assertFalse(bse.hasNbr(Side.EAST()));
		assertFalse(bse.hasNbr(Side.SOUTH()));
		assertTrue(bse.hasNbr(Side.NORTH()));
		assertFalse(bse.hasNbr(Side.BOTTOM()));
		assertTrue(bse.hasNbr(Side.TOP()));

		assertEquals(bsw.getId(), bse.getNbrId(Side.WEST()));
		assertEquals(bne.getId(), bse.getNbrId(Side.NORTH()));
		assertEquals(tse.getId(), bse.getNbrId(Side.TOP()));

		assertFalse(bse.hasNbr(Edge.BS()));
		assertTrue(bse.hasNbr(Edge.TN()));
		assertFalse(bse.hasNbr(Edge.BN()));
		assertFalse(bse.hasNbr(Edge.TS()));
		assertFalse(bse.hasNbr(Edge.BW()));
		assertFalse(bse.hasNbr(Edge.TE()));
		assertFalse(bse.hasNbr(Edge.BE()));
		assertTrue(bse.hasNbr(Edge.TW()));
		assertFalse(bse.hasNbr(Edge.SW()));
		assertFalse(bse.hasNbr(Edge.NE()));
		assertFalse(bse.hasNbr(Edge.SE()));
		assertTrue(bse.hasNbr(Edge.NW()));

		assertEquals(tne.getId(), bse.getNbrId(Edge.TN()));
		assertEquals(tsw.getId(), bse.getNbrId(Edge.TW()));
		assertEquals(bnw.getId(), bse.getNbrId(Edge.NW()));

		assertFalse(bse.hasNbr(Orthant.BSW()));
		assertFalse(bse.hasNbr(Orthant.BSE()));
		assertFalse(bse.hasNbr(Orthant.BNW()));
		assertFalse(bse.hasNbr(Orthant.BNE()));
		assertFalse(bse.hasNbr(Orthant.TSW()));
		assertFalse(bse.hasNbr(Orthant.TSE()));
		assertTrue(bse.hasNbr(Orthant.TNW()));
		assertFalse(bse.hasNbr(Orthant.TNE()));

		assertEquals(tnw.getId(), bse.getNbrId(Orthant.TNW()));

		// BNW child
		assertEquals(bnw.getId(), root.getChildId(Orthant.BNW()));
		assertTrue(bnw.hasParent());
		assertEquals(root.getId(), bnw.getParentId());
		assertFalse(bnw.hasChildren());

		assertFalse(bnw.hasNbr(Side.WEST()));
		assertTrue(bnw.hasNbr(Side.EAST()));
		assertTrue(bnw.hasNbr(Side.SOUTH()));
		assertFalse(bnw.hasNbr(Side.NORTH()));
		assertFalse(bnw.hasNbr(Side.BOTTOM()));
		assertTrue(bnw.hasNbr(Side.TOP()));

		assertEquals(bne.getId(), bnw.getNbrId(Side.EAST()));
		assertEquals(bsw.getId(), bnw.getNbrId(Side.SOUTH()));
		assertEquals(tnw.getId(), bnw.getNbrId(Side.TOP()));

		assertFalse(bnw.hasNbr(Edge.BS()));
		assertFalse(bnw.hasNbr(Edge.TN()));
		assertFalse(bnw.hasNbr(Edge.BN()));
		assertTrue(bnw.hasNbr(Edge.TS()));
		assertFalse(bnw.hasNbr(Edge.BW()));
		assertTrue(bnw.hasNbr(Edge.TE()));
		assertFalse(bnw.hasNbr(Edge.BE()));
		assertFalse(bnw.hasNbr(Edge.TW()));
		assertFalse(bnw.hasNbr(Edge.SW()));
		assertFalse(bnw.hasNbr(Edge.NE()));
		assertTrue(bnw.hasNbr(Edge.SE()));
		assertFalse(bnw.hasNbr(Edge.NW()));

		assertEquals(tsw.getId(), bnw.getNbrId(Edge.TS()));
		assertEquals(tne.getId(), bnw.getNbrId(Edge.TE()));
		assertEquals(bse.getId(), bnw.getNbrId(Edge.SE()));

		assertFalse(bnw.hasNbr(Orthant.BSW()));
		assertFalse(bnw.hasNbr(Orthant.BSE()));
		assertFalse(bnw.hasNbr(Orthant.BNW()));
		assertFalse(bnw.hasNbr(Orthant.BNE()));
		assertFalse(bnw.hasNbr(Orthant.TSW()));
		assertTrue(bnw.hasNbr(Orthant.TSE()));
		assertFalse(bnw.hasNbr(Orthant.TNW()));
		assertFalse(bnw.hasNbr(Orthant.TNE()));

		assertEquals(tse.getId(), bnw.getNbrId(Orthant.TSE()));

		// BNE child
		assertEquals(bne.getId(), root.getChildId(Orthant.BNE()));
		assertTrue(bne.hasParent());
		assertEquals(root.getId(), bne.getParentId());
		assertFalse(bne.hasChildren());

		assertTrue(bne.hasNbr(Side.WEST()));
		assertFalse(bne.hasNbr(Side.EAST()));
		assertTrue(bne.hasNbr(Side.SOUTH()));
		assertFalse(bne.hasNbr(Side.NORTH()));
		assertFalse(bne.hasNbr(Side.BOTTOM()));
		assertTrue(bne.hasNbr(Side.TOP()));

		assertEquals(bnw.getId(), bne.getNbrId(Side.WEST()));
		assertEquals(bse.getId(), bne.getNbrId(Side.SOUTH()));
		assertEquals(tne.getId(), bne.getNbrId(Side.TOP()));

		assertFalse(bne.hasNbr(Edge.BS()));
		assertFalse(bne.hasNbr(Edge.TN()));
		assertFalse(bne.hasNbr(Edge.BN()));
		assertTrue(bne.hasNbr(Edge.TS()));
		assertFalse(bne.hasNbr(Edge.BW()));
		assertFalse(bne.hasNbr(Edge.TE()));
		assertFalse(bne.hasNbr(Edge.BE()));
		assertTrue(bne.hasNbr(Edge.TW()));
		assertTrue(bne.hasNbr(Edge.SW()));
		assertFalse(bne.hasNbr(Edge.NE()));
		assertFalse(bne.hasNbr(Edge.SE()));
		assertFalse(bne.hasNbr(Edge.NW()));

		assertEquals(tse.getId(), bne.getNbrId(Edge.TS()));
		assertEquals(tnw.getId(), bne.getNbrId(Edge.TW()));
		assertEquals(bsw.getId(), bne.getNbrId(Edge.SW()));

		assertFalse(bne.hasNbr(Orthant.BSW()));
		assertFalse(bne.hasNbr(Orthant.BSE()));
		assertFalse(bne.hasNbr(Orthant.BNW()));
		assertFalse(bne.hasNbr(Orthant.BNE()));
		assertTrue(bne.hasNbr(Orthant.TSW()));
		assertFalse(bne.hasNbr(Orthant.TSE()));
		assertFalse(bne.hasNbr(Orthant.TNW()));
		assertFalse(bne.hasNbr(Orthant.TNE()));

		assertEquals(tsw.getId(), bne.getNbrId(Orthant.TSW()));

		// TSW child
		assertEquals(tsw.getId(), root.getChildId(Orthant.TSW()));
		assertTrue(tsw.hasParent());
		assertEquals(root.getId(), tsw.getParentId());
		assertFalse(tsw.hasChildren());

		assertFalse(tsw.hasNbr(Side.WEST()));
		assertTrue(tsw.hasNbr(Side.EAST()));
		assertFalse(tsw.hasNbr(Side.SOUTH()));
		assertTrue(tsw.hasNbr(Side.NORTH()));
		assertTrue(tsw.hasNbr(Side.BOTTOM()));
		assertFalse(tsw.hasNbr(Side.TOP()));

		assertEquals(tse.getId(), tsw.getNbrId(Side.EAST()));
		assertEquals(tnw.getId(), tsw.getNbrId(Side.NORTH()));
		assertEquals(bsw.getId(), tsw.getNbrId(Side.BOTTOM()));

		assertFalse(tsw.hasNbr(Edge.BS()));
		assertFalse(tsw.hasNbr(Edge.TN()));
		assertTrue(tsw.hasNbr(Edge.BN()));
		assertFalse(tsw.hasNbr(Edge.TS()));
		assertFalse(tsw.hasNbr(Edge.BW()));
		assertFalse(tsw.hasNbr(Edge.TE()));
		assertTrue(tsw.hasNbr(Edge.BE()));
		assertFalse(tsw.hasNbr(Edge.TW()));
		assertFalse(tsw.hasNbr(Edge.SW()));
		assertTrue(tsw.hasNbr(Edge.NE()));
		assertFalse(tsw.hasNbr(Edge.SE()));
		assertFalse(tsw.hasNbr(Edge.NW()));

		assertEquals(bnw.getId(), tsw.getNbrId(Edge.BN()));
		assertEquals(bse.getId(), tsw.getNbrId(Edge.BE()));
		assertEquals(tne.getId(), tsw.getNbrId(Edge.NE()));

		assertFalse(tsw.hasNbr(Orthant.BSW()));
		assertFalse(tsw.hasNbr(Orthant.BSE()));
		assertFalse(tsw.hasNbr(Orthant.BNW()));
		assertTrue(tsw.hasNbr(Orthant.BNE()));
		assertFalse(tsw.hasNbr(Orthant.TSW()));
		assertFalse(tsw.hasNbr(Orthant.TSE()));
		assertFalse(tsw.hasNbr(Orthant.TNW()));
		assertFalse(tsw.hasNbr(Orthant.TNE()));

		assertEquals(bne.getId(), tsw.getNbrId(Orthant.BNE()));

		// TSE child
		assertEquals(tse.getId(), root.getChildId(Orthant.TSE()));
		assertTrue(tse.hasParent());
		assertEquals(root.getId(), tse.getParentId());
		assertFalse(tse.hasChildren());

		assertTrue(tse.hasNbr(Side.WEST()));
		assertFalse(tse.hasNbr(Side.EAST()));
		assertFalse(tse.hasNbr(Side.SOUTH()));
		assertTrue(tse.hasNbr(Side.NORTH()));
		assertTrue(tse.hasNbr(Side.BOTTOM()));
		assertFalse(tse.hasNbr(Side.TOP()));

		assertEquals(tsw.getId(), tse.getNbrId(Side.WEST()));
		assertEquals(tne.getId(), tse.getNbrId(Side.NORTH()));
		assertEquals(bse.getId(), tse.getNbrId(Side.BOTTOM()));

		assertFalse(tse.hasNbr(Edge.BS()));
		assertFalse(tse.hasNbr(Edge.TN()));
		assertTrue(tse.hasNbr(Edge.BN()));
		assertFalse(tse.hasNbr(Edge.TS()));
		assertTrue(tse.hasNbr(Edge.BW()));
		assertFalse(tse.hasNbr(Edge.TE()));
		assertFalse(tse.hasNbr(Edge.BE()));
		assertFalse(tse.hasNbr(Edge.TW()));
		assertFalse(tse.hasNbr(Edge.SW()));
		assertFalse(tse.hasNbr(Edge.NE()));
		assertFalse(tse.hasNbr(Edge.SE()));
		assertTrue(tse.hasNbr(Edge.NW()));

		assertEquals(bne.getId(), tse.getNbrId(Edge.BN()));
		assertEquals(bsw.getId(), tse.getNbrId(Edge.BW()));
		assertEquals(tnw.getId(), tse.getNbrId(Edge.NW()));

		assertFalse(tse.hasNbr(Orthant.BSW()));
		assertFalse(tse.hasNbr(Orthant.BSE()));
		assertTrue(tse.hasNbr(Orthant.BNW()));
		assertFalse(tse.hasNbr(Orthant.BNE()));
		assertFalse(tse.hasNbr(Orthant.TSW()));
		assertFalse(tse.hasNbr(Orthant.TSE()));
		assertFalse(tse.hasNbr(Orthant.TNW()));
		assertFalse(tse.hasNbr(Orthant.TNE()));

		assertEquals(bnw.getId(), tse.getNbrId(Orthant.BNW()));

		// TNW child
		assertEquals(tnw.getId(), root.getChildId(Orthant.TNW()));
		assertTrue(tnw.hasParent());
		assertEquals(root.getId(), tnw.getParentId());
		assertFalse(tnw.hasChildren());

		assertFalse(tnw.hasNbr(Side.WEST()));
		assertTrue(tnw.hasNbr(Side.EAST()));
		assertTrue(tnw.hasNbr(Side.SOUTH()));
		assertFalse(tnw.hasNbr(Side.NORTH()));
		assertTrue(tnw.hasNbr(Side.BOTTOM()));
		assertFalse(tnw.hasNbr(Side.TOP()));

		assertEquals(tne.getId(), tnw.getNbrId(Side.EAST()));
		assertEquals(tsw.getId(), tnw.getNbrId(Side.SOUTH()));
		assertEquals(bnw.getId(), tnw.getNbrId(Side.BOTTOM()));

		assertTrue(tnw.hasNbr(Edge.BS()));
		assertFalse(tnw.hasNbr(Edge.TN()));
		assertFalse(tnw.hasNbr(Edge.BN()));
		assertFalse(tnw.hasNbr(Edge.TS()));
		assertFalse(tnw.hasNbr(Edge.BW()));
		assertFalse(tnw.hasNbr(Edge.TE()));
		assertTrue(tnw.hasNbr(Edge.BE()));
		assertFalse(tnw.hasNbr(Edge.TW()));
		assertFalse(tnw.hasNbr(Edge.SW()));
		assertFalse(tnw.hasNbr(Edge.NE()));
		assertTrue(tnw.hasNbr(Edge.SE()));
		assertFalse(tnw.hasNbr(Edge.NW()));

		assertEquals(bsw.getId(), tnw.getNbrId(Edge.BS()));
		assertEquals(bne.getId(), tnw.getNbrId(Edge.BE()));
		assertEquals(tse.getId(), tnw.getNbrId(Edge.SE()));

		assertFalse(tnw.hasNbr(Orthant.BSW()));
		assertTrue(tnw.hasNbr(Orthant.BSE()));
		assertFalse(tnw.hasNbr(Orthant.BNW()));
		assertFalse(tnw.hasNbr(Orthant.BNE()));
		assertFalse(tnw.hasNbr(Orthant.TSW()));
		assertFalse(tnw.hasNbr(Orthant.TSE()));
		assertFalse(tnw.hasNbr(Orthant.TNW()));
		assertFalse(tnw.hasNbr(Orthant.TNE()));

		assertEquals(bse.getId(), tnw.getNbrId(Orthant.BSE()));

		// TNE child
		assertEquals(tne.getId(), root.getChildId(Orthant.TNE()));
		assertTrue(tne.hasParent());
		assertEquals(root.getId(), tne.getParentId());
		assertFalse(tne.hasChildren());

		assertTrue(tne.hasNbr(Side.WEST()));
		assertFalse(tne.hasNbr(Side.EAST()));
		assertTrue(tne.hasNbr(Side.SOUTH()));
		assertFalse(tne.hasNbr(Side.NORTH()));
		assertTrue(tne.hasNbr(Side.BOTTOM()));
		assertFalse(tne.hasNbr(Side.TOP()));

		assertEquals(tnw.getId(), tne.getNbrId(Side.WEST()));
		assertEquals(tse.getId(), tne.getNbrId(Side.SOUTH()));
		assertEquals(bne.getId(), tne.getNbrId(Side.BOTTOM()));

		assertTrue(tne.hasNbr(Edge.BS()));
		assertFalse(tne.hasNbr(Edge.TN()));
		assertFalse(tne.hasNbr(Edge.BN()));
		assertFalse(tne.hasNbr(Edge.TS()));
		assertTrue(tne.hasNbr(Edge.BW()));
		assertFalse(tne.hasNbr(Edge.TE()));
		assertFalse(tne.hasNbr(Edge.BE()));
		assertFalse(tne.hasNbr(Edge.TW()));
		assertTrue(tne.hasNbr(Edge.SW()));
		assertFalse(tne.hasNbr(Edge.NE()));
		assertFalse(tne.hasNbr(Edge.SE()));
		assertFalse(tne.hasNbr(Edge.NW()));

		assertEquals(bse.getId(), tne.getNbrId(Edge.BS()));
		assertEquals(bnw.getId(), tne.getNbrId(Edge.BW()));
		assertEquals(tsw.getId(), tne.getNbrId(Edge.SW()));

		assertTrue(tne.hasNbr(Orthant.BSW()));
		assertFalse(tne.hasNbr(Orthant.BSE()));
		assertFalse(tne.hasNbr(Orthant.BNW()));
		assertFalse(tne.hasNbr(Orthant.BNE()));
		assertFalse(tne.hasNbr(Orthant.TSW()));
		assertFalse(tne.hasNbr(Orthant.TSE()));
		assertFalse(tne.hasNbr(Orthant.TNW()));
		assertFalse(tne.hasNbr(Orthant.TNE()));

		assertEquals(bsw.getId(), tne.getNbrId(Orthant.BSW()));
	}

	@Test
	void RefineAtTwice() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 2);

		Node root = forest.getNode(0);
		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		Node sw = forest.getNode(root.getChildId(Orthant.SW()));
		Node sw_sw = forest.getNode(sw.getChildId(Orthant.SW()));
		Node sw_se = forest.getNode(sw.getChildId(Orthant.SE()));
		Node sw_nw = forest.getNode(sw.getChildId(Orthant.NW()));
		Node sw_ne = forest.getNode(sw.getChildId(Orthant.NE()));
		Node se = forest.getNode(root.getChildId(Orthant.SE()));
		Node nw = forest.getNode(root.getChildId(Orthant.NW()));
		Node ne = forest.getNode(root.getChildId(Orthant.NE()));

		// SW child
		assertTrue(sw.hasParent());
		assertEquals(root.getId(), sw.getParentId());
		assertTrue(sw.hasChildren());

		assertEquals(sw_sw.getId(), sw.getChildId(Orthant.SW()));
		assertEquals(sw_se.getId(), sw.getChildId(Orthant.SE()));
		assertEquals(sw_nw.getId(), sw.getChildId(Orthant.NW()));
		assertEquals(sw_ne.getId(), sw.getChildId(Orthant.NE()));

		assertFalse(sw.hasNbr(Side.WEST()));
		assertTrue(sw.hasNbr(Side.EAST()));
		assertFalse(sw.hasNbr(Side.SOUTH()));
		assertTrue(sw.hasNbr(Side.NORTH()));

		assertEquals(se.getId(), sw.getNbrId(Side.EAST()));
		assertEquals(nw.getId(), sw.getNbrId(Side.NORTH()));

		assertFalse(sw.hasNbr(Orthant.SW()));
		assertFalse(sw.hasNbr(Orthant.SE()));
		assertFalse(sw.hasNbr(Orthant.NW()));
		assertTrue(sw.hasNbr(Orthant.NE()));

		assertEquals(ne.getId(), sw.getNbrId(Orthant.NE()));

		// SW_SW child
		assertEquals(sw_sw.getId(), sw.getChildId(Orthant.SW()));
		assertTrue(sw_sw.hasParent());
		assertEquals(sw.getId(), sw_sw.getParentId());
		assertFalse(sw_sw.hasChildren());

		assertFalse(sw_sw.hasNbr(Side.WEST()));
		assertTrue(sw_sw.hasNbr(Side.EAST()));
		assertFalse(sw_sw.hasNbr(Side.SOUTH()));
		assertTrue(sw_sw.hasNbr(Side.NORTH()));

		assertEquals(sw_se.getId(), sw_sw.getNbrId(Side.EAST()));
		assertEquals(sw_nw.getId(), sw_sw.getNbrId(Side.NORTH()));

		assertFalse(sw_sw.hasNbr(Orthant.SW()));
		assertFalse(sw_sw.hasNbr(Orthant.SE()));
		assertFalse(sw_sw.hasNbr(Orthant.NW()));
		assertTrue(sw_sw.hasNbr(Orthant.NE()));

		assertEquals(sw_ne.getId(), sw_sw.getNbrId(Orthant.NE()));

		// SW_SE child
		assertEquals(sw_se.getId(), sw.getChildId(Orthant.SE()));
		assertTrue(sw_se.hasParent());
		assertEquals(sw.getId(), sw_se.getParentId());
		assertFalse(sw_se.hasChildren());

		assertTrue(sw_se.hasNbr(Side.WEST()));
		assertFalse(sw_se.hasNbr(Side.EAST()));
		assertFalse(sw_se.hasNbr(Side.SOUTH()));
		assertTrue(sw_se.hasNbr(Side.NORTH()));

		assertEquals(sw_sw.getId(), sw_se.getNbrId(Side.WEST()));
		assertEquals(sw_ne.getId(), sw_se.getNbrId(Side.NORTH()));

		assertFalse(sw_se.hasNbr(Orthant.SW()));
		assertFalse(sw_se.hasNbr(Orthant.SE()));
		assertTrue(sw_se.hasNbr(Orthant.NW()));
		assertFalse(sw_se.hasNbr(Orthant.NE()));

		assertEquals(sw_nw.getId(), sw_se.getNbrId(Orthant.NW()));

		// SW_NW child
		assertEquals(sw_nw.getId(), sw.getChildId(Orthant.NW()));
		assertTrue(sw_nw.hasParent());
		assertEquals(sw.getId(), sw_nw.getParentId());
		assertFalse(sw_nw.hasChildren());

		assertFalse(sw_nw.hasNbr(Side.WEST()));
		assertTrue(sw_nw.hasNbr(Side.EAST()));
		assertTrue(sw_nw.hasNbr(Side.SOUTH()));
		assertFalse(sw_nw.hasNbr(Side.NORTH()));

		assertEquals(sw_ne.getId(), sw_nw.getNbrId(Side.EAST()));
		assertEquals(sw_sw.getId(), sw_nw.getNbrId(Side.SOUTH()));

		assertFalse(sw_nw.hasNbr(Orthant.SW()));
		assertTrue(sw_nw.hasNbr(Orthant.SE()));
		assertFalse(sw_nw.hasNbr(Orthant.NW()));
		assertFalse(sw_nw.hasNbr(Orthant.NE()));

		assertEquals(sw_se.getId(), sw_nw.getNbrId(Orthant.SE()));

		// SW_NE child
		assertEquals(sw_ne.getId(), sw.getChildId(Orthant.NE()));
		assertTrue(sw_ne.hasParent());
		assertEquals(sw.getId(), sw_ne.getParentId());
		assertFalse(sw_ne.hasChildren());

		assertTrue(sw_ne.hasNbr(Side.WEST()));
		assertFalse(sw_ne.hasNbr(Side.EAST()));
		assertTrue(sw_ne.hasNbr(Side.SOUTH()));
		assertFalse(sw_ne.hasNbr(Side.NORTH()));

		assertEquals(sw_nw.getId(), sw_ne.getNbrId(Side.WEST()));
		assertEquals(sw_se.getId(), sw_ne.getNbrId(Side.SOUTH()));

		assertTrue(sw_ne.hasNbr(Orthant.SW()));
		assertFalse(sw_ne.hasNbr(Orthant.SE()));
		assertFalse(sw_ne.hasNbr(Orthant.NW()));
		assertFalse(sw_ne.hasNbr(Orthant.NE()));

		assertEquals(sw_sw.getId(), sw_ne.getNbrId(Orthant.SW()));

		// SE child
		assertEquals(se.getId(), root.getChildId(Orthant.SE()));
		assertTrue(se.hasParent());
		assertEquals(root.getId(), se.getParentId());
		assertFalse(se.hasChildren());

		assertTrue(se.hasNbr(Side.WEST()));
		assertFalse(se.hasNbr(Side.EAST()));
		assertFalse(se.hasNbr(Side.SOUTH()));
		assertTrue(se.hasNbr(Side.NORTH()));

		assertEquals(sw.getId(), se.getNbrId(Side.WEST()));
		assertEquals(ne.getId(), se.getNbrId(Side.NORTH()));

		assertFalse(se.hasNbr(Orthant.SW()));
		assertFalse(se.hasNbr(Orthant.SE()));
		assertTrue(se.hasNbr(Orthant.NW()));
		assertFalse(se.hasNbr(Orthant.NE()));

		assertEquals(nw.getId(), se.getNbrId(Orthant.NW()));

		// NW child
		assertEquals(nw.getId(), root.getChildId(Orthant.NW()));
		assertTrue(nw.hasParent());
		assertEquals(root.getId(), nw.getParentId());
		assertFalse(nw.hasChildren());

		assertFalse(nw.hasNbr(Side.WEST()));
		assertTrue(nw.hasNbr(Side.EAST()));
		assertTrue(nw.hasNbr(Side.SOUTH()));
		assertFalse(nw.hasNbr(Side.NORTH()));

		assertEquals(ne.getId(), nw.getNbrId(Side.EAST()));
		assertEquals(sw.getId(), nw.getNbrId(Side.SOUTH()));

		assertFalse(nw.hasNbr(Orthant.SW()));
		assertTrue(nw.hasNbr(Orthant.SE()));
		assertFalse(nw.hasNbr(Orthant.NW()));
		assertFalse(nw.hasNbr(Orthant.NE()));

		assertEquals(se.getId(), nw.getNbrId(Orthant.SE()));

		// NE child
		assertEquals(ne.getId(), root.getChildId(Orthant.NE()));
		assertTrue(ne.hasParent());
		assertEquals(root.getId(), ne.getParentId());
		assertFalse(ne.hasChildren());

		assertTrue(ne.hasNbr(Side.WEST()));
		assertFalse(ne.hasNbr(Side.EAST()));
		assertTrue(ne.hasNbr(Side.SOUTH()));
		assertFalse(ne.hasNbr(Side.NORTH()));

		assertEquals(nw.getId(), ne.getNbrId(Side.WEST()));
		assertEquals(se.getId(), ne.getNbrId(Side.SOUTH()));

		assertTrue(ne.hasNbr(Orthant.SW()));
		assertFalse(ne.hasNbr(Orthant.SE()));
		assertFalse(ne.hasNbr(Orthant.NW()));
		assertFalse(ne.hasNbr(Orthant.NE()));

		assertEquals(sw.getId(), ne.getNbrId(Orthant.SW()));

	}

	@Test
	void RefineAtTwice3D() {
		Forest forest = new Forest(3);
		double[] coord = { .3, .3, .3 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 2);

		Node root = forest.getNode(0);
		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		Node bsw = forest.getNode(root.getChildId(Orthant.BSW()));
		Node bsw_bsw = forest.getNode(bsw.getChildId(Orthant.BSW()));
		Node bsw_bse = forest.getNode(bsw.getChildId(Orthant.BSE()));
		Node bsw_bnw = forest.getNode(bsw.getChildId(Orthant.BNW()));
		Node bsw_bne = forest.getNode(bsw.getChildId(Orthant.BNE()));
		Node bsw_tsw = forest.getNode(bsw.getChildId(Orthant.TSW()));
		Node bsw_tse = forest.getNode(bsw.getChildId(Orthant.TSE()));
		Node bsw_tnw = forest.getNode(bsw.getChildId(Orthant.TNW()));
		Node bsw_tne = forest.getNode(bsw.getChildId(Orthant.TNE()));
		Node bse = forest.getNode(root.getChildId(Orthant.BSE()));
		Node bnw = forest.getNode(root.getChildId(Orthant.BNW()));
		Node bne = forest.getNode(root.getChildId(Orthant.BNE()));
		Node tsw = forest.getNode(root.getChildId(Orthant.TSW()));
		Node tse = forest.getNode(root.getChildId(Orthant.TSE()));
		Node tnw = forest.getNode(root.getChildId(Orthant.TNW()));
		Node tne = forest.getNode(root.getChildId(Orthant.TNE()));

		// BSW child
		assertEquals(bsw.getId(), root.getChildId(Orthant.BSW()));
		assertTrue(bsw.hasParent());
		assertEquals(root.getId(), bsw.getParentId());
		assertTrue(bsw.hasChildren());

		assertFalse(bsw.hasNbr(Side.WEST()));
		assertTrue(bsw.hasNbr(Side.EAST()));
		assertFalse(bsw.hasNbr(Side.SOUTH()));
		assertTrue(bsw.hasNbr(Side.NORTH()));
		assertFalse(bsw.hasNbr(Side.BOTTOM()));
		assertTrue(bsw.hasNbr(Side.TOP()));

		assertEquals(bse.getId(), bsw.getNbrId(Side.EAST()));
		assertEquals(bnw.getId(), bsw.getNbrId(Side.NORTH()));
		assertEquals(tsw.getId(), bsw.getNbrId(Side.TOP()));

		assertFalse(bsw.hasNbr(Edge.BS()));
		assertTrue(bsw.hasNbr(Edge.TN()));
		assertFalse(bsw.hasNbr(Edge.BN()));
		assertFalse(bsw.hasNbr(Edge.TS()));
		assertFalse(bsw.hasNbr(Edge.BW()));
		assertTrue(bsw.hasNbr(Edge.TE()));
		assertFalse(bsw.hasNbr(Edge.BE()));
		assertFalse(bsw.hasNbr(Edge.TW()));
		assertFalse(bsw.hasNbr(Edge.SW()));
		assertTrue(bsw.hasNbr(Edge.NE()));
		assertFalse(bsw.hasNbr(Edge.SE()));
		assertFalse(bsw.hasNbr(Edge.NW()));

		assertEquals(tnw.getId(), bsw.getNbrId(Edge.TN()));
		assertEquals(tse.getId(), bsw.getNbrId(Edge.TE()));
		assertEquals(bne.getId(), bsw.getNbrId(Edge.NE()));

		assertFalse(bsw.hasNbr(Orthant.BSW()));
		assertFalse(bsw.hasNbr(Orthant.BSE()));
		assertFalse(bsw.hasNbr(Orthant.BNW()));
		assertFalse(bsw.hasNbr(Orthant.BNE()));
		assertFalse(bsw.hasNbr(Orthant.TSW()));
		assertFalse(bsw.hasNbr(Orthant.TSE()));
		assertFalse(bsw.hasNbr(Orthant.TNW()));
		assertTrue(bsw.hasNbr(Orthant.TNE()));

		assertEquals(tne.getId(), bsw.getNbrId(Orthant.TNE()));

		// BSW_BSW child
		assertEquals(bsw_bsw.getId(), bsw.getChildId(Orthant.BSW()));
		assertTrue(bsw_bsw.hasParent());
		assertEquals(bsw.getId(), bsw_bsw.getParentId());
		assertFalse(bsw_bsw.hasChildren());

		assertFalse(bsw_bsw.hasNbr(Side.WEST()));
		assertTrue(bsw_bsw.hasNbr(Side.EAST()));
		assertFalse(bsw_bsw.hasNbr(Side.SOUTH()));
		assertTrue(bsw_bsw.hasNbr(Side.NORTH()));
		assertFalse(bsw_bsw.hasNbr(Side.BOTTOM()));
		assertTrue(bsw_bsw.hasNbr(Side.TOP()));

		assertEquals(bsw_bse.getId(), bsw_bsw.getNbrId(Side.EAST()));
		assertEquals(bsw_bnw.getId(), bsw_bsw.getNbrId(Side.NORTH()));
		assertEquals(bsw_tsw.getId(), bsw_bsw.getNbrId(Side.TOP()));

		assertFalse(bsw_bsw.hasNbr(Edge.BS()));
		assertTrue(bsw_bsw.hasNbr(Edge.TN()));
		assertFalse(bsw_bsw.hasNbr(Edge.BN()));
		assertFalse(bsw_bsw.hasNbr(Edge.TS()));
		assertFalse(bsw_bsw.hasNbr(Edge.BW()));
		assertTrue(bsw_bsw.hasNbr(Edge.TE()));
		assertFalse(bsw_bsw.hasNbr(Edge.BE()));
		assertFalse(bsw_bsw.hasNbr(Edge.TW()));
		assertFalse(bsw_bsw.hasNbr(Edge.SW()));
		assertTrue(bsw_bsw.hasNbr(Edge.NE()));
		assertFalse(bsw_bsw.hasNbr(Edge.SE()));
		assertFalse(bsw_bsw.hasNbr(Edge.NW()));

		assertEquals(bsw_tnw.getId(), bsw_bsw.getNbrId(Edge.TN()));
		assertEquals(bsw_tse.getId(), bsw_bsw.getNbrId(Edge.TE()));
		assertEquals(bsw_bne.getId(), bsw_bsw.getNbrId(Edge.NE()));

		assertFalse(bsw_bsw.hasNbr(Orthant.BSW()));
		assertFalse(bsw_bsw.hasNbr(Orthant.BSE()));
		assertFalse(bsw_bsw.hasNbr(Orthant.BNW()));
		assertFalse(bsw_bsw.hasNbr(Orthant.BNE()));
		assertFalse(bsw_bsw.hasNbr(Orthant.TSW()));
		assertFalse(bsw_bsw.hasNbr(Orthant.TSE()));
		assertFalse(bsw_bsw.hasNbr(Orthant.TNW()));
		assertTrue(bsw_bsw.hasNbr(Orthant.TNE()));

		assertEquals(bsw_tne.getId(), bsw_bsw.getNbrId(Orthant.TNE()));

		// BSW_BSE child
		assertEquals(bsw_bse.getId(), bsw.getChildId(Orthant.BSE()));
		assertTrue(bsw_bse.hasParent());
		assertEquals(bsw.getId(), bsw_bse.getParentId());
		assertFalse(bsw_bse.hasChildren());

		assertTrue(bsw_bse.hasNbr(Side.WEST()));
		assertFalse(bsw_bse.hasNbr(Side.EAST()));
		assertFalse(bsw_bse.hasNbr(Side.SOUTH()));
		assertTrue(bsw_bse.hasNbr(Side.NORTH()));
		assertFalse(bsw_bse.hasNbr(Side.BOTTOM()));
		assertTrue(bsw_bse.hasNbr(Side.TOP()));

		assertEquals(bsw_bsw.getId(), bsw_bse.getNbrId(Side.WEST()));
		assertEquals(bsw_bne.getId(), bsw_bse.getNbrId(Side.NORTH()));
		assertEquals(bsw_tse.getId(), bsw_bse.getNbrId(Side.TOP()));

		assertFalse(bsw_bse.hasNbr(Edge.BS()));
		assertTrue(bsw_bse.hasNbr(Edge.TN()));
		assertFalse(bsw_bse.hasNbr(Edge.BN()));
		assertFalse(bsw_bse.hasNbr(Edge.TS()));
		assertFalse(bsw_bse.hasNbr(Edge.BW()));
		assertFalse(bsw_bse.hasNbr(Edge.TE()));
		assertFalse(bsw_bse.hasNbr(Edge.BE()));
		assertTrue(bsw_bse.hasNbr(Edge.TW()));
		assertFalse(bsw_bse.hasNbr(Edge.SW()));
		assertFalse(bsw_bse.hasNbr(Edge.NE()));
		assertFalse(bsw_bse.hasNbr(Edge.SE()));
		assertTrue(bsw_bse.hasNbr(Edge.NW()));

		assertEquals(bsw_tne.getId(), bsw_bse.getNbrId(Edge.TN()));
		assertEquals(bsw_tsw.getId(), bsw_bse.getNbrId(Edge.TW()));
		assertEquals(bsw_bnw.getId(), bsw_bse.getNbrId(Edge.NW()));

		assertFalse(bsw_bse.hasNbr(Orthant.BSW()));
		assertFalse(bsw_bse.hasNbr(Orthant.BSE()));
		assertFalse(bsw_bse.hasNbr(Orthant.BNW()));
		assertFalse(bsw_bse.hasNbr(Orthant.BNE()));
		assertFalse(bsw_bse.hasNbr(Orthant.TSW()));
		assertFalse(bsw_bse.hasNbr(Orthant.TSE()));
		assertTrue(bsw_bse.hasNbr(Orthant.TNW()));
		assertFalse(bsw_bse.hasNbr(Orthant.TNE()));

		assertEquals(bsw_tnw.getId(), bsw_bse.getNbrId(Orthant.TNW()));

		// BSW_BNW child
		assertEquals(bsw_bnw.getId(), bsw.getChildId(Orthant.BNW()));
		assertTrue(bsw_bnw.hasParent());
		assertEquals(bsw.getId(), bsw_bnw.getParentId());
		assertFalse(bsw_bnw.hasChildren());

		assertFalse(bsw_bnw.hasNbr(Side.WEST()));
		assertTrue(bsw_bnw.hasNbr(Side.EAST()));
		assertTrue(bsw_bnw.hasNbr(Side.SOUTH()));
		assertFalse(bsw_bnw.hasNbr(Side.NORTH()));
		assertFalse(bsw_bnw.hasNbr(Side.BOTTOM()));
		assertTrue(bsw_bnw.hasNbr(Side.TOP()));

		assertEquals(bsw_bne.getId(), bsw_bnw.getNbrId(Side.EAST()));
		assertEquals(bsw_bsw.getId(), bsw_bnw.getNbrId(Side.SOUTH()));
		assertEquals(bsw_tnw.getId(), bsw_bnw.getNbrId(Side.TOP()));

		assertFalse(bsw_bnw.hasNbr(Edge.BS()));
		assertFalse(bsw_bnw.hasNbr(Edge.TN()));
		assertFalse(bsw_bnw.hasNbr(Edge.BN()));
		assertTrue(bsw_bnw.hasNbr(Edge.TS()));
		assertFalse(bsw_bnw.hasNbr(Edge.BW()));
		assertTrue(bsw_bnw.hasNbr(Edge.TE()));
		assertFalse(bsw_bnw.hasNbr(Edge.BE()));
		assertFalse(bsw_bnw.hasNbr(Edge.TW()));
		assertFalse(bsw_bnw.hasNbr(Edge.SW()));
		assertFalse(bsw_bnw.hasNbr(Edge.NE()));
		assertTrue(bsw_bnw.hasNbr(Edge.SE()));
		assertFalse(bsw_bnw.hasNbr(Edge.NW()));

		assertEquals(bsw_tsw.getId(), bsw_bnw.getNbrId(Edge.TS()));
		assertEquals(bsw_tne.getId(), bsw_bnw.getNbrId(Edge.TE()));
		assertEquals(bsw_bse.getId(), bsw_bnw.getNbrId(Edge.SE()));

		assertFalse(bsw_bnw.hasNbr(Orthant.BSW()));
		assertFalse(bsw_bnw.hasNbr(Orthant.BSE()));
		assertFalse(bsw_bnw.hasNbr(Orthant.BNW()));
		assertFalse(bsw_bnw.hasNbr(Orthant.BNE()));
		assertFalse(bsw_bnw.hasNbr(Orthant.TSW()));
		assertTrue(bsw_bnw.hasNbr(Orthant.TSE()));
		assertFalse(bsw_bnw.hasNbr(Orthant.TNW()));
		assertFalse(bsw_bnw.hasNbr(Orthant.TNE()));

		assertEquals(bsw_tse.getId(), bsw_bnw.getNbrId(Orthant.TSE()));

		// BSW_BNE child
		assertEquals(bsw_bne.getId(), bsw.getChildId(Orthant.BNE()));
		assertTrue(bsw_bne.hasParent());
		assertEquals(bsw.getId(), bsw_bne.getParentId());
		assertFalse(bsw_bne.hasChildren());

		assertTrue(bsw_bne.hasNbr(Side.WEST()));
		assertFalse(bsw_bne.hasNbr(Side.EAST()));
		assertTrue(bsw_bne.hasNbr(Side.SOUTH()));
		assertFalse(bsw_bne.hasNbr(Side.NORTH()));
		assertFalse(bsw_bne.hasNbr(Side.BOTTOM()));
		assertTrue(bsw_bne.hasNbr(Side.TOP()));

		assertEquals(bsw_bnw.getId(), bsw_bne.getNbrId(Side.WEST()));
		assertEquals(bsw_bse.getId(), bsw_bne.getNbrId(Side.SOUTH()));
		assertEquals(bsw_tne.getId(), bsw_bne.getNbrId(Side.TOP()));

		assertFalse(bsw_bne.hasNbr(Edge.BS()));
		assertFalse(bsw_bne.hasNbr(Edge.TN()));
		assertFalse(bsw_bne.hasNbr(Edge.BN()));
		assertTrue(bsw_bne.hasNbr(Edge.TS()));
		assertFalse(bsw_bne.hasNbr(Edge.BW()));
		assertFalse(bsw_bne.hasNbr(Edge.TE()));
		assertFalse(bsw_bne.hasNbr(Edge.BE()));
		assertTrue(bsw_bne.hasNbr(Edge.TW()));
		assertTrue(bsw_bne.hasNbr(Edge.SW()));
		assertFalse(bsw_bne.hasNbr(Edge.NE()));
		assertFalse(bsw_bne.hasNbr(Edge.SE()));
		assertFalse(bsw_bne.hasNbr(Edge.NW()));

		assertEquals(bsw_tse.getId(), bsw_bne.getNbrId(Edge.TS()));
		assertEquals(bsw_tnw.getId(), bsw_bne.getNbrId(Edge.TW()));
		assertEquals(bsw_bsw.getId(), bsw_bne.getNbrId(Edge.SW()));

		assertFalse(bsw_bne.hasNbr(Orthant.BSW()));
		assertFalse(bsw_bne.hasNbr(Orthant.BSE()));
		assertFalse(bsw_bne.hasNbr(Orthant.BNW()));
		assertFalse(bsw_bne.hasNbr(Orthant.BNE()));
		assertTrue(bsw_bne.hasNbr(Orthant.TSW()));
		assertFalse(bsw_bne.hasNbr(Orthant.TSE()));
		assertFalse(bsw_bne.hasNbr(Orthant.TNW()));
		assertFalse(bsw_bne.hasNbr(Orthant.TNE()));

		assertEquals(bsw_tsw.getId(), bsw_bne.getNbrId(Orthant.TSW()));

		// BSW_TSW child
		assertEquals(bsw_tsw.getId(), bsw.getChildId(Orthant.TSW()));
		assertTrue(bsw_tsw.hasParent());
		assertEquals(bsw.getId(), bsw_tsw.getParentId());
		assertFalse(bsw_tsw.hasChildren());

		assertFalse(bsw_tsw.hasNbr(Side.WEST()));
		assertTrue(bsw_tsw.hasNbr(Side.EAST()));
		assertFalse(bsw_tsw.hasNbr(Side.SOUTH()));
		assertTrue(bsw_tsw.hasNbr(Side.NORTH()));
		assertTrue(bsw_tsw.hasNbr(Side.BOTTOM()));
		assertFalse(bsw_tsw.hasNbr(Side.TOP()));

		assertEquals(bsw_tse.getId(), bsw_tsw.getNbrId(Side.EAST()));
		assertEquals(bsw_tnw.getId(), bsw_tsw.getNbrId(Side.NORTH()));
		assertEquals(bsw_bsw.getId(), bsw_tsw.getNbrId(Side.BOTTOM()));

		assertFalse(bsw_tsw.hasNbr(Edge.BS()));
		assertFalse(bsw_tsw.hasNbr(Edge.TN()));
		assertTrue(bsw_tsw.hasNbr(Edge.BN()));
		assertFalse(bsw_tsw.hasNbr(Edge.TS()));
		assertFalse(bsw_tsw.hasNbr(Edge.BW()));
		assertFalse(bsw_tsw.hasNbr(Edge.TE()));
		assertTrue(bsw_tsw.hasNbr(Edge.BE()));
		assertFalse(bsw_tsw.hasNbr(Edge.TW()));
		assertFalse(bsw_tsw.hasNbr(Edge.SW()));
		assertTrue(bsw_tsw.hasNbr(Edge.NE()));
		assertFalse(bsw_tsw.hasNbr(Edge.SE()));
		assertFalse(bsw_tsw.hasNbr(Edge.NW()));

		assertEquals(bsw_bnw.getId(), bsw_tsw.getNbrId(Edge.BN()));
		assertEquals(bsw_bse.getId(), bsw_tsw.getNbrId(Edge.BE()));
		assertEquals(bsw_tne.getId(), bsw_tsw.getNbrId(Edge.NE()));

		assertFalse(bsw_tsw.hasNbr(Orthant.BSW()));
		assertFalse(bsw_tsw.hasNbr(Orthant.BSE()));
		assertFalse(bsw_tsw.hasNbr(Orthant.BNW()));
		assertTrue(bsw_tsw.hasNbr(Orthant.BNE()));
		assertFalse(bsw_tsw.hasNbr(Orthant.TSW()));
		assertFalse(bsw_tsw.hasNbr(Orthant.TSE()));
		assertFalse(bsw_tsw.hasNbr(Orthant.TNW()));
		assertFalse(bsw_tsw.hasNbr(Orthant.TNE()));

		assertEquals(bsw_bne.getId(), bsw_tsw.getNbrId(Orthant.BNE()));

		// BSW_TSE child
		assertEquals(bsw_tse.getId(), bsw.getChildId(Orthant.TSE()));
		assertTrue(bsw_tse.hasParent());
		assertEquals(bsw.getId(), bsw_tse.getParentId());
		assertFalse(bsw_tse.hasChildren());

		assertTrue(bsw_tse.hasNbr(Side.WEST()));
		assertFalse(bsw_tse.hasNbr(Side.EAST()));
		assertFalse(bsw_tse.hasNbr(Side.SOUTH()));
		assertTrue(bsw_tse.hasNbr(Side.NORTH()));
		assertTrue(bsw_tse.hasNbr(Side.BOTTOM()));
		assertFalse(bsw_tse.hasNbr(Side.TOP()));

		assertEquals(bsw_tsw.getId(), bsw_tse.getNbrId(Side.WEST()));
		assertEquals(bsw_tne.getId(), bsw_tse.getNbrId(Side.NORTH()));
		assertEquals(bsw_bse.getId(), bsw_tse.getNbrId(Side.BOTTOM()));

		assertFalse(bsw_tse.hasNbr(Edge.BS()));
		assertFalse(bsw_tse.hasNbr(Edge.TN()));
		assertTrue(bsw_tse.hasNbr(Edge.BN()));
		assertFalse(bsw_tse.hasNbr(Edge.TS()));
		assertTrue(bsw_tse.hasNbr(Edge.BW()));
		assertFalse(bsw_tse.hasNbr(Edge.TE()));
		assertFalse(bsw_tse.hasNbr(Edge.BE()));
		assertFalse(bsw_tse.hasNbr(Edge.TW()));
		assertFalse(bsw_tse.hasNbr(Edge.SW()));
		assertFalse(bsw_tse.hasNbr(Edge.NE()));
		assertFalse(bsw_tse.hasNbr(Edge.SE()));
		assertTrue(bsw_tse.hasNbr(Edge.NW()));

		assertEquals(bsw_bne.getId(), bsw_tse.getNbrId(Edge.BN()));
		assertEquals(bsw_bsw.getId(), bsw_tse.getNbrId(Edge.BW()));
		assertEquals(bsw_tnw.getId(), bsw_tse.getNbrId(Edge.NW()));

		assertFalse(bsw_tse.hasNbr(Orthant.BSW()));
		assertFalse(bsw_tse.hasNbr(Orthant.BSE()));
		assertTrue(bsw_tse.hasNbr(Orthant.BNW()));
		assertFalse(bsw_tse.hasNbr(Orthant.BNE()));
		assertFalse(bsw_tse.hasNbr(Orthant.TSW()));
		assertFalse(bsw_tse.hasNbr(Orthant.TSE()));
		assertFalse(bsw_tse.hasNbr(Orthant.TNW()));
		assertFalse(bsw_tse.hasNbr(Orthant.TNE()));

		assertEquals(bsw_bnw.getId(), bsw_tse.getNbrId(Orthant.BNW()));

		// BSW_TNW child
		assertEquals(bsw_tnw.getId(), bsw.getChildId(Orthant.TNW()));
		assertTrue(bsw_tnw.hasParent());
		assertEquals(bsw.getId(), bsw_tnw.getParentId());
		assertFalse(bsw_tnw.hasChildren());

		assertFalse(bsw_tnw.hasNbr(Side.WEST()));
		assertTrue(bsw_tnw.hasNbr(Side.EAST()));
		assertTrue(bsw_tnw.hasNbr(Side.SOUTH()));
		assertFalse(bsw_tnw.hasNbr(Side.NORTH()));
		assertTrue(bsw_tnw.hasNbr(Side.BOTTOM()));
		assertFalse(bsw_tnw.hasNbr(Side.TOP()));

		assertEquals(bsw_tne.getId(), bsw_tnw.getNbrId(Side.EAST()));
		assertEquals(bsw_tsw.getId(), bsw_tnw.getNbrId(Side.SOUTH()));
		assertEquals(bsw_bnw.getId(), bsw_tnw.getNbrId(Side.BOTTOM()));

		assertTrue(bsw_tnw.hasNbr(Edge.BS()));
		assertFalse(bsw_tnw.hasNbr(Edge.TN()));
		assertFalse(bsw_tnw.hasNbr(Edge.BN()));
		assertFalse(bsw_tnw.hasNbr(Edge.TS()));
		assertFalse(bsw_tnw.hasNbr(Edge.BW()));
		assertFalse(bsw_tnw.hasNbr(Edge.TE()));
		assertTrue(bsw_tnw.hasNbr(Edge.BE()));
		assertFalse(bsw_tnw.hasNbr(Edge.TW()));
		assertFalse(bsw_tnw.hasNbr(Edge.SW()));
		assertFalse(bsw_tnw.hasNbr(Edge.NE()));
		assertTrue(bsw_tnw.hasNbr(Edge.SE()));
		assertFalse(bsw_tnw.hasNbr(Edge.NW()));

		assertEquals(bsw_bsw.getId(), bsw_tnw.getNbrId(Edge.BS()));
		assertEquals(bsw_bne.getId(), bsw_tnw.getNbrId(Edge.BE()));
		assertEquals(bsw_tse.getId(), bsw_tnw.getNbrId(Edge.SE()));

		assertFalse(bsw_tnw.hasNbr(Orthant.BSW()));
		assertTrue(bsw_tnw.hasNbr(Orthant.BSE()));
		assertFalse(bsw_tnw.hasNbr(Orthant.BNW()));
		assertFalse(bsw_tnw.hasNbr(Orthant.BNE()));
		assertFalse(bsw_tnw.hasNbr(Orthant.TSW()));
		assertFalse(bsw_tnw.hasNbr(Orthant.TSE()));
		assertFalse(bsw_tnw.hasNbr(Orthant.TNW()));
		assertFalse(bsw_tnw.hasNbr(Orthant.TNE()));

		assertEquals(bsw_bse.getId(), bsw_tnw.getNbrId(Orthant.BSE()));

		// BSW_TNE child
		assertEquals(bsw_tne.getId(), bsw.getChildId(Orthant.TNE()));
		assertTrue(bsw_tne.hasParent());
		assertEquals(bsw.getId(), bsw_tne.getParentId());
		assertFalse(bsw_tne.hasChildren());

		assertTrue(bsw_tne.hasNbr(Side.WEST()));
		assertFalse(bsw_tne.hasNbr(Side.EAST()));
		assertTrue(bsw_tne.hasNbr(Side.SOUTH()));
		assertFalse(bsw_tne.hasNbr(Side.NORTH()));
		assertTrue(bsw_tne.hasNbr(Side.BOTTOM()));
		assertFalse(bsw_tne.hasNbr(Side.TOP()));

		assertEquals(bsw_tnw.getId(), bsw_tne.getNbrId(Side.WEST()));
		assertEquals(bsw_tse.getId(), bsw_tne.getNbrId(Side.SOUTH()));
		assertEquals(bsw_bne.getId(), bsw_tne.getNbrId(Side.BOTTOM()));

		assertTrue(bsw_tne.hasNbr(Edge.BS()));
		assertFalse(bsw_tne.hasNbr(Edge.TN()));
		assertFalse(bsw_tne.hasNbr(Edge.BN()));
		assertFalse(bsw_tne.hasNbr(Edge.TS()));
		assertTrue(bsw_tne.hasNbr(Edge.BW()));
		assertFalse(bsw_tne.hasNbr(Edge.TE()));
		assertFalse(bsw_tne.hasNbr(Edge.BE()));
		assertFalse(bsw_tne.hasNbr(Edge.TW()));
		assertTrue(bsw_tne.hasNbr(Edge.SW()));
		assertFalse(bsw_tne.hasNbr(Edge.NE()));
		assertFalse(bsw_tne.hasNbr(Edge.SE()));
		assertFalse(bsw_tne.hasNbr(Edge.NW()));

		assertEquals(bsw_bse.getId(), bsw_tne.getNbrId(Edge.BS()));
		assertEquals(bsw_bnw.getId(), bsw_tne.getNbrId(Edge.BW()));
		assertEquals(bsw_tsw.getId(), bsw_tne.getNbrId(Edge.SW()));

		assertTrue(bsw_tne.hasNbr(Orthant.BSW()));
		assertFalse(bsw_tne.hasNbr(Orthant.BSE()));
		assertFalse(bsw_tne.hasNbr(Orthant.BNW()));
		assertFalse(bsw_tne.hasNbr(Orthant.BNE()));
		assertFalse(bsw_tne.hasNbr(Orthant.TSW()));
		assertFalse(bsw_tne.hasNbr(Orthant.TSE()));
		assertFalse(bsw_tne.hasNbr(Orthant.TNW()));
		assertFalse(bsw_tne.hasNbr(Orthant.TNE()));

		assertEquals(bsw_bsw.getId(), bsw_tne.getNbrId(Orthant.BSW()));

		// BSE child
		assertEquals(bse.getId(), root.getChildId(Orthant.BSE()));
		assertTrue(bse.hasParent());
		assertEquals(root.getId(), bse.getParentId());
		assertFalse(bse.hasChildren());

		assertTrue(bse.hasNbr(Side.WEST()));
		assertFalse(bse.hasNbr(Side.EAST()));
		assertFalse(bse.hasNbr(Side.SOUTH()));
		assertTrue(bse.hasNbr(Side.NORTH()));
		assertFalse(bse.hasNbr(Side.BOTTOM()));
		assertTrue(bse.hasNbr(Side.TOP()));

		assertEquals(bsw.getId(), bse.getNbrId(Side.WEST()));
		assertEquals(bne.getId(), bse.getNbrId(Side.NORTH()));
		assertEquals(tse.getId(), bse.getNbrId(Side.TOP()));

		assertFalse(bse.hasNbr(Edge.BS()));
		assertTrue(bse.hasNbr(Edge.TN()));
		assertFalse(bse.hasNbr(Edge.BN()));
		assertFalse(bse.hasNbr(Edge.TS()));
		assertFalse(bse.hasNbr(Edge.BW()));
		assertFalse(bse.hasNbr(Edge.TE()));
		assertFalse(bse.hasNbr(Edge.BE()));
		assertTrue(bse.hasNbr(Edge.TW()));
		assertFalse(bse.hasNbr(Edge.SW()));
		assertFalse(bse.hasNbr(Edge.NE()));
		assertFalse(bse.hasNbr(Edge.SE()));
		assertTrue(bse.hasNbr(Edge.NW()));

		assertEquals(tne.getId(), bse.getNbrId(Edge.TN()));
		assertEquals(tsw.getId(), bse.getNbrId(Edge.TW()));
		assertEquals(bnw.getId(), bse.getNbrId(Edge.NW()));

		assertFalse(bse.hasNbr(Orthant.BSW()));
		assertFalse(bse.hasNbr(Orthant.BSE()));
		assertFalse(bse.hasNbr(Orthant.BNW()));
		assertFalse(bse.hasNbr(Orthant.BNE()));
		assertFalse(bse.hasNbr(Orthant.TSW()));
		assertFalse(bse.hasNbr(Orthant.TSE()));
		assertTrue(bse.hasNbr(Orthant.TNW()));
		assertFalse(bse.hasNbr(Orthant.TNE()));

		assertEquals(tnw.getId(), bse.getNbrId(Orthant.TNW()));

		// BNW child
		assertEquals(bnw.getId(), root.getChildId(Orthant.BNW()));
		assertTrue(bnw.hasParent());
		assertEquals(root.getId(), bnw.getParentId());
		assertFalse(bnw.hasChildren());

		assertFalse(bnw.hasNbr(Side.WEST()));
		assertTrue(bnw.hasNbr(Side.EAST()));
		assertTrue(bnw.hasNbr(Side.SOUTH()));
		assertFalse(bnw.hasNbr(Side.NORTH()));
		assertFalse(bnw.hasNbr(Side.BOTTOM()));
		assertTrue(bnw.hasNbr(Side.TOP()));

		assertEquals(bne.getId(), bnw.getNbrId(Side.EAST()));
		assertEquals(bsw.getId(), bnw.getNbrId(Side.SOUTH()));
		assertEquals(tnw.getId(), bnw.getNbrId(Side.TOP()));

		assertFalse(bnw.hasNbr(Edge.BS()));
		assertFalse(bnw.hasNbr(Edge.TN()));
		assertFalse(bnw.hasNbr(Edge.BN()));
		assertTrue(bnw.hasNbr(Edge.TS()));
		assertFalse(bnw.hasNbr(Edge.BW()));
		assertTrue(bnw.hasNbr(Edge.TE()));
		assertFalse(bnw.hasNbr(Edge.BE()));
		assertFalse(bnw.hasNbr(Edge.TW()));
		assertFalse(bnw.hasNbr(Edge.SW()));
		assertFalse(bnw.hasNbr(Edge.NE()));
		assertTrue(bnw.hasNbr(Edge.SE()));
		assertFalse(bnw.hasNbr(Edge.NW()));

		assertEquals(tsw.getId(), bnw.getNbrId(Edge.TS()));
		assertEquals(tne.getId(), bnw.getNbrId(Edge.TE()));
		assertEquals(bse.getId(), bnw.getNbrId(Edge.SE()));

		assertFalse(bnw.hasNbr(Orthant.BSW()));
		assertFalse(bnw.hasNbr(Orthant.BSE()));
		assertFalse(bnw.hasNbr(Orthant.BNW()));
		assertFalse(bnw.hasNbr(Orthant.BNE()));
		assertFalse(bnw.hasNbr(Orthant.TSW()));
		assertTrue(bnw.hasNbr(Orthant.TSE()));
		assertFalse(bnw.hasNbr(Orthant.TNW()));
		assertFalse(bnw.hasNbr(Orthant.TNE()));

		assertEquals(tse.getId(), bnw.getNbrId(Orthant.TSE()));

		// BNE child
		assertEquals(bne.getId(), root.getChildId(Orthant.BNE()));
		assertTrue(bne.hasParent());
		assertEquals(root.getId(), bne.getParentId());
		assertFalse(bne.hasChildren());

		assertTrue(bne.hasNbr(Side.WEST()));
		assertFalse(bne.hasNbr(Side.EAST()));
		assertTrue(bne.hasNbr(Side.SOUTH()));
		assertFalse(bne.hasNbr(Side.NORTH()));
		assertFalse(bne.hasNbr(Side.BOTTOM()));
		assertTrue(bne.hasNbr(Side.TOP()));

		assertEquals(bnw.getId(), bne.getNbrId(Side.WEST()));
		assertEquals(bse.getId(), bne.getNbrId(Side.SOUTH()));
		assertEquals(tne.getId(), bne.getNbrId(Side.TOP()));

		assertFalse(bne.hasNbr(Edge.BS()));
		assertFalse(bne.hasNbr(Edge.TN()));
		assertFalse(bne.hasNbr(Edge.BN()));
		assertTrue(bne.hasNbr(Edge.TS()));
		assertFalse(bne.hasNbr(Edge.BW()));
		assertFalse(bne.hasNbr(Edge.TE()));
		assertFalse(bne.hasNbr(Edge.BE()));
		assertTrue(bne.hasNbr(Edge.TW()));
		assertTrue(bne.hasNbr(Edge.SW()));
		assertFalse(bne.hasNbr(Edge.NE()));
		assertFalse(bne.hasNbr(Edge.SE()));
		assertFalse(bne.hasNbr(Edge.NW()));

		assertEquals(tse.getId(), bne.getNbrId(Edge.TS()));
		assertEquals(tnw.getId(), bne.getNbrId(Edge.TW()));
		assertEquals(bsw.getId(), bne.getNbrId(Edge.SW()));

		assertFalse(bne.hasNbr(Orthant.BSW()));
		assertFalse(bne.hasNbr(Orthant.BSE()));
		assertFalse(bne.hasNbr(Orthant.BNW()));
		assertFalse(bne.hasNbr(Orthant.BNE()));
		assertTrue(bne.hasNbr(Orthant.TSW()));
		assertFalse(bne.hasNbr(Orthant.TSE()));
		assertFalse(bne.hasNbr(Orthant.TNW()));
		assertFalse(bne.hasNbr(Orthant.TNE()));

		assertEquals(tsw.getId(), bne.getNbrId(Orthant.TSW()));

		// TSW child
		assertEquals(tsw.getId(), root.getChildId(Orthant.TSW()));
		assertTrue(tsw.hasParent());
		assertEquals(root.getId(), tsw.getParentId());
		assertFalse(tsw.hasChildren());

		assertFalse(tsw.hasNbr(Side.WEST()));
		assertTrue(tsw.hasNbr(Side.EAST()));
		assertFalse(tsw.hasNbr(Side.SOUTH()));
		assertTrue(tsw.hasNbr(Side.NORTH()));
		assertTrue(tsw.hasNbr(Side.BOTTOM()));
		assertFalse(tsw.hasNbr(Side.TOP()));

		assertEquals(tse.getId(), tsw.getNbrId(Side.EAST()));
		assertEquals(tnw.getId(), tsw.getNbrId(Side.NORTH()));
		assertEquals(bsw.getId(), tsw.getNbrId(Side.BOTTOM()));

		assertFalse(tsw.hasNbr(Edge.BS()));
		assertFalse(tsw.hasNbr(Edge.TN()));
		assertTrue(tsw.hasNbr(Edge.BN()));
		assertFalse(tsw.hasNbr(Edge.TS()));
		assertFalse(tsw.hasNbr(Edge.BW()));
		assertFalse(tsw.hasNbr(Edge.TE()));
		assertTrue(tsw.hasNbr(Edge.BE()));
		assertFalse(tsw.hasNbr(Edge.TW()));
		assertFalse(tsw.hasNbr(Edge.SW()));
		assertTrue(tsw.hasNbr(Edge.NE()));
		assertFalse(tsw.hasNbr(Edge.SE()));
		assertFalse(tsw.hasNbr(Edge.NW()));

		assertEquals(bnw.getId(), tsw.getNbrId(Edge.BN()));
		assertEquals(bse.getId(), tsw.getNbrId(Edge.BE()));
		assertEquals(tne.getId(), tsw.getNbrId(Edge.NE()));

		assertFalse(tsw.hasNbr(Orthant.BSW()));
		assertFalse(tsw.hasNbr(Orthant.BSE()));
		assertFalse(tsw.hasNbr(Orthant.BNW()));
		assertTrue(tsw.hasNbr(Orthant.BNE()));
		assertFalse(tsw.hasNbr(Orthant.TSW()));
		assertFalse(tsw.hasNbr(Orthant.TSE()));
		assertFalse(tsw.hasNbr(Orthant.TNW()));
		assertFalse(tsw.hasNbr(Orthant.TNE()));

		assertEquals(bne.getId(), tsw.getNbrId(Orthant.BNE()));

		// TSE child
		assertEquals(tse.getId(), root.getChildId(Orthant.TSE()));
		assertTrue(tse.hasParent());
		assertEquals(root.getId(), tse.getParentId());
		assertFalse(tse.hasChildren());

		assertTrue(tse.hasNbr(Side.WEST()));
		assertFalse(tse.hasNbr(Side.EAST()));
		assertFalse(tse.hasNbr(Side.SOUTH()));
		assertTrue(tse.hasNbr(Side.NORTH()));
		assertTrue(tse.hasNbr(Side.BOTTOM()));
		assertFalse(tse.hasNbr(Side.TOP()));

		assertEquals(tsw.getId(), tse.getNbrId(Side.WEST()));
		assertEquals(tne.getId(), tse.getNbrId(Side.NORTH()));
		assertEquals(bse.getId(), tse.getNbrId(Side.BOTTOM()));

		assertFalse(tse.hasNbr(Edge.BS()));
		assertFalse(tse.hasNbr(Edge.TN()));
		assertTrue(tse.hasNbr(Edge.BN()));
		assertFalse(tse.hasNbr(Edge.TS()));
		assertTrue(tse.hasNbr(Edge.BW()));
		assertFalse(tse.hasNbr(Edge.TE()));
		assertFalse(tse.hasNbr(Edge.BE()));
		assertFalse(tse.hasNbr(Edge.TW()));
		assertFalse(tse.hasNbr(Edge.SW()));
		assertFalse(tse.hasNbr(Edge.NE()));
		assertFalse(tse.hasNbr(Edge.SE()));
		assertTrue(tse.hasNbr(Edge.NW()));

		assertEquals(bne.getId(), tse.getNbrId(Edge.BN()));
		assertEquals(bsw.getId(), tse.getNbrId(Edge.BW()));
		assertEquals(tnw.getId(), tse.getNbrId(Edge.NW()));

		assertFalse(tse.hasNbr(Orthant.BSW()));
		assertFalse(tse.hasNbr(Orthant.BSE()));
		assertTrue(tse.hasNbr(Orthant.BNW()));
		assertFalse(tse.hasNbr(Orthant.BNE()));
		assertFalse(tse.hasNbr(Orthant.TSW()));
		assertFalse(tse.hasNbr(Orthant.TSE()));
		assertFalse(tse.hasNbr(Orthant.TNW()));
		assertFalse(tse.hasNbr(Orthant.TNE()));

		assertEquals(bnw.getId(), tse.getNbrId(Orthant.BNW()));

		// TNW child
		assertEquals(tnw.getId(), root.getChildId(Orthant.TNW()));
		assertTrue(tnw.hasParent());
		assertEquals(root.getId(), tnw.getParentId());
		assertFalse(tnw.hasChildren());

		assertFalse(tnw.hasNbr(Side.WEST()));
		assertTrue(tnw.hasNbr(Side.EAST()));
		assertTrue(tnw.hasNbr(Side.SOUTH()));
		assertFalse(tnw.hasNbr(Side.NORTH()));
		assertTrue(tnw.hasNbr(Side.BOTTOM()));
		assertFalse(tnw.hasNbr(Side.TOP()));

		assertEquals(tne.getId(), tnw.getNbrId(Side.EAST()));
		assertEquals(tsw.getId(), tnw.getNbrId(Side.SOUTH()));
		assertEquals(bnw.getId(), tnw.getNbrId(Side.BOTTOM()));

		assertTrue(tnw.hasNbr(Edge.BS()));
		assertFalse(tnw.hasNbr(Edge.TN()));
		assertFalse(tnw.hasNbr(Edge.BN()));
		assertFalse(tnw.hasNbr(Edge.TS()));
		assertFalse(tnw.hasNbr(Edge.BW()));
		assertFalse(tnw.hasNbr(Edge.TE()));
		assertTrue(tnw.hasNbr(Edge.BE()));
		assertFalse(tnw.hasNbr(Edge.TW()));
		assertFalse(tnw.hasNbr(Edge.SW()));
		assertFalse(tnw.hasNbr(Edge.NE()));
		assertTrue(tnw.hasNbr(Edge.SE()));
		assertFalse(tnw.hasNbr(Edge.NW()));

		assertEquals(bsw.getId(), tnw.getNbrId(Edge.BS()));
		assertEquals(bne.getId(), tnw.getNbrId(Edge.BE()));
		assertEquals(tse.getId(), tnw.getNbrId(Edge.SE()));

		assertFalse(tnw.hasNbr(Orthant.BSW()));
		assertTrue(tnw.hasNbr(Orthant.BSE()));
		assertFalse(tnw.hasNbr(Orthant.BNW()));
		assertFalse(tnw.hasNbr(Orthant.BNE()));
		assertFalse(tnw.hasNbr(Orthant.TSW()));
		assertFalse(tnw.hasNbr(Orthant.TSE()));
		assertFalse(tnw.hasNbr(Orthant.TNW()));
		assertFalse(tnw.hasNbr(Orthant.TNE()));

		assertEquals(bse.getId(), tnw.getNbrId(Orthant.BSE()));

		// TNE child
		assertEquals(tne.getId(), root.getChildId(Orthant.TNE()));
		assertTrue(tne.hasParent());
		assertEquals(root.getId(), tne.getParentId());
		assertFalse(tne.hasChildren());

		assertTrue(tne.hasNbr(Side.WEST()));
		assertFalse(tne.hasNbr(Side.EAST()));
		assertTrue(tne.hasNbr(Side.SOUTH()));
		assertFalse(tne.hasNbr(Side.NORTH()));
		assertTrue(tne.hasNbr(Side.BOTTOM()));
		assertFalse(tne.hasNbr(Side.TOP()));

		assertEquals(tnw.getId(), tne.getNbrId(Side.WEST()));
		assertEquals(tse.getId(), tne.getNbrId(Side.SOUTH()));
		assertEquals(bne.getId(), tne.getNbrId(Side.BOTTOM()));

		assertTrue(tne.hasNbr(Edge.BS()));
		assertFalse(tne.hasNbr(Edge.TN()));
		assertFalse(tne.hasNbr(Edge.BN()));
		assertFalse(tne.hasNbr(Edge.TS()));
		assertTrue(tne.hasNbr(Edge.BW()));
		assertFalse(tne.hasNbr(Edge.TE()));
		assertFalse(tne.hasNbr(Edge.BE()));
		assertFalse(tne.hasNbr(Edge.TW()));
		assertTrue(tne.hasNbr(Edge.SW()));
		assertFalse(tne.hasNbr(Edge.NE()));
		assertFalse(tne.hasNbr(Edge.SE()));
		assertFalse(tne.hasNbr(Edge.NW()));

		assertEquals(bse.getId(), tne.getNbrId(Edge.BS()));
		assertEquals(bnw.getId(), tne.getNbrId(Edge.BW()));
		assertEquals(tsw.getId(), tne.getNbrId(Edge.SW()));

		assertTrue(tne.hasNbr(Orthant.BSW()));
		assertFalse(tne.hasNbr(Orthant.BSE()));
		assertFalse(tne.hasNbr(Orthant.BNW()));
		assertFalse(tne.hasNbr(Orthant.BNE()));
		assertFalse(tne.hasNbr(Orthant.TSW()));
		assertFalse(tne.hasNbr(Orthant.TSE()));
		assertFalse(tne.hasNbr(Orthant.TNW()));
		assertFalse(tne.hasNbr(Orthant.TNE()));

		assertEquals(bsw.getId(), tne.getNbrId(Orthant.BSW()));
	}

	@Test
	void RefineNodeGood() {
		Forest forest = new Forest(2);
		forest.refineNode(0);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 1);

		Node root = forest.getNode(0);
		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		// check the children
		for (Orthant o : Orthant.getValuesForDimension(2)) {
			int child_id = root.getChildId(o);
			Node child = forest.getNode(child_id);
			assertEquals(child.getId(), child_id);
			assertTrue(child.hasParent());
			assertFalse(child.hasChildren());
			for (Side s : o.getInteriorSides()) {
				assertTrue(child.hasNbr(s));
				assertEquals(child.getNbrId(s), root.getChildId(o.getNbrOnSide(s)));
			}
			for (Side s : o.getExteriorSides()) {
				assertFalse(child.hasNbr(s));
			}

			for (Orthant c : Orthant.getValuesForDimension(2)) {
				if (c.equals(o.getOpposite())) {
					assertTrue(child.hasNbr(c));
					assertEquals(child.getNbrId(c), root.getChildId(c));
				} else {
					assertFalse(child.hasNbr(c));
				}
			}
		}
	}

	@Test
	void RefineNodeNonLeaf() {
		Forest forest = new Forest(2);
		forest.refineNode(0);
		forest.refineNode(0);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 1);

		Node root = forest.getNode(0);
		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		// check the children
		for (Orthant o : Orthant.getValuesForDimension(2)) {
			int child_id = root.getChildId(o);
			Node child = forest.getNode(child_id);

			assertEquals(child.getId(), child_id);
			assertTrue(child.hasParent());
			assertFalse(child.hasChildren());

			for (Side s : o.getInteriorSides()) {
				assertTrue(child.hasNbr(s));
				assertEquals(child.getNbrId(s), root.getChildId(o.getNbrOnSide(s)));
			}

			for (Side s : o.getExteriorSides()) {
				assertFalse(child.hasNbr(s));
			}
			for (Orthant c : Orthant.getValuesForDimension(2)) {
				if (c.equals(o.getOpposite())) {
					assertTrue(child.hasNbr(c));
					assertEquals(child.getNbrId(c), root.getChildId(c));
				} else {
					assertFalse(child.hasNbr(c));
				}
			}
		}
	}

	@Test
	void RefineNodeTwice() {
		Forest forest = new Forest(2);
		Node root = forest.getRootNode();
		forest.refineNode(root.getId());
		forest.refineNode(root.getChildId(Orthant.SW()));

		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 2);

		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		// check the children
		for (Orthant o : Orthant.getValuesForDimension(2)) {
			int child_id = root.getChildId(o);
			Node child = forest.getNode(child_id);
			assertEquals(child.getId(), child_id);
			assertTrue(child.hasParent());
			for (Side s : o.getInteriorSides()) {
				assertTrue(child.hasNbr(s));
				assertEquals(child.getNbrId(s), root.getChildId(o.getNbrOnSide(s)));
			}
			for (Side s : o.getExteriorSides()) {
				assertFalse(child.hasNbr(s));
			}
			for (Orthant c : Orthant.getValuesForDimension(2)) {
				if (c.equals(o.getOpposite())) {
					assertTrue(child.hasNbr(c));
					assertEquals(child.getNbrId(c), root.getChildId(c));
				} else {
					assertFalse(child.hasNbr(c));
				}
			}

			if (o.equals(Orthant.SW())) {
				assertTrue(child.hasChildren());
				for (Orthant child_o : Orthant.getValuesForDimension(2)) {
					int child_child_id = child.getChildId(child_o);
					Node child_child = forest.getNode(child_child_id);
					assertEquals(child_child.getId(), child_child_id);
					assertTrue(child_child.hasParent());
					for (Side s : child_o.getInteriorSides()) {
						assertTrue(child_child.hasNbr(s));
						assertEquals(child_child.getNbrId(s), child.getChildId(child_o.getNbrOnSide(s)));
					}
					for (Side s : child_o.getExteriorSides()) {
						assertFalse(child_child.hasNbr(s));
					}
					for (Orthant c : Orthant.getValuesForDimension(2)) {
						if (c.equals(child_o.getOpposite())) {
							assertTrue(child_child.hasNbr(c));
							assertEquals(child_child.getNbrId(c), child.getChildId(c));
						} else {
							assertFalse(child_child.hasNbr(c));
						}
					}
				}
			} else {
				assertFalse(child.hasChildren());
			}
		}
	}

	@Test
	void RefineNodeThrice() {
		Forest forest = new Forest(2);
		Node root = forest.getRootNode();
		forest.refineNode(root.getId());
		Node child = forest.getNode(root.getChildId(Orthant.SW()));
		forest.refineNode(child.getId());
		forest.refineNode(child.getChildId(Orthant.NE()));

		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 3);

		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		Node sw = forest.getNode(root.getChildId(Orthant.SW()));
		Node sw_sw = forest.getNode(sw.getChildId(Orthant.SW()));
		Node sw_se = forest.getNode(sw.getChildId(Orthant.SE()));
		Node sw_nw = forest.getNode(sw.getChildId(Orthant.NW()));
		Node sw_ne = forest.getNode(sw.getChildId(Orthant.NE()));
		Node sw_ne_sw = forest.getNode(sw_ne.getChildId(Orthant.SW()));
		Node sw_ne_se = forest.getNode(sw_ne.getChildId(Orthant.SE()));
		Node sw_ne_nw = forest.getNode(sw_ne.getChildId(Orthant.NW()));
		Node sw_ne_ne = forest.getNode(sw_ne.getChildId(Orthant.NE()));
		Node se = forest.getNode(root.getChildId(Orthant.SE()));
		Node se_sw = forest.getNode(se.getChildId(Orthant.SW()));
		Node se_se = forest.getNode(se.getChildId(Orthant.SE()));
		Node se_nw = forest.getNode(se.getChildId(Orthant.NW()));
		Node se_ne = forest.getNode(se.getChildId(Orthant.NE()));
		Node nw = forest.getNode(root.getChildId(Orthant.NW()));
		Node nw_sw = forest.getNode(nw.getChildId(Orthant.SW()));
		Node nw_se = forest.getNode(nw.getChildId(Orthant.SE()));
		Node nw_nw = forest.getNode(nw.getChildId(Orthant.NW()));
		Node nw_ne = forest.getNode(nw.getChildId(Orthant.NE()));
		Node ne = forest.getNode(root.getChildId(Orthant.NE()));
		Node ne_sw = forest.getNode(ne.getChildId(Orthant.SW()));
		Node ne_se = forest.getNode(ne.getChildId(Orthant.SE()));
		Node ne_nw = forest.getNode(ne.getChildId(Orthant.NW()));
		Node ne_ne = forest.getNode(ne.getChildId(Orthant.NE()));

		// SW child
		assertTrue(sw.hasParent());
		assertEquals(root.getId(), sw.getParentId());
		assertTrue(sw.hasChildren());

		assertEquals(sw_sw.getId(), sw.getChildId(Orthant.SW()));
		assertEquals(sw_se.getId(), sw.getChildId(Orthant.SE()));
		assertEquals(sw_nw.getId(), sw.getChildId(Orthant.NW()));
		assertEquals(sw_ne.getId(), sw.getChildId(Orthant.NE()));

		assertFalse(sw.hasNbr(Side.WEST()));
		assertTrue(sw.hasNbr(Side.EAST()));
		assertFalse(sw.hasNbr(Side.SOUTH()));
		assertTrue(sw.hasNbr(Side.NORTH()));

		assertEquals(se.getId(), sw.getNbrId(Side.EAST()));
		assertEquals(nw.getId(), sw.getNbrId(Side.NORTH()));

		assertFalse(sw.hasNbr(Orthant.SW()));
		assertFalse(sw.hasNbr(Orthant.SE()));
		assertFalse(sw.hasNbr(Orthant.NW()));
		assertTrue(sw.hasNbr(Orthant.NE()));

		assertEquals(ne.getId(), sw.getNbrId(Orthant.NE()));

		// SW_SW child
		assertEquals(sw_sw.getId(), sw.getChildId(Orthant.SW()));
		assertTrue(sw_sw.hasParent());
		assertEquals(sw.getId(), sw_sw.getParentId());
		assertFalse(sw_sw.hasChildren());

		assertFalse(sw_sw.hasNbr(Side.WEST()));
		assertTrue(sw_sw.hasNbr(Side.EAST()));
		assertFalse(sw_sw.hasNbr(Side.SOUTH()));
		assertTrue(sw_sw.hasNbr(Side.NORTH()));

		assertEquals(sw_se.getId(), sw_sw.getNbrId(Side.EAST()));
		assertEquals(sw_nw.getId(), sw_sw.getNbrId(Side.NORTH()));

		assertFalse(sw_sw.hasNbr(Orthant.SW()));
		assertFalse(sw_sw.hasNbr(Orthant.SE()));
		assertFalse(sw_sw.hasNbr(Orthant.NW()));
		assertTrue(sw_sw.hasNbr(Orthant.NE()));

		assertEquals(sw_ne.getId(), sw_sw.getNbrId(Orthant.NE()));

		// SW_SE child
		assertEquals(sw_se.getId(), sw.getChildId(Orthant.SE()));
		assertTrue(sw_se.hasParent());
		assertEquals(sw.getId(), sw_se.getParentId());
		assertFalse(sw_se.hasChildren());

		assertTrue(sw_se.hasNbr(Side.WEST()));
		assertTrue(sw_se.hasNbr(Side.EAST()));
		assertFalse(sw_se.hasNbr(Side.SOUTH()));
		assertTrue(sw_se.hasNbr(Side.NORTH()));

		assertEquals(sw_sw.getId(), sw_se.getNbrId(Side.WEST()));
		assertEquals(se_sw.getId(), sw_se.getNbrId(Side.EAST()));
		assertEquals(sw_ne.getId(), sw_se.getNbrId(Side.NORTH()));

		assertFalse(sw_se.hasNbr(Orthant.SW()));
		assertFalse(sw_se.hasNbr(Orthant.SE()));
		assertTrue(sw_se.hasNbr(Orthant.NW()));
		assertTrue(sw_se.hasNbr(Orthant.NE()));

		assertEquals(sw_nw.getId(), sw_se.getNbrId(Orthant.NW()));
		assertEquals(se_nw.getId(), sw_se.getNbrId(Orthant.NE()));

		// SW_NW child
		assertEquals(sw_nw.getId(), sw.getChildId(Orthant.NW()));
		assertTrue(sw_nw.hasParent());
		assertEquals(sw.getId(), sw_nw.getParentId());
		assertFalse(sw_nw.hasChildren());

		assertFalse(sw_nw.hasNbr(Side.WEST()));
		assertTrue(sw_nw.hasNbr(Side.EAST()));
		assertTrue(sw_nw.hasNbr(Side.SOUTH()));
		assertTrue(sw_nw.hasNbr(Side.NORTH()));

		assertEquals(sw_ne.getId(), sw_nw.getNbrId(Side.EAST()));
		assertEquals(sw_sw.getId(), sw_nw.getNbrId(Side.SOUTH()));
		assertEquals(nw_sw.getId(), sw_nw.getNbrId(Side.NORTH()));

		assertFalse(sw_nw.hasNbr(Orthant.SW()));
		assertTrue(sw_nw.hasNbr(Orthant.SE()));
		assertFalse(sw_nw.hasNbr(Orthant.NW()));
		assertTrue(sw_nw.hasNbr(Orthant.NE()));

		assertEquals(sw_se.getId(), sw_nw.getNbrId(Orthant.SE()));
		assertEquals(nw_se.getId(), sw_nw.getNbrId(Orthant.NE()));

		// SW_NE child
		assertEquals(sw_ne.getId(), sw.getChildId(Orthant.NE()));
		assertTrue(sw_ne.hasParent());
		assertEquals(sw.getId(), sw_ne.getParentId());
		assertTrue(sw_ne.hasChildren());

		assertEquals(sw_ne_sw.getId(), sw_ne.getChildId(Orthant.SW()));
		assertEquals(sw_ne_se.getId(), sw_ne.getChildId(Orthant.SE()));
		assertEquals(sw_ne_nw.getId(), sw_ne.getChildId(Orthant.NW()));
		assertEquals(sw_ne_ne.getId(), sw_ne.getChildId(Orthant.NE()));

		assertTrue(sw_ne.hasNbr(Side.WEST()));
		assertTrue(sw_ne.hasNbr(Side.EAST()));
		assertTrue(sw_ne.hasNbr(Side.SOUTH()));
		assertTrue(sw_ne.hasNbr(Side.NORTH()));

		assertEquals(sw_nw.getId(), sw_ne.getNbrId(Side.WEST()));
		assertEquals(se_nw.getId(), sw_ne.getNbrId(Side.EAST()));
		assertEquals(sw_se.getId(), sw_ne.getNbrId(Side.SOUTH()));
		assertEquals(nw_se.getId(), sw_ne.getNbrId(Side.NORTH()));

		assertTrue(sw_ne.hasNbr(Orthant.SW()));
		assertTrue(sw_ne.hasNbr(Orthant.SE()));
		assertTrue(sw_ne.hasNbr(Orthant.NW()));
		assertTrue(sw_ne.hasNbr(Orthant.NE()));

		assertEquals(sw_sw.getId(), sw_ne.getNbrId(Orthant.SW()));
		assertEquals(se_sw.getId(), sw_ne.getNbrId(Orthant.SE()));
		assertEquals(nw_sw.getId(), sw_ne.getNbrId(Orthant.NW()));
		assertEquals(ne_sw.getId(), sw_ne.getNbrId(Orthant.NE()));

		// SW_NE_SW child
		assertEquals(sw_ne_sw.getId(), sw_ne.getChildId(Orthant.SW()));
		assertTrue(sw_ne_sw.hasParent());
		assertEquals(sw_ne.getId(), sw_ne_sw.getParentId());
		assertFalse(sw_ne_sw.hasChildren());

		assertFalse(sw_ne_sw.hasNbr(Side.WEST()));
		assertTrue(sw_ne_sw.hasNbr(Side.EAST()));
		assertFalse(sw_ne_sw.hasNbr(Side.SOUTH()));
		assertTrue(sw_ne_sw.hasNbr(Side.NORTH()));

		assertEquals(sw_ne_se.getId(), sw_ne_sw.getNbrId(Side.EAST()));
		assertEquals(sw_ne_nw.getId(), sw_ne_sw.getNbrId(Side.NORTH()));

		assertFalse(sw_ne_sw.hasNbr(Orthant.SW()));
		assertFalse(sw_ne_sw.hasNbr(Orthant.SE()));
		assertFalse(sw_ne_sw.hasNbr(Orthant.NW()));
		assertTrue(sw_ne_sw.hasNbr(Orthant.NE()));

		assertEquals(sw_ne_ne.getId(), sw_ne_sw.getNbrId(Orthant.NE()));

		// SW_NE_SE child
		assertEquals(sw_ne_se.getId(), sw_ne.getChildId(Orthant.SE()));
		assertTrue(sw_ne_se.hasParent());
		assertEquals(sw_ne.getId(), sw_ne_se.getParentId());
		assertFalse(sw_ne_se.hasChildren());

		assertTrue(sw_ne_se.hasNbr(Side.WEST()));
		assertFalse(sw_ne_se.hasNbr(Side.EAST()));
		assertFalse(sw_ne_se.hasNbr(Side.SOUTH()));
		assertTrue(sw_ne_se.hasNbr(Side.NORTH()));

		assertEquals(sw_ne_sw.getId(), sw_ne_se.getNbrId(Side.WEST()));
		assertEquals(sw_ne_ne.getId(), sw_ne_se.getNbrId(Side.NORTH()));

		assertFalse(sw_ne_se.hasNbr(Orthant.SW()));
		assertFalse(sw_ne_se.hasNbr(Orthant.SE()));
		assertTrue(sw_ne_se.hasNbr(Orthant.NW()));
		assertFalse(sw_ne_se.hasNbr(Orthant.NE()));

		assertEquals(sw_ne_nw.getId(), sw_ne_se.getNbrId(Orthant.NW()));

		// SW_NE_NW child
		assertEquals(sw_ne_nw.getId(), sw_ne.getChildId(Orthant.NW()));
		assertTrue(sw_ne_nw.hasParent());
		assertEquals(sw_ne.getId(), sw_ne_nw.getParentId());
		assertFalse(sw_ne_nw.hasChildren());

		assertFalse(sw_ne_nw.hasNbr(Side.WEST()));
		assertTrue(sw_ne_nw.hasNbr(Side.EAST()));
		assertTrue(sw_ne_nw.hasNbr(Side.SOUTH()));
		assertFalse(sw_ne_nw.hasNbr(Side.NORTH()));

		assertEquals(sw_ne_ne.getId(), sw_ne_nw.getNbrId(Side.EAST()));
		assertEquals(sw_ne_sw.getId(), sw_ne_nw.getNbrId(Side.SOUTH()));

		assertFalse(sw_ne_nw.hasNbr(Orthant.SW()));
		assertTrue(sw_ne_nw.hasNbr(Orthant.SE()));
		assertFalse(sw_ne_nw.hasNbr(Orthant.NW()));
		assertFalse(sw_ne_nw.hasNbr(Orthant.NE()));

		assertEquals(sw_ne_se.getId(), sw_ne_nw.getNbrId(Orthant.SE()));

		// SW_NE_NE child
		assertEquals(sw_ne_ne.getId(), sw_ne.getChildId(Orthant.NE()));
		assertTrue(sw_ne_ne.hasParent());
		assertEquals(sw_ne.getId(), sw_ne_ne.getParentId());
		assertFalse(sw_ne_ne.hasChildren());

		assertTrue(sw_ne_ne.hasNbr(Side.WEST()));
		assertFalse(sw_ne_ne.hasNbr(Side.EAST()));
		assertTrue(sw_ne_ne.hasNbr(Side.SOUTH()));
		assertFalse(sw_ne_ne.hasNbr(Side.NORTH()));

		assertEquals(sw_ne_nw.getId(), sw_ne_ne.getNbrId(Side.WEST()));
		assertEquals(sw_ne_se.getId(), sw_ne_ne.getNbrId(Side.SOUTH()));

		assertTrue(sw_ne_ne.hasNbr(Orthant.SW()));
		assertFalse(sw_ne_ne.hasNbr(Orthant.SE()));
		assertFalse(sw_ne_ne.hasNbr(Orthant.NW()));
		assertFalse(sw_ne_ne.hasNbr(Orthant.NE()));

		assertEquals(sw_ne_sw.getId(), sw_ne_ne.getNbrId(Orthant.SW()));

		// SE child
		assertEquals(se.getId(), root.getChildId(Orthant.SE()));
		assertTrue(se.hasParent());
		assertEquals(root.getId(), se.getParentId());
		assertTrue(se.hasChildren());

		assertEquals(se_sw.getId(), se.getChildId(Orthant.SW()));
		assertEquals(se_se.getId(), se.getChildId(Orthant.SE()));
		assertEquals(se_nw.getId(), se.getChildId(Orthant.NW()));
		assertEquals(se_ne.getId(), se.getChildId(Orthant.NE()));

		assertTrue(se.hasNbr(Side.WEST()));
		assertFalse(se.hasNbr(Side.EAST()));
		assertFalse(se.hasNbr(Side.SOUTH()));
		assertTrue(se.hasNbr(Side.NORTH()));

		assertEquals(sw.getId(), se.getNbrId(Side.WEST()));
		assertEquals(ne.getId(), se.getNbrId(Side.NORTH()));

		assertFalse(se.hasNbr(Orthant.SW()));
		assertFalse(se.hasNbr(Orthant.SE()));
		assertTrue(se.hasNbr(Orthant.NW()));
		assertFalse(se.hasNbr(Orthant.NE()));

		assertEquals(nw.getId(), se.getNbrId(Orthant.NW()));

		// SE_SW child
		assertEquals(se_sw.getId(), se.getChildId(Orthant.SW()));
		assertTrue(se_sw.hasParent());
		assertEquals(se.getId(), se_sw.getParentId());
		assertFalse(se_sw.hasChildren());

		assertTrue(se_sw.hasNbr(Side.WEST()));
		assertTrue(se_sw.hasNbr(Side.EAST()));
		assertFalse(se_sw.hasNbr(Side.SOUTH()));
		assertTrue(se_sw.hasNbr(Side.NORTH()));

		assertEquals(sw_se.getId(), se_sw.getNbrId(Side.WEST()));
		assertEquals(se_se.getId(), se_sw.getNbrId(Side.EAST()));
		assertEquals(se_nw.getId(), se_sw.getNbrId(Side.NORTH()));

		assertFalse(se_sw.hasNbr(Orthant.SW()));
		assertFalse(se_sw.hasNbr(Orthant.SE()));
		assertTrue(se_sw.hasNbr(Orthant.NW()));
		assertTrue(se_sw.hasNbr(Orthant.NE()));

		assertEquals(sw_ne.getId(), se_sw.getNbrId(Orthant.NW()));
		assertEquals(se_ne.getId(), se_sw.getNbrId(Orthant.NE()));

		// SE_SE child
		assertEquals(se_se.getId(), se.getChildId(Orthant.SE()));
		assertTrue(se_se.hasParent());
		assertEquals(se.getId(), se_se.getParentId());
		assertFalse(se_se.hasChildren());

		assertTrue(se_se.hasNbr(Side.WEST()));
		assertFalse(se_se.hasNbr(Side.EAST()));
		assertFalse(se_se.hasNbr(Side.SOUTH()));
		assertTrue(se_se.hasNbr(Side.NORTH()));

		assertEquals(se_sw.getId(), se_se.getNbrId(Side.WEST()));
		assertEquals(se_ne.getId(), se_se.getNbrId(Side.NORTH()));

		assertFalse(se_se.hasNbr(Orthant.SW()));
		assertFalse(se_se.hasNbr(Orthant.SE()));
		assertTrue(se_se.hasNbr(Orthant.NW()));
		assertFalse(se_se.hasNbr(Orthant.NE()));

		assertEquals(se_nw.getId(), se_se.getNbrId(Orthant.NW()));

		// SE_NW child
		assertEquals(se_nw.getId(), se.getChildId(Orthant.NW()));
		assertTrue(se_nw.hasParent());
		assertEquals(se.getId(), se_nw.getParentId());
		assertFalse(se_nw.hasChildren());

		assertTrue(se_nw.hasNbr(Side.WEST()));
		assertTrue(se_nw.hasNbr(Side.EAST()));
		assertTrue(se_nw.hasNbr(Side.SOUTH()));
		assertTrue(se_nw.hasNbr(Side.NORTH()));

		assertEquals(sw_ne.getId(), se_nw.getNbrId(Side.WEST()));
		assertEquals(se_ne.getId(), se_nw.getNbrId(Side.EAST()));
		assertEquals(se_sw.getId(), se_nw.getNbrId(Side.SOUTH()));
		assertEquals(ne_sw.getId(), se_nw.getNbrId(Side.NORTH()));

		assertTrue(se_nw.hasNbr(Orthant.SW()));
		assertTrue(se_nw.hasNbr(Orthant.SE()));
		assertTrue(se_nw.hasNbr(Orthant.NW()));
		assertTrue(se_nw.hasNbr(Orthant.NE()));

		assertEquals(sw_se.getId(), se_nw.getNbrId(Orthant.SW()));
		assertEquals(se_se.getId(), se_nw.getNbrId(Orthant.SE()));
		assertEquals(nw_se.getId(), se_nw.getNbrId(Orthant.NW()));
		assertEquals(ne_se.getId(), se_nw.getNbrId(Orthant.NE()));

		// SE_NE child
		assertEquals(se_ne.getId(), se.getChildId(Orthant.NE()));
		assertTrue(se_ne.hasParent());
		assertEquals(se.getId(), se_ne.getParentId());
		assertFalse(se_ne.hasChildren());

		assertTrue(se_ne.hasNbr(Side.WEST()));
		assertFalse(se_ne.hasNbr(Side.EAST()));
		assertTrue(se_ne.hasNbr(Side.SOUTH()));
		assertTrue(se_ne.hasNbr(Side.NORTH()));

		assertEquals(se_nw.getId(), se_ne.getNbrId(Side.WEST()));
		assertEquals(se_se.getId(), se_ne.getNbrId(Side.SOUTH()));
		assertEquals(ne_se.getId(), se_ne.getNbrId(Side.NORTH()));

		assertTrue(se_ne.hasNbr(Orthant.SW()));
		assertFalse(se_ne.hasNbr(Orthant.SE()));
		assertTrue(se_ne.hasNbr(Orthant.NW()));
		assertFalse(se_ne.hasNbr(Orthant.NE()));

		assertEquals(se_sw.getId(), se_ne.getNbrId(Orthant.SW()));
		assertEquals(ne_sw.getId(), se_ne.getNbrId(Orthant.NW()));

		// NW child
		assertEquals(nw.getId(), root.getChildId(Orthant.NW()));
		assertTrue(nw.hasParent());
		assertEquals(root.getId(), nw.getParentId());
		assertTrue(nw.hasChildren());

		assertEquals(nw_sw.getId(), nw.getChildId(Orthant.SW()));
		assertEquals(nw_se.getId(), nw.getChildId(Orthant.SE()));
		assertEquals(nw_nw.getId(), nw.getChildId(Orthant.NW()));
		assertEquals(nw_ne.getId(), nw.getChildId(Orthant.NE()));

		assertFalse(nw.hasNbr(Side.WEST()));
		assertTrue(nw.hasNbr(Side.EAST()));
		assertTrue(nw.hasNbr(Side.SOUTH()));
		assertFalse(nw.hasNbr(Side.NORTH()));

		assertEquals(ne.getId(), nw.getNbrId(Side.EAST()));
		assertEquals(sw.getId(), nw.getNbrId(Side.SOUTH()));

		assertFalse(nw.hasNbr(Orthant.SW()));
		assertTrue(nw.hasNbr(Orthant.SE()));
		assertFalse(nw.hasNbr(Orthant.NW()));
		assertFalse(nw.hasNbr(Orthant.NE()));

		assertEquals(se.getId(), nw.getNbrId(Orthant.SE()));

		// NW_SW child
		assertEquals(nw_sw.getId(), nw.getChildId(Orthant.SW()));
		assertTrue(nw_sw.hasParent());
		assertEquals(nw.getId(), nw_sw.getParentId());
		assertFalse(nw_sw.hasChildren());

		assertFalse(nw_sw.hasNbr(Side.WEST()));
		assertTrue(nw_sw.hasNbr(Side.EAST()));
		assertTrue(nw_sw.hasNbr(Side.SOUTH()));
		assertTrue(nw_sw.hasNbr(Side.NORTH()));

		assertEquals(nw_se.getId(), nw_sw.getNbrId(Side.EAST()));
		assertEquals(sw_nw.getId(), nw_sw.getNbrId(Side.SOUTH()));
		assertEquals(nw_nw.getId(), nw_sw.getNbrId(Side.NORTH()));

		assertFalse(nw_sw.hasNbr(Orthant.SW()));
		assertTrue(nw_sw.hasNbr(Orthant.SE()));
		assertFalse(nw_sw.hasNbr(Orthant.NW()));
		assertTrue(nw_sw.hasNbr(Orthant.NE()));

		assertEquals(nw_ne.getId(), nw_sw.getNbrId(Orthant.NE()));
		assertEquals(sw_ne.getId(), nw_sw.getNbrId(Orthant.SE()));

		// NW_SE child
		assertEquals(nw_se.getId(), nw.getChildId(Orthant.SE()));
		assertTrue(nw_se.hasParent());
		assertEquals(nw.getId(), nw_se.getParentId());
		assertFalse(nw_se.hasChildren());

		assertTrue(nw_se.hasNbr(Side.WEST()));
		assertTrue(nw_se.hasNbr(Side.EAST()));
		assertTrue(nw_se.hasNbr(Side.SOUTH()));
		assertTrue(nw_se.hasNbr(Side.NORTH()));

		assertEquals(nw_sw.getId(), nw_se.getNbrId(Side.WEST()));
		assertEquals(ne_sw.getId(), nw_se.getNbrId(Side.EAST()));
		assertEquals(sw_ne.getId(), nw_se.getNbrId(Side.SOUTH()));
		assertEquals(nw_ne.getId(), nw_se.getNbrId(Side.NORTH()));

		assertTrue(nw_se.hasNbr(Orthant.SW()));
		assertTrue(nw_se.hasNbr(Orthant.SE()));
		assertTrue(nw_se.hasNbr(Orthant.NW()));
		assertTrue(nw_se.hasNbr(Orthant.NE()));

		assertEquals(sw_nw.getId(), nw_se.getNbrId(Orthant.SW()));
		assertEquals(se_nw.getId(), nw_se.getNbrId(Orthant.SE()));
		assertEquals(nw_nw.getId(), nw_se.getNbrId(Orthant.NW()));
		assertEquals(ne_nw.getId(), nw_se.getNbrId(Orthant.NE()));

		// NW_NW child
		assertEquals(nw_nw.getId(), nw.getChildId(Orthant.NW()));
		assertTrue(nw_nw.hasParent());
		assertEquals(nw.getId(), nw_nw.getParentId());
		assertFalse(nw_nw.hasChildren());

		assertFalse(nw_nw.hasNbr(Side.WEST()));
		assertTrue(nw_nw.hasNbr(Side.EAST()));
		assertTrue(nw_nw.hasNbr(Side.SOUTH()));
		assertFalse(nw_nw.hasNbr(Side.NORTH()));

		assertEquals(nw_ne.getId(), nw_nw.getNbrId(Side.EAST()));
		assertEquals(nw_sw.getId(), nw_nw.getNbrId(Side.SOUTH()));

		assertFalse(nw_nw.hasNbr(Orthant.SW()));
		assertTrue(nw_nw.hasNbr(Orthant.SE()));
		assertFalse(nw_nw.hasNbr(Orthant.NW()));
		assertFalse(nw_nw.hasNbr(Orthant.NE()));

		assertEquals(nw_se.getId(), nw_nw.getNbrId(Orthant.SE()));

		// NW_NE child
		assertEquals(nw_ne.getId(), nw.getChildId(Orthant.NE()));
		assertTrue(nw_ne.hasParent());
		assertEquals(nw.getId(), nw_ne.getParentId());
		assertFalse(nw_ne.hasChildren());

		assertTrue(nw_ne.hasNbr(Side.WEST()));
		assertTrue(nw_ne.hasNbr(Side.EAST()));
		assertTrue(nw_ne.hasNbr(Side.SOUTH()));
		assertFalse(nw_ne.hasNbr(Side.NORTH()));

		assertEquals(nw_nw.getId(), nw_ne.getNbrId(Side.WEST()));
		assertEquals(ne_nw.getId(), nw_ne.getNbrId(Side.EAST()));
		assertEquals(nw_se.getId(), nw_ne.getNbrId(Side.SOUTH()));

		assertTrue(nw_ne.hasNbr(Orthant.SW()));
		assertTrue(nw_ne.hasNbr(Orthant.SE()));
		assertFalse(nw_ne.hasNbr(Orthant.NW()));
		assertFalse(nw_ne.hasNbr(Orthant.NE()));

		assertEquals(nw_sw.getId(), nw_ne.getNbrId(Orthant.SW()));
		assertEquals(ne_sw.getId(), nw_ne.getNbrId(Orthant.SE()));

		// NE child
		assertEquals(ne.getId(), root.getChildId(Orthant.NE()));
		assertTrue(ne.hasParent());
		assertEquals(root.getId(), ne.getParentId());
		assertTrue(ne.hasChildren());

		assertEquals(ne_sw.getId(), ne.getChildId(Orthant.SW()));
		assertEquals(ne_se.getId(), ne.getChildId(Orthant.SE()));
		assertEquals(ne_nw.getId(), ne.getChildId(Orthant.NW()));
		assertEquals(ne_ne.getId(), ne.getChildId(Orthant.NE()));

		assertTrue(ne.hasNbr(Side.WEST()));
		assertFalse(ne.hasNbr(Side.EAST()));
		assertTrue(ne.hasNbr(Side.SOUTH()));
		assertFalse(ne.hasNbr(Side.NORTH()));

		assertEquals(nw.getId(), ne.getNbrId(Side.WEST()));
		assertEquals(se.getId(), ne.getNbrId(Side.SOUTH()));

		assertTrue(ne.hasNbr(Orthant.SW()));
		assertFalse(ne.hasNbr(Orthant.SE()));
		assertFalse(ne.hasNbr(Orthant.NW()));
		assertFalse(ne.hasNbr(Orthant.NE()));

		assertEquals(sw.getId(), ne.getNbrId(Orthant.SW()));

		// NE_SW child
		assertEquals(ne_sw.getId(), ne.getChildId(Orthant.SW()));
		assertTrue(ne_sw.hasParent());
		assertEquals(ne.getId(), ne_sw.getParentId());
		assertFalse(ne_sw.hasChildren());

		assertTrue(ne_sw.hasNbr(Side.WEST()));
		assertTrue(ne_sw.hasNbr(Side.EAST()));
		assertTrue(ne_sw.hasNbr(Side.SOUTH()));
		assertTrue(ne_sw.hasNbr(Side.NORTH()));

		assertEquals(nw_se.getId(), ne_sw.getNbrId(Side.WEST()));
		assertEquals(ne_se.getId(), ne_sw.getNbrId(Side.EAST()));
		assertEquals(se_nw.getId(), ne_sw.getNbrId(Side.SOUTH()));
		assertEquals(ne_nw.getId(), ne_sw.getNbrId(Side.NORTH()));

		assertTrue(ne_sw.hasNbr(Orthant.SW()));
		assertTrue(ne_sw.hasNbr(Orthant.SE()));
		assertTrue(ne_sw.hasNbr(Orthant.NW()));
		assertTrue(ne_sw.hasNbr(Orthant.NE()));

		assertEquals(sw_ne.getId(), ne_sw.getNbrId(Orthant.SW()));
		assertEquals(se_ne.getId(), ne_sw.getNbrId(Orthant.SE()));
		assertEquals(nw_ne.getId(), ne_sw.getNbrId(Orthant.NW()));
		assertEquals(ne_ne.getId(), ne_sw.getNbrId(Orthant.NE()));

		// NE_SE child
		assertEquals(ne_se.getId(), ne.getChildId(Orthant.SE()));
		assertTrue(ne_se.hasParent());
		assertEquals(ne.getId(), ne_se.getParentId());
		assertFalse(ne_se.hasChildren());

		assertTrue(ne_se.hasNbr(Side.WEST()));
		assertFalse(ne_se.hasNbr(Side.EAST()));
		assertTrue(ne_se.hasNbr(Side.SOUTH()));
		assertTrue(ne_se.hasNbr(Side.NORTH()));

		assertEquals(ne_sw.getId(), ne_se.getNbrId(Side.WEST()));
		assertEquals(se_ne.getId(), ne_se.getNbrId(Side.SOUTH()));
		assertEquals(ne_ne.getId(), ne_se.getNbrId(Side.NORTH()));

		assertTrue(ne_se.hasNbr(Orthant.SW()));
		assertFalse(ne_se.hasNbr(Orthant.SE()));
		assertTrue(ne_se.hasNbr(Orthant.NW()));
		assertFalse(ne_se.hasNbr(Orthant.NE()));

		assertEquals(se_nw.getId(), ne_se.getNbrId(Orthant.SW()));
		assertEquals(ne_nw.getId(), ne_se.getNbrId(Orthant.NW()));

		// NE_NW child
		assertEquals(ne_nw.getId(), ne.getChildId(Orthant.NW()));
		assertTrue(ne_nw.hasParent());
		assertEquals(ne.getId(), ne_nw.getParentId());
		assertFalse(ne_nw.hasChildren());

		assertTrue(ne_nw.hasNbr(Side.WEST()));
		assertTrue(ne_nw.hasNbr(Side.EAST()));
		assertTrue(ne_nw.hasNbr(Side.SOUTH()));
		assertFalse(ne_nw.hasNbr(Side.NORTH()));

		assertEquals(nw_ne.getId(), ne_nw.getNbrId(Side.WEST()));
		assertEquals(ne_ne.getId(), ne_nw.getNbrId(Side.EAST()));
		assertEquals(ne_sw.getId(), ne_nw.getNbrId(Side.SOUTH()));

		assertTrue(ne_nw.hasNbr(Orthant.SW()));
		assertTrue(ne_nw.hasNbr(Orthant.SE()));
		assertFalse(ne_nw.hasNbr(Orthant.NW()));
		assertFalse(ne_nw.hasNbr(Orthant.NE()));

		assertEquals(nw_se.getId(), ne_nw.getNbrId(Orthant.SW()));
		assertEquals(ne_se.getId(), ne_nw.getNbrId(Orthant.SE()));

		// NE_NE child
		assertEquals(ne_ne.getId(), ne.getChildId(Orthant.NE()));
		assertTrue(ne_ne.hasParent());
		assertEquals(ne.getId(), ne_ne.getParentId());
		assertFalse(ne_ne.hasChildren());

		assertTrue(ne_ne.hasNbr(Side.WEST()));
		assertFalse(ne_ne.hasNbr(Side.EAST()));
		assertTrue(ne_ne.hasNbr(Side.SOUTH()));
		assertFalse(ne_ne.hasNbr(Side.NORTH()));

		assertEquals(ne_nw.getId(), ne_ne.getNbrId(Side.WEST()));
		assertEquals(ne_se.getId(), ne_ne.getNbrId(Side.SOUTH()));

		assertTrue(ne_ne.hasNbr(Orthant.SW()));
		assertFalse(ne_ne.hasNbr(Orthant.SE()));
		assertFalse(ne_ne.hasNbr(Orthant.NW()));
		assertFalse(ne_ne.hasNbr(Orthant.NE()));

		assertEquals(ne_sw.getId(), ne_ne.getNbrId(Orthant.SW()));

	}

	@Test
	void RefineAtThrice3D() {
		Forest forest = new Forest(3);
		double[] coord = { .3, .3, .3 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		forest.refineAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 3);

		Node root = forest.getNode(0);
		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		Node bsw = forest.getNode(root.getChildId(Orthant.BSW()));
		Node bsw_bsw = forest.getNode(bsw.getChildId(Orthant.BSW()));
		Node bsw_bse = forest.getNode(bsw.getChildId(Orthant.BSE()));
		Node bsw_bnw = forest.getNode(bsw.getChildId(Orthant.BNW()));
		Node bsw_bne = forest.getNode(bsw.getChildId(Orthant.BNE()));
		Node bsw_tsw = forest.getNode(bsw.getChildId(Orthant.TSW()));
		Node bsw_tse = forest.getNode(bsw.getChildId(Orthant.TSE()));
		Node bsw_tnw = forest.getNode(bsw.getChildId(Orthant.TNW()));
		Node bsw_tne = forest.getNode(bsw.getChildId(Orthant.TNE()));
		Node bse = forest.getNode(root.getChildId(Orthant.BSE()));
		Node bse_bsw = forest.getNode(bse.getChildId(Orthant.BSW()));
		Node bse_bnw = forest.getNode(bse.getChildId(Orthant.BNW()));
		Node bse_tsw = forest.getNode(bse.getChildId(Orthant.TSW()));
		Node bse_tnw = forest.getNode(bse.getChildId(Orthant.TNW()));
		Node bse_tne = forest.getNode(bse.getChildId(Orthant.TNE()));
		Node bnw = forest.getNode(root.getChildId(Orthant.BNW()));
		Node bnw_bsw = forest.getNode(bnw.getChildId(Orthant.BSW()));
		Node bnw_bse = forest.getNode(bnw.getChildId(Orthant.BSE()));
		Node bnw_tsw = forest.getNode(bnw.getChildId(Orthant.TSW()));
		Node bnw_tse = forest.getNode(bnw.getChildId(Orthant.TSE()));
		Node bnw_tnw = forest.getNode(bnw.getChildId(Orthant.TNW()));
		Node bnw_tne = forest.getNode(bnw.getChildId(Orthant.TNE()));
		Node bne = forest.getNode(root.getChildId(Orthant.BNE()));
		Node bne_bsw = forest.getNode(bne.getChildId(Orthant.BSW()));
		Node bne_tsw = forest.getNode(bne.getChildId(Orthant.TSW()));
		Node bne_tse = forest.getNode(bne.getChildId(Orthant.TSE()));
		Node bne_tnw = forest.getNode(bne.getChildId(Orthant.TNW()));
		Node bne_tne = forest.getNode(bne.getChildId(Orthant.TNE()));
		Node tsw = forest.getNode(root.getChildId(Orthant.TSW()));
		Node tsw_bsw = forest.getNode(tsw.getChildId(Orthant.BSW()));
		Node tsw_bse = forest.getNode(tsw.getChildId(Orthant.BSE()));
		Node tsw_bnw = forest.getNode(tsw.getChildId(Orthant.BNW()));
		Node tsw_bne = forest.getNode(tsw.getChildId(Orthant.BNE()));
		Node tsw_tsw = forest.getNode(tsw.getChildId(Orthant.TSW()));
		Node tsw_tse = forest.getNode(tsw.getChildId(Orthant.TSE()));
		Node tsw_tnw = forest.getNode(tsw.getChildId(Orthant.TNW()));
		Node tsw_tne = forest.getNode(tsw.getChildId(Orthant.TNE()));
		Node tse = forest.getNode(root.getChildId(Orthant.TSE()));
		Node tse_bsw = forest.getNode(tse.getChildId(Orthant.BSW()));
		Node tse_bnw = forest.getNode(tse.getChildId(Orthant.BNW()));
		Node tse_bne = forest.getNode(tse.getChildId(Orthant.BNE()));
		Node tse_tsw = forest.getNode(tse.getChildId(Orthant.TSW()));
		Node tse_tnw = forest.getNode(tse.getChildId(Orthant.TNW()));
		Node tse_tne = forest.getNode(tse.getChildId(Orthant.TNE()));
		Node tnw = forest.getNode(root.getChildId(Orthant.TNW()));
		Node tnw_bsw = forest.getNode(tnw.getChildId(Orthant.BSW()));
		Node tnw_bse = forest.getNode(tnw.getChildId(Orthant.BSE()));
		Node tnw_bnw = forest.getNode(tnw.getChildId(Orthant.BNW()));
		Node tnw_bne = forest.getNode(tnw.getChildId(Orthant.BNE()));
		Node tnw_tsw = forest.getNode(tnw.getChildId(Orthant.TSW()));
		Node tnw_tse = forest.getNode(tnw.getChildId(Orthant.TSE()));
		Node tnw_tnw = forest.getNode(tnw.getChildId(Orthant.TNW()));
		Node tnw_tne = forest.getNode(tnw.getChildId(Orthant.TNE()));
		Node tne = forest.getNode(root.getChildId(Orthant.TNE()));
		Node tne_bsw = forest.getNode(tne.getChildId(Orthant.BSW()));
		Node tne_bse = forest.getNode(tne.getChildId(Orthant.BSE()));
		Node tne_bnw = forest.getNode(tne.getChildId(Orthant.BNW()));
		Node tne_bne = forest.getNode(tne.getChildId(Orthant.BNE()));
		Node tne_tsw = forest.getNode(tne.getChildId(Orthant.TSW()));
		Node tne_tse = forest.getNode(tne.getChildId(Orthant.TSE()));
		Node tne_tnw = forest.getNode(tne.getChildId(Orthant.TNW()));
		Node tne_tne = forest.getNode(tne.getChildId(Orthant.TNE()));

		// BSW child
		assertEquals(bsw.getId(), root.getChildId(Orthant.BSW()));
		assertTrue(bsw.hasParent());
		assertEquals(root.getId(), bsw.getParentId());
		assertTrue(bsw.hasChildren());

		// BSW_TNE child
		assertEquals(bsw_tne.getId(), bsw.getChildId(Orthant.TNE()));
		assertTrue(bsw_tne.hasParent());
		assertEquals(bsw.getId(), bsw_tne.getParentId());
		assertTrue(bsw_tne.hasChildren());

		assertTrue(bsw_tne.hasNbr(Side.WEST()));
		assertTrue(bsw_tne.hasNbr(Side.EAST()));
		assertTrue(bsw_tne.hasNbr(Side.SOUTH()));
		assertTrue(bsw_tne.hasNbr(Side.NORTH()));
		assertTrue(bsw_tne.hasNbr(Side.BOTTOM()));
		assertTrue(bsw_tne.hasNbr(Side.TOP()));

		assertEquals(bsw_tnw.getId(), bsw_tne.getNbrId(Side.WEST()));
		assertEquals(bse_tnw.getId(), bsw_tne.getNbrId(Side.EAST()));
		assertEquals(bsw_tse.getId(), bsw_tne.getNbrId(Side.SOUTH()));
		assertEquals(bnw_tse.getId(), bsw_tne.getNbrId(Side.NORTH()));
		assertEquals(bsw_bne.getId(), bsw_tne.getNbrId(Side.BOTTOM()));
		assertEquals(tsw_bne.getId(), bsw_tne.getNbrId(Side.TOP()));

		assertTrue(bsw_tne.hasNbr(Edge.BS()));
		assertTrue(bsw_tne.hasNbr(Edge.TN()));
		assertTrue(bsw_tne.hasNbr(Edge.BN()));
		assertTrue(bsw_tne.hasNbr(Edge.TS()));
		assertTrue(bsw_tne.hasNbr(Edge.BW()));
		assertTrue(bsw_tne.hasNbr(Edge.TE()));
		assertTrue(bsw_tne.hasNbr(Edge.BE()));
		assertTrue(bsw_tne.hasNbr(Edge.TW()));
		assertTrue(bsw_tne.hasNbr(Edge.SW()));
		assertTrue(bsw_tne.hasNbr(Edge.NE()));
		assertTrue(bsw_tne.hasNbr(Edge.SE()));
		assertTrue(bsw_tne.hasNbr(Edge.NW()));

		assertEquals(bsw_bse.getId(), bsw_tne.getNbrId(Edge.BS()));
		assertEquals(tnw_bse.getId(), bsw_tne.getNbrId(Edge.TN()));
		assertEquals(bnw_bse.getId(), bsw_tne.getNbrId(Edge.BN()));
		assertEquals(tsw_bse.getId(), bsw_tne.getNbrId(Edge.TS()));
		assertEquals(bsw_bnw.getId(), bsw_tne.getNbrId(Edge.BW()));
		assertEquals(tse_bnw.getId(), bsw_tne.getNbrId(Edge.TE()));
		assertEquals(bse_bnw.getId(), bsw_tne.getNbrId(Edge.BE()));
		assertEquals(tsw_bnw.getId(), bsw_tne.getNbrId(Edge.TW()));
		assertEquals(bsw_tsw.getId(), bsw_tne.getNbrId(Edge.SW()));
		assertEquals(bne_tsw.getId(), bsw_tne.getNbrId(Edge.NE()));
		assertEquals(bse_tsw.getId(), bsw_tne.getNbrId(Edge.SE()));
		assertEquals(bnw_tsw.getId(), bsw_tne.getNbrId(Edge.NW()));

		assertTrue(bsw_tne.hasNbr(Orthant.BSW()));
		assertTrue(bsw_tne.hasNbr(Orthant.BSE()));
		assertTrue(bsw_tne.hasNbr(Orthant.BNW()));
		assertTrue(bsw_tne.hasNbr(Orthant.BNE()));
		assertTrue(bsw_tne.hasNbr(Orthant.TSW()));
		assertTrue(bsw_tne.hasNbr(Orthant.TSE()));
		assertTrue(bsw_tne.hasNbr(Orthant.TNW()));
		assertTrue(bsw_tne.hasNbr(Orthant.TNE()));

		assertEquals(bsw_bsw.getId(), bsw_tne.getNbrId(Orthant.BSW()));
		assertEquals(bse_bsw.getId(), bsw_tne.getNbrId(Orthant.BSE()));
		assertEquals(bnw_bsw.getId(), bsw_tne.getNbrId(Orthant.BNW()));
		assertEquals(bne_bsw.getId(), bsw_tne.getNbrId(Orthant.BNE()));
		assertEquals(tsw_bsw.getId(), bsw_tne.getNbrId(Orthant.TSW()));
		assertEquals(tse_bsw.getId(), bsw_tne.getNbrId(Orthant.TSE()));
		assertEquals(tnw_bsw.getId(), bsw_tne.getNbrId(Orthant.TNW()));
		assertEquals(tne_bsw.getId(), bsw_tne.getNbrId(Orthant.TNE()));

		// TSW child
		assertEquals(tsw.getId(), root.getChildId(Orthant.TSW()));
		assertTrue(tsw.hasParent());
		assertEquals(root.getId(), tsw.getParentId());
		assertTrue(tsw.hasChildren());

		// TSW_BNE child
		assertEquals(tsw_bne.getId(), tsw.getChildId(Orthant.BNE()));
		assertTrue(tsw_bne.hasParent());
		assertEquals(tsw.getId(), tsw_bne.getParentId());
		assertFalse(tsw_bne.hasChildren());

		assertTrue(tsw_bne.hasNbr(Side.WEST()));
		assertTrue(tsw_bne.hasNbr(Side.EAST()));
		assertTrue(tsw_bne.hasNbr(Side.SOUTH()));
		assertTrue(tsw_bne.hasNbr(Side.NORTH()));
		assertTrue(tsw_bne.hasNbr(Side.BOTTOM()));
		assertTrue(tsw_bne.hasNbr(Side.TOP()));

		assertEquals(tsw_bnw.getId(), tsw_bne.getNbrId(Side.WEST()));
		assertEquals(tse_bnw.getId(), tsw_bne.getNbrId(Side.EAST()));
		assertEquals(tsw_bse.getId(), tsw_bne.getNbrId(Side.SOUTH()));
		assertEquals(tnw_bse.getId(), tsw_bne.getNbrId(Side.NORTH()));
		assertEquals(bsw_tne.getId(), tsw_bne.getNbrId(Side.BOTTOM()));
		assertEquals(tsw_tne.getId(), tsw_bne.getNbrId(Side.TOP()));

		assertTrue(tsw_bne.hasNbr(Edge.BS()));
		assertTrue(tsw_bne.hasNbr(Edge.TN()));
		assertTrue(tsw_bne.hasNbr(Edge.BN()));
		assertTrue(tsw_bne.hasNbr(Edge.TS()));
		assertTrue(tsw_bne.hasNbr(Edge.BW()));
		assertTrue(tsw_bne.hasNbr(Edge.TE()));
		assertTrue(tsw_bne.hasNbr(Edge.BE()));
		assertTrue(tsw_bne.hasNbr(Edge.TW()));
		assertTrue(tsw_bne.hasNbr(Edge.SW()));
		assertTrue(tsw_bne.hasNbr(Edge.NE()));
		assertTrue(tsw_bne.hasNbr(Edge.SE()));
		assertTrue(tsw_bne.hasNbr(Edge.NW()));

		assertEquals(bsw_tse.getId(), tsw_bne.getNbrId(Edge.BS()));
		assertEquals(tnw_tse.getId(), tsw_bne.getNbrId(Edge.TN()));
		assertEquals(bnw_tse.getId(), tsw_bne.getNbrId(Edge.BN()));
		assertEquals(tsw_tse.getId(), tsw_bne.getNbrId(Edge.TS()));
		assertEquals(bsw_tnw.getId(), tsw_bne.getNbrId(Edge.BW()));
		assertEquals(tse_tnw.getId(), tsw_bne.getNbrId(Edge.TE()));
		assertEquals(bse_tnw.getId(), tsw_bne.getNbrId(Edge.BE()));
		assertEquals(tsw_tnw.getId(), tsw_bne.getNbrId(Edge.TW()));
		assertEquals(tsw_bsw.getId(), tsw_bne.getNbrId(Edge.SW()));
		assertEquals(tne_bsw.getId(), tsw_bne.getNbrId(Edge.NE()));
		assertEquals(tse_bsw.getId(), tsw_bne.getNbrId(Edge.SE()));
		assertEquals(tnw_bsw.getId(), tsw_bne.getNbrId(Edge.NW()));

		assertTrue(tsw_bne.hasNbr(Orthant.BSW()));
		assertTrue(tsw_bne.hasNbr(Orthant.BSE()));
		assertTrue(tsw_bne.hasNbr(Orthant.BNW()));
		assertTrue(tsw_bne.hasNbr(Orthant.BNE()));
		assertTrue(tsw_bne.hasNbr(Orthant.TSW()));
		assertTrue(tsw_bne.hasNbr(Orthant.TSE()));
		assertTrue(tsw_bne.hasNbr(Orthant.TNW()));
		assertTrue(tsw_bne.hasNbr(Orthant.TNE()));

		assertEquals(bsw_tsw.getId(), tsw_bne.getNbrId(Orthant.BSW()));
		assertEquals(bse_tsw.getId(), tsw_bne.getNbrId(Orthant.BSE()));
		assertEquals(bnw_tsw.getId(), tsw_bne.getNbrId(Orthant.BNW()));
		assertEquals(bne_tsw.getId(), tsw_bne.getNbrId(Orthant.BNE()));
		assertEquals(tsw_tsw.getId(), tsw_bne.getNbrId(Orthant.TSW()));
		assertEquals(tse_tsw.getId(), tsw_bne.getNbrId(Orthant.TSE()));
		assertEquals(tnw_tsw.getId(), tsw_bne.getNbrId(Orthant.TNW()));
		assertEquals(tne_tsw.getId(), tsw_bne.getNbrId(Orthant.TNE()));

		// TNW child
		assertEquals(tnw.getId(), root.getChildId(Orthant.TNW()));
		assertTrue(tnw.hasParent());
		assertEquals(root.getId(), tnw.getParentId());
		assertTrue(tnw.hasChildren());

		// TNW_BSE child
		assertEquals(tnw_bse.getId(), tnw.getChildId(Orthant.BSE()));
		assertTrue(tnw_bse.hasParent());
		assertEquals(tnw.getId(), tnw_bse.getParentId());
		assertFalse(tnw_bse.hasChildren());

		assertTrue(tnw_bse.hasNbr(Side.WEST()));
		assertTrue(tnw_bse.hasNbr(Side.EAST()));
		assertTrue(tnw_bse.hasNbr(Side.SOUTH()));
		assertTrue(tnw_bse.hasNbr(Side.NORTH()));
		assertTrue(tnw_bse.hasNbr(Side.BOTTOM()));
		assertTrue(tnw_bse.hasNbr(Side.TOP()));

		assertEquals(tnw_bsw.getId(), tnw_bse.getNbrId(Side.WEST()));
		assertEquals(tne_bsw.getId(), tnw_bse.getNbrId(Side.EAST()));
		assertEquals(tsw_bne.getId(), tnw_bse.getNbrId(Side.SOUTH()));
		assertEquals(tnw_bne.getId(), tnw_bse.getNbrId(Side.NORTH()));
		assertEquals(bnw_tse.getId(), tnw_bse.getNbrId(Side.BOTTOM()));
		assertEquals(tnw_tse.getId(), tnw_bse.getNbrId(Side.TOP()));

		assertTrue(tnw_bse.hasNbr(Edge.BS()));
		assertTrue(tnw_bse.hasNbr(Edge.TN()));
		assertTrue(tnw_bse.hasNbr(Edge.BN()));
		assertTrue(tnw_bse.hasNbr(Edge.TS()));
		assertTrue(tnw_bse.hasNbr(Edge.BW()));
		assertTrue(tnw_bse.hasNbr(Edge.TE()));
		assertTrue(tnw_bse.hasNbr(Edge.BE()));
		assertTrue(tnw_bse.hasNbr(Edge.TW()));
		assertTrue(tnw_bse.hasNbr(Edge.SW()));
		assertTrue(tnw_bse.hasNbr(Edge.NE()));
		assertTrue(tnw_bse.hasNbr(Edge.SE()));
		assertTrue(tnw_bse.hasNbr(Edge.NW()));

		assertEquals(bsw_tne.getId(), tnw_bse.getNbrId(Edge.BS()));
		assertEquals(tnw_tne.getId(), tnw_bse.getNbrId(Edge.TN()));
		assertEquals(bnw_tne.getId(), tnw_bse.getNbrId(Edge.BN()));
		assertEquals(tsw_tne.getId(), tnw_bse.getNbrId(Edge.TS()));
		assertEquals(bnw_tsw.getId(), tnw_bse.getNbrId(Edge.BW()));
		assertEquals(tne_tsw.getId(), tnw_bse.getNbrId(Edge.TE()));
		assertEquals(bne_tsw.getId(), tnw_bse.getNbrId(Edge.BE()));
		assertEquals(tnw_tsw.getId(), tnw_bse.getNbrId(Edge.TW()));
		assertEquals(tsw_bnw.getId(), tnw_bse.getNbrId(Edge.SW()));
		assertEquals(tne_bnw.getId(), tnw_bse.getNbrId(Edge.NE()));
		assertEquals(tse_bnw.getId(), tnw_bse.getNbrId(Edge.SE()));
		assertEquals(tnw_bnw.getId(), tnw_bse.getNbrId(Edge.NW()));

		assertTrue(tnw_bse.hasNbr(Orthant.BSW()));
		assertTrue(tnw_bse.hasNbr(Orthant.BSE()));
		assertTrue(tnw_bse.hasNbr(Orthant.BNW()));
		assertTrue(tnw_bse.hasNbr(Orthant.BNE()));
		assertTrue(tnw_bse.hasNbr(Orthant.TSW()));
		assertTrue(tnw_bse.hasNbr(Orthant.TSE()));
		assertTrue(tnw_bse.hasNbr(Orthant.TNW()));
		assertTrue(tnw_bse.hasNbr(Orthant.TNE()));

		assertEquals(bsw_tnw.getId(), tnw_bse.getNbrId(Orthant.BSW()));
		assertEquals(bse_tnw.getId(), tnw_bse.getNbrId(Orthant.BSE()));
		assertEquals(bnw_tnw.getId(), tnw_bse.getNbrId(Orthant.BNW()));
		assertEquals(bne_tnw.getId(), tnw_bse.getNbrId(Orthant.BNE()));
		assertEquals(tsw_tnw.getId(), tnw_bse.getNbrId(Orthant.TSW()));
		assertEquals(tse_tnw.getId(), tnw_bse.getNbrId(Orthant.TSE()));
		assertEquals(tnw_tnw.getId(), tnw_bse.getNbrId(Orthant.TNW()));
		assertEquals(tne_tnw.getId(), tnw_bse.getNbrId(Orthant.TNE()));

		// TNE child
		assertEquals(tne.getId(), root.getChildId(Orthant.TNE()));
		assertTrue(tne.hasParent());
		assertEquals(root.getId(), tne.getParentId());
		assertTrue(tne.hasChildren());

		// TNE_BSW child
		assertEquals(tne_bsw.getId(), tne.getChildId(Orthant.BSW()));
		assertTrue(tne_bsw.hasParent());
		assertEquals(tne.getId(), tne_bsw.getParentId());
		assertFalse(tne_bsw.hasChildren());

		assertTrue(tne_bsw.hasNbr(Side.WEST()));
		assertTrue(tne_bsw.hasNbr(Side.EAST()));
		assertTrue(tne_bsw.hasNbr(Side.SOUTH()));
		assertTrue(tne_bsw.hasNbr(Side.NORTH()));
		assertTrue(tne_bsw.hasNbr(Side.BOTTOM()));
		assertTrue(tne_bsw.hasNbr(Side.TOP()));

		assertEquals(tnw_bse.getId(), tne_bsw.getNbrId(Side.WEST()));
		assertEquals(tne_bse.getId(), tne_bsw.getNbrId(Side.EAST()));
		assertEquals(tse_bnw.getId(), tne_bsw.getNbrId(Side.SOUTH()));
		assertEquals(tne_bnw.getId(), tne_bsw.getNbrId(Side.NORTH()));
		assertEquals(bne_tsw.getId(), tne_bsw.getNbrId(Side.BOTTOM()));
		assertEquals(tne_tsw.getId(), tne_bsw.getNbrId(Side.TOP()));

		assertTrue(tne_bsw.hasNbr(Edge.BS()));
		assertTrue(tne_bsw.hasNbr(Edge.TN()));
		assertTrue(tne_bsw.hasNbr(Edge.BN()));
		assertTrue(tne_bsw.hasNbr(Edge.TS()));
		assertTrue(tne_bsw.hasNbr(Edge.BW()));
		assertTrue(tne_bsw.hasNbr(Edge.TE()));
		assertTrue(tne_bsw.hasNbr(Edge.BE()));
		assertTrue(tne_bsw.hasNbr(Edge.TW()));
		assertTrue(tne_bsw.hasNbr(Edge.SW()));
		assertTrue(tne_bsw.hasNbr(Edge.NE()));
		assertTrue(tne_bsw.hasNbr(Edge.SE()));
		assertTrue(tne_bsw.hasNbr(Edge.NW()));

		assertEquals(bse_tnw.getId(), tne_bsw.getNbrId(Edge.BS()));
		assertEquals(tne_tnw.getId(), tne_bsw.getNbrId(Edge.TN()));
		assertEquals(bne_tnw.getId(), tne_bsw.getNbrId(Edge.BN()));
		assertEquals(tse_tnw.getId(), tne_bsw.getNbrId(Edge.TS()));
		assertEquals(bnw_tse.getId(), tne_bsw.getNbrId(Edge.BW()));
		assertEquals(tne_tse.getId(), tne_bsw.getNbrId(Edge.TE()));
		assertEquals(bne_tse.getId(), tne_bsw.getNbrId(Edge.BE()));
		assertEquals(tnw_tse.getId(), tne_bsw.getNbrId(Edge.TW()));
		assertEquals(tsw_bne.getId(), tne_bsw.getNbrId(Edge.SW()));
		assertEquals(tne_bne.getId(), tne_bsw.getNbrId(Edge.NE()));
		assertEquals(tse_bne.getId(), tne_bsw.getNbrId(Edge.SE()));
		assertEquals(tnw_bne.getId(), tne_bsw.getNbrId(Edge.NW()));

		assertTrue(tne_bsw.hasNbr(Orthant.BSW()));
		assertTrue(tne_bsw.hasNbr(Orthant.BSE()));
		assertTrue(tne_bsw.hasNbr(Orthant.BNW()));
		assertTrue(tne_bsw.hasNbr(Orthant.BNE()));
		assertTrue(tne_bsw.hasNbr(Orthant.TSW()));
		assertTrue(tne_bsw.hasNbr(Orthant.TSE()));
		assertTrue(tne_bsw.hasNbr(Orthant.TNW()));
		assertTrue(tne_bsw.hasNbr(Orthant.TNE()));

		assertEquals(bsw_tne.getId(), tne_bsw.getNbrId(Orthant.BSW()));
		assertEquals(bse_tne.getId(), tne_bsw.getNbrId(Orthant.BSE()));
		assertEquals(bnw_tne.getId(), tne_bsw.getNbrId(Orthant.BNW()));
		assertEquals(bne_tne.getId(), tne_bsw.getNbrId(Orthant.BNE()));
		assertEquals(tsw_tne.getId(), tne_bsw.getNbrId(Orthant.TSW()));
		assertEquals(tse_tne.getId(), tne_bsw.getNbrId(Orthant.TSE()));
		assertEquals(tnw_tne.getId(), tne_bsw.getNbrId(Orthant.TNW()));
		assertEquals(tne_tne.getId(), tne_bsw.getNbrId(Orthant.TNE()));

	}

	@Test
	void RefineAtThrice() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		forest.refineAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 3);

		Node root = forest.getNode(0);
		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		// check the children
		// southwest root
		{
			Orthant o = Orthant.SW();
			int child_id = root.getChildId(o);
			Node child = forest.getNode(child_id);
			assertEquals(child.getId(), child_id);
			assertTrue(child.hasParent());
			for (Side s : o.getInteriorSides()) {
				assertTrue(child.hasNbr(s));
				assertEquals(child.getNbrId(s), root.getChildId(o.getNbrOnSide(s)));
			}
			for (Side s : o.getExteriorSides()) {
				assertFalse(child.hasNbr(s));
			}
			assertTrue(child.hasChildren());
			for (Orthant child_o : Orthant.getValuesForDimension(2)) {
				int child_child_id = child.getChildId(child_o);
				Node child_child = forest.getNode(child_child_id);
				assertEquals(child_child.getId(), child_child_id);
				assertTrue(child_child.hasParent());
				for (Side s : Side.getValuesForDimension(2)) {
					if (intersection(o.getExteriorSides(), child_o.getExteriorSides()).contains(s)) {
						assertFalse(child_child.hasNbr(s));
					} else if (set(child_o.getExteriorSides()).contains(s)) {
						assertTrue(child_child.hasNbr(s));
						assertEquals(child_child.getNbrId(s),
								forest.getNode(root.getChildId(o.getNbrOnSide(s))).getChildId(child_o.getNbrOnSide(s)));
					} else {
						assertTrue(child_child.hasNbr(s));
						assertEquals(child_child.getNbrId(s), child.getChildId(child_o.getNbrOnSide(s)));
					}
				}
				if (child_o.equals(Orthant.NE())) {
					assertTrue(child_child.hasChildren());
				} else {
					assertFalse(child_child.hasChildren());
				}
			}
		}
		// southeast root
		{
			Orthant o = Orthant.SE();
			int child_id = root.getChildId(o);
			Node child = forest.getNode(child_id);
			assertEquals(child.getId(), child_id);
			assertTrue(child.hasParent());
			for (Side s : o.getInteriorSides()) {
				assertTrue(child.hasNbr(s));
				assertEquals(child.getNbrId(s), root.getChildId(o.getNbrOnSide(s)));
			}
			for (Side s : o.getExteriorSides()) {
				assertFalse(child.hasNbr(s));
			}
			assertTrue(child.hasChildren());
		}
		// northwest root
		{
			Orthant o = Orthant.NW();
			int child_id = root.getChildId(o);
			Node child = forest.getNode(child_id);
			assertEquals(child.getId(), child_id);
			assertTrue(child.hasParent());
			for (Side s : o.getInteriorSides()) {
				assertTrue(child.hasNbr(s));
				assertEquals(child.getNbrId(s), root.getChildId(o.getNbrOnSide(s)));
			}
			for (Side s : o.getExteriorSides()) {
				assertFalse(child.hasNbr(s));
			}
			assertTrue(child.hasChildren());
		}
		// northeast root
		{
			Orthant o = Orthant.NE();
			int child_id = root.getChildId(o);
			Node child = forest.getNode(child_id);
			assertEquals(child.getId(), child_id);
			assertTrue(child.hasParent());
			for (Side s : o.getInteriorSides()) {
				assertTrue(child.hasNbr(s));
				assertEquals(child.getNbrId(s), root.getChildId(o.getNbrOnSide(s)));
			}
			for (Side s : o.getExteriorSides()) {
				assertFalse(child.hasNbr(s));
			}
			assertTrue(child.hasChildren());
		}
	}

	@Test
	void CoarsenAtRoot() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.coarsenAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertEquals(forest.getMaxLevel(), 0);
	}

	@Test
	void CoarsenAtAfterRefineAt() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.refineAt(coord);
		forest.coarsenAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasParent());
		assertFalse(forest.getNode(0).hasChildren());
		assertEquals(forest.getMaxLevel(), 0);
		assertEquals(forest.getNode(1), null);
	}

	@Test
	void CoarsenAtAfterRefineAtTwiceFinest() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		forest.coarsenAt(coord);

		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 1);

		Node root = forest.getNode(0);
		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		// check the children
		for (Orthant o : Orthant.getValuesForDimension(2)) {
			int child_id = root.getChildId(o);
			Node child = forest.getNode(child_id);
			assertEquals(child.getId(), child_id);
			assertTrue(child.hasParent());
			assertFalse(child.hasChildren());
			for (Side s : o.getInteriorSides()) {
				assertTrue(child.hasNbr(s));
				assertEquals(child.getNbrId(s), root.getChildId(o.getNbrOnSide(s)));
			}
			for (Side s : o.getExteriorSides()) {
				assertFalse(child.hasNbr(s));
			}
		}
	}

	@Test
	void CoarsenAtAfterRefineAtThriceCoarsesr() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		forest.refineAt(coord);
		coord[0] = .25;
		coord[1] = .75;
		forest.coarsenAt(coord);

		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasParent());
		assertTrue(forest.getNode(0).hasChildren());
		assertEquals(forest.getMaxLevel(), 2);
	}

	@Test
	void CoarsenAtAfterRefineAtTwiceCoarsest() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		coord[0] = .75;
		coord[1] = .75;
		forest.coarsenAt(coord);

		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasParent());
		assertFalse(forest.getNode(0).hasChildren());
		assertEquals(forest.getMaxLevel(), 0);
		assertEquals(forest.getNode(1), null);
	}

	@Test
	void CoarsenNodeRoot() {
		Forest forest = new Forest(2);
		forest.coarsenNode(0);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertEquals(forest.getMaxLevel(), 0);
	}

	@Test
	void CoarsenNodeBad() {
		Forest forest = new Forest(2);
		forest.coarsenNode(69);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertEquals(forest.getMaxLevel(), 0);
	}

	@Test
	void CoarsenNodeAfterRefineAt() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.refineAt(coord);
		forest.coarsenNode(forest.getRootNode().getChildId(Orthant.SW()));
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasParent());
		assertFalse(forest.getNode(0).hasChildren());
		assertEquals(forest.getMaxLevel(), 0);
		assertEquals(forest.getNode(1), null);
	}

	@Test
	void CoarsenNodeAfterRefineAtTwiceFinest() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		forest.coarsenAt(coord);
		forest.coarsenNode(forest.getNode(forest.getRootNode().getChildId(Orthant.SW())).getChildId(Orthant.SW()));

		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 1);

		Node root = forest.getNode(0);
		assertEquals(root.getId(), 0);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		// check the children
		for (Orthant o : Orthant.getValuesForDimension(2)) {
			int child_id = root.getChildId(o);
			Node child = forest.getNode(child_id);
			assertEquals(child.getId(), child_id);
			assertTrue(child.hasParent());
			assertFalse(child.hasChildren());
			for (Side s : o.getInteriorSides()) {
				assertTrue(child.hasNbr(s));
				assertEquals(child.getNbrId(s), root.getChildId(o.getNbrOnSide(s)));
			}
			for (Side s : o.getExteriorSides()) {
				assertFalse(child.hasNbr(s));
			}
		}
	}

	@Test
	void CoarsenNodeAfterRefineAtTwiceCoarser() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		forest.coarsenNode(forest.getRootNode().getChildId(Orthant.NW()));

		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasParent());
		assertFalse(forest.getNode(0).hasChildren());
		assertEquals(forest.getMaxLevel(), 0);
		assertEquals(forest.getNode(1), null);
		assertEquals(forest.getNode(7), null);
	}

	@Test
	void CoarsenNodeAfterRefineAtTwiceCoarserNodeWithChildren() {
		Forest forest = new Forest(2);
		double[] coord = { .3, .3 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		forest.coarsenNode(forest.getRootNode().getChildId(Orthant.SW()));

		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasParent());
		assertFalse(forest.getNode(0).hasChildren());
		assertEquals(forest.getMaxLevel(), 0);
		assertEquals(forest.getNode(1), null);
	}

	@Test
	void RefineAtBad() {
		Forest forest = new Forest(2);
		double[] coord = { .5, 2 };
		forest.refineAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasChildren());
	}

	@Test
	void RefineNodeBad() {
		Forest forest = new Forest(2);
		forest.refineNode(1000);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasChildren());
	}

	@Test
	void CoarsenAtBad() {
		Forest forest = new Forest(2);
		double[] coord = { .5, 2 };
		forest.coarsenAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasChildren());
	}

	@Test
	void AddNbrAtBad() {
		Forest forest = new Forest(2);
		double[] coord = { -.5, 1.5 };
		forest.addNbrAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 1);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasChildren());
	}

	@Test
	void IsPotentialNbrSingleRoot() {
		Forest forest = new Forest(2);
		double[] coord = { .5, .5 };
		double[] starts = new double[2];
		double[] lengths = new double[2];
		assertFalse(forest.coordIsPotentialNbr(coord, starts, lengths));
		coord[0] = -2;
		assertFalse(forest.coordIsPotentialNbr(coord, starts, lengths));
		coord[0] = -.5;
		assertTrue(forest.coordIsPotentialNbr(coord, starts, lengths));
		assertEquals(-1, starts[0]);
		assertEquals(0, starts[1]);
		assertEquals(1, lengths[0]);
		assertEquals(1, lengths[1]);
		coord[0] = 1.5;
		assertTrue(forest.coordIsPotentialNbr(coord, starts, lengths));
		assertEquals(1, starts[0]);
		assertEquals(0, starts[1]);
		assertEquals(1, lengths[0]);
		assertEquals(1, lengths[1]);
		coord[0] = 3;
		assertFalse(forest.coordIsPotentialNbr(coord, starts, lengths));
		coord[1] = -1;
		assertFalse(forest.coordIsPotentialNbr(coord, starts, lengths));
		coord[0] = .5;
		coord[1] = 2.5;
		assertFalse(forest.coordIsPotentialNbr(coord, starts, lengths));
		coord[1] = -2.5;
		assertFalse(forest.coordIsPotentialNbr(coord, starts, lengths));
	}

	@Test
	void AddNbrAtWest() {
		Forest forest = new Forest(2);
		double[] coord = { -.5, .5 };
		forest.addNbrAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 2);
		assertTrue(forest.getRootIds().contains(0));
		assertTrue(forest.getRootIds().contains(1));

		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasChildren());
		assertEquals(forest.getNode(0).getNbrId(Side.WEST()), 1);

		assertEquals(forest.getNode(1).getId(), 1);
		assertFalse(forest.getNode(1).hasChildren());
		assertEquals(forest.getNode(1).getNbrId(Side.EAST()), 0);
	}

	@Test
	void AddNbrAtNorth() {
		Forest forest = new Forest(2);
		double[] coord = { .5, 1.5 };
		forest.addNbrAt(coord);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 2);
		assertTrue(forest.getRootIds().contains(0));
		assertTrue(forest.getRootIds().contains(1));

		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasChildren());
		assertEquals(forest.getNode(0).getNbrId(Side.NORTH()), 1);

		assertEquals(forest.getNode(1).getId(), 1);
		assertFalse(forest.getNode(1).hasChildren());
		assertEquals(forest.getNode(1).getNbrId(Side.SOUTH()), 0);
	}

	@Test
	void AddNbrAtWestThenNorth() {
		Forest forest = new Forest(2);
		double[] west = { -.5, .5 };
		forest.addNbrAt(west);
		double[] north = { .5, 1.5 };
		forest.addNbrAt(north);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 3);
		assertTrue(forest.getRootIds().contains(0));
		assertTrue(forest.getRootIds().contains(1));
		assertTrue(forest.getRootIds().contains(2));

		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasChildren());
		assertEquals(forest.getNode(0).getNbrId(Side.WEST()), 1);

		assertEquals(forest.getNode(1).getId(), 1);
		assertFalse(forest.getNode(1).hasChildren());
		assertFalse(forest.getNode(1).hasParent());
		assertEquals(forest.getNode(1).getNbrId(Side.EAST()), 0);

		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasChildren());
		assertEquals(forest.getNode(0).getNbrId(Side.NORTH()), 2);

		assertEquals(forest.getNode(2).getId(), 2);
		assertFalse(forest.getNode(2).hasChildren());
		assertFalse(forest.getNode(2).hasParent());
		assertEquals(forest.getNode(2).getNbrId(Side.SOUTH()), 0);
	}

	@Test
	void AddNbrAtWestThenNorthThenNorthWest() {
		Forest forest = new Forest(2);
		double[] west = { -.5, .5 };
		forest.addNbrAt(west);
		double[] north = { .5, 1.5 };
		forest.addNbrAt(north);
		double[] northwest = { -.5, 1.5 };
		forest.addNbrAt(northwest);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 4);
		assertTrue(forest.getRootIds().contains(0));
		assertTrue(forest.getRootIds().contains(1));
		assertTrue(forest.getRootIds().contains(2));
		assertTrue(forest.getRootIds().contains(3));

		assertEquals(forest.getNode(0).getId(), 0);
		assertFalse(forest.getNode(0).hasChildren());
		assertEquals(forest.getNode(0).getNbrId(Side.WEST()), 1);
		assertEquals(forest.getNode(0).getNbrId(Side.NORTH()), 2);

		assertEquals(forest.getNode(1).getId(), 1);
		assertFalse(forest.getNode(1).hasChildren());
		assertFalse(forest.getNode(1).hasParent());
		assertEquals(forest.getNode(1).getNbrId(Side.EAST()), 0);
		assertEquals(forest.getNode(1).getNbrId(Side.NORTH()), 3);

		assertEquals(forest.getNode(2).getId(), 2);
		assertFalse(forest.getNode(2).hasChildren());
		assertFalse(forest.getNode(2).hasParent());
		assertEquals(forest.getNode(2).getNbrId(Side.SOUTH()), 0);
		assertEquals(forest.getNode(2).getNbrId(Side.WEST()), 3);

		assertEquals(forest.getNode(3).getId(), 3);
		assertFalse(forest.getNode(3).hasChildren());
		assertFalse(forest.getNode(3).hasParent());
		assertEquals(forest.getNode(3).getNbrId(Side.SOUTH()), 1);
		assertEquals(forest.getNode(3).getNbrId(Side.EAST()), 2);
	}

	@Test
	void AddNbrAtNorthThenNorthWestThenWestAfterRefineAtTwiceRoot() {
		Forest forest = new Forest(2);
		double[] coord = { .2, .2 };
		forest.refineAt(coord);
		forest.refineAt(coord);
		double[] north = { .2, 1.5 };
		forest.addNbrAt(north);
		double[] northwest = { -.2, 1.5 };
		forest.addNbrAt(northwest);
		double[] west = { -.2, .5 };
		forest.addNbrAt(west);
		assertEquals(forest.getRootNode().getId(), 0);
		assertEquals(forest.getRootIds().size(), 4);
		assertTrue(forest.getRootIds().contains(0));
		assertEquals(forest.getMaxLevel(), 2);

		int other_root_id = 0;
		for (int root_id : forest.getRootIds()) {
			if (root_id != 0) {
				other_root_id = root_id;
			}
		}

		Node root = forest.getNode(other_root_id);
		assertEquals(root.getId(), other_root_id);
		assertFalse(root.hasParent());
		assertTrue(root.hasChildren());

		// check the children
		for (Orthant o : Orthant.getValuesForDimension(2)) {
			int child_id = root.getChildId(o);
			Node child = forest.getNode(child_id);
			assertFalse(child.hasChildren());
			if (o.equals(Orthant.SE())) {
				assertFalse(child.hasNbr(Side.SOUTH()));
				assertTrue(child.hasNbr(Side.EAST()));
				assertEquals(forest.getRootNode().getChildId(Orthant.SW()), child.getNbrId(Side.EAST()));
				assertEquals(child.getId(),
						forest.getNode(forest.getRootNode().getChildId(Orthant.SW())).getNbrId(Side.WEST()));
			} else if (o.equals(Orthant.NE())) {
				assertFalse(child.hasNbr(Side.NORTH()));
				assertTrue(child.hasNbr(Side.EAST()));
				assertEquals(forest.getRootNode().getChildId(Orthant.NW()), child.getNbrId(Side.EAST()));
				assertEquals(child.getId(),
						forest.getNode(forest.getRootNode().getChildId(Orthant.NW())).getNbrId(Side.WEST()));
			} else {
				for (Side s : o.getExteriorSides()) {
					assertFalse(child.hasNbr(s));
				}
			}
		}
	}

	@Test
	void GetLeaftAtAfterRefineAtTwiceRoot() {
		Forest forest = new Forest(2);
		double[] coord = { .2, .2 };
		forest.refineAt(coord);
		forest.refineAt(coord);

		assertEquals(forest.getLeafAt(coord).getLevel(), 2);
		assertTrue(forest.getLeafAt(coord).coordIsInside(coord));

		double[] coord2 = { .75, .75 };
		assertEquals(forest.getLeafAt(coord2).getLevel(), 1);
		assertTrue(forest.getLeafAt(coord2).coordIsInside(coord2));
	}

	@Test
	void GetLeaftAtDefaultConstructor() {
		Forest forest = new Forest(2);

		double[] coord2 = { .75, .75 };
		assertEquals(forest.getLeafAt(coord2).getLevel(), 0);
		assertEquals(forest.getLeafAt(coord2).getId(), 0);
		assertTrue(forest.getLeafAt(coord2).coordIsInside(coord2));
	}

	@Test
	void GetLeaftAtInvalid() {
		Forest forest = new Forest(2);

		double[] coord2 = { 1.75, .75 };
		assertEquals(forest.getLeafAt(coord2), null);
	}

	@Test
	void GetLeaftAfterAddNbrAt() {
		Forest forest = new Forest(2);

		double[] coord2 = { 1.75, .75 };
		forest.addNbrAt(coord2);

		assertEquals(forest.getLeafAt(coord2).getLevel(), 0);
		assertEquals(forest.getLeafAt(coord2).getId(), 1);
		assertTrue(forest.getLeafAt(coord2).coordIsInside(coord2));
	}

	@Test
	void IsPotentialNbrAfterAddNbrAdd() {
		Forest forest = new Forest(2);

		double[] coord2 = { 1.75, .75 };
		forest.addNbrAt(coord2);

		double[] coord = { 2.75, .75 };
		double[] starts = { 2.75, .75 };
		double[] lengths = { 2.75, .75 };
		assertTrue(forest.coordIsPotentialNbr(coord, starts, lengths));
	}
}
