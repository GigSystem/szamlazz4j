package hu.gigsystem.szamlazz4j.model.invoice.query;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import hu.gigsystem.szamlazz4j.SzamlaAgent;
import hu.gigsystem.szamlazz4j.model.BaseRequest;
import hu.gigsystem.szamlazz4j.model.BaseResponse;
import lombok.Builder;
import lombok.Getter;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

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
public class InvoicePdfQueryRequest extends BaseRequest<InvoicePdfQueryRequest.Response> {

    /**
     * The response version to be used, must always be 2.
     */
    @JacksonXmlProperty(localName = "valaszVerzio", namespace = "http://www.szamlazz.hu/xmlszamlapdf")
    private final Integer responseVersion = 2;
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
        super("szamla_agent_pdf", "xmlszamlapdf.xml");
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

    /**
     * Represents the response returned from the Számlázz.hu API
     * for the invoice PDF request.
     */
    @Getter
    @JacksonXmlRootElement(localName = "xmlszamlavalasz", namespace = "http://www.szamlazz.hu/xmlszamlavalasz")
    static class Response extends BaseResponse {

        /**
         * The order number related to the invoice.
         */
        @JacksonXmlProperty(localName = "szamlaszam")
        private String orderNumber;

        /**
         * The net price of the invoice.
         */
        @JacksonXmlProperty(localName = "szamlanetto")
        private Double netPrice;

        /**
         * The total gross price of the invoice.
         */
        @JacksonXmlProperty(localName = "szamlabrutto")
        private Double totalPrice;

        /**
         * The amount receivable.
         */
        @JacksonXmlProperty(localName = "kintlevoseg")
        private Double receivable;

        /**
         * URL to the buyer's account information.
         */
        @JacksonXmlProperty(localName = "vevoifiokurl")
        private String buyerAccountUrl;

        /**
         * The invoice PDF encoded as a Base64 string.
         */
        @JacksonXmlProperty(localName = "pdf")
        private String pdf;

        /**
         * Decodes the Base64 encoded PDF string into a byte array.
         *
         * @return decoded PDF data as bytes
         * @throws IllegalStateException if the PDF string is null or empty
         */
        public byte[] getDecodedPdf() {
            if (pdf == null || pdf.isEmpty()) {
                throw new IllegalStateException("Cannot write invoice PDF, due to PDF being null or empty!");
            }
            return Base64.getDecoder().decode(pdf);
        }

        /**
         * Writes the decoded PDF to the specified output file.
         *
         * @param output the path to write the PDF file to
         * @throws IOException if an I/O error occurs writing the file
         */
        public void writePdfToFile(Path output) throws IOException {
            byte[] pdfData = getDecodedPdf();
            try (BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(output, StandardOpenOption.CREATE_NEW))) {
                out.write(pdfData, 0, pdfData.length);
            }
        }
    }
}
