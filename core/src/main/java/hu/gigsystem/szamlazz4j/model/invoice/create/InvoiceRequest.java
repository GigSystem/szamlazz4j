package hu.gigsystem.szamlazz4j.model.invoice.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import hu.gigsystem.szamlazz4j.SzamlaAgent;
import hu.gigsystem.szamlazz4j.model.BaseRequest;
import hu.gigsystem.szamlazz4j.model.invoice.XmlInvoiceResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a request to create an invoice in the Számlázz.hu API.
 * <p>
 * This request contains settings, header information, seller and customer details,
 * and the list of invoice items.
 * </p>
 * <p>
 * The XML elements are mapped to the "http://www.szamlazz.hu/xmlszamla" namespace.
 * </p>
 * <p>
 * Extends {@link BaseRequest} with a response type of {@link XmlInvoiceResponse}.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Builder
@JacksonXmlRootElement(localName = "xmlszamla", namespace = "http://www.szamlazz.hu/xmlszamla")
public class InvoiceRequest extends BaseRequest<XmlInvoiceResponse> {

    /**
     * Invoice settings containing authentication and behavior options.
     */
    @JacksonXmlProperty(localName = "beallitasok", namespace = "http://www.szamlazz.hu/xmlszamla")
    private InvoiceSettings settings;

    /**
     * Invoice header information (e.g., invoice date, payment method).
     */
    @JacksonXmlProperty(localName = "fejlec", namespace = "http://www.szamlazz.hu/xmlszamla")
    private InvoiceHeader header;

