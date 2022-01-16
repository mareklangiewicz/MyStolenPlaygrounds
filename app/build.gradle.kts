plugins {
    id("com.android.application") version Vers.androidGradlePlugin
    kotlin("android") version Vers.kotlin
}

buildscript { defaultAndroBuildScript() }

repositories { defaultRepositories() }

android { defaultAndroid("pl.mareklangiewicz.playgrounds", withCompose = true) }

dependencies {
    implementation(project(":lib1"))
    implementation(project(":lib-ui-samples"))
    defaultAndroidDeps(withCompose = true)
    defaultAndroidTestDeps(configuration = "implementation", withCompose = true)
    // I use test stuff in main sources so I can add some tests sources to playgrounds app
}

group = "pl.mareklangiewicz.playgrounds"
version = "0.0.01"

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = defaultJvmVersion
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

