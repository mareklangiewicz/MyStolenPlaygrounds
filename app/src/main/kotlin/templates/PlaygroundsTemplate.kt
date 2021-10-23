package pl.mareklangiewicz.playgrounds

import androidx.compose.animation.core.samples.*
import androidx.compose.animation.samples.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.samples.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.samples.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.mareklangiewicz.myfancyframe.MyFancyFrame

data class MySampleData(val title: String, val path: String?, val code: @Composable () -> Unit)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaygroundsTemplate() {

    val srcKotlinDir = "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin"

    @OptIn(ExperimentalComposeUiApi::class)
    val samples = listOf(
        MySampleData("Some Sample 0", "${srcKotlinDir}/blabla.kt") { Text("Some Sample 0") }, // REPLACE
        MySampleData("Some Sample 1", "${srcKotlinDir}/blabla.kt") { Text("Some Sample 1") }, // REMOVE
        MySampleData("Some Sample 2", "${srcKotlinDir}/blabla.kt") { Text("Some Sample 2") }, // REMOVE
        MySampleData("Some Sample 3", "${srcKotlinDir}/blabla.kt") { Text("Some Sample 3") } // REMOVE
    )

    var selectedSample by remember { mutableStateOf(samples[0]) }

    Row {
        LazyVerticalGrid(cells = GridCells.Adaptive(164.dp), modifier = Modifier.weight(.5f)) {
            for (sample in samples)
                MySampleItem(sample) { selectedSample = it; println(it.path) }

            MyFancyItem("Some Sample") { Text("Some Sample") } // REMOVE
            MyFancyItem("Some Sample") { Text("Some Sample") } // REMOVE
            MyFancyItem("Some Sample") { Text("Some Sample") } // REMOVE
            for (i in 1..15) MyFancyItem("Hello Column") { HelloColumn() } // REMOVE
            for (i in 1..15) MyFancyItem("Another Sample $i") { Text("Some Sample $i") } // REMOVE
        }
        MyFancyFrame(Modifier.weight(1f), selectedSample.title) {
            selectedSample.code()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.MySampleItem(data: MySampleData, onSampleClick: (MySampleData) -> Unit = {}) {
    MyFancyItem(data.title, { onSampleClick(data) }, data.code)
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.MyFancyItem(title: String, onClick: () -> Unit = {}, content: @Composable () -> Unit) {
    item { MyFancyFrame(Modifier.size(164.dp, 256.dp), title = title, onClick = onClick) { content() } }
}


// BEGIN generated Playgrounds from PlaygroundsTemplate
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Playgrounds() {

    val srcKotlinDir = "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin"

    @OptIn(ExperimentalComposeUiApi::class)
    val samples = listOf(
        MySampleData("AlignmentLineSample", "${srcKotlinDir}/stolen/ui-samples/AlignmentLineSample.kt") { AlignmentLineSample() },
        MySampleData("AlphaSample", "${srcKotlinDir}/stolen/ui-samples/AlphaSample.kt") { AlphaSample() },
        MySampleData("AndroidViewSample", "${srcKotlinDir}/stolen/ui-samples/AndroidViewSample.kt") { AndroidViewSample() },
        MySampleData("AndroidDrawableInDrawScopeSample", "${srcKotlinDir}/stolen/ui-samples/AndroidViewSample.kt") { AndroidDrawableInDrawScopeSample() },
        MySampleData("BlurSample", "${srcKotlinDir}/stolen/ui-samples/BlurSample.kt") { BlurSample() },
        MySampleData("ImageBlurSample", "${srcKotlinDir}/stolen/ui-samples/BlurSample.kt") { ImageBlurSample() },
        MySampleData("DialogSample", "${srcKotlinDir}/stolen/ui-samples/DialogSample.kt") { DialogSample() },
        MySampleData("DrawWithCacheModifierSample", "${srcKotlinDir}/stolen/ui-samples/DrawModifierSample.kt") { DrawWithCacheModifierSample() },
        MySampleData("DrawWithCacheModifierStateParameterSample", "${srcKotlinDir}/stolen/ui-samples/DrawModifierSample.kt") { DrawWithCacheModifierStateParameterSample() },
        MySampleData("DrawWithCacheContentSample", "${srcKotlinDir}/stolen/ui-samples/DrawModifierSample.kt") { DrawWithCacheContentSample() },
        MySampleData("FocusableSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { FocusableSample() },
        MySampleData("FocusableSampleUsingLowerLevelFocusTarget", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { FocusableSampleUsingLowerLevelFocusTarget() },
        MySampleData("CaptureFocusSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { CaptureFocusSample() },
        MySampleData("RequestFocusSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { RequestFocusSample() },
        MySampleData("ClearFocusSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { ClearFocusSample() },
        MySampleData("MoveFocusSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { MoveFocusSample() },
        MySampleData("CreateFocusRequesterRefsSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { CreateFocusRequesterRefsSample() },
        MySampleData("CustomFocusOrderSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { CustomFocusOrderSample() },
//        MySampleData("FocusPropertiesSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { FocusPropertiesSample() },
        MySampleData("InspectableModifierSample", "${srcKotlinDir}/stolen/ui-samples/InspectableModifierSample.kt") { InspectableModifierSample() },
        MySampleData("KeyEventSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { KeyEventSample() },
        MySampleData("KeyEventTypeSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { KeyEventTypeSample() },
        MySampleData("KeyEventIsAltPressedSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { KeyEventIsAltPressedSample() },
        MySampleData("KeyEventIsCtrlPressedSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { KeyEventIsCtrlPressedSample() },
        MySampleData("KeyEventIsMetaPressedSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { KeyEventIsMetaPressedSample() },
        MySampleData("KeyEventIsShiftPressedSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { KeyEventIsShiftPressedSample() },
        MySampleData("ChangeOpacity", "${srcKotlinDir}/stolen/ui-samples/LayerModifierSamples.kt") { ChangeOpacity() },
        MySampleData("AnimateFadeIn", "${srcKotlinDir}/stolen/ui-samples/LayerModifierSamples.kt") { AnimateFadeIn() },
        MySampleData("LayoutModifierSample", "${srcKotlinDir}/stolen/ui-samples/LayoutSample.kt") { LayoutModifierSample() },
        MySampleData("ConvenienceLayoutModifierSample", "${srcKotlinDir}/stolen/ui-samples/LayoutSample.kt") { ConvenienceLayoutModifierSample() },
        MySampleData("ModifierLocalParentChildCommunicationWithinLayoutNodeSample", "${srcKotlinDir}/stolen/ui-samples/ModifierLocalSamples.kt") { ModifierLocalParentChildCommunicationWithinLayoutNodeSample() },
        MySampleData("ModifierLocalChildParentCommunicationWithinLayoutNodeSample", "${srcKotlinDir}/stolen/ui-samples/ModifierLocalSamples.kt") { ModifierLocalChildParentCommunicationWithinLayoutNodeSample() },
        MySampleData("ModifierLocalParentChildCommunicationInterLayoutNodeSample", "${srcKotlinDir}/stolen/ui-samples/ModifierLocalSamples.kt") { ModifierLocalParentChildCommunicationInterLayoutNodeSample() },
        MySampleData("ModifierLocalChildParentCommunicationInterLayoutNodeSample", "${srcKotlinDir}/stolen/ui-samples/ModifierLocalSamples.kt") { ModifierLocalChildParentCommunicationInterLayoutNodeSample() },
        MySampleData("ModifierUsageSample", "${srcKotlinDir}/stolen/ui-samples/ModifierSamples.kt") { ModifierUsageSample() },
        MySampleData("ModifierFactorySample", "${srcKotlinDir}/stolen/ui-samples/ModifierSamples.kt") { ModifierFactorySample() },
        MySampleData("ModifierParameterSample", "${srcKotlinDir}/stolen/ui-samples/ModifierSamples.kt") { ModifierParameterSample() },
        MySampleData("SubcomponentModifierSample", "${srcKotlinDir}/stolen/ui-samples/ModifierSamples.kt") { SubcomponentModifierSample() },
        MySampleData("NestedScrollConnectionSample", "${srcKotlinDir}/stolen/ui-samples/NestedScrollSamples.kt") { NestedScrollConnectionSample() },
        MySampleData("NestedScrollDispatcherSample", "${srcKotlinDir}/stolen/ui-samples/NestedScrollSamples.kt") { NestedScrollDispatcherSample() },
        MySampleData("OnGloballyPositioned", "${srcKotlinDir}/stolen/ui-samples/OnGloballyPositionedSamples.kt") { OnGloballyPositioned() },
        MySampleData("PainterModifierSample", "${srcKotlinDir}/stolen/ui-samples/PainterSample.kt") { PainterModifierSample() },
        MySampleData("PainterResourceSample", "${srcKotlinDir}/stolen/ui-samples/PainterSample.kt") { PainterResourceSample() },
        MySampleData("PointerIconSample", "${srcKotlinDir}/stolen/ui-samples/PointerIconSample.kt") { PointerIconSample() },
        MySampleData("PopupSample", "${srcKotlinDir}/stolen/ui-samples/PopupSample.kt") { PopupSample() },
        MySampleData("RotateSample", "${srcKotlinDir}/stolen/ui-samples/RotateSample.kt") { RotateSample() },
        MySampleData("ScaleUniformSample", "${srcKotlinDir}/stolen/ui-samples/ScaleSample.kt") { ScaleUniformSample() },
        MySampleData("ScaleNonUniformSample", "${srcKotlinDir}/stolen/ui-samples/ScaleSample.kt") { ScaleNonUniformSample() },
        MySampleData("ShadowSample", "${srcKotlinDir}/stolen/ui-samples/ShadowSample.kt") { ShadowSample() },
        MySampleData("SoftwareKeyboardControllerSample", "${srcKotlinDir}/stolen/ui-samples/SoftwareKeyboardControllerSample.kt") { SoftwareKeyboardControllerSample() },
        MySampleData("ZIndexModifierSample", "${srcKotlinDir}/stolen/ui-samples/ZIndexModifierSample.kt") { ZIndexModifierSample() },
        MySampleData("GradientBrushSample", "${srcKotlinDir}/stolen/ui-graphics-samples/BrushSamples.kt") { GradientBrushSample() },
        MySampleData("DrawScopeSample", "${srcKotlinDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { DrawScopeSample() },
        MySampleData("DrawScopeBatchedTransformSample", "${srcKotlinDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { DrawScopeBatchedTransformSample() },
        MySampleData("DrawScopeOvalBrushSample", "${srcKotlinDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { DrawScopeOvalBrushSample() },
        MySampleData("DrawScopeOvalColorSample", "${srcKotlinDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { DrawScopeOvalColorSample() },
        MySampleData("StampedPathEffectSample", "${srcKotlinDir}/stolen/ui-graphics-samples/PathEffectSample.kt") { StampedPathEffectSample() },
        MySampleData("AnimatableAnimateToGenericsType", "${srcKotlinDir}/stolen/animation-core-samples/AnimatableSamples.kt") { AnimatableAnimateToGenericsType() },
        MySampleData("AlphaAnimationSample", "${srcKotlinDir}/stolen/animation-core-samples/AnimatedValueSamples.kt") { AlphaAnimationSample() },
        MySampleData("ArbitraryValueTypeTransitionSample", "${srcKotlinDir}/stolen/animation-core-samples/AnimatedValueSamples.kt") { ArbitraryValueTypeTransitionSample() },
        MySampleData("DpAnimationSample", "${srcKotlinDir}/stolen/animation-core-samples/AnimatedValueSamples.kt") { DpAnimationSample() },
        MySampleData("InfiniteProgressIndicator", "${srcKotlinDir}/stolen/animation-core-samples/AnimationSpecSamples.kt") { InfiniteProgressIndicator() },
        MySampleData("InfiniteTransitionSample", "${srcKotlinDir}/stolen/animation-core-samples/InfiniteTransitionSamples.kt") { InfiniteTransitionSample() },
        MySampleData("InfiniteTransitionAnimateValueSample", "${srcKotlinDir}/stolen/animation-core-samples/InfiniteTransitionSamples.kt") { InfiniteTransitionAnimateValueSample() },
        MySampleData("GestureAnimationSample", "${srcKotlinDir}/stolen/animation-core-samples/TransitionSamples.kt") { GestureAnimationSample() },
        MySampleData("AnimateFloatSample", "${srcKotlinDir}/stolen/animation-core-samples/TransitionSamples.kt") { AnimateFloatSample() },
        MySampleData("DoubleTapToLikeSample", "${srcKotlinDir}/stolen/animation-core-samples/TransitionSamples.kt") { DoubleTapToLikeSample() },
        MySampleData("ColorAnimationSample", "${srcKotlinDir}/stolen/animation-samples/AnimatedValueSamples.kt") { ColorAnimationSample() },
        MySampleData("HorizontalTransitionSample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { HorizontalTransitionSample() },
        MySampleData("SlideTransition", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { SlideTransition() },
        MySampleData("FadeTransition", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { FadeTransition() },
        MySampleData("FullyLoadedTransition", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { FullyLoadedTransition() },
        MySampleData("AnimatedVisibilityWithBooleanVisibleParamNoReceiver", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { AnimatedVisibilityWithBooleanVisibleParamNoReceiver() },
        MySampleData("SlideInOutSample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { SlideInOutSample() },
        MySampleData("ExpandShrinkVerticallySample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { ExpandShrinkVerticallySample() },
        MySampleData("ExpandInShrinkOutSample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { ExpandInShrinkOutSample() },
        MySampleData("ColumnAnimatedVisibilitySample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { ColumnAnimatedVisibilitySample() },
        MySampleData("AVScopeAnimateEnterExit", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { AVScopeAnimateEnterExit() },
        MySampleData("AnimatedVisibilityLazyColumnSample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { AnimatedVisibilityLazyColumnSample() },
        MySampleData("AVColumnScopeWithMutableTransitionState", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { AVColumnScopeWithMutableTransitionState() },
        MySampleData("AnimateEnterExitPartialContent", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { AnimateEnterExitPartialContent() },
        MySampleData("AnimateContent", "${srcKotlinDir}/stolen/animation-samples/AnimationModifierSample.kt") { AnimateContent() },
        MySampleData("CrossfadeSample", "${srcKotlinDir}/stolen/animation-samples/CrossfadeSample.kt") { CrossfadeSample() },
        MySampleData("GestureAnimationSample", "${srcKotlinDir}/stolen/animation-samples/TransitionSamples.kt") { GestureAnimationSample2() },
        MySampleData("InfiniteTransitionSample", "${srcKotlinDir}/stolen/animation-samples/TransitionSamples.kt") { InfiniteTransitionSample2() },
    )

    var selectedSample by remember { mutableStateOf(samples[0]) }

    Row {
        LazyVerticalGrid(cells = GridCells.Adaptive(164.dp), modifier = Modifier.weight(.5f)) {
            for (sample in samples)
                MySampleItem(sample) { selectedSample = it; println(it.path) }

        }
        MyFancyFrame(Modifier.weight(1f), selectedSample.title) {
            selectedSample.code()
        }
    }
}
// END generated Playgrounds from PlaygroundsTemplate


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PlaygroundsTemplatePreview() {
    PlaygroundsTheme(darkTheme = false) {
        PlaygroundsTemplate()
    }
}


@Composable
private fun HelloColumn() {
    MyFancyFrame(title = "Hello!") {
        val d = LocalDensity.current.density
        Column {
            for (a in 1..5)
                Text(text = "Hello $a d:$d")
        }
    }
}
