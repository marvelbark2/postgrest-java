package ws.prospeak.postgrest;

import ws.prospeak.postgrest.builder.PostgrestFilterBuilder;
import ws.prospeak.postgrest.builder.PostgrestQueryBuilder;

import java.util.Map;

public class PostgrestRpcBuilder {
    String url;
    Map<String, String> headers;
    String schema;
    public PostgrestRpcBuilder(String url, Map<String, String> headers, String schema) {
        this.url = url;
        this.headers = headers;
        this.schema = schema;
    }

    public PostgrestFilterBuilder rpc(Map<String, String> params) {
        PostgrestQueryBuilder postgrestQueryBuilder = new  PostgrestQueryBuilder(
                url,
                headers,
                schema
        );
        return new PostgrestFilterBuilder(postgrestQueryBuilder);
    }
}
