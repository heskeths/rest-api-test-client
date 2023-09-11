import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class RestApiConnection {

    private final HttpClient client = createNewHttpClient();
    private final HttpRequest request;

    public RestApiConnection(String urlString, String requestString) {
        this.request = createNewHttpRequest(urlString, requestString);
    }

    public Response sendRequest(int count) {

        Long durationNano = 0L;

        HttpResponse<String> response;
        try {
            durationNano = System.nanoTime();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            durationNano = System.nanoTime() - durationNano;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Response(count, durationNano);
    }

    private HttpClient createNewHttpClient() {
        return HttpClient.newHttpClient();
    }

    private HttpRequest createNewHttpRequest(String urlString, String requestString) {
        URI uri;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        HttpRequest request = HttpRequest.newBuilder(uri)
                                         .timeout(Duration.of(10, SECONDS))
                                         .headers("Content-Type", "application/xml")
                                         .POST(HttpRequest.BodyPublishers.ofString(requestString))
                                         .build();

        return request;
    }
}
