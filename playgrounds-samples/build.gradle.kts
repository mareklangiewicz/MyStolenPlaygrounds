
// region [[Andro Lib Build Imports and Plugs]]

import org.jetbrains.kotlin.gradle.plugin.*
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.utils.*
import pl.mareklangiewicz.templatefun.*

plugins {
  id("pl.mareklangiewicz.templatefun")
  plugAll(plugs.AndroKmpNoVer, plugs.KotlinMulti, plugs.KotlinMultiCompose, plugs.ComposeJbNoVer, plugs.VannikPublish)
}

// endregion [[Andro Lib Build Imports and Plugs]]

// Since AGP 9 an android library is a KMP module with 'com.android.kotlin.multiplatform.library',
// so this is defaultBuildTemplateForAndroLib (templatefun) rather than the old local
// defaultBuildTemplateForAndroidLib, and withCompose moved to the repo-wide lib in settings.
// publishVariant = "debug" became LibPublish.androVariant.
val lib = gradle.extLib.let { it.copy(info = it.info.copy(namespace = "androidx.compose.ui.samples")) }

defaultBuildTemplateForAndroLib(lib, publish = LibPublish(androVariant = "debug"))


// The KMP android target names its configurations per SOURCE SET, so `implementation` does not
// exist here -- same shape playgrounds-demos already uses.
//
// material-icons-extended is NOT covered by LibCompose.withComposeMaterialIconsExtended: only
// defaultBuildTemplateForComposeMppLib reads that flag (MppBuildTemplates.kt:412), and this is an
// andro lib, whose defaultComposeAndroDeps has no icons entry at all. Setting the flag repo-wide
// would look like it configured something and would not, so the dependency is stated where it is
// used. The stolen androidx samples reach for Icons.Filled.* / Icons.Rounded.* throughout.
dependencies {
    "androidMainImplementation"(AndroidX.Compose.Material.icons_extended)
}
