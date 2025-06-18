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
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

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

// Task para fazer merge dos arquivos .exec de todas as execuções de teste
tasks.register<JacocoMerge>("jacocoMergeExecFiles") {
	executionData.setFrom(fileTree(buildDir).include("**/jacoco/*.exec"))
	destinationFile = file("${buildDir}/jacoco/merged.exec")
}

// Task para gerar o relatório combinado
tasks.register<JacocoReport>("jacocoMergedReport") {
	dependsOn("test", "jacocoMergeExecFiles")

	executionData.setFrom(file("${buildDir}/jacoco/merged.exec"))

	sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
	classDirectories.setFrom(
		fileTree("${buildDir}/classes/java/main") {
			include("com/dw/credito/controller/**", "com/dw/credito/service/**")
		},
		fileTree("${buildDir}/classes/kotlin/main") {
			include("com/dw/credito/controller/**", "com/dw/credito/service/**")
		}
	)

	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}

// Coverage Verification sobre o relatório mergeado
tasks.register<JacocoReport>("jacocoTestCoverageVerification") {
	dependsOn("jacocoMergedReport")
	executionData.setFrom(file("${buildDir}/jacoco/merged.exec"))

	sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
	classDirectories.setFrom(
		fileTree("${buildDir}/classes/java/main") {
			include("com/dw/credito/controller/**", "com/dw/credito/service/**")
		},
		fileTree("${buildDir}/classes/kotlin/main") {
			include("com/dw/credito/controller/**", "com/dw/credito/service/**")
		}
	)

	reports {
		xml.required.set(false)
		html.required.set(false)
	}
}

tasks.named("sonar") {
	dependsOn(tasks.compileJava, tasks.named("jacocoMergedReport"))
}

sonar {
	properties {
		property("sonar.host.url", "http://localhost:9000")
		property("sonar.login", "seu_token")
		property("sonar.coverage.jacoco.xmlReportPaths",
			"${layout.buildDirectory.get().asFile}/reports/jacoco/jacocoMergedReport.xml")
		property("sonar.gradle.skipCompile", "true")
		property("sonar.inclusions", "**/controller/**,**/service/**")
	}
}
