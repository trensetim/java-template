package com.timtrense.template.example;

import com.timtrense.template.Template;
import com.timtrense.template.TemplateBuilder;

import java.util.Map;

public class HelloEnumExample {

    enum CarType {
        TRUCK,
        PASSENGER_CAR,
        F1_CAR
    }

    public static void main(String[] args) {
        Template helloWorldTemplate = TemplateBuilder.build("I like driving $enum(cartype,SOME_OPTIONAL_DEFAULT)!");
        String text = helloWorldTemplate.process(Map.of("cartype", CarType.PASSENGER_CAR));

        System.out.println(text);
    }
}
