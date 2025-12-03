@file:Suppress("UNUSED_VARIABLE")
@file:OptIn(NotPortableApi::class,DelicateApi::class)

import okio.Path
import okio.FileSystem.Companion.SYSTEM
import okio.Path.Companion.toPath
import okio.Path.Companion.toOkioPath
import pl.mareklangiewicz.annotations.*
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.ure.*
import pl.mareklangiewicz.utils.*
import pl.mareklangiewicz.sourcefun.*
import pl.mareklangiewicz.io.*
import kotlinx.coroutines.runBlocking

plugins {
    plug(plugs.KotlinMulti) apply false
    plug(plugs.KotlinMultiCompose) apply false
    plug(plugs.ComposeJb) apply false
    plug(plugs.AndroLib) apply false
    plug(plugs.AndroApp) apply false
    plug(plugs.NexusPublish)
    // plug(plugs.SourceFun)
    id("pl.mareklangiewicz.sourcefun") version "0.4.36"
    // https://plugins.gradle.org/search?term=pl.mareklangiewicz
}

rootExtString["verKGround"] = "0.1.20" // https://central.sonatype.com/artifact/pl.mareklangiewicz/kground/versions
rootExtString["verUWidgets"] = "0.0.45" // https://central.sonatype.com/artifact/pl.mareklangiewicz/uwidgets/versions 

val MyStolenPlaygrounds = myLibDetails(
    name = "MyStolenPlaygrounds",
    description = "Collection of Compose related samples, ui tests etc.",
    githubUrl = "https://github.com/langara/MyStolenPlaygrounds",
    version = Ver(0, 0, 6),
    settings = LibSettings(
        withTestJUnit4 = true,
        withTestJUnit5 = false,
        withTestUSpekX = true,
        withTestGoogleTruth = true,
        withTestMockitoKotlin = true,
        andro = LibAndroSettings(sdkCompilePreview = Vers.AndroSdkPreview, publishVariant = "debug"),
    )
)

defaultBuildTemplateForRootProject(MyStolenPlaygrounds)

val playgroundsAppPath = rootProjectPath / "playgrounds-app"
val playgroundsBasicPath = rootProjectPath / "playgrounds-basic"
val playgroundsSamplesPath = rootProjectPath / "playgrounds-samples"
val playgroundsDemosPath = rootProjectPath / "playgrounds-demos"

val myCodeRootPath = "/home/marek/code".toPath()

// val androidxPath = myCodeRootPath / "android/androidx-main/frameworks/support"
val androidxPath = myCodeRootPath / "kotlin/compose-multiplatform-core"

val composePath = androidxPath / "compose"

val srcAppKotlinPath = playgroundsAppPath / "src/main/kotlin"
val srcBasicKotlinPath = playgroundsBasicPath / "src/main/kotlin"
val srcBasicJavaPath = playgroundsBasicPath / "src/main/java"
val srcSamplesKotlinPath = playgroundsSamplesPath / "src/main/kotlin"
val srcDemosKotlinPath = playgroundsDemosPath / "src/main/kotlin"

val stolenBasicKotlinPath = srcBasicKotlinPath / "stolen"
val stolenBasicJavaPath = srcBasicJavaPath // java files have to be in directories same as packages :(
val stolenSamplesKotlinPath = srcSamplesKotlinPath / "stolen"
val stolenDemosKotlinPath = srcDemosKotlinPath / "stolen"
val stolenBasicUnitTestsPath = playgroundsBasicPath / "src/test/kotlin/stolen"
val stolenBasicAndroTestsPath = playgroundsBasicPath / "src/androidTest/kotlin/stolen"
val templatesAppSrcKotlinPath = srcAppKotlinPath / "templates"

fun String.containsOneOf(vararg substrings: String) = substrings.any { it in this }

