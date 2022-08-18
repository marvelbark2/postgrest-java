package ws.prospeak.postgrest.builder;

public class PostgrestFilterBuilder extends PostgrestBuilder {
    private final PostgrestQueryBuilder postgrestQueryBuilder;
    public PostgrestFilterBuilder(PostgrestQueryBuilder postgrestQueryBuilder) {
        super(String.valueOf(postgrestQueryBuilder.url), postgrestQueryBuilder.headers, postgrestQueryBuilder.schema);
        this.postgrestQueryBuilder = postgrestQueryBuilder;
    }
}
