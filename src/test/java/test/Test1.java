package test;

import org.boilit.bsl.Engine;
import org.boilit.bsl.xio.FileResourceLoader;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        String file = "D:\\W04WorkSpace\\Maven001\\Bsl\\src\\test\\java\\test\\test1.html";

        Engine engine = Engine.getEngine();
        engine.getTemplateCache().clear();
        engine.setInputEncoding(System.getProperty("file.encoding"));
        engine.setOutputEncoding("UTF-8");
        engine.setSpecifiedEncoder(true);
        engine.setUseTemplateCache(true);
        engine.setResourceLoader(new FileResourceLoader());
        engine.setTextProcessor(null);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("d", null);
        engine.getTemplate(file).execute(model, System.out);
    }
}
