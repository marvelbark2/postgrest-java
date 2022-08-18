package io.supabase.builder;

import com.google.gson.Gson;
import io.supabase.builder.PostgrestFilterBuilder;
import io.supabase.utils.HttpMethod;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PostgrestQueryBuilder extends PostgrestBuilder {
    public PostgrestQueryBuilder(String url, Map<String, String> headers, String schema) {
        //
        super(url, headers, schema);
    }

    private final static Gson GSON = new Gson();


    public PostgrestFilterBuilder select(String columns) {
        setHttpMethod(HttpMethod.GET);
        AtomicBoolean quoted = new AtomicBoolean(false);
        Pattern pattern = Pattern.compile("\\s");
        String cleanedColumns = Arrays.stream(columns.split("")).map((c) -> {
            if(pattern.matcher(c).matches() && !quoted.get()) {
                return "";
            }
            if(c.equals("\"")) {
                quoted.set(! quoted.get());
            }
            return c;
        }).collect(Collectors.joining (""));
        System.out.println(cleanedColumns);
        appendSearchParams("select", cleanedColumns);
        return new PostgrestFilterBuilder(this);
    }

    public PostgrestFilterBuilder select() {
        return this.select("*");
    }

    public PostgrestFilterBuilder insert(List<Map<String, Object>> values) {
        return getPostgrestFilterBuilder(HttpMethod.POST, GSON.toJson(values));
    }

    public PostgrestFilterBuilder update(Map<String, Object> value) {
        return getPostgrestFilterBuilder(HttpMethod.PATCH, GSON.toJson(value));
    }

    public PostgrestFilterBuilder delete() {
        return getPostgrestFilterBuilder(HttpMethod.DELETE, "");
    }

    private PostgrestFilterBuilder getPostgrestFilterBuilder(HttpMethod httpMethod, String json) {
        this.setHttpMethod(httpMethod);
        headers.put("Prefer", "");
        body = json;
        return new PostgrestFilterBuilder(this);
    }
}
