package org.boilit.bsl;

import org.boilit.bsl.formatter.FormatterManager;
import org.boilit.bsl.formatter.IFormatter;
import org.boilit.bsl.xio.FileResourceLoader;
import org.boilit.bsl.xio.IResourceLoader;
import org.boilit.bsl.xtc.EmptyCompressor;
import org.boilit.bsl.xtc.ITextCompressor;
import org.boilit.logger.DefaultLogger;
import org.boilit.logger.ILogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Boilit
 * @see
 */
public final class Engine {
    public static final String PROPERTIES_BSL = "bsl.properties";
    private ILogger logger;
    private String inputEncoding;
    private String outputEncoding;
    private boolean specifiedEncoder;
    private boolean useTemplateCache;
    private IResourceLoader resourceLoader;
    private ConcurrentMap<String, Template> templateCache;
    private ITextCompressor textCompressor;
    private final FormatterManager formatterManager;

    public Engine() {
        this.logger = new DefaultLogger();
        this.inputEncoding = System.getProperty("file.encoding");
        this.outputEncoding = "UTF-8";
        this.specifiedEncoder = true;
        this.useTemplateCache = true;
        this.textCompressor = new EmptyCompressor();
        this.formatterManager = new FormatterManager();
        this.templateCache = new ConcurrentHashMap<String, Template>();
    }

    public static final Engine getEngine() throws Exception {
        return getEngine(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_BSL));
    }

    public static final Engine getEngine(InputStream inputStream) throws Exception {
        Properties properties = new Properties();
        properties.load(inputStream);
        return getEngine(properties);
    }

    public static final Engine getEngine(Properties properties) throws Exception {
        if (properties == null) {
            return new Engine();
        }
        Engine engine = new Engine();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String logger = properties.getProperty("logger");
        if (logger != null && logger.trim().length() > 0) {
            engine.setLogger((ILogger) classLoader.loadClass(logger).newInstance());
        }
        final String inputEncoding = properties.getProperty("inputEncoding");
        if (inputEncoding != null && inputEncoding.trim().length() > 0) {
            engine.setInputEncoding(inputEncoding);
        }
        final String outputEncoding = properties.getProperty("outputEncoding");
        if (outputEncoding != null && outputEncoding.trim().length() > 0) {
            engine.setOutputEncoding(outputEncoding);
        }
        final String specifiedEncoder = properties.getProperty("specifiedEncoder");
        if (specifiedEncoder != null && specifiedEncoder.trim().length() > 0) {
            engine.setSpecifiedEncoder(Boolean.parseBoolean(specifiedEncoder));
        }
        final String useTemplateCache = properties.getProperty("useTemplateCache");
        if (useTemplateCache != null && useTemplateCache.trim().length() > 0) {
            engine.setUseTemplateCache(Boolean.parseBoolean(useTemplateCache));
        }
        final String textCompressor = properties.getProperty("textCompressor");
        if (textCompressor != null && textCompressor.trim().length() > 0) {
            engine.setTextCompressor((ITextCompressor) classLoader.loadClass(textCompressor).newInstance());
        }
        return engine;
    }

    public final ILogger getLogger() {
        return logger;
    }

    public final void setLogger(final ILogger logger) {
        this.logger = logger == null ? new DefaultLogger() : logger;
    }

    public final String getInputEncoding() {
        return inputEncoding;
    }

    public final void setInputEncoding(final String inputEncoding) {
        this.inputEncoding = inputEncoding;
    }

    public final String getOutputEncoding() {
        return outputEncoding;
    }

    public final void setOutputEncoding(final String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    public final boolean isSpecifiedEncoder() {
        return specifiedEncoder;
    }

    public final void setSpecifiedEncoder(final boolean specifiedEncoder) {
        this.specifiedEncoder = specifiedEncoder;
    }

    public final boolean isUseTemplateCache() {
        return useTemplateCache;
    }

    public final void setUseTemplateCache(final boolean useTemplateCache) {
        this.useTemplateCache = useTemplateCache;
    }

    public final IResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public final void setResourceLoader(final IResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public final ITextCompressor getTextCompressor() {
        return textCompressor;
    }

    public final void setTextCompressor(final ITextCompressor textCompressor) {
        this.textCompressor = textCompressor == null ? new EmptyCompressor() : textCompressor;
    }

    public final IFormatter registerFormatter(final Class clazz, final IFormatter formatter) {
        return formatterManager.add(clazz, formatter);
    }

    public final void clearTemplateCache() {
        templateCache.clear();
    }

    public final Template getTemplate(final String name) {
        IResourceLoader resourceLoader = this.resourceLoader;
        if (resourceLoader == null) {
            resourceLoader = this.resourceLoader = new FileResourceLoader(this.getInputEncoding());
        }
        if (!useTemplateCache) {
            return new Template(this, resourceLoader.getResource(name), formatterManager);
        }
        Template template = templateCache.get(name);
        if (template == null) {
            templateCache.put(name, template = new Template(this, resourceLoader.getResource(name), formatterManager));
        }
        return template;
    }
}
