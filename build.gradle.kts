import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.openapi.generator") version "5.3.0"
    id("com.avast.gradle.docker-compose") version "0.14.9"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
}

group = "ro.daniil.zaru"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val r2dbcVersion = "0.8.10.RELEASE"
val r2dbcPoolVersion = "0.9.0.M2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework:spring-jdbc")
    implementation("io.swagger:swagger-annotations:1.6.3")
    implementation("org.openapitools:jackson-databind-nullable:0.2.1")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("io.r2dbc:r2dbc-pool:$r2dbcPoolVersion")
    implementation("io.r2dbc:r2dbc-postgresql:$r2dbcVersion")
    implementation("io.r2dbc:r2dbc-spi:0.9.0.M2")
    runtimeOnly("org.postgresql:postgresql")
    implementation("io.springfox:springfox-swagger2:2.0.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val apiCodeGeneratedDir = "${project.buildDir}/generated"

java {
    sourceSets["main"].apply {
        java.srcDir("$apiCodeGeneratedDir/src/main/java")
    }
}

openApiGenerate {
    generatorName.set("spring")
    configOptions.apply {
        put("reactive", "true")
        put("delegatePattern", "true")
    }
    inputSpec.set("${project.rootDir}/src/main/resources/openapi.yaml")
    generateApiTests.set(false)
    generateModelTests.set(false)
    outputDir.set(apiCodeGeneratedDir)
    apiPackage.set("ro.daniil.zaru.phonebookingsystem.api")
    modelPackage.set("ro.daniil.zaru.phonebookingsystem.model")
    ignoreFileOverride.set("${project.rootDir}/src/main/.openapi-generator-ignore")
}

tasks.create("removeOpenApiTools", Delete::class) {
    delete(files("$apiCodeGeneratedDir/src/main/java/org/openapitools"))
}

tasks.getByName("openApiGenerate").finalizedBy("removeOpenApiTools")

dockerCompose {
    useComposeFiles.set(listOf("docker-compose.yaml"))
}