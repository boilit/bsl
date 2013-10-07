package org.boilit.bsl;

import org.boilit.bsl.encoding.EncoderFactory;
import org.boilit.bsl.encoding.IEncoder;
import org.boilit.bsl.formatter.FormatterManager;
import org.boilit.bsl.xio.BytesPrinter;
import org.boilit.bsl.xio.CharsPrinter;
import org.boilit.bsl.xio.IPrinter;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractTemplate implements ITemplate {
    private final IEngine engine;
    private final Detection detection;

    public AbstractTemplate(final IEngine engine) {
        this.engine = engine;
        this.detection = new Detection();
    }

    @Override
    public final IEngine getEngine() {
        return engine;
    }

    @Override
    public final String getInputEncoding() {
        return engine.getInputEncoding();
    }

    @Override
    public final String getOutputEncoding() {
        return engine.getOutputEncoding();
    }

    @Override
    public final boolean isSpecifiedEncoder() {
        return engine.isSpecifiedEncoder();
    }

    @Override
    public final ITextProcessor getTextProcessor() {
        return engine.getTextProcessor();
    }

    @Override
    public final FormatterManager getFormatterManager() {
        return engine.getFormatterManager();
    }

    @Override
    public final Detection getDetection() {
        return detection;
    }

    public final Object execute(final Map<String, Object> model, final OutputStream outputStream) throws Exception  {
        final IEncoder encoder = EncoderFactory.getEncoder(this.getOutputEncoding(), this.isSpecifiedEncoder());
        return this.execute(model, new BytesPrinter(outputStream, encoder));
    }

    public final Object execute(final Map<String, Object> model, final Writer writer) throws Exception  {
        final IEncoder encoder = EncoderFactory.getEncoder(this.getOutputEncoding(), this.isSpecifiedEncoder());
        return this.execute(model, new CharsPrinter(writer, encoder));
    }

    public final Object execute(final Map<String, Object> model, final IPrinter printer) throws Exception {
        return this.execute(new Context(this.detection, printer, model));
    }
}
