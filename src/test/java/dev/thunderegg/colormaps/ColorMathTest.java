package dev.thunderegg.colormaps;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ColorMathTest {

    @Test
    public void clampBetween() {
        assertThat(ColorMath.clamp(0.5), is(0.5));
    }

    @Test
    public void clampBelow() {
        assertThat(ColorMath.clamp(-0.5), is(0.0));
    }

    @Test
    public void clampAbove() {
        assertThat(ColorMath.clamp(1.5), is(1.0));
    }

}
