package pl.mareklangiewicz.playgrounds

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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.mareklangiewicz.myfancyframe.MyFancyFrame

data class MySampleData(val title: String, val path: String?, val code: @Composable () -> Unit)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaygroundsTemplate() {

    val samplesDir = "/home/marek/code/kotlin/MyStolenPlaygrounds/lib-ui-samples/src/main/kotlin"

    @OptIn(ExperimentalComposeUiApi::class)
    val samples = listOf(
        MySampleData("Some Sample 0", "${samplesDir}/blabla.kt") { Text("Some Sample 0") }, // REPLACE
        MySampleData("Some Sample 1", "${samplesDir}/blabla.kt") { Text("Some Sample 1") }, // REMOVE
        MySampleData("Some Sample 2", "${samplesDir}/blabla.kt") { Text("Some Sample 2") }, // REMOVE
        MySampleData("Some Sample 3", "${samplesDir}/blabla.kt") { Text("Some Sample 3") } // REMOVE
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

//region Generated Playgrounds from PlaygroundsTemplate
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Playgrounds() {

    val samplesDir = "/home/marek/code/kotlin/MyStolenPlaygrounds/lib-ui-samples/src/main/kotlin"

    @OptIn(ExperimentalComposeUiApi::class)
    val samples = listOf(
        MySampleData("androidx.compose.ui.samples.AlignmentLineSample", "${samplesDir}/stolen/ui-samples/AlignmentLineSample.kt") { androidx.compose.ui.samples.AlignmentLineSample() },
        MySampleData("androidx.compose.ui.samples.AlphaSample", "${samplesDir}/stolen/ui-samples/AlphaSample.kt") { androidx.compose.ui.samples.AlphaSample() },
        MySampleData("androidx.compose.ui.samples.AndroidViewSample", "${samplesDir}/stolen/ui-samples/AndroidViewSample.kt") { androidx.compose.ui.samples.AndroidViewSample() },
        MySampleData("androidx.compose.ui.samples.AndroidDrawableInDrawScopeSample", "${samplesDir}/stolen/ui-samples/AndroidViewSample.kt") { androidx.compose.ui.samples.AndroidDrawableInDrawScopeSample() },
        MySampleData("androidx.compose.ui.samples.BlurSample", "${samplesDir}/stolen/ui-samples/BlurSample.kt") { androidx.compose.ui.samples.BlurSample() },
        MySampleData("androidx.compose.ui.samples.ImageBlurSample", "${samplesDir}/stolen/ui-samples/BlurSample.kt") { androidx.compose.ui.samples.ImageBlurSample() },
        MySampleData("androidx.compose.ui.samples.DialogSample", "${samplesDir}/stolen/ui-samples/DialogSample.kt") { androidx.compose.ui.samples.DialogSample() },
        MySampleData("androidx.compose.ui.samples.DrawWithCacheModifierSample", "${samplesDir}/stolen/ui-samples/DrawModifierSample.kt") { androidx.compose.ui.samples.DrawWithCacheModifierSample() },
        MySampleData("androidx.compose.ui.samples.DrawWithCacheModifierStateParameterSample", "${samplesDir}/stolen/ui-samples/DrawModifierSample.kt") { androidx.compose.ui.samples.DrawWithCacheModifierStateParameterSample() },
        MySampleData("androidx.compose.ui.samples.DrawWithCacheContentSample", "${samplesDir}/stolen/ui-samples/DrawModifierSample.kt") { androidx.compose.ui.samples.DrawWithCacheContentSample() },
        MySampleData("androidx.compose.ui.samples.FocusableSample", "${samplesDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.FocusableSample() },
        MySampleData("androidx.compose.ui.samples.FocusableSampleUsingLowerLevelFocusTarget", "${samplesDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.FocusableSampleUsingLowerLevelFocusTarget() },
        MySampleData("androidx.compose.ui.samples.CaptureFocusSample", "${samplesDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.CaptureFocusSample() },
        MySampleData("androidx.compose.ui.samples.RequestFocusSample", "${samplesDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.RequestFocusSample() },
        MySampleData("androidx.compose.ui.samples.ClearFocusSample", "${samplesDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.ClearFocusSample() },
        MySampleData("androidx.compose.ui.samples.MoveFocusSample", "${samplesDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.MoveFocusSample() },
        MySampleData("androidx.compose.ui.samples.CreateFocusRequesterRefsSample", "${samplesDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.CreateFocusRequesterRefsSample() },
        MySampleData("androidx.compose.ui.samples.CustomFocusOrderSample", "${samplesDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.CustomFocusOrderSample() },
        MySampleData("androidx.compose.ui.samples.FocusPropertiesSample", "${samplesDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.FocusPropertiesSample() },
        MySampleData("androidx.compose.ui.samples.InspectableModifierSample", "${samplesDir}/stolen/ui-samples/InspectableModifierSample.kt") { androidx.compose.ui.samples.InspectableModifierSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventSample", "${samplesDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventTypeSample", "${samplesDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventTypeSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsAltPressedSample", "${samplesDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsAltPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsCtrlPressedSample", "${samplesDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsCtrlPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsMetaPressedSample", "${samplesDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsMetaPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsShiftPressedSample", "${samplesDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsShiftPressedSample() },
        MySampleData("androidx.compose.ui.samples.ChangeOpacity", "${samplesDir}/stolen/ui-samples/LayerModifierSamples.kt") { androidx.compose.ui.samples.ChangeOpacity() },
        MySampleData("androidx.compose.ui.samples.AnimateFadeIn", "${samplesDir}/stolen/ui-samples/LayerModifierSamples.kt") { androidx.compose.ui.samples.AnimateFadeIn() },
        MySampleData("androidx.compose.ui.samples.LayoutModifierSample", "${samplesDir}/stolen/ui-samples/LayoutSample.kt") { androidx.compose.ui.samples.LayoutModifierSample() },
        MySampleData("androidx.compose.ui.samples.ConvenienceLayoutModifierSample", "${samplesDir}/stolen/ui-samples/LayoutSample.kt") { androidx.compose.ui.samples.ConvenienceLayoutModifierSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalParentChildCommunicationWithinLayoutNodeSample", "${samplesDir}/stolen/ui-samples/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalParentChildCommunicationWithinLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalChildParentCommunicationWithinLayoutNodeSample", "${samplesDir}/stolen/ui-samples/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalChildParentCommunicationWithinLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalParentChildCommunicationInterLayoutNodeSample", "${samplesDir}/stolen/ui-samples/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalParentChildCommunicationInterLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalChildParentCommunicationInterLayoutNodeSample", "${samplesDir}/stolen/ui-samples/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalChildParentCommunicationInterLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierUsageSample", "${samplesDir}/stolen/ui-samples/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierUsageSample() },
        MySampleData("androidx.compose.ui.samples.ModifierFactorySample", "${samplesDir}/stolen/ui-samples/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierFactorySample() },
        MySampleData("androidx.compose.ui.samples.ModifierParameterSample", "${samplesDir}/stolen/ui-samples/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierParameterSample() },
        MySampleData("androidx.compose.ui.samples.SubcomponentModifierSample", "${samplesDir}/stolen/ui-samples/ModifierSamples.kt") { androidx.compose.ui.samples.SubcomponentModifierSample() },
        MySampleData("androidx.compose.ui.samples.NestedScrollConnectionSample", "${samplesDir}/stolen/ui-samples/NestedScrollSamples.kt") { androidx.compose.ui.samples.NestedScrollConnectionSample() },
        MySampleData("androidx.compose.ui.samples.NestedScrollDispatcherSample", "${samplesDir}/stolen/ui-samples/NestedScrollSamples.kt") { androidx.compose.ui.samples.NestedScrollDispatcherSample() },
        MySampleData("androidx.compose.ui.samples.OnGloballyPositioned", "${samplesDir}/stolen/ui-samples/OnGloballyPositionedSamples.kt") { androidx.compose.ui.samples.OnGloballyPositioned() },
        MySampleData("androidx.compose.ui.samples.OnPlaced", "${samplesDir}/stolen/ui-samples/OnPlacedSamples.kt") { androidx.compose.ui.samples.OnPlaced() },
        MySampleData("androidx.compose.ui.samples.PainterModifierSample", "${samplesDir}/stolen/ui-samples/PainterSample.kt") { androidx.compose.ui.samples.PainterModifierSample() },
        MySampleData("androidx.compose.ui.samples.PainterResourceSample", "${samplesDir}/stolen/ui-samples/PainterSample.kt") { androidx.compose.ui.samples.PainterResourceSample() },
        MySampleData("androidx.compose.ui.samples.PointerIconSample", "${samplesDir}/stolen/ui-samples/PointerIconSample.kt") { androidx.compose.ui.samples.PointerIconSample() },
        MySampleData("androidx.compose.ui.samples.PopupSample", "${samplesDir}/stolen/ui-samples/PopupSample.kt") { androidx.compose.ui.samples.PopupSample() },
        MySampleData("androidx.compose.ui.samples.RotateSample", "${samplesDir}/stolen/ui-samples/RotateSample.kt") { androidx.compose.ui.samples.RotateSample() },
        MySampleData("androidx.compose.ui.samples.ScaleUniformSample", "${samplesDir}/stolen/ui-samples/ScaleSample.kt") { androidx.compose.ui.samples.ScaleUniformSample() },
        MySampleData("androidx.compose.ui.samples.ScaleNonUniformSample", "${samplesDir}/stolen/ui-samples/ScaleSample.kt") { androidx.compose.ui.samples.ScaleNonUniformSample() },
        MySampleData("androidx.compose.ui.samples.ShadowSample", "${samplesDir}/stolen/ui-samples/ShadowSample.kt") { androidx.compose.ui.samples.ShadowSample() },
        MySampleData("androidx.compose.ui.samples.SoftwareKeyboardControllerSample", "${samplesDir}/stolen/ui-samples/SoftwareKeyboardControllerSample.kt") { androidx.compose.ui.samples.SoftwareKeyboardControllerSample() },
        MySampleData("androidx.compose.ui.samples.ZIndexModifierSample", "${samplesDir}/stolen/ui-samples/ZIndexModifierSample.kt") { androidx.compose.ui.samples.ZIndexModifierSample() },
        MySampleData("androidx.compose.ui.graphics.samples.GradientBrushSample", "${samplesDir}/stolen/ui-graphics-samples/BrushSamples.kt") { androidx.compose.ui.graphics.samples.GradientBrushSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeSample", "${samplesDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeBatchedTransformSample", "${samplesDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeBatchedTransformSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeOvalBrushSample", "${samplesDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeOvalBrushSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeOvalColorSample", "${samplesDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeOvalColorSample() },
        MySampleData("androidx.compose.ui.graphics.samples.StampedPathEffectSample", "${samplesDir}/stolen/ui-graphics-samples/PathEffectSample.kt") { androidx.compose.ui.graphics.samples.StampedPathEffectSample() },
        MySampleData("androidx.compose.animation.core.samples.AnimatableAnimateToGenericsType", "${samplesDir}/stolen/animation-core-samples/AnimatableSamples.kt") { androidx.compose.animation.core.samples.AnimatableAnimateToGenericsType() },
        MySampleData("androidx.compose.animation.core.samples.AlphaAnimationSample", "${samplesDir}/stolen/animation-core-samples/AnimatedValueSamples.kt") { androidx.compose.animation.core.samples.AlphaAnimationSample() },
        MySampleData("androidx.compose.animation.core.samples.ArbitraryValueTypeTransitionSample", "${samplesDir}/stolen/animation-core-samples/AnimatedValueSamples.kt") { androidx.compose.animation.core.samples.ArbitraryValueTypeTransitionSample() },
        MySampleData("androidx.compose.animation.core.samples.DpAnimationSample", "${samplesDir}/stolen/animation-core-samples/AnimatedValueSamples.kt") { androidx.compose.animation.core.samples.DpAnimationSample() },
        MySampleData("androidx.compose.animation.core.samples.InfiniteProgressIndicator", "${samplesDir}/stolen/animation-core-samples/AnimationSpecSamples.kt") { androidx.compose.animation.core.samples.InfiniteProgressIndicator() },
        MySampleData("androidx.compose.animation.core.samples.InfiniteTransitionSample", "${samplesDir}/stolen/animation-core-samples/InfiniteTransitionSamples.kt") { androidx.compose.animation.core.samples.InfiniteTransitionSample() },
        MySampleData("androidx.compose.animation.core.samples.InfiniteTransitionAnimateValueSample", "${samplesDir}/stolen/animation-core-samples/InfiniteTransitionSamples.kt") { androidx.compose.animation.core.samples.InfiniteTransitionAnimateValueSample() },
        MySampleData("androidx.compose.animation.core.samples.GestureAnimationSample", "${samplesDir}/stolen/animation-core-samples/TransitionSamples.kt") { androidx.compose.animation.core.samples.GestureAnimationSample() },
        MySampleData("androidx.compose.animation.core.samples.AnimateFloatSample", "${samplesDir}/stolen/animation-core-samples/TransitionSamples.kt") { androidx.compose.animation.core.samples.AnimateFloatSample() },
        MySampleData("androidx.compose.animation.core.samples.DoubleTapToLikeSample", "${samplesDir}/stolen/animation-core-samples/TransitionSamples.kt") { androidx.compose.animation.core.samples.DoubleTapToLikeSample() },
        MySampleData("androidx.compose.animation.samples.ColorAnimationSample", "${samplesDir}/stolen/animation-samples/AnimatedValueSamples.kt") { androidx.compose.animation.samples.ColorAnimationSample() },
        MySampleData("androidx.compose.animation.samples.HorizontalTransitionSample", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.HorizontalTransitionSample() },
        MySampleData("androidx.compose.animation.samples.SlideTransition", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.SlideTransition() },
        MySampleData("androidx.compose.animation.samples.FadeTransition", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.FadeTransition() },
        MySampleData("androidx.compose.animation.samples.FullyLoadedTransition", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.FullyLoadedTransition() },
        MySampleData("androidx.compose.animation.samples.AnimatedVisibilityWithBooleanVisibleParamNoReceiver", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AnimatedVisibilityWithBooleanVisibleParamNoReceiver() },
        MySampleData("androidx.compose.animation.samples.SlideInOutSample", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.SlideInOutSample() },
        MySampleData("androidx.compose.animation.samples.ExpandShrinkVerticallySample", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.ExpandShrinkVerticallySample() },
        MySampleData("androidx.compose.animation.samples.ExpandInShrinkOutSample", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.ExpandInShrinkOutSample() },
        MySampleData("androidx.compose.animation.samples.ColumnAnimatedVisibilitySample", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.ColumnAnimatedVisibilitySample() },
        MySampleData("androidx.compose.animation.samples.AVScopeAnimateEnterExit", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AVScopeAnimateEnterExit() },
        MySampleData("androidx.compose.animation.samples.AnimatedVisibilityLazyColumnSample", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AnimatedVisibilityLazyColumnSample() },
        MySampleData("androidx.compose.animation.samples.AVColumnScopeWithMutableTransitionState", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AVColumnScopeWithMutableTransitionState() },
        MySampleData("androidx.compose.animation.samples.AnimateEnterExitPartialContent", "${samplesDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AnimateEnterExitPartialContent() },
        MySampleData("androidx.compose.animation.samples.AnimateContent", "${samplesDir}/stolen/animation-samples/AnimationModifierSample.kt") { androidx.compose.animation.samples.AnimateContent() },
        MySampleData("androidx.compose.animation.samples.CrossfadeSample", "${samplesDir}/stolen/animation-samples/CrossfadeSample.kt") { androidx.compose.animation.samples.CrossfadeSample() },
        MySampleData("androidx.compose.animation.samples.GestureAnimationSample", "${samplesDir}/stolen/animation-samples/TransitionSamples.kt") { androidx.compose.animation.samples.GestureAnimationSample() },
        MySampleData("androidx.compose.animation.samples.InfiniteTransitionSample", "${samplesDir}/stolen/animation-samples/TransitionSamples.kt") { androidx.compose.animation.samples.InfiniteTransitionSample() },
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
//endregion Generated Playgrounds from PlaygroundsTemplate

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
