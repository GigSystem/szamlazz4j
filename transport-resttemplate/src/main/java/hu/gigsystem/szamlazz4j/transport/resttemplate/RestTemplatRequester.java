package hu.gigsystem.szamlazz4j.transport.resttemplate;

import hu.gigsystem.szamlazz4j.SzamlaAgent;
import hu.gigsystem.szamlazz4j.request.Requester;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of the {@link Requester} interface using Spring's RestTemplate to send HTTP requests.
 * <p>
 * This class sends a multipart/form-data POST request containing an XML payload
 * to the Számlázz.hu API endpoint.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 5th of July, 2025
 */
public class RestTemplatRequester implements Requester {

    private final RestTemplate template = new RestTemplate();

    /**
     * Sends an HTTP POST request to the Számlázz.hu endpoint with the given XML payload
     * packaged as a multipart form data.
     *
     * @param xmlPayload the XML content to send as part of the request body
     * @param fileName   the form data part name and the file name for the XML content
     * @param agent      the {@link SzamlaAgent} providing authentication credentials (not directly used here)
     * @return the raw response body string returned by the server
     * @throws IOException if there is a network or I/O error during the request
     */
    @Override
    public String doRequest(String xmlPayload, String fileName, SzamlaAgent agent) throws IOException {
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource file = getResourceFromPayload(xmlPayload, fileName);
        body.add(fileName, file);
        try {
            return doRequest(body);
        } catch (RestClientException e) {
            throw new IOException("Szamlazz request resulted in error!", e);
        }
    }

    /**
     * Does the actual request to Számlázz.
     *
     * @param body the multipart form data
     * @return the raw response XML
     * @throws RestClientException if the request fails or there is a network or I/O error
     */
    private String doRequest(LinkedMultiValueMap<String, Object> body) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> raw = template.exchange(SzamlaAgent.ENDPOINT, HttpMethod.POST, request, String.class);
        return raw.getBody();
    }

    /**
     * Creates a byte array resource from the raw xml payload, so we can upload as a file.
     *
     * @param xmlPayload the raw XML string data
     * @param fileName the name of the file
     * @return the generated resource
     */
    private ByteArrayResource getResourceFromPayload(String xmlPayload, String fileName) {
        return new ByteArrayResource(xmlPayload.getBytes(StandardCharsets.UTF_8)) {
            @Override
            public String getFilename() {
                return fileName + ".xml";
            }
        };
    }
}
