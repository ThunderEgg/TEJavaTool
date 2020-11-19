package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import dev.thunderegg.Info;
import dev.thunderegg.Timer;
import dev.thunderegg.Timing;
import javafx.scene.control.TreeItem;

public class Statistics extends PatchStatistics {

    ArrayList<Statistics> childStats = new ArrayList<>();

    private Statistics(String name) {
        super(name);
    }

    public Statistics(Timer timer) {
        super("");
        if (timer.timings != null) {
            addChildStats(timer.timings);
        }
    }

    private void addChildStats(Collection<Timing> timings) {
        HashMap<String, Statistics> nameToStat = new HashMap<>();
        HashMap<String, LinkedList<Timing>> nameToChildren = new HashMap<>();
        for (Timing timing : timings) {
            if (!nameToStat.containsKey(timing.name)) {
                nameToStat.put(timing.name, new Statistics(timing.name));
                nameToChildren.put(timing.name, new LinkedList<>());
            }
            nameToStat.get(timing.name).addStatsFromTiming(timing);
            if (timing.timings != null) {
                nameToChildren.get(timing.name).addAll(timing.timings);
            }
        }
        childStats = new ArrayList<>(nameToStat.values());
        for (Statistics stats : childStats) {
            stats.addChildStats(nameToChildren.get(stats.getName()));
        }
    }

    private void addStatsFromTiming(Timing timing) {
        Statistic timeStat = new Statistic(timing);
        addStatisticUsingTimingAsKey("Time", timing, timeStat);
        if (timing.infos != null) {
            for (Info info : timing.infos) {
                Statistic infoStat = new Statistic(info);
                addStatisticUsingTimingAsKey(info.name, timing, infoStat);
            }
        }
    }

    private void addStatisticUsingTimingAsKey(String name, Timing timing, Statistic stat) {
        if (timing.patch_id != null) {
            addStatisticForPatch(new PatchKey(name, timing.domain_id, timing.patch_id), stat);
        } else if (timing.domain_id != null) {
            addStatisticForDomain(new DomainKey(name, timing.domain_id), stat);
        } else {
            addStatistic(new UnassociatedKey(name), stat);
        }
    }

    public ArrayList<Statistics> getChildren() {
        return childStats;
    }

    public TreeItem<Statistics> getTree() {
        TreeItem<Statistics> item = new TreeItem<>(this);
        for (Statistics child : childStats) {
            item.getChildren().add(child.getTree());
        }
        return item;
    }

}