// TODO NOW: test sourceFun DSL
sourceFun {

    grp = "steal"

    fun regSteal(src: Path, out: Path, transform: Pair<Path, Path>.(String) -> String? = { it }) = reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        this.src = src
        this.out = out
        setTransformFun(transform)
    }

    val srcUiUi = composePath / "ui/ui"
    val srcUiGraphics = composePath / "ui/ui-graphics"
    val srcFoundation = composePath / "foundation/foundation"
    val srcFoundationLayout = composePath / "foundation/foundation-layout"

    val srcUiUT = srcUiUi / "src/test/kotlin/androidx/compose/ui"
    val srcUiGraphicsAT = srcUiGraphics / "src/androidAndroidTest/kotlin/androidx/compose/ui/graphics"
    val srcFoundationUT = srcFoundation / "src/test/kotlin/androidx/compose/foundation"
    val srcFoundationAT = srcFoundation / "src/androidAndroidTest/kotlin/androidx/compose/foundation"
    val srcFoundationLayoutAT = srcFoundationLayout / "src/androidAndroidTest/kotlin/androidx/compose/foundation/layout"

    val stealComposeUiUnitTests by regSteal(srcUiUT, stolenBasicUnitTestsPath / "ui-tests") { it.withInternalAccessIssuesSuppressed() }
    val stealComposeFoundationUnitTests by regSteal(srcFoundationUT, stolenBasicUnitTestsPath / "foundation-tests") { it.withInternalAccessIssuesSuppressed() }
    val stealComposeFoundationAndroTests by regSteal(srcFoundationAT, stolenBasicAndroTestsPath / "foundation-tests") {
        when {
            "text" in first.segments -> null
            first.name.containsOneOf("Lazy", "Pager") -> null
            "nhaarman" in it -> null
            "import androidx.compose.foundation.text" in it -> null
            "import androidx.compose.foundation.test.R" in it -> null
            "import androidx.testutils" in it -> null
            "import androidx.compose.foundation.lazy" in it -> null
            else -> it.withInternalAccessIssuesSuppressed()
        }
    }
    val stealComposeFoundationLayoutAndroTests by regSteal(srcFoundationLayoutAT, stolenBasicAndroTestsPath / "foundation-layout-tests") {
        val interesting = name.containsOneOf("BoxTest", "LayoutTest", "IntrinsicTest", "SizeTest", "PaddingTest", "OffsetTest")
        if (interesting && "Window" !in name) it else null
    }
    val stealComposeUiGraphicsAndroTests by regSteal(srcUiGraphicsAT, stolenBasicAndroTestsPath / "ui-graphics-tests") { it.withInternalAccessIssuesSuppressed() }

    val stealComposeAnnotations by regSteal(androidxPath / "annotation/annotation-sampled/src/main/java/androidx/annotation", stolenSamplesKotlinPath / "androidx-annotation")
    val stealComposeSourcesJava by regSteal(composePath / "ui/ui-android-stubs/src/main/java/android/view", stolenBasicJavaPath / "android/view")
    val stealComposeSourcesTestUtilsCommon by regSteal(composePath / "test-utils/src/commonMain/kotlin/androidx/compose/testutils", stolenBasicKotlinPath / "compose-testutils")
    val stealComposeSourcesTestUtilsAndro by regSteal(composePath / "test-utils/src/androidMain/kotlin/androidx/compose/testutils", stolenBasicKotlinPath / "compose-testutils") {
        if ("Screenshot" in name) null else it
    }
    val stealComposeSamplesUi by regSteal(composePath / "ui/ui/samples/src/main/java/androidx/compose/ui/samples", stolenSamplesKotlinPath / "samples-ui")
    val stealComposeSamplesUiGraphics by regSteal(composePath / "ui/ui-graphics/samples/src/main/java/androidx/compose/ui/graphics/samples", stolenSamplesKotlinPath / "samples-ui-graphics")
    val stealComposeSamplesFoundation by regSteal(srcFoundation / "samples/src/main/java/androidx/compose/foundation/samples", stolenSamplesKotlinPath / "samples-foundation")
    val stealComposeSamplesAnimationCore by regSteal(composePath / "animation/animation-core/samples/src/main/java/androidx/compose/animation/core/samples", stolenSamplesKotlinPath / "samples-animation-core")
    val stealComposeSamplesAnimation by regSteal(composePath / "animation/animation/samples/src/main/java/androidx/compose/animation/samples", stolenSamplesKotlinPath / "samples-animation")
    val m3 = composePath / "material3/material3"
    val stealComposeMaterial3Samples by regSteal(m3 / "samples/src/main/java/androidx/compose/material3/samples", stolenDemosKotlinPath / "material3-samples")
    val stealComposeMaterial3Catalog by regSteal(m3 / "integration-tests/material3-catalog/src/main/java/androidx/compose/material3/catalog", stolenDemosKotlinPath / "material3-catalog")
    val stealComposeMaterial3Demos by regSteal(m3 / "integration-tests/material3-demos/src/main/java/androidx/compose/material3/demos", stolenDemosKotlinPath / "material3-demos")
    val stealComposeFoundationDemos by regSteal(srcFoundation / "integration-tests/foundation-demos/src/main/java/androidx/compose/foundation/demos", stolenDemosKotlinPath / "foundation-demos")
    val stealComposeCommonDemos by regSteal(composePath / "integration-tests/demos/common/src/main/java/androidx/compose/integration/demos/common", stolenDemosKotlinPath / "common-demos")
    val stealComposeSourcesAll by reg { dependsOn(
        stealComposeSourcesJava,
        stealComposeSourcesTestUtilsCommon,
        stealComposeSourcesTestUtilsAndro,
    ) }
    val stealComposeSamplesAll by reg { dependsOn(
        stealComposeSamplesUi,
        stealComposeSamplesUiGraphics,
        stealComposeSamplesFoundation,
        stealComposeSamplesAnimationCore,
        stealComposeSamplesAnimation,
    ) }
    val stealComposeMaterial3All by reg { dependsOn(
        stealComposeMaterial3Samples,
        stealComposeMaterial3Catalog,
        stealComposeMaterial3Demos,
        stealComposeFoundationDemos,
        stealComposeCommonDemos,
    ) }
    val stealAll by reg { dependsOn(
        stealComposeUiUnitTests,
        stealComposeFoundationUnitTests,
        stealComposeFoundationAndroTests,
        stealComposeFoundationLayoutAndroTests,
        stealComposeUiGraphicsAndroTests,
        stealComposeAnnotations,
        stealComposeSourcesAll,
        stealComposeSamplesAll,
        stealComposeMaterial3All,
    ) }

    val patchStolenStuff by tasks.registering {
        group = "steal"
        doLast {
            project.exec {
                commandLine("git", "apply", "./patchStolenStuff.patch")
            }
        }
    }

    val processStolenSamples by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        dependsOn(stealComposeSamplesAll)
        src = stolenSamplesKotlinPath
        out = templatesAppSrcKotlinPath
        setTaskAction { srcTree: FileTree, outDir: Directory ->
            val samples = mutableListOf<Pair<String, Path?>>() // funName to filePath
            srcTree.visit {
                if (isDirectory) return@visit
                val samplePath = file.toOkioPath()
                val sampleContent = SYSTEM.readUtf8(samplePath)
                val pkg = sampleContent.ktFindPackageName()
                samples += sampleContent.findSampledComposableFunNames().map { "$pkg.$it" to samplePath }
            }
            runWithUCtxForTask {
                processComposeTemplates(outDir, samples)
            }
        }
    }
}

