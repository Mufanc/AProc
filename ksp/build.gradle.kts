plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.kotlin.jvm)
}

val cfgSourceCompatibility: JavaVersion by rootProject.extra
val cfgTargetCompatibility: JavaVersion by rootProject.extra

java {
    sourceCompatibility = cfgSourceCompatibility
    targetCompatibility = cfgTargetCompatibility
}

dependencies {
    implementation(project(":annotation"))
    implementation(libs.ksp)
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
