package hu.gigsystem.szamlazz4j.model.invoice.query;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import hu.gigsystem.szamlazz4j.SzamlaAgent;
import hu.gigsystem.szamlazz4j.model.BaseRequest;
import hu.gigsystem.szamlazz4j.model.invoice.XmlInvoiceResponse;
import lombok.Builder;

/**
 * Represents a request to generate or retrieve a PDF version of an invoice
 * from the Számlázz.hu API.
 * <p>
 * This request requires authentication details and invoice identifiers.
 * The response includes the PDF encoded in base64 format which can be decoded
 * and saved locally.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Builder
@JacksonXmlRootElement(localName = "xmlszamlapdf", namespace = "http://www.szamlazz.hu/xmlszamlapdf")
public class InvoicePdfQueryRequest extends BaseRequest<XmlInvoiceResponse> {

    /**
     * The agent key for authenticating with the Számlázz.hu API.
     */
    @JacksonXmlProperty(localName = "szamlaagentkulcs", namespace = "http://www.szamlazz.hu/xmlszamlapdf")
    private String key;
    /**
     * The username of the user requesting the invoice PDF.
     */
    @JacksonXmlProperty(localName = "felhasznalo", namespace = "http://www.szamlazz.hu/xmlszamlapdf")
    private String username;
    /**
     * The password associated with the user.
     */
    @JacksonXmlProperty(localName = "jelszo", namespace = "http://www.szamlazz.hu/xmlszamlapdf")
    private String password;
    /**
     * The invoice number to retrieve the PDF for.
     */
    @JacksonXmlProperty(localName = "szamlaszam", namespace = "http://www.szamlazz.hu/xmlszamlapdf")
    private String invoiceNumber;

    /**
     * The response version to be used, must always be 2.
     */
    @JacksonXmlProperty(localName = "valaszVerzio", namespace = "http://www.szamlazz.hu/xmlszamlapdf")
    private final Integer responseVersion = 2;

    /**
     * Optional order number associated with the invoice.
     */
    @JacksonXmlProperty(localName = "rendelesSzam", namespace = "http://www.szamlazz.hu/xmlszamlapdf")
    private String orderNumber;

    /**
     * Optional external ID for the invoice.
     */
    @JacksonXmlProperty(localName = "szamlaKulsoAzon", namespace = "http://www.szamlazz.hu/xmlszamlapdf")
    private String invoiceExternalId;

    /**
     * Constructs a new {@code InvoicePdfRequest} with mandatory fields.
     *
     * @param invoiceNumber     the invoice number
     * @param orderNumber       the order number (optional)
     * @param invoiceExternalId the external invoice ID (optional)
     */
    public InvoicePdfQueryRequest(String keyIgnored, String usernameIgnored, String passwordIgnored, String invoiceNumber, String orderNumber, String invoiceExternalId) {
        super("action-szamla_agent_pdf", "xmlszamlapdf.xml");
        this.invoiceNumber = invoiceNumber;
        this.orderNumber = orderNumber;
        this.invoiceExternalId = invoiceExternalId;
    }

    /**
     * Sets the Számlázz.hu agent credentials to this request.
     *
     * @param agent the Számlázz.hu agent containing credentials
     */
    @Override
    public void setAgent(SzamlaAgent agent) {
        this.key = agent.getKey();
        this.username = agent.getUsername();
        this.password = agent.getPassword();
    }
}
