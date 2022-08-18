# postgrest-java

Current version: Memo


example: 
```java
        //rest URL
        String url = "";

        // public key
        String pubKey = "";
        
        //Example map to preload request header with payload like apikey
        Map<String, String> headers = new HashMap<>(Map.of("apikey", pubKey ));

        String schema = "api";

        PostgrestClient client = new PostgrestClient(
            url, headers, schema
        );

        PostgrestQueryBuilder contactQB = client.from("contacts");
        
        // SELECT id, name FROM contacts;
        PostgrestFilterBuilder contactsFilter = contactQB.select("id, name");
        
        // Contact is just a POJO class
        Contact[] contactsName = contactsFilter.get(Contact[].class);

        System.out.println(Arrays.toString(contactsName));

        // SELECT * FROM contacts;
        PostgrestFilterBuilder allContactsFilter = contactQB.select();

        Contact[] allContacts = allContactsFilter.get(Contact[].class);

        System.out.println(Arrays.toString(allContacts));


        contactQB.insert(new ArrayList(new HashMap<>(
                Map.of("col", "val", "col2", "val2", ...)
        ))).get(...)
        
        //Always call get method to fetch the api
        
```