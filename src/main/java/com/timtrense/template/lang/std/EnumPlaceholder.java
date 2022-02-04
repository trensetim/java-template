package com.timtrense.template.lang.std;

import com.timtrense.template.Context;
import com.timtrense.template.TemplatePart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;

/**
 * A placeholder for injecting the name of a enum constant.
 * <p>
 * The actual value must be a {@link Enum} and must be present in the {@link Context} under the {@link #name}.
 * The actual value may be a {@link Callable} returning a {@link Enum}.
 * If the value is not present or the {@link Callable} returns {@code null} then the {@link #defaultValue} will
 * be printed (which may be an arbitrary string and will NOT be formatted).
 *
 * @author Tim Trense
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class EnumPlaceholder implements TemplatePart {

    private @NonNull String name;
    private String defaultValue;

    @SneakyThrows
    @Override
    public String process(Context context) {
        Object value = context.get(name);
        if (value instanceof Callable<?>) {
            value = ((Callable<?>) value).call();
        }
        Enum<?> enumValue = ((Enum<?>) value);
        if (enumValue == null) {
            return defaultValue;
        }
        return enumValue.name();
    }
}
