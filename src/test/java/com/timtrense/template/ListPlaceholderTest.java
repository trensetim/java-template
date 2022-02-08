package com.timtrense.template;

import com.timtrense.template.lang.std.ListPlaceholder;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListPlaceholderTest {

    @Test
    public void process__emptyList__onlyPrefixSuffix() {
        TemplatePart list = new ListPlaceholder("var", "default", "COMMA", "PRE", "SUF");
        Template template = new Template(new TemplatePart[]{list});

        String result = template.process(Map.of("var", Collections.emptyList()));

        assertEquals("PRESUF", result);
    }

    @Test
    public void process__singleItemList__noComma() {
        TemplatePart list = new ListPlaceholder("var", "default", "COMMA", "PRE", "SUF");
        Template template = new Template(new TemplatePart[]{list});

        String result = template.process(Map.of("var", List.of(1)));

        assertEquals("PRE1SUF", result);
    }

    @Test
    public void process__doubleItemList__commaInBetween() {
        TemplatePart list = new ListPlaceholder("var", "default", "COMMA", "PRE", "SUF");
        Template template = new Template(new TemplatePart[]{list});

        String result = template.process(Map.of("var", List.of(1, 2)));

        assertEquals("PRE1COMMA2SUF", result);
    }

    @Test
    public void process__doubleItemStream__commaInBetween() {
        TemplatePart list = new ListPlaceholder("var", "default", "COMMA", "PRE", "SUF");
        Template template = new Template(new TemplatePart[]{list});

        String result = template.process(Map.of("var", Stream.of(1, "hello")));

        assertEquals("PRE1COMMAhelloSUF", result);
    }

    @Test
    public void process__doubleItemArray__commaInBetween() {
        TemplatePart list = new ListPlaceholder("var", "default", "COMMA", "PRE", "SUF");
        Template template = new Template(new TemplatePart[]{list});

        String result = template.process(Map.of("var", new int[]{1, 2}));

        assertEquals("PRE1COMMA2SUF", result);
    }

    @Test
    public void build__justVarname__validTemplate() {
        String result = Template.process("$list(var)", Map.of("var", List.of(1)));
        assertEquals("[1]", result);
    }

    @Test
    public void build__withDefault__givesDefaultOnNullValue() {
        String result = Template.process("$list(var,DEFAULT)", Collections.emptyMap());
        assertEquals("DEFAULT", result);
    }

    @Test
    public void build__withEmptyDelimiter__delimitsByComma() {
        String result = Template.process("$list(var,DEFAULT,,{,})", Map.of("var", List.of(1, 2)));
        assertEquals("{1,2}", result);
    }

}
