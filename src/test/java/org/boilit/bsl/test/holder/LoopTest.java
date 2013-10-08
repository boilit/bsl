package org.boilit.bsl.test.holder;

import org.boilit.bsl.Engine;
import org.boilit.bsl.IEngine;

/**
 * @author Boilit
 * @see
 */
public class LoopTest {
    public static void main(String[] args) throws Exception {
        String file = "D:\\W04WorkSpace\\Maven001\\Bsl\\src\\test\\java\\test\\loop.html";

        IEngine engine = Engine.getEngine();
        engine.setUseTemplateCache(false);

        engine.getTemplate(file).execute(null, System.out);
    }
}
