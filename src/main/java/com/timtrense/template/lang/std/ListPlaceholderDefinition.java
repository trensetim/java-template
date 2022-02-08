package com.timtrense.template.lang.std;

import com.timtrense.template.Context;
import com.timtrense.template.PlaceholderDefinition;
import com.timtrense.template.TemplatePart;
import lombok.NonNull;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The default {@link PlaceholderDefinition} for a {@link ListPlaceholder}.
 * <p>
 * The placeholder needs to be written like
 * <pre>
 * variableName
 * (SEPARATOR WHITESPACE? defaultValue)?
 * (SEPARATOR WHITESPACE? delimiter)?
 * (SEPARATOR WHITESPACE? prefix)?
 * (SEPARATOR WHITESPACE? suffix)?
 * </pre>
 * where SEPARATOR may be a given {@link Character} and elements with an <b>?</b> are optional.
 * E.G. {@code myVariable,defaultValue} resolving to either the value of the {@link Context}-variable "myVariable" or
 * the given default value if that variable has a {@code null} value.
 * <p>
 * Since one may want to use SEPARATOR as a delimiter, one can specify the delimiter to be an empty string to
 * make this happen. E.g. {@code $(var,default,,[,])} will make for a separator being ',' due to the third argument
 * being left blank.
 *
 * @author Tim Trense
 * @since 1.1
 */
public class ListPlaceholderDefinition implements PlaceholderDefinition {

    private static final int GROUP_VARIABLE_NAME = 1;
    private static final int GROUP_DEFAULT_VALUE = 2;
    private static final int GROUP_DELIMITER = 3;
    private static final int GROUP_PREFIX = 4;
    private static final int GROUP_SUFFIX = 5;

    @NonNull
    private final Pattern pattern;

    private final String separator;

    /**
     * @param s separator, see {@link ListPlaceholderDefinition}
     */
    public ListPlaceholderDefinition(char s) {
        String requiredComponent = "([^" + s + "]+)";
        String maybeBlankComponent = "([^" + s + "]*)";
        String componentSeparator = s + "\\s*";
        pattern = Pattern.compile("(\\w[^" + s + "]*)"
                        + "(?:" + componentSeparator + requiredComponent + ")?" // defaultValue
                        + "(?:" + componentSeparator + maybeBlankComponent + ")?" // delimiter
                        + "(?:" + componentSeparator + requiredComponent + ")?" // prefix
                        + "(?:" + componentSeparator + requiredComponent + ")?", // suffix
                Pattern.CASE_INSENSITIVE);
        this.separator = String.valueOf(s);
    }

    @Override
    public TemplatePart compile(@NonNull String placeholderText) {
        Matcher matcher = pattern.matcher(placeholderText);
        if (!matcher.matches()) {
            return null;
        }
        String name = matcher.group(GROUP_VARIABLE_NAME);
        Object defaultValue = ListPlaceholder.DEFAULT_VALUE;
        String prefix = ListPlaceholder.DEFAULT_PREFIX;
        String suffix = ListPlaceholder.DEFAULT_SUFFIX;
        String delimiter = ListPlaceholder.DEFAULT_DELIMITER;
        if (matcher.groupCount() > GROUP_VARIABLE_NAME) {
            defaultValue = matcher.group(GROUP_DEFAULT_VALUE);
//            if (defaultValue == null) {
//                defaultValue = ListPlaceholder.DEFAULT_VALUE; // DEFAULT_VALUE==null
//            }
        }
        if (matcher.groupCount() > GROUP_DEFAULT_VALUE) {
            delimiter = Objects.requireNonNullElse(matcher.group(GROUP_DELIMITER), ListPlaceholder.DEFAULT_DELIMITER);
        }
        if (matcher.groupCount() > GROUP_DELIMITER) {
            prefix = Objects.requireNonNullElse(matcher.group(GROUP_PREFIX), ListPlaceholder.DEFAULT_PREFIX);
        }
        if (matcher.groupCount() > GROUP_PREFIX) {
            suffix = Objects.requireNonNullElse(matcher.group(GROUP_SUFFIX), ListPlaceholder.DEFAULT_SUFFIX);
        }

        if (delimiter.isEmpty()) {
            delimiter = separator;
        }

        return new ListPlaceholder(name, defaultValue, delimiter, prefix, suffix);
    }
}
