package com.timtrense.template;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.timtrense.template.lang.std.DateTimePlaceholder;
import com.timtrense.template.lang.std.TextPlaceholder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateTest {

    @Test
    public void apply__helloWorld_tim__helloMe() {
        TemplatePart hello = new TextPart( "Hello, " );
        TemplatePart world = new TextPlaceholder( "world", "World" );
        TemplatePart exclamation = new TextPart( "!" );
        Template template = new Template( new TemplatePart[]{hello, world, exclamation} );

        String result = template.process( Map.of( "world", "Me" ) );

        assertEquals( "Hello, Me!", result );
    }

    @Test
    public void apply__helloDateTime__helloDate() {
        TemplatePart hello = new TextPart( "Hello, " );
        TemplatePart world = new DateTimePlaceholder( "time", DateTimeFormatter.ofPattern( "yyyy-MM-dd" ), "MY_DEFAULT_VALUE" );
        TemplatePart exclamation = new TextPart( "!" );
        Template template = new Template( new TemplatePart[]{hello, world, exclamation} );

        LocalDateTime now = LocalDateTime.of( 2021, 1, 1, 0, 0 );
        String result = template.process( Map.of( "time", now ) );

        assertEquals( "Hello, 2021-01-01!", result );
    }

    @Test
    public void apply__helloDateTime__helloDefault() {
        TemplatePart hello = new TextPart( "Hello, " );
        TemplatePart world = new DateTimePlaceholder( "time", DateTimeFormatter.ofPattern( "yyyy-MM-dd" ), "MY_DEFAULT_VALUE" );
        TemplatePart exclamation = new TextPart( "!" );
        Template template = new Template( new TemplatePart[]{hello, world, exclamation} );

        LocalDateTime now = LocalDateTime.of( 2021, 1, 1, 0, 0 );
        String result = template.process( Map.of( "WRONG_TIME_KEY", now ) );

        assertEquals( "Hello, MY_DEFAULT_VALUE!", result );
    }


}
