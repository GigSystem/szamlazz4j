# szamlazz4j

A **szamlazz4j** a SzámlaAgent API Java nyelvű wrapperje.  
A projekt függetlenül működik a Számlázz.hu-tól.

---

## Áttekintés

A szamlazz4j egyszerű és rugalmas megoldást nyújt a SzámlaAgent API integrálására Java (illetve bármilyen JVM alapú) alkalmazásokban. A wrapper moduláris felépítésű, így különböző HTTP kliensekkel és környezetekkel kompatibilis.

A projekt csak egy wrapper, a felhasználónak köteles a Számlázz.hu által leírt szabályoknak megfelelni. Az ehhez kapcsolódó fontos oldalak:
- https://docs.szamlazz.hu/hu/agent/basics/agent-user
- https://docs.szamlazz.hu/hu/agent/basics/error-handling
- https://docs.szamlazz.hu/hu/agent/basics/details

> [!WARNING]  
> A SzámlaAgent használata költségeket von maga után, továbbá egyes számlabeállítások további díjakat eredményezhetnek (lsd. e-számla)!

## Támogatott funkciók

Itt látható a Számlázz API-ból implementált funkciók.

> [!NOTE]  
> Production használat előtt a funkciókat érdemes teszt módban letesztelni, a megfelelő működés tesztelése érdekében!

| Funkció                        | Állapot             |
|-------------------------------|---------------------|
| Számla létrehozás             | ✅ Támogatott        |
| Számla PDF lekérése           | ✅ Támogatott        |
| Számla sztornó                | ✅ Támogatott        |
| Befizetés rögzítése           | ✅ Támogatott    |
| Számla XML lekérése           | ✅ Támogatott    |
| Díjbekérő törlése             | ✅ Támogatott   |
| Nyugta létrehozás             | ❌ Nem támogatott    |
| Nyugta sztornó                | ❌ Nem támogatott    |
| Nyugta lekérdezés             | ❌ Nem támogatott    |
| Nyugta kiküldés              | ❌ Nem támogatott    |
| Adószám lekérdezése           | ❌ Nem támogatott    |


## Projekt felépítése

A maximális rugalmasság érdekében a szamlazz4j több modulból áll, így környezethez igazodva különböző HTTP klienseket használhatunk:

- **core** — a wrapper motorja
- **transport-resttemplate** — Spring RestTemplate implementáció
- **transport-okhttp** — OkHttp implementáció

A modulok szabadon kombinálhatók az igények szerint.

## Telepítés

> [!IMPORTANT]  
> A transport modulok önmagukban nem tartalmazzák az általuk implementált klienst. Tehát a például a projektbe az okhttp-t a fejlesztőnek kell hozzáadnia!


Jelenleg a projekt tesztüzemként működik, és csak a snapshot repository-ról érhető el:
```kotlin
repositories {
  maven {
    name = "Central Portal Snapshots"
    url = URI("https://central.sonatype.com/repository/maven-snapshots/")
  }
  mavenCentral()
}
```

```xml
<repositories>
  <repository>
    <name>Central Portal Snapshots</name>
    <id>central-portal-snapshots</id>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
    <releases>
      <enabled>false</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
```

### Gradle (Kotlin DSL)
```kotlin
implementation("hu.gigsystem.szamlazz4j:core:<verzió>")

// Opcionális transport modulok
implementation("hu.gigsystem.szamlazz4j:transport-resttemplate:<verzió>")
implementation("hu.gigsystem.szamlazz4j:transport-okhttp:<verzió>")
```

### Maven
```xml
<dependency>
    <groupId>hu.gigsystem.szamlazz4j</groupId>
    <artifactId>core</artifactId>
    <version>&lt;verzió&gt;</version>
</dependency>

<!-- Opcionális transport modulok -->
<dependency>
    <groupId>hu.gigsystem.szamlazz4j</groupId>
    <artifactId>transport-resttemplate</artifactId>
    <version>&lt;verzió&gt;</version>
</dependency>

<dependency>
    <groupId>hu.gigsystem.szamlazz4j</groupId>
    <artifactId>transport-okhttp</artifactId>
    <version>&lt;verzió&gt;</version>
</dependency>
```
## Használat
A kódban legyen egy újrahasznált SzamlaAgent kliens:
```java
public static final SzamlaAgent AGENT = new SzamlaAgent.Builder()
        .requester(new OkHttpRequester())
        .key("<szamla-agent-kulcs>")
        .bank("Globális bank")
        .bankNumber("számlaszám")
        .build();

// Vagy Spring beanként
@Bean
public SzamlaAgent szamlaAgent() {
    return new SzamlaAgent.Builder()
            .requester(new RestTemplateRequester())
            .key("<szamla-agent-kulcs>")
            .bank("Globális bank")
            .bankNumber("számlaszám")
            .build();
}
```
Ez után a kérések és a szükséges adatok Factory-kal létrehoztóak, majd a kérés elküldhető:

```java
InvoiceRequest request = InvoiceRequest.builder()
        .header(header)
        .seller(seller)
        .customer(buyer)
        .settings(settings)
        .items(items)
        .build();

try {
    XmlInvoiceResponse response = agent.sendRequest(request);
    if (!response.isSuccess()) {
        // lekezeles
    }
} catch (IOException e) {
    // Lekezeles
     e.printStackTrace();
} catch (RequestValidationException e) {
    // Lekezeles
    e.printStackTrace();
}
```

## Rólunk
A szamlazz4j fejlesztője és fenntartója a GigSystem Kft., egy zenei-technológiai cég.
Mivel mi magunk is számos nyílt forráskódú megoldást használunk, elkötelezettek vagyunk a közösség támogatása mellett nyílt forráskódú eszközökkel.

Weboldalunk: https://www.gigsystem.hu/

## Licenc

```
Copyright 2025 GigSystem Kft.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```