package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.Test;

import dev.thunderegg.Info;
import dev.thunderegg.Timer;
import dev.thunderegg.Timing;
import javafx.scene.control.TreeItem;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

public class StatisticsTest {

    @Test
    public void emptyTimer() {
        Timer timer = new Timer();
        Statistics stats = new Statistics(timer);
        assertThat(stats.getName(), is(""));
        assertThat(stats.getChildren().size(), is(0));
        assertThat(stats.getStatisticNames().size(), is(0));

        // CHECK TREE
        TreeItem<Statistics> root = stats.getTree();
        assertThat(root.getValue(), is(sameInstance(stats)));
        assertThat(root.getChildren().size(), is(0));
    }

    @Test
    public void timerWithOneUnassociatedChild() {
        Timer timer = new Timer();
        timer.timings = new ArrayList<Timing>();
        Timing child = new Timing();
        child.name = "Child";
        child.max = 10;
        timer.timings.add(child);

        Statistics stats = new Statistics(timer);
        assertThat(stats.getName(), is(""));
        assertThat(stats.getChildren().size(), is(1));
        Statistics childStats = stats.getChildren().get(0);
        assertThat(childStats.getName(), is("Child"));
        assertThat(childStats.getStatisticNames().size(), is(1));
        assertThat(childStats.getStatisticNames(), contains("Time"));
        assertThat(childStats.getStatistic(new UnassociatedKey("Time")).max, is(10.0));

        // CHECK TREE
        TreeItem<Statistics> root = stats.getTree();
        assertThat(root.getValue(), is(sameInstance(stats)));
        assertThat(root.getChildren().size(), is(1));
        assertThat(root.getChildren().get(0).getValue(), is(sameInstance(childStats)));
        assertThat(root.getChildren().get(0).getChildren().size(), is(0));
    }

    @Test
    public void timerWithOneUnassociatedChildWithInfo() {
        Timer timer = new Timer();
        timer.timings = new ArrayList<Timing>();
        Timing child = new Timing();
        child.name = "Child";
        child.max = 10;
        Info info = new Info();
        info.max = 1;
        info.name = "Info";
        child.infos = new ArrayList<>();
        child.infos.add(info);
        timer.timings.add(child);

        Statistics stats = new Statistics(timer);
        assertThat(stats.getName(), is(""));
        assertThat(stats.getChildren().size(), is(1));
        Statistics childStats = stats.getChildren().get(0);
        assertThat(childStats.getName(), is("Child"));
        assertThat(childStats.getStatisticNames().size(), is(2));
        assertThat(childStats.getStatisticNames(), contains("Info", "Time"));
        assertThat(childStats.getStatistic(new UnassociatedKey("Info")).max, is(1.0));
        assertThat(childStats.getStatistic(new UnassociatedKey("Time")).max, is(10.0));
    }

    @Test
    public void timerWithOneDomainChild() {
        Timer timer = new Timer();
        timer.timings = new ArrayList<Timing>();
        Timing child = new Timing();
        child.name = "Child";
        child.domain_id = 1;
        child.max = 10;
        timer.timings.add(child);

        Statistics stats = new Statistics(timer);
        assertThat(stats.getName(), is(""));
        assertThat(stats.getChildren().size(), is(1));
        Statistics childStats = stats.getChildren().get(0);
        assertThat(childStats.getName(), is("Child"));
        assertThat(childStats.getDomainsForName("Time"), contains(1));
        assertThat(childStats.getStatisticNames().size(), is(1));
        assertThat(childStats.getStatisticNames(), contains("Time"));
        assertThat(childStats.getStatistic(new UnassociatedKey("Time")).max, is(10.0));
    }

    @Test
    public void timerWithTwoDomainChild() {
        Timer timer = new Timer();
        timer.timings = new ArrayList<Timing>();
        Timing child = new Timing();
        child.name = "Child";
        child.domain_id = 1;
        child.sum = 10;
        timer.timings.add(child);
        Timing child2 = new Timing();
        child2.name = "Child";
        child2.domain_id = 2;
        child2.sum = 11;
        timer.timings.add(child2);

        Statistics stats = new Statistics(timer);
        assertThat(stats.getName(), is(""));
        assertThat(stats.getChildren().size(), is(1));
        Statistics childStats = stats.getChildren().get(0);
        assertThat(childStats.getName(), is("Child"));
        assertThat(childStats.getDomainsForName("Time"), contains(1, 2));
        assertThat(childStats.getStatisticNames().size(), is(1));
        assertThat(childStats.getStatisticNames(), contains("Time"));
        assertThat(childStats.getStatistic(new UnassociatedKey("Time")).sum, is(21.0));
    }

    @Test
    public void timerWithOnePatchChild() {
        Timer timer = new Timer();
        timer.timings = new ArrayList<Timing>();
        Timing child = new Timing();
        child.name = "Child";
        child.domain_id = 1;
        child.patch_id = 0;
        child.max = 10;
        timer.timings.add(child);

        Statistics stats = new Statistics(timer);
        assertThat(stats.getName(), is(""));
        assertThat(stats.getChildren().size(), is(1));
        Statistics childStats = stats.getChildren().get(0);
        assertThat(childStats.getName(), is("Child"));
        assertThat(childStats.getDomainsForName("Time"), contains(1));
        assertThat(childStats.getStatisticNames().size(), is(1));
        assertThat(childStats.getStatisticNames(), contains("Time"));
        assertThat(childStats.getStatistic(new UnassociatedKey("Time")).max, is(10.0));
    }

