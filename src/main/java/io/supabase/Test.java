package io.supabase;

import io.supabase.builder.PostgrestFilterBuilder;
import io.supabase.builder.PostgrestQueryBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String url = "";
        String pubKey = "";

        Map<String, String> headers = new HashMap<>(Map.of("apikey", pubKey ));

        String schema = "api";

        PostgrestClient client = new PostgrestClient(
            url, headers, schema
        );

        PostgrestQueryBuilder contactQB = client.from("contacts");

        PostgrestFilterBuilder contactsFilter = contactQB.select("id, name");

        Contact[] contactsName = contactsFilter.get(Contact[].class);

        System.out.println(Arrays.toString(contactsName));

        PostgrestFilterBuilder allContactsFilter = contactQB.select();

        Contact[] allContacts = allContactsFilter.get(Contact[].class);

        System.out.println(Arrays.toString(allContacts));

    }
}
