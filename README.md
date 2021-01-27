## Einfacher Vokabeltrainer Deutsch -> Englisch

Da im Homeschooling oft nicht die Zeit da ist, um beim Nachwuchs die Vokabeln abzuhören,
ein simpler Vokabeltrainer (DEU->ENG)

Build & run
-----------

**Voraussetzungen:**

* Java 15
* Apache Maven (https://maven.apache.org/)

```bash
./src/main/resources/application.properties
```

Die Liste decr Vokabeln kann in der Datei 

```bash
./src/main/resources/data.csv
```

editiert/erweitert werden.

Build mit 

```bash
mvn package
```

und 

```bash
java -jar target/vocabulary-trainer-1.0.0-SNAPSHOT.jar
```

oder

```bash
mvn spring-boot:run
```

um die Anwendung auszuführen.

Anwendung ist im Browser unter der URL

```bash
http://localhost:8080
```

erreichbar.