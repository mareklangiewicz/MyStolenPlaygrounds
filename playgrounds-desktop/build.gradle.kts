
// region [[Full MPP App Build Imports and Plugs]]

import org.jetbrains.compose.*
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
  )
}

// endregion [[Full MPP App Build Imports and Plugs]]

// The desktop half of the two-app split. playgrounds-app stays a plain AGP android application
// (AGP 9 gives an android app no KMP shape at all), and everything that can be multiplatform lives
// in :playgrounds-basic's commonMain, which both apps consume.
//
// jvm ONLY, deliberately. `andro = null` is what keeps the android target out -- presence is the
// scope in DepsKt's Lib model, so dropping the settings drops the target. withJs stays false
// (LibFlags' default): a js target means skiko on a canvas, a kotlin-js-store/yarn.lock to keep
// current and the slowest compilation in the build, and nothing here asks for the web yet.
val lib = gradle.extLib.let {
  it.copy(
    info = it.info.copy(
      namespace = "pl.mareklangiewicz.playgrounds.desktop",
      id = "pl.mareklangiewicz.playgrounds.desktop",
      // appMainClass defaults to "App_jvmKt", which is src/jvmMain/.../App.jvm.kt's top-level main.
      appMainPackage = "pl.mareklangiewicz.playgrounds",
    ),
    flags = it.flags.copy(withJvm = true, withJs = false),
    andro = null,
  )
}

defaultBuildTemplateForFullMppApp(lib) {
  implementation(project(":playgrounds-basic"))
}
