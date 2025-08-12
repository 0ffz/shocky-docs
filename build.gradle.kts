plugins {
    application
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    id("com.gradleup.shadow") version "9.0.1"
    `maven-publish`
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
    mainClass = "me.dvyy.shocky.docs.MainKt"
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

publishing {
    repositories {
        maven {
            name = "mineinabyssMaven"
            val repo = "https://repo.mineinabyss.com/"
            val isSnapshot = System.getenv("IS_SNAPSHOT") == "true"
            val url = if (isSnapshot) repo + "snapshots" else repo + "releases"
            setUrl(url)
            credentials(PasswordCredentials::class)
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
        }
    }
}
