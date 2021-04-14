package com.timtrense.template.lang.std;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NonNull;

import com.timtrense.template.PlaceholderDefinition;
import com.timtrense.template.TemplatePart;

public class DateTimePlaceholderDefinition implements PlaceholderDefinition {

    private final @NonNull Pattern pattern;

    public DateTimePlaceholderDefinition( char s ) {
        pattern = Pattern.compile( "(\\w[^" + s + "]*)" + s + "\\s*?([^" + s + "]+)?" + s + "\\s*?(.+)?", Pattern.CASE_INSENSITIVE );
    }

    @Override
    public TemplatePart compile( @NonNull String placeholderText ) {
        Matcher matcher = pattern.matcher( placeholderText );
        if ( !matcher.matches() ) {
            return null;
        }
        String name = matcher.group( 1 );
        String formatString = null;
        if ( matcher.groupCount() > 1 ) {
            formatString = matcher.group( 2 );
        }
        String defaultValue = name;
        if ( matcher.groupCount() > 2 ) {
            defaultValue = matcher.group( 3 );
        }

        DateTimeFormatter format;
        if ( formatString == null ) {
            format = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        }
        else {
            format = DateTimeFormatter.ofPattern( formatString );
        }

        return new DateTimePlaceholder( name, format, defaultValue );
    }
}
