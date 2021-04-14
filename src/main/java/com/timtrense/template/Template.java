package com.timtrense.template;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * A compiled text containing placeholders that are to be replaced by actual values
 *
 * @author Tim Trense
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class Template {

    private final @NonNull TemplatePart[] parts;

    /**
     * Instantiate from a {@link List<TemplatePart>}
     *
     * @param parts the parts of this template
     */
    public Template( @NonNull List<@NonNull TemplatePart> parts ) {
        this.parts = parts.toArray( new TemplatePart[0] );
    }

    /**
     * Build the actual text from replacing the contained placeholders for their respective actual values
     *
     * @param context the {@link Context} containing the necessary information to resolve the placeholders
     * @return the actual resolved text
     */
    public String process( Context context ) {
        StringBuilder builder = new StringBuilder();
        appendTo( context, builder );
        return builder.toString();
    }

    public void process( Context context, Consumer<Object> consumer ) {
        for ( TemplatePart p : parts ) {
            p.process( context, consumer );
        }
    }

    public void appendTo( Context context, StringBuilder appendable ) {
        for ( TemplatePart p : parts ) {
            p.append( context, appendable );
        }
    }

    public void appendTo( Context context, StringBuffer appendable ) {
        for ( TemplatePart p : parts ) {
            p.append( context, appendable );
        }
    }

    public void appendTo( Context context, Appendable appendable ) throws IOException {
        for ( TemplatePart p : parts ) {
            p.append( context, appendable );
        }
    }

    public void writeTo( Context context, Writer writer ) throws IOException {
        for ( TemplatePart p : parts ) {
            p.writeTo( context, writer );
        }
    }

    public void writeTo( Context context, OutputStream outputStream ) throws IOException {
        for ( TemplatePart p : parts ) {
            p.writeTo( context, outputStream );
        }
    }

    public void writeTo( Context context, OutputStream outputStream, Charset charset ) throws IOException {
        for ( TemplatePart p : parts ) {
            p.writeTo( context, outputStream, charset );
        }
    }

    /**
     * Build the actual text from replacing the contained placeholders for their respective actual values
     *
     * @param values the values of a {@link Context} (constructed with otherwise default settings) containing the necessary information to resolve the placeholders
     * @return the actual resolved text
     */
    public String process( Map<@NonNull String, Object> values ) {
        Context context = new Context( values );
        return process( context );
    }

}
