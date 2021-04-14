package com.timtrense.template;

import java.util.regex.Matcher;
import lombok.NonNull;

/**
 * The definition (Builder) for {@link TemplatePart template parts} that come from resolving placeholders
 *
 * @author Tim Trense
 * @since 1.0
 */
public interface PlaceholderDefinition {

    /**
     * Creates a {@link PlaceholderDefinition} that returns always the given parameter on {@link #compile(String)}.
     * This function may be used to write some code in a more expressive way as opposed to using a lambda for this.
     *
     * @param part the result of {@link #compile(String)}
     * @return a {@link PlaceholderDefinition} that will always return the parameter
     */
    static PlaceholderDefinition of( TemplatePart part ) {
        return t -> part;
    }

    /**
     * Creates a {@link TemplatePart} that is a placeholder for a future value in a {@link Template}.
     * This implementation should be stateless.
     * A {@link TemplateBuilder} will use this function when finding a placeholder in a template text
     * to construct an object representation of that placeholder
     *
     * @param placeholderText the inner content of the placeholder (usually excluding the wrapping braces)
     *                        as defined by {@link TemplateLanguage#getPlaceholderWrapperPattern()}
     *                        and {@link TemplateLanguage#compilePlaceholder(Matcher)}
     * @return an object representation of that placeholder that, when invoked within a {@link Context},
     * will give the actual value for it
     */
    TemplatePart compile( @NonNull String placeholderText );
}
