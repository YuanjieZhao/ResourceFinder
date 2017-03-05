package ca.ubc.cs.cpsc210.resourcefinder.parser;

import ca.ubc.cs.cpsc210.resourcefinder.model.ResourceRegistry;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// Handler for XML resource parsing
public class ResourceHandler extends DefaultHandler {
    private ResourceRegistry registry;
    private StringBuilder accumulator;

    // EFFECTS: constructs resource handler for XML parser
    public ResourceHandler(ResourceRegistry registry) {
        this.registry = registry;
        accumulator = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        // TODO: implement rest of this method
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        // TODO: implement this method

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        accumulator.append(ch, start, length);
    }
}
