package com.timtrense.template;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.timtrense.template.lang.std.TextPlaceholder;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@BenchmarkMode( Mode.AverageTime )
@Warmup( iterations = 1, time = 1 )
@Measurement( iterations = 3, time = 5 )
@Fork( 1 )
public class SpeedTest {

    private static final int ITERATION = 5_000_000;

    public static void main( String[] args ) throws IOException {
        org.openjdk.jmh.Main.main( args );
    }

    @Test
    @Benchmark
    public void test_textformat() {
        long start = System.currentTimeMillis();
        for ( int i = 0; i < ITERATION; i++ ) {
            TextPart hello = new TextPart( "Hello, " );
            TextPlaceholder world = new TextPlaceholder( "world", "World" );
            TextPart exclamation = new TextPart( "!" );
            Template template = new Template( new TemplatePart[]{hello, world, exclamation} );
            String result = template.process( Map.of( "world", "Me" ) );
            assertEquals( "Hello, Me!", result );
        }
        long end = System.currentTimeMillis();

        System.out.println( "test_textformat took " + ( end - start ) + "ms" );
    }

    @Test
    @Benchmark
    public void test_textformat_precompiled() {
        Template template = TemplateBuilder.build( "Hello, $(world,World)!" );
        com.timtrense.template.Context context = new com.timtrense.template.Context();
        context.put( "world", "Me" );

        long start = System.currentTimeMillis();
        for ( int i = 0; i < ITERATION; i++ ) {
            String result = template.process( context );
            assertEquals( "Hello, Me!", result );
        }
        long end = System.currentTimeMillis();

        System.out.println( "test_textformat_precompiled took " + ( end - start ) + "ms" );
    }

    @Test
    @Benchmark
    public void test_textformat_compiling() {
        TemplateBuilder builder = new TemplateBuilder();
        com.timtrense.template.Context context = new com.timtrense.template.Context();
        context.put( "world", "Me" );
        long start = System.currentTimeMillis();
        for ( int i = 0; i < ITERATION; i++ ) {
            Template template = builder.buildTemplate( "Hello, $(world,World)!" );
            String result = template.process( context );
            assertEquals( "Hello, Me!", result );
        }
        long end = System.currentTimeMillis();

        System.out.println( "test_textformat_compiling took " + ( end - start ) + "ms" );
    }

    @Test
    @Benchmark
    public void test_textformat_compilingInline() {
        long start = System.currentTimeMillis();
        for ( int i = 0; i < ITERATION; i++ ) {
            Template template = TemplateBuilder.build( "Hello, $(world,World)!" );
            String result = template.process( Map.of( "world", "Me" ) );
            assertEquals( "Hello, Me!", result );
        }
        long end = System.currentTimeMillis();

        System.out.println( "test_textformat_compilingInline took " + ( end - start ) + "ms" );
    }

    @Test
    @Benchmark
    public void test_thymeleaf() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        //resolver.setTemplateMode( TemplateMode.TEXT );
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver( resolver );
        Context context = new Context();
        context.setVariable( "world", "Me" );


        long start = System.currentTimeMillis();
        for ( int i = 0; i < ITERATION; i++ ) {
            String result = engine.process( "SpeedTest.test_thymeleaf.template", context );
            assertEquals( "Hello, Me!", result );
        }
        long end = System.currentTimeMillis();

        System.out.println( "test_thymeleaf took " + ( end - start ) + "ms" );
    }
}
