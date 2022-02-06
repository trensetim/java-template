package com.timtrense.template.lang.std;

import com.timtrense.template.PlaceholderDefinition;
import lombok.NonNull;

import java.util.Collections;
import java.util.Map;

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
