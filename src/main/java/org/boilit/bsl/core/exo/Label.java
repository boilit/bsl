package org.boilit.bsl.core.exo;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractStructure;
import org.boilit.bsl.Detection;
import org.boilit.bsl.Context;
import org.boilit.bsl.exception.DetectException;

/**
 * @author Boilit
 * @see
 */
public final class Label extends AbstractStructure {
    private final String label;
    private int labelMark;

    public Label(final int line, final int column, final String label, final ITemplate template) {
        super(line, column, template);
        this.label = label;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        return context.getVariable(labelMark);
    }

    @Override
    public final Label detect() throws Exception {
        final Detection detection = this.getTemplate().getDetection();
        labelMark = detection.getVarIndex(label);
        if(labelMark == -1) {
            throw new DetectException(this, "Label[" + label + "] hasn't defined!");
        }
        return this;
    }
}
