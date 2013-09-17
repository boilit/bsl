package org.boilit.bsl.xtc;

/**
 * @author Boilit
 * @see
 */
public final class GeneralCompressor implements ITextCompressor {
    @Override
    public final String doCompress(final String value) {
        if(value == null) {
            return null;
        }
        return value.replaceAll("[\n\r]+[\t ]*[\n\r]","\n\r").replaceAll("^[\n\r][\t ]*","");
    }
}
