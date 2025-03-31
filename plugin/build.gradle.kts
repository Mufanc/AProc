plugins {
    id("java-library")
    id("maven-publish")
    id("java-gradle-plugin")
    alias(libs.plugins.kotlin.jvm)
}

val cfgSourceCompatibility: JavaVersion by rootProject.extra
val cfgTargetCompatibility: JavaVersion by rootProject.extra

java {
    sourceCompatibility = cfgSourceCompatibility
    targetCompatibility = cfgTargetCompatibility
}

gradlePlugin {
    plugins {
        create("aproc") {
            id = project.group.toString()
            implementationClass = "$id.plugin.AProcPlugin"
        }
    }
}

dependencies {
    implementation(libs.agp)
}

tasks.processResources {
    filesMatching("META-INF/aproc-plugin.properties") {
        expand(project.properties)
    }
}

afterEvaluate {
    publishing {
        publications {
            named<MavenPublication>("pluginMaven") {
                artifactId = project.name
            }
        }
    }
}
