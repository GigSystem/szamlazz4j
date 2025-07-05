package hu.gigsystem.szamlazz4j.model.invoice.query;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import hu.gigsystem.szamlazz4j.SzamlaAgent;
import hu.gigsystem.szamlazz4j.model.BaseRequest;
import hu.gigsystem.szamlazz4j.model.invoice.query.response.FullInvoiceResponse;
import lombok.Builder;

/**
 * Represents a request to query a full invoice from the Számlázz.hu system.
 * This request corresponds to the {@code xmlszamlaxml} endpoint and is used
 * to retrieve all details of an already issued invoice.
 *
 * <p>Typical use case includes fetching invoice contents by invoice number, order ID,
 * or other identifying fields to validate, display, or archive invoice data.
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 5th of July, 2025
 */
@Builder
@JacksonXmlRootElement(localName = "xmlszamlaxml", namespace = "http://www.szamlazz.hu/xmlszamlaxml")
public class InvoiceQueryRequest extends BaseRequest<FullInvoiceResponse> {

    /**
     * The username of the user requesting the invoice.
     */
    @JacksonXmlProperty(localName = "felhasznalo", namespace = "http://www.szamlazz.hu/xmlszamlaxml")
    private String username;

    /**
     * The password associated with the user.
     */
    @JacksonXmlProperty(localName = "jelszo", namespace = "http://www.szamlazz.hu/xmlszamlaxml")
    private String password;

    /**
     * The agent key for authenticating with the Számlázz.hu API.
     */
    @JacksonXmlProperty(localName = "szamlaagentkulcs", namespace = "http://www.szamlazz.hu/xmlszamlaxml")
    private String key;

    /**
     * The invoice number to retrieve.
     */
    @JacksonXmlProperty(localName = "szamlaszam", namespace = "http://www.szamlazz.hu/xmlszamlaxml")
    private String invoiceNumber;

    /**
     * Optional order number associated with the invoice.
     */
    @JacksonXmlProperty(localName = "rendelesSzam", namespace = "http://www.szamlazz.hu/xmlszamlaxml")
    private String orderNumber;

    /**
     * Constructs a new {@code InvoiceQueryRequest} with mandatory fields.
     *
     * @param invoiceNumber the invoice number (or orderNumber)
     * @param orderNumber   the order number (or invoiceNumber)
     */
    public InvoiceQueryRequest(String usernameIgnored, String passwordIgnored, String keyIgnored, String invoiceNumber, String orderNumber) {
        super("action-szamla_agent_xml", "xmlszamlaxml.xml");
        if (invoiceNumber == null && orderNumber == null) {
            throw new IllegalArgumentException("Either invoiceNumber or orderNumber is required!");
        }

        this.invoiceNumber = invoiceNumber;
        this.orderNumber = orderNumber;
    }

    @Override
    public void setAgent(SzamlaAgent agent) {
        this.key = agent.getKey();
        this.username = agent.getUsername();
        this.password = agent.getPassword();
    }
}
