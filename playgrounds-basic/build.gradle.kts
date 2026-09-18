
// region [[Full MPP Lib Build Imports and Plugs]]

import com.android.build.api.dsl.*
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.jetbrains.compose.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.utils.*
import pl.mareklangiewicz.templatefun.*

plugins {
  id("pl.mareklangiewicz.templatefun")
  plugAll(
    plugs.KotlinMulti,
    plugs.KotlinMultiCompose,
    plugs.ComposeJbNoVer,
    plugs.VannikPublish,
  )
}

// endregion [[Full MPP Lib Build Imports and Plugs]]


// workaround for crazy gradle bugs like this one or similar:
// https://youtrack.jetbrains.com/issue/KT-43500/KJS-IR-Failed-to-resolve-Kotlin-library-on-attempting-to-resolve-compileOnly-transitive-dependency-from-direct-dependency
repositories { maven(repos.composeJbDev) }

val newNamespace = "pl.mareklangiewicz.playgrounds.basic"

val lib = gradle.extLib.let { it.copy(info = it.info.copy(namespace = newNamespace)) }

// publishVariant = "debug" was a per-REPO andro setting; it is a publishing decision, so it is
// LibPublish.androVariant now, stated by the module that publishes.
defaultBuildTemplateForFullMppLib(lib, publish = LibPublish(androVariant = "debug")) {
// workaround for crazy gradle bugs like this one or similar:
// https://youtrack.jetbrains.com/issue/KT-43500/KJS-IR-Failed-to-resolve-Kotlin-library-on-attempting-to-resolve-compileOnly-transitive-dependency-from-direct-dependency
  // implementation(KotlinX.coroutines_core)
  implementation(Langiewicz.kgroundx_io)
  implementation(Langiewicz.uwidgets)
  implementation(Langiewicz.uwidgets_demo)
}

// Workaround needed for preview in android studio e.g. in MyExaminedLayoutPreview.kt
// TODO_later: track: https://github.com/JetBrains/compose-multiplatform/issues/4869
//
// Was guarded by `if (newDetails.settings.withAndro)` and configured LibraryExtension. Both are
// gone: andro presence IS the scope now (lib.andro != null), and since AGP 9 an android KMP module
// has no LibraryExtension at all -- 'com.android.kotlin.multiplatform.library' names its
// configurations per source set instead.
if (lib.andro != null) {
  dependencies {
    addAll("androidMainImplementation",
      AndroidX.Compose.Ui.tooling,
      AndroidX.Compose.Ui.tooling_preview,
    )
  }
}

// setMyWeirdSubstitutions is gone from DepsKt, and with it the rootExtString["verKGround"] /
// ["verUWidgets"] pins it consumed -- the scoped-local-publication recipe replaces that hack
// (see KGround/docs/design/local-build-logic-loop.md). Same removal UWidgets made in d51c44d.

// android {
//   dependencies {
//     implementation(AndroidX.AutoFill.autofill)
//     defaultAndroTestDeps(newDetails.settings, configuration = "implementation")
//     testImplementation(Org.Robolectric.robolectric)
//   }
//
//   testOptions {
//     unitTests.isReturnDefaultValues = true
//   }
// }
