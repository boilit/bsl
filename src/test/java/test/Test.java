package test;

import org.boilit.bsl.IEngine;
import org.boilit.bsl.Engine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String file = "D:\\W04WorkSpace\\Maven001\\Bsl\\src\\test\\java\\test\\bsl.html";

        List<Stock> items = Stock.dummyItems();

        Engine engine = Engine.getEngine();
        engine.setSpecifiedEncoder(true);

        Map<String, Object> model;

        OutputStream outputStream;
        outputStream = new ByteArrayOutputStream(8192);
        model= new HashMap<String, Object>();
        model.put("items", items);
        engine.getTemplate(file).execute(model, outputStream);
        outputStream.close();

        System.out.println(((ByteArrayOutputStream) outputStream).size());

        System.out.write(((ByteArrayOutputStream) outputStream).toByteArray());

        long t1= System.currentTimeMillis();
        for(int i=0;i<10000;i++) {
            outputStream = new ByteArrayOutputStream(512);
            model= new HashMap<String, Object>();
            model.put("items", items);
            engine.getTemplate(file).execute(model, outputStream);
            outputStream.close();
        }
        long t2= System.currentTimeMillis();

        System.out.println();
        System.out.println("times:"+(t2-t1)+"ms");
    }
}
