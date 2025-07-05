plugins {
    java
    id("com.vanniktech.maven.publish") version "0.33.0"
}

repositories {
    mavenCentral()
}

val jacksonVersion = "2.19.1"
val lombok = "1.18.38"
val logback = "1.5.18"
val slf4j = "2.0.17"
val caffeine = "3.2.1"

group = "hu.gigsystem.szamlazz4j"

dependencies {
    implementation("com.github.ben-manes.caffeine:caffeine:$caffeine")
    implementation(platform("com.fasterxml.jackson:jackson-bom:$jacksonVersion"))
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.core:jackson-core")

    implementation("org.slf4j:slf4j-api:$slf4j")

    compileOnly("org.projectlombok:lombok:$lombok")
    annotationProcessor("org.projectlombok:lombok:$lombok")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("ch.qos.logback:logback-classic:$logback")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates("hu.gigsystem.szamlazz4j", "core", project.version.toString())
    pom {
        name.set("Szamlazz4j Core")
        description.set("Core of the szamlazz4j project. This module does not contain any transport options!")
        inceptionYear.set("2025")
        url.set("https://github.com/GigSystem/szamlazz4j")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
    }
}