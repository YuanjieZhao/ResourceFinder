package ca.ubc.cs.cpsc210.resourcefinder.parser;

import ca.ubc.cs.cpsc210.resourcefinder.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

// Handler for XML resource parsing
public class ResourceHandler extends DefaultHandler {
    private ResourceRegistry registry;
    private Resource resource;
    private StringBuilder accumulator;

    private String address;
    private GeoPoint geoLocation;
    private Double lat;
    private Double lon;
    private URL webAddress;
    private String phoneNumber;

    private String name;
    private ContactInfo contactInfo;
    private Set<Service> services;



    // EFFECTS: constructs resource handler for XML parser
    public ResourceHandler(ResourceRegistry registry) {
        this.registry = registry;
        accumulator = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        System.out.println("StartElement: " + qName);

        if (qName == "resource") {
            name = null;
            address = null;
            lat = lon = null; // if the apparant type of lat & lon is double (a primitive type), then null cannot be assigned to them
            webAddress = null;
            phoneNumber = null;
            services = new HashSet<>(); // after this initialization statement, the services is not null, but its content is null
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        String data = accumulator.toString().trim();   // trim leading and trailing whitespace
        accumulator.setLength(0);                      // clear accumulator

        switch (qName) {
            case "name":
                name = data;
                break;
            case "address":
                address = data;
                break;
            case "lat":
                lat = Double.parseDouble(data);
                // NumberFormatException can be thrown from the Double.parseDouble(), but since it is an unchecked exception,
                // it doesn't have to be caught or declared thrown.
                // If a NumberFormatException is thrown, the pase() will catch it and throw a resourceParsingException
                break;
            case "lon":
                lon = Double.parseDouble(data);
                break;
            case "location":
                geoLocation = new GeoPoint(lat, lon);
                break;
            case "webaddress":
                try {
                    webAddress = new URL(data);
                    break;
                } catch (MalformedURLException e) {
                    throw new SAXException();
                    // can't throw ResourceParsingException because the method being override does not throw this exception
                    // To throw ResourceParsingException (according to the specification of parse() in IResourceParser interface,
                    // we first throw SAXException to the caller of this method, and the caller will throw
                    // the ResourceParsingException after it catches SAXException (refer to parse() defined in
                    // XMLResourceParser class
                }
            case "phone":
                phoneNumber = data;
                break;
            case "service":
                services.add(string2Service(data));
                break;
            case "resource":
                contactInfo = new ContactInfo(address, geoLocation, webAddress, phoneNumber);
                resource = new Resource(name, contactInfo);

                for (Service s : services) {
                    resource.addService(s);
                }

                if (!isAnyFieldOfResourceMissing())
                    registry.addResource(resource);
                System.out.println("Finish parsing a resource");
            default:
                return;
        }
    }

    // EFFECTS: return true if any field of the resource or contactInfo is null
    private boolean isAnyFieldOfResourceMissing() {
        return (address == null) || (lat == null) || (lon == null) ||
                (webAddress == null) || (phoneNumber == null) || (name == null) ||
                (services.isEmpty());
    }


    // EFFECTS: convert a given string of Service into a Service
    private Service string2Service(String nameOfService) {
        switch (nameOfService) {
            case "Shelter":
                return Service.SHELTER;
            case "Food":
                return Service.FOOD;
            case "Counselling":
                return Service.COUNSELLING;
            case "Youth services":
                return Service.YOUTH;
            case "Senior services":
                return Service.SENIOR;
            case "Legal advice":
                return Service.LEGAL;
            default:
                return null;
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        accumulator.append(ch, start, length);
    }
}
