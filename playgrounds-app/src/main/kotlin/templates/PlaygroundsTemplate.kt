@file:Suppress("FunctionName", "PackageDirectoryMismatch")
@file:OptIn(ExperimentalComposeUiApi::class)

package pl.mareklangiewicz.playgrounds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class MySampleData(val title: String, val path: String?, val code: @Composable () -> Unit)

@Composable
fun PlaygroundsTemplate() {

    val samplesDir = "/home/marek/code/kotlin/MyStolenPlaygrounds/lib-ui-samples/src/main/kotlin"

    val samples = listOf(
        MySampleData("Some Sample 0", "${samplesDir}/blabla.kt") { Text("Some Sample 0") }, // REPLACE
        MySampleData("Some Sample 1", "${samplesDir}/blabla.kt") { Text("Some Sample 1") }, // REMOVE
        MySampleData("Some Sample 2", "${samplesDir}/blabla.kt") { Text("Some Sample 2") }, // REMOVE
        MySampleData("Some Sample 3", "${samplesDir}/blabla.kt") { Text("Some Sample 3") } // REMOVE
    )

    var selectedSample by remember { mutableStateOf(samples[0]) }

    Row {
        LazyVerticalGrid(columns = GridCells.Adaptive(164.dp), modifier = Modifier.weight(.5f)) {
            item { MySimpleAssets("version-details") }
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

// region Generated Playgrounds from PlaygroundsTemplate
@Composable
fun Playgrounds() {

    val samplesDir = "/home/marek/code/kotlin/MyStolenPlaygrounds/lib-ui-samples/src/main/kotlin"

    val samples = listOf(
        MySampleData("androidx.compose.animation.core.samples.GestureAnimationSample", "${samplesDir}/stolen/samples-animation-core/TransitionSamples.kt") { androidx.compose.animation.core.samples.GestureAnimationSample() },
        MySampleData("androidx.compose.animation.core.samples.AnimateFloatSample", "${samplesDir}/stolen/samples-animation-core/TransitionSamples.kt") { androidx.compose.animation.core.samples.AnimateFloatSample() },
        MySampleData("androidx.compose.animation.core.samples.DoubleTapToLikeSample", "${samplesDir}/stolen/samples-animation-core/TransitionSamples.kt") { androidx.compose.animation.core.samples.DoubleTapToLikeSample() },
        MySampleData("androidx.compose.animation.core.samples.InfiniteProgressIndicator", "${samplesDir}/stolen/samples-animation-core/AnimationSpecSamples.kt") { androidx.compose.animation.core.samples.InfiniteProgressIndicator() },
        MySampleData("androidx.compose.animation.core.samples.AnimatableAnimateToGenericsType", "${samplesDir}/stolen/samples-animation-core/AnimatableSamples.kt") { androidx.compose.animation.core.samples.AnimatableAnimateToGenericsType() },
        MySampleData("androidx.compose.animation.core.samples.AlphaAnimationSample", "${samplesDir}/stolen/samples-animation-core/AnimatedValueSamples.kt") { androidx.compose.animation.core.samples.AlphaAnimationSample() },
        MySampleData("androidx.compose.animation.core.samples.ArbitraryValueTypeTransitionSample", "${samplesDir}/stolen/samples-animation-core/AnimatedValueSamples.kt") { androidx.compose.animation.core.samples.ArbitraryValueTypeTransitionSample() },
        MySampleData("androidx.compose.animation.core.samples.DpAnimationSample", "${samplesDir}/stolen/samples-animation-core/AnimatedValueSamples.kt") { androidx.compose.animation.core.samples.DpAnimationSample() },
        MySampleData("androidx.compose.animation.core.samples.InfiniteTransitionSample", "${samplesDir}/stolen/samples-animation-core/InfiniteTransitionSamples.kt") { androidx.compose.animation.core.samples.InfiniteTransitionSample() },
        MySampleData("androidx.compose.animation.core.samples.InfiniteTransitionAnimateValueSample", "${samplesDir}/stolen/samples-animation-core/InfiniteTransitionSamples.kt") { androidx.compose.animation.core.samples.InfiniteTransitionAnimateValueSample() },
        MySampleData("androidx.compose.ui.samples.SoftwareKeyboardControllerSample", "${samplesDir}/stolen/samples-ui/SoftwareKeyboardControllerSample.kt") { androidx.compose.ui.samples.SoftwareKeyboardControllerSample() },
        MySampleData("androidx.compose.ui.samples.AndroidViewSample", "${samplesDir}/stolen/samples-ui/AndroidViewSample.kt") { androidx.compose.ui.samples.AndroidViewSample() },
        MySampleData("androidx.compose.ui.samples.AndroidDrawableInDrawScopeSample", "${samplesDir}/stolen/samples-ui/AndroidViewSample.kt") { androidx.compose.ui.samples.AndroidDrawableInDrawScopeSample() },
        MySampleData("androidx.compose.ui.samples.LayoutModifierSample", "${samplesDir}/stolen/samples-ui/LayoutSample.kt") { androidx.compose.ui.samples.LayoutModifierSample() },
        MySampleData("androidx.compose.ui.samples.ConvenienceLayoutModifierSample", "${samplesDir}/stolen/samples-ui/LayoutSample.kt") { androidx.compose.ui.samples.ConvenienceLayoutModifierSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalParentChildCommunicationWithinLayoutNodeSample", "${samplesDir}/stolen/samples-ui/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalParentChildCommunicationWithinLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalChildParentCommunicationWithinLayoutNodeSample", "${samplesDir}/stolen/samples-ui/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalChildParentCommunicationWithinLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalParentChildCommunicationInterLayoutNodeSample", "${samplesDir}/stolen/samples-ui/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalParentChildCommunicationInterLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalChildParentCommunicationInterLayoutNodeSample", "${samplesDir}/stolen/samples-ui/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalChildParentCommunicationInterLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ScaleUniformSample", "${samplesDir}/stolen/samples-ui/ScaleSample.kt") { androidx.compose.ui.samples.ScaleUniformSample() },
        MySampleData("androidx.compose.ui.samples.ScaleNonUniformSample", "${samplesDir}/stolen/samples-ui/ScaleSample.kt") { androidx.compose.ui.samples.ScaleNonUniformSample() },
        MySampleData("androidx.compose.ui.samples.DialogSample", "${samplesDir}/stolen/samples-ui/DialogSample.kt") { androidx.compose.ui.samples.DialogSample() },
        MySampleData("androidx.compose.ui.samples.DrawWithCacheModifierSample", "${samplesDir}/stolen/samples-ui/DrawModifierSample.kt") { androidx.compose.ui.samples.DrawWithCacheModifierSample() },
        MySampleData("androidx.compose.ui.samples.DrawWithCacheModifierStateParameterSample", "${samplesDir}/stolen/samples-ui/DrawModifierSample.kt") { androidx.compose.ui.samples.DrawWithCacheModifierStateParameterSample() },
        MySampleData("androidx.compose.ui.samples.DrawWithCacheContentSample", "${samplesDir}/stolen/samples-ui/DrawModifierSample.kt") { androidx.compose.ui.samples.DrawWithCacheContentSample() },
        MySampleData("androidx.compose.ui.samples.BlurSample", "${samplesDir}/stolen/samples-ui/BlurSample.kt") { androidx.compose.ui.samples.BlurSample() },
        MySampleData("androidx.compose.ui.samples.ImageBlurSample", "${samplesDir}/stolen/samples-ui/BlurSample.kt") { androidx.compose.ui.samples.ImageBlurSample() },
        MySampleData("androidx.compose.ui.samples.OnGloballyPositioned", "${samplesDir}/stolen/samples-ui/OnGloballyPositionedSamples.kt") { androidx.compose.ui.samples.OnGloballyPositioned() },
        MySampleData("androidx.compose.ui.samples.PainterModifierSample", "${samplesDir}/stolen/samples-ui/PainterSample.kt") { androidx.compose.ui.samples.PainterModifierSample() },
        MySampleData("androidx.compose.ui.samples.PainterResourceSample", "${samplesDir}/stolen/samples-ui/PainterSample.kt") { androidx.compose.ui.samples.PainterResourceSample() },
        MySampleData("androidx.compose.ui.samples.ChangeOpacity", "${samplesDir}/stolen/samples-ui/LayerModifierSamples.kt") { androidx.compose.ui.samples.ChangeOpacity() },
        MySampleData("androidx.compose.ui.samples.AnimateFadeIn", "${samplesDir}/stolen/samples-ui/LayerModifierSamples.kt") { androidx.compose.ui.samples.AnimateFadeIn() },
        MySampleData("androidx.compose.ui.samples.InspectableModifierSample", "${samplesDir}/stolen/samples-ui/InspectableModifierSample.kt") { androidx.compose.ui.samples.InspectableModifierSample() },
        MySampleData("androidx.compose.ui.samples.PopupSample", "${samplesDir}/stolen/samples-ui/PopupSample.kt") { androidx.compose.ui.samples.PopupSample() },
        MySampleData("androidx.compose.ui.samples.AlignmentLineSample", "${samplesDir}/stolen/samples-ui/AlignmentLineSample.kt") { androidx.compose.ui.samples.AlignmentLineSample() },
        MySampleData("androidx.compose.ui.samples.FocusableSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.FocusableSample() },
        MySampleData("androidx.compose.ui.samples.FocusableSampleUsingLowerLevelFocusTarget", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.FocusableSampleUsingLowerLevelFocusTarget() },
        MySampleData("androidx.compose.ui.samples.CaptureFocusSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.CaptureFocusSample() },
        MySampleData("androidx.compose.ui.samples.RequestFocusSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.RequestFocusSample() },
        MySampleData("androidx.compose.ui.samples.ClearFocusSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.ClearFocusSample() },
        MySampleData("androidx.compose.ui.samples.MoveFocusSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.MoveFocusSample() },
        MySampleData("androidx.compose.ui.samples.CreateFocusRequesterRefsSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.CreateFocusRequesterRefsSample() },
        MySampleData("androidx.compose.ui.samples.CustomFocusOrderSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.CustomFocusOrderSample() },
        MySampleData("androidx.compose.ui.samples.FocusPropertiesSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.FocusPropertiesSample() },
        MySampleData("androidx.compose.ui.samples.ShadowSample", "${samplesDir}/stolen/samples-ui/ShadowSample.kt") { androidx.compose.ui.samples.ShadowSample() },
        MySampleData("androidx.compose.ui.samples.AlphaSample", "${samplesDir}/stolen/samples-ui/AlphaSample.kt") { androidx.compose.ui.samples.AlphaSample() },
        MySampleData("androidx.compose.ui.samples.RotateSample", "${samplesDir}/stolen/samples-ui/RotateSample.kt") { androidx.compose.ui.samples.RotateSample() },
        MySampleData("androidx.compose.ui.samples.ModifierUsageSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierUsageSample() },
        MySampleData("androidx.compose.ui.samples.ModifierFactorySample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierFactorySample() },
        MySampleData("androidx.compose.ui.samples.ModifierParameterSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierParameterSample() },
        MySampleData("androidx.compose.ui.samples.SubcomponentModifierSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.SubcomponentModifierSample() },
        MySampleData("androidx.compose.ui.samples.OnPlaced", "${samplesDir}/stolen/samples-ui/OnPlacedSamples.kt") { androidx.compose.ui.samples.OnPlaced() },
        MySampleData("androidx.compose.ui.samples.PointerIconSample", "${samplesDir}/stolen/samples-ui/PointerIconSample.kt") { androidx.compose.ui.samples.PointerIconSample() },
        MySampleData("androidx.compose.ui.samples.ViewInComposeNestedScrollInteropSample", "${samplesDir}/stolen/samples-ui/NestedScrollInteropSamples.kt") { androidx.compose.ui.samples.ViewInComposeNestedScrollInteropSample() },
        MySampleData("androidx.compose.ui.samples.NestedScrollConnectionSample", "${samplesDir}/stolen/samples-ui/NestedScrollSamples.kt") { androidx.compose.ui.samples.NestedScrollConnectionSample() },
        MySampleData("androidx.compose.ui.samples.NestedScrollDispatcherSample", "${samplesDir}/stolen/samples-ui/NestedScrollSamples.kt") { androidx.compose.ui.samples.NestedScrollDispatcherSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventTypeSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventTypeSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsAltPressedSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsAltPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsCtrlPressedSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsCtrlPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsMetaPressedSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsMetaPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsShiftPressedSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsShiftPressedSample() },
        MySampleData("androidx.compose.ui.samples.RotaryEventSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.RotaryEventSample() },
        MySampleData("androidx.compose.ui.samples.PreRotaryEventSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.PreRotaryEventSample() },
        MySampleData("androidx.compose.ui.samples.ZIndexModifierSample", "${samplesDir}/stolen/samples-ui/ZIndexModifierSample.kt") { androidx.compose.ui.samples.ZIndexModifierSample() },
        MySampleData("androidx.compose.animation.samples.GestureAnimationSample", "${samplesDir}/stolen/samples-animation/TransitionSamples.kt") { androidx.compose.animation.samples.GestureAnimationSample() },
        MySampleData("androidx.compose.animation.samples.InfiniteTransitionSample", "${samplesDir}/stolen/samples-animation/TransitionSamples.kt") { androidx.compose.animation.samples.InfiniteTransitionSample() },
        MySampleData("androidx.compose.animation.samples.AnimateContent", "${samplesDir}/stolen/samples-animation/AnimationModifierSample.kt") { androidx.compose.animation.samples.AnimateContent() },
        MySampleData("androidx.compose.animation.samples.HorizontalTransitionSample", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.HorizontalTransitionSample() },
        MySampleData("androidx.compose.animation.samples.SlideTransition", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.SlideTransition() },
        MySampleData("androidx.compose.animation.samples.FadeTransition", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.FadeTransition() },
        MySampleData("androidx.compose.animation.samples.FullyLoadedTransition", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.FullyLoadedTransition() },
        MySampleData("androidx.compose.animation.samples.AnimatedVisibilityWithBooleanVisibleParamNoReceiver", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AnimatedVisibilityWithBooleanVisibleParamNoReceiver() },
        MySampleData("androidx.compose.animation.samples.SlideInOutSample", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.SlideInOutSample() },
        MySampleData("androidx.compose.animation.samples.ExpandShrinkVerticallySample", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.ExpandShrinkVerticallySample() },
        MySampleData("androidx.compose.animation.samples.ExpandInShrinkOutSample", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.ExpandInShrinkOutSample() },
        MySampleData("androidx.compose.animation.samples.ColumnAnimatedVisibilitySample", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.ColumnAnimatedVisibilitySample() },
        MySampleData("androidx.compose.animation.samples.AVScopeAnimateEnterExit", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AVScopeAnimateEnterExit() },
        MySampleData("androidx.compose.animation.samples.AnimatedVisibilityLazyColumnSample", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AnimatedVisibilityLazyColumnSample() },
        MySampleData("androidx.compose.animation.samples.AVColumnScopeWithMutableTransitionState", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AVColumnScopeWithMutableTransitionState() },
        MySampleData("androidx.compose.animation.samples.AnimateEnterExitPartialContent", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AnimateEnterExitPartialContent() },
        MySampleData("androidx.compose.animation.samples.CrossfadeSample", "${samplesDir}/stolen/samples-animation/CrossfadeSample.kt") { androidx.compose.animation.samples.CrossfadeSample() },
        MySampleData("androidx.compose.animation.samples.ColorAnimationSample", "${samplesDir}/stolen/samples-animation/AnimatedValueSamples.kt") { androidx.compose.animation.samples.ColorAnimationSample() },
        MySampleData("androidx.compose.ui.graphics.samples.GradientBrushSample", "${samplesDir}/stolen/samples-ui-graphics/BrushSamples.kt") { androidx.compose.ui.graphics.samples.GradientBrushSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeSample", "${samplesDir}/stolen/samples-ui-graphics/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeBatchedTransformSample", "${samplesDir}/stolen/samples-ui-graphics/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeBatchedTransformSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeOvalBrushSample", "${samplesDir}/stolen/samples-ui-graphics/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeOvalBrushSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeOvalColorSample", "${samplesDir}/stolen/samples-ui-graphics/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeOvalColorSample() },
        MySampleData("androidx.compose.ui.graphics.samples.StampedPathEffectSample", "${samplesDir}/stolen/samples-ui-graphics/PathEffectSample.kt") { androidx.compose.ui.graphics.samples.StampedPathEffectSample() },
    )

    var selectedSample by remember { mutableStateOf(samples[0]) }

    Row {
        LazyVerticalGrid(columns = GridCells.Adaptive(164.dp), modifier = Modifier.weight(.5f)) {
            item { MySimpleAssets("version-details") }
            for (sample in samples)
                MySampleItem(sample) { selectedSample = it; println(it.path) }
        }
        MyFancyFrame(Modifier.weight(1f), selectedSample.title) {
            selectedSample.code()
        }
    }
}
// endregion Generated Playgrounds from PlaygroundsTemplate

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
