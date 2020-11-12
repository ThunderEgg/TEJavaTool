package dev.thunderegg.meshcreator.twodimensiontimer;

/**
 * A key for patch associated statistics
 */
public class PatchKey extends DomainKey {

    /**
     * The id of the patch associated with the statistic
     */
    public final int patchId;

    /**
     * Construct a new PatchKey
     * 
     * @param name     the name of the statistic
     * @param domainId the id of the domain associated with the statistic
     * @param patchId  the id of the patch associated with the statistic
     */
    public PatchKey(String name, int domainId, int patchId) {
        super(name, domainId);
        this.patchId = patchId;
    }

    /**
     * Compare the keys by name, domainId, and then patchId
     * 
     * @param a the first key
     * @param b the second key
     * @return 0, negative, or postive value if a is equal to, less than, or greater
     *         than b
     */
    public static int compare(PatchKey key1, PatchKey key2) {
        int retval = DomainKey.compare(key1, key2);
        if (retval == 0) {
            retval = Integer.compare(key1.patchId, key2.patchId);
        }
        return retval;
    }

    @Override
    public boolean equals(Object obj) {
        boolean retval = false;
        if (obj != null && getClass() == obj.getClass()) {
            PatchKey b = (PatchKey) obj;
            retval = compare(this, b) == 0;

        }
        return retval;
    }

}
