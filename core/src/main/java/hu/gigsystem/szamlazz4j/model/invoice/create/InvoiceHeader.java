package hu.gigsystem.szamlazz4j.model.invoice.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import hu.gigsystem.szamlazz4j.model.invoice.enums.InvoiceLanguage;
import hu.gigsystem.szamlazz4j.model.invoice.enums.InvoiceTemplate;
import lombok.Builder;

import java.time.LocalDate;

/**
 * Represents the header information of an invoice.
 * <p>
 * Maps to XML elements in the "http://www.szamlazz.hu/xmlszamla" namespace.
 * Contains key metadata such as invoice dates, payment details, currency, language,
 * and additional optional invoice properties like correction flags, templates, and notes.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Builder
public class InvoiceHeader {

    /**
     * Date when the invoice was created (keltDatum).
     */
    @JacksonXmlProperty(localName = "keltDatum", namespace = "http://www.szamlazz.hu/xmlszamla")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    /**
     * Date of performance/payment completion (teljesitesDatum).
     */
    @JacksonXmlProperty(localName = "teljesitesDatum", namespace = "http://www.szamlazz.hu/xmlszamla")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate paidAt;

    /**
     * Payment deadline date (fizetesiHataridoDatum).
     */
    @JacksonXmlProperty(localName = "fizetesiHataridoDatum", namespace = "http://www.szamlazz.hu/xmlszamla")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate paymentDeadlineAt;

    /**
     * Payment method description or code (fizmod).
     */
    @JacksonXmlProperty(localName = "fizmod", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String paymentMethod;

    /**
     * Currency code, defaults to Hungarian Forint (HUF) (penznem).
     */
    @Builder.Default
    @JacksonXmlProperty(localName = "penznem", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String currency = "HUF";

    /**
     * Invoice language code (szamlaNyelve), defaults to Hungarian (HU).
     */
    @Builder.Default
    @JacksonXmlProperty(localName = "szamlaNyelve", namespace = "http://www.szamlazz.hu/xmlszamla")
    private InvoiceLanguage language = InvoiceLanguage.HU;

    /**
     * Additional notes or comments (megjegyzes).
     */
    @JacksonXmlProperty(localName = "megjegyzes", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String note;

    /**
     * Bank name used for exchange rate reference (arfolyamBank).
     */
    @JacksonXmlProperty(localName = "arfolyamBank", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String exchangeBank;

    /**
     * Exchange rate applied to the invoice (arfolyam).
     */
    @JacksonXmlProperty(localName = "arfolyam", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Double exchangeRate;

    /**
     * Customer's order number for reference (rendelesSzam).
     */
    @JacksonXmlProperty(localName = "rendelesSzam", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String orderNumber;

    /**
     * Proforma invoice number if applicable (dijbekeroSzamlaszam).
     */
    @JacksonXmlProperty(localName = "dijbekeroSzamlaszam", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String proformaNumber;

    /**
     * Flag indicating if this is an advance invoice (elolegszamla).
     */
    @JacksonXmlProperty(localName = "elolegszamla", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean advanceInvoice;

    /**
     * Flag indicating if this is a  invoice (vegszamla).
     */
    @JacksonXmlProperty(localName = "vegszamla", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean Invoice;

    /**
     * Invoice number of the advance invoice this  invoice refers to (elolegSzamlaszam).
     */
    @JacksonXmlProperty(localName = "elolegSzamlaszam", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String advanceInvoiceNumber;

    /**
     * Flag indicating if this invoice is a correction (helyesbitoszamla).
     */
    @JacksonXmlProperty(localName = "helyesbitoszamla", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean correctionInvoice;

    /**
     * Invoice number of the corrected invoice (helyesbitettSzamlaszam).
     */
    @JacksonXmlProperty(localName = "helyesbitettSzamlaszam", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String correctedInvoiceNumber;

    /**
     * Flag indicating if this invoice is a proforma invoice (dijbekero).
     */
    @JacksonXmlProperty(localName = "dijbekero", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean proforma;

    /**
     * Flag indicating if this invoice has a delivery note (szallitolevel).
     */
    @JacksonXmlProperty(localName = "szallitolevel", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean deliveryNote;

    /**
     * Optional logo image or identifier (logoExtra).
     */
    @JacksonXmlProperty(localName = "logoExtra", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String logo;

    /**
     * Prefix for the invoice number (szamlaszamElotag).
     */
    @JacksonXmlProperty(localName = "szamlaszamElotag", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String invoicePrefix;

    /**
     * Amount due correction, if any (fizetendoKorrekcio).
     */
    @JacksonXmlProperty(localName = "fizetendoKorrekcio", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Double amountDueCorretion;

    /**
     * Flag indicating if the invoice is marked as paid (fizetve).
     */
    @JacksonXmlProperty(localName = "fizetve", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean paid;

    /**
     * Flag indicating if margin VAT scheme applies (arresAfa).
     */
    @JacksonXmlProperty(localName = "arresAfa", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean marginVat;

    /**
     * Flag indicating if EU VAT rules apply (eusAfa).
     */
    @JacksonXmlProperty(localName = "eusAfa", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean euVat;

    /**
     * Template to use for invoice layout (szamlaSablon).
     */
    @JacksonXmlProperty(localName = "szamlaSablon", namespace = "http://www.szamlazz.hu/xmlszamla")
    private InvoiceTemplate template;

    /**
     * Whether to generate a PDF preview (elonezetpdf).
     */
    @JacksonXmlProperty(localName = "elonezetpdf", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean previewPdf;
}
