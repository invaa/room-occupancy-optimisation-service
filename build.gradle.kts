plugins {
	java
	alias(libs.plugins.spring.dependency.management) apply false
	alias(libs.plugins.spring.framework.boot) apply false
	alias(libs.plugins.spotless)
	alias(libs.plugins.openapi.generator)
}

group = "com.beusable"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
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
	implementation(libs.spring.boot.starter)
	implementation(libs.spring.boot.starter.validation)
	implementation(libs.spring.boot.starter.web)
	implementation(libs.spring.boot.starter.actuator)

	// OpenApi class generation
	implementation(libs.swagger.parser.v3)
	implementation(libs.jackson.databind.nullable)

	// Traceability
	implementation(libs.micrometer.tracing)
	testImplementation(libs.lombok)

	// Lombok
	compileOnly(libs.lombok)
	annotationProcessor(libs.lombok)

	// Testing
	testImplementation(libs.assertj)
	testImplementation(libs.spring.boot.starter.test)
	testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}

spotless {
	java {
		trimTrailingWhitespace()
		indentWithSpaces()
		importOrder()
		removeUnusedImports()
		cleanthat()
		formatAnnotations()
	}
}

openApiGenerate {
	generatorName.set("spring")
	//library.set("spring-boot")
	inputSpec.set("$projectDir/openapi/api.yaml")
	outputDir.set("$projectDir/build/generated")
	apiPackage.set("com.beusable.roos.api")
	modelPackage.set("com.beusable.roos.model")
	typeMappings.put("Double", "java.math.BigDecimal")
	configOptions.put("interfaceOnly", "true")
	configOptions.put("skipDefaultInterface", "true")
	configOptions.put("useSpringBoot3", "true")
	configOptions.put("useBeanValidation", "true")
	configOptions.put("useJakartaEe", "true")
	configOptions.put("useResponseEntity", "true")
	configOptions.put("performBeanValidation", "true")
	configOptions.put("additionalModelTypeAnnotations", "@lombok.Builder;")
}

sourceSets["main"].java.srcDir("$projectDir/build/generated/src/main/java")

// Fix for non working properly validation of List<@DecimalMin...>
tasks.register("replaceValidationAnnotations") {
	doLast {
		val generatedDir = file("$projectDir/build/generated/src/main/java/com/beusable/roos/model/RoomAllocationRequest.java")
		val searchString = "List<@DecimalMin( value = \"0\", inclusive = true)java.math.BigDecimal>"
		val replacementString = "@com.beusable.roos.validation.ValidGuestPayments List<java.math.BigDecimal>"

		generatedDir.walk().forEach { file ->
			if (file.isFile && file.extension == "java") {
				var content = file.readText()

				if (content.contains(searchString)) {
					content = content.replace(searchString, replacementString)
					file.writeText(content)
					println("Patched validation in: ${file.relativeTo(generatedDir)}")
				}
			}
		}
	}
}

tasks.openApiGenerate {
	dependsOn(tasks["spotlessJava"])
}

tasks.openApiGenerate.configure {
	finalizedBy(tasks.named("replaceValidationAnnotations"))
}

tasks.compileJava {
	dependsOn(tasks.openApiGenerate)
}

