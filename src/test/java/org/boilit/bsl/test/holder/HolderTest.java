package org.boilit.bsl.test.holder;

import org.boilit.bsl.IEngine;
import org.boilit.bsl.Engine;
import org.boilit.bsl.formatter.DateFormatter;
import org.boilit.bsl.xio.StringResourceLoader;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public class HolderTest {
    public static void main(String[] args) throws Exception {
        StringResourceLoader loader = new StringResourceLoader();
        IEngine engine = Engine.getEngine();
        engine.setUseTemplateCache(false);
        engine.setResourceLoader(loader);
        engine.registerFormatter(Date.class, new DateFormatter("yyyy-MM-dd"));
        engine.registerFormatter(Date.class, new DateFormatter("yyyy-MM-dd HH:mm:ss"));

        loader.getResources().put("test/holder0.html", "<!--[ var a=2; echo(a+3*4); ]-->");
        loader.getResources().put("test/holder1.html", "<!--[ arg value; ]--> ${value.substring(1)}");
        loader.getResources().put("test/holder2.html", "<!--[ arg value; echo(value); ]-->");
        loader.getResources().put("test/holder3.html", "\\<!--[ ]--> \\${a} <!--[ echo('\\\\]-->'); ]-->");
        loader.getResources().put("test/holder4.html", "<!--[arg date, value; echo(date, 'yyyy-MM-dd HH:mm:ss'); echo('\n'); include('holder1.html', {'value': value.substring(1)});]-->");

        engine.getTemplate("test/holder0.html").execute(null, System.out);
        System.out.println();

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("value", "aaa200");

        engine.getTemplate("test/holder1.html").execute(model, System.out);
        System.out.println();

        engine.getTemplate("test/holder2.html").execute(model, System.out);
        System.out.println();

        engine.getTemplate("test/holder3.html").execute(model, System.out);
        System.out.println();

        model.put("date", new Date());
        engine.getTemplate("test/holder4.html").execute(model, System.out);
    }
}
