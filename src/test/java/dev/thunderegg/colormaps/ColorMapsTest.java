package dev.thunderegg.colormaps;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestReporter;

import javafx.scene.paint.Color;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("serial")
public class ColorMapsTest {

    private static Map<String, Double[][]> mplValues;
    private static Map<String, ColorMap> colormaps;

    @BeforeAll
    public static void read() {
        Gson gson = (new GsonBuilder()).create();
        InputStreamReader is = new InputStreamReader(
                ColorMapsTest.class.getClassLoader().getResourceAsStream("matplotlibcolormaps.json"));
        mplValues = gson.fromJson(is, new TypeToken<Map<String, Double[][]>>() {
        }.getType());
        colormaps = new HashMap<>();
        for (ColorMap cmap : ColorMaps.getColorMaps()) {
            colormaps.put(cmap.getName(), cmap);
        }
    }

    private final double delta = 1.0 / 255 / 2;

    @TestFactory
    Collection<DynamicTest> colorMapValues() {
        LinkedList<DynamicTest> tests = new LinkedList<>();
        for (String name : mplValues.keySet()) {
            tests.add(DynamicTest.dynamicTest(name, () -> {
                try {
                    ColorMap cmap = colormaps.get(name);
                    Double[][] values = mplValues.get(name);
                    for (int i = 0; i < values.length; i++) {
                        double x = ((double) i) / ((double) values.length - 1.0);
                        Color color = cmap.getColor(x);
                        Double[] value = values[i];
                        try {
                            assertThat(color.getRed(), is(closeTo(value[0], delta)));
                        } catch (AssertionError e) {
                            throw new AssertionError(
                                    "\nRed value on index " + i + "(" + x + ") of " + name + ":" + e.getMessage());
                        }
                        try {
                            assertThat(color.getGreen(), is(closeTo(value[1], delta)));
                        } catch (AssertionError e) {
                            throw new AssertionError(
                                    "\nGreen value on index " + i + "(" + x + "): of " + name + "" + e.getMessage());
                        }
                        try {
                            assertThat(color.getBlue(), is(closeTo(value[2], delta)));
                        } catch (AssertionError e) {
                            throw new AssertionError(
                                    "\nBlue value on index " + i + "(" + x + "): of " + name + "" + e.getMessage());
                        }
                    }
                } catch (AssertionError e) {
                    throw e;
                } catch (Throwable e) {
                    throw new Throwable(name + ": " + e.getMessage());
                }
            }));
        }
        return tests;
    }

}