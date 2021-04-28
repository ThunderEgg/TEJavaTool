package dev.thunderegg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EdgeTest {

	@Test
	void BSConstructor() {
		assertEquals(Edge.BS(), new Edge(0));
	}

	@Test
	void TNConstructor() {
		assertEquals(Edge.TN(), new Edge(1));
	}

	@Test
	void BNConstructor() {
		assertEquals(Edge.BN(), new Edge(2));
	}

	@Test
	void TSConstructor() {
		assertEquals(Edge.TS(), new Edge(3));
	}

	@Test
	void BWConstructor() {
		assertEquals(Edge.BW(), new Edge(4));
	}

	@Test
	void TEConstructor() {
		assertEquals(Edge.TE(), new Edge(5));
	}

	@Test
	void BEConstructor() {
		assertEquals(Edge.BE(), new Edge(6));
	}

	@Test
	void TWConstructor() {
		assertEquals(Edge.TW(), new Edge(7));
	}

	@Test
	void SWConstructor() {
		assertEquals(Edge.SW(), new Edge(8));
	}

	@Test
	void NEConstructor() {
		assertEquals(Edge.NE(), new Edge(9));
	}

	@Test
	void SEConstructor() {
		assertEquals(Edge.SE(), new Edge(10));
	}

	@Test
	void NWConstructor() {
		assertEquals(Edge.NW(), new Edge(11));
	}

	@Test
	void BSgetOpposite() {
		assertEquals(Edge.BS().getOpposite(), Edge.TN());
	}

	@Test
	void TNgetOpposite() {
		assertEquals(Edge.TN().getOpposite(), Edge.BS());
	}

	@Test
	void BNgetOpposite() {
		assertEquals(Edge.BN().getOpposite(), Edge.TS());
	}

	@Test
	void TSgetOpposite() {
		assertEquals(Edge.TS().getOpposite(), Edge.BN());
	}

	@Test
	void BWgetOpposite() {
		assertEquals(Edge.BW().getOpposite(), Edge.TE());
	}

	@Test
	void TEgetOpposite() {
		assertEquals(Edge.TE().getOpposite(), Edge.BW());
	}

	@Test
	void BEgetOpposite() {
		assertEquals(Edge.BE().getOpposite(), Edge.TW());
	}

	@Test
	void TWgetOpposite() {
		assertEquals(Edge.TW().getOpposite(), Edge.BE());
	}

	@Test
	void SWgetOpposite() {
		assertEquals(Edge.SW().getOpposite(), Edge.NE());
	}

	@Test
	void NEgetOpposite() {
		assertEquals(Edge.NE().getOpposite(), Edge.SW());
	}

	@Test
	void SEgetOpposite() {
		assertEquals(Edge.SE().getOpposite(), Edge.NW());
	}

	@Test
	void NWgetOpposite() {
		assertEquals(Edge.NW().getOpposite(), Edge.SE());
	}

	@Test
	void GetNumOrthantsForDimension2() {
		assertEquals(Edge.getNumEdgesForDimension(2), 0);
	}

	@Test
	void GetNumOrthantsForDimension3() {
		assertEquals(Edge.getNumEdgesForDimension(3), 12);
	}

	@Test
	void GetValuesForDimension2() {
		Edge[] values = Edge.getValuesForDimension(2);
		assertEquals(values.length, 0);
	}

	@Test
	void GetValuesForDimension3() {
		Edge[] values = Edge.getValuesForDimension(3);
		assertEquals(values.length, 12);
		assertEquals(values[0], Edge.BS());
		assertEquals(values[1], Edge.TN());
		assertEquals(values[2], Edge.BN());
		assertEquals(values[3], Edge.TS());
		assertEquals(values[4], Edge.BW());
		assertEquals(values[5], Edge.TE());
		assertEquals(values[6], Edge.BE());
		assertEquals(values[7], Edge.TW());
		assertEquals(values[8], Edge.SW());
		assertEquals(values[9], Edge.NE());
		assertEquals(values[10], Edge.SE());
		assertEquals(values[11], Edge.NW());
	}

	@Test
	void EqualsOtherObject() {
		Object o = new Object();
		Edge s = Edge.SE();
		assertNotEquals(s, o);
	}

	@Test
	void EqualsSameObject() {
		Edge s = Edge.SE();
		assertEquals(s, s);
	}

	@Test
	void EqualsDifferentOrthantSameDimension() {
		Edge s1 = Edge.SE();
		Edge s2 = Edge.NW();
		assertNotEquals(s1, s2);
	}

	@Test
	void getIndex() {
		for (int i = 0; i < 12; i++) {
			assertEquals(new Edge(i).getIndex(), i);
		}
	}

	@Test
	void BSToString() {
		assertEquals(Edge.BS().toString(), "BS");
	}

	@Test
	void TNToString() {
		assertEquals(Edge.TN().toString(), "TN");
	}

	@Test
	void BNToString() {
		assertEquals(Edge.BN().toString(), "BN");
	}

	@Test
	void TSToString() {
		assertEquals(Edge.TS().toString(), "TS");
	}

	@Test
	void BWToString() {
		assertEquals(Edge.BW().toString(), "BW");
	}

	@Test
	void TEToString() {
		assertEquals(Edge.TE().toString(), "TE");
	}

	@Test
	void BEToString() {
		assertEquals(Edge.BE().toString(), "BE");
	}

	@Test
	void TWToString() {
		assertEquals(Edge.TW().toString(), "TW");
	}

	@Test
	void SWToString() {
		assertEquals(Edge.SW().toString(), "SW");
	}

	@Test
	void NEToString() {
		assertEquals(Edge.NE().toString(), "NE");
	}

	@Test
	void SEToString() {
		assertEquals(Edge.SE().toString(), "SE");
	}

	@Test
	void NWToString() {
		assertEquals(Edge.NW().toString(), "NW");
	}

	@Test
	void InvalidToString() {
		assertEquals((new Edge(100)).toString(), "INVALID VALUE: 100");
	}

	@Test
	void BSFromString() {
		assertEquals(Edge.fromString("BS"), Edge.BS());
	}

	@Test
	void TNFromString() {
		assertEquals(Edge.fromString("TN"), Edge.TN());
	}

	@Test
	void BNFromString() {
		assertEquals(Edge.fromString("BN"), Edge.BN());
	}

	@Test
	void TSFromString() {
		assertEquals(Edge.fromString("TS"), Edge.TS());
	}

	@Test
	void BWFromString() {
		assertEquals(Edge.fromString("BW"), Edge.BW());
	}

	@Test
	void TEFromString() {
		assertEquals(Edge.fromString("TE"), Edge.TE());
	}

	@Test
	void BEFromString() {
		assertEquals(Edge.fromString("BE"), Edge.BE());
	}

	@Test
	void TWFromString() {
		assertEquals(Edge.fromString("TW"), Edge.TW());
	}

	@Test
	void SWFromString() {
		assertEquals(Edge.fromString("SW"), Edge.SW());
	}

	@Test
	void NEFromString() {
		assertEquals(Edge.fromString("NE"), Edge.NE());
	}

	@Test
	void SEFromString() {
		assertEquals(Edge.fromString("SE"), Edge.SE());
	}

	@Test
	void NWFromString() {
		assertEquals(Edge.fromString("NW"), Edge.NW());
	}

	@Test
	void InvalidFromString() {
		assertThrows(IllegalArgumentException.class, () -> {
			Orthant.fromString("BLAH");
		});
	}

}
