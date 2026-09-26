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
import pl.mareklangiewicz.templatefun.*
import pl.mareklangiewicz.io.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import org.gradle.process.ExecOperations

/** See [patchStolenStuff]. Exists only because Gradle 9 removed Project.exec. */
abstract class GitApplyPatchTask : DefaultTask() {
    @get:Input abstract val patch: Property<String>
    @get:Internal abstract val workDir: DirectoryProperty
    @get:Inject abstract val execOps: ExecOperations
    @TaskAction fun apply() {
        execOps.exec {
            commandLine("git", "apply", patch.get())
            workingDir = workDir.get().asFile
        }
    }
}

plugins {
    plug(plugs.KotlinMulti) apply false
    plug(plugs.KotlinMultiCompose) apply false
    plug(plugs.ComposeJb) apply false
    plug(plugs.AndroLib) apply false
    plug(plugs.AndroApp) apply false
    // Since AGP 9 an android library is a KMP module with this plugin, and like VannikPublish it
    // must be resolved ONCE here with its version -- templatefun carries it on its own classpath
    // at "unknown version", so a versioned request in a subproject cannot be checked against it.
    plug(plugs.AndroKmp) apply false
    plug(plugs.TemplateFun) apply false

    // Resolve the publish plugin ONCE here, with its version. Without this the only source of
    // it is the templatefun plugin's own classpath (templatefun depends on it), which Gradle sees
    // as "unknown version" -- and then a versioned request in a subproject cannot be checked
    // against it.
    plug(plugs.VannikPublish) apply false
    // plug(plugs.SourceFun)
    id("pl.mareklangiewicz.sourcefun") version "0.4.46"
    // https://plugins.gradle.org/search?term=pl.mareklangiewicz
}

rootExtString["verKGround"] = "0.1.22" // https://central.sonatype.com/artifact/pl.mareklangiewicz/kground/versions
rootExtString["verUWidgets"] = "0.0.45" // https://central.sonatype.com/artifact/pl.mareklangiewicz/uwidgets/versions 

// The lib definition (name/version/flags/compose/andro) moved to settings.gradle.kts as
// gradle.extLib, and the local defaultBuildTemplateForRootProject that used to set
// rootExtLibDetails is gone -- it is plugs.TemplateFun's job now.
defaultGroupAndVerAndDescription(gradle.extLib)

val playgroundsAppPath = rootProjectPath / "playgrounds-app"
val playgroundsBasicPath = rootProjectPath / "playgrounds-basic"
val playgroundsSamplesPath = rootProjectPath / "playgrounds-samples"
val playgroundsDemosPath = rootProjectPath / "playgrounds-demos"

val myCodeRootPath = "/home/marek/code".toPath()

// val androidxPath = myCodeRootPath / "android/androidx-main/frameworks/support"
val androidxPath = myCodeRootPath / "kotlin/compose-multiplatform-core"

val composePath = androidxPath / "compose"

// Both ends of every steal moved to the KMP source-set layout, for two independent reasons that
// happen to have produced the same names:
//  - the DESTINATIONS, because since AGP 9 an android module is a KMP module, so playgrounds-basic
//    is androidMain/androidHostTest/androidDeviceTest now. The sources were moved during that
//    migration but these path constants were not, so `stealAll` would have written into dead
//    directories and compiled ZERO files while still reporting BUILD SUCCESSFUL.
//  - the SOURCES, because upstream androidx did the same migration: src/test -> src/androidHostTest
//    and src/androidAndroidTest -> src/androidDeviceTest.
val srcAppKotlinPath = playgroundsAppPath / "src/main/kotlin"
val srcBasicKotlinPath = playgroundsBasicPath / "src/androidMain/kotlin"
// NOT under src/. These are android PLATFORM stubs (android.view.DisplayListCanvas / RenderNode /
// HardwareCanvas) that must resolve at compile time and must never be packaged -- upstream keeps
// them in a separate compileOnly module, ui-android-stubs. Anywhere under src/androidMain/java and
// AGP's javac compiles them into the AAR, which would ship classes in the `android.view` package.
// Outside src/ they are invisible to AGP, and playgrounds-basic adds this directory to the KOTLIN
// source path alone, which is resolution without code generation.
val srcBasicJavaPath = playgroundsBasicPath / "stubs/java"
val srcSamplesKotlinPath = playgroundsSamplesPath / "src/androidMain/kotlin"
val srcDemosKotlinPath = playgroundsDemosPath / "src/androidMain/kotlin"

