package org.boilit.bsl;

import org.boilit.bsl.formatter.FormatterManager;
import org.boilit.bsl.formatter.IFormatter;
import org.boilit.bsl.xio.FileResourceLoader;
import org.boilit.bsl.xio.IResourceLoader;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractEngine implements IEngine {
    private String inputEncoding;
    private String outputEncoding;
    private boolean specifiedEncoder;
    private boolean useTemplateCache;
    private IResourceLoader resourceLoader;
    private ITextProcessor textProcessor;
    private IBreakPointer breakPointer;
    private final FormatterManager formatterManager;
    private final ConcurrentMap<String, ITemplate> templateCache;

    public AbstractEngine() {
        this.inputEncoding = null;
        this.outputEncoding = "UTF-8";
        this.specifiedEncoder = false;
        this.useTemplateCache = true;
        this.formatterManager = new FormatterManager();
        this.templateCache = new ConcurrentHashMap<String, ITemplate>();
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

    public final ITextProcessor getTextProcessor() {
        return textProcessor;
    }

    public final void setTextProcessor(final ITextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    public final IBreakPointer getBreakPointer() {
        return breakPointer;
    }

    public final void setBreakPointer(IBreakPointer breakPointer) {
        this.breakPointer = breakPointer;
    }

    public final FormatterManager getFormatterManager() {
        return formatterManager;
    }

    public final IFormatter registerFormatter(final Class clazz, final IFormatter formatter) {
        return formatterManager.add(clazz, formatter);
    }

    public final void removeTemplateFromCache(final String name) {
        templateCache.remove(name);
    }

    public final void clearTemplateCache() {
        templateCache.clear();
    }

    public final ITemplate getTemplate(final String name) throws Exception {
        IResourceLoader resourceLoader = this.resourceLoader;
        if (resourceLoader == null) {
            resourceLoader = this.resourceLoader = new FileResourceLoader();
        }
        resourceLoader.setEncoding(this.getInputEncoding());
        if (!useTemplateCache) {
            return new Template(this, resourceLoader.getResource(name));
        }
        ITemplate template = templateCache.get(name);
        if (template == null) {
            templateCache.put(name, template = new Template(this, resourceLoader.getResource(name)));
        }
        return template;
    }
}
