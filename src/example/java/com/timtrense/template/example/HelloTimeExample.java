package com.timtrense.template.example;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Map;

import com.timtrense.template.Template;
import com.timtrense.template.TemplateBuilder;

public class HelloTimeExample {

    public static void main( String[] args ) {
        Template isoTime = TemplateBuilder.build( "Hello, $datetime(time)!" );
        Template customTime = TemplateBuilder.build( "Hello, $datetime(time,dd.MM.yyyy HH:mm Z)!" );
        TemporalAccessor timeValue = Math.random() > .5 ? OffsetDateTime.now() : ZonedDateTime.now();
        String isoText = isoTime.process( Map.of( "time", timeValue ) );
        String customText = customTime.process( Map.of( "time", timeValue ) );

        System.out.println( isoText );
        System.out.println( customText );
    }
}
