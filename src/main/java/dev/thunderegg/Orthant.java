package dev.thunderegg;

public class Orthant {
	/**
	 * The number of Cartesian dimensions
	 */
	private int dimension;
	/**
	 * The value of this orthant
	 */
	private int val;

	/**
	 * Construct a new Orthant
	 * 
	 * @param dimension the number of Cartesian dimensions
	 * @param val       the value
	 */
	public Orthant(int dimension, int val) {
		this.dimension = dimension;
		this.val = val;
	}

	/**
	 * Get the number of valid values for a given dimension
	 * 
	 * @param dimension the number of Cartesian dimensions
	 * @return the number of values
	 */
	public static int getNumOrthantsForDimension(int dimension) {
		return 0b1 << dimension;
	}

	/**
	 * Get an array of all the valid values for a given dimension
	 * 
	 * @param dimension the number of Cartesian dimensions
	 * @return the array of values
	 */
	public static Orthant[] getValuesForDimension(int dimension) {
		Orthant[] ret = new Orthant[getNumOrthantsForDimension(dimension)];
		for (int i = 0; i < getNumOrthantsForDimension(dimension); i++) {
			ret[i] = new Orthant(dimension, i);
		}
		return ret;
	}

	/**
	 * Lower Axis
	 * 
	 * @return the new Orthant
	 */
	public static Orthant Lower() {
		return new Orthant(1, 0b0);
	}

	/**
	 * Upper Axis
	 * 
	 * @return the new Orthant
	 */
	public static Orthant Upper() {
		return new Orthant(1, 0b1);
	}

	/**
	 * Southwestern Quadrant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant SW() {
		return new Orthant(2, 0b00);
	}

	/**
	 * Southeastern Quadrant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant SE() {
		return new Orthant(2, 0b01);
	}

	/**
	 * Northwestern Quadrant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant NW() {
		return new Orthant(2, 0b10);
	}

	/**
	 * Northeastern Quadrant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant NE() {
		return new Orthant(2, 0b11);
	}

	/**
	 * Bottom-South-West octant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant BSW() {
		return new Orthant(3, 0b000);
	}

	/**
	 * Bottom-South-East octant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant BSE() {
		return new Orthant(3, 0b001);
	}

	/**
	 * Bottom-North-West octant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant BNW() {
		return new Orthant(3, 0b010);
	}

	/**
	 * Bottom-North-East octant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant BNE() {
		return new Orthant(3, 0b011);
	}

	/**
	 * Top-South-West octant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant TSW() {
		return new Orthant(3, 0b100);
	}

	/**
	 * Top-South-East octant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant TSE() {
		return new Orthant(3, 0b101);
	}

	/**
	 * Top-North-West octant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant TNW() {
		return new Orthant(3, 0b110);
	}

	/**
	 * Top-North-East octant
	 * 
	 * @return the new Orthant
	 */
	public static Orthant TNE() {
		return new Orthant(3, 0b111);
	}

