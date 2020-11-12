package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UnassociatedKeyTest {
    @Test
    public void constructor() {
        UnassociatedKey key = new UnassociatedKey("a");
        assertThat(key.name, is(equalTo("a")));
    }

    @Test
    public void equalsIsEqual() {
        UnassociatedKey key1 = new UnassociatedKey("a");
        UnassociatedKey key2 = new UnassociatedKey("a");
        assertThat(key1, is(equalTo(key2)));
    }

    @Test
    public void equalsIsNotEqual() {
        UnassociatedKey key1 = new UnassociatedKey("a");
        UnassociatedKey key2 = new UnassociatedKey("b");
        assertThat(key1, is(not(equalTo(key2))));
    }

    @Test
    public void equalsIsNotEqualDifferentObject() {
        UnassociatedKey key1 = new UnassociatedKey("a");
        Integer key2 = Integer.valueOf(2);
        assertThat(key1, is(not(equalTo(key2))));
    }

    @Test
    public void equalsIsNotEqualNull() {
        UnassociatedKey key1 = new UnassociatedKey("a");
        Integer key2 = null;
        assertThat(key1, is(not(equalTo(key2))));
    }

    @Test
    public void compareToEquals() {
        UnassociatedKey key1 = new UnassociatedKey("a");
        UnassociatedKey key2 = new UnassociatedKey("a");
        assertThat(UnassociatedKey.compare(key1, key2), is(equalTo(0)));
    }

    @Test
    public void compareToLessThan() {
        UnassociatedKey key1 = new UnassociatedKey("a");
        UnassociatedKey key2 = new UnassociatedKey("b");
        assertThat(UnassociatedKey.compare(key1, key2), is(lessThan(0)));
    }

    @Test
    public void compareToGreaterThan() {
        UnassociatedKey key1 = new UnassociatedKey("b");
        UnassociatedKey key2 = new UnassociatedKey("a");
        assertThat(UnassociatedKey.compare(key1, key2), is(greaterThan(0)));
    }
}
