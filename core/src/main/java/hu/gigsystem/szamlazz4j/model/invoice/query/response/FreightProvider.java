package hu.gigsystem.szamlazz4j.model.invoice.query.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

/**
 * Represents a freight provider, including identification, address, tax, and banking information.
 * <p>
 * This class is annotated for XML serialization/deserialization based on the Számlázz.hu XML schema.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
public class FreightProvider {

    /**
     * Unique identifier for the freight provider.
     * Mapped to the XML element {@code <id>}.
     */
    @JacksonXmlProperty(localName = "id")
    private String id;

    /**
     * Name of the freight provider (e.g., carrier company name).
     * Mapped to the XML element {@code <nev>}.
     */
    @JacksonXmlProperty(localName = "nev")
    private String name;

    /**
     * The address of the freight provider.
     * Mapped to the XML element {@code <cim>}.
     */
    @JacksonXmlProperty(localName = "cim")
    private Address address;

    /**
     * The domestic tax number of the provider.
     * Mapped to the XML element {@code <adoszam>}.
     */
    @JacksonXmlProperty(localName = "adoszam")
    private String taxNumber;

    /**
     * The EU tax number of the provider (if applicable).
     * Mapped to the XML element {@code <adoszameu>}.
     */
    @JacksonXmlProperty(localName = "adoszameu")
    private String taxNumberEu;

    /**
     * Banking details of the freight provider.
     * Mapped to the XML element {@code <bank>}.
     */
    @JacksonXmlProperty(localName = "bank")
    private Bank bank;

    /**
     * Represents the bank account details of a freight provider.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 3rd of July, 2025
     */
    @Getter
    public static class Bank {

        /**
         * The name of the bank.
         * Mapped to the XML element {@code <nev>}.
         */
        @JacksonXmlProperty(localName = "nev")
        private String name;

        /**
         * The provider's bank account number.
         * Mapped to the XML element {@code <bankszamla>}.
         */
        @JacksonXmlProperty(localName = "bankszamla")
        private String bankNumber;
    }
}
