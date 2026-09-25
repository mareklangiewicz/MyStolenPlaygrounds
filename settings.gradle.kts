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


// region [[My Settings Stuff]]

// https://docs.gradle.org/current/userguide/upgrading_version_9.html#opt_into_gradle_10_behavior_by_disabling_implicit_lookup_in_parent_projects
enableFeaturePreview("NO_IMPLICIT_LOOKUP_IN_PARENT_PROJECTS")

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }

  // Opt-in through the environment, so this region is identical in every project and no flag has
  // to live above it and be kept in sync. Unset means off. To enable for one run:
  //   ENABLE_LOCAL_DEPSKT_IN_DIR=/home/marek/code/kotlin/DepsKt ./gradlew build
  val enableLocalDepsKtInDir = System.getenv("ENABLE_LOCAL_DEPSKT_IN_DIR")?.let { File(it).normalize() }
  // The env var reaches nested builds too, so DepsKt's own copy of this region sees it: skip self.
  // Pass a String: this scope's includeBuild takes only String, and a File silently resolves to the
  // outer Settings.includeBuild, a plain composite that never offers DepsKt's PLUGINS.
  if (enableLocalDepsKtInDir != null && enableLocalDepsKtInDir != rootDir.normalize()) {
    logger.warn("Including local build $enableLocalDepsKtInDir")
    includeBuild(enableLocalDepsKtInDir.path)
  }
}

plugins {
  id("pl.mareklangiewicz.deps.settings") version "0.4.66" // https://plugins.gradle.org/search?term=mareklangiewicz
  id("com.gradle.develocity") version "4.6.0" // https://docs.gradle.com/develocity/gradle-plugin/
}

develocity {
  buildScan {
    termsOfUseUrl = "https://gradle.com/terms-of-service"
    termsOfUseAgree = "yes"
    // Opt-in through the environment; unset means no scan is ever published, which is what keeps
    // private repos safe without anyone remembering to switch them off. A public repo turns it on
    // in its own CI workflow:  ENABLE_BUILD_SCAN_PUBLISHING_ON_FAILURE=true
    // Read into a local at configuration time: `onlyIf` runs at the END of the build, and reading
    // a settings-script top-level `val` from there would capture the script OBJECT, which the
    // configuration cache rejects. A local is captured by value.
    val enabled = System.getenv("ENABLE_BUILD_SCAN_PUBLISHING_ON_FAILURE") == "true"
    publishing.onlyIf { enabled && it.buildResult.failures.isNotEmpty() }
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

