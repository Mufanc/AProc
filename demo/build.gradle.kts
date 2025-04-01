plugins {
    alias(libs.plugins.agp.app)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    id("xyz.mufanc.aproc") version "0.2.0"
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
