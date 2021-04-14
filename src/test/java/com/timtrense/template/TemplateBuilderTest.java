package com.timtrense.template;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.timtrense.template.lang.std.GroovyTemplateLanguage;
import com.timtrense.template.lang.std.TextPlaceholder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemplateBuilderTest {

    @Test
    public void build__helloWorld__helloWorld() {
        String templateString = "Hello, $(world,World)!";
        Template template = TemplateBuilder.build( templateString );

        assertEquals( 3, template.getParts().length );
        assertTrue( template.getParts()[0] instanceof TextPart );
        assertEquals( "Hello, ", ( (TextPart)template.getParts()[0] ).getText() );
        assertTrue( template.getParts()[2] instanceof TextPart );
        assertEquals( "!", ( (TextPart)template.getParts()[2] ).getText() );
        assertTrue( template.getParts()[1] instanceof TextPlaceholder );
        assertEquals( "world", ( (TextPlaceholder)template.getParts()[1] ).getName() );
        assertEquals( "World", ( (TextPlaceholder)template.getParts()[1] ).getDefaultValue() );
    }

    @Test
    public void build_on_groovyLanguage__helloWorld__helloWorld() {
        String templateString = "Hello, ${world;World}!";
        TemplateBuilder builder = new TemplateBuilder();
        builder.setTemplateLanguage( new GroovyTemplateLanguage() );
        Template template = builder.buildTemplate( templateString );

        assertEquals( 3, template.getParts().length );
        assertTrue( template.getParts()[0] instanceof TextPart );
        assertEquals( "Hello, ", ( (TextPart)template.getParts()[0] ).getText() );
        assertTrue( template.getParts()[2] instanceof TextPart );
        assertEquals( "!", ( (TextPart)template.getParts()[2] ).getText() );
        assertTrue( template.getParts()[1] instanceof TextPlaceholder );
        assertEquals( "world", ( (TextPlaceholder)template.getParts()[1] ).getName() );
        assertEquals( "World", ( (TextPlaceholder)template.getParts()[1] ).getDefaultValue() );
    }

    @Test
    public void build__helloDateTime__helloYear() {
        String templateString = "Hello, $datetime(time,yyyy-MM-dd,MY_DEFAULT_VALUE)!";
        Template template = TemplateBuilder.build( templateString );
        String text = template.process( Map.of( "time", LocalDateTime.of( 2021, 1, 1, 0, 0 ) ) );

        assertEquals( "Hello, 2021-01-01!", text );
    }

    @Test
    public void build__escaping__escapedText() {
        String templateString = "Hello, \\$datetime(time,yyyy-MM-dd,MY_DEFAULT_VALUE)!";
        Template template = TemplateBuilder.build( templateString );
        String text = template.process( Map.of( "time", LocalDateTime.of( 2021, 1, 1, 0, 0 ) ) );

        assertEquals( "Hello, $datetime(time,yyyy-MM-dd,MY_DEFAULT_VALUE)!", text );
    }

    @Test
    public void build__unknownPlaceholder__escapedText() {
        String templateString = "Hello, $UNKNOWN_PLACEHOLDER(time,yyyy-MM-dd,MY_DEFAULT_VALUE)!";
        Template template = TemplateBuilder.build( templateString );
        String text = template.process( Map.of( "time", LocalDateTime.of( 2021, 1, 1, 0, 0 ) ) );

        assertEquals( "Hello, $UNKNOWN_PLACEHOLDER(time,yyyy-MM-dd,MY_DEFAULT_VALUE)!", text );
    }
}
