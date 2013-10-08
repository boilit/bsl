package org.boilit.bsl.core.dxs;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.Detection;
import org.boilit.bsl.Context;
import org.boilit.bsl.core.eao.NormalAssign;
import org.boilit.bsl.exception.DetectException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Var extends AbstractDirective {
    private int[] variableMarks;
    private NormalAssign[] assigns;
    private List<NormalAssign> children;

    public Var(final int line, final int column, final ITemplate template) {
        super(line, column, template);
        this.children = new ArrayList<NormalAssign>();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final int[] variableMarks = this.variableMarks;
        final NormalAssign[] assigns = this.assigns;
        for (int i = assigns.length - 1; i >= 0; i--) {
            context.setVariable(assigns[i].getLabel(), variableMarks[i], null);
            assigns[i].execute(context);
        }
        return null;
    }

    @Override
    public final Var optimize() throws Exception {
        if (children.size() == 0) {
            return null;
        }
        Collections.reverse(children);
        variableMarks = new int[children.size()];
        assigns = new NormalAssign[children.size()];
        children.toArray(assigns);
        children.clear();
        children = null;
        return this;
    }

    @Override
    public final Var detect() throws Exception {
        final Detection detection = this.getTemplate().getDetection();
        final NormalAssign[] assigns = this.assigns;
        for (int i = assigns.length - 1; i >= 0; i--) {
            if (assigns[i] == null) {
                continue;
            }
            variableMarks[i] = detection.getVarIndex(assigns[i].getLabel());
            if (variableMarks[i] != -1) {
                throw new DetectException(this, "Label[" + assigns[i].getLabel() + "] duplicated defined!");
            }
            detection.addVariable(assigns[i].getLabel());
            variableMarks[i] = detection.getVarIndex(assigns[i].getLabel());
            assigns[i].detect();
        }
        return this;
    }

    public final Var add(NormalAssign assign) throws Exception {
        if ((assign = assign.optimize()) == null) {
            return this;
        }
        this.children.add(assign);
        return this;
    }
}
