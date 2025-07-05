package hu.gigsystem.szamlazz4j.request;

/**
 * Exception thrown when an XML request fails validation against its XML Schema (XSD).
 * <p>
 * This unchecked exception wraps the underlying cause of the validation failure,
 * providing a descriptive message for easier debugging.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
public class RequestValidationException extends Exception {

    /**
     * Constructs a new {@code RequestValidationException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause   the underlying cause of the validation failure
     */
    public RequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
