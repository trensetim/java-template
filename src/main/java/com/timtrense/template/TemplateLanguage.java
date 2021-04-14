package com.timtrense.template;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The definition of the style of placeholders in a {@link Template} source text
 *
 * @author Tim Trense
 * @since 1.0
 */
public interface TemplateLanguage {

    /**
     * @return the character used to tell if potential match of the {@link #getPlaceholderWrapperPattern()} should be ignored because it is commented-out
     */
    char getEscapeSequence();

    /**
     * @return the pattern enclosing all placeholders that will be applied consecutively
     * to the {@link Template} source text to split it to {@link TextPart text parts} and {@link TemplatePart placeholder parts}
     */
    Pattern getPlaceholderWrapperPattern();

    /**
     * called when there is a found match to {@link #getPlaceholderWrapperPattern()} that should be compiled to a
     * {@link TemplatePart placeholder part}.
     *
     * Implementations MUST NOT alter the parameter matchedPlaceholder.
     * Implementations MUST NOT have side-effects nor state.
     *
     * @param matchedPlaceholder the matcher that was called {@link Matcher#find()} on thus holding the currently found placeholder.
     * @return the compiled {@link TemplatePart} or null if the contents of the matched region do not express a valid placeholder
     * (which will not lead to an error but instead treat the indicated region as regular {@link TextPart text}
     */
    TemplatePart compilePlaceholder( Matcher matchedPlaceholder );
}
