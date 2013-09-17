package test;

import org.boilit.bsl.Engine;
import org.boilit.bsl.xio.FileResourceLoader;
import org.boilit.bsl.xtc.EmptyCompressor;
import org.boilit.bsl.xtc.ExtremeCompressor;
import org.boilit.logger.DefaultLogger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public class BslTest {
    public static void main(String[] args) throws Exception {
        String file = "D:\\W04WorkSpace\\Maven001\\Bsl\\src\\test\\java\\test\\bsl.html";

        List<Stock> items = Stock.dummyItems();

        Engine engine = new Engine();
        engine.clearTemplateCache();
        engine.setLogger(new DefaultLogger());
        engine.setInputEncoding(System.getProperty("file.encoding"));
        engine.setOutputEncoding("GBK");
        engine.setSpecifiedEncoder(true);
        engine.setUseTemplateCache(true);
        engine.setResourceLoader(new FileResourceLoader(engine.getInputEncoding()));
        engine.setTextCompressor(new EmptyCompressor());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("date", new Date());
        model.put("items", items);
        engine.getTemplate(file).execute(model, System.out);
    }
}
