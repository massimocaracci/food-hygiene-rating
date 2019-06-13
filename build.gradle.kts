plugins {
    id("java")
    id("idea")
    id("eclipse")
    id("maven-publish")
    id("com.palantir.docker") version "0.20.1"
    id("org.springframework.boot") version "2.1.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

java {
    sourceCompatibility = JavaVersion.VERSION_12
    targetCompatibility = JavaVersion.VERSION_12
}

group = "uk.co.pantasoft.fhr"
version = "1.0.0-SNAPSHOT"

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-parent:2.1.3.RELEASE")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Greenwich.RELEASE")
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.9.7")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.9.7")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.7")

    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("org.apache.commons:commons-lang3:3.8.1")
    implementation("org.apache.commons:commons-collections4:4.0")

    implementation("io.springfox:springfox-swagger2:2.7.0")
    implementation("io.springfox:springfox-swagger-ui:2.7.0")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("io.github.openfeign:feign-httpclient")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("redis.clients:jedis:3.1.0-m2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("junit:junit:4.12")
    testImplementation("com.github.javafaker:javafaker:0.14")
    testImplementation("com.jayway.jsonpath:json-path-assert:2.4.0")
}

tasks {

    docker {
        name = "pantasoft/${bootJar.get().archiveBaseName.get()}:${project.version}"
        files(bootJar.get().archiveFile)
        buildArgs(
                mapOf("JAR_FILE" to bootJar.get().archiveFileName.get(),
                        "JAVA_OPTS" to "-Xms64m -Xmx128m"
                )
        )
        pull(true)
        tags("latest")
        noCache(true)
        dependsOn(bootJar.get(), build.get())
    }

    test {
        description = "Runs the unit tests"
        group = "verification"
        testLogging.showExceptions = true
        exclude("**/*IntegrationTest.class")
    }

    register<Test>("integrationTest") {
        description = "Runs the integration tests"
        group = "verification"
        testLogging.showExceptions = true
        include("**/*IntegrationTest.class")
    }
}