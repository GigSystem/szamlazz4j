package hu.gigsystem.szamlazz4j.model.invoice.query.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

/**
 * Represents a postal address, used for billing or shipping purposes.
 * <p>
 * This class is annotated for XML serialization/deserialization using Jackson,
 * following the XML schema used by Számlázz.hu.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
public class Address {

        /**
         * The country of the address.
         * Mapped to the XML element {@code <orszag>}.
         */
        @JacksonXmlProperty(localName = "orszag")
        private String country;

        /**
         * The postal code or ZIP code.
         * Mapped to the XML element {@code <irsz>}.
         */
        @JacksonXmlProperty(localName = "irsz")
        private String postCode;

        /**
         * The name of the city or locality.
         * Mapped to the XML element {@code <telepules>}.
         */
        @JacksonXmlProperty(localName = "telepules")
        private String city;

        /**
         * The full street address (e.g., street name, number, floor, etc.).
         * Mapped to the XML element {@code <cim>}.
         */
        @JacksonXmlProperty(localName = "cim")
        private String address;
}
