package dev.thunderegg.meshcreator.twodimensiontimer;

import java.util.ArrayList;

import dev.thunderegg.Timer;
import dev.thunderegg.Timing;
import javafx.scene.control.TreeItem;

public class TimerToStatisticsTree {

    public static TreeItem<PatchStatistics> convert(Timer timer) {
        TreeItem<PatchStatistics> root = new TreeItem<PatchStatistics>();
        addTimings(root, timer.timings);
        return root;
    }

    private static void addTimings(TreeItem<PatchStatistics> parent, ArrayList<Timing> timings) {

    }

}