fun String.withInternalAccessIssuesSuppressed(): String {
    val r = ureKtComposeTestOutline().compile().matchEntire(this) ?: error("Incorrect compose test file")
    val ktLicenceComment by r
    val ktOtherStuffBeforePackageLine by r
    val ktPackageLine by r
    val ktRest by r
    val ktFileSuppress =
        """@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE", "EXPOSED_PARAMETER_TYPE", "EXPOSED_PROPERTY_TYPE", "CANNOT_OVERRIDE_INVISIBLE_MEMBER")"""

    return "$ktLicenceComment\n\n$ktFileSuppress\n\n$ktOtherStuffBeforePackageLine$ktPackageLine\n$ktRest"
}

fun String.ktFindPackageName() = urePackageLine().compile().find(this)!!["ktPackageName"]

suspend fun processComposeTemplates(
    templatesDir: Directory,
    samples: Collection<Pair<String, Path?>>,
) = processTemplates(
    templatesPath = templatesDir.asFile.toOkioPath(),
    samples = samples,
    interpolations = mapOf(
//            stolenSrcKotlinDir.toString() to "stolenSrcKotlinDir",
//            templatesSrcKotlinDir.toString() to "templatesSrcKotlinDir",
        srcSamplesKotlinPath.toString() to "samplesDir",
    )
)

