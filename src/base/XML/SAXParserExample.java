package base.XML;

import model.Employee;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SAXParserExample {
    private ArrayList<Employee> employees = new ArrayList<>();
    private SAXParserFactory factory;
    private SAXParser parser;

    public SAXParserExample() throws ParserConfigurationException, SAXException {
        factory = SAXParserFactory.newInstance();
        parser = factory.newSAXParser();
    }

    public String getResult() {
        StringBuilder sb = new StringBuilder();
        employees.forEach(x -> sb.append(String.format("[name=%s, job=%s] \n", x.getName(), x.getJob())));
        return String.valueOf(sb);
    }

    public void run1() throws ParserConfigurationException, SAXException, IOException {
        XMLHandler1 handler = new XMLHandler1();
        parser.parse(new File("src/resources/xml/file1.xml"), handler);
        System.out.println(getResult());
    }

    public void run2() throws ParserConfigurationException, SAXException, IOException {
        XMLHandler2 handler = new XMLHandler2();
        parser.parse(new File("src/resources/xml/file2.xml"), handler);
        System.out.println(getResult());
    }



    private static class ExampleXMLHandler extends DefaultHandler {
        @Override
        public void startDocument() throws SAXException {
            // Тут будет логика реакции на начало документа
        }

        @Override
        public void endDocument() throws SAXException {
            // Тут будет логика реакции на конец документа
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            // Тут будет логика реакции на начало элемента
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            // Тут будет логика реакции на конец элемента
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            // Тут будет логика реакции на текст между элементами
        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
            // Тут будет логика реакции на пустое пространство внутри элементов (пробелы, переносы строчек и так далее).

        }
    }

    private class XMLHandler1 extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("employee")) {
                String name = attributes.getValue("name");
                String job = attributes.getValue("job");
                employees.add(new Employee(name, job));
            }
        }
    }


    private class XMLHandler2 extends DefaultHandler {
        private String name, job, lastElementName;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            lastElementName = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();

            if (!information.isEmpty()) {
                if (lastElementName.equals("name"))
                    name = information;
                if (lastElementName.equals("job"))
                    job = information;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ( (name != null && !name.isEmpty()) && (job != null && !job.isEmpty()) ) {
                employees.add(new Employee(name, job));
                name = null;
                job = null;
            }
        }
    }

}
