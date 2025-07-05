package hu.gigsystem.szamlazz4j.model.invoice.query.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

/**
 * Represents the summarized totals of an invoice, including VAT breakdown and grand totals.
 * <p>
 * This class is designed for XML (de)serialization with the Számlázz.hu schema.
 * It contains both total values and VAT group-level breakdowns.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
public class InvoiceSum {

    /**
     * VAT breakdown for a specific VAT rate group.
     * Mapped to the XML element {@code <afakulcsossz>}.
     */
    @JacksonXmlProperty(localName = "afakulcsossz")
    private VatSum vatSum;

    /**
     * Total summary of the invoice (net, VAT, gross).
     * Mapped to the XML element {@code <totalossz>}.
     */
    @JacksonXmlProperty(localName = "totalossz")
    private TotalSum totalSum;

    /**
     * Represents the total amounts across the invoice, including net, VAT, and gross sums.
     */
    @Getter
    public static class TotalSum {

        /**
         * The total net price of all invoice items.
         * Mapped to the XML element {@code <netto>}.
         */
        @JacksonXmlProperty(localName = "netto")
        private Double netPrice;

        /**
         * The total VAT amount across all invoice items.
         * Mapped to the XML element {@code <afa>}.
         */
        @JacksonXmlProperty(localName = "afa")
        private Double vatAmount;

        /**
         * The total gross amount (net + VAT) of the invoice.
         * Mapped to the XML element {@code <brutto>}.
         */
        @JacksonXmlProperty(localName = "brutto")
        private Double total;
    }

    /**
     * Represents the VAT group summary, showing net, VAT, and gross for a specific VAT rate.
     */
    @Getter
    public static class VatSum {

        /**
         * The VAT rate applied (e.g., 27%, 5%, 0%, AAM, M).
         * Mapped to the XML element {@code <afakulcs>}.
         */
        @JacksonXmlProperty(localName = "afakulcs")
        private String vatRate;

        /**
         * The total net amount under this VAT rate.
         * Mapped to the XML element {@code <netto>}.
         */
        @JacksonXmlProperty(localName = "netto")
        private Double netPrice;

        /**
         * The VAT amount corresponding to this VAT rate group.
         * Mapped to the XML element {@code <afa>}.
         */
        @JacksonXmlProperty(localName = "afa")
        private Double vatAmount;

        /**
         * The total gross amount (net + VAT) under this VAT rate.
         * Mapped to the XML element {@code <brutto>}.
         */
        @JacksonXmlProperty(localName = "brutto")
        private Double total;
    }
}
