plugins {
    id("java")
}

group = "hu.gigsystem.szamlazz4j"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":core"))
    compileOnly(platform("com.squareup.okhttp3:okhttp-bom:5.0.0"))
    compileOnly("com.squareup.okhttp3:okhttp")

    testImplementation(platform("com.squareup.okhttp3:okhttp-bom:5.0.0"))
    testImplementation("com.squareup.okhttp3:okhttp")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}