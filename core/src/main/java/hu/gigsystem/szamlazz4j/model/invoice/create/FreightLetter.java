package hu.gigsystem.szamlazz4j.model.invoice.create;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import hu.gigsystem.szamlazz4j.model.invoice.enums.FreightProvider;
import lombok.Builder;

/**
 * Represents a freight letter containing shipment details for various freight providers.
 * <p>
 * This class can serialize/deserialize to/from XML in accordance with the Számlázz.hu XML schema.
 * It supports multiple freight provider types (e.g., Trans-o-flex, Sprinter, PPP, MPL).
 * The {@code @Builder} annotation allows for fluent object creation.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 5th of July, 2025
 */
@Builder
public class FreightLetter {

    /**
     * The selected freight provider. Defaults to {@link FreightProvider#EMPTY} if not set.
     */
    @Builder.Default
    @JacksonXmlProperty(localName = "azonosito", namespace = "http://www.szamlazz.hu/xmlszamla")
    private FreightProvider freightProvider = FreightProvider.EMPTY;

    /**
     * The barcode associated with the shipment.
     */
    @JacksonXmlProperty(localName = "vonalkod", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String barcode;

    /**
     * An optional note or comment associated with the freight letter.
     */
    @JacksonXmlProperty(localName = "megjegyzes", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String note;

    /**
     * Trans-o-flex shipping-specific data.
     */
    @JacksonXmlProperty(localName = "tof", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Transoflex transoflexData;

    /**
     * PPP-specific shipping data.
     */
    @JacksonXmlProperty(localName = "ppp", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Ppp pppData;

    /**
     * Sprinter-specific shipping data.
     */
    @JacksonXmlProperty(localName = "sprinter", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Sprinter sprinterData;

    /**
     * MPL-specific shipping data.
     */
    @JacksonXmlProperty(localName = "mpl", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Mpl mplData;

    /**
     * Contains shipping information specific to the Trans-o-flex provider.
     */
    @Builder
    public static class Transoflex {
        /** Provider identifier. */
        @JacksonXmlProperty(localName = "azonosito", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String id;

        /** Unique shipment ID. */
        @JacksonXmlProperty(localName = "shipmentID", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String shipmentId;

        /** Package number or code. */
        @JacksonXmlProperty(localName = "csomagszam", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String packageCode;

        /** Country code of the recipient. */
        @JacksonXmlProperty(localName = "countryCode", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String countryCode;

        /** ZIP code of the recipient. */
        @JacksonXmlProperty(localName = "zip", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String zipCode;

        /** Type of service requested. */
        @JacksonXmlProperty(localName = "service", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String service;
    }

    /**
     * Contains shipping information specific to the Sprinter provider.
     */
    @Builder
    public static class Sprinter {
        /** Provider identifier. */
        @JacksonXmlProperty(localName = "azonosito", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String id;

        /** Sender code assigned by Sprinter. */
        @JacksonXmlProperty(localName = "feladokod", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String senderCode;

        /** Direction code for route planning. */
        @JacksonXmlProperty(localName = "iranykod", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String directionCode;

        /** Package number or code. */
        @JacksonXmlProperty(localName = "csomagszam", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String packageCode;

        /** Postfix part of the barcode. */
        @JacksonXmlProperty(localName = "vonalkodPostfix", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String barcodePostfix;

        /** Estimated or scheduled shipping time. */
        @JacksonXmlProperty(localName = "szallitasiIdo", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String shippingTime;
    }

    /**
     * Contains barcode configuration data specific to the PPP provider.
     */
    @Builder
    public static class Ppp {
        /** Prefix part of the barcode. */
        @JacksonXmlProperty(localName = "vonalkodPrefix", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String barcodePrefix;

        /** Postfix part of the barcode. */
        @JacksonXmlProperty(localName = "vonalkodPostfix", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String barcodePostfix;
    }

    /**
     * Contains shipping information specific to the MPL (Magyar Posta Logisztika) provider.
     */
    @Builder
    public static class Mpl {
        /** MPL customer code. */
        @JacksonXmlProperty(localName = "vevokod", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String customerCode;

        /** Barcode associated with the shipment. */
        @JacksonXmlProperty(localName = "vonalkod", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String barcode;

        /** Weight of the shipment. */
        @JacksonXmlProperty(localName = "tomeg", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String weight;

        /** Optional additional services requested. */
        @JacksonXmlProperty(localName = "kulonszolgaltatasok", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String otherServices;

        /** Declared value of the shipment for insurance purposes. */
        @JacksonXmlProperty(localName = "erteknyilvanitas", namespace = "http://www.szamlazz.hu/xmlszamla")
        private Double value;
    }
}
