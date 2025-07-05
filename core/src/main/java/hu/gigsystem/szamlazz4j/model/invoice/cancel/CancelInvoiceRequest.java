package hu.gigsystem.szamlazz4j.model.invoice.cancel;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import hu.gigsystem.szamlazz4j.SzamlaAgent;
import hu.gigsystem.szamlazz4j.model.BaseRequest;
import hu.gigsystem.szamlazz4j.model.invoice.XmlInvoiceResponse;
import lombok.Builder;

/**
 * Represents a cancellation request for an invoice in the Számlázz.hu system.
 * <p>
 * The request includes cancellation-specific settings, header data, seller, and customer information.
 * It produces an {@link XmlInvoiceResponse} as a response type.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 **/
@Builder
@JacksonXmlRootElement(localName = "xmlszamlast", namespace = "http://www.szamlazz.hu/xmlszamlast")
public class CancelInvoiceRequest extends BaseRequest<XmlInvoiceResponse> {

    /**
     * Cancellation settings containing authentication and behavior options.
     */
    @JacksonXmlProperty(localName = "beallitasok", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private CancelSettings settings;

    /**
     * Header data for the invoice cancellation.
     */
    @JacksonXmlProperty(localName = "fejlec", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private CancelHeader header;

    /**
     * Information about the seller, such as email communication details.
     */
    @JacksonXmlProperty(localName = "elado", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private Seller seller;

    /**
     * Information about the customer related to the canceled invoice.
     */
    @JacksonXmlProperty(localName = "vevo", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private Customer customer;

    /**
     * Constructs a new {@code CancelInvoiceRequest} with the specified data.
     *
     * @param settings configuration options and credentials
     * @param header   metadata for the cancellation
     * @param seller   seller-related email options
     * @param customer customer data
     */
    public CancelInvoiceRequest(CancelSettings settings, CancelHeader header, Seller seller, Customer customer) {
        super("action-szamla_agent_st", "xmlszamlast.xml");
        this.settings = settings;
        this.header = header;
        this.seller = seller;
        this.customer = customer;
    }

    /**
     * Injects the {@link SzamlaAgent} credentials into nested settings and seller objects.
     *
     * @param agent the agent containing key or username/password
     */
    @Override
    public void setAgent(SzamlaAgent agent) {
        this.settings.setAgent(agent);
        this.seller.setAgent(agent);
    }

    /**
     * Represents the customer information included in the invoice cancellation request.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 3rd of July, 2025
     */
    @Builder
    public static class Customer {
        /**
         * Email
         */
        @JacksonXmlProperty(localName = "email", namespace = "http://www.szamlazz.hu/xmlszamlast")
        private String email;

        /**
         * Tax number.
         */
        @JacksonXmlProperty(localName = "adoszam", namespace = "http://www.szamlazz.hu/xmlszamlast")
        private String taxNumber;

        /**
         * European Union tax number.
         */
        @JacksonXmlProperty(localName = "adoszamEU", namespace = "http://www.szamlazz.hu/xmlszamlast")
        private String europeanTaxNumber;
    }

    /**
     * Represents seller-specific data for the cancellation request,
     * primarily related to email settings.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 3rd of July, 2025
     */
    @Builder
    public static class Seller {
        /**
         * Email address for replies.
         */
        @JacksonXmlProperty(localName = "emailReplyto", namespace = "http://www.szamlazz.hu/xmlszamlast")
        private String emailReplyTo;

        /**
         * Subject line for emails.
         */
        @JacksonXmlProperty(localName = "emailTargy", namespace = "http://www.szamlazz.hu/xmlszamlast")
        private String emailSubject;

        /**
         * Email body text.
         */
        @JacksonXmlProperty(localName = "emailSzoveg", namespace = "http://www.szamlazz.hu/xmlszamlast")
        private String emailText;

        public void setAgent(SzamlaAgent agent) {
            if (this.emailReplyTo == null) this.emailReplyTo = agent.getEmailReplyTo();
        }
    }
}
