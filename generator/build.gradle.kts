plugins {
    kotlin("jvm")
    kotlin("kapt")
}


repositories {
    mavenCentral()
}

dependencies {
    kapt("com.google.auto.service:auto-service:1.0-rc5")
    implementation("com.google.auto.service:auto-service:1.0-rc5")
    implementation("com.squareup:kotlinpoet:1.2.0")
}