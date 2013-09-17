package org.boilit.bsl.xtc;

/**
 * @author Boilit
 * @see
 */
public final class EmptyCompressor implements ITextCompressor {
    @Override
    public final String doCompress(final String value) {
        return value;
    }
}
