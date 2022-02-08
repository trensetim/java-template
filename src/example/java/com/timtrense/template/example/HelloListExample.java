package com.timtrense.template.example;

import com.timtrense.template.Template;
import com.timtrense.template.TemplateBuilder;

import java.util.List;
import java.util.Map;

public class HelloListExample {

    public static void main(String[] args) {
        Template basketTemplate = TemplateBuilder.build("I have $list(shopping_basket) in my shopping basket.");
        System.out.println(
                basketTemplate.process(Map.of("shopping_basket", List.of("tea", "milk")))
        );
    }
}
