package com.timtrense.template.lang.std;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NonNull;

import com.timtrense.template.PlaceholderDefinition;
import com.timtrense.template.TemplatePart;

public class TextPlaceholderDefinition implements PlaceholderDefinition {

    @NonNull
    private final Pattern pattern;

    public TextPlaceholderDefinition( char s ) {
        pattern = Pattern.compile( "(\\w[^" + s + "]*)" + s + "\\s*?(.+)?", Pattern.CASE_INSENSITIVE );
    }

    @Override
    public TemplatePart compile( @NonNull String placeholderText ) {
        Matcher matcher = pattern.matcher( placeholderText );
        if ( !matcher.matches() ) {
            return null;
        }
        String name = matcher.group( 1 );
        String defaultValue = name;
        if ( matcher.groupCount() > 1 ) {
            defaultValue = matcher.group( 2 );
        }
        return new TextPlaceholder( name, defaultValue );
    }

}
