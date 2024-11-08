plugins {
    java
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

val restateVersion = "1.1.1"


dependencies {
    annotationProcessor("dev.restate:sdk-api-gen:$restateVersion")
    implementation("dev.restate:sdk-api:$restateVersion")
    implementation("dev.restate:sdk-serde-jackson:$restateVersion")
}

tasks.test {
    useJUnitPlatform()
}