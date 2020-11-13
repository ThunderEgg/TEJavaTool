package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * A collection of statistics that do not have a domain associated with it, but
 * not a patch
 */
public class DomainStatistics extends UnassociatedStatistics {
    /**
     * Map from domain to name to stat
     */
    private TreeMap<DomainKey, Statistic> domainStats = new TreeMap<>(DomainKey::compare);

    /**
     * Construct a new DomainStatistics object
     * 
     * @param name the of this group of statistics
     */
    public DomainStatistics(String name) {
        super(name);
    }

    /**
     * Get a list of domains that have statistics
     * 
     * @return the list
     */
    public Collection<Integer> getDomainsForName(String name) {
        DomainKey lower = new DomainKey(name, Integer.MIN_VALUE);
        DomainKey upper = new DomainKey(name, Integer.MAX_VALUE);
        NavigableMap<DomainKey, Statistic> subMap = domainStats.subMap(lower, true, upper, true);
        ArrayList<Integer> list = new ArrayList<Integer>(subMap.size());
        for (DomainKey key : subMap.keySet()) {
            list.add(key.domainId);
        }
        return list;
    }

    /**
     * Add a domain specific statistic
     * 
     * @param key  the key
     * @param stat the statistic
     */
    public void addStatisticForDomain(DomainKey key, Statistic stat) {
        addStatistic(key, stat);
        domainStats.merge(key, new Statistic(stat), Statistic::merge);
    }

    /**
     * Get a domain specific statistic
     * 
     * @param key the key
     * @return the statistic
     */
    public Statistic getStatisticForDomain(DomainKey key) {
        Statistic retval = domainStats.get(key);
        if (retval != null) {
            retval = new Statistic(retval);
        }
        return retval;
    }
}
