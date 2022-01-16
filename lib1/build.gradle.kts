plugins {
    id("com.android.library") version Vers.androidGradlePlugin
    kotlin("android") version Vers.kotlin
}

buildscript { defaultAndroBuildScript() }

repositories { defaultRepositories() }

android { defaultAndroid(withCompose = true) }

dependencies {
    defaultAndroidDeps(withCompose = true)
    defaultAndroidTestDeps(configuration = "implementation", withCompose = true)
    // I use test stuff in main sources so I can add some tests sources to playgrounds app
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = defaultJvmVersion
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}
