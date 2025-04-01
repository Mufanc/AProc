plugins {
    id("maven-publish")
    alias(libs.plugins.agp.lib)
    alias(libs.plugins.kotlin.android)
}

val cfgMinSdkVersion: Int by rootProject.extra
val cfgCompileSdkVersion: Int by rootProject.extra
val cfgSourceCompatibility: JavaVersion by rootProject.extra
val cfgTargetCompatibility: JavaVersion by rootProject.extra
val cfgKotlinJvmTarget: String by rootProject.extra

android {
    namespace = "xyz.mufanc.aproc.runtime"
    compileSdk = cfgCompileSdkVersion

    defaultConfig {
        minSdk = cfgMinSdkVersion
        consumerProguardFiles("consumer-rules.pro")
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

    publishing {
        singleVariant("release")
    }

    sourceSets {
        getByName("main") {
            java.srcDirs("external/manifest-editor/lib/src/main/java")
            resources.srcDirs("external/manifest-editor/lib/src/main")
        }
    }
}

dependencies {
    implementation(libs.joor)
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>(project.name) {
                afterEvaluate {
                    from(components.getByName("release"))
                }
            }
        }
    }
}