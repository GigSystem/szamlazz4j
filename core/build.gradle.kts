import org.gradle.kotlin.dsl.signing
import java.util.Base64

plugins {
    java
    signing
    `maven-publish`
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("Szamlazz4j Core")
                description.set("Core of the szamlazz4j project. This module does not contain any transport options!")
                inceptionYear.set("2025")
                url.set("https://github.com/GigSystem/szamlazz4j")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://github.com/GigSystem/szamlazz4j/blob/master/LICENSE")
                        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("totht0mi")
                        name.set("Tamás Tóth")
                        url.set("https://github.com/TOTHT0MI")
                    }
                }
                scm {
                    url.set("https://github.com/GigSystem/szamlazz4j")
                    connection.set("scm:git:git://github.com/GigSystem/szamlazz4j.git")
                }
            }
        }
    }
    repositories {
        maven {
            name = "sonatype"
            val releasesRepoUrl = uri("https://central.sonatype.com/repository/maven-releases/")
            val snapshotsRepoUrl = uri("https://central.sonatype.com/repository/maven-snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            credentials {
                username = findProperty("gsCentral.username") as String?
                password = findProperty("gsCentral.password") as String?
            }
        }
    }
}

signing {
    val password: String? = findProperty("gsSigning.password") as? String
    val secretKey: String? = findProperty("gsSigning.secretKey") as? String
    println("password = ${if (password.isNullOrBlank()) "null or blank" else "set"}")
    println("secretKey = ${if (secretKey.isNullOrBlank()) "null or blank" else "set"}")
    useInMemoryPgpKeys(String(Base64.getDecoder().decode(secretKey)), password)
    sign(publishing.publications["mavenJava"])
}