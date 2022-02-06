package com.timtrense.template.lang.std;

import com.timtrense.template.PlaceholderDefinition;
import lombok.NonNull;

import java.util.Collections;
import java.util.Map;

/**
 * The default immutable variant of {@link StandardTemplateLanguage}.
 *
 * @author Tim Trense
 * @since 1.1
 */
public class ImmutableStandardTemplateLanguage extends StandardTemplateLanguage {

    /**
     * @see ImmutableStandardTemplateLanguage
     */
    public ImmutableStandardTemplateLanguage() {
        super();
    }

    protected ImmutableStandardTemplateLanguage(Map<@NonNull String, @NonNull PlaceholderDefinition> placeholderDefinitions) {
        super(placeholderDefinitions);
    }

    /**
     * @return an unmodifiable view of {@link StandardTemplateLanguage#getPlaceholderDefinitions()}
     */
    @Override
    public @NonNull Map<String, PlaceholderDefinition> getPlaceholderDefinitions() {
        return Collections.unmodifiableMap(super.getPlaceholderDefinitions());
    }
}