    /**
     * Seller information such as bank details and email settings.
     */
    @JacksonXmlProperty(localName = "elado", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Seller seller;

    /**
     * Customer information for billing and shipping.
     */
    @JacksonXmlProperty(localName = "vevo", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Customer customer;

    /**
     * Shipment information (fuvarlevél)
     */
    @JacksonXmlProperty(localName = "fuvarlevel", namespace = "http://www.szamlazz.hu/xmlszamla")
    private FreightLetter freightLetter;

    /**
     * List of invoice line items.
     */
    @JacksonXmlElementWrapper(localName = "tetelek", namespace = "http://www.szamlazz.hu/xmlszamla")
    @JacksonXmlProperty(localName = "tetel", namespace = "http://www.szamlazz.hu/xmlszamla")
    private List<InvoiceItem> items;

    /**
     * Constructs an invoice request with the specified parameters.
     *
     * @param settings the invoice settings
     * @param header   the invoice header information
     * @param seller   the seller's details
     * @param customer the customer's details
     * @param items    the list of invoice items
     */
    public InvoiceRequest(InvoiceSettings settings, InvoiceHeader header, Seller seller, Customer customer, FreightLetter freightLetter, List<InvoiceItem> items) {
        super("action-xmlagentxmlfile", "xmlszamla.xml");
        this.settings = settings;
        this.header = header;
        this.seller = seller;
        this.customer = customer;
        this.freightLetter = freightLetter;
        this.items = items;
    }

    /**
     * Sets the {@link SzamlaAgent} for this request.
     * Passes the agent to settings and seller to fill in any missing data.
     *
     * @param agent the agent instance
     */
    @Override
    public void setAgent(SzamlaAgent agent) {
        this.settings.setAgent(agent);
        this.seller.setAgent(agent);
    }

    /**
     * Seller details for the invoice.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 3rd of July, 2025
     */
    @Builder
    public static class Seller {

        /**
         * Bank name of the seller.
         */
        @JacksonXmlProperty(localName = "bank", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String bankName;

        /**
         * Bank account number of the seller.
         */
        @JacksonXmlProperty(localName = "bankszamlaszam", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String bankNumber;

        /**
         * Email address for replies.
         */
        @JacksonXmlProperty(localName = "emailReplyto", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String emailReplyTo;

        /**
         * Subject line for emails.
         */
        @JacksonXmlProperty(localName = "emailTargy", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String emailSubject;

        /**
         * Email body text.
         */
        @JacksonXmlProperty(localName = "emailSzoveg", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String emailText;

        /**
         * Name of the person signing the invoice.
         */
        @JacksonXmlProperty(localName = "alairoNeve", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String signerName;

        /**
         * Populates missing seller fields from the given agent.
         *
         * @param agent the SzamlaAgent to retrieve defaults from
         */
        public void setAgent(SzamlaAgent agent) {
            if (this.bankName == null) this.bankName = agent.getBank();
            if (this.bankNumber == null) this.bankNumber = agent.getBankNumber();
            if (this.emailReplyTo == null) this.emailReplyTo = agent.getEmailReplyTo();
            if (this.signerName == null) this.signerName = agent.getSignerName();
        }
    }

    /**
     * Represents customer ledger data (vevői főköynv) for accounting purposes in the Számlázz.hu system.
     * This data is typically attached to invoices for bookkeeping or reporting needs.
     *
     * <p>Contains information about the customer’s general ledger accounts, accounting period,
     * and bookkeeping/posting details.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 5th of July, 2025
     */
    @Builder
    public static class CustomerLedger {

        /**
         * Date of posting (könyvelés dátuma), representing when the transaction is recorded in the books.
         */
        @JacksonXmlProperty(localName = "konyvelesDatum", namespace = "http://www.szamlazz.hu/xmlszamla")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate postingDate;

        /**
         * Customer identifier used in the accounting system (vevő azonosító).
         */
        @JacksonXmlProperty(localName = "vevoAzonosito", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String customerId;

        /**
         * Customer general ledger account number (vevő főkönyvi szám).
         */
        @JacksonXmlProperty(localName = "vevoFokonyviSzam", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String postingNumber;

        /**
         * Indicates whether the transaction is part of a continuous performance contract (folyamatos teljesítés).
         */
        @JacksonXmlProperty(localName = "folyamatosTelj", namespace = "http://www.szamlazz.hu/xmlszamla")
        private Boolean continuousCompletion;

        /**
         * Start date of the accounting period (elszámolási időszak kezdete).
         */
        @JacksonXmlProperty(localName = "elszDatumTol", namespace = "http://www.szamlazz.hu/xmlszamla")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate accountFrom;

        /**
         * End date of the accounting period (elszámolási időszak vége).
         */
        @JacksonXmlProperty(localName = "elszDatumIg", namespace = "http://www.szamlazz.hu/xmlszamla")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate accountTo;
    }


    /**
     * Customer details for the invoice.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 3rd of July, 2025
     */
    @Builder
    public static class Customer {

        /**
         * Customer name.
         */
        @JacksonXmlProperty(localName = "nev", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String name;

        /**
         * Customer country.
         */
        @JacksonXmlProperty(localName = "orszag", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String country;

        /**
         * Postal code.
         */
        @JacksonXmlProperty(localName = "irsz", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String postCode;

        /**
         * City or town.
         */
        @JacksonXmlProperty(localName = "telepules", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String city;

        /**
         * Street address.
         */
        @JacksonXmlProperty(localName = "cim", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String address;

        /**
         * Email address of the customer.
         */
        @JacksonXmlProperty(localName = "email", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String email;

        /**
         * Whether to send the invoice by email.
         */
        @Builder.Default
        @JacksonXmlProperty(localName = "sendEmail", namespace = "http://www.szamlazz.hu/xmlszamla")
        private Boolean sendEmail = false;

        /**
         * Tax type of the customer. Defaults to NO_TAX_NUMBER.
         */
        @Builder.Default
        @JacksonXmlProperty(localName = "adoalany", namespace = "http://www.szamlazz.hu/xmlszamla")
        private TaxType taxType = TaxType.NO_TAX_NUMBER;

        /**
         * Tax number if applicable.
         */
        @JacksonXmlProperty(localName = "adoszam", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String taxNumber;

        /**
         * Group identifier, if any.
         */
        @JacksonXmlProperty(localName = "csoportazonosito", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String groupId;

        /**
         * European Union tax number.
         */
        @JacksonXmlProperty(localName = "adoszamEU", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String europeanTaxNumber;

        /**
         * Name used for postal delivery.
         */
        @JacksonXmlProperty(localName = "postazasiNev", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String postName;

        /**
         * Country used for postal delivery.
         */
        @JacksonXmlProperty(localName = "postazasiOrszag", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String postCountry;

        /**
         * Postal code used for postal delivery.
         */
        @JacksonXmlProperty(localName = "postazasiIrsz", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String postPostCode;

        /**
         * City used for postal delivery.
         */
        @JacksonXmlProperty(localName = "postazasiTelepules", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String postCity;

        /**
         * Street address used for postal delivery.
         */
        @JacksonXmlProperty(localName = "postazasiCim", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String postAddress;

        /**
         * Customer ledger info
         */
        @JacksonXmlProperty(localName = "vevoFokonyv", namespace = "http://www.szamlazz.hu/xmlszamla")
        private CustomerLedger customerLedger;

        /**
         * Customer identifier.
         */
        @JacksonXmlProperty(localName = "azonosito", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String id;

        /**
         * Name of the person authorized to sign on behalf of the customer.
         */
        @JacksonXmlProperty(localName = "alairoNeve", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String signerName;

        /**
         * Customer phone number.
         */
        @JacksonXmlProperty(localName = "telefonszam", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String phoneNumber;

        /**
         * Additional notes regarding the customer.
         */
        @JacksonXmlProperty(localName = "megjegyzes", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String note;

        /**
         * Enumeration of tax types applicable to the customer.
         *
         * @author Tamás Tóth
         * @version 1.0.0
         * @since 3rd of July, 2025
         */
        @RequiredArgsConstructor
        public enum TaxType {
            BUSINESS_OUTSIDE_EU(7),
            BUSINESS_INSIDE_EU(6),
            HAS_HUNGARIAN_TAX_NUMBER(1),
            UNKNOWN(0),
            NO_TAX_NUMBER(-1);

            private final int code;

            /**
             * Gets the integer code representing the tax type.
             *
             * @return tax type code
             */
            @JsonValue
            public int getCode() {
                return this.code;
            }
        }
    }
}
