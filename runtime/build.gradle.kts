import com.android.build.api.dsl.LibraryExtension

plugins {
    id("maven-publish")
    alias(libs.plugins.agp.lib)
}

val cfgMinSdkVersion: Int by rootProject.extra
val cfgCompileSdkVersion: Int by rootProject.extra
val cfgSourceCompatibility: JavaVersion by rootProject.extra
val cfgTargetCompatibility: JavaVersion by rootProject.extra

configure<LibraryExtension> {
    namespace = "xyz.mufanc.aproc.runtime"
    compileSdk = cfgCompileSdkVersion

    defaultConfig {
        minSdk = cfgMinSdkVersion
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

    publishing {
        singleVariant("release")
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