suspend fun processTemplates(
    templatesPath: Path,
    samples: Collection<Pair<String, Path?>>,
    interpolations: Map<String, String>
) = processEachFile(templatesPath, templatesPath) { _, _, templateFileContent ->

    val r = ureContentWithTemplate.compile().matchEntire(templateFileContent) ?: error("No template")
    val partBeforeTemplate by r
    val partTemplate by r
    val partBeforeGenerationRegion by r
    val partAfterGenerationRegion by r
    val funName by r
    val regionName = "Generated " + funName + " from " + funName + "Template"
    val newGenerationArea = processFunTemplate(partTemplate, funName, samples, interpolations)
    val wholeRegion = "// region $regionName\n$newGenerationArea// endregion $regionName\n"
    val templateRelatedStuff = partBeforeTemplate + partTemplate
    val regionRelatedStuff = partBeforeGenerationRegion + wholeRegion + partAfterGenerationRegion
    templateRelatedStuff + regionRelatedStuff
}

fun String.findSampledComposableFunNames(): Sequence<String> =
    ureSampledFunHeader
        .compile()
        .findAll(this)
        .map { result: MatchResult -> result["funName"].also { println("found fun: $it") } }

fun processFunTemplate(
    template: String,
    templateFunName: String,
    samples: Collection<Pair<String, Path?>>,
    interpolations: Map<String, String>
) =
    template
        .replace(templateFunName + "Template", templateFunName)
        .replace(ureLineWithEndingComment(ureText("REMOVE")).compile(), "")
        .replace(ureLineWithEndingComment(ureText("REPLACE")).compile(),
            samples
                .joinToString(
                    prefix = " ".repeat(8),
                    separator = "\n" + " ".repeat(8),
                    postfix = "\n",
                    transform = { (name, path) ->
                        val pathStr = if (path == null) "null" else "\"${path.toShortStr(interpolations)}\""
                        "MySampleData(\"$name\", $pathStr) { $name() },"
                    }
                )
        )

fun String.interpolate(vararg interpolations: Pair<String, String>) = interpolate(interpolations.toMap())
fun String.interpolate(interpolations: Map<String, String>) =
    interpolations.keys.fold(this) { acc, key -> acc.replace(key, "\\\${${interpolations[key]!!}}") }

fun Path.toShortStr(interpolations: Map<String, String>) = toString().interpolate(interpolations)

val ureSampledFunHeader = ure {
    1 of atBOLine
    // FIXME_later: sth like ureInAnyOrder...
    1 of (ureText("@Sampled") or ureText("@Composable"))
    1..MAX of chSpace
    1 of (ureText("@Sampled") or ureText("@Composable"))
    1..MAX of chSpace
    1 of ureText("fun")
    1..MAX of chSpace
    1 of ure("funName") {
        1 of chUpper
        0..MAX of (chWord or chDigit)
    }
    1 of ureText("()")
}


val ureParamsNotNested = ure { // I assume no internal expressions with parenthesis
    1 of ch('(')
    0..MAX of !ch('(')
    1 of ch(')')
}

val ureAnnotations = ure {
    1..MAX of { // single annotation
        1 of ch('@')
        1 of ureIdent(chUpper)
        0..1 of ureParamsNotNested
        1..MAX of chSpace
    }
}

val ureAnnotationsWithComposable = ure {
    1 of ureAnnotations
    1 of ure {
        1 of ureText("@Composable")
        1..100 of chSpace
    }.lookBehind()
}

val ureMaybeSomeSpaces = ure { 0..MAX of ch(' ') }

