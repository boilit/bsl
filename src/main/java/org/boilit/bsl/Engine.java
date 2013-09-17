package org.boilit.bsl;

import org.boilit.bsl.formatter.FormatterManager;
import org.boilit.bsl.formatter.IFormatter;
import org.boilit.bsl.xio.FileResourceLoader;
import org.boilit.bsl.xio.IResourceLoader;
import org.boilit.bsl.xtc.EmptyCompressor;
import org.boilit.bsl.xtc.ITextCompressor;
import org.boilit.logger.DefaultLogger;
import org.boilit.logger.ILogger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Boilit
 * @see
 */
public final class Engine {
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
        this.templateCache = new ConcurrentHashMap<String, Template>();
        this.textCompressor = new EmptyCompressor();
        this.formatterManager = new FormatterManager();
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
