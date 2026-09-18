
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
// Compose Multiplatform does not release in lockstep, so each artifact carries its OWN version,
// and the policy is verLastBeta -- newest beta-or-better. Same rule templatefun's internal
// ComposeJb object applies; it is internal, so it is restated here rather than reached into.
fun Dep.jb() = withVer(verLastBeta)

defaultBuildTemplateForFullMppLib(lib, publish = LibPublish(androVariant = "debug")) {
// workaround for crazy gradle bugs like this one or similar:
// https://youtrack.jetbrains.com/issue/KT-43500/KJS-IR-Failed-to-resolve-Kotlin-library-on-attempting-to-resolve-compileOnly-transitive-dependency-from-direct-dependency
  // implementation(KotlinX.coroutines_core)
  implementation(Langiewicz.kgroundx_io)
  implementation(Langiewicz.uwidgets)
  implementation(Langiewicz.uwidgets_demo)

  // The stolen androidx `test-utils` module lands in MAIN sources, not test sources, because that
  // is where upstream keeps it -- compose/test-utils is a library whose commonMain/androidMain ARE
  // these files. Its consumers here are both androidHostTest (2 files) and androidDeviceTest (20),
  // and those two source sets have no shared android-aware parent to hold it, so main it is, and
  // main needs the test libraries on its own compile classpath.
  implementation(Org.JetBrains.Compose.Ui.test.jb())
  implementation(KotlinX.coroutines_test)
  // ui-test-junit4 belongs in commonMain, not only androidMain: the stolen
  // ParameterizedComposeTestRule.kt is a COMMON file and it builds on ComposeTestRule /
  // ComposeContentTestRule / createComposeRule. This module's targets are jvm and android, both of
  // which have that artifact. It did not surface from compileAndroidMain -- only `assemble`, which
  // reaches the jvm compilation, sees commonMain compiled on its own.
  implementation(Org.JetBrains.Compose.Ui.test_junit4.jb())
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
      // For the android half of the stolen test-utils: ActivityScenarioRule and
      // androidx.activity.compose.setContent. The junit4 compose rules it also needs are on
      // commonMain above (JetBrains' artifacts, not androidx's -- the stolen code calls
      // `test.junit4.v2.createComposeRule`, which only compose-multiplatform has).
      AndroidX.Test.Ext.junit_ktx,
      AndroidX.Activity.compose,
    )
    // The stolen androidx HOST tests assert with Google Truth and several run under Robolectric.
    // templatefun's defaultAndroTestDeps covers Truth only where the repo-wide flags reach it, and
    // nothing has ever put Robolectric here -- the old build had it commented out in a dead
    // `android { dependencies { ... } }` block. Without these two, 156 of the ~360 host-test
    // errors were nothing but `Unresolved reference 'isEqualTo'` and friends.
    addAll("androidHostTestImplementation",
      Com.Google.Truth.truth,
      AndroidX.Test.Ext.truth,
      Org.Robolectric.robolectric,
    )
  }
}

// The stolen android platform STUBS (android.view.DisplayListCanvas / HardwareCanvas / RenderNode)
// live in src/androidMain/java, and the KMP android target does not put that directory on the
// Kotlin compilation's source path by itself -- AGP's old `sourceSets["main"].java` is not part of
// this plugin's model. Handing it to the kotlin source set is what lets the compiler RESOLVE them;
// verified by removing this line, which brings back exactly the three
// "Unresolved reference 'DisplayListCanvas'/'RenderNode'" errors in
// stolen/compose-testutils/AndroidComposeTestCaseRunner.android.kt.
//
// They live in stubs/java rather than src/androidMain/java precisely so that AGP's javac never
// sees them: from inside src/ it compiled them and their classes reached
// intermediates/runtime_library_classes_dir, i.e. this library would have shipped classes in the
// `android.view` package. Adding the directory to the KOTLIN source path is resolution without
// code generation, which is the same contract upstream's compileOnly ui-android-stubs module has.
kotlin.sourceSets.named("androidMain") { kotlin.srcDir("stubs/java") }

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
