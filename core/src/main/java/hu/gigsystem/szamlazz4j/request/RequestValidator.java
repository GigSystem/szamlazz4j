package hu.gigsystem.szamlazz4j.request;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for validating XML request payloads against XML Schema (XSD) files.
 * <p>
 * This class is designed for static use only and provides thread-safe caching of schema validators
 * to improve performance by reusing compiled schemas.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
public class RequestValidator {

    /**
     * A thread-safe registry of compiled {@link Validator} instances keyed by schema file name.
     */
    private static final ConcurrentHashMap<String, Validator> validators = new ConcurrentHashMap<>(4);

    /**
     * Private constructor to prevent instantiation.
     */
    private RequestValidator() {
        // static use only
    }

    /**
     * Validates the given XML string against the specified XML schema.
     *
     * @param mappedXml  the XML content as a string to validate
     * @param schemaFile the filename of the XML schema (XSD) resource located in the classpath under "/schemas/"
     * @throws RequestValidationException if the XML does not conform to the schema or if validation fails
     */
    public static void validateRequest(String mappedXml, String schemaFile) throws RequestValidationException {
        try {
            Validator validator = getValidator(schemaFile);
            validator.validate(new StreamSource(new StringReader((mappedXml))));
        } catch (Exception e) {
            throw new RequestValidationException("Request failed validation!", e);
        }
    }

    /**
     * Retrieves a cached {@link Validator} instance for the given schema file, or creates and caches
     * a new one if it does not exist yet.
     *
     * @param schemaFile the filename of the XML schema (XSD) resource located in the classpath under "/schemas/"
     * @return a cached or newly created {@link Validator} instance
     * @throws IOException  if there is an error reading the schema resource
     * @throws SAXException if the schema file is invalid or cannot be parsed
     */
    private static Validator getValidator(String schemaFile) throws IOException, SAXException {
        if (validators.containsKey(schemaFile)) {
            return validators.get(schemaFile);
        }

        try (InputStream input = new BufferedInputStream(Objects.requireNonNull(RequestValidator.class.getResourceAsStream("/schemas/" + schemaFile)))) {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource((input)));
            Validator validator = schema.newValidator();
            validators.put(schemaFile, validator);
            return validator;
        }
    }
}
