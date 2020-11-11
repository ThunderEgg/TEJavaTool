package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.Collection;

public class PatchStatisticsTest {

    private PatchStatistics stats;

    @BeforeEach
    public void setup() {
        stats = new PatchStatistics();
    }

    @Test
    public void getStatForPatchEmptyIsNull() {
        Statistic stat = stats.getStatisticForPatch("Hello", 1, 2);
        assertThat(stat, is(nullValue()));
    }

    @Test
    public void addPatchStatThenGetNames() {
        Statistic stat = new Statistic();
        stats.addStatisticForPatch("Hello", 1, 2, stat);
        Collection<String> names = stats.getNames();
        assertThat(names, is(not(nullValue())));
        assertThat(names.size(), is(equalTo(1)));
        assertThat(names, contains("Hello"));
    }

    @Test
    public void addPatchStatThenGetStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForPatch("Hello", 1, 2, stat);
        Statistic stat2 = stats.getStatistic("Hello");
        assertThat(stat2, is(equalTo(stat)));
    }

    @Test
    public void addPatchStatThenGetDomainStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForPatch("Hello", 1, 2, stat);
        Statistic stat2 = stats.getStatisticForDomain("Hello", 1);
        assertThat(stat2, is(equalTo(stat)));
    }

    @Test
    public void addPatchStatSameDomainSamePatchMergesThenGetStat() {
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

        stats.addStatisticForPatch("Hello", 1, 0, stat1);
        stats.addStatisticForPatch("Hello", 1, 0, stat2);
        Statistic result = stats.getStatistic("Hello");
        assertThat(result, is(equalTo(Statistic.merge(stat1, stat2))));
    }

    @Test
    public void addPatchStatSameDomainSamePatchMergesThenGetDomainStat() {
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

        stats.addStatisticForPatch("Hello", 1, 0, stat1);
        stats.addStatisticForPatch("Hello", 1, 0, stat2);
        Statistic result = stats.getStatisticForDomain("Hello", 1);
        assertThat(result, is(equalTo(Statistic.merge(stat1, stat2))));
    }

    @Test
    public void addPatchStatSameDomainSamePatchMergesThenGetPatchStat() {
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

        stats.addStatisticForPatch("Hello", 1, 0, stat1);
        stats.addStatisticForPatch("Hello", 1, 0, stat2);
        Statistic result = stats.getStatisticForPatch("Hello", 1, 0);
        assertThat(result, is(equalTo(Statistic.merge(stat1, stat2))));
    }

    @Test
    public void addPatchStatSameDomainDifferentPatchMergesThenGetStat() {
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

        stats.addStatisticForPatch("Hello", 1, 0, stat1);
        stats.addStatisticForPatch("Hello", 1, 1, stat2);
        Statistic result = stats.getStatistic("Hello");
        assertThat(result, is(equalTo(Statistic.merge(stat1, stat2))));
    }

    @Test
    public void addPatchStatDifferentDomainMergesThenGetStat() {
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

        stats.addStatisticForPatch("Hello", 1, 0, stat1);
        stats.addStatisticForPatch("Hello", 2, 0, stat2);
        Statistic result = stats.getStatistic("Hello");
        assertThat(result, is(equalTo(Statistic.merge(stat1, stat2))));
    }

    @Test
    public void addPatchStatThenGetNonExistantStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForPatch("Hello", 1, 0, stat);
        Statistic stat2 = stats.getStatistic("fjsal");
        assertThat(stat2, is(nullValue()));
    }

    @Test
    public void addPatchStatThenGetNonExistantDomainStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForPatch("Hello", 1, 0, stat);
        Statistic stat2 = stats.getStatisticForDomain("Hello", 2);
        assertThat(stat2, is(nullValue()));
    }

    @Test
    public void addPatchStatThenGetNonExistantPatchStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForPatch("Hello", 1, 0, stat);
        Statistic stat2 = stats.getStatisticForPatch("Hello", 1, 1);
        assertThat(stat2, is(nullValue()));
    }

    @Test
    public void addPatchStatEncapsulated() {
        Statistic stat = new Statistic();
        stats.addStatisticForPatch("Hello", 1, 0, stat);
        stat.min = 0;
        Statistic stat2 = stats.getStatisticForPatch("Hello", 1, 0);
        assertThat(stat2, is(not(nullValue())));
        assertThat(stat2, is(not(sameInstance(stat))));
        assertThat(stat2, is(not(equalTo(stat))));
    }

    @Test
    public void getPatchStatEncapulated() {
        stats.addStatisticForPatch("Hello", 1, 0, new Statistic());
        Statistic stat1 = stats.getStatisticForPatch("Hello", 1, 0);
        Statistic stat2 = stats.getStatisticForPatch("Hello", 1, 0);
        assertThat(stat2, is(not(sameInstance(stat1))));
    }

    @Test
    public void addTwoPatchStatsThenGetStats() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatisticForPatch("Hello", 1, 0, helloStat);
        stats.addStatisticForPatch("Bye", 1, 0, byeStat);
        assertThat(stats.getStatistic("Hello"), is(equalTo(helloStat)));
        assertThat(stats.getStatistic("Bye"), is(equalTo(byeStat)));
    }

    @Test
    public void addTwoPatchStatsThenGetDomainStats() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatisticForPatch("Hello", 1, 0, helloStat);
        stats.addStatisticForPatch("Bye", 1, 0, byeStat);
        assertThat(stats.getStatisticForDomain("Hello", 1), is(equalTo(helloStat)));
        assertThat(stats.getStatisticForDomain("Bye", 1), is(equalTo(byeStat)));
    }

    @Test
    public void addTwoPatchStatsThenGetPatchStats() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatisticForPatch("Hello", 1, 0, helloStat);
        stats.addStatisticForPatch("Bye", 1, 0, byeStat);
        assertThat(stats.getStatisticForPatch("Hello", 1, 0), is(equalTo(helloStat)));
        assertThat(stats.getStatisticForPatch("Bye", 1, 0), is(equalTo(byeStat)));
    }

    @Test
    public void addTwoPatchStatsThenGetStatNames() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatisticForPatch("Hello", 1, 0, new Statistic());
        stats.addStatisticForPatch("Bye", 1, 0, new Statistic());
        Collection<String> names = stats.getNames();
        assertThat(names.size(), is(equalTo(2)));
        assertThat(names, containsInAnyOrder("Hello", "Bye"));

    }

    @Test
    public void addTwoPatchStatsThenGetStatDomains() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatisticForPatch("Hello", 1, 0, new Statistic());
        stats.addStatisticForPatch("Hello", 2, 0, new Statistic());
        Collection<Integer> domains = stats.getDomainsForName("Hello");
        assertThat(domains.size(), is(equalTo(2)));
        assertThat(domains, containsInAnyOrder(1, 2));

    }

}
