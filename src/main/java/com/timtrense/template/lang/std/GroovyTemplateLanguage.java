package com.timtrense.template.lang.std;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.timtrense.template.PlaceholderDefinition;

public class GroovyTemplateLanguage extends StandardTemplateLanguage {

    private static final Pattern PLACEHOLDER_WRAPPER = Pattern.compile( "\\$([^{]*)\\{([^}]*)}" );
    private static final Map<String, PlaceholderDefinition> PLACEHOLDER_DEFINITIONS = Map.of(
            "", new TextPlaceholderDefinition( ';' ),
            "datetime", new DateTimePlaceholderDefinition( ';' ),
            "enum", new EnumPlaceholderDefinition( ';' )
    );

    public GroovyTemplateLanguage() {
        super( new HashMap<>( PLACEHOLDER_DEFINITIONS ) );
    }

    @Override
    public Pattern getPlaceholderWrapperPattern() {
        return PLACEHOLDER_WRAPPER;
    }

}
