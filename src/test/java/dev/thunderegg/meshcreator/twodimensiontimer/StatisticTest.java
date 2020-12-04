package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.thunderegg.Info;
import dev.thunderegg.Timing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StatisticTest {

    @Test
    public void defaultMinIsMaxOfDouble() {
        Statistic stat = new Statistic();
        assertThat(stat.getMin(), is(equalTo(Double.POSITIVE_INFINITY)));
    }

    @Test
    public void setMin() {
        Statistic stat = new Statistic();
        stat.setMin(2.022);
        assertThat(stat.getMin(), is(equalTo(2.022)));
    }

    @Test
    public void defaultMinPropertyValueIsMaxOfDouble() {
        Statistic stat = new Statistic();
        assertThat(stat.getMinProperty().getValue(), is(equalTo(Double.POSITIVE_INFINITY)));
    }

    @Test
    public void getMinPropertyBean() {
        Statistic stat = new Statistic();
        assertThat(stat.getMinProperty().getBean(), is(sameInstance(stat)));
    }

    @Test
    public void getMinPropertyName() {
        Statistic stat = new Statistic();
        assertThat(stat.getMinProperty().getName(), is("min"));
    }

    @Test
    public void defaultMaxIsMinOfDouble() {
        Statistic stat = new Statistic();
        assertThat(stat.getMax(), is(equalTo(Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void setMax() {
        Statistic stat = new Statistic();
        stat.setMax(2.022);
        assertThat(stat.getMax(), is(equalTo(2.022)));
    }

    @Test
    public void defaultMaxPropertyValueIsMaxOfDouble() {
        Statistic stat = new Statistic();
        assertThat(stat.getMaxProperty().getValue(), is(equalTo(Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void getMaxPropertyBean() {
        Statistic stat = new Statistic();
        assertThat(stat.getMaxProperty().getBean(), is(sameInstance(stat)));
    }

    @Test
    public void getMaxPropertyName() {
        Statistic stat = new Statistic();
        assertThat(stat.getMaxProperty().getName(), is("max"));
    }

    @Test
    public void defaultSumIsZero() {
        Statistic stat = new Statistic();
        assertThat(stat.getSum(), is(equalTo(0.0)));
    }

    @Test
    public void setSum() {
        Statistic stat = new Statistic();
        stat.setSum(2.022);
        assertThat(stat.getSum(), is(equalTo(2.022)));
    }

    @Test
    public void defaultSumPropertyValueIsZero() {
        Statistic stat = new Statistic();
        assertThat(stat.getSumProperty().getValue(), is(equalTo(0.0)));
    }

    @Test
    public void getSumPropertyBean() {
        Statistic stat = new Statistic();
        assertThat(stat.getSumProperty().getBean(), is(sameInstance(stat)));
    }

    @Test
    public void getSumPropertyName() {
        Statistic stat = new Statistic();
        assertThat(stat.getSumProperty().getName(), is("sum"));
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
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(-2390);
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic(stat1);
        assertThat(stat1, is(equalTo(stat2)));
        assertThat(stat1.getMinProperty(), is(not(sameInstance(stat2.getMinProperty()))));
        assertThat(stat1.getMaxProperty(), is(not(sameInstance(stat2.getMaxProperty()))));
        assertThat(stat1.getSumProperty(), is(not(sameInstance(stat2.getSumProperty()))));
    }

    @Test
    public void sameEquals() {
        Statistic stat1 = new Statistic();
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(-2390);
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.setMin(-2);
        stat2.setMax(23);
        stat2.setSum(-2390);
        stat2.numCalls = 3243980;
        assertThat(stat1, is(equalTo(stat2)));
    }

    @Test
    public void differentSumNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(42);
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.setMin(-2);
        stat2.setMax(23);
        stat2.setSum(-2390);
        stat2.numCalls = 3243980;
        assertThat(stat1, is(not(equalTo(stat2))));
    }

    @Test
    public void differentMinNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(-2390);
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.setMin(0);
        stat2.setMax(23);
        stat2.setSum(-2390);
        stat2.numCalls = 3243980;
        assertThat(stat1, is(not(equalTo(stat2))));
    }

    @Test
    public void differentMaxNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(-2390);
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.setMin(-2);
        stat2.setMax(-292);
        stat2.setSum(-2390);
        stat2.numCalls = 3243980;
        assertThat(stat1, is(not(equalTo(stat2))));
    }

    @Test
    public void differentNumCallsNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(-2390);
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.setMin(-2);
        stat2.setMax(23);
        stat2.setSum(-2390);
        stat2.numCalls = 299;
        assertThat(stat1, is(not(equalTo(stat2))));
    }

    @Test
    public void differentClassNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(-2390);
        stat1.numCalls = 3243980;
        Double d = Double.valueOf(2);
        assertThat(stat1, is(not(equalTo(d))));
    }

    @Test
    public void nullNotEqual() {
        Statistic stat1 = new Statistic();
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(-2390);
        stat1.numCalls = 3243980;
        assertThat(stat1, is(not(equalTo(null))));
    }

    @Test
    public void testToString() {
        Statistic stat1 = new Statistic();
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(-2390);
        stat1.numCalls = 3243980;
        assertThat(stat1.toString(), is(equalTo("min: " + stat1.getMin() + ", max: " + stat1.getMax() + ", sum: "
                + stat1.getSum() + ", numCalls: " + stat1.numCalls)));
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
        stat1.setMin(-2);
        Statistic stat2 = new Statistic();
        stat2.setMin(0);
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.getMin(), is(equalTo(-2.0)));
    }

    @Test
    public void mergeMin2() {
        Statistic stat1 = new Statistic();
        stat1.setMin(0);
        Statistic stat2 = new Statistic();
        stat2.setMin(-2);
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.getMin(), is(equalTo(-2.0)));
    }

    @Test
    public void mergeMax1() {
        Statistic stat1 = new Statistic();
        stat1.setMax(-2);
        Statistic stat2 = new Statistic();
        stat2.setMax(0);
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.getMax(), is(equalTo(0.0)));
    }

    @Test
    public void mergeMax2() {
        Statistic stat1 = new Statistic();
        stat1.setMax(0);
        Statistic stat2 = new Statistic();
        stat2.setMax(-2);
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.getMax(), is(equalTo(0.0)));
    }

    @Test
    public void mergeSum() {
        Statistic stat1 = new Statistic();
        stat1.setSum(11);
        Statistic stat2 = new Statistic();
        stat2.setSum(96);
        Statistic result = Statistic.merge(stat1, stat2);
        assertThat(result.getSum(), is(equalTo(107.0)));
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

    @Test
    public void timingConstructor() {
        Timing timing = new Timing();
        timing.name = "Child";
        timing.domain_id = 1;
        timing.patch_id = Integer.MAX_VALUE;
        timing.min = 1;
        timing.max = 10;
        timing.sum = 11;
        timing.num_calls = 100;

        Statistic stat = new Statistic(timing);
        assertThat(stat.getMin(), is(equalTo(timing.min)));
        assertThat(stat.getMax(), is(equalTo(timing.max)));
        assertThat(stat.getSum(), is(equalTo(timing.sum)));
        assertThat(stat.numCalls, is(equalTo(timing.num_calls)));
    }

    @Test
    public void infoConstructor() {
        Info info = new Info();
        info.name = "Child";
        info.min = 1;
        info.max = 10;
        info.sum = 11;
        info.num_calls = 100;

        Statistic stat = new Statistic(info);
        assertThat(stat.getMin(), is(equalTo(info.min)));
        assertThat(stat.getMax(), is(equalTo(info.max)));
        assertThat(stat.getSum(), is(equalTo(info.sum)));
        assertThat(stat.numCalls, is(equalTo(info.num_calls)));
    }

    @Test
    public void getStatisticAverage() {
        Statistic stat = new Statistic();
        stat.setMin(-2);
        stat.setMax(23);
        stat.setSum(-2390);
        stat.numCalls = 100;
        assertThat(stat.getStatistic("Average"), is(stat.getSum() / stat.numCalls));
    }

    @Test
    public void getStatisticMin() {
        Statistic stat = new Statistic();
        stat.setMin(-2);
        stat.setMax(23);
        stat.setSum(-2390);
        stat.numCalls = 100;
        assertThat(stat.getStatistic("Min"), is(stat.getMin()));
    }

    @Test
    public void getStatisticMax() {
        Statistic stat = new Statistic();
        stat.setMin(-2);
        stat.setMax(23);
        stat.setSum(-2390);
        stat.numCalls = 100;
        assertThat(stat.getStatistic("Max"), is(stat.getMax()));
    }

    @Test
    public void getStatisticInvalid() {
        Statistic stat = new Statistic();
        stat.setMin(-2);
        stat.setMax(23);
        stat.setSum(-2390);
        stat.numCalls = 100;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            stat.getStatistic("sildfsjafl;jkl;");
        });
    }
}
