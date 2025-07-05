package hu.gigsystem.szamlazz4j.model.invoice.query.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

/**
 * Represents a single line item on an invoice, including product or service details,
 * pricing, tax, and accounting ledger information.
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
public class InvoiceItem {

    /**
     * The name or description of the invoiced item.
     * Mapped to the XML element {@code <nev>}.
     */
    @JacksonXmlProperty(localName = "nev")
    private String name;

    /**
     * The quantity of the item being invoiced.
     * Mapped to the XML element {@code <mennyiseg>}.
     */
    @JacksonXmlProperty(localName = "mennyiseg")
    private Double quantity;

    /**
     * The unit of measure for the quantity (e.g., pcs, kg).
     * Mapped to the XML element {@code <mennyisegiegyseg>}.
     */
    @JacksonXmlProperty(localName = "mennyisegiegyseg")
    private String quantityUnit;

    /**
     * The net unit price of the item (before VAT).
     * Mapped to the XML element {@code <nettoegysegar>}.
     */
    @JacksonXmlProperty(localName = "nettoegysegar")
    private Double netUnitPrice;

    /**
     * The VAT rate applied to this item (e.g., 27%, 0%, M, AAM).
     * Mapped to the XML element {@code <afakulcs>}.
     */
    @JacksonXmlProperty(localName = "afakulcs")
    private String vatRate;

    /**
     * The total net price for the item (quantity × net unit price).
     * Mapped to the XML element {@code <netto>}.
     */
    @JacksonXmlProperty(localName = "netto")
    private Double netPrice;

    /**
     * The base value used for margin-based VAT calculation (optional).
     * Mapped to the XML element {@code <arresafaalap>}.
     */
    @JacksonXmlProperty(localName = "arresafaalap")
    private Double marginVatBase;

    /**
     * The VAT amount applied to the item.
     * Mapped to the XML element {@code <afa>}.
     */
    @JacksonXmlProperty(localName = "afa")
    private Double vatAmount;

    /**
     * The gross total (net + VAT) for this item.
     * Mapped to the XML element {@code <brutto>}.
     */
    @JacksonXmlProperty(localName = "brutto")
    private Double total;

    /**
     * Optional comment or note associated with the invoice item.
     * Mapped to the XML element {@code <megjegyzes>}.
     */
    @JacksonXmlProperty(localName = "megjegyzes")
    private String note;

    /**
     * Ledger data for accounting purposes.
     * Mapped to the XML element {@code <fokonyv>}.
     */
    @JacksonXmlProperty(localName = "fokonyv")
    private Ledger ledger;

    /**
     * Represents accounting ledger details for an invoice item,
     * including income and VAT accounts, and economic events.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 3rd of July, 2025
     */
    @Getter
    public static class Ledger {

        /**
         * Income account code used in bookkeeping.
         * Mapped to the XML element {@code <arbevetel>}.
         * <p>Type intentionally set as {@code String} to ensure parsing safety.</p>
         */
        @JacksonXmlProperty(localName = "arbevetel")
        private String income;

        /**
         * VAT account code used in bookkeeping.
         * Mapped to the XML element {@code <afa>}.
         * <p>Type intentionally set as {@code String} to ensure parsing safety.</p>
         */
        @JacksonXmlProperty(localName = "afa")
        private String vat;

        /**
         * The type of economic event associated with the item (gazdasági esemény),
         * such as sale, service, or other accounting-related classification.
         * Mapped to the XML element {@code <gazdasagiEsem>} in the Számlázz.hu namespace.
         */
        @JacksonXmlProperty(localName = "gazdasagiEsem", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String economicEvent;

        /**
         * The VAT-specific economic event type (gazdasági esemény ÁFA),
         * used to categorize VAT events in accounting.
         * Mapped to the XML element {@code <gazdasagiEsemAfa>} in the Számlázz.hu namespace.
         */
        @JacksonXmlProperty(localName = "gazdasagiEsemAfa", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String economicEventVat;
    }
}
