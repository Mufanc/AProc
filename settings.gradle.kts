pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        mavenLocal {
            content {
                includeGroup("xyz.mufanc.aproc")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal {
            content {
                includeGroup("xyz.mufanc.aproc")
            }
        }
    }
}

rootProject.name = "AProc"

include(":annotation")
include(":ksp")
include(":runtime")
include(":plugin")

if (!System.getenv("BUILD_DEMO").isNullOrEmpty()) {
    include(":demo")
}
