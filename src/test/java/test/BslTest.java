package test;

import org.boilit.bsl.IEngine;
import org.boilit.bsl.Engine;

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
        String file = "D:\\W04WorkSpace\\Maven001\\Bsl\\src\\test\\java\\test\\bsl1.html";

        List<Stock> items = Stock.dummyItems();

        IEngine engine = Engine.getEngine();
//        engine.clearTemplateCache();
//        engine.setInputEncoding(System.getProperty("file.encoding"));
//        engine.setOutputEncoding("UTF-8");
//        engine.setSpecifiedEncoder(true);
//        engine.setUseTemplateCache(true);
//        engine.setResourceLoader(new FileResourceLoader().setEncoding(engine.getInputEncoding()));
//        engine.setTextProcessor(new DefaultTextProcessor());
        String[][] values = new String[][]{new String[]{"abcd"}, new String[]{"efgh"}};
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("date", new Date());
        model.put("items", items);
        model.put("name", "MyName");
        model.put("value", "MyValue");
        model.put("values", values);
        engine.getTemplate(file).execute(model, System.out);
    }
}
