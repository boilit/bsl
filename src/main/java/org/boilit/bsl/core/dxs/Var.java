package org.boilit.bsl.core.dxs;

import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.core.eao.NormalAssign;
import org.boilit.bsl.exception.ExecuteException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Var extends AbstractDirective {
    private NormalAssign[] assigns;
    private List<NormalAssign> children;
    private int labelIndex = -1;

    public Var(final int line, final int column) {
        super(line, column);
        this.children = new ArrayList<NormalAssign>();
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        final NormalAssign[] assigns = this.assigns;
        int labelIndex = this.labelIndex;
        final int n = assigns.length;
        for (int i = 0; i < n; i++) {
            if(labelIndex == -1) {
                labelIndex = context.getVariableIndex(assigns[i].getLabel());
                if (labelIndex != -1) {
                    throw new ExecuteException(this, "Label[" + assigns[i].getLabel() + "] duplicated defined!");
                }
            }
            context.addVariable(assigns[i].getLabel(), null);
            assigns[i].execute(context);
        }
        // terminate label validate
        this.labelIndex = -2;
        return null;
    }

    @Override
    public final Var optimize() throws Exception {
        if(children.size() == 0) {
            return null;
        }
        assigns = new NormalAssign[children.size()];
        children.toArray(assigns);
        children.clear();
        children = null;
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
