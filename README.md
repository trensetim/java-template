# java-template

A minimal, easy to use, extreme high performance Template Engine for Java with a minimal footprint

[![Build Status](https://jenkins.timtrense.com/buildStatus/icon?job=java-template)](https://jenkins.timtrense.com/job/java-template/)
[![Quality Gate Status](https://sonarqube.timtrense.com/api/project_badges/measure?project=com.timtrense%3Ajava-template&metric=alert_status)](https://sonarqube.timtrense.com/dashboard?id=com.timtrense%3Ajava-template)

Current Version: 1.0-SNAPSHOT

# Use Case

With many other templating engines at your hand, why choose this one?

- It is way smaller and requires way less configuration (namely none) than, say thymeleaf.
- It is intended to be used for very small templates on enormous throughput with just basic put-a-value-here logic (although you may build more complex things with custom placeholder types).
- Its syntax is configurable to your hearts content.
- Compared to `String.replace` its (a) blazing fast and (b) more expressive in code.

# Features

- Blazing fast template processing (around 16k hello world templates per millisecond in my local single-threaded benchmarks)
- Builds precompiled templates as a java object structure
- Ease to use template language
- Configurable template language (if you prefer another syntactic flavor)
- A `Template` is thread safe to process

# Examples

You can both use an inlined syntax, giving you adhoc-templating:

```
Template.process(
    "Hello, $(name, World)!",
    Map.of("name","Tim")
)
```

Or you may want to build a `Template` before using it multiple times (which gives you a significant performance boost).

```
Template helloWorldTemplate=TemplateBuilder.build("I am precompiled but executed at $datetime(time, yyyy-MM-dd, some unknown time)!");
String resultNow=helloWorldTemplate.process(Map.of("time",ZonedDateTime.now()));
// ... take some coffee
String resultLater=helloWorldTemplate.process(Map.of("time",ZonedDateTime.now()));
```

As you can see, you may provide a default value if some placeholders are left blank. But you can omit that, auto-filling the placeholder with its name. Also the format for times and dates may be
specified (falling back to ISO format if omitted).

There are some types of placeholders:

- `$(myVar, myDefaultValue)` giving you just the String value of the variable
- `$datetime(myVar, myFormat, myDefaultValue)` giving you just the formatted value
- `$enum(myVar, myDefaultValue)` giving you the enums name
- `$myCustomPlaceholderDefinition(whatever content i like)` can be implemented by you, see `PlaceholderDefinition`

For examples on that, see src/example/.../CustomPlaceholderExample

If you like to customize the locale settings, you may choose a slightly more verbose way (showing you lazy evaluation here too).

```
Context context=new Context(Locale.GERMANY,TimeZone.getTimeZone(ZoneId.of("Europe/Berlin")));
context.put("name","lazy Tim"::toString);
String lazyText=helloWorldTemplate.process(context);
```

# Benchmarks

Some benchmarking results can be found in benchmark.txt and reproduced from src/test/.../SpeedTest using JMH. Here is the key part:

```
Each test case processes a hello world template (as seen above) 5 million times.
Below are the average times for that:

Benchmark                                  Mode  Cnt  Score   Error  Units
SpeedTest.test_textformat_compiling        avgt    3  2,192 ± 0,158   s/op
SpeedTest.test_textformat_precompiled      avgt    3  0,299 ± 0,053   s/op
SpeedTest.test_thymeleaf                   avgt    3  4,860 ± 0,241   s/op
```

# Installing

You may get the library artifact from my maven server:

```
<repositories>
    <repository>
        <id>nexus.timtrense.com</id>
        <name>nexus.timtrense.com</name>
        <url>https://nexus.timtrense.com/repository/com.timtrense-SNAPSHOT</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.timtremse</groupId>
    <artifactId>java-template</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

Or for gradle:

```groovy
repositories {
    maven {
        url 'https://nexus.timtrense.com/repository/com.timtrense-SNAPSHOT'
    }
}

dependencies {
    compile group: 'com.timtrense', name: 'java-template', version: '1.0-SNAPSHOT'
}
```