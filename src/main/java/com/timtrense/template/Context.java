package com.timtrense.template;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * The context with which a {@link Template} can be resolved to real text.
 * The {@link Context} holds the placeholders values and the {@link Locale} and
 * {@link TimeZone} of the user for that the template will be resolved (both default to system settings).
 *
 * @author Tim Trense
 * @since 1.0
 */
@AllArgsConstructor
public class Context extends HashMap<String, Object> {

    @Getter
    @NonNull
    private TimeZone timeZone = TimeZone.getDefault();
    @Getter
    @NonNull
    private Locale locale = Locale.getDefault();

    /**
     * Instantiates a Context with initially no placeholder values
     */
    public Context() {
    }

    /**
     * Instantiates a Context from the given placeholder values
     *
     * @param values {@link Map} of placeholder keys (names) to values that will be copied
     */
    public Context( @NonNull Map<@NonNull String, Object> values ) {
        super( values );
    }

    /**
     * Instantiates a Context from the given placeholder values
     *
     * @param values   {@link Map} of placeholder keys (names) to values that will be copied
     * @param locale   the users {@link Locale}
     * @param timeZone the users {@link TimeZone}
     */
    public Context( @NonNull Map<@NonNull String, Object> values, @NonNull Locale locale, @NonNull TimeZone timeZone ) {
        super( values );
        this.locale = locale;
        this.timeZone = timeZone;
    }

    /**
     * Instantiates a Context from the given locale
     *
     * @param locale   the users {@link Locale}
     * @param timeZone the users {@link TimeZone}
     */
    public Context( @NonNull Locale locale, @NonNull TimeZone timeZone ) {
        this.locale = locale;
        this.timeZone = timeZone;
    }

    /**
     * Instantiates a Context from the given locale
     *
     * @param timeZone the users {@link TimeZone}
     */
    public Context( @NonNull TimeZone timeZone ) {
        this.timeZone = timeZone;
    }


    /**
     * Instantiates a Context from the given locale
     *
     * @param locale the users {@link Locale}
     */
    public Context( @NonNull Locale locale ) {
        this.locale = locale;
    }

    /**
     * @return the current {@link ZoneOffset} of the contained {@link #timeZone}
     */
    public ZoneOffset getZoneOffsetNow() {
        return getZoneOffsetAt( Instant.now() );
    }

    /**
     * @param instant the time at which to resolve the offset
     * @return the {@link ZoneOffset} of the contained {@link #timeZone} at specified time
     */
    public ZoneOffset getZoneOffsetAt( @NonNull Instant instant ) {
        return timeZone.toZoneId().getRules().getOffset( instant );
    }

    /**
     * Contract to put in lazily evaluated lambda expressions
     *
     * @param key as stated by {@link Map#put(Object, Object)}
     * @param value as stated by {@link Map#put(Object, Object)}
     * @return as stated by {@link Map#put(Object, Object)}
     * @see Map#put(Object, Object)
     */
    public Object put( @NonNull String key, Callable<?> value ) {
        return super.put( key, value );
    }
}
