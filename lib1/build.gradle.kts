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

group = "pl.mareklangiewicz.lib1"
version = "0.0.01"


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "16"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 26
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_16
        targetCompatibility = JavaVersion.VERSION_16

    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Vers.composeAndroidCompiler
    }
    packagingOptions {
        resources.excludes.add("**/*.md")
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
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