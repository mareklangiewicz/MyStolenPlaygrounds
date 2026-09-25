
// region [[Andro Lib Build Imports and Plugs]]

import com.android.build.api.dsl.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import com.vanniktech.maven.publish.*
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.utils.*
import pl.mareklangiewicz.templatefun.*

plugins {
  plugAll(
    plugs.TemplateFunNoVer, // version comes from the root: a versioned request here fails in composite builds
    plugs.KotlinMulti,
    plugs.KotlinMultiCompose,
    plugs.ComposeJbNoVer,
    plugs.AndroKmpNoVer,
    plugs.VannikPublish,
  )
}

// endregion [[Andro Lib Build Imports and Plugs]]

// Since AGP 9 an android library is a KMP module with 'com.android.kotlin.multiplatform.library',
// so this is defaultBuildTemplateForAndroLib (templatefun) rather than the old local
// defaultBuildTemplateForAndroidLib, and withCompose moved to the repo-wide lib in settings.
// publishVariant = "debug" became LibPublish.androVariant.
val lib = gradle.extLib.let { it.copy(info = it.info.copy(namespace = "androidx.compose.material3.catalog.library")) }

defaultBuildTemplateForAndroLib(lib, publish = LibPublish(androVariant = "debug"))


// The KMP android target names its configurations per SOURCE SET, so the plain `implementation`
// of the old com.android.library path does not exist here any more.
dependencies {
    "androidMainImplementation"(project(":playgrounds-samples"))
    "androidMainImplementation"(AndroidX.Compose.Material3.material3)
    "androidMainImplementation"(AndroidX.Navigation.compose)
    // Not covered by LibCompose.withComposeMaterialIconsExtended -- only
    // defaultBuildTemplateForComposeMppLib reads that flag (MppBuildTemplates.kt:412), and this is
    // an andro lib. The stolen material3 samples/demos use Icons.* on almost every screen: it was
    // 937 of the 1451 errors here.
    "androidMainImplementation"(AndroidX.Compose.Material.icons_extended)
}
