plugins {
    application
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    id("com.gradleup.shadow") version "9.0.0-rc3"
}


repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
    maven("https://repo.mineinabyss.com/snapshots")
}

dependencies {
    implementation("me.dvyy:shocky:0.3.0-dev.2")
    implementation("me.dvyy:shocky-icons:0.3.0-dev.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

application {
    mainClass = "MainKt"
}

tasks {
    shadowJar {
        archiveFileName.set("shocky-docs.jar")
    }
    register("generate") {
        run.get().args("generate")
        finalizedBy(run)
    }
    register("serve") {
        run.get().args("serve", "dev")
        finalizedBy(run)
    }
}
