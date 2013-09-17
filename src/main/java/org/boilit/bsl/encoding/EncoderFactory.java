package org.boilit.bsl.encoding;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class EncoderFactory {
    private static Map<String, IEncoder> encoderMap = new HashMap<String, IEncoder>();

    public static final IEncoder getEncoder(final String encoding, final boolean specified) {
        if (encoding == null) {
            return null;
        }
        String encode = encoding.toUpperCase();
        IEncoder encoder = encoderMap.get(encode);
        if (encoder != null) {
            return encoder;
        }
        if (!specified) {
            encoder = new DefaultEncoder(encode);
        } else if ("UTF-8".equals(encode)) {
            encoder = new UTF8Encoder(encode);
        } else if ("GBK".equals(encode)) {
            encoder = new GBKEncoder(encode);
        } else if ("ISO8859-1".equals(encode)) {
            encoder = new Latin1Encoder(encode);
        } else {
            encoder = new DefaultEncoder(encode);
        }
        encoderMap.put(encode, encoder);
        return encoder;
    }
}
