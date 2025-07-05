package hu.gigsystem.szamlazz4j.model.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import hu.gigsystem.szamlazz4j.SzamlaAgent;
import hu.gigsystem.szamlazz4j.model.BaseRequest;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a request to process an invoice payment via the Számlázz.hu API.
 * This class is responsible for holding the payment information and authentication
 * settings required by the endpoint {@code szamla_agent_kifiz}.
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 5th of July, 2025
 */
@Builder
public class InvoicePaymentRequest extends BaseRequest<XmlInvoiceResponse> {

    /**
     * Configuration settings including authentication details, invoice number, etc.
     */
    @JacksonXmlProperty(localName = "beallitasok", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
    private Settings settings;

    /**
     * List of payments to be applied to the invoice.
     */
    @JacksonXmlProperty(localName = "kifizetes", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
    private List<Payment> payments;

    /**
     * Constructs a new InvoicePaymentRequest instance.
     **/
    protected InvoicePaymentRequest(Settings settings, List<Payment> payments) {
        super("szamla_agent_kifiz", "xmlszamlakifiz.xml");
        this.settings = settings;
        this.payments = payments;
    }

    /**
     * Sets the authentication agent for the request, updating the settings with the agent's credentials.
     *
     * @param agent the SzamlaAgent instance containing authentication data
     */
    @Override
    public void setAgent(SzamlaAgent agent) {
        settings.setAgent(agent);
    }

    /**
     * Represents a payment record in the invoice payment request.
     * Each payment contains the date, type, amount, and an optional description.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 5th of July, 2025
     */
    @Builder
    public static class Payment {

        /**
         * Date when the payment was made (format: yyyy-MM-dd).
         */
        @JacksonXmlProperty(localName = "datum", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate paymentDate;

        /**
         * Type or legal basis of the payment.
         */
        @JacksonXmlProperty(localName = "jogcim", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        private String paymentType;

        /**
         * Amount of the payment.
         */
        @JacksonXmlProperty(localName = "osszeg", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        private Double amount;

        /**
         * Optional textual description for the payment.
         */
        @JacksonXmlProperty(localName = "leiras", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        private String description;
    }

    /**
     * Configuration and authentication details used in the request.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 5th of July, 2025
     */
    @Builder
    public static class Settings {

        /**
         * Expected version of the response. Defaults to {@code 2}.
         */
        @Builder.Default
        @JacksonXmlProperty(localName = "valaszVerzio", namespace = "http://www.szamlazz.hu/xmlszamla")
        private final Integer answerType = 2;
        /**
         * Username for authentication.
         */
        @JacksonXmlProperty(localName = "felhasznalo", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        private String username;
        /**
         * Password for authentication.
         */
        @JacksonXmlProperty(localName = "jelszo", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        private String password;
        /**
         * The API key used for authentication.
         */
        @JacksonXmlProperty(localName = "szamlaagentkulcs", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        private String key;
        /**
         * The number of the invoice to which the payments apply.
         */
        @JacksonXmlProperty(localName = "szamlaszam", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        private String invoiceNumber;
        /**
         * Tax number of the invoice issuer or customer, depending on API usage.
         */
        @JacksonXmlProperty(localName = "adoszam", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        private String taxNumber;
        /**
         * Indicates whether the payment should be treated as additive (multiple payments per invoice).
         */
        @JacksonXmlProperty(localName = "additiv", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        private Boolean additive;
        /**
         * Optional aggregator identifier, used in agent-based or multi-tenant systems.
         */
        @JacksonXmlProperty(localName = "aggregator", namespace = "http://www.szamlazz.hu/xmlszamlakifiz")
        private String aggregator;

        /**
         * Sets the authentication details from the given {@link SzamlaAgent}.
         *
         * @param agent the SzamlaAgent containing key, username, and password
         */
        public void setAgent(SzamlaAgent agent) {
            this.key = agent.getKey();
            this.username = agent.getUsername();
            this.password = agent.getPassword();
        }
    }
}
