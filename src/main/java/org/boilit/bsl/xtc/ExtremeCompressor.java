package org.boilit.bsl.xtc;

/**
 * @author Boilit
 * @see
 */
public final class ExtremeCompressor implements ITextCompressor {
    @Override
    public final String doCompress(final String value) {
        if(value == null) {
            return null;
        }
        return value.replaceAll(">\\s*[\n\r]+\\s*",">").replaceAll("\\s*[\n\r]+\\s*<","<").replaceAll("[\n\r\t ]+"," ");
    }
}
