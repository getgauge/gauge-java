package com.thoughtworks.gauge.execution.parameters.parsers.base;

import com.thoughtworks.gauge.GaugeConstant;
import com.thoughtworks.gauge.execution.parameters.ParsingException;
import com.thoughtworks.gauge.execution.parameters.parsers.converters.TableConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.types.EnumParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.types.PrimitiveParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.types.PrimitivesConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.types.TableParameterParser;
import gauge.messages.Spec.Parameter;
import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.annotation.Nullable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ParameterParsingChain implements ParameterParser {

    private List<ParameterParser> chain = new LinkedList<>();

    public ParameterParsingChain() {
        createReflections().getSubTypesOf(CustomParameterParser.class).stream()
                .map(this::asCustomParameterParser)
                .filter(Objects::nonNull)
                .forEach(chain::add);
        chain.add(new TableParameterParser(new TableConverter()));
        chain.add(new EnumParameterParser());
        chain.add(new PrimitiveParameterParser(new PrimitivesConverter()));
    }

    private Reflections createReflections() {
        Configuration config = new ConfigurationBuilder()
                .setScanners(new SubTypesScanner())
                .addUrls(getUrls())
                .filterInputsBy(new FilterBuilder().include(".+\\.class"));
        return new Reflections(config);
    }

    private @Nullable
    ParameterParser asCustomParameterParser(Class<? extends ParameterParser> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // currently there seems to be no logging system used, so we cannot warn the user about an error
            return null;
        }
    }


    @Override
    public boolean canParse(Class<?> parameterType, Parameter parameter) {
        return true;
    }

    public Object parse(Class<?> parameterType, Parameter parameter) throws ParsingException {
        for (ParameterParser parser : chain) {
            if (parser.canParse(parameterType, parameter)) {
                return parser.parse(parameterType, parameter);
            }
        }
        return parameter.getValue();
    }

    private Collection<URL> getUrls() {
        final String packagesToScan = System.getenv(GaugeConstant.PACKAGE_TO_SCAN);
        if (packagesToScan != null) {
            Collection<URL> urls = new ArrayList<>();
            final List<String> packages = Arrays.asList(packagesToScan.split(","));
            for (String packageToScan : packages) {
                urls.addAll(ClasspathHelper.forPackage(packageToScan));
            }
            return urls;
        }
        return ClasspathHelper.forJavaClassPath();
    }

}
