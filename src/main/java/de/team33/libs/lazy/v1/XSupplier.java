package de.team33.libs.lazy.v1;

import java.util.function.Supplier;

/**
 * A kind of supplier that allows to throw a checked exception.
 *
 * @see Supplier
 */
@FunctionalInterface
public interface XSupplier<T, X extends Exception> {

    /**
     * @see Supplier#get()
     */
    T get() throws X;
}
