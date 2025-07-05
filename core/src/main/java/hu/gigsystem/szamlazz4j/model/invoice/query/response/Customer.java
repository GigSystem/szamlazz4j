package hu.gigsystem.szamlazz4j.model.invoice.query.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

/**
 * Represents a customer entity used in invoicing, including contact, address, and ledger information.
 * <p>
 * This class is structured for XML serialization/deserialization with the Számlázz.hu XML schema.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
public class Customer {

    /**
     * The unique identifier of the customer.
     * Mapped to the XML element {@code <id>}.
     */
    @JacksonXmlProperty(localName = "id")
    private String id;

    /**
     * The full name or company name of the customer.
     * Mapped to the XML element {@code <nev>}.
     */
    @JacksonXmlProperty(localName = "nev")
    private String name;

    /**
     * The postal address of the customer.
     * Mapped to the XML element {@code <cim>}.
     */
    @JacksonXmlProperty(localName = "cim")
    private Address address;

    /**
     * The customer's email address.
     * Mapped to the XML element {@code <email>}.
     */
    @JacksonXmlProperty(localName = "email")
    private String email;

    /**
     * The customer's tax number.
     * Mapped to the XML element {@code <adoszam>}.
     */
    @JacksonXmlProperty(localName = "adoszam")
    private String taxNumber;

    /**
     * Ledger-related information for accounting integration.
     * Mapped to the XML element {@code <fokonyv>}.
     */
    @JacksonXmlProperty(localName = "fokonyv")
    private Ledger ledger;

    /**
     * Represents ledger details related to the customer, typically used for accounting systems.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 3rd of July, 2025
     */
    @Getter
    public static class Ledger {

        /**
         * The customer's name or code used in the accounting system.
         * Mapped to the XML element {@code <vevo>}.
         */
        @JacksonXmlProperty(localName = "vevo")
        private String customer;

        /**
         * A unique identifier used in the accounting system for the customer.
         * Mapped to the XML element {@code <vevoazon>}.
         */
        @JacksonXmlProperty(localName = "vevoazon")
        private String customerId;
    }
}
