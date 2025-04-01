plugins {
    alias(libs.plugins.agp.app)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

buildscript {
    if (project.hasProperty("demo")) {
        repositories {
            mavenLocal {
                content {
                    includeGroup("xyz.mufanc.aproc")
                }
            }
        }

        dependencies {
            classpath("xyz.mufanc.aproc:plugin:${project.version}")
        }
    }
}

if (project.hasProperty("demo")) {
    apply(plugin = "xyz.mufanc.aproc")
}

val cfgMinSdkVersion: Int by rootProject.extra
val cfgTargetSdkVersion: Int by rootProject.extra
val cfgCompileSdkVersion: Int by rootProject.extra
val cfgSourceCompatibility: JavaVersion by rootProject.extra
val cfgTargetCompatibility: JavaVersion by rootProject.extra
val cfgKotlinJvmTarget: String by rootProject.extra

android {
    namespace = "xyz.mufanc.aproc"
    compileSdk = cfgCompileSdkVersion

    defaultConfig {
        applicationId = "xyz.mufanc.aproc"
        minSdk = cfgMinSdkVersion
        targetSdk = cfgTargetSdkVersion
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = cfgSourceCompatibility
        targetCompatibility = cfgTargetCompatibility
    }

    kotlinOptions {
        jvmTarget = cfgKotlinJvmTarget
    }
}

dependencies {
    if (!project.hasProperty("demo")) {
        compileOnly(project(":annotation"))
        compileOnly(project(":runtime"))
    }
}
