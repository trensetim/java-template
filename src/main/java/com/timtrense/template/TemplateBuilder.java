package com.timtrense.template;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.timtrense.template.lang.std.StandardTemplateLanguage;

/**
 * The Builder Class for {@link Template templates}.
 * A {@link TemplateBuilder} has a {@link TemplateLanguage} that it uses to
 * parse template source texts.
 * The static default instance will use the {@link StandardTemplateLanguage} and
 * NOT fail on invalid placeholders.
 *
 * @author Tim Trense
 * @since 1.0
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TemplateBuilder {

    /**
     * The default instance for building templates. Users may change the
     * contained {@link #templateLanguage} to their hearts contents globally
     * for the application while bootstrapping/configuring it.
     */
    public static final TemplateBuilder DEFAULT_INSTANCE = new TemplateBuilder();

    /**
     * The definition of a templates source text language used to parse those.
     */
    private @NonNull TemplateLanguage templateLanguage = new StandardTemplateLanguage();
    /**
     * Whether to abort the building of a template if a anticipated placeholder
     * could not be compiled by the {@link #templateLanguage}.
     * Defaults to {@code false} which means "keep the failed placeholder as a normal part of text"
     */
    private boolean failOnInvalidPlaceholder = false;

    /**
     * Uses the {@link StandardTemplateLanguage} to parse the given template
     * source text using {@link #buildTemplate(String)} on the {@link #DEFAULT_INSTANCE}.
     *
     * @param templateText the template source text
     * @return the parsed template, not null
     */
    public static Template build( @NonNull String templateText ) {
        return DEFAULT_INSTANCE.buildTemplate( templateText );
    }

    /**
     * Uses the configured {@link #templateLanguage} to parse the given template
     * source text
     *
     * @param templateText the template source text
     * @return the parsed template, not null
     */
    public Template buildTemplate( @NonNull String templateText ) {
        List<TemplatePart> parts = new LinkedList<>();
        Matcher placeholderMatcher = templateLanguage.getPlaceholderWrapperPattern().matcher( templateText );
        char escapeSequence = templateLanguage.getEscapeSequence();

        int searchPosition = 0;
        int templateTextLength = templateText.length();
        while ( searchPosition < templateTextLength ) {
            if ( !placeholderMatcher.find( searchPosition ) ) {
                // just a trailing TextPart remaining
                break;
            }

            TemplatePart nextPart = templateLanguage.compilePlaceholder( placeholderMatcher );
            // nextPart == null : means could not compile

            if ( nextPart == null && failOnInvalidPlaceholder ) {
                int position = placeholderMatcher.start();
                throw new TemplateFormatException(
                        "Could not compile placeholder on template on \"" + templateText + "\" at " + position,
                        templateText, position );
            }

            int start = placeholderMatcher.start();

            if ( templateText.charAt( start - 1 ) == escapeSequence || nextPart == null ) {
                // add text part between last placeholder and escape sequence
                int endOfBetween = ( nextPart != null ) ? ( start - 1 ) : start;
                parts.add( new TextPart( templateText.substring( searchPosition, endOfBetween ) ) );

                // add text part containing the matched text
                searchPosition = placeholderMatcher.end();
                parts.add( new TextPart( templateText.substring( start, searchPosition ) ) );
                continue;
            }
            else if ( start > searchPosition ) {
                // add text part between last placeholder and current placeholder
                parts.add( new TextPart( templateText.substring( searchPosition, start ) ) );
            }

            parts.add( nextPart );

            searchPosition = placeholderMatcher.end();
        }

        if ( searchPosition < templateTextLength ) {
            // add remaining part
            parts.add( new TextPart( templateText.substring( searchPosition ) ) );
        }

        return new Template( parts );
    }
}
