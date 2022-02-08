package com.timtrense.template.lang.std;

import com.timtrense.template.PlaceholderDefinition;
import com.timtrense.template.TemplateLanguage;
import com.timtrense.template.TemplatePart;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The default implementation flavor for {@link TemplateLanguage}.
 * <p>
 * The {@link com.timtrense.template.Template template source text} would look something like:
 * <code>
 * <p>
 * Hello, $(name, World)!
 * It is $datetime(time, HH:mm, SOME_DEFAULT) o'clock.
 * I like to drive a $enum(carType, MAJOR_BRAND).
 * <p>
 * Here is an escaped \$sequence() that will just
 * be rendered as is (excluding the first '\' )
 * </code>
 * Basic values are enclosed using {@code ${variable, default} } notation.
 * There are basic transformations that can be applied, like {@code $datetime }
 * which converts an {@link java.time.temporal.TemporalAccessor} to text using a given format (or ISO-Zoned-Format if omitted).
 * Notice that defaults may always be omitted.
 * <p>
 * Hint: You may either extend from this class or call {@link #placeholderDefinitions}.{@link Map#put(Object, Object) put(name, PlaceholderDefinition)}
 * to add custom {@link PlaceholderDefinition placeholder types}
 *
 * @author Tim Trense
 * @since 1.0
 */
public class StandardTemplateLanguage implements TemplateLanguage {

    private static final Pattern PLACEHOLDER_WRAPPER = Pattern.compile("\\$([^(]*)\\(([^)]*)\\)");
    private static final Map<String, PlaceholderDefinition> PLACEHOLDER_DEFINITIONS = Map.of(
            "", new TextPlaceholderDefinition(','),
            "datetime", new DateTimePlaceholderDefinition(','),
            "enum", new EnumPlaceholderDefinition(','),
            "list", new ListPlaceholderDefinition(',')
    );

    /**
     * all known placeholders.
     * The getter returns a mutable reference to the internal state to allow for easy "inline" extension of
     * the standard language used in one's application.
     * While this allows one to tamper with the language after creation, it lowers the learning curve for
     * this library. If one prefers an immutable variant of a language, one may
     * use {@link ImmutableStandardTemplateLanguage} .
     */
    @Getter
    @SuppressFBWarnings("EI_EXPOSE_REP")
    private final @NonNull Map<String, PlaceholderDefinition> placeholderDefinitions;

    /**
     * @see StandardTemplateLanguage
     */
    public StandardTemplateLanguage() {
        placeholderDefinitions = new HashMap<>(PLACEHOLDER_DEFINITIONS);
    }

    protected StandardTemplateLanguage(@NonNull Map<@NonNull String, @NonNull PlaceholderDefinition> placeholderDefinitions) {
        this.placeholderDefinitions = placeholderDefinitions;
    }

    @Override
    public char getEscapeSequence() {
        return '\\';
    }

    @Override
    public Pattern getPlaceholderWrapperPattern() {
        return PLACEHOLDER_WRAPPER;
    }

    @Override
    public TemplatePart compilePlaceholder(Matcher placeholderMatcher) {
        String placeholderName = placeholderMatcher.group(1);
        PlaceholderDefinition nextPlaceholder = placeholderDefinitions.get(placeholderName);
        if (nextPlaceholder == null) {
            return null;
        }
        return nextPlaceholder.compile(placeholderMatcher.group(2));
    }
}
