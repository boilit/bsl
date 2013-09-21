package org.boilit.bsl.core.sxs;

import org.boilit.bsl.Template;
import org.boilit.bsl.core.AbstractStatement;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.xio.IPrinter;

/**
 * @author Boilit
 * @see
 */
public final class Text extends AbstractStatement {
    private String value;
    private byte[] bytes;
    private final Template template;

    public Text(final int line, final int column, final String value, final Template template) {
        super(line, column);
        this.value = value;
        this.template = template;
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        final IPrinter printer = context.getPrinter();
        if(printer.getPrinterKind() == IPrinter.BYTES_PRINTER) {
            printer.print(bytes);
        } else {
            printer.print(value);
        }
        return null;
    }

    @Override
    public final AbstractStatement optimize() throws Exception {
        if (value == null || (value).trim().length() == 0) {
            return null;
        }
        value = template.getTextCompressor().doCompress(value);
        if(value==null || (value).trim().length() == 0) {
            return null;
        }
        bytes = value.getBytes(template.getOutputEncoding());
        return this;
    }
}
