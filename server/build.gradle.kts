plugins {
    java
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("org.xerial:sqlite-jdbc:3.45.2.0")
    implementation("com.google.code.gson:gson:2.13.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
