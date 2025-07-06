package hu.gigsystem.szamlazz4j.model.invoice.cancel;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import hu.gigsystem.szamlazz4j.SzamlaAgent;
import lombok.Builder;

/**
 * Configuration settings for cancelling an invoice using the Számlázz.hu API.
 * <p>
 * This class contains authentication credentials and behavioral flags that
 * influence how the cancellation request is processed.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Builder
public class CancelSettings {

    /**
     * Username for authentication.
     */
    @JacksonXmlProperty(localName = "felhasznalo", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private String username;
    /**
     * Password for authentication.
     */
    @JacksonXmlProperty(localName = "jelszo", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private String password;
    /**
     * The API key used for authentication.
     */
    @JacksonXmlProperty(localName = "szamlaagentkulcs", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private String key;
    /**
     * Whether to generate electronic invoices.
     * Defaults to {@code false}.
     */
    @Builder.Default
    @JacksonXmlProperty(localName = "eszamla", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private boolean electornicInvoice = false;
    /**
     * Whether the invoice should be downloaded automatically.
     * Defaults to {@code false}.
     */
    @Builder.Default
    @JacksonXmlProperty(localName = "szamlaLetoltes", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private boolean downloadInvoice = false;
    /**
     * The number of copies to download for the invoice.
     */
    @JacksonXmlProperty(localName = "szamlaLetoltesPld", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private Integer downloadInvoiceNumber;
    /**
     * Optional aggregator identifier, if used.
     */
    @JacksonXmlProperty(localName = "aggregator", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private String aggregator;
    /**
     * Indicates if guardian service is enabled.
     */
    @JacksonXmlProperty(localName = "guardian", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private Boolean guardian;

    /**
     * The version of the response expected from the API.
     * Must be {@code 2}.
     */
    @Builder.Default
    @JacksonXmlProperty(localName = "valaszVerzio", namespace = "http://www.szamlazz.hu/xmlszamlast")
    private final Integer answerType = 2;

    /**
     * The external id of the invoice.
     */
    @JacksonXmlProperty(localName = "szamlaKulsoAzon", namespace = "http://www.szamlazz.hu/xmlszamlast")
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
