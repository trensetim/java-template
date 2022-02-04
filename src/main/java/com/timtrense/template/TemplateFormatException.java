package com.timtrense.template;

import lombok.Getter;

/**
 * Indicates that a template could not be build correctly
 *
 * @author Tim Trense
 * @since 1.0
 */
public class TemplateFormatException extends IllegalArgumentException {

    @Getter
    private final String templateText;
    @Getter
    private final Integer position;

    /**
     * Standard Exception Constructor.
     *
     * @param message      the exception message as in {@link Exception#getMessage()}
     * @param templateText the processed template text
     * @param position     the position in the template text that caused this exception
     */
    public TemplateFormatException(String message, String templateText, Integer position) {
        super(message);
        this.templateText = templateText;
        this.position = position;
    }

    /**
     * Standard Exception Constructor with differing root cause.
     *
     * @param message      the exception message as in {@link Exception#getMessage()}
     * @param templateText the processed template text
     * @param position     the position in the template text that caused this exception
     * @param cause        the root exception for this one
     */
    public TemplateFormatException(String message, String templateText, Integer position, Throwable cause) {
        super(message, cause);
        this.templateText = templateText;
        this.position = position;
    }

}
