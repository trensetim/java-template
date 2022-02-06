package com.timtrense.template.example;

import com.timtrense.template.Context;
import com.timtrense.template.Template;
import com.timtrense.template.TemplateBuilder;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;

public class HelloWorldExample {

    public static void main(String[] args) {
        System.out.println(Template.process("Hello, $(name, World)!", Map.of("name", "Tim")));

        Template helloWorldTemplate = TemplateBuilder.build("Hello, precompiled $(name,World)!");
        String text = helloWorldTemplate.process(Map.of("name", "Tim"));
        System.out.println(text);

        Context context = new Context(Locale.GERMANY, ZoneId.of("Europe/Berlin"));
        context.put("name", "lazy Tim"::toString);
        String lazyText = helloWorldTemplate.process(context);
        System.out.println(lazyText);
    }
}
