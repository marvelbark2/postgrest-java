package ws.prospeak.postgrest;

import ws.prospeak.postgrest.builder.PostgrestFilterBuilder;
import ws.prospeak.postgrest.builder.PostgrestQueryBuilder;

import java.util.HashMap;
import java.util.Map;

public class PostgrestClient {
    private final String url;
    private final Map<String, String> headers;

    private final String schema;

    public PostgrestClient(String url, Map<String, String> headers, String schema) {
        this.url = url;
        this.headers = headers;
        this.schema = schema;
    }

    public PostgrestClient(String url, String schema) {
        this.url = url;
        this.schema = schema;
        this.headers = new HashMap<>();
    }


    public PostgrestClient auth(String token) {
        headers.put("Authorization", "Bearer " + token);
        return this;
    }

    public PostgrestQueryBuilder from(String table) {
        String url = String.format("%s/%s", this.url, table);
        return new PostgrestQueryBuilder(
                    url,
                    headers,
                    schema
        );
    }

    public PostgrestFilterBuilder rpc(String fn, Map<String, String> params) {
        String rpcUrl = String.format("%s/rpc/%s", this.url, fn);
                //'${this.url}/rpc/$fn';
        return new PostgrestRpcBuilder(
                rpcUrl,
                headers,
                schema
            ).rpc(params);
    }
}
