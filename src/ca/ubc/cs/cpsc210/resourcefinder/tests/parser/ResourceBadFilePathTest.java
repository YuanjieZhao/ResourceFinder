package ca.ubc.cs.cpsc210.resourcefinder.tests.parser;

import ca.ubc.cs.cpsc210.resourcefinder.parser.IResourceParser;
import ca.ubc.cs.cpsc210.resourcefinder.parser.ResourceParsingException;
import ca.ubc.cs.cpsc210.resourcefinder.parser.XMLResourceParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

// Unit tests for XMLResourceParser class
public class ResourceBadFilePathTest {
    private static final String FILE_NAME = "./data/fileDoesntExist.xml";
    private IResourceParser resourceParser;

    @Before
    public void runBefore() {
        resourceParser = new XMLResourceParser(FILE_NAME);
    }

    @Test (expected = IOException.class)
    public void testParse() throws ResourceParsingException, IOException {
        resourceParser.parse();
    }
}