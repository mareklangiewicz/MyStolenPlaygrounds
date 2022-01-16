plugins {
    id("com.android.library") version Vers.androidGradlePlugin
    kotlin("android") version Vers.kotlin
}

buildscript {
    defaultAndroBuildScript()
}

repositories {
    defaultRepositories()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "16"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

android {
    defaultAndroid(withCompose = true)
}

dependencies {
    implementation (Deps.androidxCoreKtx)
    implementation (Deps.androidxAppcompat)
    implementation (Deps.androidMaterial)
    implementation (Deps.composeAndroidUi)
    implementation (Deps.composeAndroidUiTooling)
    implementation (Deps.composeAndroidMaterial3)
    implementation (Deps.composeAndroidMaterial)
    implementation (Deps.androidxLifecycleCompiler)
    implementation (Deps.androidxLifecycleRuntimeKtx)
    implementation (Deps.androidxActivityCompose)
    // I use test stuff in main sources so I can add some tests sources to playgrounds app
//    implementation (Deps.uspekx)
    implementation (Deps.junit4)
    implementation (Deps.androidxEspressoCore)
    implementation (Deps.googleTruth)
    implementation (Deps.androidxTestRules)
    implementation (Deps.androidxTestRunner)
    implementation (Deps.androidxTestExtTruth)
    implementation (Deps.androidxTestExtJUnit)
    implementation (Deps.composeAndroidUiTest)
    implementation (Deps.composeAndroidUiTestJUnit4)
    implementation (Deps.composeAndroidUiTestManifest)
    implementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
//    implementation(Deps.mockitoKotlin2)
    implementation(Deps.mockitoAndroid)
}