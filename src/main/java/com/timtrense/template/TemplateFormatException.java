package com.timtrense.template;

import lombok.Getter;

public class TemplateFormatException extends IllegalArgumentException {

    @Getter
    private final String templateText;
    @Getter
    private final Integer position;

    public TemplateFormatException( String message, String templateText, Integer position ) {
        super( message );
        this.templateText = templateText;
        this.position = position;
    }

    public TemplateFormatException( String message, String templateText, Integer position, Throwable cause ) {
        super( message, cause );
        this.templateText = templateText;
        this.position = position;
    }

}
