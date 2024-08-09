plugins {
	java
	id("com.diffplug.spotless") version "6.25.0"
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.devin.sportsbet"
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
	implementation("org.drools:drools-core:9.44.0.Final")
	implementation("org.drools:drools-compiler:9.44.0.Final")
	implementation("org.drools:drools-mvel:9.44.0.Final")
	implementation("org.kie:kie-spring:7.74.1.Final")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.droolsassert:droolsassert:3.2.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("io.rest-assured:rest-assured:5.5.0")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

spotless {
	java {
		googleJavaFormat()
		target("src/**/*.java")
	}
}