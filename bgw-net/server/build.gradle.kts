val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
	kotlin("jvm")
}

group = "tools.aqua"
version = rootProject.version

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.ktor:ktor-server-netty:$ktor_version")
	implementation("io.ktor:ktor-websockets:$ktor_version")
	implementation("ch.qos.logback:logback-classic:$logback_version")
}