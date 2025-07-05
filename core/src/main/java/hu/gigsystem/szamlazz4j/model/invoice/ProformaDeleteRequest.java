package hu.gigsystem.szamlazz4j.model.invoice;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import hu.gigsystem.szamlazz4j.SzamlaAgent;
import hu.gigsystem.szamlazz4j.model.BaseRequest;
import hu.gigsystem.szamlazz4j.model.BaseResponse;
import lombok.Builder;

/**
 * Represents a request to delete a proforma invoice (díjbekérő) using the Számlázz.hu API.
 * This request targets the endpoint {@code szamla_agent_dijbekero_torlese}.
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 5th of July, 2025
 **/
@Builder
public class ProformaDeleteRequest extends BaseRequest<BaseResponse.SimpleResponse> {
    /**
     * Configuration and authentication details required for the request.
     */
    @JacksonXmlProperty(localName = "beallitasok", namespace = "http://www.szamlazz.hu/xmlszamladbkdel")
    private Settings settings;

    /**
     * Header containing invoice and order numbers to identify the proforma to be deleted.
     */
    @JacksonXmlProperty(localName = "fejlec", namespace = "http://www.szamlazz.hu/xmlszamladbkdel")
    private Header header;

    /**
     * Constructs a new {@code ProformaDeleteRequest} instance with predefined request name and XML file.
     */
    protected ProformaDeleteRequest(Settings settings, Header header) {
        super("szamla_agent_dijbekero_torlese", "xmlszamladbkdel");
        this.settings = settings;
        this.header = header;
    }

    /**
     * Sets the authentication agent by updating the {@link Settings} object with the agent's credentials.
     *
     * @param agent the {@link SzamlaAgent} instance containing the API credentials
     */
    @Override
    public void setAgent(SzamlaAgent agent) {
        settings.setAgent(agent);
    }

    /**
     * Contains invoice and order identifiers required to delete the correct proforma invoice.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 5th of July, 2025
     */
    @Builder
    public static class Header {

        /**
         * The number of the proforma invoice to be deleted.
         */
        @JacksonXmlProperty(localName = "szamlaszam", namespace = "http://www.szamlazz.hu/xmlszamladbkdel")
        private String invoiceNumber;

        /**
         * Optional order number associated with the invoice.
         */
        @JacksonXmlProperty(localName = "rendelesszam", namespace = "http://www.szamlazz.hu/xmlszamladbkdel")
        private String orderNumber;
    }

    /**
     * Authentication credentials used to authorize the request.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 5th of July, 2025
     */
    @Builder
    public static class Settings {

        /**
         * Username for authentication.
         */
        @JacksonXmlProperty(localName = "felhasznalo", namespace = "http://www.szamlazz.hu/xmlszamladbkdel")
        private String username;

        /**
         * Password for authentication.
         */
        @JacksonXmlProperty(localName = "jelszo", namespace = "http://www.szamlazz.hu/xmlszamladbkdel")
        private String password;

        /**
         * API key used for authenticating the request.
         */
        @JacksonXmlProperty(localName = "szamlaagentkulcs", namespace = "http://www.szamlazz.hu/xmlszamladbkdel")
        private String key;

        /**
         * Sets authentication fields using a {@link SzamlaAgent} instance.
         *
         * @param agent the {@link SzamlaAgent} containing username, password, and key
         */
        public void setAgent(SzamlaAgent agent) {
            this.key = agent.getKey();
            this.username = agent.getUsername();
            this.password = agent.getPassword();
        }
    }
}
