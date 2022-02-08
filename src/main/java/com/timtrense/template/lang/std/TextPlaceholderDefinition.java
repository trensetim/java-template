package com.timtrense.template.lang.std;

import com.timtrense.template.Context;
import com.timtrense.template.PlaceholderDefinition;
import com.timtrense.template.TemplatePart;
import lombok.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The default {@link PlaceholderDefinition} for a {@link TextPlaceholder}.
 * <p>
 * The placeholder needs to be written like
 * {@code variableName SEPARATOR OPTIONAL_WHITESPACE defaultValue} where SEPARATOR may be a given {@link Character}.
 * E.G. {@code myVariable,defaultValue} resolving to either the value of the {@link Context}-variable "myVariable" or
 * the given default value if that variable has a {@code null} value
 *
 * @author Tim Trense
 * @since 1.0
 */
public class TextPlaceholderDefinition implements PlaceholderDefinition {

    @NonNull
    private final Pattern pattern;

    /**
     * @param s separator, see {@link TextPlaceholderDefinition}
     */
    public TextPlaceholderDefinition(char s) {
        pattern = Pattern.compile("(\\w[^" + s + "]*)(?:" + s + "\\s*(.+))?", Pattern.CASE_INSENSITIVE);
    }

    @Override
    public TemplatePart compile(@NonNull String placeholderText) {
        Matcher matcher = pattern.matcher(placeholderText);
        if (!matcher.matches()) {
            return null;
        }
        String name = matcher.group(1);
        String defaultValue = null;
        if (matcher.groupCount() > 1) {
            defaultValue = matcher.group(2); // maybe null if omitted
        }
        return new TextPlaceholder(name, defaultValue);
    }

}
