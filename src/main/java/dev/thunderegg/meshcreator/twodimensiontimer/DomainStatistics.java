package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * A collection of statistics that do not have a domain associated with it, but
 * not a patch
 */
public class DomainStatistics extends UnassociatedStatistics {
    /**
     * Map from domain to name to stat
     */
    private Map<Integer, Map<String, Statistic>> domainStats = new TreeMap<>();

    /**
     * Get a list of domains that have statistics
     * 
     * @return the list
     */
    public Collection<Integer> getDomains() {
        return new ArrayList<Integer>(domainStats.keySet());
    }

    /**
     * Get the statistic names for a domain
     * 
     * @param domainId the id of the domain
     * @return the statistic names
     */
    public Collection<String> getNamesForDomain(int domainId) {
        Collection<String> retval = null;
        if (domainStats.containsKey(domainId)) {
            retval = new ArrayList<String>(domainStats.get(domainId).keySet());
        }
        return retval;
    }

    /**
     * Add a domain specific statistic
     * 
     * @param domainId the id of the domain
     * @param name     the name of statistic
     * @param stat     the statistic
     */
    public void addStatisticForDomain(int domainId, String name, Statistic stat) {
        addStatistic(name, stat);
        if (!domainStats.containsKey(domainId)) {
            domainStats.put(domainId, new TreeMap<>());
        }
        domainStats.get(domainId).merge(name, new Statistic(stat), Statistic::merge);
    }

    /**
     * Get a domain specific statistic
     * 
     * @param domainId the id of the domain
     * @param name     the name of statistic
     * @return the statistic
     */
    public Statistic getStatisticForDomain(int domainId, String name) {
        Statistic retval = null;
        if (domainStats.containsKey(domainId)) {
            retval = domainStats.get(domainId).get(name);
        }
        if (retval != null) {
            retval = new Statistic(retval);
        }
        return retval;
    }
}
