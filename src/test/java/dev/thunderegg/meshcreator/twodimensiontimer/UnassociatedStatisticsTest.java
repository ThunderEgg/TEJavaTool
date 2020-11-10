package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.Collection;

public class UnassociatedStatisticsTest {

    private UnassociatedStatistics stats;

    @BeforeEach
    public void setup() {
        stats = new UnassociatedStatistics();
    }

    @Test
    public void getNamesEmpty() {
        Collection<String> names = stats.getNames();
        assertThat(names, is(not(nullValue())));
        assertThat(names.size(), is(equalTo(0)));
    }

    @Test
    public void getStatIsNullEmpty() {
        Statistic stat = stats.getStatistic("hello");
        assertThat(stat, is(nullValue()));
    }

    @Test
    public void addStatThenGetName() {
        Statistic stat = new Statistic();
        stats.addStatistic("Hello", stat);
        Collection<String> names = stats.getNames();
        assertThat(names, is(not(nullValue())));
        assertThat(names.size(), is(equalTo(1)));
        assertThat(names, contains("Hello"));
    }

    @Test
    public void addStatThenGetStat() {
        Statistic stat = new Statistic();
        stats.addStatistic("Hello", stat);
        Statistic stat2 = stats.getStatistic("Hello");
        assertThat(stat2, is(equalTo(stat)));
    }

    @Test
    public void addStatMergesThenGetStat() {
        Statistic stat1 = new Statistic();
        stat1.min = -2;
        stat1.max = 23;
        stat1.sum = -2390;
        stat1.numCalls = 3243980;
        Statistic stat2 = new Statistic();
        stat2.min = 32;
        stat2.max = -2190;
        stat2.sum = 329239020;
        stat2.numCalls = 299;

        stats.addStatistic("Hello", stat1);
        stats.addStatistic("Hello", stat2);
        Statistic result = stats.getStatistic("Hello");
        assertThat(result, is(equalTo(Statistic.merge(stat1, stat2))));
    }

    @Test
    public void addStatThenGetNonExistantStat() {
        Statistic stat = new Statistic();
        stats.addStatistic("Hello", stat);
        Statistic stat2 = stats.getStatistic("fjsal");
        assertThat(stat2, is(nullValue()));
    }

    @Test
    public void addStatEncapsulated() {
        Statistic stat = new Statistic();
        stats.addStatistic("Hello", stat);
        stat.min = 0;
        Statistic stat2 = stats.getStatistic("Hello");
        assertThat(stat2, is(not(nullValue())));
        assertThat(stat2, is(not(sameInstance(stat))));
        assertThat(stat2, is(not(equalTo(stat))));
    }

    @Test
    public void getStatEncapulated() {
        stats.addStatistic("Hello", new Statistic());
        Statistic stat1 = stats.getStatistic("Hello");
        Statistic stat2 = stats.getStatistic("Hello");
        assertThat(stat2, is(not(sameInstance(stat1))));
    }

    @Test
    public void getStatNamesEncapsulated() {
        Statistic stat = new Statistic();
        String name = "Hello";
        stats.addStatistic(name, stat);
        Collection<String> names1 = stats.getNames();
        Collection<String> names2 = stats.getNames();
        assertThat(names1, is(not(nullValue())));
        assertThat(names2, is(not(nullValue())));
        assertThat(names1, is(not(sameInstance(names2))));
    }

    @Test
    public void addTwoStatsThenGetStats() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatistic("Hello", helloStat);
        stats.addStatistic("Bye", byeStat);
        assertThat(stats.getStatistic("Hello"), is(equalTo(helloStat)));
        assertThat(stats.getStatistic("Bye"), is(equalTo(byeStat)));
    }

    @Test
    public void addTwoStatsThenGetStatNames() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatistic("Hello", new Statistic());
        stats.addStatistic("Bye", new Statistic());
        Collection<String> names = stats.getNames();
        assertThat(names.size(), is(equalTo(2)));
        assertThat(names, containsInAnyOrder("Hello", "Bye"));

    }

}
