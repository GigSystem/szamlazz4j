package hu.gigsystem.szamlazz4j.model.invoice;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import hu.gigsystem.szamlazz4j.model.BaseResponse;
import lombok.Getter;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

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