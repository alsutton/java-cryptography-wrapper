import net.ltgt.gradle.errorprone.errorprone
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.2.0")
    }
}

plugins {
    java
    id("net.ltgt.errorprone") version "1.3.0"
    id("com.github.ben-manes.versions") version "0.36.0"
    `maven-publish`
    jacoco
}

repositories {
    mavenCentral()
}

val junitVersion="5.7.0"
dependencies {
    implementation("org.bouncycastle:bcprov-jdk15on:1.68")

    testImplementation("com.google.truth:truth:1.1.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")

    errorprone("com.google.errorprone:error_prone_core:2.5.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.alsutton"
            artifactId = "java-cryptography-wrapper"
            version = "1.0"

            from(components["java"])

            pom {
                name.set("Java Cryptography Wrapper")
                description.set("Wrapper to provide a simple API to the Java Cryptography APIs")
                url.set("https://github.com/alsutton/java-cryptography-wrapper")
                licenses {
                    license {
                        name.set("Creative Commons CC0 Universal, Version 1.0")
                        url.set("https://github.com/alsutton/java-cryptography-wrapper/blob/main/LICENSE")
                    }
                }
            }
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone.allErrorsAsWarnings.set(false)
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        exceptionFormat = TestExceptionFormat.FULL
    }
}

jacoco {
    toolVersion = "0.8.2"
}

tasks.withType<JacocoReport> {
    group = "Reporting"
    reports {
        xml.isEnabled = true
        csv.isEnabled =  false
        html.destination = file("${buildDir}/reports/coverage")
    }
}
