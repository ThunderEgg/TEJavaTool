package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * A collection of statistics that do not have a domain or patch associated with
 * it.
 */
public class UnassociatedStatistics {
    private Map<String, Statistic> stats = new TreeMap<>();

    /**
     * Add a statistic to this timing if the statistic is already added, it merges
     * with the exiting statistic
     * 
     * @param name the name of statistic to add
     * @param stat the statistic to add
     */
    public void addStatistic(String name, Statistic stat) {
        stats.merge(name, new Statistic(stat), Statistic::merge);
    }

    /**
     * Get the names of the staticstics in this timing
     * 
     * @return the names of the statistics
     */
    public Collection<String> getNames() {
        return new ArrayList<String>(stats.keySet());
    }

    /**
     * Get a particular statistic
     * 
     * @param name the name of the statistic
     * @return the statistic, or null if there is no statistic associated with the
     *         name
     */
    public Statistic getStatistic(String name) {
        Statistic stat = stats.get(name);
        Statistic ret = null;
        if (stat != null) {
            ret = new Statistic(stat);
        }
        return ret;
    }

}
