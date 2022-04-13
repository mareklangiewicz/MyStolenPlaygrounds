import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.deps.*
import org.gradle.configurationcache.extensions.*
import pl.mareklangiewicz.sourcefun.*
import pl.mareklangiewicz.utils.*

plugins {
    id("com.android.application") version vers.androidGradlePlugin
    kotlin("android") version vers.kotlinForCompose
    id("maven-publish")
    id("signing")
}

defaultBuildTemplateForAndroidApp(
    appId = "pl.mareklangiewicz.playgrounds",
    withCompose = true,
    details = libs.MyStolenPlaygrounds,
    publishVariant = "debug",
)

val generateVersionDetails by tasks.registering(VersionDetailsTask::class) {
    generatedAssetsDir provides layout.buildDirectory.dir("generated/assets")
}

android {
    sourceSets["main"].assets.srcDir(generateVersionDetails)
}

dependencies {
    implementation(project(":playgrounds-basic"))
    implementation(project(":playgrounds-ui-samples"))
    defaultAndroTestDeps(configuration = "implementation", withCompose = true)
    // I use test stuff in main sources so I can add some tests sources to playgrounds app
}

androidComponents {
    onVariants {
        val capitalizedVariantName = it.name.capitalized()
        afterEvaluate {
            // FIXME_someday: watch: https://issuetracker.google.com/issues/191774971
            tasks.named("generate${capitalizedVariantName}Assets") { dependsOn("generateVersionDetails") }
        }
    }
}

// region [Kotlin Module Build Template]

fun TaskCollection<Task>.defaultKotlinCompileOptions(
    jvmTargetVer: String = vers.defaultJvm,
    requiresOptIn: Boolean = true
) = withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = jvmTargetVer
        if (requiresOptIn) freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

// endregion [Kotlin Module Build Template]

// region [Andro Common Build Template]

fun CommonExtension<*,*,*,*>.defaultCompileOptions(
    jvmVersion: String = vers.defaultJvm
) = compileOptions {
    sourceCompatibility(jvmVersion)
    targetCompatibility(jvmVersion)
}

fun CommonExtension<*,*,*,*>.defaultComposeStuff() {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = vers.composeAndroidCompiler
    }
}

fun CommonExtension<*,*,*,*>.defaultPackagingOptions() = packagingOptions {
    resources.excludes.defaultAndroExcludedResources()
}

// endregion [Andro Common Build Template]

// region [Andro App Build Template]

fun Project.defaultBuildTemplateForAndroidApp(
    appId: String,
    appNamespace: String = appId,
    appVerCode: Int = 1,
    appVerName: String = v(patch = appVerCode),
    jvmVersion: String = vers.defaultJvm,
    sdkCompile: Int = vers.androidSdkCompile,
    sdkTarget: Int = vers.androidSdkTarget,
    sdkMin: Int = vers.androidSdkMin,
    withCompose: Boolean = false,
    details: LibDetails = libs.Unknown,
    publishVariant: String? = null, // null means disable publishing to maven repo
) {
    repositories { defaultRepos() }
    android {
        defaultAndroApp(appId, appNamespace, appVerCode, appVerName, jvmVersion, sdkCompile, sdkTarget, sdkMin, withCompose)
        publishVariant?.let { defaultAndroAppPublishVariant(it) }
    }
    dependencies {
        defaultAndroDeps(withCompose = withCompose)
        defaultAndroTestDeps(withCompose = withCompose)
    }
    tasks.defaultKotlinCompileOptions()
    defaultGroupAndVerAndDescription(details)
    publishVariant?.let {
        defaultPublishingOfAndroApp(details, it)
        defaultSigning()
    }
}

fun ApplicationExtension.defaultAndroApp(
    appId: String,
    appNamespace: String = appId,
    appVerCode: Int = 1,
    appVerName: String = v(patch = appVerCode),
    jvmVersion: String = vers.defaultJvm,
    sdkCompile: Int = vers.androidSdkCompile,
    sdkTarget: Int = vers.androidSdkTarget,
    sdkMin: Int = vers.androidSdkMin,
    withCompose: Boolean = false,
) {
    compileSdk = sdkCompile
    defaultCompileOptions(jvmVersion)
    defaultDefaultConfig(appId, appNamespace, appVerCode, appVerName, sdkTarget, sdkMin)
    defaultBuildTypes()
    if (withCompose) defaultComposeStuff()
    defaultPackagingOptions()
}

fun ApplicationExtension.defaultDefaultConfig(
    appId: String,
    appNamespace: String = appId,
    appVerCode: Int = 1,
    appVerName: String = v(patch = appVerCode),
    sdkTarget: Int = vers.androidSdkTarget,
    sdkMin: Int = vers.androidSdkMin,
) = defaultConfig {
    applicationId = appId
    namespace = appNamespace
    targetSdk = sdkTarget
    minSdk = sdkMin
    versionCode = appVerCode
    versionName = appVerName
    testInstrumentationRunner = vers.androidTestRunnerClass
}

fun ApplicationExtension.defaultBuildTypes() = buildTypes { release { isMinifyEnabled = false } }

fun ApplicationExtension.defaultAndroAppPublishVariant(
    variant: String = "debug",
    publishAPK: Boolean = true,
    publishAAB: Boolean = false,
) {
    require(!publishAAB || !publishAPK) { "Either APK or AAB can be published, but not both." }
    publishing { singleVariant(variant) { if (publishAPK) publishApk() } }
}

// endregion [Andro App Build Template]