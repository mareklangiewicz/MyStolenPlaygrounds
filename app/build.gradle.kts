@file:OptIn(okio.ExperimentalFileSystem::class)
import okio.Path.Companion.toPath

plugins {
    id("com.android.application") version Vers.androidGradlePlugin
    kotlin("android") version Vers.kotlin
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        mavenCentral()
    }
    dependencies {
        classpath(Deps.kotlinGradlePlugin)
        classpath(Deps.androidGradlePlugin)
    }
}

repositories {
    google()
    mavenCentral()
    maven(url = "https://kotlin.bintray.com/kotlinx")
    maven(Repos.jitpack)
}

group = "pl.mareklangiewicz.playgrounds"
version = "0.0.01"

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "pl.mareklangiewicz.playgrounds"
        minSdk = 26
        targetSdk = 30
        versionCode = 1
        versionName = "0.0.01"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Vers.composeAndroid
    }
    packagingOptions {
        resources.excludes.add("**/*.md")
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.6.0")
    implementation ("androidx.appcompat:appcompat:1.3.0")
    implementation ("com.google.android.material:material:1.4.0")
    implementation (Deps.composeAndroidUi)
    implementation (Deps.composeAndroidUiTooling)
    implementation (Deps.composeAndroidMaterial)
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation ("androidx.activity:activity-compose:1.3.0-rc01")
    api(Deps.uspekx)
    testImplementation ("junit:junit:4.13.2")
}
