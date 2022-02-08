package com.timtrense.template;

import com.timtrense.template.lang.std.TextPlaceholder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TextPlaceholderTest {

    @Test
    public void build__onlyVarname__validPart() {
        Template template = TemplateBuilder.build("$(var)");

        assertEquals(1, template.getParts().length);
        assertInstanceOf(TextPlaceholder.class, template.getParts()[0]);
        assertEquals("var", ((TextPlaceholder) template.getParts()[0]).getName());
        assertNull(((TextPlaceholder) template.getParts()[0]).getDefaultValue());
    }
}
