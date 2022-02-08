package com.timtrense.template.lang.std;

import com.timtrense.template.PlaceholderDefinition;
import com.timtrense.template.TemplateLanguage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A {@link TemplateLanguage} that uses <a href="https://groovy-lang.org/">groovy</a>-like syntax.
 *
 * @author Tim Trense
 * @since 1.0
 */
public class GroovyTemplateLanguage extends StandardTemplateLanguage {

    private static final Pattern PLACEHOLDER_WRAPPER = Pattern.compile("\\$([^{]*)\\{([^}]*)}");
    private static final Map<String, PlaceholderDefinition> PLACEHOLDER_DEFINITIONS = Map.of(
            "", new TextPlaceholderDefinition(';'),
            "datetime", new DateTimePlaceholderDefinition(';'),
            "enum", new EnumPlaceholderDefinition(';'),
            "list", new ListPlaceholderDefinition(';')
    );

    /**
     * @see GroovyTemplateLanguage
     */
    public GroovyTemplateLanguage() {
        super(new HashMap<>(PLACEHOLDER_DEFINITIONS));
    }

    @Override
    public Pattern getPlaceholderWrapperPattern() {
        return PLACEHOLDER_WRAPPER;
    }

}
