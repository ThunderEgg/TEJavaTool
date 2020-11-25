package dev.thunderegg.meshcreator.twodimensiontimer;

import dev.thunderegg.Info;
import dev.thunderegg.Timing;

/**
 * Represents a statistic about something
 */
public class Statistic {
    /**
     * Min value
     */
    public double min = Double.POSITIVE_INFINITY;
    /**
     * Max value
     */
    public double max = Double.NEGATIVE_INFINITY;
    /**
     * sum of all values
     */
    public double sum = 0;
    /**
     * Number of values
     */
    public int numCalls = 0;

    /**
     * Default constructor
     */
    public Statistic() {

    }

    /**
     * Copy constructor
     * 
     * @param statistic the statistic to copy
     */
    public Statistic(Statistic statistic) {
        min = statistic.min;
        max = statistic.max;
        sum = statistic.sum;
        numCalls = statistic.numCalls;
    }

    /**
     * Create a time statistic form a timing
     * 
     * @param timing the timing to create the statistic from
     */
    public Statistic(Timing timing) {
        min = timing.min;
        max = timing.max;
        sum = timing.sum;
        numCalls = timing.num_calls;
    }

    /**
     * Create a time statistic form a info object
     * 
     * @param info the info object to create the statistic from
     */
    public Statistic(Info info) {
        min = info.min;
        max = info.max;
        sum = info.sum;
        numCalls = info.num_calls;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj != null && obj.getClass() == Statistic.class) {
            Statistic other = (Statistic) obj;
            equal = (min == other.min && max == other.max && sum == other.sum && numCalls == other.numCalls);
        }
        return equal;
    }

    @Override
    public String toString() {
        return "min: " + min + ", max: " + max + ", sum: " + sum + ", numCalls: " + numCalls;
    }

    public static Statistic merge(Statistic a, Statistic b) {
        Statistic ret = new Statistic();
        ret.min = Math.min(a.min, b.min);
        ret.max = Math.max(a.max, b.max);
        ret.sum = a.sum + b.sum;
        ret.numCalls = a.numCalls + b.numCalls;
        return ret;
    }

    public double getStatistic(String stat) {
        double ret;
        switch (stat) {
            case "Average":
                ret = sum / numCalls;
                break;
            case "Min":
                ret = min;
                break;
            case "Max":
                ret = max;
                break;
            default:
                throw new IllegalArgumentException("Invalid argument of " + stat);
        }
        return ret;
    }

}
