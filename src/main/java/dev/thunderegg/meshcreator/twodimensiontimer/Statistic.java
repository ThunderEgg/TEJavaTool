package dev.thunderegg.meshcreator.twodimensiontimer;

import dev.thunderegg.Info;
import dev.thunderegg.Timing;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Represents a statistic about something
 */
public class Statistic {
    /**
     * Min value
     */
    private DoubleProperty min = new SimpleDoubleProperty(this, "min", Double.POSITIVE_INFINITY);
    /**
     * Max value
     */
    private DoubleProperty max = new SimpleDoubleProperty(this, "max", Double.NEGATIVE_INFINITY);
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
     * Get the minium value
     * 
     * @return the minimum value
     */
    public double getMin() {
        return min.doubleValue();
    }

    /**
     * Set the minimum value
     * 
     * @param value the minimum value
     */
    public void setMin(double value) {
        min.set(value);
    }

    /**
     * Get the min property
     * 
     * @return the min property
     */
    public DoubleProperty getMinProperty() {
        return min;
    }

    /**
     * Get the maximum value
     * 
     * @return the maximum value
     */
    public double getMax() {
        return max.doubleValue();
    }

    /**
     * Set the maximum value
     * 
     * @param value the maximum value
     */
    public void setMax(double value) {
        max.set(value);
    }

    /**
     * Get the max property
     * 
     * @return the max property
     */
    public DoubleProperty getMaxProperty() {
        return max;
    }

    /**
     * Copy constructor, performs a deep copy
     * 
     * @param statistic the statistic to copy
     */
    public Statistic(Statistic statistic) {
        setMin(statistic.getMin());
        setMax(statistic.getMax());
        sum = statistic.sum;
        numCalls = statistic.numCalls;
    }

    /**
     * Create a time statistic form a timing
     * 
     * @param timing the timing to create the statistic from
     */
    public Statistic(Timing timing) {
        setMin(timing.min);
        setMax(timing.max);
        sum = timing.sum;
        numCalls = timing.num_calls;
    }

    /**
     * Create a time statistic form a info object
     * 
     * @param info the info object to create the statistic from
     */
    public Statistic(Info info) {
        setMin(info.min);
        setMax(info.max);
        sum = info.sum;
        numCalls = info.num_calls;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj != null && obj.getClass() == Statistic.class) {
            Statistic other = (Statistic) obj;
            equal = (getMin() == other.getMin() && getMax() == other.getMax() && sum == other.sum
                    && numCalls == other.numCalls);
        }
        return equal;
    }

    @Override
    public String toString() {
        return "min: " + getMin() + ", max: " + getMax() + ", sum: " + sum + ", numCalls: " + numCalls;
    }

    public static Statistic merge(Statistic a, Statistic b) {
        Statistic ret = new Statistic();
        ret.setMin(Math.min(a.getMin(), b.getMin()));
        ret.setMax(Math.max(a.getMax(), b.getMax()));
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
                ret = getMin();
                break;
            case "Max":
                ret = getMax();
                break;
            default:
                throw new IllegalArgumentException("Invalid argument of " + stat);
        }
        return ret;
    }

}
