plugins {
    kotlin("jvm")
    kotlin("kapt")
}


repositories {
    mavenCentral()
}

sourceSets {
    getByName("main").java.srcDirs("${buildDir.absolutePath}/tmp/kapt/main/kotlinGenerated/")
}

dependencies {
    kapt(project(":generator"))
    compileOnly(project(":generator"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.31")
}
