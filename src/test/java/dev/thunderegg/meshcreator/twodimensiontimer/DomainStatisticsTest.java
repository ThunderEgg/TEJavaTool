package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.Collection;

public class DomainStatisticsTest {

    private DomainStatistics stats;

    @BeforeEach
    public void setup() {
        stats = new DomainStatistics();
    }

    @Test
    public void getDomainsEmpty() {
        Collection<Integer> domains = stats.getDomains();
        assertThat(domains, is(not(nullValue())));
        assertThat(domains.size(), is(equalTo(0)));
    }

    @Test
    public void getStatForDomainEmptyIsNull() {
        Statistic stat = stats.getStatisticForDomain(1, "Hello");
        assertThat(stat, is(nullValue()));
    }

    @Test
    public void addDomainStatThenGetNames() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(1, "Hello", stat);
        Collection<String> names = stats.getNames();
        assertThat(names, is(not(nullValue())));
        assertThat(names.size(), is(equalTo(1)));
        assertThat(names, contains("Hello"));
    }

    @Test
    public void addDomainStatThenGetNamesForDomain() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(1, "Hello", stat);
        Collection<String> names = stats.getNamesForDomain(1);
        assertThat(names, is(not(nullValue())));
        assertThat(names.size(), is(equalTo(1)));
        assertThat(names, contains("Hello"));
    }

    @Test
    public void addDomainStatThenGetStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(1, "Hello", stat);
        Statistic stat2 = stats.getStatistic("Hello");
        assertThat(stat2, is(equalTo(stat)));
    }

    @Test
    public void addDomainStatSameDomainMergesThenGetStat() {
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

        stats.addStatisticForDomain(1, "Hello", stat1);
        stats.addStatisticForDomain(1, "Hello", stat2);
        Statistic result = stats.getStatistic("Hello");
        assertThat(result, is(equalTo(Statistic.merge(stat1, stat2))));
    }

    @Test
    public void addDomainStatDifferentDomainMergesThenGetStat() {
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

        stats.addStatisticForDomain(1, "Hello", stat1);
        stats.addStatisticForDomain(2, "Hello", stat2);
        Statistic result = stats.getStatistic("Hello");
        assertThat(result, is(equalTo(Statistic.merge(stat1, stat2))));
    }

    @Test
    public void addDomainStatThenGetNonExistantStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(1, "Hello", stat);
        Statistic stat2 = stats.getStatistic("fjsal");
        assertThat(stat2, is(nullValue()));
    }

    @Test
    public void addDomainStatThenGetNamesForNonExistantDomain() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(1, "Hello", stat);
        Collection<String> names = stats.getNamesForDomain(2);
        assertThat(names, is(nullValue()));
    }

    @Test
    public void addDomainStatThenGetNonExistantDomainStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(1, "Hello", stat);
        Statistic stat2 = stats.getStatisticForDomain(2, "fjsal");
        assertThat(stat2, is(nullValue()));
    }

    @Test
    public void addDomainStatEncapsulated() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(1, "Hello", stat);
        stat.min = 0;
        Statistic stat2 = stats.getStatisticForDomain(1, "Hello");
        assertThat(stat2, is(not(nullValue())));
        assertThat(stat2, is(not(sameInstance(stat))));
        assertThat(stat2, is(not(equalTo(stat))));
    }

    @Test
    public void getDomainStatEncapulated() {
        stats.addStatisticForDomain(1, "Hello", new Statistic());
        Statistic stat1 = stats.getStatisticForDomain(1, "Hello");
        Statistic stat2 = stats.getStatisticForDomain(1, "Hello");
        assertThat(stat2, is(not(sameInstance(stat1))));
    }

    @Test
    public void getNamesForDomainEncapsulated() {
        stats.addStatisticForDomain(1, "Hello", new Statistic());
        Collection<String> names1 = stats.getNamesForDomain(1);
        Collection<String> names2 = stats.getNamesForDomain(1);
        assertThat(names1, is(not(sameInstance(names2))));
    }

    @Test
    public void getDomainsEncapsulated() {
        Statistic stat = new Statistic();
        String name = "Hello";
        stats.addStatisticForDomain(1, name, stat);
        Collection<Integer> domains1 = stats.getDomains();
        Collection<Integer> domains2 = stats.getDomains();
        assertThat(domains1, is(not(nullValue())));
        assertThat(domains2, is(not(nullValue())));
        assertThat(domains1, is(not(sameInstance(domains2))));
    }

    @Test
    public void addTwoDomainStatsThenGetStats() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatisticForDomain(1, "Hello", helloStat);
        stats.addStatisticForDomain(1, "Bye", byeStat);
        assertThat(stats.getStatistic("Hello"), is(equalTo(helloStat)));
        assertThat(stats.getStatistic("Bye"), is(equalTo(byeStat)));
    }

    @Test
    public void addTwoDomainStatsThenGetDomainStats() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatisticForDomain(1, "Hello", helloStat);
        stats.addStatisticForDomain(1, "Bye", byeStat);
        assertThat(stats.getStatisticForDomain(1, "Hello"), is(equalTo(helloStat)));
        assertThat(stats.getStatisticForDomain(1, "Bye"), is(equalTo(byeStat)));
    }

    @Test
    public void addTwoDomainStatsThenGetStatNames() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatisticForDomain(1, "Hello", new Statistic());
        stats.addStatisticForDomain(1, "Bye", new Statistic());
        Collection<String> names = stats.getNames();
        assertThat(names.size(), is(equalTo(2)));
        assertThat(names, containsInAnyOrder("Hello", "Bye"));

    }

    @Test
    public void addTwoDomainStatsThenGetStatDomains() {
        Statistic helloStat = new Statistic();
        helloStat.numCalls = 1;
        Statistic byeStat = new Statistic();
        byeStat.numCalls = 2;
        stats.addStatisticForDomain(1, "Hello", new Statistic());
        stats.addStatisticForDomain(2, "Hello", new Statistic());
        Collection<Integer> domains = stats.getDomains();
        assertThat(domains.size(), is(equalTo(2)));
        assertThat(domains, containsInAnyOrder(1, 2));

    }

}
