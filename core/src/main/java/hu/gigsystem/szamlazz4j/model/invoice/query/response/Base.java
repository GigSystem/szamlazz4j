package hu.gigsystem.szamlazz4j.model.invoice.query.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import hu.gigsystem.szamlazz4j.model.invoice.enums.InvoiceLanguage;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Represents the base information of an invoice, including metadata such as invoice number,
 * dates, payment details, and configuration flags.
 * <p>
 * This class is designed for XML serialization/deserialization using Jackson, in accordance
 * with the Számlázz.hu XML schema.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
public class Base {

    /**
     * Unique identifier of the invoice.
     * Mapped to the XML element {@code <id>}.
     */
    @JacksonXmlProperty(localName = "id")
    private String id;

    /**
     * The invoice number.
     * Mapped to the XML element {@code <szamlaszam>}.
     */
    @JacksonXmlProperty(localName = "szamlaszam")
    private String invoiceNumber;

    /**
     * The type of the invoice (e.g., regular, proforma).
     * Mapped to the XML element {@code <tipus>}.
     */
    @JacksonXmlProperty(localName = "tipus")
    private String type;

    /**
     * Indicates if the invoice is an electronic invoice (1) or not (0).
     * Mapped to the XML element {@code <eszamla>}.
     */
    @JacksonXmlProperty(localName = "eszamla")
    private Integer electronicInvoice;

    /**
     * The issue date of the invoice.
     * Mapped to the XML element {@code <kelt>}.
     * Format: {@code yyyy-MM-dd}
     */
    @JacksonXmlProperty(localName = "kelt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    /**
     * The performance (fulfillment) date of the invoice.
     * Mapped to the XML element {@code <telj>}.
     * Format: {@code yyyy-MM-dd}
     */
    @JacksonXmlProperty(localName = "telj")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate paidAt;

    /**
     * The payment deadline date.
     * Mapped to the XML element {@code <fizh>}.
     * Format: {@code yyyy-MM-dd}
     */
    @JacksonXmlProperty(localName = "fizh")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate paymentDeadlineAt;

    /**
     * The human-readable payment method (e.g., bank transfer, cash).
     * Mapped to the XML element {@code <fizmod>}.
     */
    @JacksonXmlProperty(localName = "fizmod")
    private String paymentMethod;

    /**
     * The standardized payment method string.
     * Mapped to the XML element {@code <fizmodunified>}.
     */
    @JacksonXmlProperty(localName = "fizmodunified")
    private String paymentMethodUnified;

    /**
     * The language of the invoice.
     * Mapped to the XML element {@code <nyelv>}.
     */
    @JacksonXmlProperty(localName = "nyelv")
    private InvoiceLanguage language;

    /**
     * The currency of the invoice (e.g., HUF, EUR).
     * Mapped to the XML element {@code <devizanem>}.
     */
    @JacksonXmlProperty(localName = "devizanem")
    private String currency;

    /**
     * The exchange rate used if the invoice is in a foreign currency.
     * Mapped to the XML element {@code <devizaarf>}.
     */
    @JacksonXmlProperty(localName = "devizaarf")
    private Double exchangeRate;

    /**
     * Optional note or comment added to the invoice.
     * Mapped to the XML element {@code <megjegyzes>}.
     */
    @JacksonXmlProperty(localName = "megjegyzes")
    private String note;

    /**
     * Indicates whether the invoice impacts cash flow accounting.
     * Mapped to the XML element {@code <penzforg>}.
     */
    @JacksonXmlProperty(localName = "penzforg")
    private Boolean cashFlow;

    /**
     * Indicates whether the invoice is KATA-specific (small business taxation).
     * Mapped to the XML element {@code <kata>}.
     */
    @JacksonXmlProperty(localName = "kata")
    private Boolean kata;

    /**
     * The recipient's email address for sending the invoice.
     * Mapped to the XML element {@code <email>}.
     */
    @JacksonXmlProperty(localName = "email")
    private String email;

    /**
     * Indicates if the invoice was generated in test mode.
     * Mapped to the XML element {@code <tesyt>}.
     */
    @JacksonXmlProperty(localName = "tesyt")
    private Boolean testMode;
}
