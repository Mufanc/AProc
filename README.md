# AProc

[![](https://jitpack.io/v/xyz.mufanc/aproc.svg)](https://jitpack.io/#xyz.mufanc/aproc)

🚀 A Gradle plugin that simplifies building single-file app_process applets for Android

### Usage

1. in **settings.gradle.kts**

```kotlin
dependencyResolutionManagement {
    repositories {
        maven("https://jitpack.io")
    }
}
```

2. in app **build.gradle.kts**

```kotlin
plugins {
    id("com.google.devtools.ksp") version "<ksp-version>"
    id("xyz.mufanc.aproc") version "<aproc-version>"
}
```

3. annotate entry point with `@ShellEntry`

```kotlin
import xyz.mufanc.aproc.annotation.ShellEntry

@ShellEntry
object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        ...
    }
}
```
