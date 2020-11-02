package base.tests;

import base.XML.SAXParserExample;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XMLParsersTest {

    @Test
    public void testSAXParser1() {
        SAXParserExample saxParser = null;
        try {
            saxParser = new SAXParserExample();
            saxParser.run1();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSAXParser2() {
        SAXParserExample saxParser = null;
        try {
            saxParser = new SAXParserExample();
            saxParser.run2();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}