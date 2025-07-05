package hu.gigsystem.szamlazz4j.request;

import hu.gigsystem.szamlazz4j.SzamlaAgent;

import java.io.IOException;

/**
 * Represents a component responsible for executing HTTP requests
 * to the Számlázz.hu API.
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
public interface Requester {

    /**
     * Sends the given XML payload to the remote endpoint and returns the raw response as a string.
     *
     * @param xmlPayload the XML data to send in the request body
     * @param fileName   optional file name related to the request (may be used for attachments or identification)
     * @param agent      the {@link SzamlaAgent} instance initiating the request, providing context
     * @return the raw XML response from the server as a string
     * @throws IOException if there is a problem executing the request or receiving the response
     */
    String doRequest(String xmlPayload, String fileName, SzamlaAgent agent) throws IOException;

}
