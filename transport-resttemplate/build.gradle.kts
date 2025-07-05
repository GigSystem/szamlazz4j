plugins {
    id("java")
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