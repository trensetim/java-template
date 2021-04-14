package com.timtrense.template.example;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.timtrense.template.Template;
import com.timtrense.template.TemplateBuilder;
import com.timtrense.template.TemplateLanguage;
import com.timtrense.template.TemplatePart;

public class CustomLanguageExample {

    public static void main( String[] args ) {
        TemplateBuilder builder = new TemplateBuilder();
        builder.setTemplateLanguage( new MyLanguage() );
        Template helloWorldTemplate = builder.buildTemplate( "Hello, [name]!" );

        String text = helloWorldTemplate.process( Map.of( "name", "Tim" ) );
        System.out.println( text );

        String worldText = builder.buildTemplate( "Hello, [name]!" ).process( Map.of( "unknown_name", "Tim" ) );
        System.out.println( worldText );

        String escapedText = builder.buildTemplate( "Hello, #[escaped]!" ).process( Map.of( "escaped", "Tim" ) );
        System.out.println( escapedText );
    }

    static class MyLanguage implements TemplateLanguage {

        @Override
        public char getEscapeSequence() {
            return '#';
        }

        @Override
        public Pattern getPlaceholderWrapperPattern() {
            return Pattern.compile( "\\[([^]]+)]" );
        }

        @Override
        public TemplatePart compilePlaceholder( Matcher matchedPlaceholder ) {
            String valueName = matchedPlaceholder.group( 1 );
            return ( context ) -> context.getOrDefault( valueName, "World" );
        }
    }
}
