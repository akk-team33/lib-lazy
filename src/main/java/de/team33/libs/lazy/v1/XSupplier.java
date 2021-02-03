package de.team33.libs.lazy.v1;

/**
 * @deprecated Remains only for compatibility reasons, use {@link de.team33.libs.exceptional.v4.functional.XSupplier}
 * instead.
 */
@Deprecated
@FunctionalInterface
public interface XSupplier<T, X extends Exception> extends de.team33.libs.exceptional.v4.functional.XSupplier<T, X> {
}
