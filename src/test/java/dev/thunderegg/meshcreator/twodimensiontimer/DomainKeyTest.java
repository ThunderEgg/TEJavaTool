package dev.thunderegg.meshcreator.twodimensiontimer;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DomainKeyTest {
    @Test
    public void constructor() {
        DomainKey key = new DomainKey("a", 1);
        assertThat(key.name, is(equalTo("a")));
        assertThat(key.domainId, is(equalTo(1)));
    }

    @Test
    public void equalsIsEqual() {
        DomainKey key1 = new DomainKey("a", 1);
        DomainKey key2 = new DomainKey("a", 1);
        assertThat(key1, is(equalTo(key2)));
    }

    @Test
    public void equalsIsNotEqual() {
        DomainKey key1 = new DomainKey("a", 1);
        DomainKey key2 = new DomainKey("b", 1);
        assertThat(key1, is(not(equalTo(key2))));
        key1 = new DomainKey("a", 1);
        key2 = new DomainKey("a", 2);
        assertThat(key1, is(not(equalTo(key2))));
        key1 = new DomainKey("a", 1);
        key2 = new DomainKey("b", 2);
        assertThat(key1, is(not(equalTo(key2))));
    }

    @Test
    public void equalsIsNotEqualDifferentObject() {
        DomainKey key1 = new DomainKey("a", 1);
        Integer key2 = Integer.valueOf(2);
        assertThat(key1, is(not(equalTo(key2))));
    }

    @Test
    public void equalsIsNotEqualNull() {
        DomainKey key1 = new DomainKey("a", 1);
        Integer key2 = null;
        assertThat(key1, is(not(equalTo(key2))));
    }

    @Test
    public void compareToEquals() {
        DomainKey key1 = new DomainKey("a", 1);
        DomainKey key2 = new DomainKey("a", 1);
        assertThat(DomainKey.compare(key1, key2), is(equalTo(0)));
    }

    @Test
    public void compareToLessThan() {
        DomainKey key1 = new DomainKey("a", 1);
        DomainKey key2 = new DomainKey("b", 1);
        assertThat(DomainKey.compare(key1, key2), is(lessThan(0)));
        key1 = new DomainKey("a", 1);
        key2 = new DomainKey("a", 2);
        assertThat(DomainKey.compare(key1, key2), is(lessThan(0)));
    }

    @Test
    public void compareToGreaterThan() {
        DomainKey key1 = new DomainKey("b", 1);
        DomainKey key2 = new DomainKey("a", 1);
        assertThat(DomainKey.compare(key1, key2), is(greaterThan(0)));
        key1 = new DomainKey("a", 2);
        key2 = new DomainKey("a", 1);
        assertThat(DomainKey.compare(key1, key2), is(greaterThan(0)));
    }
}
