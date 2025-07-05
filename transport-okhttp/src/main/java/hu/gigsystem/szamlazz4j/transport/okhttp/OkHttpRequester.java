package hu.gigsystem.szamlazz4j.transport.okhttp;

import hu.gigsystem.szamlazz4j.SzamlaAgent;
import hu.gigsystem.szamlazz4j.request.Requester;
import okhttp3.*;

import java.io.IOException;

/**
 * Implementation of the {@link Requester} interface using OkHttp to send HTTP requests.
 * <p>
 * This class sends a multipart/form-data POST request containing an XML payload
 * to the Számlázz.hu API endpoint.
 * </p>
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
public class OkHttpRequester implements Requester {

    private final OkHttpClient client = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/xml");

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
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(fileName, fileName + ".xml", RequestBody.create(xmlPayload, mediaType))
                .build();

        Request request = new Request.Builder()
                .url(SzamlaAgent.ENDPOINT)
                .post(body)
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            return response.body().string();
        }
    }
}
