package com.timtrense.template;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import lombok.NonNull;

/**
 * An object representation of a gap in a gap text (that is, a {@link Template}) that,
 * when invoked on a {@link Context}, will give the actual value.
 * The required method to implement by subclasses is {@link #process(Context)}.
 * If invocation of that method however would create a significant amount of heap
 * or processing, an implementation may override the other provided methods to be
 * able to stream its results.
 *
 * @author Tim Trense
 * @since 1.0
 */
public interface TemplatePart {

    /**
     * Create the actual value of the placeholder that comes from the {@link Context}.
     * The result WILL be converted using {@link String#valueOf(Object)}.
     * Returning {@link Object} is just a convenience.
     *
     * Implementations MUST be stateless and MUST have no side-effects and MUST not alter the given context.
     * Implementations MAY throw {@link RuntimeException} which
     * will end the processing of more parts and will bubble
     * up to the user of {@link Template#process(Context)}
     *
     * @param context the context with all the information to resolve a {@link Template} once in this very situation
     * @return the value of this placeholder derived from information of the
     * context. MUST be String-convertible. Not null.
     */
    Object process( Context context );

    default void process( Context context, @NonNull Consumer<Object> consumer ) {
        consumer.accept( process( context ) );
    }

    default void append( Context context, @NonNull StringBuilder builder ) {
        builder.append( process( context ) );
    }

    default void append( Context context, @NonNull StringBuffer buffer ) {
        buffer.append( process( context ) );
    }

    default void append( Context context, @NonNull Appendable appendable ) throws IOException {
        appendable.append( String.valueOf( process( context ) ) );
    }

    default void writeTo( Context context, @NonNull Writer writer ) throws IOException {
        writer.write( String.valueOf( process( context ) ) );
    }

    default void writeTo( Context context, @NonNull OutputStream outputStream, @NonNull Charset charset ) throws IOException {
        outputStream.write( String.valueOf( process( context ) ).getBytes( charset ) );
    }

    default void writeTo( Context context, @NonNull OutputStream outputStream ) throws IOException {
        writeTo( context, outputStream, StandardCharsets.UTF_8 );
    }
}
