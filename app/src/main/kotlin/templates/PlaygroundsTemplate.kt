@file:OptIn(ExperimentalFoundationApi::class)
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

@Composable
fun PlaygroundsTemplate() {

    val samples = listOf(
        MySampleData("Some Sample 0", "/home/marek/code/blabla.kt") { Text("Some Sample 0") }, // REPLACE
        MySampleData("Some Sample 1", "/home/marek/code/blabla.kt") { Text("Some Sample 1") }, // REMOVE
        MySampleData("Some Sample 2", "/home/marek/code/blabla.kt") { Text("Some Sample 2") }, // REMOVE
        MySampleData("Some Sample 3", "/home/marek/code/blabla.kt") { Text("Some Sample 3") } // REMOVE
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

fun LazyGridScope.MySampleItem(data: MySampleData, onSampleClick: (MySampleData) -> Unit = {}) {
    MyFancyItem(data.title, { onSampleClick(data) }, data.code)
}

fun LazyGridScope.MyFancyItem(title: String, onClick: () -> Unit = {}, content: @Composable () -> Unit) {
    item { MyFancyFrame(Modifier.size(164.dp, 256.dp), title = title, onClick = onClick) { content() } }
}


// BEGIN generated Playgrounds from PlaygroundsTemplate
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Playgrounds() {

    val samples = listOf(
        MySampleData("AlignmentLineSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/AlignmentLineSample.kt") { AlignmentLineSample() },
        MySampleData("AlphaSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/AlphaSample.kt") { AlphaSample() },
        MySampleData("AndroidViewSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/AndroidViewSample.kt") { AndroidViewSample() },
        MySampleData("AndroidDrawableInDrawScopeSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/AndroidViewSample.kt") { AndroidDrawableInDrawScopeSample() },
        MySampleData("BlurSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/BlurSample.kt") { BlurSample() },
        MySampleData("ImageBlurSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/BlurSample.kt") { ImageBlurSample() },
        MySampleData("DialogSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/DialogSample.kt") { DialogSample() },
        MySampleData("DrawWithCacheModifierSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/DrawModifierSample.kt") { DrawWithCacheModifierSample() },
        MySampleData("DrawWithCacheModifierStateParameterSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/DrawModifierSample.kt") { DrawWithCacheModifierStateParameterSample() },
        MySampleData("DrawWithCacheContentSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/DrawModifierSample.kt") { DrawWithCacheContentSample() },
        MySampleData("FocusableSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/FocusSamples.kt") { FocusableSample() },
        MySampleData("FocusableSampleUsingLowerLevelFocusTarget", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/FocusSamples.kt") { FocusableSampleUsingLowerLevelFocusTarget() },
        MySampleData("CaptureFocusSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/FocusSamples.kt") { CaptureFocusSample() },
        MySampleData("RequestFocusSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/FocusSamples.kt") { RequestFocusSample() },
        MySampleData("ClearFocusSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/FocusSamples.kt") { ClearFocusSample() },
        MySampleData("MoveFocusSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/FocusSamples.kt") { MoveFocusSample() },
        MySampleData("CreateFocusRequesterRefsSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/FocusSamples.kt") { CreateFocusRequesterRefsSample() },
        MySampleData("CustomFocusOrderSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/FocusSamples.kt") { CustomFocusOrderSample() },
//        MySampleData("FocusPropertiesSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/FocusSamples.kt") { FocusPropertiesSample() },
        MySampleData("InspectableModifierSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/InspectableModifierSample.kt") { InspectableModifierSample() },
        MySampleData("KeyEventSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/KeyInputSamples.kt") { KeyEventSample() },
        MySampleData("KeyEventTypeSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/KeyInputSamples.kt") { KeyEventTypeSample() },
        MySampleData("KeyEventIsAltPressedSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/KeyInputSamples.kt") { KeyEventIsAltPressedSample() },
        MySampleData("KeyEventIsCtrlPressedSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/KeyInputSamples.kt") { KeyEventIsCtrlPressedSample() },
        MySampleData("KeyEventIsMetaPressedSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/KeyInputSamples.kt") { KeyEventIsMetaPressedSample() },
        MySampleData("KeyEventIsShiftPressedSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/KeyInputSamples.kt") { KeyEventIsShiftPressedSample() },
        MySampleData("ChangeOpacity", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/LayerModifierSamples.kt") { ChangeOpacity() },
        MySampleData("AnimateFadeIn", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/LayerModifierSamples.kt") { AnimateFadeIn() },
        MySampleData("LayoutModifierSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/LayoutSample.kt") { LayoutModifierSample() },
        MySampleData("ConvenienceLayoutModifierSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/LayoutSample.kt") { ConvenienceLayoutModifierSample() },
        MySampleData("ModifierLocalParentChildCommunicationWithinLayoutNodeSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ModifierLocalSamples.kt") { ModifierLocalParentChildCommunicationWithinLayoutNodeSample() },
        MySampleData("ModifierLocalChildParentCommunicationWithinLayoutNodeSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ModifierLocalSamples.kt") { ModifierLocalChildParentCommunicationWithinLayoutNodeSample() },
        MySampleData("ModifierLocalParentChildCommunicationInterLayoutNodeSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ModifierLocalSamples.kt") { ModifierLocalParentChildCommunicationInterLayoutNodeSample() },
        MySampleData("ModifierLocalChildParentCommunicationInterLayoutNodeSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ModifierLocalSamples.kt") { ModifierLocalChildParentCommunicationInterLayoutNodeSample() },
        MySampleData("ModifierUsageSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ModifierSamples.kt") { ModifierUsageSample() },
        MySampleData("ModifierFactorySample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ModifierSamples.kt") { ModifierFactorySample() },
        MySampleData("ModifierParameterSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ModifierSamples.kt") { ModifierParameterSample() },
        MySampleData("SubcomponentModifierSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ModifierSamples.kt") { SubcomponentModifierSample() },
        MySampleData("NestedScrollConnectionSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/NestedScrollSamples.kt") { NestedScrollConnectionSample() },
        MySampleData("NestedScrollDispatcherSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/NestedScrollSamples.kt") { NestedScrollDispatcherSample() },
        MySampleData("OnGloballyPositioned", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/OnGloballyPositionedSamples.kt") { OnGloballyPositioned() },
        MySampleData("PainterModifierSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/PainterSample.kt") { PainterModifierSample() },
        MySampleData("PainterResourceSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/PainterSample.kt") { PainterResourceSample() },
        MySampleData("PopupSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/PopupSample.kt") { PopupSample() },
        MySampleData("RotateSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/RotateSample.kt") { RotateSample() },
        MySampleData("ScaleUniformSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ScaleSample.kt") { ScaleUniformSample() },
        MySampleData("ScaleNonUniformSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ScaleSample.kt") { ScaleNonUniformSample() },
        MySampleData("ShadowSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ShadowSample.kt") { ShadowSample() },
        MySampleData("SoftwareKeyboardControllerSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/SoftwareKeyboardControllerSample.kt") { SoftwareKeyboardControllerSample() },
        MySampleData("ZIndexModifierSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-samples/ZIndexModifierSample.kt") { ZIndexModifierSample() },
        MySampleData("GradientBrushSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-graphics-samples/BrushSamples.kt") { GradientBrushSample() },
        MySampleData("DrawScopeSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-graphics-samples/DrawScopeSample.kt") { DrawScopeSample() },
        MySampleData("DrawScopeBatchedTransformSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-graphics-samples/DrawScopeSample.kt") { DrawScopeBatchedTransformSample() },
        MySampleData("DrawScopeOvalBrushSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-graphics-samples/DrawScopeSample.kt") { DrawScopeOvalBrushSample() },
        MySampleData("DrawScopeOvalColorSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-graphics-samples/DrawScopeSample.kt") { DrawScopeOvalColorSample() },
        MySampleData("StampedPathEffectSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/ui-graphics-samples/PathEffectSample.kt") { StampedPathEffectSample() },
        MySampleData("AnimatableAnimateToGenericsType", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-core-samples/AnimatableSamples.kt") { AnimatableAnimateToGenericsType() },
        MySampleData("AlphaAnimationSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-core-samples/AnimatedValueSamples.kt") { AlphaAnimationSample() },
        MySampleData("ArbitraryValueTypeTransitionSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-core-samples/AnimatedValueSamples.kt") { ArbitraryValueTypeTransitionSample() },
        MySampleData("DpAnimationSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-core-samples/AnimatedValueSamples.kt") { DpAnimationSample() },
        MySampleData("InfiniteProgressIndicator", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-core-samples/AnimationSpecSamples.kt") { InfiniteProgressIndicator() },
        MySampleData("InfiniteTransitionSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-core-samples/InfiniteTransitionSamples.kt") { InfiniteTransitionSample() },
        MySampleData("InfiniteTransitionAnimateValueSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-core-samples/InfiniteTransitionSamples.kt") { InfiniteTransitionAnimateValueSample() },
        MySampleData("GestureAnimationSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-core-samples/TransitionSamples.kt") { GestureAnimationSample() },
        MySampleData("AnimateFloatSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-core-samples/TransitionSamples.kt") { AnimateFloatSample() },
        MySampleData("DoubleTapToLikeSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-core-samples/TransitionSamples.kt") { DoubleTapToLikeSample() },
        MySampleData("ColorAnimationSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedValueSamples.kt") { ColorAnimationSample() },
        MySampleData("HorizontalTransitionSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { HorizontalTransitionSample() },
        MySampleData("SlideTransition", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { SlideTransition() },
        MySampleData("FadeTransition", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { FadeTransition() },
        MySampleData("FullyLoadedTransition", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { FullyLoadedTransition() },
        MySampleData("AnimatedVisibilityWithBooleanVisibleParamNoReceiver", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { AnimatedVisibilityWithBooleanVisibleParamNoReceiver() },
        MySampleData("SlideInOutSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { SlideInOutSample() },
        MySampleData("ExpandShrinkVerticallySample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { ExpandShrinkVerticallySample() },
        MySampleData("ExpandInShrinkOutSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { ExpandInShrinkOutSample() },
        MySampleData("ColumnAnimatedVisibilitySample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { ColumnAnimatedVisibilitySample() },
        MySampleData("AVScopeAnimateEnterExit", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { AVScopeAnimateEnterExit() },
        MySampleData("AnimatedVisibilityLazyColumnSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { AnimatedVisibilityLazyColumnSample() },
        MySampleData("AVColumnScopeWithMutableTransitionState", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { AVColumnScopeWithMutableTransitionState() },
        MySampleData("AnimateEnterExitPartialContent", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimatedVisibilitySamples.kt") { AnimateEnterExitPartialContent() },
        MySampleData("AnimateContent", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/AnimationModifierSample.kt") { AnimateContent() },
        MySampleData("CrossfadeSample", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/CrossfadeSample.kt") { CrossfadeSample() },
        MySampleData("GestureAnimationSample2", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/TransitionSamples.kt") { GestureAnimationSample2() },
        MySampleData("InfiniteTransitionSample2", "/home/marek/code/kotlin/MyStolenPlaygrounds/app/src/main/kotlin/stolen/animation-samples/TransitionSamples.kt") { InfiniteTransitionSample2() },
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
