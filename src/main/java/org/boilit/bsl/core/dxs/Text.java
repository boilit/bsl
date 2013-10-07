package org.boilit.bsl.core.dxs;

import org.boilit.bsl.ITextProcessor;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.core.AbstractStatement;
import org.boilit.bsl.Context;
import org.boilit.bsl.xio.IPrinter;

/**
 * @author Boilit
 * @see
 */
public final class Text extends AbstractDirective {
    private String value;
    private byte[] bytes;

    public Text(final int line, final int column, final String value, final ITemplate template) {
        super(line, column, template);
        this.value = value;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final IPrinter printer = context.getPrinter();
        if(printer.getPrinterKind() == IPrinter.BYTES_PRINTER) {
            printer.print(bytes);
        } else {
            printer.print(value);
        }
        return null;
    }

    @Override
    public final AbstractDirective optimize() throws Exception {
        if (value == null || (value).trim().length() == 0) {
            return null;
        }
        final ITextProcessor processor = this.getTemplate().getTextProcessor();
        if(processor != null) {
            value = processor.process(value);
        }
        if(value==null || (value).trim().length() == 0) {
            return null;
        }
        bytes = value.getBytes(this.getTemplate().getOutputEncoding());
        return this;
    }

    @Override
    public final AbstractStatement detect() throws Exception {
        return this;
    }
}
