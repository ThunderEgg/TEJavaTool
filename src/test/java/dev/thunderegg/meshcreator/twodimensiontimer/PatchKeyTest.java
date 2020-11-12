package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PatchKeyTest {
    @Test
    public void constructor() {
        PatchKey key = new PatchKey("a", 1, 2);
        assertThat(key.name, is(equalTo("a")));
        assertThat(key.domainId, is(equalTo(1)));
        assertThat(key.patchId, is(equalTo(2)));
    }

    @Test
    public void equalsIsEqual() {
        PatchKey key1 = new PatchKey("a", 1, 2);
        PatchKey key2 = new PatchKey("a", 1, 2);
        assertThat(key1, is(equalTo(key2)));
    }

    @Test
    public void equalsIsNotEqual() {
        PatchKey key1 = new PatchKey("a", 1, 0);
        PatchKey key2 = new PatchKey("b", 1, 0);
        assertThat(key1, is(not(equalTo(key2))));
        key1 = new PatchKey("a", 1, 0);
        key2 = new PatchKey("a", 2, 0);
        assertThat(key1, is(not(equalTo(key2))));
        key1 = new PatchKey("a", 1, 0);
        key2 = new PatchKey("a", 1, 1);
        assertThat(key1, is(not(equalTo(key2))));
    }

    @Test
    public void equalsIsNotEqualDifferentObject() {
        PatchKey key1 = new PatchKey("a", 1, 0);
        Integer key2 = Integer.valueOf(2);
        assertThat(key1, is(not(equalTo(key2))));
    }

    @Test
    public void equalsIsNotEqualNull() {
        PatchKey key1 = new PatchKey("a", 1, 0);
        Integer key2 = null;
        assertThat(key1, is(not(equalTo(key2))));
    }

    @Test
    public void compareToEquals() {
        PatchKey key1 = new PatchKey("a", 1, 0);
        PatchKey key2 = new PatchKey("a", 1, 0);
        assertThat(PatchKey.compare(key1, key2), is(equalTo(0)));
    }

    @Test
    public void compareToLessThan() {
        PatchKey key1 = new PatchKey("a", 1, 0);
        PatchKey key2 = new PatchKey("b", 1, 0);
        assertThat(PatchKey.compare(key1, key2), is(lessThan(0)));
        key1 = new PatchKey("a", 1, 0);
        key2 = new PatchKey("a", 2, 0);
        assertThat(PatchKey.compare(key1, key2), is(lessThan(0)));
        key1 = new PatchKey("a", 1, 0);
        key2 = new PatchKey("a", 1, 1);
        assertThat(PatchKey.compare(key1, key2), is(lessThan(0)));
    }

    @Test
    public void compareToGreaterThan() {
        PatchKey key1 = new PatchKey("b", 1, 0);
        PatchKey key2 = new PatchKey("a", 1, 0);
        assertThat(PatchKey.compare(key1, key2), is(greaterThan(0)));
        key1 = new PatchKey("a", 2, 0);
        key2 = new PatchKey("a", 1, 0);
        assertThat(PatchKey.compare(key1, key2), is(greaterThan(0)));
        key1 = new PatchKey("a", 1, 1);
        key2 = new PatchKey("a", 1, 0);
        assertThat(PatchKey.compare(key1, key2), is(greaterThan(0)));
    }
}
