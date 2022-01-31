package com.timtrense.template.lang.std;

import com.timtrense.template.Context;
import com.timtrense.template.TemplatePart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;

/**
 * A placeholder for injecting an arbitrary string.
 * <p>
 * The actual value may be any kind of {@link Object} and must be present in the {@link Context} under the {@link #name}.
 * The actual value may be a {@link Callable} returning an {@link Object}.
 * If the value is not present or the {@link Callable} returns {@code null} then the {@link #defaultValue} will
 * be printed (which may be an arbitrary string and will NOT be formatted).
 * <p>
 * The actual value may be a {@link String}. Otherwise, {@link Object#toString()} will be called
 *
 * @author Tim Trense
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class TextPlaceholder implements TemplatePart {

    private @NonNull String name;
    private String defaultValue;

    @SneakyThrows
    @Override
    public Object process(Context context) {
        Object value = context.getOrDefault(name, defaultValue);
        if (value instanceof Callable) {
            value = ((Callable<?>) value).call();
        }
        return value;
    }
}
