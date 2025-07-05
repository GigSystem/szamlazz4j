package hu.gigsystem.szamlazz4j.model.invoice.cancel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import hu.gigsystem.szamlazz4j.model.invoice.enums.InvoiceTemplate;
import lombok.Builder;

import java.time.LocalDate;

/**
 * Represents the header section of a cancellation request for an invoice
 * submitted to the Számlázz.hu API.
 * <p>
 * Contains metadata about the original invoice and how the cancellation should be processed.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 **/
@Builder
public class CancelHeader {

    /**
     * The date the original invoice was issued.
     * Corresponds to the XML tag {@code <keltDatum>}.
     */
    @JacksonXmlProperty(localName = "keltDatum", namespace = "http://www.szamlazz.hu/xmlszamlast")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    /**
     * The date of performance (completion of service or delivery).
     * Corresponds to the XML tag {@code <teljesitesDatum>}.
     */
    @JacksonXmlProperty(localName = "teljesitesDatum", namespace = "http://www.szamlazz.hu/xmlszamlast")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate paidAt;

    /**
     * Optional textual comment to include with the cancellation.
     * Corresponds to the XML tag {@code <megjegyzes>}.
     */
    @JacksonXmlProperty(localName = "megjegyzes", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private String note;

    /**
     * The type of cancellation. Example values: "sztorno", "helyesbito".
     * Corresponds to the XML tag {@code <tipus>}.
     */
    @JacksonXmlProperty(localName = "tipus", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private String type;

    /**
     * Specifies the invoice layout template to use in the generated cancellation PDF.
     * Corresponds to the XML tag {@code <szamlaSablon>}.
     */
    @JacksonXmlProperty(localName = "szamlaSablon", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private InvoiceTemplate template;

}
