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
        stats = new DomainStatistics("Test");
    }

    @Test
    public void getNameEmpty() {
        assertThat(stats.getName(), is("Test"));
    }

    @Test
    public void toStringEmpty() {
        assertThat(stats.toString(), is("Test"));
    }

    @Test
    public void getDomainsEmpty() {
        Collection<Integer> domains = stats.getDomainsForName("hello");
        assertThat(domains.size(), is(0));
    }

    @Test
    public void getStatForDomainEmptyIsNull() {
        Statistic stat = stats.getStatisticForDomain(new DomainKey("Hello", 1));
        assertThat(stat, is(nullValue()));
    }

    @Test
    public void addDomainStatThenGetNames() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(new DomainKey("Hello", 1), stat);
        Collection<String> names = stats.getStatisticNames();
        assertThat(names, is(not(nullValue())));
        assertThat(names.size(), is(equalTo(1)));
        assertThat(names, contains("Hello"));
    }

    @Test
    public void addDomainStatThenGetStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(new DomainKey("Hello", 1), stat);
        Statistic stat2 = stats.getStatistic(new UnassociatedKey("Hello"));
        assertThat(stat2, is(equalTo(stat)));
    }

    @Test
    public void addDomainStatSameDomainMergesThenGetStat() {
        Statistic stat1 = new Statistic();
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(-2390);
        stat1.setNumCalls(3243980);
        Statistic stat2 = new Statistic();
        stat2.setMin(32);
        stat2.setMax(-2190);
        stat2.setSum(329239020);
        stat2.setNumCalls(299);

        stats.addStatisticForDomain(new DomainKey("Hello", 1), stat1);
        stats.addStatisticForDomain(new DomainKey("Hello", 1), stat2);
        Statistic result = stats.getStatistic(new UnassociatedKey("Hello"));
        assertThat(result, is(equalTo(Statistic.merge(stat1, stat2))));
    }

    @Test
    public void addDomainStatDifferentDomainMergesThenGetStat() {
        Statistic stat1 = new Statistic();
        stat1.setMin(-2);
        stat1.setMax(23);
        stat1.setSum(-2390);
        stat1.setNumCalls(3243980);
        Statistic stat2 = new Statistic();
        stat2.setMin(32);
        stat2.setMax(-2190);
        stat2.setSum(329239020);
        stat2.setNumCalls(299);

        stats.addStatisticForDomain(new DomainKey("Hello", 1), stat1);
        stats.addStatisticForDomain(new DomainKey("Hello", 2), stat2);
        Statistic result = stats.getStatistic(new UnassociatedKey("Hello"));
        assertThat(result, is(equalTo(Statistic.merge(stat1, stat2))));
    }

    @Test
    public void addDomainStatThenGetNonExistantStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(new DomainKey("Hello", 1), stat);
        Statistic stat2 = stats.getStatistic(new UnassociatedKey("fjsal"));
        assertThat(stat2, is(nullValue()));
    }

    @Test
    public void addDomainStatThenGetNonExistantDomainStat() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(new DomainKey("Hello", 1), stat);
        Statistic stat2 = stats.getStatisticForDomain(new DomainKey("fjsal", 1));
        assertThat(stat2, is(nullValue()));
    }

    @Test
    public void addDomainStatEncapsulated() {
        Statistic stat = new Statistic();
        stats.addStatisticForDomain(new DomainKey("Hello", 1), stat);
        stat.setMin(0);
        Statistic stat2 = stats.getStatisticForDomain(new DomainKey("Hello", 1));
        assertThat(stat2, is(not(nullValue())));
        assertThat(stat2, is(not(sameInstance(stat))));
        assertThat(stat2, is(not(equalTo(stat))));
    }

    @Test
    public void getDomainStatEncapulated() {
        stats.addStatisticForDomain(new DomainKey("Hello", 1), new Statistic());
        Statistic stat1 = stats.getStatisticForDomain(new DomainKey("Hello", 1));
        Statistic stat2 = stats.getStatisticForDomain(new DomainKey("Hello", 1));
        assertThat(stat2, is(not(sameInstance(stat1))));
    }

    @Test
    public void getDomainsEncapsulated() {
        Statistic stat = new Statistic();
        String name = "Hello";
        stats.addStatisticForDomain(new DomainKey(name, 1), stat);
        Collection<Integer> domains1 = stats.getDomainsForName("Hello");
        Collection<Integer> domains2 = stats.getDomainsForName("Hello");
        assertThat(domains1, is(not(nullValue())));
        assertThat(domains2, is(not(nullValue())));
        assertThat(domains1, is(not(sameInstance(domains2))));
    }

    @Test
    public void addTwoDomainStatsThenGetStats() {
        Statistic helloStat = new Statistic();
        helloStat.setNumCalls(1);
        Statistic byeStat = new Statistic();
        byeStat.setNumCalls(2);
        stats.addStatisticForDomain(new DomainKey("Hello", 1), helloStat);
        stats.addStatisticForDomain(new DomainKey("Bye", 1), byeStat);
        assertThat(stats.getStatistic(new UnassociatedKey("Hello")), is(equalTo(helloStat)));
        assertThat(stats.getStatistic(new UnassociatedKey("Bye")), is(equalTo(byeStat)));
    }

    @Test
    public void addTwoDomainStatsThenGetDomainStats() {
        Statistic helloStat = new Statistic();
        helloStat.setNumCalls(1);
        Statistic byeStat = new Statistic();
        byeStat.setNumCalls(2);
        stats.addStatisticForDomain(new DomainKey("Hello", 1), helloStat);
        stats.addStatisticForDomain(new DomainKey("Bye", 1), byeStat);
        assertThat(stats.getStatisticForDomain(new DomainKey("Hello", 1)), is(equalTo(helloStat)));
        assertThat(stats.getStatisticForDomain(new DomainKey("Bye", 1)), is(equalTo(byeStat)));
    }

    @Test
    public void addTwoDomainStatsThenGetStatNames() {
        Statistic helloStat = new Statistic();
        helloStat.setNumCalls(1);
        Statistic byeStat = new Statistic();
        byeStat.setNumCalls(2);
        stats.addStatisticForDomain(new DomainKey("Hello", 1), new Statistic());
        stats.addStatisticForDomain(new DomainKey("Bye", 1), new Statistic());
        Collection<String> names = stats.getStatisticNames();
        assertThat(names.size(), is(equalTo(2)));
        assertThat(names, containsInAnyOrder("Hello", "Bye"));

    }

    @Test
    public void addTwoDomainStatsThenGetStatDomains() {
        Statistic helloStat = new Statistic();
        helloStat.setNumCalls(1);
        Statistic byeStat = new Statistic();
        byeStat.setNumCalls(2);
        stats.addStatisticForDomain(new DomainKey("Hello", 1), new Statistic());
        stats.addStatisticForDomain(new DomainKey("Hello", 2), new Statistic());
        Collection<Integer> domains = stats.getDomainsForName("Hello");
        assertThat(domains.size(), is(equalTo(2)));
        assertThat(domains, containsInAnyOrder(1, 2));

    }

}
