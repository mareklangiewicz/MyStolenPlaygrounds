import pl.mareklangiewicz.sourcefun.*

// region [[Andro App Build Imports and Plugs]]

import com.android.build.api.dsl.*
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.utils.*
import pl.mareklangiewicz.templatefun.*

plugins {
  id("pl.mareklangiewicz.templatefun")
  // No kotlin-android plugin here: since AGP 9, 'com.android.application' brings Kotlin support
  // built in, and applying org.jetbrains.kotlin.android on top of it is a hard error
  // ("no longer required for Kotlin support since AGP 9.0"). DepsKt marks plugs.KotlinAndro
  // deprecated for exactly this reason.
  plugAll(
    plugs.KotlinMultiComposeNoVer,
    plugs.ComposeJbNoVer,
    plugs.AndroAppNoVer,
  )
  id("pl.mareklangiewicz.sourcefun")
}

// endregion [[Andro App Build Imports and Plugs]]

// This module is deliberately NOT a KMP module, and that is the whole point of the two-app split.
// Since AGP 9, 'com.android.application' cannot be combined with the Kotlin Multiplatform plugin,
// and the plugin AGP names as the replacement ('com.android.kotlin.multiplatform.library') is a
// LIBRARY plugin with no application counterpart -- so an android APP has no KMP shape at all.
// Rather than fight that, the android app stays a plain AGP app on the AGP source layout
// (src/main, src/androidTest), and everything that wants to be multiplatform lives in
// :playgrounds-basic's commonMain and is shared with :playgrounds-desktop.
//
// templatefun's defaultBuildTemplateForAndroApp is already non-KMP -- it only ever touches
// ApplicationExtension -- so the ~400 lines of vendored local build template that used to be
// inlined here are gone, along with setMyWeirdSubstitutions (DepsKt dropped that hack; versions
// come from DepsKt now).

val newNamespace = "pl.mareklangiewicz.playgrounds"

val lib = gradle.extLib.let {
  it.copy(
    info = it.info.copy(
      namespace = newNamespace,
      id = "$newNamespace.app",
      appMainPackage = newNamespace,
    ),
  )
}

defaultBuildTemplateForAndroApp(lib) {
  implementation(Langiewicz.kgroundx_io)
  implementation(Langiewicz.uwidgets)
  implementation(Langiewicz.uwidgets_demo)
  implementation(AndroidX.Browser.browser)
  implementation(project(":playgrounds-basic"))
  // implementation(project(":playgrounds-samples"))
  // implementation(project(":playgrounds-demos"))
  // Device-test deps, spelled out. templatefun's defaultAndroTestDeps would cover this, but it is
  // a `context(LibFlags, LibAndro)` function and Gradle compiles build scripts WITHOUT
  // -Xcontext-parameters, so a .gradle.kts file cannot call it at all (see DepsKt deps/build.gradle.kts:61).
  // The old build added these to `implementation` too, "so I can add some tests sources to
  // playgrounds app" -- but no main source here imports a test API, only src/androidTest does,
  // so they go where they are actually used.
  addAllWithVer("androidTestImplementation", Vers.ComposeAndro,
    AndroidX.Compose.Ui.test,
    AndroidX.Compose.Ui.test_manifest,
    AndroidX.Compose.Ui.test_junit4,
  )
  addAll("androidTestImplementation",
    JUnit.junit,
    AndroidX.Test.Ext.junit_ktx,
    AndroidX.Test.rules,
    AndroidX.Test.runner,
    Langiewicz.uspekx_junit4,
  )
}

val generateBuildDetails = tasks.register<BuildDetailsTask>("generateBuildDetails") {
  outputDir provides layout.buildDirectory.dir("generated-assets/build-details")
}

// Wiring the generated assets through the VARIANT API, not the source-set API. AGP 9 rejects
// `sourceSets["main"].assets.srcDir(<Provider>)` outright ("You cannot add Provider instances to
// the Android SourceSet API"), and addGeneratedSourceDirectory is strictly better anyway: it
// carries the task dependency itself, so the old afterEvaluate + generate<Variant>Assets
// dependsOn dance -- and the issuetracker.google.com/issues/191774971 FIXME it carried -- are gone.
androidComponents {
  onVariants { variant ->
    variant.sources.assets?.addGeneratedSourceDirectory(generateBuildDetails, BuildDetailsTask::outputDir)
  }
}
