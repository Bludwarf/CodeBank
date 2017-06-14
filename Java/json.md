# Conversion fichier JSON -> org.json.JSONObject -> POJO

```java
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new JsonOrgModule());

final String jsonContent = FileUtils.readFileToString(new File("src/test/resources/json", filename));
final JSONObject jsonObject = new JSONObject(jsonContent);
final MonPOJO pojo = mapper.convertValue(jsonObject, MonPOJO.class);
```

# Jackson

## Annotations

  - [http://wiki.fasterxml.com/JacksonAnnotations](http://wiki.fasterxml.com/JacksonAnnotations).