package dev.thunderegg.meshcreator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

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
