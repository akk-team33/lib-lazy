package de.team33.test.lazy.v1;

import de.team33.libs.lazy.v1.XLazy;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class XLazyTest {

    private int counter = 0;
    private XLazy<Date, InterruptedException> lazy = new XLazy<>(() -> {
        counter += 1;
        Thread.sleep(1);
        return new Date();
    });
    private Date first = new Date();

    @Test
    public void get() throws InterruptedException {
        Thread.sleep(1);
        assertTrue(first.compareTo(lazy.get()) < 0);
        assertSame(lazy.get(), lazy.get());
        assertEquals(1, counter);
    }

    @Test
    public void getMultiThreaded() throws InterruptedException {
        final Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(() -> {
                try {
                    lazy.get();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            });
        }

        assertEquals(0, counter);

        for (final Thread thread : threads) {
            thread.start();
        }
        for (final Thread thread : threads) {
            thread.join();
        }

        assertEquals(1, counter);
    }
}
