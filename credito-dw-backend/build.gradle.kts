plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	jacoco
	id("org.sonarqube") version "4.4.1.3373"
}

group = "com.dw.credito"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
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
	testImplementation("com.h2database:h2")

	// Testes
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.testcontainers:postgresql:1.19.3")
	testImplementation("org.testcontainers:junit-jupiter:1.19.3")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<JavaCompile> {
	options.compilerArgs.addAll(listOf(
		"-Amapstruct.suppressGeneratorTimestamp=true",
		"-Amapstruct.defaultComponentModel=spring",
		"-Amapstruct.unmappedTargetPolicy=IGNORE"
	))
}

jacoco {
	toolVersion = "0.8.11"
	reportsDirectory.set(layout.buildDirectory.dir("reports/jacoco"))
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(false)
	}

	classDirectories.setFrom(files(classDirectories.files.map {
		fileTree(it).apply {
			// Inclui apenas controllers e services
			include(
				"com/dw/credito/controller/**",
				"com/dw/credito/service/**"
			)
			// Exclui todos os outros pacotes
			exclude(
				"com/dw/credito/config/**",
				"com/dw/credito/model/**",
				"com/dw/credito/dto/**",
				"com/dw/credito/repository/**",
				"com/dw/credito/exception/**",
				"**/*Application.class"
			)
		}
	}))
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				minimum = "0.80".toBigDecimal()
			}
		}

		rule {
			element = "CLASS"
			includes = listOf( // Foca apenas em controllers e services
				"com.dw.credito.controller.*",
				"com.dw.credito.service.*"
			)
			limit {
				counter = "LINE"
				value = "COVEREDRATIO"
				minimum = "0.90".toBigDecimal()
			}
		}
	}
}

tasks {
	jacocoTestReport {
		finalizedBy(jacocoTestCoverageVerification)
	}
}

sonar {
	properties {
		property("sonar.host.url", "http://localhost:9000")
		property("sonar.login", "seu_token")
		property("sonar.coverage.jacoco.xmlReportPaths",
			"${layout.buildDirectory.get().asFile}/reports/jacoco/test/jacocoTestReport.xml")
		property("sonar.gradle.skipCompile", "true")
		// Configuração para focar apenas em controllers e services no Sonar
		property("sonar.inclusions",
			"**/controller/**,**/service/**")
	}
}

tasks.named("sonar") {
	dependsOn(tasks.compileJava, tasks.jacocoTestReport)
}

tasks.register<JacocoReport>("jacocoMergedReport") {
	executionData.from(fileTree(layout.buildDirectory.asFile.get()).include("**/jacoco/*.exec"))
	sourceDirectories.from(files("src/main/kotlin", "src/main/java"))
	classDirectories.from(files("${layout.buildDirectory.get()}/classes/kotlin/main", "${layout.buildDirectory.get()}/classes/java/main"))

	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}