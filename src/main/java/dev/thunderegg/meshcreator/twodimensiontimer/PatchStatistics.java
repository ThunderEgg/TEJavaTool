package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import dev.thunderegg.Patch;

/**
 * A collection of statistics that have a patch and domain associated with it.
 */
public class PatchStatistics extends DomainStatistics {
    /**
     * Map of name, domain, and patch id to statistic
     */
    private Map<PatchKey, Statistic> domainStats = new TreeMap<>(PatchKey::compare);

    /**
     * Construct a new UnassociatedStatistics object
     * 
     * @param name the of this group of statistics
     */
    public PatchStatistics(String name) {
        super(name);
    }

    /**
     * Get a patch specific statistic
     * 
     * @param key the key
     * @return the statistic
     */
    public Statistic getStatisticForPatch(PatchKey key) {
        Statistic retval = domainStats.get(key);
        if (retval != null) {
            retval = new Statistic(retval);
        }
        return retval;
    }

    /**
     * Add a patch specific statistic
     * 
     * @param key  the key
     * @param stat the statistic to add
     */
    public void addStatisticForPatch(PatchKey key, Statistic stat) {
        addStatisticForDomain(key, stat);
        domainStats.merge(key, new Statistic(stat), Statistic::merge);
    }
}
