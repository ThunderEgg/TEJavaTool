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
    private Map<String, Map<Integer, Statistic>> domainStats = new TreeMap<>();

    /**
     * Get a list of domains that have statistics
     * 
     * @return the list
     */
    public Collection<Integer> getDomainsForName(String name) {
        Collection<Integer> retval = null;
        if (domainStats.containsKey(name)) {
            retval = new ArrayList<Integer>(domainStats.get(name).keySet());
        }
        return retval;
    }

    /**
     * Add a domain specific statistic
     * 
     * @param name     the name of statistic
     * @param domainId the id of the domain
     * @param stat     the statistic
     */
    public void addStatisticForDomain(String name, int domainId, Statistic stat) {
        addStatistic(name, stat);
        if (!domainStats.containsKey(name)) {
            domainStats.put(name, new TreeMap<>());
        }
        domainStats.get(name).merge(domainId, new Statistic(stat), Statistic::merge);
    }

    /**
     * Get a domain specific statistic
     * 
     * @param name     the name of statistic
     * @param domainId the id of the domain
     * @return the statistic
     */
    public Statistic getStatisticForDomain(String name, int domainId) {
        Statistic retval = null;
        if (domainStats.containsKey(name)) {
            retval = domainStats.get(name).get(domainId);
        }
        if (retval != null) {
            retval = new Statistic(retval);
        }
        return retval;
    }
}