	/**
	 * Get the index of this orthant
	 * 
	 * @return the index
	 */
	public int getIndex() {
		return val;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Orthant)) {
			return false;
		}

		Orthant orth = (Orthant) o;

		return orth.dimension == dimension && orth.val == val;
	}

	/**
	 * Return whether or not the Orthant is lower on a specific axis
	 * 
	 * @param axis the axis
	 * @return whether or not it's lower
	 */
	public boolean isLowerOnAxis(int axis) {
		return (val & (0b1 << axis)) == 0;
	}

	/**
	 * Get the exterior sides on this orthant
	 * 
	 * @return the sides, in sorted order
	 */
	public Side[] getExteriorSides() {
		Side[] ret = new Side[dimension];
		for (int i = 0; i < dimension; i++) {
			int side_val = ((i << 1) | ((val >> i) & 0b1));
			ret[i] = new Side(side_val);
		}
		return ret;
	}

	/**
	 * Get the interior sides on this orthant
	 * 
	 * @return the sides, in sorted order
	 */
	public Side[] getInteriorSides() {
		Side[] ret = new Side[dimension];
		for (int i = 0; i < dimension; i++) {
			int side_val = ((i << 1) | ((val >> i) & 0b1)) ^ 0b1;
			ret[i] = new Side(side_val);
		}
		return ret;
	}

	/**
	 * Get the neighboring orthant on a particular side
	 * 
	 * @param s the side
	 * @return the orthant
	 */
	public Orthant getNbrOnSide(Side s) {
		int nbr_val = val ^ (1 << s.getAxis());
		return new Orthant(dimension, nbr_val);
	}

	/**
	 * Get the orthants that lie on a particular side.
	 * 
	 * @param dimension the number of Cartesian dimensions
	 * @param s         the side
	 * @return the orthants, in sorted order
	 */
	public static Orthant[] GetValuesOnSide(int dimension, Side s) {
		Orthant[] ret = new Orthant[getNumOrthantsForDimension(dimension) / 2];
		int bit_to_insert = s.getAxis();
		int set_bit = s.isLowerOnAxis() ? 0x0 : 0x1;
		int lower_mask = ~((~0x0) << bit_to_insert);
		int upper_mask = (~0x0) << (bit_to_insert + 1);
		for (int i = 0; i < ret.length; i++) {
			int new_value = (i << 1) & upper_mask;
			new_value |= i & lower_mask;
			new_value |= set_bit << bit_to_insert;
			ret[i] = new Orthant(dimension, new_value);
		}
		return ret;
	}

	@Override
	public String toString() {
		String ret = new String();
		if (dimension == 1) {
			if (equals(Orthant.Lower())) {
				ret = "LOWER";
			} else if (equals(Orthant.Upper())) {
				ret = "UPPER";
			} else {
				ret = "INVALID 1D VALUE: " + val;
			}
		} else if (dimension == 2) {
			if (equals(Orthant.SW())) {
				ret = "SW";
			} else if (equals(Orthant.SE())) {
				ret = "SE";
			} else if (equals(Orthant.NW())) {
				ret = "NW";
			} else if (equals(Orthant.NE())) {
				ret = "NE";
			} else {
				ret = "INVALID 2D VALUE: " + val;
			}
		} else if (dimension == 3) {
			if (equals(Orthant.BSW())) {
				ret = "BSW";
			} else if (equals(Orthant.BSE())) {
				ret = "BSE";
			} else if (equals(Orthant.BNW())) {
				ret = "BNW";
			} else if (equals(Orthant.BNE())) {
				ret = "BNE";
			} else if (equals(Orthant.TSW())) {
				ret = "TSW";
			} else if (equals(Orthant.TSE())) {
				ret = "TSE";
			} else if (equals(Orthant.TNW())) {
				ret = "TNW";
			} else if (equals(Orthant.TNE())) {
				ret = "TNE";
			} else {
				ret = "INVALID 3D VALUE: " + val;
			}
		} else {
			ret = "[";
			Side[] exterior_sides = getExteriorSides();
			for (int i = 1; i < dimension; i++) {
				ret += exterior_sides[dimension - i].toString() + ", ";

			}
			ret += exterior_sides[0] + "]";
		}
		return ret;
	}

	public static Orthant fromString(String string) {
		Orthant ret = null;
		if (string.equals("LOWER")) {
			ret = Orthant.Lower();
		} else if (string.equals("UPPER")) {
			ret = Orthant.Upper();
		} else if (string.equals("SW")) {
			ret = Orthant.SW();
		} else if (string.equals("SE")) {
			ret = Orthant.SE();
		} else if (string.equals("NW")) {
			ret = Orthant.NW();
		} else if (string.equals("NE")) {
			ret = Orthant.NE();
		} else if (string.equals("BSW")) {
			ret = Orthant.BSW();
		} else if (string.equals("BSE")) {
			ret = Orthant.BSE();
		} else if (string.equals("BNW")) {
			ret = Orthant.BNW();
		} else if (string.equals("BNE")) {
			ret = Orthant.BNE();
		} else if (string.equals("TSW")) {
			ret = Orthant.TSW();
		} else if (string.equals("TSE")) {
			ret = Orthant.TSE();
		} else if (string.equals("TNW")) {
			ret = Orthant.TNW();
		} else if (string.equals("TNE")) {
			ret = Orthant.TNE();
		} else {
			throw new IllegalArgumentException("Invalid Orthant String");
		}
		return ret;
	}

	public Orthant collapseOnAxis(int axis) {
		if (axis < 0 || axis > dimension) {
			throw new IllegalArgumentException("Invalid Axis: " + axis);
		}
		Orthant ret = null;
		if (dimension > 1) {
			int lower_mask = ~(~0x0 << axis);
			int upper_mask = ~0x0 << axis + 1;
			int new_val = (val & lower_mask) | ((val & upper_mask) >> 1);
			ret = new Orthant(dimension - 1, new_val);
		}
		return ret;
	}

	/**
	 * Get the orthant on the opposite side
	 * 
	 * @return the orthant on the opposite side
	 */
	public Orthant getOpposite() {
		int mask = ~(~0x0 << dimension);
		return new Orthant(dimension, val ^ mask);
	}

	@Override
	public int hashCode() {
		return val;
	}
}
