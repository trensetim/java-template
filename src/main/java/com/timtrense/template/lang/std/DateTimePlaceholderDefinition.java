package com.timtrense.template.lang.std;

import com.timtrense.template.Context;
import com.timtrense.template.PlaceholderDefinition;
import com.timtrense.template.TemplatePart;
import lombok.NonNull;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The default {@link PlaceholderDefinition} for a {@link DateTimePlaceholder}.
 * <p>
 * The placeholder needs to be written like
 * {@code variableName SEPARATOR OPTIONAL_WHITESPACE formatText SEPARATOR OPTIONAL_WHITESPACE defaultValue}
 * where SEPARATOR may be a given {@link Character}.
 * E.G. {@code myVariable,yyyy-MM-dd,defaultValue} resolving to either the value of the {@link Context}-variable "myVariable"
 * formatted as YEAR-MONTH-DAY or the given default value if that variable has a {@code null} value.
 *
 * @author Tim Trense
 * @since 1.0
 */
public class DateTimePlaceholderDefinition implements PlaceholderDefinition {

    private static final int GROUP_VARIABLE_NAME = 1;
    private static final int GROUP_FORMAT_STRING = 2;
    private static final int GROUP_DEFAULT_VALUE = 2;
    private final @NonNull Pattern pattern;

    /**
     * @param s separator, see {@link DateTimePlaceholderDefinition}
     */
    public DateTimePlaceholderDefinition(char s) {
        pattern = Pattern.compile("(\\w[^" + s + "]*)" + s + "\\s*?([^" + s + "]+)?" + s + "\\s*?(.+)?", Pattern.CASE_INSENSITIVE);
    }

    @Override
    public TemplatePart compile(@NonNull String placeholderText) {
        Matcher matcher = pattern.matcher(placeholderText);
        if (!matcher.matches()) {
            return null;
        }
        String name = matcher.group(GROUP_VARIABLE_NAME);
        String formatString = null;
        if (matcher.groupCount() > GROUP_VARIABLE_NAME) {
            formatString = matcher.group(GROUP_FORMAT_STRING);
        }
        String defaultValue = name;
        if (matcher.groupCount() > GROUP_FORMAT_STRING) {
            defaultValue = matcher.group(GROUP_DEFAULT_VALUE);
        }

        DateTimeFormatter format;
        if (formatString == null) {
            format = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        } else {
            format = DateTimeFormatter.ofPattern(formatString);
        }

        return new DateTimePlaceholder(name, format, defaultValue);
    }
}
