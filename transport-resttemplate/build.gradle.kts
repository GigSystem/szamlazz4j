import java.util.Base64

plugins {
    id("java")
    signing
    `maven-publish`
}

group = "hu.gigsystem.szamlazz4j"

repositories {
    mavenCentral()
}

val springVersion = "6.2.8"

dependencies {
    compileOnly(project(":core"))
    compileOnly("org.springframework:spring-web:$springVersion")

    testImplementation("org.springframework:spring-web:$springVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
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