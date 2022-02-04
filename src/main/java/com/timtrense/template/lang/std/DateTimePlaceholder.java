package com.timtrense.template.lang.std;

import com.timtrense.template.Context;
import com.timtrense.template.TemplatePart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.concurrent.Callable;

/**
 * A placeholder for injecting a date and/or time value, formatted according to an {@link DateTimeFormatter}.
 * <p>
 * The actual value must be a {@link TemporalAccessor} and must be present in the {@link Context} under the {@link #name}.
 * The actual value may be a {@link Callable} returning a {@link TemporalAccessor}.
 * If the value is not present or the {@link Callable} returns {@code null} then the {@link #defaultValue} will
 * be printed (which may be an arbitrary string and will NOT be formatted).
 *
 * @author Tim Trense
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class DateTimePlaceholder implements TemplatePart {

    private String name;
    private DateTimeFormatter formatter;
    private String defaultValue;

    @SneakyThrows
    @Override
    public String process(Context context) {
        Object value = context.get(name);
        if (value instanceof Callable<?>) {
            value = ((Callable<?>) value).call();
        }
        if (value == null) {
            return defaultValue;
        }

        return formatter.format((TemporalAccessor) value);
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDefaultValue(TemporalAccessor defaultValue) {
        this.defaultValue = formatter.format(defaultValue);
    }
}
