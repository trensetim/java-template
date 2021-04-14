package com.timtrense.template.lang.std;

import java.util.concurrent.Callable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;

import com.timtrense.template.Context;
import com.timtrense.template.TemplatePart;

@Data
@AllArgsConstructor
public class TextPlaceholder implements TemplatePart {

    private @NonNull String name;
    private String defaultValue;

    @SneakyThrows
    @Override
    public Object process( Context context ) {
        Object value = context.getOrDefault( name, defaultValue );
        if ( value instanceof Callable ) {
            value = ( (Callable<?>)value ).call();
        }
        return value;
    }
}
