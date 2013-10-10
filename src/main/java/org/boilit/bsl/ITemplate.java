package org.boilit.bsl;

import org.boilit.bsl.encoding.EncoderFactory;
import org.boilit.bsl.encoding.IEncoder;
import org.boilit.bsl.exception.ScriptException;
import org.boilit.bsl.formatter.FormatterManager;
import org.boilit.bsl.xio.BytesPrinter;
import org.boilit.bsl.xio.CharsPrinter;
import org.boilit.bsl.xio.IPrinter;
import org.boilit.bsl.xio.IResource;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Boilit
 * @see
 */
public interface ITemplate {

    public IEngine getEngine();

    public ITemplate getTemplate();

    public IResource getResource();

    public String getInputEncoding();

    public String getOutputEncoding();

    public boolean isSpecifiedEncoder();

    public ITextProcessor getTextProcessor();

    public FormatterManager getFormatterManager();

    public Detection getDetection();

    public ConcurrentMap<String, Fragment> getFragments();

    public ClassLoader getClassLoader();

    public Object execute(Map<String, Object> model, OutputStream outputStream) throws Exception;

    public Object execute(Map<String, Object> model, Writer writer) throws Exception;

    public Object execute(Map<String, Object> model, IPrinter printer) throws Exception ;

    public Object execute(Context context) throws Exception;
}