val ureIndentedNotEmptyLineContent = ure {
    1 of ch(' ')
    1 of ureMaybeSomeSpaces
    1 of !chOfAnyExact(' ', '\n')
    0..MAX of !ch('\n')
}

val ureIndentedLine = ure {
    1 of atBOLine
    1 of (ureIndentedNotEmptyLineContent or ureMaybeSomeSpaces)
    1 of chLF
}

val ureComposableFunTemplate = ure {
    1 of ureAnnotationsWithComposable
    0..MAX of chSpace // annotations can contain ending spaces too
    1 of atBOLine // we have to start from new line to easier find ending brace }
    1 of ureText("fun")
    1 of chSpace
    1 of ure { 1 of ureIdent(chUpper) }.withName("funName")
    1 of ureText("Template")
    1 of ureParamsNotNested
    1 of chSpace
    1 of ch('{')
    1 of chLF
    1..MAX of ureIndentedLine
    1 of atBOLine
    1 of ch('}')
    1 of chLF
}

val ureContentWithTemplate = ure {
    val regionName = ure {
        1 of ureText("Generated ")
        1 of ureRef(name = "funName") // backreference to actual template function name (inside ureComposableFunTemplate)
        1 of ureText(" from ")
        1 of ureRef(name = "funName") // backreference to actual template function name again
        1 of ureText("Template")
    }
    1 of ureWhateva().withName("partBeforeTemplate")
    1 of ure { 1 of ureComposableFunTemplate }.withName("partTemplate")
    1 of ureWhateva().withName("partBeforeGenerationRegion")
    1 of ureRegion(ureWhateva(), regionLabel = regionName)
    1 of ureWhateva(reluctant = false).withName("partAfterGenerationRegion")
}

// region [[Root Build Template]]

/** Publishing to Sonatype OSSRH has to be explicitly allowed here, by setting withSonatypeOssPublishing to true. */
fun Project.defaultBuildTemplateForRootProject(details: LibDetails? = null) {
  ext.addDefaultStuffFromSystemEnvs()
  details?.let {
    rootExtLibDetails = it
    defaultGroupAndVerAndDescription(it)
    if (it.settings.withSonatypeOssPublishing) defaultSonatypeOssNexusPublishing()
  }

  // kinda workaround for kinda issue with kotlin native
  // https://youtrack.jetbrains.com/issue/KT-48410/Sync-failed.-Could-not-determine-the-dependencies-of-task-commonizeNativeDistribution.#focus=Comments-27-5144160.0-0
  repositories { mavenCentral() }
}

/**
 * System.getenv() should contain six env variables with given prefix, like:
 * * MYKOTLIBS_signing_keyId
 * * MYKOTLIBS_signing_password
 * * MYKOTLIBS_signing_keyFile (or MYKOTLIBS_signing_key with whole signing key)
 * * MYKOTLIBS_ossrhUsername
 * * MYKOTLIBS_ossrhPassword
 * * MYKOTLIBS_sonatypeStagingProfileId
 * * First three of these used in fun pl.mareklangiewicz.defaults.defaultSigning
 * * See KGround/template-full/template-full-lib/build.gradle.kts
 */
fun ExtraPropertiesExtension.addDefaultStuffFromSystemEnvs(envKeyMatchPrefix: String = "MYKOTLIBS_") =
  addAllFromSystemEnvs(envKeyMatchPrefix)

fun Project.defaultSonatypeOssNexusPublishing(
  sonatypeStagingProfileId: String = rootExtString["sonatypeStagingProfileId"],
  ossrhUsername: String = rootExtString["ossrhUsername"],
  ossrhPassword: String = rootExtString["ossrhPassword"],
) {
  nexusPublishing {
    this.repositories {
      sonatype {  // only for users registered in Sonatype after 24 Feb 2021
        stagingProfileId put sonatypeStagingProfileId
        username put ossrhUsername
        password put ossrhPassword
        nexusUrl put repos.sonatypeOssNexus
        snapshotRepositoryUrl put repos.sonatypeOssSnapshots
      }
    }
  }
}

// endregion [[Root Build Template]]
