# Date

## Multi pattern

Multi-pattern **uniquement pour le parsing**.

    public static final DateTimeFormatter DATE_FORMAT_WITH_TIME    = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT_WITHOUT_TIME = DateTimeFormat.forPattern("dd/MM/yyyy");
    public static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder().append(null, new DateTimeParser[] {
      DATE_FORMAT_WITH_TIME.getParser(),
      DATE_FORMAT_WITHOUT_TIME.getParser()
    }).toFormatter();
    
[source](http://andreabergia.com/parsing-dates-in-multiple-formats-in-joda-time/)

Exemple d'utilisation :

    // Parsing
    final Date date = DATE_FORMAT.parseDateTime(dateString).toDate();
    
    // Affichage (impossible d'utiliser le format multi-pattern pour faire un print sous peine de provoquer une IllegalArgumentException)
    DATE_FORMAT_WITH_TIME.print(new DateTime(date));

## Format ISO 8601

Pour parser une date par exemple `2016-04-08T16:54:41.000+02:00` :

```
yyyy-MM-dd'T'HH:mm:ss.SSSX
```