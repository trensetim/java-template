package com.timtrense.template.example;

import java.util.Map;

import com.timtrense.template.Context;
import com.timtrense.template.Template;
import com.timtrense.template.TemplateBuilder;

public class HelloWorldExample {

    public static void main( String[] args ) {
        Template helloWorldTemplate = TemplateBuilder.build( "Hello, $(name,World)!" );

        String text = helloWorldTemplate.process( Map.of( "name", "Tim" ) );
        System.out.println( text );

        Context context = new Context();
        context.put( "name", "lazy Tim"::toString );
        String lazyText = helloWorldTemplate.process( context );
        System.out.println( lazyText );
    }
}
