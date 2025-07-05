package hu.gigsystem.szamlazz4j.model.invoice;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import hu.gigsystem.szamlazz4j.model.BaseResponse;
import lombok.Getter;

/**
 * Represents the response from the Számlázz.hu API after submitting an invoice request.
 * <p>
 * Maps the XML response elements in the
 * "<a href="http://www.szamlazz.hu/xmlszamlavalasz">...</a>" namespace.
 * </p>
 * <p>
 * Extends {@link BaseResponse}, which provides common response fields like success
 * status and error information.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
@JacksonXmlRootElement(localName = "xmlszamlavalasz", namespace = "http://www.szamlazz.hu/xmlszamlavalasz")
public class XmlInvoiceResponse extends BaseResponse {
    /**
     * The unique invoice number assigned by the system.
     */
    @JacksonXmlProperty(localName = "szamlaszam")
    private String invoiceNumber;

    /**
     * Total invoice amount including taxes (gross).
     */
    @JacksonXmlProperty(localName = "szamlabrutto")
    private Double priceTotal;

    /**
     * Total invoice amount excluding taxes (net).
     */
    @JacksonXmlProperty(localName = "szamlanetto")
    private Double priceTotalExclTax;

    /**
     * URL to the customer's account or invoice details page.
     */
    @JacksonXmlProperty(localName = "vevoifiokurl")
    private String customerUrl;

    /**
     * Outstanding amount due (receivable).
     */
    @JacksonXmlProperty(localName = "kintlevoseg")
    private Double receivable;

    /**
     * Base64 encoded PDF file of the invoice.
     */
    @JacksonXmlProperty(localName = "pdf")
    private String pdf;
}