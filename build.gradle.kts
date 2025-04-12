// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.agp.app) apply false
    alias(libs.plugins.agp.lib) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
}

val cfgMinSdkVersion by extra(30)
val cfgTargetSdkVersion by extra(34)
val cfgCompileSdkVersion by extra(34)
val cfgSourceCompatibility by extra(JavaVersion.VERSION_17)
val cfgTargetCompatibility by extra(JavaVersion.VERSION_17)
val cfgKotlinJvmTarget by extra("17")
val cfgNdkVersion by extra("29.0.13113456")

subprojects {
    group = "xyz.mufanc.aproc"
    version = "1.0.0"
}
