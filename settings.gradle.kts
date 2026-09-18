@file:Suppress("UnstableApiUsage")

// TODO NOW:
// 1. DONE: all five modules compile, and `./gradlew build` is green.
// 2. try hard to auto include demo mpp from compose-multiplatform-core
// 3. analyze web runtime, analyze native targets config?, tests? learn using this demo and repeat the similar solutions.
// 4. re-enable the generated samples region in playgrounds-app's PlaygroundsTemplate.kt -- it is
//    still commented out with its own "TODO NOW: reenable", and its samplesDir points at a
//    lib-ui-samples path that no longer exists. :playgrounds-samples is back in the build now, so
//    `processStolenSamples` can regenerate it.
// 5. playgrounds-desktop is jvm only. Web (js/wasm) is deliberately not wired: see its build file.
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.utils.extLib

rootProject.name = "MyStolenPlaygrounds"


// Careful with auto publishing fails/stack traces
val buildScanPublishingAllowed =
  System.getenv("GITHUB_ACTIONS") == "true"
  // true
  // false

// region [[My Settings Stuff <~~]]
// ~~>".*/Deps\.kt"~~>"../DepsKt"<~~
// endregion [[My Settings Stuff <~~]]
// region [[My Settings Stuff]]

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }

  val depsDir = File(rootDir, "../DepsKt").normalize()
  val depsInclude =
    // depsDir.exists()
    false
  if (depsInclude) {
    logger.warn("Including local build $depsDir")
    includeBuild(depsDir)
  }
}

plugins {
  id("pl.mareklangiewicz.deps.settings") version "0.4.63" // https://plugins.gradle.org/search?term=mareklangiewicz
  id("com.gradle.develocity") version "4.5.1" // https://docs.gradle.com/develocity/gradle-plugin/
}

develocity {
  buildScan {
    termsOfUseUrl = "https://gradle.com/terms-of-service"
    termsOfUseAgree = "yes"
    publishing.onlyIf { buildScanPublishingAllowed && it.buildResult.failures.isNotEmpty() }
  }
}

// endregion [[My Settings Stuff]]

// Moved here from build.gradle.kts: the lib definition lives in settings now (gradle.extLib),
// instead of rootExtLibDetails in the root build.
gradle.extLib = lib(
  info = myLibInfo(
    name = "MyStolenPlaygrounds",
    description = "Collection of Compose related samples, ui tests etc.",
    githubUrl = "https://github.com/langara/MyStolenPlaygrounds",
    version = Ver(0, 0, 6),
  ),
  flags = LibFlags(
    withTestJUnit4 = true,
    // Separate flag on purpose: android DEVICE tests cannot take JUnit5 at all, so templatefun
    // asks for this one when configuring androidDeviceTestImplementation. Without it the device
    // configuration silently misses uspekx-junit4 and @RunWith(USpekJUnit4Runner) stops resolving
    // -- which is exactly what playgrounds-basic's MyPlaygroundsUSpek.kt hit.
    withTestJUnit4OnAndroidDevice = true,
    withTestJUnit5 = false,
    withTestUSpekX = true,
    withTestGoogleTruth = true,
    withTestMockitoKotlin = true,
  ),
  withCompose = true,
  withAndro = true,
  // publishVariant = "debug" is NOT here any more: the variant is a publishing decision, so as of
  // DepsKt 0.4.63 it is LibPublish.androVariant, stated per module by the modules that publish.
  andro = LibAndro(sdkCompilePreview = Vers.AndroSdkPreview),
)

include(":playgrounds-app")      // android application (plain AGP -- AGP 9 gives an app no KMP shape)
include(":playgrounds-desktop")  // jvm Compose MPP application
include(":playgrounds-basic")    // the shared library both apps render
include(":playgrounds-samples")
include(":playgrounds-demos")

