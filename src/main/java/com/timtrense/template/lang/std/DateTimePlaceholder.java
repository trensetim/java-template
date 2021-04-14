package com.timtrense.template.lang.std;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.concurrent.Callable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import com.timtrense.template.Context;
import com.timtrense.template.TemplatePart;

@Data
@AllArgsConstructor
public class DateTimePlaceholder implements TemplatePart {

    private String name;
    private DateTimeFormatter formatter;
    private String defaultValue;

    @SneakyThrows
    @Override
    public String process( Context context ) {
        Object value = context.get( name );
        if ( value instanceof Callable<?> ) {
            value = ( (Callable<?>)value ).call();
        }
        if ( value == null ) {
            return defaultValue;
        }

        return formatter.format( (TemporalAccessor)value );
    }

    public void setDefaultValue( String defaultValue ) {
        this.defaultValue = defaultValue;
    }

    public void setDefaultValue( TemporalAccessor defaultValue ) {
        this.defaultValue = formatter.format( defaultValue );
    }
}
