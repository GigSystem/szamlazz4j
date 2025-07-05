package hu.gigsystem.szamlazz4j.model.invoice.query.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
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
import java.util.List;

/**
 * Represents the full response of an invoice, including seller, buyer, items, payments,
 * and summary information.
 * <p>
 * This class is the root element in the XML structure returned by the Számlázz.hu system.
 * It extends {@link BaseResponse} and includes detailed invoice content.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
@JacksonXmlRootElement(localName = "szamla", namespace = "http://www.szamlazz.hu/szamla")
public class FullInvoiceResponse extends BaseResponse {

    /**
     * The freight provider (supplier) of the invoice.
     * Mapped to the XML element {@code <szallito>}.
     */
    @JacksonXmlProperty(localName = "szallito")
    private FreightProvider freightProvider;

    /**
     * Basic invoice metadata including dates, payment method, currency, and notes.
     * Mapped to the XML element {@code <alap>}.
     */
    @JacksonXmlProperty(localName = "alap")
    private Base base;

    /**
     * The customer (buyer) details.
     * Mapped to the XML element {@code <vevo>}.
     */
    @JacksonXmlProperty(localName = "vevo")
    private Customer customer;

    /**
     * The list of items or services billed on the invoice.
     * Mapped to the XML wrapper {@code <tetelek>} and individual items as {@code <tetel>}.
     */
    @JacksonXmlElementWrapper(localName = "tetelek")
    @JacksonXmlProperty(localName = "tetel")
    private List<InvoiceItem> items;

    /**
     * The total amounts section, summarizing the invoice's financials.
     * Mapped to the XML element {@code <osszegek>}.
     */
    @JacksonXmlProperty(localName = "osszegek")
    private InvoiceSum invoiceSum;

    /**
     * A list of payments related to this invoice.
     * Mapped to the XML wrapper {@code <kifizetesek>} and individual elements as {@code <kifizetes>}.
     */
    @JacksonXmlElementWrapper(localName = "kifizetesek")
    @JacksonXmlProperty(localName = "kifizetes")
    private List<Payment> payments;

    /**
     * A Base64-encoded PDF representation of the invoice.
     * Mapped to the XML element {@code <pdf>}.
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
