package hu.gigsystem.szamlazz4j;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import hu.gigsystem.szamlazz4j.model.BaseRequest;
import hu.gigsystem.szamlazz4j.request.RequestValidationException;
import hu.gigsystem.szamlazz4j.request.RequestValidator;
import hu.gigsystem.szamlazz4j.request.Requester;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.time.Duration;

/**
 * The {@code SzamlaAgent} class is a client for interacting with the Számlázz.hu invoicing API.
 * It supports authentication via either a username/password pair or a single key, but not both.
 *
 * <p>This class is immutable and thread-safe. It uses a builder pattern for instantiation,
 * facilitated by Lombok's {@code @Builder} annotation.</p>
 *
 * <p>The class provides functionality to send requests to the Számlázz.hu endpoint, validate requests
 * against XML schemas, and deserialize responses into Java objects.</p>
 *
 * <p>Optional global fields such as bank information, email reply-to address, and signer name
 * can be set and will be included in requests if applicable.</p>
 *
 * <p>The {@code requester} is an abstraction responsible for executing the actual HTTP request,
 * allowing customization or mocking during testing.</p>
 *
 * <p>The class uses a configured {@link XmlMapper} for XML serialization/deserialization
 * with specific settings, including writing the XML declaration and ignoring unknown properties.</p>
 *
 * <h2>Authentication</h2>
 * <ul>
 *   <li>Either {@code key} must be provided</li>
 *   <li>Or both {@code username} and {@code password} must be provided</li>
 *   <li>Both authentication methods cannot be used simultaneously</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * SzamlaAgent agent = SzamlaAgent.builder()
 *                                .username("user")
 *                                .password("pass")
 *                                .requester(new NativeJavaRequester())
 *                                .build();
 *
 * InvoiceRequest request = new InvoiceRequest(...);
 * InvoiceResponse response = agent.sendRequest(request);
 * }</pre>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
@Builder(builderClassName = "Builder")
public class SzamlaAgent {

    public static final String ENDPOINT = "https://www.szamlazz.hu/szamla/";

    /**
     * The username used for authentication with the Számlázz.hu API.
     * Required if {@code key} is not provided.
     */
    private final String username;

    /**
     * The password used for authentication with the Számlázz.hu API.
     * Required if {@code key} is not provided.
     */
    private final String password;

    /**
     * The API key used for authentication with the Számlázz.hu API.
     * Required if {@code username} and {@code password} are not provided.
     */
    private final String key;

    /**
     * Optional bank name associated with the invoicing account.
     * Included in requests if provided.
     */
    private final String bank;

    /**
     * Optional bank account number associated with the invoicing account.
     * Included in requests if provided. If this is property set on the request builder, then that takes priority.
     */
    private final String bankNumber;

    /**
     * Optional email address used as the reply-to address in outgoing emails.
     * If this is property set on the request builder, then that takes priority.
     */
    private final String emailReplyTo;

    /**
     * Optional name of the document signer.
     * If this is property set on the request builder, then that takes priority.
     */
    private final String signerName;

    /**
     * Flag indicating whether caching of responses is enabled.
     * If this is property set on the request builder, then that takes priority.
     */
    @lombok.Builder.Default
    private final boolean enableCaching = true;

    /**
     * The {@link Requester} implementation responsible for executing HTTP requests.
     * Must not be {@code null}.
     */
    @NonNull
    private final Requester requester;

    /**
     * The {@link XmlMapper} instance used internally for XML serialization and deserialization.
     * This field is initialized automatically and is not exposed via the builder.
     */
    private final XmlMapper mapper = createMapper();

    private final Cache<Integer, Object> responseCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofMinutes(5))
            .build();

    /**
     * Constructs a new {@code SzamlaAgent} instance. Used by Lombok.
     *
     * <p>Authentication must be done using either a {@code key} or a combination of {@code username} and {@code password}.
     * Providing both authentication methods simultaneously or missing required credentials will throw an {@link IllegalArgumentException}.</p>
     *
     * @param username      the username for authentication (not needed if {@code key} is provided)
     * @param password      the password for authentication (not needed if {@code key} is provided)
     * @param key           the API key for authentication (not needed if {@code username} and {@code password} are provided)
     * @param bank          optional bank name to be included in requests
     * @param bankNumber    optional bank account number to be included in requests
     * @param emailReplyTo  optional email address used as reply-to in requests
     * @param signerName    optional name of the signer for documents
     * @param enableCaching whether to enable caching of responses
     * @param requester     the {@link Requester} instance used to execute HTTP requests (must not be null)
     * @throws IllegalArgumentException if authentication parameters are invalid or missing
     */
    protected SzamlaAgent(String username, String password, String key, String bank, String bankNumber, String emailReplyTo, String signerName, boolean enableCaching, @NonNull Requester requester) {
        if (key != null && (username != null || password != null)) {
            throw new IllegalArgumentException("Only combinations [key] or [username & password] is allowed!");
        }
        if (key == null && (username == null || password == null || username.isEmpty() || password.isEmpty())) {
            throw new IllegalArgumentException("Only combinations [key] or [username & password] is allowed!");
        }
        this.username = username;
        this.password = password;
        this.key = key;
        this.bank = bank;
        this.bankNumber = bankNumber;
        this.emailReplyTo = emailReplyTo;
        this.signerName = signerName;
        this.enableCaching = enableCaching;
        this.requester = requester;
    }

    /**
     * Creates and configures a new {@link XmlMapper} instance used for XML serialization and deserialization.
     *
     * <p>The mapper is configured to:</p>
     * <ul>
     *   <li>Write the XML declaration</li>
     *   <li>Ignore unknown properties during deserialization</li>
     *   <li>Include non-null and non-empty properties only during serialization</li>
     *   <li>Automatically register available modules</li>
     * </ul>
     *
     * @return a configured {@link XmlMapper} instance
     */
    private static XmlMapper createMapper() {
        return (XmlMapper) new XmlMapper()
                .configure(com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * Sends a request to the Számlázz.hu API and parses the response into an object of the requested type.
     *
     * <p>The given {@link BaseRequest} will be validated and serialized to XML before being sent.</p>
     *
     * @param <T>     the type of the expected response object
     * @param request the request object to send (must not be null)
     * @return the response deserialized from XML into an object of type {@code T}
     * @throws RuntimeException if the underlying request execution fails or response cannot be parsed
     */
    public <T> T sendRequest(BaseRequest<T> request, Class<T> clazz) throws IOException, RequestValidationException {
        request.setAgent(this);
        String data = validateRequest(request);

        if (enableCaching) {
            Object cachedData = responseCache.getIfPresent(request.hashCode());
            if (cachedData != null) {
                return clazz.cast(cachedData);
            }

            String rawResponse = requester.doRequest(data, request.getFileName(), this);
            T response = mapper.readValue(rawResponse, clazz);
            responseCache.put(request.hashCode(), response);
            return response;
        }

        String rawResponse = requester.doRequest(data, request.getFileName(), this);
        return mapper.readValue(rawResponse, clazz);
    }

    /**
     * Validates the given request object by serializing it to XML and validating the resulting XML against the request's schema.
     *
     * <p>This method ensures the request conforms to the expected XML structure before sending it to the API.</p>
     *
     * @param request the request to validate (must not be null)
     * @return the serialized XML string of the request
     * @throws RequestValidationException if validation fails
     */
    public String validateRequest(BaseRequest<?> request) throws RequestValidationException {
        String data;
        try {
            data = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RequestValidationException("Request validation failed!", e);
        }
        RequestValidator.validateRequest(data, request.getSchemaFile());
        return data;
    }
}
