
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

