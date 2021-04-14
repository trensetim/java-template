package com.timtrense.template.example;

import java.util.Map;

import com.timtrense.template.Template;
import com.timtrense.template.TemplateBuilder;

public class HelloEnumExample {

    enum CAR_TYPE{
        TRUCK,
        PASSENGER_CAR,
        F1_CAR
    }

    public static void main( String[] args ) {
        Template helloWorldTemplate = TemplateBuilder.build( "I like driving $enum(cartype,SOME_OPTIONAL_DEFAULT)!" );
        String text = helloWorldTemplate.process( Map.of( "cartype", CAR_TYPE.PASSENGER_CAR ) );

        System.out.println( text );
    }
}
