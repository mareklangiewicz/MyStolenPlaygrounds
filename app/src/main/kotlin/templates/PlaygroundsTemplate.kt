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
        MySampleData("androidx.compose.ui.samples.AlignmentLineSample", "${srcKotlinDir}/stolen/ui-samples/AlignmentLineSample.kt") { androidx.compose.ui.samples.AlignmentLineSample() },
        MySampleData("androidx.compose.ui.samples.AlphaSample", "${srcKotlinDir}/stolen/ui-samples/AlphaSample.kt") { androidx.compose.ui.samples.AlphaSample() },
        MySampleData("androidx.compose.ui.samples.AndroidViewSample", "${srcKotlinDir}/stolen/ui-samples/AndroidViewSample.kt") { androidx.compose.ui.samples.AndroidViewSample() },
        MySampleData("androidx.compose.ui.samples.AndroidDrawableInDrawScopeSample", "${srcKotlinDir}/stolen/ui-samples/AndroidViewSample.kt") { androidx.compose.ui.samples.AndroidDrawableInDrawScopeSample() },
        MySampleData("androidx.compose.ui.samples.BlurSample", "${srcKotlinDir}/stolen/ui-samples/BlurSample.kt") { androidx.compose.ui.samples.BlurSample() },
        MySampleData("androidx.compose.ui.samples.ImageBlurSample", "${srcKotlinDir}/stolen/ui-samples/BlurSample.kt") { androidx.compose.ui.samples.ImageBlurSample() },
        MySampleData("androidx.compose.ui.samples.DialogSample", "${srcKotlinDir}/stolen/ui-samples/DialogSample.kt") { androidx.compose.ui.samples.DialogSample() },
        MySampleData("androidx.compose.ui.samples.DrawWithCacheModifierSample", "${srcKotlinDir}/stolen/ui-samples/DrawModifierSample.kt") { androidx.compose.ui.samples.DrawWithCacheModifierSample() },
        MySampleData("androidx.compose.ui.samples.DrawWithCacheModifierStateParameterSample", "${srcKotlinDir}/stolen/ui-samples/DrawModifierSample.kt") { androidx.compose.ui.samples.DrawWithCacheModifierStateParameterSample() },
        MySampleData("androidx.compose.ui.samples.DrawWithCacheContentSample", "${srcKotlinDir}/stolen/ui-samples/DrawModifierSample.kt") { androidx.compose.ui.samples.DrawWithCacheContentSample() },
        MySampleData("androidx.compose.ui.samples.FocusableSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.FocusableSample() },
        MySampleData("androidx.compose.ui.samples.FocusableSampleUsingLowerLevelFocusTarget", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.FocusableSampleUsingLowerLevelFocusTarget() },
        MySampleData("androidx.compose.ui.samples.CaptureFocusSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.CaptureFocusSample() },
        MySampleData("androidx.compose.ui.samples.RequestFocusSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.RequestFocusSample() },
        MySampleData("androidx.compose.ui.samples.ClearFocusSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.ClearFocusSample() },
        MySampleData("androidx.compose.ui.samples.MoveFocusSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.MoveFocusSample() },
        MySampleData("androidx.compose.ui.samples.CreateFocusRequesterRefsSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.CreateFocusRequesterRefsSample() },
        MySampleData("androidx.compose.ui.samples.CustomFocusOrderSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.CustomFocusOrderSample() },
        MySampleData("androidx.compose.ui.samples.FocusPropertiesSample", "${srcKotlinDir}/stolen/ui-samples/FocusSamples.kt") { androidx.compose.ui.samples.FocusPropertiesSample() },
        MySampleData("androidx.compose.ui.samples.InspectableModifierSample", "${srcKotlinDir}/stolen/ui-samples/InspectableModifierSample.kt") { androidx.compose.ui.samples.InspectableModifierSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventTypeSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventTypeSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsAltPressedSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsAltPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsCtrlPressedSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsCtrlPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsMetaPressedSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsMetaPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsShiftPressedSample", "${srcKotlinDir}/stolen/ui-samples/KeyInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsShiftPressedSample() },
        MySampleData("androidx.compose.ui.samples.ChangeOpacity", "${srcKotlinDir}/stolen/ui-samples/LayerModifierSamples.kt") { androidx.compose.ui.samples.ChangeOpacity() },
        MySampleData("androidx.compose.ui.samples.AnimateFadeIn", "${srcKotlinDir}/stolen/ui-samples/LayerModifierSamples.kt") { androidx.compose.ui.samples.AnimateFadeIn() },
        MySampleData("androidx.compose.ui.samples.LayoutModifierSample", "${srcKotlinDir}/stolen/ui-samples/LayoutSample.kt") { androidx.compose.ui.samples.LayoutModifierSample() },
        MySampleData("androidx.compose.ui.samples.ConvenienceLayoutModifierSample", "${srcKotlinDir}/stolen/ui-samples/LayoutSample.kt") { androidx.compose.ui.samples.ConvenienceLayoutModifierSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalParentChildCommunicationWithinLayoutNodeSample", "${srcKotlinDir}/stolen/ui-samples/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalParentChildCommunicationWithinLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalChildParentCommunicationWithinLayoutNodeSample", "${srcKotlinDir}/stolen/ui-samples/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalChildParentCommunicationWithinLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalParentChildCommunicationInterLayoutNodeSample", "${srcKotlinDir}/stolen/ui-samples/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalParentChildCommunicationInterLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierLocalChildParentCommunicationInterLayoutNodeSample", "${srcKotlinDir}/stolen/ui-samples/ModifierLocalSamples.kt") { androidx.compose.ui.samples.ModifierLocalChildParentCommunicationInterLayoutNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierUsageSample", "${srcKotlinDir}/stolen/ui-samples/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierUsageSample() },
        MySampleData("androidx.compose.ui.samples.ModifierFactorySample", "${srcKotlinDir}/stolen/ui-samples/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierFactorySample() },
        MySampleData("androidx.compose.ui.samples.ModifierParameterSample", "${srcKotlinDir}/stolen/ui-samples/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierParameterSample() },
        MySampleData("androidx.compose.ui.samples.SubcomponentModifierSample", "${srcKotlinDir}/stolen/ui-samples/ModifierSamples.kt") { androidx.compose.ui.samples.SubcomponentModifierSample() },
        MySampleData("androidx.compose.ui.samples.NestedScrollConnectionSample", "${srcKotlinDir}/stolen/ui-samples/NestedScrollSamples.kt") { androidx.compose.ui.samples.NestedScrollConnectionSample() },
        MySampleData("androidx.compose.ui.samples.NestedScrollDispatcherSample", "${srcKotlinDir}/stolen/ui-samples/NestedScrollSamples.kt") { androidx.compose.ui.samples.NestedScrollDispatcherSample() },
        MySampleData("androidx.compose.ui.samples.OnGloballyPositioned", "${srcKotlinDir}/stolen/ui-samples/OnGloballyPositionedSamples.kt") { androidx.compose.ui.samples.OnGloballyPositioned() },
        MySampleData("androidx.compose.ui.samples.OnPlaced", "${srcKotlinDir}/stolen/ui-samples/OnPlacedSamples.kt") { androidx.compose.ui.samples.OnPlaced() },
        MySampleData("androidx.compose.ui.samples.PainterModifierSample", "${srcKotlinDir}/stolen/ui-samples/PainterSample.kt") { androidx.compose.ui.samples.PainterModifierSample() },
        MySampleData("androidx.compose.ui.samples.PainterResourceSample", "${srcKotlinDir}/stolen/ui-samples/PainterSample.kt") { androidx.compose.ui.samples.PainterResourceSample() },
        MySampleData("androidx.compose.ui.samples.PointerIconSample", "${srcKotlinDir}/stolen/ui-samples/PointerIconSample.kt") { androidx.compose.ui.samples.PointerIconSample() },
        MySampleData("androidx.compose.ui.samples.PopupSample", "${srcKotlinDir}/stolen/ui-samples/PopupSample.kt") { androidx.compose.ui.samples.PopupSample() },
        MySampleData("androidx.compose.ui.samples.RotateSample", "${srcKotlinDir}/stolen/ui-samples/RotateSample.kt") { androidx.compose.ui.samples.RotateSample() },
        MySampleData("androidx.compose.ui.samples.ScaleUniformSample", "${srcKotlinDir}/stolen/ui-samples/ScaleSample.kt") { androidx.compose.ui.samples.ScaleUniformSample() },
        MySampleData("androidx.compose.ui.samples.ScaleNonUniformSample", "${srcKotlinDir}/stolen/ui-samples/ScaleSample.kt") { androidx.compose.ui.samples.ScaleNonUniformSample() },
        MySampleData("androidx.compose.ui.samples.ShadowSample", "${srcKotlinDir}/stolen/ui-samples/ShadowSample.kt") { androidx.compose.ui.samples.ShadowSample() },
        MySampleData("androidx.compose.ui.samples.SoftwareKeyboardControllerSample", "${srcKotlinDir}/stolen/ui-samples/SoftwareKeyboardControllerSample.kt") { androidx.compose.ui.samples.SoftwareKeyboardControllerSample() },
        MySampleData("androidx.compose.ui.samples.ZIndexModifierSample", "${srcKotlinDir}/stolen/ui-samples/ZIndexModifierSample.kt") { androidx.compose.ui.samples.ZIndexModifierSample() },
        MySampleData("androidx.compose.ui.graphics.samples.GradientBrushSample", "${srcKotlinDir}/stolen/ui-graphics-samples/BrushSamples.kt") { androidx.compose.ui.graphics.samples.GradientBrushSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeSample", "${srcKotlinDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeBatchedTransformSample", "${srcKotlinDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeBatchedTransformSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeOvalBrushSample", "${srcKotlinDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeOvalBrushSample() },
        MySampleData("androidx.compose.ui.graphics.samples.DrawScopeOvalColorSample", "${srcKotlinDir}/stolen/ui-graphics-samples/DrawScopeSample.kt") { androidx.compose.ui.graphics.samples.DrawScopeOvalColorSample() },
        MySampleData("androidx.compose.ui.graphics.samples.StampedPathEffectSample", "${srcKotlinDir}/stolen/ui-graphics-samples/PathEffectSample.kt") { androidx.compose.ui.graphics.samples.StampedPathEffectSample() },
        MySampleData("androidx.compose.animation.core.samples.AnimatableAnimateToGenericsType", "${srcKotlinDir}/stolen/animation-core-samples/AnimatableSamples.kt") { androidx.compose.animation.core.samples.AnimatableAnimateToGenericsType() },
        MySampleData("androidx.compose.animation.core.samples.AlphaAnimationSample", "${srcKotlinDir}/stolen/animation-core-samples/AnimatedValueSamples.kt") { androidx.compose.animation.core.samples.AlphaAnimationSample() },
        MySampleData("androidx.compose.animation.core.samples.ArbitraryValueTypeTransitionSample", "${srcKotlinDir}/stolen/animation-core-samples/AnimatedValueSamples.kt") { androidx.compose.animation.core.samples.ArbitraryValueTypeTransitionSample() },
        MySampleData("androidx.compose.animation.core.samples.DpAnimationSample", "${srcKotlinDir}/stolen/animation-core-samples/AnimatedValueSamples.kt") { androidx.compose.animation.core.samples.DpAnimationSample() },
        MySampleData("androidx.compose.animation.core.samples.InfiniteProgressIndicator", "${srcKotlinDir}/stolen/animation-core-samples/AnimationSpecSamples.kt") { androidx.compose.animation.core.samples.InfiniteProgressIndicator() },
        MySampleData("androidx.compose.animation.core.samples.InfiniteTransitionSample", "${srcKotlinDir}/stolen/animation-core-samples/InfiniteTransitionSamples.kt") { androidx.compose.animation.core.samples.InfiniteTransitionSample() },
        MySampleData("androidx.compose.animation.core.samples.InfiniteTransitionAnimateValueSample", "${srcKotlinDir}/stolen/animation-core-samples/InfiniteTransitionSamples.kt") { androidx.compose.animation.core.samples.InfiniteTransitionAnimateValueSample() },
        MySampleData("androidx.compose.animation.core.samples.GestureAnimationSample", "${srcKotlinDir}/stolen/animation-core-samples/TransitionSamples.kt") { androidx.compose.animation.core.samples.GestureAnimationSample() },
        MySampleData("androidx.compose.animation.core.samples.AnimateFloatSample", "${srcKotlinDir}/stolen/animation-core-samples/TransitionSamples.kt") { androidx.compose.animation.core.samples.AnimateFloatSample() },
        MySampleData("androidx.compose.animation.core.samples.DoubleTapToLikeSample", "${srcKotlinDir}/stolen/animation-core-samples/TransitionSamples.kt") { androidx.compose.animation.core.samples.DoubleTapToLikeSample() },
        MySampleData("androidx.compose.animation.samples.ColorAnimationSample", "${srcKotlinDir}/stolen/animation-samples/AnimatedValueSamples.kt") { androidx.compose.animation.samples.ColorAnimationSample() },
        MySampleData("androidx.compose.animation.samples.HorizontalTransitionSample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.HorizontalTransitionSample() },
        MySampleData("androidx.compose.animation.samples.SlideTransition", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.SlideTransition() },
        MySampleData("androidx.compose.animation.samples.FadeTransition", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.FadeTransition() },
        MySampleData("androidx.compose.animation.samples.FullyLoadedTransition", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.FullyLoadedTransition() },
        MySampleData("androidx.compose.animation.samples.AnimatedVisibilityWithBooleanVisibleParamNoReceiver", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AnimatedVisibilityWithBooleanVisibleParamNoReceiver() },
        MySampleData("androidx.compose.animation.samples.SlideInOutSample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.SlideInOutSample() },
        MySampleData("androidx.compose.animation.samples.ExpandShrinkVerticallySample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.ExpandShrinkVerticallySample() },
        MySampleData("androidx.compose.animation.samples.ExpandInShrinkOutSample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.ExpandInShrinkOutSample() },
        MySampleData("androidx.compose.animation.samples.ColumnAnimatedVisibilitySample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.ColumnAnimatedVisibilitySample() },
        MySampleData("androidx.compose.animation.samples.AVScopeAnimateEnterExit", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AVScopeAnimateEnterExit() },
        MySampleData("androidx.compose.animation.samples.AnimatedVisibilityLazyColumnSample", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AnimatedVisibilityLazyColumnSample() },
        MySampleData("androidx.compose.animation.samples.AVColumnScopeWithMutableTransitionState", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AVColumnScopeWithMutableTransitionState() },
        MySampleData("androidx.compose.animation.samples.AnimateEnterExitPartialContent", "${srcKotlinDir}/stolen/animation-samples/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AnimateEnterExitPartialContent() },
        MySampleData("androidx.compose.animation.samples.AnimateContent", "${srcKotlinDir}/stolen/animation-samples/AnimationModifierSample.kt") { androidx.compose.animation.samples.AnimateContent() },
        MySampleData("androidx.compose.animation.samples.CrossfadeSample", "${srcKotlinDir}/stolen/animation-samples/CrossfadeSample.kt") { androidx.compose.animation.samples.CrossfadeSample() },
        MySampleData("androidx.compose.animation.samples.GestureAnimationSample", "${srcKotlinDir}/stolen/animation-samples/TransitionSamples.kt") { androidx.compose.animation.samples.GestureAnimationSample() },
        MySampleData("androidx.compose.animation.samples.InfiniteTransitionSample", "${srcKotlinDir}/stolen/animation-samples/TransitionSamples.kt") { androidx.compose.animation.samples.InfiniteTransitionSample() },
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
