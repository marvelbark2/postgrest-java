package ws.prospeak.postgrest.builder;

import com.google.gson.Gson;
import ws.prospeak.postgrest.utils.HttpMethod;
import ws.prospeak.postgrest.utils.URLUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class PostgrestBuilder {
    private final static Gson GSON = new Gson();
    protected URI url;
    private static HttpMethod httpMethod;

    protected Map<String, String> headers;
    protected String schema;

    protected static String body;

    private static final Map<String, String> queryParams = new HashMap<>();


    protected void setHttpMethod(HttpMethod method) {
        httpMethod = method;
    }
    public PostgrestBuilder(String url, Map<String, String> headers, String schema) {
        try {
            this.url = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.headers = headers;
        this.schema = schema;
    }
    protected void appendSearchParams(String key, String value) {
        //final searchParams = Map<String, dynamic>.from(_url.queryParametersAll);
        //    searchParams[key] = [...searchParams[key] ?? [], value];
        //    _url = _url.replace(queryParameters: searchParams);

        queryParams.put(key, value);
    }

    public <T> T get(Class<T> to) {
        HttpRequest request = getHttpRequest();
        try {
            HttpResponse<String> response = getHttpResponse(request);
            String json = response.body();
            System.out.println(json);
            queryParams.clear();
            return GSON.fromJson(json, to);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    private HttpRequest getHttpRequest() {
        bootstrapURI();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(this.url);
        for(String key: headers.keySet()) {
            requestBuilder = requestBuilder.setHeader(key, headers.get(key));
        }
        System.out.println(this.url);
        if(httpMethod.equals(HttpMethod.GET)) {
            requestBuilder = requestBuilder.GET();
        } else if(httpMethod.equals(HttpMethod.POST)) {
            requestBuilder = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));
        } else if(httpMethod.equals(HttpMethod.PATCH)) {
            requestBuilder = requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(body));
        } else if(httpMethod.equals(HttpMethod.DELETE)) {
            requestBuilder = requestBuilder.DELETE();
        }


        return requestBuilder.build();
    }

    private void bootstrapURI() {
        String queries = URLUtils.urlEncodeUTF8(queryParams);
        System.out.println("Queries, " + queries + " / " + queryParams);
        try {
             this.url = URLUtils.appendUri(this.url, queries);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse<String> getHttpResponse(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }
}
