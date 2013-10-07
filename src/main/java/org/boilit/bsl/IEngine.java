package org.boilit.bsl;

import org.boilit.bsl.formatter.FormatterManager;
import org.boilit.bsl.formatter.IFormatter;
import org.boilit.bsl.xio.FileResourceLoader;
import org.boilit.bsl.xio.IResourceLoader;

/**
 * @author Boilit
 * @see
 */
public interface IEngine {

    public String getInputEncoding();

    public void setInputEncoding(String inputEncoding);

    public String getOutputEncoding();

    public void setOutputEncoding(String outputEncoding);

    public boolean isSpecifiedEncoder();

    public void setSpecifiedEncoder(boolean specifiedEncoder);

    public boolean isUseTemplateCache();

    public void setUseTemplateCache(boolean useTemplateCache);

    public IResourceLoader getResourceLoader();

    public void setResourceLoader(IResourceLoader resourceLoader);

    public ITextProcessor getTextProcessor();

    public void setTextProcessor(ITextProcessor textProcessor);

    public IBreakPointer getBreakPointer();

    public void setBreakPointer(IBreakPointer breakPointer);

    public FormatterManager getFormatterManager();

    public IFormatter registerFormatter(Class clazz, IFormatter formatter);

    public void removeTemplateFromCache(String name);

    public void clearTemplateCache();

    public ITemplate getTemplate(String name) throws Exception ;
}
