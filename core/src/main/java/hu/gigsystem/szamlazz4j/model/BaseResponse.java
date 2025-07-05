package hu.gigsystem.szamlazz4j.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

/**
 * Abstract base class representing a response from the Számlázz.hu API.
 *
 * <p>This class defines common response fields such as success status, error code,
 * and error message that are typically included in all API responses.</p>
 *
 * <p>Concrete response classes should extend this base to inherit common properties.</p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@Getter
public abstract class BaseResponse {

    /**
     * Indicates whether the request was successful.
     * Mapped from the XML element {@code <sikeres>}.
     */
    @JacksonXmlProperty(localName = "sikeres")
    private boolean success;

    /**
     * Error code returned by the API if the request failed.
     * Mapped from the XML element {@code <hibakod>}.
     */
    @JacksonXmlProperty(localName = "hibakod")
    private Integer errorCode;

    /**
     * Error message returned by the API if the request failed.
     * Mapped from the XML element {@code <hibauzenet>}.
     */
    @JacksonXmlProperty(localName = "hibauzenet")
    private String error;

    /**
     * Used for responses that don't send back meaningful data, just whether it was successful or not.
     *
     * @author Tamás Tóth
     * @version 1.0.0
     * @since 5th of July, 2025
     */
    public static class SimpleResponse extends BaseResponse {
    }
}
