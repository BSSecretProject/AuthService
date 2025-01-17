plugins {
    id("java")
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.kafka:spring-kafka")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    /**
     * Spring boot starters
     */
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.3")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    /**
     * JWT
     */
    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")

    /**
     * Spring security
     */

    implementation("org.springframework.security:spring-security-crypto:6.3.3")
    implementation ("org.springframework.security:spring-security-test")

    /**
     * Lombok
     */
    implementation ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")

    /**
     * Mapper
     */

    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

    /*
    logging
     */
    implementation("com.fasterxml.jackson.core:jackson-databind")

    implementation ("org.slf4j:slf4j-api:2.0.9")
    implementation ("ch.qos.logback:logback-classic:1.4.11")

    runtimeOnly ("org.postgresql:postgresql")

}

tasks.test {
    useJUnitPlatform()
}