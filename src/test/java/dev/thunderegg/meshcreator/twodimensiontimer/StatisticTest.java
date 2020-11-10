package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StatisticTest {

    @Test
    public void defaultMinIsMaxOfDouble() {
        Statistic stat = new Statistic();
        assertThat(stat.min, is(equalTo(Double.POSITIVE_INFINITY)));
    }

    @Test
    public void defaultMaxIsMinOfDouble() {
        Statistic stat = new Statistic();
        assertThat(stat.max, is(equalTo(Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void defaultSumIsZero() {
        Statistic stat = new Statistic();
        assertThat(stat.sum, is(equalTo(0.0)));
    }

    @Test
    public void defaultNumCallsIsZero() {
        Statistic stat = new Statistic();
        assertThat(stat.numCalls, is(equalTo(0)));
    }

    @Test
    public void defaultEquals() {
        Statistic stat1 = new Statistic();
        Statistic stat2 = new Statistic();
        assertThat(stat1, is(equalTo(stat2)));
    }

    @Test
    public void copyConstructor() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        stat1.max = 23;
        stat1.sum = -2390;
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic(stat1);
        assertThat(stat1, is(equalTo(stat2)));
    }

    @Test
    public void sameEquals() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        stat1.max = 23;
        stat1.sum = -2390;
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.min = -2;
        stat2.max = 23;
        stat2.sum = -2390;
        stat2.numCalls = 3243980;
        assertThat(stat1, is(equalTo(stat2)));
    }

    @Test
    public void differentSumNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        stat1.max = 23;
        stat1.sum = 42;
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.min = -2;
        stat2.max = 23;
        stat2.sum = -2390;
        stat2.numCalls = 3243980;
        assertThat(stat1, is(not(equalTo(stat2))));
    }

    @Test
    public void differentMinNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        stat1.max = 23;
        stat1.sum = -2390;
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.min = 0;
        stat2.max = 23;
        stat2.sum = -2390;
        stat2.numCalls = 3243980;
        assertThat(stat1, is(not(equalTo(stat2))));
    }

    @Test
    public void differentMaxNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        stat1.max = 23;
        stat1.sum = -2390;
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.min = -2;
        stat2.max = -292;
        stat2.sum = -2390;
        stat2.numCalls = 3243980;
        assertThat(stat1, is(not(equalTo(stat2))));
    }

    @Test
    public void differentNumCallsNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        stat1.max = 23;
        stat1.sum = -2390;
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.min = -2;
        stat2.max = 23;
        stat2.sum = -2390;
        stat2.numCalls = 299;
        assertThat(stat1, is(not(equalTo(stat2))));
    }

    @Test
    public void differentClassNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        stat1.max = 23;
        stat1.sum = -2390;
        stat1.numCalls = 3243980;
        Double d = Double.valueOf(2);
        assertThat(stat1, is(not(equalTo(d))));
    }

    @Test
    public void nullNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        stat1.max = 23;
        stat1.sum = -2390;
        stat1.numCalls = 3243980;
        assertThat(stat1, is(not(equalTo(null))));
    }

    @Test
    public void testToString() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        stat1.max = 23;
        stat1.sum = -2390;
        stat1.numCalls = 3243980;
        assertThat(stat1.toString(), is(equalTo("min: " + stat1.min + ", max: " + stat1.max + ", sum: " + stat1.sum
                + ", numCalls: " + stat1.numCalls)));
    }

    @Test
    public void mergeEncapsulation() {
        Statistic stat1 = new Statistic();
        Statistic stat2 = new Statistic();
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result, is(not(sameInstance(stat1))));
        assertThat(result, is(not(sameInstance(stat2))));
    }

    @Test
    public void mergeMin1() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        Statistic stat2 = new Statistic();
        stat2.min = 0;
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.min, is(equalTo(-2.0)));
    }

    @Test
    public void mergeMin2() {
        Statistic stat1 = new Statistic();
        stat1.min = 0;
        Statistic stat2 = new Statistic();
        stat2.min = -2;
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.min, is(equalTo(-2.0)));
    }

    @Test
    public void mergeMax1() {
        Statistic stat1 = new Statistic();
        stat1.max = -2;
        Statistic stat2 = new Statistic();
        stat2.max = 0;
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.max, is(equalTo(0.0)));
    }

    @Test
    public void mergeMax2() {
        Statistic stat1 = new Statistic();
        stat1.max = 0;
        Statistic stat2 = new Statistic();
        stat2.max = -2;
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.max, is(equalTo(0.0)));
    }

    @Test
    public void mergeSum() {
        Statistic stat1 = new Statistic();
        stat1.sum = 11;
        Statistic stat2 = new Statistic();
        stat2.sum = 96;
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.sum, is(equalTo(107.0)));
    }

    @Test
    public void mergeNumCalls() {
        Statistic stat1 = new Statistic();
        stat1.numCalls = 11;
        Statistic stat2 = new Statistic();
        stat2.numCalls = 96;
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.numCalls, is(equalTo(107)));
    }
}
