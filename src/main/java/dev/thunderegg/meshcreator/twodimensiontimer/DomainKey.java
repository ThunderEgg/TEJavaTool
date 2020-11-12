package dev.thunderegg.meshcreator.twodimensiontimer;

/**
 * The key for domain statistics
 */
public class DomainKey extends UnassociatedKey {

    /**
     * The id of the domain associated with the statistic
     */
    public final int domainId;

    /**
     * Construct a new DomainKey
     * 
     * @param name     the name of the statistic
     * @param domainId the id of the domain that is associated with the statistic
     */
    public DomainKey(String name, int domainId) {
        super(name);
        this.domainId = domainId;
    }

    /**
     * Compare the keys by name and then domainId
     * 
     * @param a the first key
     * @param b the second key
     * @return 0, negative, or postive value if a is equal to, less than, or greater
     *         than b
     */
    public static int compare(DomainKey key1, DomainKey key2) {
        int retval = UnassociatedKey.compare(key1, key2);
        if (retval == 0) {
            retval = Integer.compare(key1.domainId, key2.domainId);
        }
        return retval;
    }

    @Override
    public boolean equals(Object obj) {
        boolean retval = false;
        if (obj != null && getClass() == obj.getClass()) {
            DomainKey b = (DomainKey) obj;
            retval = compare(this, b) == 0;

        }
        return retval;
    }

}
