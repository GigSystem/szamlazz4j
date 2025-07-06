package hu.gigsystem.szamlazz4j.model.invoice.create;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import hu.gigsystem.szamlazz4j.SzamlaAgent;
import lombok.Builder;

/**
 * Represents configuration settings for creating or managing invoices
 * via the Számlázz.hu API.
 *
 * <p>This class uses Jackson XML annotations to map fields to XML elements
 * with the specified namespace. It supports builder pattern for easy construction.</p>
 *
 * <p>Some fields have default values to control invoice behavior, such as
 * whether to generate electronic invoices or download them automatically.</p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Builder
public class InvoiceSettings {

    /**
     * The API key used for authentication.
     */
    @JacksonXmlProperty(localName = "szamlaagentkulcs", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String key;
    /**
     * Username for authentication.
     */
    @JacksonXmlProperty(localName = "felhasznalo", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String username;
    /**
     * Password for authentication.
     */
    @JacksonXmlProperty(localName = "jelszo", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String password;
    /**
     * Whether to generate electronic invoices.
     * Defaults to {@code false}.
     */
    @Builder.Default
    @JacksonXmlProperty(localName = "eszamla", namespace = "http://www.szamlazz.hu/xmlszamla")
    private boolean electornicInvoice = false;
    /**
     * Whether the invoice should be downloaded automatically.
     * Defaults to {@code false}.
     */
    @Builder.Default
    @JacksonXmlProperty(localName = "szamlaLetoltes", namespace = "http://www.szamlazz.hu/xmlszamla")
    private boolean downloadInvoice = false;
    /**
     * The number of copies to download for the invoice.
     */
    @JacksonXmlProperty(localName = "szamlaLetoltesPld", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Integer downloadInvoiceNumber;

    /**
     * The version of the response expected from the API.
     * Must be {@code 2}.
     */
    @Builder.Default
    @JacksonXmlProperty(localName = "valaszVerzio", namespace = "http://www.szamlazz.hu/xmlszamla")
    private final Integer answerType = 2;

    /**
     * Optional aggregator identifier, if used.
     */
    @JacksonXmlProperty(localName = "aggregator", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String aggregator;

    /**
     * Indicates if guardian service is enabled.
     */
    @JacksonXmlProperty(localName = "guardian", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean guardian;

    /**
     * Indicates if commodity ID invoice is enabled.
     */
    @JacksonXmlProperty(localName = "cikkazoninvoice", namespace = "http://www.szamlazz.hu/xmlszamla")
    private Boolean commodityIdInvoice;

    @JacksonXmlProperty(localName = "szamlaKulsoAzon", namespace = "http://www.szamlazz.hu/xmlszamla")
    private String externalId;

    /**
     * Copies authentication data from the given {@link SzamlaAgent} into this settings instance.
     *
     * @param agent the {@link SzamlaAgent} from which to copy key, username, and password
     */
    public void setAgent(SzamlaAgent agent) {
        this.key = agent.getKey();
        this.username = agent.getUsername();
        this.password = agent.getPassword();
    }
}
