package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.Map;
import java.util.TreeMap;

/**
 * A collection of statistics that have a patch and domain associated with it.
 */
public class PatchStatistics extends DomainStatistics {
    /**
     * Map of name, domain, and patch id to statistic
     */
    private Map<String, Map<Integer, Map<Integer, Statistic>>> domainStats = new TreeMap<>();

    /**
     * Get a patch specific statistic
     * 
     * @param name     the name of statistic
     * @param domainId the id of the domain
     * @param patchId  the id of the patch
     * @return the statistic
     */
    public Statistic getStatisticForPatch(String name, int domainId, int patchId) {
        Statistic retval = null;
        if (domainStats.containsKey(name) && domainStats.get(name).containsKey(domainId)) {
            retval = domainStats.get(name).get(domainId).get(patchId);
        }
        if (retval != null) {
            retval = new Statistic(retval);
        }
        return retval;
    }

    /**
     * Add a patch specific statistic
     * 
     * @param name     the name of the statistic
     * @param domainId the id of the domain
     * @param patchId  the id of the patch
     * @param stat     the statistic to add
     */
    public void addStatisticForPatch(String name, int domainId, int patchId, Statistic stat) {
        addStatisticForDomain(name, domainId, stat);
        if (!domainStats.containsKey(name)) {
            domainStats.put(name, new TreeMap<>());
        }
        if (!domainStats.get(name).containsKey(domainId)) {
            domainStats.get(name).put(domainId, new TreeMap<>());
        }
        domainStats.get(name).get(domainId).merge(patchId, new Statistic(stat), Statistic::merge);
    }
}
