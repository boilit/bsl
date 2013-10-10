package org.boilit.bsl;

import org.boilit.bsl.formatter.FormatterManager;
import org.boilit.bsl.formatter.IFormatter;
import org.boilit.bsl.xio.IResourceLoader;

import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public interface IEngine {

    public ClassLoader getClassLoader();

    public String getInputEncoding();

    public String getOutputEncoding();

    public boolean isSpecifiedEncoder();

    public boolean isUseTemplateCache();

    public IResourceLoader getResourceLoader();

    public ITextProcessor getTextProcessor();

    public IBreakPointer getBreakPointer();

    public IFormatter registerFormatter(Class clazz, IFormatter formatter);

    public FormatterManager getFormatterManager();

    public Map<String, ITemplate> getTemplateCache();

    public ITemplate getTemplate(String name) throws Exception ;
}
