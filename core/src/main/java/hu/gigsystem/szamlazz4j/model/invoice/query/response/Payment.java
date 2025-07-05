package hu.gigsystem.szamlazz4j.model.invoice.query.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Represents a payment record associated with an invoice,
 * including payment date, amount, claim type, and related banking details.
 * <p>
 * Designed for XML serialization/deserialization with Számlázz.hu schema.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
public class Payment {

    /**
     * The date the payment was made.
     * Formatted as {@code yyyy-MM-dd}.
     * Mapped to the XML element {@code <datum>}.
     */
    @JacksonXmlProperty(localName = "datum")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * The legal title or reason for the payment (claim).
     * Mapped to the XML element {@code <jogcim>}.
     */
    @JacksonXmlProperty(localName = "jogcim")
    private String claim;

    /**
     * The amount of the payment.
     * Mapped to the XML element {@code <osszeg>}.
     */
    @JacksonXmlProperty(localName = "osszeg")
    private Double amount;

    /**
     * Optional note or comment related to the payment.
     * Mapped to the XML element {@code <megjegyzes>}.
     */
    @JacksonXmlProperty(localName = "megjegyzes")
    private String note;

    /**
     * The bank account number from which the payment was made.
     * Mapped to the XML element {@code <bankszamlaszam>}.
     */
    @JacksonXmlProperty(localName = "bankszamlaszam")
    private String bankNumber;
}