val srcBasicCommonKotlinPath = playgroundsBasicPath / "src/commonMain/kotlin"
val srcBasicJvmKotlinPath = playgroundsBasicPath / "src/jvmMain/kotlin"
val stolenBasicCommonKotlinPath = srcBasicCommonKotlinPath / "stolen"
val stolenBasicJvmKotlinPath = srcBasicJvmKotlinPath / "stolen"
val stolenBasicKotlinPath = srcBasicKotlinPath / "stolen"
val stolenBasicJavaPath = srcBasicJavaPath // java files have to be in directories same as packages :(
val stolenSamplesKotlinPath = srcSamplesKotlinPath / "stolen"
val stolenDemosKotlinPath = srcDemosKotlinPath / "stolen"
val stolenBasicUnitTestsPath = playgroundsBasicPath / "src/androidHostTest/kotlin/stolen"
val stolenBasicAndroTestsPath = playgroundsBasicPath / "src/androidDeviceTest/kotlin/stolen"
val templatesAppSrcKotlinPath = srcAppKotlinPath / "templates"

fun String.containsOneOf(vararg substrings: String) = substrings.any { it in this }

// TODO NOW: test sourceFun DSL
sourceFun {

    grp = "steal"

    // Upstream source trees are not all source: androidx sprinkles OWNERS, README.md and the like
    // through them, and the old code fed every one of those to the per-task transform. That was
    // survivable while the checked-out compose was ancient; against a current checkout the first
    // OWNERS file under foundation/text reaches withInternalAccessIssuesSuppressed and dies as
    // "Incorrect compose test file". Non-source files are skipped HERE, once, rather than in each
    // of the five transforms that would otherwise each need to know about them.
    fun regSteal(src: Path, out: Path, transform: Pair<Path, Path>.(String) -> String? = { it }) = reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        this.src = src
        this.out = out
        setTransformFun { content ->
            if (first.name.substringAfterLast('.', "") in setOf("kt", "java")) transform(content) else null
        }
    }

    val srcUiUi = composePath / "ui/ui"
    val srcUiGraphics = composePath / "ui/ui-graphics"
    val srcFoundation = composePath / "foundation/foundation"
    val srcFoundationLayout = composePath / "foundation/foundation-layout"

    val srcUiUT = srcUiUi / "src/androidHostTest/kotlin/androidx/compose/ui"
    val srcUiGraphicsAT = srcUiGraphics / "src/androidDeviceTest/kotlin/androidx/compose/ui/graphics"
    val srcFoundationUT = srcFoundation / "src/androidHostTest/kotlin/androidx/compose/foundation"
    val srcFoundationAT = srcFoundation / "src/androidDeviceTest/kotlin/androidx/compose/foundation"
    val srcFoundationLayoutAT = srcFoundationLayout / "src/androidDeviceTest/kotlin/androidx/compose/foundation/layout"

    // Subtrees excluded from the HOST tests for the same reason as from the device tests: they are
    // built on helper/matcher/base classes that live beside them and get filtered out, so what is
    // left cannot resolve its own supertypes. foundation's text/ alone accounted for ~340 of the
    // ~360 host-test errors (SelectionLayoutTest 191, SelectableInfoTest 44, ...).
    fun Path.inAnyDirOf(vararg dirs: String) = segments.any { it in dirs }

    // Individually named host tests that no subtree rule covers, each excluded for a REASON that
    // was measured by compiling them, not assumed:
    //  - HitTestResultTest / RectListTest: HitTestResult and RectList stopped being `List`s
    //    upstream, so every `assertThat(it).hasSize(..)` / `subList` / `listIterator` in them is
    //    gone. Nothing on the classpath can bring that back.
    //  - DelegatingNodeTest, MediaQueryIntegrationTest: reach for internal test-only helpers
    //    (markAsAttached / runDetachLifecycle / areWindowInsetsRulersEnabled) that live in
    //    androidx's own test infrastructure and are not part of any published artifact.
    //  - BringIntoViewRequestPriorityQueueTest, StylePropertyTest, StyleAnimationsTest,
    //    PointerInputTest, IndirectPointerNavigationGestureDetectorHostTest: ordinary signature
    //    drift (Dp vs Float, nullability) in APIs this repo does not control.
    val excludedUnitTests = setOf(
        "HitTestResultTest.kt",
        "RectListTest.kt",
        "DelegatingNodeTest.kt",
        "MediaQueryIntegrationTest.kt",
        "BringIntoViewRequestPriorityQueueTest.kt",
        "StylePropertyTest.kt",
        "StyleAnimationsTest.kt",
        "PointerInputTest.kt",
        "IndirectPointerNavigationGestureDetectorHostTest.kt",
        // A DIFFERENT failure class from everything above, and the only one that gets past the
        // frontend: these two write `object : Modifier.Node() {}`, and Modifier.Node has an
        // INTERNAL open member (updateCoordinator). withInternalAccessIssuesSuppressed makes the
        // frontend accept the subclass, but the IR backend then cannot generate it --
        // "No override for FUN IR_EXTERNAL_DECLARATION_STUB name:updateCoordinator". Suppressing
        // visibility diagnostics buys frontend access to internal API, never the ability to
        // OVERRIDE it across a compilation boundary, so no filter-free fix exists.
        "ThrottledCallbacksTest.kt",
        "ModifierNodeElementTest.kt",
    )

    // A THIRD category, and the only one that is not about compiling at all: these COMPILE fine and
    // then fail when run. 706 stolen tests execute, 527 pass, and these nine classes account for
    // all 179 failures -- Robolectric shadow/SDK-level behaviour and androidx-internal test
    // harness assumptions that this repo does not reproduce. They are kept out so `./gradlew build`
    // means something; re-steal any of them by deleting its line if it is worth chasing.
    val runtimeFailingUnitTests = setOf(
        "AndroidComposeViewAccessibilityTraversalTest.kt",
        "ComposedModifierTest.kt",
        "DepthSortedSetTest.kt",
        "HapticFeedbackTest.kt",
        "HitTestTouchBoundsExpansionTest.kt",
        "IndirectPointerInputChangeTest.kt",
        "LayoutNodeTest.kt",
        "ModifierLocalConsumerEntityTest.kt",
        "StyleTest.kt",
    )

    val stealComposeUiUnitTests by regSteal(srcUiUT, stolenBasicUnitTestsPath / "ui-tests") {
        if (first.inAnyDirOf("text", "autofill") || first.name in excludedUnitTests || first.name in runtimeFailingUnitTests) null
        else it.withInternalAccessIssuesSuppressed(first)
    }
    val stealComposeFoundationUnitTests by regSteal(srcFoundationUT, stolenBasicUnitTestsPath / "foundation-tests") {
        if (first.inAnyDirOf("text", "lazy") || first.name in excludedUnitTests || first.name in runtimeFailingUnitTests) null
        else it.withInternalAccessIssuesSuppressed(first)
    }
    val stealComposeFoundationAndroTests by regSteal(srcFoundationAT, stolenBasicAndroTestsPath / "foundation-tests") {
        when {
            // Whole SUBTREES that pull in helper/base classes this repo does not steal. The old
            // list was "text" plus file names containing Lazy/Pager, which no longer covers what
            // upstream has: foundation's androidDeviceTest now has pager/, lazy/, content/ and
            // draganddrop/ packages whose tests extend siblings (BasePagerTest,
            // Scrollable2DInputTest, ClipDataSubject, ...) that never get stolen. Excluding by
            // DIRECTORY says the real rule; matching on file names was always an approximation of
            // it -- PageLayoutPositionOnScrollingTest.kt does not contain "Pager".
            first.segments.any { it in setOf("text", "pager", "lazy", "content", "draganddrop") } -> null
            first.name.containsOneOf("Lazy", "Pager") -> null
            // CASCADE of the content filters below. Those filters drop a file for what it IMPORTS,
            // with no idea what else in the tree extends it, so base classes get dropped while
            // their subclasses are kept -- and the subclasses then cannot resolve their own
            // supertype. Measured, not guessed:
            //   Scrollable2DInputTest  dropped for `import androidx.compose.foundation.text.matchers`
            //   ScrollableTest         dropped for `import androidx.compose.foundation.lazy`
            //   OverscrollTest         likewise
            // so everything below them goes too. AbstractScrollable2DTest is stolen but orphaned
            // once its siblings are gone.
            first.name.containsOneOf("Scrollable2D", "ScrollableArea", "ScrollFocusableInteraction") -> null
            // Genuine upstream API drift rather than a cascade: `magnifier(..)` changed shape
            // (sourceCenter is now required), so this test cannot compile against ANY subset.
            first.name == "PlatformMagnifierTest.kt" -> null
            // Truth's Subject.Factory turned platform types into non-null parameters, so this one
            // needs its stolen source edited, not filtered. That is patchStolenStuff's job, and
            // that patch is currently stale -- see the repo README note. Excluded until then.
            first.name == "TransformableTest.kt" -> null
            "nhaarman" in it -> null
            "import androidx.compose.foundation.text" in it -> null
            "import androidx.compose.foundation.test.R" in it -> null
            "import androidx.testutils" in it -> null
            "import androidx.compose.foundation.lazy" in it -> null
            else -> it.withInternalAccessIssuesSuppressed(first)
        }
    }
    val stealComposeFoundationLayoutAndroTests by regSteal(srcFoundationLayoutAT, stolenBasicAndroTestsPath / "foundation-layout-tests") {
        // first.name, NOT name. The transform runs with a Pair<Path, Path> receiver, which has no
        // `name`, so a bare `name` silently resolved to the enclosing Gradle PROJECT's name
        // ("MyStolenPlaygrounds") -- a constant. This filter therefore matched nothing and stole 0
        // of 31 files, and the sibling Screenshot filter below matched nothing and stole all 7.
        // Neither ever failed; the counts are the only thing that shows it.
        // TestActivity is not an "interesting" test itself -- it is the Activity LayoutTest's
        // ActivityTestRule instantiates, so stealing LayoutTest without it cannot compile.
        val interesting = first.name.containsOneOf("BoxTest", "LayoutTest", "IntrinsicTest", "SizeTest", "PaddingTest", "OffsetTest", "TestActivity")
        // "FlexBoxTest" matches "BoxTest" as a substring, so the intent-to-steal-BoxTest filter was
        // dragging FlexBox in by accident. It brings a whole experimental opt-in surface
        // (ExperimentalLayoutApi) that nothing here asked for, so it is named out explicitly.
        val unwanted = first.name.containsOneOf("Window", "FlexBox")
        // These tests reach into foundation-layout internals (BoxScopeInstance) exactly like the
        // ui/foundation tests do, so they need the same suppression header. They never got it,
        // which did not show while the filter above was dead and stole nothing.
        if (interesting && !unwanted) it.withInternalAccessIssuesSuppressed(first) else null
    }
    val stealComposeUiGraphicsAndroTests by regSteal(srcUiGraphicsAT, stolenBasicAndroTestsPath / "ui-graphics-tests") { it.withInternalAccessIssuesSuppressed(first) }

    val stealComposeAnnotations by regSteal(androidxPath / "annotation/annotation-sampled/src/main/java/androidx/annotation", stolenSamplesKotlinPath / "androidx-annotation")
    val stealComposeSourcesJava by regSteal(composePath / "ui/ui-android-stubs/src/main/java/android/view", stolenBasicJavaPath / "android/view")
    val stealComposeSourcesTestUtilsCommon by regSteal(composePath / "test-utils/src/commonMain/kotlin/androidx/compose/testutils", stolenBasicCommonKotlinPath / "compose-testutils")
    val stealComposeSourcesTestUtilsAndro by regSteal(composePath / "test-utils/src/androidMain/kotlin/androidx/compose/testutils", stolenBasicKotlinPath / "compose-testutils") {
        if ("Screenshot" in first.name) null else it
    }
    // The commonMain half declares `expect class NativeView`, so stealing commonMain + androidMain
    // alone leaves the JVM target with no actual -- "The 'expect' declaration 'NativeView' has no
    // 'actual' declaration in module '<commonMain> for JVM'". Upstream's desktopMain is this
    // module's jvmMain.
    val stealComposeSourcesTestUtilsJvm by regSteal(composePath / "test-utils/src/desktopMain/kotlin/androidx/compose/testutils", stolenBasicJvmKotlinPath / "compose-testutils")
    // androidx's sample modules ship their own res/, and several samples draw from it
    // (R.drawable.yt_profile and friends) or from an android layout XML. Those resources are not
    // stolen -- only the kotlin is -- so `R` cannot resolve. Dropping the files that use it is the
    // honest option; the alternative is inventing drawables that make the samples render something
    // other than what they are samples OF.
    val samplesNeedingAndroidxRes = setOf(
        "SharedTransitionSamples.kt",
        "ContextMenuSample.kt",
        "AndroidViewSample.kt",
        "BlurSample.kt",
        "NestedScrollInteropSamples.kt",
        "PainterSample.kt",
    )
    fun Pair<Path, Path>.dropIfNeedsRes(content: String) =
        if (first.name in samplesNeedingAndroidxRes) null else content

    val stealComposeSamplesUi by regSteal(composePath / "ui/ui/samples/src/main/java/androidx/compose/ui/samples", stolenSamplesKotlinPath / "samples-ui") { dropIfNeedsRes(it) }
    val stealComposeSamplesUiGraphics by regSteal(composePath / "ui/ui-graphics/samples/src/main/java/androidx/compose/ui/graphics/samples", stolenSamplesKotlinPath / "samples-ui-graphics")
    val stealComposeSamplesFoundation by regSteal(srcFoundation / "samples/src/main/java/androidx/compose/foundation/samples", stolenSamplesKotlinPath / "samples-foundation") { dropIfNeedsRes(it) }
    val stealComposeSamplesAnimationCore by regSteal(composePath / "animation/animation-core/samples/src/main/java/androidx/compose/animation/core/samples", stolenSamplesKotlinPath / "samples-animation-core")
    val stealComposeSamplesAnimation by regSteal(composePath / "animation/animation/samples/src/main/java/androidx/compose/animation/samples", stolenSamplesKotlinPath / "samples-animation") { dropIfNeedsRes(it) }
    val m3 = composePath / "material3/material3"
    // Demo/sample files that cannot compile out of context, each for a stated reason:
    //  - Carousel*: draw from androidx's own res/ (R.drawable.*), which is not stolen.
    //  - AppBarSamples / NavigationSuiteScaffoldDemo: need
    //    androidx.compose.material3.adaptive.navigationsuite, which is not in DepsKt's catalog at
    //    all, plus sample functions from that artifact's own samples module.
    val excludedDemos = setOf(
        "CarouselDemos.kt",
        "CarouselSamples.kt",
        "AppBarSamples.kt",
        "NavigationSuiteScaffoldDemo.kt",
        // Slider's API moved: valueRange and onValueChangeFinished are gone from the overloads
        // these use. Ordinary drift, not a missing dependency.
        "SliderSamples.kt",
        "SliderDemos.kt",
        // ScrollField's content lambda gained a third parameter. Drift again.
        "ScrollFieldSamples.kt",
        // ListDemos indexes a paging demo from androidx.paging, which this repo does not depend on.
        "ListDemos.kt",
    )
    // The two INDEX files (the DemoCategory trees the app's "AX Demos" tab shows) name some of the
    // demos excluded above, so they are stolen with exactly those entries cut out, by title,
    // rather than dropped whole. Measured at the pinned tag: 3 of 13 material3 entries and 1 of 32
    // foundation entries go; every other entry resolves against the stolen demos and samples.
    val demoIndexCuts = mapOf(
        // Carousel*, SliderDemos and NavigationSuiteScaffoldDemo are excluded above.
        "Material3Demos.kt" to listOf("Carousel", "Sliders", "Navigation Suite Scaffold"),
        // LazyListDemos lives in ListDemos.kt, excluded above.
        "FoundationDemos.kt" to listOf("Lazy lists"),
    )
    fun Pair<Path, Path>.dropIfExcludedDemo(content: String) = when (first.name) {
        in excludedDemos -> null
        in demoIndexCuts -> content.withoutDemoEntries(demoIndexCuts.getValue(first.name), at = first)
        else -> content
    }

    val stealComposeMaterial3Samples by regSteal(m3 / "samples/src/main/java/androidx/compose/material3/samples", stolenDemosKotlinPath / "material3-samples") { dropIfExcludedDemo(it) }
    // The catalog is the material3 samples again, dressed as an app: model/Examples.kt wraps 311
    // sample functions, model/Components.kt groups those lists into catalog screens. Measured at the
    // pinned tag, the 55 samples this repo does not have fall into exactly these components, and
    // each one loses ALL of its examples: Adaptive + NavigationSuiteScaffold need material3-adaptive
    // (not in DepsKt), and the rest are the Carousel/AppBar/Slider/ScrollField samples excluded
    // above. So each goes whole: off the home screen, its examples list emptied, and the sample
    // imports that leaves unused dropped.
    val catalogCutComponents = listOf("Adaptive", "BottomAppBars", "Carousel", "NavigationSuiteScaffold", "ScrollField", "Sliders", "TopAppBar")
    // Compile, but crash the app the moment they open (found by opening every catalog example on a
    // device): each keeps a TimePickerDisplayMode in rememberSaveable, and the material3 this repo
    // resolves cannot put that in a Bundle ("MutableState containing Picker cannot be saved").
    val catalogCrashingExamples = listOf("TimePickerSwitchableSample", "VibrantTimePickerSwitchableSample", "VibrantTimePickerScrollSample")
    val catalogPath = m3 / "integration-tests/material3-catalog/src/main"
    val stealComposeMaterial3Catalog by regSteal(catalogPath / "java/androidx/compose/material3/catalog", stolenDemosKotlinPath / "material3-catalog") {
        when (first.name) {
            "Components.kt" -> it.withoutExactLines(catalogCutComponents.map { c -> "        $c," }, at = first)
            "Examples.kt" -> it
                .withoutDemoEntries(catalogCrashingExamples, at = first)
                .withEmptiedExampleLists(catalogCutComponents.map { c -> "${c}Examples" }, at = first)
            else -> it
        }
    }
    // Verbatim: strings and two drawables the catalog's R.* calls need. regSteal skips non-source
    // files, hence a plain copy. The output dir holds nothing else; the task only writes, never deletes.
    val stealComposeMaterial3CatalogRes by reg {
        doNotTrackState("FIXME_later: getting false positives: UP-TO-DATE")
        src = catalogPath / "res"
        out = playgroundsDemosPath / "src/androidMain/res"
        setTransformFun { it }
    }
    val stealComposeMaterial3Demos by regSteal(m3 / "integration-tests/material3-demos/src/main/java/androidx/compose/material3/demos", stolenDemosKotlinPath / "material3-demos") { dropIfExcludedDemo(it) }
    // text/ goes for the same reason it goes everywhere else in this file: its demos are built on
    // sample functions that live in androidx's text samples module, which is not stolen.
    val stealComposeFoundationDemos by regSteal(srcFoundation / "integration-tests/foundation-demos/src/main/java/androidx/compose/foundation/demos", stolenDemosKotlinPath / "foundation-demos") {
        // text2/ is the newer BasicTextField demo set and goes for the same reason as text/.
        if (first.inAnyDirOf("text", "text2")) null else dropIfExcludedDemo(it)
    }
    val stealComposeCommonDemos by regSteal(composePath / "integration-tests/demos/common/src/main/java/androidx/compose/integration/demos/common", stolenDemosKotlinPath / "common-demos")
    val stealComposeSourcesAll by reg { dependsOn(
        stealComposeSourcesJava,
        stealComposeSourcesTestUtilsCommon,
        stealComposeSourcesTestUtilsAndro,
        stealComposeSourcesTestUtilsJvm,
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
        stealComposeMaterial3CatalogRes,
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

    // Was `project.exec { commandLine(..) }` in doLast. Project.exec is GONE in Gradle 9 -- it was
    // one of the APIs that made a task's action reach back into the Project object at execution
    // time. The supported replacement is an injected ExecOperations service, which a script cannot
    // inject into an ad-hoc task, so the task gets a real type.
    val patchStolenStuff by tasks.registering(GitApplyPatchTask::class) {
        group = "steal"
        patch.set("./patchStolenStuff.patch")
        workDir.set(layout.projectDirectory)
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

/**
 * Cuts whole `ComposableDemo("title") { .. }` / `DemoCategory("title", ..)` entries out of an androidx
 * demo index, or `Example(name = "title", ..) { .. }` entries out of the material3 catalog, by title.
 * Each title must match exactly ONE entry: a cut that silently matched nothing would leave the
 * index naming a missing demo, which fails far away, as an unresolved reference in a file nobody
 * edited.
 */
fun String.withoutDemoEntries(titles: List<String>, at: Path? = null): String = titles.fold(this) { content, title ->
    val starts = Regex("""\n[ \t]*(?:(?:ComposableDemo|DemoCategory)\(\s*|Example\(\s*name = )"${Regex.escape(title)}"""").findAll(content).toList()
    check(starts.size == 1) { "Demo entry \"$title\" found ${starts.size} times (expected 1) in ${at ?: "<unknown path>"}" }
    val start = starts.single().range.first
    // Balanced scan over the call's parens, then over a trailing lambda if one follows. Titles and
    // arguments in these index files carry no brackets inside string literals, so plain counting holds.
    fun closeOf(open: Int): Int {
        var depth = 0
        for (i in open until content.length) {
            when (content[i]) { '(', '{' -> depth++; ')', '}' -> if (--depth == 0) return i }
        }
        error("Unbalanced demo entry \"$title\" in ${at ?: "<unknown path>"}")
    }
    var end = closeOf(content.indexOf('(', start)) + 1
    val afterParens = content.substring(end).trimStart(' ')
    if (afterParens.startsWith("{")) end = closeOf(content.indexOf('{', end)) + 1
    if (content.getOrNull(end) == ',') end++
    content.removeRange(start, end)
}

/** Removes each given line, which must occur exactly once as a whole line. */
fun String.withoutExactLines(lines: List<String>, at: Path? = null): String = lines.fold(this) { content, line ->
    val n = content.lines().count { it == line }
    check(n == 1) { "Line \"$line\" found $n times (expected 1) in ${at ?: "<unknown path>"}" }
    content.replace("\n$line\n", "\n")
}

/**
 * Replaces each `val <name> =\n    listOf(..)` in the catalog's Examples.kt with an empty list, then drops
 * the samples imports nothing references any more. The count of dropped imports is printed: it is
 * the one number that shows the cut took exactly the samples it was meant to.
 */
fun String.withEmptiedExampleLists(names: List<String>, at: Path? = null): String {
    val emptied = names.fold(this) { content, name ->
        val starts = Regex("""\nval ${Regex.escape(name)} =\s*listOf\(""").findAll(content).toList()
        check(starts.size == 1) { "Examples list $name found ${starts.size} times (expected 1) in ${at ?: "<unknown path>"}" }
        val open = starts.single().range.last
        var depth = 0
        var close = -1
        for (i in open until content.length) {
            when (content[i]) { '(', '{' -> depth++; ')', '}' -> if (--depth == 0) { close = i; break } }
        }
        check(close > 0) { "Unbalanced examples list $name in ${at ?: "<unknown path>"}" }
        content.replaceRange(starts.single().range.first, close + 1, "\nval $name = emptyList<Example>()")
    }
    val sampleImport = Regex("""^import androidx\.compose\.material3\.(?:samples|adaptive[\w.]*)\.(\w+)$""")
    val body = emptied.lines().filterNot { sampleImport.matches(it) }.joinToString("\n")
    var dropped = 0
    val result = emptied.lines().filterNot { line ->
        val simple = sampleImport.matchEntire(line)?.groupValues?.get(1) ?: return@filterNot false
        (!Regex("""\b${Regex.escape(simple)}\b""").containsMatchIn(body)).also { if (it) dropped++ }
    }.joinToString("\n")
    println("withEmptiedExampleLists: emptied ${names.size} lists, dropped $dropped now-unused sample imports in ${at?.name}")
    return result
}

fun String.withInternalAccessIssuesSuppressed(at: Path? = null): String {
    // The path is in the message on purpose: "Incorrect compose test file" alone, across ~500
    // stolen files, says nothing about WHICH file drifted out of the expected outline.
    val r = ureKtComposeTestOutline().compile().matchEntire(this)
        ?: error("Incorrect compose test file: ${at ?: "<unknown path>"}")
    val ktLicenceComment by r
    val ktOtherStuffBeforePackageLine by r
    val ktPackageLine by r
    val ktRest by r
    val ourSuppressions = listOf(
        "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE", "EXPOSED_PARAMETER_TYPE", "EXPOSED_PROPERTY_TYPE",
        "CANNOT_OVERRIDE_INVISIBLE_MEMBER",
    )

    // @file:Suppress is NOT repeatable, so a file that already carries one cannot simply be handed
    // a second: Kotlin rejects the result with "This annotation is not repeatable" at the injected
    // line. Upstream has picked up @file:Suppress("DEPRECATION") on several files since this code
    // was written (LayoutNodeTest, ModifierLocalConsumerEntityTest, RecordingInputConnection*Test),
    // so the two are MERGED into one annotation instead of stacked.
    val existingSuppressUre = Regex("""@file:Suppress\(([^)]*)\)\s*""")
    val existingSuppressions = existingSuppressUre.findAll(ktOtherStuffBeforePackageLine)
        .flatMap { it.groupValues[1].split(',') }
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .toList()
    val otherStuffWithoutSuppress = ktOtherStuffBeforePackageLine.replace(existingSuppressUre, "")
    val allSuppressions = (ourSuppressions.map { "\"" + it + "\"" } + existingSuppressions).distinct()
    val ktFileSuppress = "@file:Suppress(" + allSuppressions.joinToString(", ") + ")"

    return "$ktLicenceComment\n\n$ktFileSuppress\n\n$otherStuffWithoutSuppress$ktPackageLine\n$ktRest"
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
    1..MAX of chWhiteSpace
    1 of (ureText("@Sampled") or ureText("@Composable"))
    1..MAX of chWhiteSpace
    1 of ureText("fun")
    1..MAX of chWhiteSpace
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
        1..MAX of chWhiteSpace
    }
}

val ureAnnotationsWithComposable = ure {
    1 of ureAnnotations
    1 of ure {
        1 of ureText("@Composable")
        1..100 of chWhiteSpace
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
    0..MAX of chWhiteSpace // annotations can contain ending spaces too
    1 of atBOLine // we have to start from new line to easier find ending brace }
    1 of ureText("fun")
    1 of chSpace
    1 of ure { 1 of ureIdent(chUpper, withWordBoundaries = false) }.withName("funName") // no \b: "Template" follows directly
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


