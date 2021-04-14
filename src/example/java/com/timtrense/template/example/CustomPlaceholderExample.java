package com.timtrense.template.example;

import java.util.List;
import java.util.Map;

import com.timtrense.template.Context;
import com.timtrense.template.PlaceholderDefinition;
import com.timtrense.template.Template;
import com.timtrense.template.TemplateBuilder;
import com.timtrense.template.lang.std.StandardTemplateLanguage;

public class CustomPlaceholderExample {

    @SuppressWarnings( "unchecked" )
    public static void main( String[] args ) {
        TemplateBuilder builder = new TemplateBuilder();

        ( (StandardTemplateLanguage)builder.getTemplateLanguage() )
                .getPlaceholderDefinitions()
                .put( "myPlaceholder", PlaceholderDefinition.of( context ->
                        String.join( " and ", (List<String>)context.get( "names" ) )
                ) );

        Template helloWorldTemplate = builder.buildTemplate( "Hello, $myPlaceholder()!" );

        Context context = new Context( Map.of( "names", List.of( "Tim", "Vanessa" ) ) );
        String text = helloWorldTemplate.process( context );
        System.out.println( text );
    }

}
