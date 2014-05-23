package org.boilit.bsl;

import org.boilit.bsl.xio.IResourceLoader;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Boilit
 * @see
 */
public final class Engine extends AbstractEngine {
    public static final String PROPERTIES_BSL = "bsl.properties";

    public Engine() {
        super(Thread.currentThread().getContextClassLoader());
    }

    public Engine(final ClassLoader classLoader) {
        super(classLoader);
    }

    public static final Engine getEngine() throws Exception {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return getEngine(classLoader, classLoader.getResourceAsStream(PROPERTIES_BSL));
    }

    public static final Engine getEngine(ClassLoader classLoader) throws Exception {
        return getEngine(classLoader, classLoader.getResourceAsStream(PROPERTIES_BSL));
    }

    public static final Engine getEngine(ClassLoader classLoader, InputStream inputStream) throws Exception {
        Properties properties = new Properties();
        properties.load(inputStream);
        return getEngine(classLoader, properties);
    }

    public static final Engine getEngine(ClassLoader classLoader, Properties properties) throws Exception {
        if (properties == null) {
            return new Engine(classLoader);
        }
        Engine engine = new Engine(classLoader);
        final String inputEncoding = properties.getProperty("inputEncoding");
        if (inputEncoding != null && inputEncoding.trim().length() > 0) {
            if (inputEncoding.trim().equalsIgnoreCase("system")) {
                engine.setInputEncoding(System.getProperty("file.encoding"));
            } else {
                engine.setInputEncoding(inputEncoding.trim());
            }
        }
        final String outputEncoding = properties.getProperty("outputEncoding");
        if (outputEncoding != null && outputEncoding.trim().length() > 0) {
            engine.setOutputEncoding(outputEncoding.trim());
        }
        final String specifiedEncoder = properties.getProperty("specifiedEncoder");
        if (specifiedEncoder != null && specifiedEncoder.trim().length() > 0) {
            engine.setSpecifiedEncoder(Boolean.parseBoolean(specifiedEncoder.trim()));
        }
        final String useTemplateCache = properties.getProperty("useTemplateCache");
        if (useTemplateCache != null && useTemplateCache.trim().length() > 0) {
            engine.setUseTemplateCache(Boolean.parseBoolean(useTemplateCache.trim()));
        }
        final String resourceLoader = properties.getProperty("resourceLoader");
        if (resourceLoader != null && resourceLoader.trim().length() > 0) {
            engine.setResourceLoader((IResourceLoader) classLoader.loadClass(resourceLoader.trim()).newInstance());
        }
        final String textProcessor = properties.getProperty("textProcessor");
        if (textProcessor != null && textProcessor.trim().length() > 0) {
            engine.setTextProcessor((ITextProcessor) classLoader.loadClass(textProcessor.trim()).newInstance());
        }
        final String breakPointer = properties.getProperty("breakPointer");
        if (breakPointer != null && breakPointer.trim().length() > 0) {
            engine.setBreakPointer((IBreakPointer) classLoader.loadClass(breakPointer.trim()).newInstance());
        }
        return engine;
    }
}
