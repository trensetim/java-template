package com.timtrense.template.lang.std;

import com.timtrense.template.Context;
import com.timtrense.template.TemplatePart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A placeholder for injecting a list-string of some collection.
 * <p>
 * The actual value may be any kind of {@link Collection} or array or {@link Stream} and must be
 * present in the {@link Context} under the {@link #name}.
 * The actual value may be a {@link Callable} returning an {@link Collection}, array or {@link Stream}.
 * If the value is not present or the {@link Callable} returns {@code null} then the {@link #defaultValue} will
 * be printed (which may be an arbitrary string and will NOT be formatted).
 * <p>
 * The given {@link Collection}, array or {@link Stream} will be completely enumerated.
 * If a {@link Stream} is supplied, it will be fully consumed by this placeholder.
 * {@link Objects#toString(Object)} will be called on each element.
 * <p>
 * <b>Warning: Do not supply infinite streams here</b>
 * <p>
 * A list-string is composed like {@link Collectors#joining(CharSequence, CharSequence, CharSequence)}.
 * <p>
 * Example: For a collection/array/stream containing 'A', 'B', 'C' and a prefix='[' and suffix=']' and delimiter=','
 * the resulting string would be "<b>[A,B,C]</b>"
 *
 * @author Tim Trense
 * @since 1.1
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ListPlaceholder implements TemplatePart {

    public static final Object DEFAULT_VALUE = null;
    public static final String DEFAULT_PREFIX = "[";
    public static final String DEFAULT_SUFFIX = "]";
    public static final String DEFAULT_DELIMITER = ",";

    private @NonNull String name;
    private Object defaultValue = DEFAULT_VALUE;
    private @NonNull String delimiter = DEFAULT_DELIMITER;
    private @NonNull String prefix = DEFAULT_PREFIX;
    private @NonNull String suffix = DEFAULT_SUFFIX;

    /**
     * Creates an instance with default values
     *
     * @param name         the {@link #name}
     * @param defaultValue the {@link #defaultValue}
     * @see #DEFAULT_DELIMITER
     * @see #DEFAULT_PREFIX
     * @see #DEFAULT_SUFFIX
     */
    public ListPlaceholder(@NonNull String name, Object defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    @SneakyThrows
    @Override
    public Object process(Context context) {
        Object value = context.get(name);
        if (value instanceof Callable) {
            value = ((Callable<?>) value).call();
        }
        if (value == null) {
            return defaultValue;
        }
        Stream<?> stream;
        if (value.getClass().isArray()) {
            final Object arrayObject = value;
            stream = Stream.generate(new Supplier<>() {
                private int index = 0;

                @Override
                public Object get() {
                    int thisIndex = index++;
                    if (thisIndex >= Array.getLength(arrayObject)) {
                        return null;
                    }
                    return Array.get(arrayObject, thisIndex);
                }
            }).takeWhile(Objects::nonNull);
        } else if (value instanceof Collection) {
            stream = ((Collection<?>) value).stream();
        } else if (value instanceof Stream) {
            stream = (Stream<?>) value;
        } else {
            return defaultValue;
        }

        return stream
                .map(Objects::toString)
                .collect(Collectors.joining(delimiter, prefix, suffix));
    }
}
