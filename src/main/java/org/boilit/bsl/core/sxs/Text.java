package org.boilit.bsl.core.sxs;

import org.boilit.bsl.Template;
import org.boilit.bsl.core.AbstractStatement;
import org.boilit.bsl.core.ExecuteContext;

/**
 * @author Boilit
 * @see
 */
public final class Text extends AbstractStatement {
    private Object value;
    private final Template template;

    public Text(final int line, final int column, final String value, final Template template) {
        super(line, column);
        this.value = value;
        this.template = template;
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        context.getPrinter().print(value);
        return null;
    }

    @Override
    public final AbstractStatement optimize() throws Exception {
        if (value == null || ((String) value).trim().length() == 0) {
            return null;
        }
        value = template.getTextCompressor().doCompress((String) value);
        if(value==null || ((String) value).trim().length() == 0) {
            return null;
        }
        value = ((String) value).getBytes(template.getOutputEncoding());
        return this;
    }
}
