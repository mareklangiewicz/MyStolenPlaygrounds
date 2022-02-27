import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.configurationcache.extensions.*
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.sourcefun.*
import pl.mareklangiewicz.utils.*

plugins {
    id("com.android.application") version Vers.androidGradlePlugin
    kotlin("android") version Vers.kotlin
}

repositories { defaultRepos() }

val generateVersionDetails by tasks.registering(VersionDetailsTask::class) {
    generatedAssetsDir provides layout.buildDirectory.dir("generated/assets")
}

android {
    defaultAndro("pl.mareklangiewicz.playgrounds", withCompose = true)
    sourceSets["main"].assets.srcDir(generateVersionDetails)
}

dependencies {
    implementation(project(":lib1"))
    implementation(project(":lib-ui-samples"))
    defaultAndroDeps(withCompose = true)
    defaultAndroTestDeps(configuration = "implementation", withCompose = true)
    // I use test stuff in main sources so I can add some tests sources to playgrounds app
}

group = "pl.mareklangiewicz.playgrounds"
version = "0.0.01"

tasks.configureKotlinCompileTasks()

androidComponents {
    onVariants {
        val capitalizedVariantName = it.name.capitalized()
        afterEvaluate {
            // FIXME_someday: watch: https://issuetracker.google.com/issues/191774971
            tasks.named("generate${capitalizedVariantName}Assets") { dependsOn("generateVersionDetails") }
        }
    }
}

// region Andro Build Template

fun TaskCollection<Task>.configureKotlinCompileTasks() {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = Vers.defaultJvm
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}

fun ApplicationExtension.defaultAndro(
    appId: String,
    appVerCode: Int = 1,
    appVerName: String = defaultVerName(patch = appVerCode),
    jvmVersion: String = Vers.defaultJvm,
    withCompose: Boolean = false,
) {
    compileSdk = Vers.androidCompileSdk
    defaultCompileOptions(jvmVersion)
    defaultDefaultConfig(appId, appVerCode, appVerName)
    defaultBuildTypes()
    if (withCompose) defaultComposeStuff()
    defaultPackagingOptions()
}

fun LibraryExtension.defaultAndro(
    jvmVersion: String = Vers.defaultJvm,
    withCompose: Boolean = false,
) {
    compileSdk = Vers.androidCompileSdk
    defaultCompileOptions(jvmVersion)
    defaultDefaultConfig()
    defaultBuildTypes()
    if (withCompose) defaultComposeStuff()
    defaultPackagingOptions()
}

fun ApplicationExtension.defaultDefaultConfig(
    appId: String,
    appVerCode: Int = 1,
    appVerName: String = defaultVerName(patch = appVerCode)
) = defaultConfig {
    applicationId = appId
    minSdk = Vers.androidMinSdk
    targetSdk = Vers.androidTargetSdk
    versionCode = appVerCode
    versionName = appVerName
    testInstrumentationRunner = Vers.androidTestRunnerClass
}

fun LibraryExtension.defaultDefaultConfig() = defaultConfig {
    minSdk = Vers.androidMinSdk
    targetSdk = Vers.androidTargetSdk
    testInstrumentationRunner = Vers.androidTestRunnerClass
}

fun CommonExtension<*,*,*,*>.defaultCompileOptions(
    jvmVersion: String = Vers.defaultJvm
) = compileOptions {
    sourceCompatibility(jvmVersion)
    targetCompatibility(jvmVersion)
}

fun ApplicationExtension.defaultBuildTypes() = buildTypes { release { isMinifyEnabled = false } }
fun LibraryExtension.defaultBuildTypes() = buildTypes { release { isMinifyEnabled = false } }

fun CommonExtension<*,*,*,*>.defaultComposeStuff() {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Vers.composeAndroidCompiler
    }
}

fun CommonExtension<*,*,*,*>.defaultPackagingOptions() = packagingOptions {
    resources.excludes.defaultAndroExcludedResources()
}

// endregion Andro Build Template