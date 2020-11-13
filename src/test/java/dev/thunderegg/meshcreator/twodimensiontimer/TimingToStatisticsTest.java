package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.Test;

import dev.thunderegg.Info;
import dev.thunderegg.Timing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TimingToStatisticsTest {

    @Test
    public void convertUnassociatedTimingWithoutInfo() {
        Timing timing = new Timing();
        timing.domain_id = Integer.MAX_VALUE;
        timing.patch_id = Integer.MAX_VALUE;
        timing.name = "Test";
        timing.min = 4;
        timing.max = 3;
        timing.sum = 20;
        timing.num_calls = 3;
        UnassociatedStatistics stats = TimingToStatstics.convert(timing);
        assertThat(stats.getName(), is("Test"));
        assertThat(stats.getStatisticNames().size(), is(1));
        assertThat(stats.getStatisticNames(), contains("Time"));
        Statistic stat = stats.getStatistic(new UnassociatedKey("Time"));
        assertThat(stat.min, is(timing.min));
        assertThat(stat.max, is(timing.max));
        assertThat(stat.sum, is(timing.sum));
        assertThat(stat.numCalls, is(timing.num_calls));
    }

    @Test
    public void convertUnassociatedTimingWithInfo() {
        Timing timing = new Timing();
        timing.domain_id = Integer.MAX_VALUE;
        timing.patch_id = Integer.MAX_VALUE;
        timing.name = "Test";
        timing.min = 4;
        timing.max = 3;
        timing.sum = 20;
        timing.num_calls = 3;
        Info info = new Info();
        info.name = "Calls";
        info.min = 2;
        info.max = 29;
        info.sum = 1020;
        info.num_calls = 3;
        timing.infos.add(info);
        UnassociatedStatistics stats = TimingToStatstics.convert(timing);
        assertThat(stats.getName(), is("Test"));
        assertThat(stats.getStatisticNames().size(), is(2));
        assertThat(stats.getStatisticNames(), contains("Time", "Calls"));
        Statistic stat = stats.getStatistic(new UnassociatedKey("Time"));
        assertThat(stat.min, is(timing.min));
        assertThat(stat.max, is(timing.max));
        assertThat(stat.sum, is(timing.sum));
        assertThat(stat.numCalls, is(timing.num_calls));
        stat = stats.getStatistic(new UnassociatedKey("Calls"));
        assertThat(stat.min, is(info.min));
        assertThat(stat.max, is(info.max));
        assertThat(stat.sum, is(info.sum));
        assertThat(stat.numCalls, is(info.num_calls));
    }

    @Test
    public void convertDomainTimingWithoutInfo() {
        Timing timing = new Timing();
        timing.domain_id = 1;
        timing.patch_id = Integer.MAX_VALUE;
        timing.name = "Test";
        timing.min = 4;
        timing.max = 3;
        timing.sum = 20;
        timing.num_calls = 3;
        UnassociatedStatistics stats = TimingToStatstics.convert(timing);
        assertThat(stats.getName(), is("Test"));
        assertThat(stats.getStatisticNames().size(), is(1));
        assertThat(stats.getStatisticNames(), contains("Time"));
        Statistic stat = stats.getStatistic(new UnassociatedKey("Time"));
        assertThat(stat.min, is(timing.min));
        assertThat(stat.max, is(timing.max));
        assertThat(stat.sum, is(timing.sum));
        assertThat(stat.numCalls, is(timing.num_calls));
    }

    @Test
    public void convertDomainTimingWithInfo() {
        Timing timing = new Timing();
        timing.domain_id = 1;
        timing.patch_id = Integer.MAX_VALUE;
        timing.name = "Test";
        timing.min = 4;
        timing.max = 3;
        timing.sum = 20;
        timing.num_calls = 3;
        Info info = new Info();
        info.name = "Calls";
        info.min = 2;
        info.max = 29;
        info.sum = 1020;
        info.num_calls = 3;
        timing.infos.add(info);
        UnassociatedStatistics stats = TimingToStatstics.convert(timing);
        assertThat(stats.getName(), is("Test"));
        assertThat(stats.getStatisticNames().size(), is(2));
        assertThat(stats.getStatisticNames(), contains("Time", "Calls"));
        Statistic stat = stats.getStatistic(new UnassociatedKey("Time"));
        assertThat(stat.min, is(timing.min));
        assertThat(stat.max, is(timing.max));
        assertThat(stat.sum, is(timing.sum));
        assertThat(stat.numCalls, is(timing.num_calls));
        stat = stats.getStatistic(new UnassociatedKey("Calls"));
        assertThat(stat.min, is(info.min));
        assertThat(stat.max, is(info.max));
        assertThat(stat.sum, is(info.sum));
        assertThat(stat.numCalls, is(info.num_calls));
    }

}
