plugins {
	java
	alias(libs.plugins.spring.dependency.management) apply false
	alias(libs.plugins.spring.framework.boot) apply false
	alias(libs.plugins.spotless)
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

	// Traceability
	implementation(libs.micrometer.tracing)

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