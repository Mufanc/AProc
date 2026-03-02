import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.agp.app) apply false
    alias(libs.plugins.agp.lib) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
}

val cfgMinSdkVersion by extra(30)
val cfgTargetSdkVersion by extra(36)
val cfgCompileSdkVersion by extra(36)
val cfgSourceCompatibility by extra(JavaVersion.VERSION_17)
val cfgTargetCompatibility by extra(JavaVersion.VERSION_17)
val cfgNdkVersion by extra("29.0.14206865")

subprojects {
    group = "xyz.mufanc.aproc"
    version = "1.1.0"
}
