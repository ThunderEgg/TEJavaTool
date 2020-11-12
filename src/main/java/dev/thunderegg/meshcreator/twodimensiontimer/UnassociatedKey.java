package dev.thunderegg.meshcreator.twodimensiontimer;

/**
 * The key for unassociated statistics
 */
public class UnassociatedKey {

    /**
     * The name of the statistic
     */
    public final String name;

    /**
     * Construct a new key
     * 
     * @param name the unassociated statistic
     */
    public UnassociatedKey(String name) {
        this.name = name;
    }

    /**
     * Compare the keys by name
     * 
     * @param a the first key
     * @param b the second key
     * @return 0, negative, or postive value if a is equal to, less than, or greater
     *         than b
     */
    public static int compare(UnassociatedKey a, UnassociatedKey b) {
        return a.name.compareTo(b.name);
    }

    @Override
    public boolean equals(Object obj) {
        boolean retval = false;
        if (obj != null && getClass() == obj.getClass()) {
            UnassociatedKey b = (UnassociatedKey) obj;
            retval = compare(this, b) == 0;

        }
        return retval;
    }

}
