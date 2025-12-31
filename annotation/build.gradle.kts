import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.kotlin.jvm)
}

val cfgSourceCompatibility: JavaVersion by rootProject.extra
val cfgTargetCompatibility: JavaVersion by rootProject.extra
val cfgKotlinJvmTarget: JvmTarget by rootProject.extra

java {
    sourceCompatibility = cfgSourceCompatibility
    targetCompatibility = cfgTargetCompatibility
}

kotlin {
    compilerOptions {
        jvmTarget.set(cfgKotlinJvmTarget)
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>(project.name) {
                from(components["java"])
            }
        }
    }
}
