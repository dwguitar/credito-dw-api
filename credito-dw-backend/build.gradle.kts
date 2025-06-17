plugins {
	java
	id("org.springframework.boot") version "3.2.4" // Versão estável mais recente
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.dw.credito"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot Starters
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.kafka:spring-kafka")

	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// MapStruct
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

	// Documentação
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	// Banco de dados
	runtimeOnly("org.postgresql:postgresql")

	// Testes
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Configuração para MapStruct
tasks.withType<JavaCompile> {
	options.compilerArgs.addAll(listOf(
		"-Amapstruct.suppressGeneratorTimestamp=true",
		"-Amapstruct.defaultComponentModel=spring",
		"-Amapstruct.unmappedTargetPolicy=IGNORE"
	))
}