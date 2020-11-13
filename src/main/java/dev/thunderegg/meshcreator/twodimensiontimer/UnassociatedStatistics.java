package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

/**
 * A collection of statistics that do not have a domain or patch associated with
 * it.
 */
public class UnassociatedStatistics {

    private final String name;
    private TreeMap<UnassociatedKey, Statistic> stats = new TreeMap<>(UnassociatedKey::compare);

    /**
     * Construct a new UnassociatedStatistics object
     * 
     * @param name the of this group of statistics
     */
    public UnassociatedStatistics(String name) {
        this.name = name;
    }

    /**
     * Add a statistic to this timing if the statistic is already added, it merges
     * with the exiting statistic
     * 
     * @param key  the key
     * @param stat the statistic to add
     */
    public void addStatistic(UnassociatedKey key, Statistic stat) {
        stats.merge(key, new Statistic(stat), Statistic::merge);
    }

    /**
     * Get the names of the staticstics in this timing
     * 
     * @return the names of the statistics
     */
    public Collection<String> getStatisticNames() {
        ArrayList<String> list = new ArrayList<String>(stats.size());
        for (UnassociatedKey key : stats.keySet()) {
            list.add(key.name);
        }
        return list;
    }

    /**
     * Get a particular statistic
     * 
     * @param key the key
     * @return the statistic, or null if there is no statistic associated with the
     *         name
     */
    public Statistic getStatistic(UnassociatedKey key) {
        Statistic stat = stats.get(key);
        Statistic ret = null;
        if (stat != null) {
            ret = new Statistic(stat);
        }
        return ret;
    }

    /**
     * Get the name for this group of statistics
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
