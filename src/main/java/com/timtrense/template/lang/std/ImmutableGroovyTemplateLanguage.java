package com.timtrense.template.lang.std;

import com.timtrense.template.PlaceholderDefinition;
import com.timtrense.template.TemplateLanguage;
import lombok.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The default immutable variant of {@link GroovyTemplateLanguage}
 *
 * @author Tim Trense
 * @since 1.1
 */
public class ImmutableGroovyTemplateLanguage extends GroovyTemplateLanguage {

    /**
     * @see ImmutableGroovyTemplateLanguage
     */
    public ImmutableGroovyTemplateLanguage() {
        super();
    }

    @Override
    public @NonNull Map<String, PlaceholderDefinition> getPlaceholderDefinitions() {
        return Collections.unmodifiableMap(super.getPlaceholderDefinitions());
    }
}
