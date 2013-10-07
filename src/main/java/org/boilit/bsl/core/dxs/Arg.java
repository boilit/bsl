package org.boilit.bsl.core.dxs;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.Context;
import org.boilit.bsl.Detection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Arg extends AbstractDirective {
    private String[] labels;
    private List<String> children;

    public Arg(final int line, final int column, final ITemplate template) {
        super(line, column, template);
        this.children = new ArrayList<String>();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        return null;
    }

    @Override
    public final Arg optimize() throws Exception {
        if (children.size() == 0) {
            return null;
        }
        labels = new String[children.size()];
        children.toArray(labels);
        children.clear();
        children = null;
        return this;
    }

    @Override
    public final Arg detect() throws Exception {
        final Detection detection = this.getTemplate().getDetection();
        final String[] labels = this.labels;
        for (int i = 0, n = labels.length; i < n; i++) {
            detection.addArgument(labels[i]);
        }
        return this;
    }

    public final Arg add(final String label) throws Exception {
        if (label == null || label.trim().length() == 0) {
            return this;
        }
        this.children.add(label);
        return this;
    }
}