    @Test
    public void timerWithTwoPatchChild() {
        Timer timer = new Timer();
        timer.timings = new ArrayList<Timing>();
        Timing child = new Timing();
        child.name = "Child";
        child.domain_id = 1;
        child.patch_id = 0;
        child.sum = 10;
        timer.timings.add(child);
        Timing child2 = new Timing();
        child2.name = "Child";
        child2.domain_id = 1;
        child2.patch_id = 1;
        child2.sum = 11;
        timer.timings.add(child2);

        Statistics stats = new Statistics(timer);
        assertThat(stats.getName(), is(""));
        assertThat(stats.getChildren().size(), is(1));
        Statistics childStats = stats.getChildren().get(0);
        assertThat(childStats.getName(), is("Child"));
        assertThat(childStats.getDomainsForName("Time"), contains(1));
        assertThat(childStats.getStatisticNames().size(), is(1));
        assertThat(childStats.getStatisticNames(), contains("Time"));
        assertThat(childStats.getStatistic(new UnassociatedKey("Time")).sum, is(21.0));
        assertThat(childStats.getStatisticForPatch(new PatchKey("Time", 1, 0)), is(new Statistic(child)));
        assertThat(childStats.getStatisticForPatch(new PatchKey("Time", 1, 1)), is(new Statistic(child2)));
    }

    @Test
    public void timerWithOneUnassociatedChildWithChild() {
        Timer timer = new Timer();
        timer.timings = new ArrayList<Timing>();
        Timing child = new Timing();
        child.name = "Child";
        child.max = 10;
        timer.timings.add(child);
        Timing childChild = new Timing();
        childChild.name = "ChildChild";
        childChild.max = 11;
        child.timings = new ArrayList<>();
        child.timings.add(childChild);

        Statistics stats = new Statistics(timer);
        assertThat(stats.getName(), is(""));
        assertThat(stats.getChildren().size(), is(1));
        Statistics childStats = stats.getChildren().get(0);
        assertThat(childStats.getName(), is("Child"));
        assertThat(childStats.getStatisticNames().size(), is(1));
        assertThat(childStats.getStatisticNames(), contains("Time"));
        assertThat(childStats.getStatistic(new UnassociatedKey("Time")).max, is(10.0));
        assertThat(childStats.getChildren().size(), is(1));
        Statistics childChildStats = childStats.getChildren().get(0);
        assertThat(childChildStats.getName(), is("ChildChild"));
        assertThat(childChildStats.getStatisticNames().size(), is(1));
        assertThat(childChildStats.getStatisticNames(), contains("Time"));
        assertThat(childChildStats.getStatistic(new UnassociatedKey("Time")).max, is(11.0));
    }

    @Test
    public void timerWithTwoPatchChildWithChildren() {
        Timer timer = new Timer();
        timer.timings = new ArrayList<Timing>();
        Timing child = new Timing();
        child.name = "Child";
        child.domain_id = 1;
        child.patch_id = 0;
        child.sum = 10;
        timer.timings.add(child);
        Timing childChild = new Timing();
        childChild.name = "ChildChild";
        childChild.sum = 11;
        child.timings = new ArrayList<>();
        child.timings.add(childChild);
        Timing child2 = new Timing();
        child2.name = "Child";
        child2.domain_id = 1;
        child2.patch_id = 1;
        child2.sum = 11;
        timer.timings.add(child2);
        Timing childChild2 = new Timing();
        childChild2.name = "ChildChild";
        childChild2.sum = 20;
        child2.timings = new ArrayList<>();
        child2.timings.add(childChild2);

        Statistics stats = new Statistics(timer);
        assertThat(stats.getName(), is(""));
        assertThat(stats.getChildren().size(), is(1));
        Statistics childStats = stats.getChildren().get(0);
        assertThat(childStats.getName(), is("Child"));
        assertThat(childStats.getDomainsForName("Time"), contains(1));
        assertThat(childStats.getStatisticNames().size(), is(1));
        assertThat(childStats.getStatisticNames(), contains("Time"));
        assertThat(childStats.getStatistic(new UnassociatedKey("Time")).sum, is(21.0));
        assertThat(childStats.getStatisticForPatch(new PatchKey("Time", 1, 0)), is(new Statistic(child)));
        assertThat(childStats.getStatisticForPatch(new PatchKey("Time", 1, 1)), is(new Statistic(child2)));

        Statistics childChildStats = childStats.getChildren().get(0);
        assertThat(childChildStats.getName(), is("ChildChild"));
        assertThat(childChildStats.getStatisticNames().size(), is(1));
        assertThat(childChildStats.getStatisticNames(), contains("Time"));
        assertThat(childChildStats.getStatistic(new UnassociatedKey("Time")).sum, is(31.0));
    }
}
