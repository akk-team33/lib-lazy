package de.team33.test.lazy.v1;

import de.team33.libs.lazy.v1.Lazy;
import org.junit.Test;

import java.util.Date;

import static de.team33.libs.testing.v1.Runner.parallel;
import static de.team33.libs.testing.v1.Runner.sequential;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class LazyTest {

    private static final String ZERO_SMALLER_FIRST =
            "Although <lazy> already exists, the date <zero> must be smaller than the (first) evaluation of <lazy>";

    private int counter = 0;
    private Lazy<Date> lazy = new Lazy<>(() -> {
        try {
            counter += 1;
            Thread.sleep(1);
            return new Date();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    });

    @Test
    public void getFirst() throws InterruptedException {
        final Date zero = new Date();
        Thread.sleep(1);
        assertTrue(ZERO_SMALLER_FIRST, zero.compareTo(lazy.get()) < 0);
    }

    @Test
    public void getSame() {
        assertSame("If <lazy> is evaluated several times, the result must always be the same.", lazy.get(), lazy.get());
    }

    @Test
    public void getSequential() {
        assertEquals(0, counter);
        sequential(100, () -> lazy.get());
        assertEquals(1, counter);
    }

    @Test
    public void getParallel() {
        assertEquals(0, counter);
        parallel(100, () -> lazy.get());
        assertEquals(1, counter);
    }
}
