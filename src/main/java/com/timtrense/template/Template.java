package com.timtrense.template;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.NonNull;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A compiled text containing placeholders that are to be replaced by actual values
 *
 * @author Tim Trense
 * @since 1.0
 */
@Data
/* The circular dependency is with TemplateBuilder, used from static process(templateText, context).
   While not overly clean, this method lowers the learning curve for the library quite a bit
   and reduces code complexities in the main use case of "just a quick template here" for users of this library.
   So we accept this circ.dep. as a tradeoff for good developer experience.
   */
@SuppressFBWarnings("FCCD_FIND_CLASS_CIRCULAR_DEPENDENCY")
public class Template {

    private final @NonNull TemplatePart[] parts;

    /**
     * Instantiate from a {@link List}
     *
     * @param parts the parts of this template. On the given {@link List} {@link List#toArray(Object[]) toArray} will be called
     */
    public Template(@NonNull List<@NonNull TemplatePart> parts) {
        this.parts = parts.toArray(new TemplatePart[0]);
    }

    /**
     * @param parts the parts of this template. The given array will be copied
     */
    public Template(@NonNull TemplatePart @NonNull [] parts) {
        this.parts = Arrays.copyOf(parts, parts.length);
    }

    /**
     * Ad-Hoc compiles a {@link Template} from the given source text and
     * uses the given {@link Context} to {@link #process(Context)} it.
     *
     * @param templateText the template source text
     * @param context      all placeholder values to put in combined with a locale setting
     * @return the actual text
     */
    public static String process(@NonNull String templateText, @NonNull Context context) {
        Template template = TemplateBuilder.build(templateText);
        return template.process(context);
    }

    /**
     * Ad-Hoc compiles a {@link Template} from the given source text and
     * creates a default {@link Context} to {@link #process(Context)} it.
     *
     * @param templateText the template source text
     * @param values       the placeholder values to put in
     * @return the actual text
     */
    public static String process(@NonNull String templateText, @NonNull Map<@NonNull String, Object> values) {
        Template template = TemplateBuilder.build(templateText);
        return template.process(values);
    }

    /**
     * @return an immutable copy of all parts of this
     */
    public @NonNull TemplatePart[] getParts() {
        return Arrays.copyOf(parts, parts.length);
    }

    /**
     * Build the actual text from replacing the contained placeholders for their respective actual values
     *
     * @param context the {@link Context} containing the necessary information to resolve the placeholders
     * @return the actual resolved text
     */
    @SuppressFBWarnings("MOM_MISLEADING_OVERLOAD_MODEL")
    public String process(Context context) {
        StringBuilder builder = new StringBuilder();
        appendTo(context, builder);
        return builder.toString();
    }

    /**
     * Overloading for {@link #process(Context)}
     *
     * @param context  the context to process all {@link TemplatePart TemplateParts}
     * @param consumer destination for the output of {@link TemplatePart#process(Context)}
     */
    public void process(Context context, Consumer<Object> consumer) {
        for (TemplatePart p : parts) {
            p.process(context, consumer);
        }
    }

    /**
     * Overloading for {@link #process(Context)}
     *
     * @param context    the context to process all {@link TemplatePart TemplateParts}
     * @param appendable destination for the output of {@link TemplatePart#process(Context)}
     */
    public void appendTo(Context context, StringBuilder appendable) {
        for (TemplatePart p : parts) {
            p.append(context, appendable);
        }
    }

    /**
     * Overloading for {@link #process(Context)}
     *
     * @param context    the context to process all {@link TemplatePart TemplateParts}
     * @param appendable destination for the output of {@link TemplatePart#process(Context)}
     */
    public void appendTo(Context context, StringBuffer appendable) {
        for (TemplatePart p : parts) {
            p.append(context, appendable);
        }
    }

    /**
     * Overloading for {@link #process(Context)}
     *
     * @param context    the context to process all {@link TemplatePart TemplateParts}
     * @param appendable destination for the output of {@link TemplatePart#process(Context)}
     */
    public void appendTo(Context context, Appendable appendable) throws IOException {
        for (TemplatePart p : parts) {
            p.append(context, appendable);
        }
    }

    /**
     * Overloading for {@link #process(Context)}
     *
     * @param context the context to process all {@link TemplatePart TemplateParts}
     * @param writer  destination for the output of {@link TemplatePart#process(Context)}
     */
    public void writeTo(Context context, Writer writer) throws IOException {
        for (TemplatePart p : parts) {
            p.writeTo(context, writer);
        }
    }

    /**
     * Overloading for {@link #process(Context)}
     *
     * @param context      the context to process all {@link TemplatePart TemplateParts}
     * @param outputStream destination for the output of {@link TemplatePart#process(Context)}
     */
    public void writeTo(Context context, OutputStream outputStream) throws IOException {
        for (TemplatePart p : parts) {
            p.writeTo(context, outputStream);
        }
    }

    /**
     * Overloading for {@link #process(Context)}
     *
     * @param context      the context to process all {@link TemplatePart TemplateParts}
     * @param outputStream destination for the output of {@link TemplatePart#process(Context)}
     * @param charset      the charset to encode the output of {@link TemplatePart#process(Context)} with
     */
    public void writeTo(Context context, OutputStream outputStream, Charset charset) throws IOException {
        for (TemplatePart p : parts) {
            p.writeTo(context, outputStream, charset);
        }
    }

    /**
     * Build the actual text from replacing the contained placeholders for their respective actual values
     *
     * @param values the values of a {@link Context} (constructed with otherwise default settings) containing the necessary information to resolve the placeholders
     * @return the actual resolved text
     */
    public String process(@NonNull Map<@NonNull String, Object> values) {
        Context context = new Context(values);
        return process(context);
    }
}
