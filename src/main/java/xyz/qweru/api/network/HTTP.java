package xyz.qweru.api.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static xyz.qweru.gruvhack.Gruvhack.LOGGER;

public class HTTP {
    private final URI uri;

    public HTTP(URI baseUri) {
        this.uri = baseUri;
    }

    public String get(String path) throws IOException {
        return request(path, "GET");
    }

    public String get() throws IOException {
        return get("/");
    }

    public String put(String path) throws IOException {
        return request(path, "PUT");
    }

    public String request(String path, String method) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = this.uri.resolve(path).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }
}
