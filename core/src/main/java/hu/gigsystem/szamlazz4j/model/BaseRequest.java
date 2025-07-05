package hu.gigsystem.szamlazz4j.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.gigsystem.szamlazz4j.SzamlaAgent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Abstract base class representing a request to the Számlázz.hu API.
 *
 * <p>Generic type parameter {@code T} defines the expected response type
 * corresponding to this request.</p>
 *
 * <p>This class encapsulates common metadata required for requests such as
 * the XML schema file used for validation and the file name used in multipart uploads.</p>
 *
 * <p>The class is designed to be extended by concrete request implementations.</p>
 *
 * @param <T> the type of the response expected from this request
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseRequest<T> {

    /**
     * The file name to use when sending the request as a multipart file part.
     * This value is ignored during JSON/XML serialization.
     */
    @JsonIgnore
    private final String fileName;

    /**
     * The name of the XML schema file (.xsd) used to validate this request.
     * This value is ignored during JSON/XML serialization.
     */
    @JsonIgnore
    private final String schemaFile;

    /**
     * Sets the {@link SzamlaAgent} instance that will send this request.
     * <p>
     * Concrete subclasses should implement this method to accept and store
     * the agent, if needed, to customize request behavior or access global data.
     * </p>
     *
     * @param agent the {@link SzamlaAgent} responsible for sending this request
     */
    public abstract void setAgent(SzamlaAgent agent);
}
