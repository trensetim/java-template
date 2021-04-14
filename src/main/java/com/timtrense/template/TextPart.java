package com.timtrense.template;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * The default non-placeholder implementation of {@link TemplatePart}.
 * That will always give a constant text when {@link TemplatePart#process processed}
 *
 * @author Tim Trense
 * @since 1.0
 */
@RequiredArgsConstructor
public class TextPart implements TemplatePart {

    @Setter
    @Getter
    private @NonNull String text;

    @Override
    public String process( Context context ) {
        return text;
    }
}
