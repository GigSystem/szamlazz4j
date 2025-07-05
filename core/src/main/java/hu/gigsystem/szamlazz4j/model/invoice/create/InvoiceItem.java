package hu.gigsystem.szamlazz4j.model.invoice.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;

import java.time.LocalDate;

/**
 * Represents a single item (line) in an invoice.
 * <p>
 * Maps XML elements from the "http://www.szamlazz.hu/xmlszamla" namespace.
 * </p>
 * <p>
 * Contains pricing, quantity, VAT, and descriptive information for the invoice item.
 * For the VAT type needed, please consult <a href="https://docs.szamlazz.hu/hu/agent/generating_invoice/vat-rates">...</a> and your accountant!
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Builder
public class InvoiceItem {

    /**
     * VAT code: TEHK
     */
    public static String VAT_TEHK = "TEHK";
    /**
     * VAT code: TAHK
     */
    public static String VAT_TAHK = "TAHK";
    /**
     * VAT code: TAM
     */
    public static String VAT_TAM = "TAM";
    /**
     * VAT code: AAM
     */
    public static String VAT_AAM = "AAM";
    /**
     * VAT code: EUT
     */
    public static String VAT_EUT = "EUT";
    /**
     * VAT code: EUKT
     */
    public static String VAT_EUKT = "EUKT";
    /**
     * VAT code: F.AFA
     */
    public static String VAT_F_AFA = "F.AFA";
    /**
     * VAT code: K.AFA
     */
    public static String VAT_K_AFA = "K.AFA";
    /**
     * VAT code: HO
     */
    public static String VAT_HO = "HO";
    /**
     * VAT code: EUE
     */
    public static String VAT_EUE = "EUE";
    /**
     * VAT code: EUFADE
     */
    public static String VAT_EUFADE = "EUFADE";
    /**
     * VAT code: EUFAD37
     */
    public static String VAT_EUFAD37 = "EUFAD37";
    /**
     * VAT code: ATK
     */
    public static String VAT_ATK = "ATK";
    /**
     * VAT code: NAM
     */
    public static String VAT_NAM = "NAM";
    /**
     * VAT code: EAM
     */
    public static String VAT_EAM = "EAM";
    /**
     * VAT code: KBAUK
     */
    public static String VAT_KBAUK = "KBAUK";
    /**
     * VAT code: KBAET
     */
    public static String VAT_KBAET = "KBAET";

    /**
     * Name or description of the invoice item.
     */
    @JacksonXmlProperty(localName = "megnevezes", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String name;

    /**
     * Unique identifier or SKU of the item.
     */
    @JacksonXmlProperty(localName = "azonosito", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String id;

    /**
     * Quantity of the item being invoiced.
     */
    @JacksonXmlProperty(localName = "mennyiseg", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Double quantity;

    /**
     * Unit of measurement for the quantity (e.g., pcs, kg).
     */
    @JacksonXmlProperty(localName = "mennyisegiEgyseg", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String quantityUnit;

    /**
     * Net unit price for the item, excluding VAT.
     */
    @JacksonXmlProperty(localName = "nettoEgysegar", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Double netUnitPrice;

    /**
     * VAT rate code applied to this item (use the VAT_* constants).
     */
    @JacksonXmlProperty(localName = "afakulcs", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String vatRate;

    /**
     * Margin VAT base amount, if applicable (used for margin scheme VAT).
     */
    @JacksonXmlProperty(localName = "arresAfaAlap", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Double marginVatBase;

    /**
     * Net total price for the item (quantity * unit price), excluding VAT.
     */
    @JacksonXmlProperty(localName = "nettoErtek", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Double netPrice;

    /**
     * VAT amount calculated for this item.
     */
    @JacksonXmlProperty(localName = "afaErtek", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Double vatAmount;

    /**
     * Total gross price for this item including VAT.
     */
    @JacksonXmlProperty(localName = "bruttoErtek", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Double totalPrice;

    /**
     * Optional note or comment about the item.
     */
    @JacksonXmlProperty(localName = "megjegyzes", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String note;

    @JacksonXmlProperty(localName = "tetelFokonyv")
    private ItemLedger itemLedger;

    /**
     * Code indicating deletion or cancellation of the item (if applicable).
     */
    @JacksonXmlProperty(localName = "torloKod")
    private Integer deleteCode;

    /**
     * Represents item-level ledger (tétel főkönyv) information for invoice lines in the Számlázz.hu system.
     * This structure is typically used to provide detailed bookkeeping attributes
     * for each invoice item, enabling integration with accounting systems.
     *
     * <p>It contains general ledger codes, economic events, VAT-specific ledger codes,
     * and the accounting period related to the specific item.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 5th of July, 2025
     */
    @Builder
    public static class ItemLedger {

        /**
         * The type of economic event associated with the item (gazdasági esemény),
         * such as sale, service, or other accounting category.
         */
        @JacksonXmlProperty(localName = "gazdasagiEsem", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String economicEvent;

        /**
         * The type of VAT-related economic event (gazdasági esemény ÁFA),
         * used for VAT categorization in accounting.
         */
        @JacksonXmlProperty(localName = "gazdasagiEsemAfa", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String economicEventVat;

        /**
         * General ledger account number used for sales revenue (árbevétel főkönyvi szám).
         */
        @JacksonXmlProperty(localName = "arbevetelFokonyviSzam", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String salesRevenueLedgerNumber;

        /**
         * General ledger account number used for VAT accounting (ÁFA főkönyvi szám).
         */
        @JacksonXmlProperty(localName = "afaFokonyviSzam", namespace = "http://www.szamlazz.hu/xmlszamla")
        private String vatLedgerNumber;

        /**
         * Start date of the accounting period for the item (elszámolási időszak kezdete).
         */
        @JacksonXmlProperty(localName = "elszDatumTol", namespace = "http://www.szamlazz.hu/xmlszamla")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate accountFrom;

        /**
         * End date of the accounting period for the item (elszámolási időszak vége).
         */
        @JacksonXmlProperty(localName = "elszDatumIg", namespace = "http://www.szamlazz.hu/xmlszamla")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate accountTo;
    }

}
