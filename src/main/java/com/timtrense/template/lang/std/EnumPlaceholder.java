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
public class EnumPlaceholder implements TemplatePart {

    private @NonNull String name;
    private String defaultValue;

    @SneakyThrows
    @Override
    public String process( Context context ) {
        Object value = context.get( name );
        if ( value instanceof Callable<?> ) {
            value = ( (Callable<?>)value ).call();
        }
        Enum<?> enumValue = ( (Enum<?>)value );
        if ( enumValue == null ) {
            return defaultValue;
        }
        return enumValue.name();
    }
}
