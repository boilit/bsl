package org.boilit.bsl;

import org.boilit.bsl.core.sxs.FragDefine;
import org.boilit.bsl.xio.IResource;

import java.util.concurrent.ConcurrentMap;

/**
 * @author Boilit
 * @see
 */
public final class Fragment extends AbstractTemplate {
    private final ITemplate template;
    private FragDefine fragDefine;

    public Fragment(final ITemplate template) {
        super(template.getEngine());
        this.template = template;
    }

    public ITemplate getTemplate() {
        return template;
    }

    @Override
    public IResource getResource() {
        return null;
    }

    @Override
    public final ConcurrentMap<String, Fragment> getFragments() {
        return null;
    }

    @Override
    public Object execute(final Context context) throws Exception {
        return null;
    }

    public final FragDefine getFragDefine() {
        return fragDefine;
    }

    public final void setFragDefine(final FragDefine fragDefine) {
        this.fragDefine = fragDefine;
    }

    public final void appendToTemplate() {
        this.template.getFragments().put(this.fragDefine.getLabel(), this);
    }
}
