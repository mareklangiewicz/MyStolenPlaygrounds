@file:Suppress("FunctionName", "PackageDirectoryMismatch")
@file:OptIn(ExperimentalComposeUiApi::class)

package pl.mareklangiewicz.playgrounds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*

data class MySampleData(val title: String, val path: String?, val code: @Composable () -> Unit)

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

@Composable
fun PlaygroundsTemplate() {

    val samplesDir = "/home/marek/code/kotlin/MyStolenPlaygrounds/lib-ui-samples/src/main/kotlin"

    val samples = listOf(
        MySampleData("Version details", null) { MySimpleAssets("version-details") },
        MySampleData("Some Sample 1", "${samplesDir}/blabla.kt") { Text("Some Sample 1") }, // REPLACE
        MySampleData("Some Sample 2", "${samplesDir}/blabla.kt") { Text("Some Sample 2") }, // REMOVE
        MySampleData("Some Sample 3", "${samplesDir}/blabla.kt") { Text("Some Sample 3") }, // REMOVE
    )

    var selectedSample by remember { mutableStateOf(samples[0]) }

    Row {
        LazyVerticalGrid(columns = GridCells.Adaptive(164.dp), modifier = Modifier.weight(.5f)) {
            for (sample in samples) MySampleItem(sample) { selectedSample = it; println(it.path) }
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

internal val LocalFancyItemShowContent = staticCompositionLocalOf { false }

fun LazyGridScope.MyFancyItem(title: String, onClick: () -> Unit = {}, content: @Composable () -> Unit) {
    item {
        val showContent = LocalFancyItemShowContent.current
        val m = if (showContent) Modifier.size(164.dp, 256.dp) else Modifier
        MyFancyFrame(m, title = title, onClick = onClick) {
            if (showContent) content() else Text("Show", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }
}

// region Generated Playgrounds from PlaygroundsTemplate
@Composable
fun Playgrounds() {

    val samplesDir = "/home/marek/code/kotlin/MyStolenPlaygrounds/lib-ui-samples/src/main/kotlin"

    val samples = listOf(
        MySampleData("Version details", null) { MySimpleAssets("version-details") },
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
        MySampleData("androidx.compose.ui.samples.InspectorInfoInComposedModifierSample", "${samplesDir}/stolen/samples-ui/InspectorInfoInComposedModifierSamples.kt") { androidx.compose.ui.samples.InspectorInfoInComposedModifierSample() },
        MySampleData("androidx.compose.ui.samples.InspectorInfoInComposedModifierWithArgumentsSample", "${samplesDir}/stolen/samples-ui/InspectorInfoInComposedModifierSamples.kt") { androidx.compose.ui.samples.InspectorInfoInComposedModifierWithArgumentsSample() },
        MySampleData("androidx.compose.ui.samples.LayoutModifierSample", "${samplesDir}/stolen/samples-ui/LayoutSample.kt") { androidx.compose.ui.samples.LayoutModifierSample() },
        MySampleData("androidx.compose.ui.samples.LayoutModifierNodeSample", "${samplesDir}/stolen/samples-ui/LayoutSample.kt") { androidx.compose.ui.samples.LayoutModifierNodeSample() },
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
        MySampleData("androidx.compose.ui.samples.DrawModifierNodeSample", "${samplesDir}/stolen/samples-ui/DrawModifierSample.kt") { androidx.compose.ui.samples.DrawModifierNodeSample() },
        MySampleData("androidx.compose.ui.samples.BlurSample", "${samplesDir}/stolen/samples-ui/BlurSample.kt") { androidx.compose.ui.samples.BlurSample() },
        MySampleData("androidx.compose.ui.samples.ImageBlurSample", "${samplesDir}/stolen/samples-ui/BlurSample.kt") { androidx.compose.ui.samples.ImageBlurSample() },
        MySampleData("androidx.compose.ui.samples.OnGloballyPositioned", "${samplesDir}/stolen/samples-ui/OnGloballyPositionedSamples.kt") { androidx.compose.ui.samples.OnGloballyPositioned() },
        MySampleData("androidx.compose.ui.samples.PainterModifierSample", "${samplesDir}/stolen/samples-ui/PainterSample.kt") { androidx.compose.ui.samples.PainterModifierSample() },
        MySampleData("androidx.compose.ui.samples.PainterResourceSample", "${samplesDir}/stolen/samples-ui/PainterSample.kt") { androidx.compose.ui.samples.PainterResourceSample() },
        MySampleData("androidx.compose.ui.samples.ChangeOpacity", "${samplesDir}/stolen/samples-ui/LayerModifierSamples.kt") { androidx.compose.ui.samples.ChangeOpacity() },
        MySampleData("androidx.compose.ui.samples.AnimateFadeIn", "${samplesDir}/stolen/samples-ui/LayerModifierSamples.kt") { androidx.compose.ui.samples.AnimateFadeIn() },
        MySampleData("androidx.compose.ui.samples.CompositingStrategyModulateAlpha", "${samplesDir}/stolen/samples-ui/LayerModifierSamples.kt") { androidx.compose.ui.samples.CompositingStrategyModulateAlpha() },
        MySampleData("androidx.compose.ui.samples.InspectableModifierSample", "${samplesDir}/stolen/samples-ui/InspectableModifierSample.kt") { androidx.compose.ui.samples.InspectableModifierSample() },
        MySampleData("androidx.compose.ui.samples.LookaheadLayoutSample", "${samplesDir}/stolen/samples-ui/LookaheadLayoutSample.kt") { androidx.compose.ui.samples.LookaheadLayoutSample() },
        MySampleData("androidx.compose.ui.samples.LookaheadLayoutCoordinatesSample", "${samplesDir}/stolen/samples-ui/LookaheadLayoutSample.kt") { androidx.compose.ui.samples.LookaheadLayoutCoordinatesSample() },
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
        MySampleData("androidx.compose.ui.samples.CancelFocusMoveSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.CancelFocusMoveSample() },
        MySampleData("androidx.compose.ui.samples.CustomFocusEnterSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.CustomFocusEnterSample() },
        MySampleData("androidx.compose.ui.samples.CustomFocusExitSample", "${samplesDir}/stolen/samples-ui/FocusSamples.kt") { androidx.compose.ui.samples.CustomFocusExitSample() },
        MySampleData("androidx.compose.ui.samples.ShadowSample", "${samplesDir}/stolen/samples-ui/ShadowSample.kt") { androidx.compose.ui.samples.ShadowSample() },
        MySampleData("androidx.compose.ui.samples.AlphaSample", "${samplesDir}/stolen/samples-ui/AlphaSample.kt") { androidx.compose.ui.samples.AlphaSample() },
        MySampleData("androidx.compose.ui.samples.PlacementScopeCoordinatesSample", "${samplesDir}/stolen/samples-ui/PlacementScopeCoordinatesSample.kt") { androidx.compose.ui.samples.PlacementScopeCoordinatesSample() },
        MySampleData("androidx.compose.ui.samples.RotateSample", "${samplesDir}/stolen/samples-ui/RotateSample.kt") { androidx.compose.ui.samples.RotateSample() },
        MySampleData("androidx.compose.ui.samples.ModifierUsageSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierUsageSample() },
        MySampleData("androidx.compose.ui.samples.ModifierFactorySample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierFactorySample() },
        MySampleData("androidx.compose.ui.samples.ModifierParameterSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierParameterSample() },
        MySampleData("androidx.compose.ui.samples.SubcomponentModifierSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.SubcomponentModifierSample() },
        MySampleData("androidx.compose.ui.samples.DelegatedNodeSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.DelegatedNodeSample() },
        MySampleData("androidx.compose.ui.samples.ModifierElementOfSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.ModifierElementOfSample() },
        MySampleData("androidx.compose.ui.samples.SemanticsModifierNodeSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.SemanticsModifierNodeSample() },
        MySampleData("androidx.compose.ui.samples.PointerInputModifierNodeSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.PointerInputModifierNodeSample() },
        MySampleData("androidx.compose.ui.samples.LayoutAwareModifierNodeSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.LayoutAwareModifierNodeSample() },
        MySampleData("androidx.compose.ui.samples.GlobalPositionAwareModifierNodeSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.GlobalPositionAwareModifierNodeSample() },
        MySampleData("androidx.compose.ui.samples.JustReadingOrProvidingModifierLocalNodeSample", "${samplesDir}/stolen/samples-ui/ModifierSamples.kt") { androidx.compose.ui.samples.JustReadingOrProvidingModifierLocalNodeSample() },
        MySampleData("androidx.compose.ui.samples.OnPlaced", "${samplesDir}/stolen/samples-ui/OnPlacedSamples.kt") { androidx.compose.ui.samples.OnPlaced() },
        MySampleData("androidx.compose.ui.samples.PointerIconSample", "${samplesDir}/stolen/samples-ui/PointerIconSample.kt") { androidx.compose.ui.samples.PointerIconSample() },
        MySampleData("androidx.compose.ui.samples.ComposeInCooperatingViewNestedScrollInteropSample", "${samplesDir}/stolen/samples-ui/NestedScrollInteropSamples.kt") { androidx.compose.ui.samples.ComposeInCooperatingViewNestedScrollInteropSample() },
        MySampleData("androidx.compose.ui.samples.ViewInComposeNestedScrollInteropSample", "${samplesDir}/stolen/samples-ui/NestedScrollInteropSamples.kt") { androidx.compose.ui.samples.ViewInComposeNestedScrollInteropSample() },
        MySampleData("androidx.compose.ui.samples.NestedScrollConnectionSample", "${samplesDir}/stolen/samples-ui/NestedScrollSamples.kt") { androidx.compose.ui.samples.NestedScrollConnectionSample() },
        MySampleData("androidx.compose.ui.samples.NestedScrollDispatcherSample", "${samplesDir}/stolen/samples-ui/NestedScrollSamples.kt") { androidx.compose.ui.samples.NestedScrollDispatcherSample() },
        MySampleData("androidx.compose.ui.samples.ScrollableContainerSample", "${samplesDir}/stolen/samples-ui/ScrollableContainerSample.kt") { androidx.compose.ui.samples.ScrollableContainerSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventTypeSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventTypeSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsAltPressedSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsAltPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsCtrlPressedSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsCtrlPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsMetaPressedSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsMetaPressedSample() },
        MySampleData("androidx.compose.ui.samples.KeyEventIsShiftPressedSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.KeyEventIsShiftPressedSample() },
        MySampleData("androidx.compose.ui.samples.RotaryEventSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.RotaryEventSample() },
        MySampleData("androidx.compose.ui.samples.PreRotaryEventSample", "${samplesDir}/stolen/samples-ui/FocusAwareInputSamples.kt") { androidx.compose.ui.samples.PreRotaryEventSample() },
        MySampleData("androidx.compose.ui.samples.ZIndexModifierSample", "${samplesDir}/stolen/samples-ui/ZIndexModifierSample.kt") { androidx.compose.ui.samples.ZIndexModifierSample() },
        MySampleData("androidx.compose.foundation.samples.DrawBackgroundColor", "${samplesDir}/stolen/samples-foundation/DrawBackgroundSamples.kt") { androidx.compose.foundation.samples.DrawBackgroundColor() },
        MySampleData("androidx.compose.foundation.samples.DrawBackgroundShapedBrush", "${samplesDir}/stolen/samples-foundation/DrawBackgroundSamples.kt") { androidx.compose.foundation.samples.DrawBackgroundShapedBrush() },
        MySampleData("androidx.compose.foundation.samples.InlineTextContentSample", "${samplesDir}/stolen/samples-foundation/InlineTextContentSample.kt") { androidx.compose.foundation.samples.InlineTextContentSample() },
        MySampleData("androidx.compose.foundation.samples.CanvasSample", "${samplesDir}/stolen/samples-foundation/CanvasSamples.kt") { androidx.compose.foundation.samples.CanvasSample() },
        MySampleData("androidx.compose.foundation.samples.SnapFlingBehaviorSimpleSample", "${samplesDir}/stolen/samples-foundation/SnapFlingBehaviorSample.kt") { androidx.compose.foundation.samples.SnapFlingBehaviorSimpleSample() },
        MySampleData("androidx.compose.foundation.samples.SnapFlingBehaviorCustomizedSample", "${samplesDir}/stolen/samples-foundation/SnapFlingBehaviorSample.kt") { androidx.compose.foundation.samples.SnapFlingBehaviorCustomizedSample() },
        MySampleData("androidx.compose.foundation.samples.SelectableSample", "${samplesDir}/stolen/samples-foundation/SelectableSamples.kt") { androidx.compose.foundation.samples.SelectableSample() },
        MySampleData("androidx.compose.foundation.samples.ToggleableSample", "${samplesDir}/stolen/samples-foundation/ToggleableSamples.kt") { androidx.compose.foundation.samples.ToggleableSample() },
        MySampleData("androidx.compose.foundation.samples.TriStateToggleableSample", "${samplesDir}/stolen/samples-foundation/ToggleableSamples.kt") { androidx.compose.foundation.samples.TriStateToggleableSample() },
        MySampleData("androidx.compose.foundation.samples.SelectionSample", "${samplesDir}/stolen/samples-foundation/SelectionSample.kt") { androidx.compose.foundation.samples.SelectionSample() },
        MySampleData("androidx.compose.foundation.samples.DisableSelectionSample", "${samplesDir}/stolen/samples-foundation/SelectionSample.kt") { androidx.compose.foundation.samples.DisableSelectionSample() },
        MySampleData("androidx.compose.foundation.samples.DetectTransformGestures", "${samplesDir}/stolen/samples-foundation/TransformGestureSamples.kt") { androidx.compose.foundation.samples.DetectTransformGestures() },
        MySampleData("androidx.compose.foundation.samples.CalculateRotation", "${samplesDir}/stolen/samples-foundation/TransformGestureSamples.kt") { androidx.compose.foundation.samples.CalculateRotation() },
        MySampleData("androidx.compose.foundation.samples.CalculateZoom", "${samplesDir}/stolen/samples-foundation/TransformGestureSamples.kt") { androidx.compose.foundation.samples.CalculateZoom() },
        MySampleData("androidx.compose.foundation.samples.CalculatePan", "${samplesDir}/stolen/samples-foundation/TransformGestureSamples.kt") { androidx.compose.foundation.samples.CalculatePan() },
        MySampleData("androidx.compose.foundation.samples.CalculateCentroidSize", "${samplesDir}/stolen/samples-foundation/TransformGestureSamples.kt") { androidx.compose.foundation.samples.CalculateCentroidSize() },
        MySampleData("androidx.compose.foundation.samples.HoverableSample", "${samplesDir}/stolen/samples-foundation/HoverableSample.kt") { androidx.compose.foundation.samples.HoverableSample() },
        MySampleData("androidx.compose.foundation.samples.AwaitHorizontalDragOrCancellationSample", "${samplesDir}/stolen/samples-foundation/DragGestureDetectorSamples.kt") { androidx.compose.foundation.samples.AwaitHorizontalDragOrCancellationSample() },
        MySampleData("androidx.compose.foundation.samples.HorizontalDragSample", "${samplesDir}/stolen/samples-foundation/DragGestureDetectorSamples.kt") { androidx.compose.foundation.samples.HorizontalDragSample() },
        MySampleData("androidx.compose.foundation.samples.DetectHorizontalDragGesturesSample", "${samplesDir}/stolen/samples-foundation/DragGestureDetectorSamples.kt") { androidx.compose.foundation.samples.DetectHorizontalDragGesturesSample() },
        MySampleData("androidx.compose.foundation.samples.AwaitVerticalDragOrCancellationSample", "${samplesDir}/stolen/samples-foundation/DragGestureDetectorSamples.kt") { androidx.compose.foundation.samples.AwaitVerticalDragOrCancellationSample() },
        MySampleData("androidx.compose.foundation.samples.VerticalDragSample", "${samplesDir}/stolen/samples-foundation/DragGestureDetectorSamples.kt") { androidx.compose.foundation.samples.VerticalDragSample() },
        MySampleData("androidx.compose.foundation.samples.DetectVerticalDragGesturesSample", "${samplesDir}/stolen/samples-foundation/DragGestureDetectorSamples.kt") { androidx.compose.foundation.samples.DetectVerticalDragGesturesSample() },
        MySampleData("androidx.compose.foundation.samples.AwaitDragOrCancellationSample", "${samplesDir}/stolen/samples-foundation/DragGestureDetectorSamples.kt") { androidx.compose.foundation.samples.AwaitDragOrCancellationSample() },
        MySampleData("androidx.compose.foundation.samples.DragSample", "${samplesDir}/stolen/samples-foundation/DragGestureDetectorSamples.kt") { androidx.compose.foundation.samples.DragSample() },
        MySampleData("androidx.compose.foundation.samples.DetectDragGesturesSample", "${samplesDir}/stolen/samples-foundation/DragGestureDetectorSamples.kt") { androidx.compose.foundation.samples.DetectDragGesturesSample() },
        MySampleData("androidx.compose.foundation.samples.DetectDragWithLongPressGesturesSample", "${samplesDir}/stolen/samples-foundation/DragGestureDetectorSamples.kt") { androidx.compose.foundation.samples.DetectDragWithLongPressGesturesSample() },
        MySampleData("androidx.compose.foundation.samples.BorderSample", "${samplesDir}/stolen/samples-foundation/BorderSamples.kt") { androidx.compose.foundation.samples.BorderSample() },
        MySampleData("androidx.compose.foundation.samples.BorderSampleWithBrush", "${samplesDir}/stolen/samples-foundation/BorderSamples.kt") { androidx.compose.foundation.samples.BorderSampleWithBrush() },
        MySampleData("androidx.compose.foundation.samples.BorderSampleWithDataClass", "${samplesDir}/stolen/samples-foundation/BorderSamples.kt") { androidx.compose.foundation.samples.BorderSampleWithDataClass() },
        MySampleData("androidx.compose.foundation.samples.MagnifierSample", "${samplesDir}/stolen/samples-foundation/MagnifierSamples.kt") { androidx.compose.foundation.samples.MagnifierSample() },
        MySampleData("androidx.compose.foundation.samples.ScrollableSample", "${samplesDir}/stolen/samples-foundation/ScrollableSamples.kt") { androidx.compose.foundation.samples.ScrollableSample() },
        MySampleData("androidx.compose.foundation.samples.CanScrollSample", "${samplesDir}/stolen/samples-foundation/ScrollableSamples.kt") { androidx.compose.foundation.samples.CanScrollSample() },
        MySampleData("androidx.compose.foundation.samples.OverscrollSample", "${samplesDir}/stolen/samples-foundation/OverscrollSample.kt") { androidx.compose.foundation.samples.OverscrollSample() },
        MySampleData("androidx.compose.foundation.samples.ClickableSample", "${samplesDir}/stolen/samples-foundation/ClickableSamples.kt") { androidx.compose.foundation.samples.ClickableSample() },
        MySampleData("androidx.compose.foundation.samples.DarkThemeSample", "${samplesDir}/stolen/samples-foundation/DarkThemeSample.kt") { androidx.compose.foundation.samples.DarkThemeSample() },
        MySampleData("androidx.compose.foundation.samples.FocusableSample", "${samplesDir}/stolen/samples-foundation/FocusableSample.kt") { androidx.compose.foundation.samples.FocusableSample() },
        MySampleData("androidx.compose.foundation.samples.FocusGroupSample", "${samplesDir}/stolen/samples-foundation/FocusableSample.kt") { androidx.compose.foundation.samples.FocusGroupSample() },
        MySampleData("androidx.compose.foundation.samples.FocusableFocusGroupSample", "${samplesDir}/stolen/samples-foundation/FocusableSample.kt") { androidx.compose.foundation.samples.FocusableFocusGroupSample() },
        MySampleData("androidx.compose.foundation.samples.TransformableSample", "${samplesDir}/stolen/samples-foundation/TransformableSample.kt") { androidx.compose.foundation.samples.TransformableSample() },
        MySampleData("androidx.compose.foundation.samples.ImageSample", "${samplesDir}/stolen/samples-foundation/ImageSamples.kt") { androidx.compose.foundation.samples.ImageSample() },
        MySampleData("androidx.compose.foundation.samples.BitmapPainterSubsectionSample", "${samplesDir}/stolen/samples-foundation/ImageSamples.kt") { androidx.compose.foundation.samples.BitmapPainterSubsectionSample() },
        MySampleData("androidx.compose.foundation.samples.BitmapPainterSample", "${samplesDir}/stolen/samples-foundation/ImageSamples.kt") { androidx.compose.foundation.samples.BitmapPainterSample() },
        MySampleData("androidx.compose.foundation.samples.SimpleHorizontalPagerSample", "${samplesDir}/stolen/samples-foundation/PagerSamples.kt") { androidx.compose.foundation.samples.SimpleHorizontalPagerSample() },
        MySampleData("androidx.compose.foundation.samples.SimpleVerticalPagerSample", "${samplesDir}/stolen/samples-foundation/PagerSamples.kt") { androidx.compose.foundation.samples.SimpleVerticalPagerSample() },
        MySampleData("androidx.compose.foundation.samples.PagerWithStateSample", "${samplesDir}/stolen/samples-foundation/PagerSamples.kt") { androidx.compose.foundation.samples.PagerWithStateSample() },
        MySampleData("androidx.compose.foundation.samples.CustomPageSizeSample", "${samplesDir}/stolen/samples-foundation/PagerSamples.kt") { androidx.compose.foundation.samples.CustomPageSizeSample() },
        MySampleData("androidx.compose.foundation.samples.ObservingStateChangesInPagerStateSample", "${samplesDir}/stolen/samples-foundation/PagerSamples.kt") { androidx.compose.foundation.samples.ObservingStateChangesInPagerStateSample() },
        MySampleData("androidx.compose.foundation.samples.AnimateScrollPageSample", "${samplesDir}/stolen/samples-foundation/PagerSamples.kt") { androidx.compose.foundation.samples.AnimateScrollPageSample() },
        MySampleData("androidx.compose.foundation.samples.ScrollToPageSample", "${samplesDir}/stolen/samples-foundation/PagerSamples.kt") { androidx.compose.foundation.samples.ScrollToPageSample() },
        MySampleData("androidx.compose.foundation.samples.BringIntoViewSample", "${samplesDir}/stolen/samples-foundation/BringIntoViewSamples.kt") { androidx.compose.foundation.samples.BringIntoViewSample() },
        MySampleData("androidx.compose.foundation.samples.BringPartOfComposableIntoViewSample", "${samplesDir}/stolen/samples-foundation/BringIntoViewSamples.kt") { androidx.compose.foundation.samples.BringPartOfComposableIntoViewSample() },
        MySampleData("androidx.compose.foundation.samples.BringIntoViewResponderSample", "${samplesDir}/stolen/samples-foundation/BringIntoViewSamples.kt") { androidx.compose.foundation.samples.BringIntoViewResponderSample() },
        MySampleData("androidx.compose.foundation.samples.LazyVerticalGridSample", "${samplesDir}/stolen/samples-foundation/LazyGridSamples.kt") { androidx.compose.foundation.samples.LazyVerticalGridSample() },
        MySampleData("androidx.compose.foundation.samples.LazyVerticalGridSpanSample", "${samplesDir}/stolen/samples-foundation/LazyGridSamples.kt") { androidx.compose.foundation.samples.LazyVerticalGridSpanSample() },
        MySampleData("androidx.compose.foundation.samples.LazyHorizontalGridSample", "${samplesDir}/stolen/samples-foundation/LazyGridSamples.kt") { androidx.compose.foundation.samples.LazyHorizontalGridSample() },
        MySampleData("androidx.compose.foundation.samples.LazyHorizontalGridSpanSample", "${samplesDir}/stolen/samples-foundation/LazyGridSamples.kt") { androidx.compose.foundation.samples.LazyHorizontalGridSpanSample() },
        MySampleData("androidx.compose.foundation.samples.UsingGridScrollPositionForSideEffectSample", "${samplesDir}/stolen/samples-foundation/LazyGridSamples.kt") { androidx.compose.foundation.samples.UsingGridScrollPositionForSideEffectSample() },
        MySampleData("androidx.compose.foundation.samples.UsingGridScrollPositionInCompositionSample", "${samplesDir}/stolen/samples-foundation/LazyGridSamples.kt") { androidx.compose.foundation.samples.UsingGridScrollPositionInCompositionSample() },
        MySampleData("androidx.compose.foundation.samples.UsingGridLayoutInfoForSideEffectSample", "${samplesDir}/stolen/samples-foundation/LazyGridSamples.kt") { androidx.compose.foundation.samples.UsingGridLayoutInfoForSideEffectSample() },
        MySampleData("androidx.compose.foundation.samples.LazyColumnSample", "${samplesDir}/stolen/samples-foundation/LazyDslSamples.kt") { androidx.compose.foundation.samples.LazyColumnSample() },
        MySampleData("androidx.compose.foundation.samples.LazyRowSample", "${samplesDir}/stolen/samples-foundation/LazyDslSamples.kt") { androidx.compose.foundation.samples.LazyRowSample() },
        MySampleData("androidx.compose.foundation.samples.StickyHeaderSample", "${samplesDir}/stolen/samples-foundation/LazyDslSamples.kt") { androidx.compose.foundation.samples.StickyHeaderSample() },
        MySampleData("androidx.compose.foundation.samples.ItemPlacementAnimationSample", "${samplesDir}/stolen/samples-foundation/LazyDslSamples.kt") { androidx.compose.foundation.samples.ItemPlacementAnimationSample() },
        MySampleData("androidx.compose.foundation.samples.UsingListScrollPositionForSideEffectSample", "${samplesDir}/stolen/samples-foundation/LazyDslSamples.kt") { androidx.compose.foundation.samples.UsingListScrollPositionForSideEffectSample() },
        MySampleData("androidx.compose.foundation.samples.UsingListScrollPositionInCompositionSample", "${samplesDir}/stolen/samples-foundation/LazyDslSamples.kt") { androidx.compose.foundation.samples.UsingListScrollPositionInCompositionSample() },
        MySampleData("androidx.compose.foundation.samples.UsingListLayoutInfoForSideEffectSample", "${samplesDir}/stolen/samples-foundation/LazyDslSamples.kt") { androidx.compose.foundation.samples.UsingListLayoutInfoForSideEffectSample() },
        MySampleData("androidx.compose.foundation.samples.DeterminateProgressSemanticsSample", "${samplesDir}/stolen/samples-foundation/ProgressSemanticsSamples.kt") { androidx.compose.foundation.samples.DeterminateProgressSemanticsSample() },
        MySampleData("androidx.compose.foundation.samples.IndeterminateProgressSemanticsSample", "${samplesDir}/stolen/samples-foundation/ProgressSemanticsSamples.kt") { androidx.compose.foundation.samples.IndeterminateProgressSemanticsSample() },
        MySampleData("androidx.compose.foundation.samples.BasicTextFieldSample", "${samplesDir}/stolen/samples-foundation/BasicTextFieldSamples.kt") { androidx.compose.foundation.samples.BasicTextFieldSample() },
        MySampleData("androidx.compose.foundation.samples.BasicTextFieldWithStringSample", "${samplesDir}/stolen/samples-foundation/BasicTextFieldSamples.kt") { androidx.compose.foundation.samples.BasicTextFieldWithStringSample() },
        MySampleData("androidx.compose.foundation.samples.CreditCardSample", "${samplesDir}/stolen/samples-foundation/BasicTextFieldSamples.kt") { androidx.compose.foundation.samples.CreditCardSample() },
        MySampleData("androidx.compose.foundation.samples.AwaitLongPressOrCancellationSample", "${samplesDir}/stolen/samples-foundation/TapGestureSamples.kt") { androidx.compose.foundation.samples.AwaitLongPressOrCancellationSample() },
        MySampleData("androidx.compose.foundation.samples.HorizontalScrollSample", "${samplesDir}/stolen/samples-foundation/ScrollerSamples.kt") { androidx.compose.foundation.samples.HorizontalScrollSample() },
        MySampleData("androidx.compose.foundation.samples.VerticalScrollExample", "${samplesDir}/stolen/samples-foundation/ScrollerSamples.kt") { androidx.compose.foundation.samples.VerticalScrollExample() },
        MySampleData("androidx.compose.foundation.samples.ControlledScrollableRowSample", "${samplesDir}/stolen/samples-foundation/ScrollerSamples.kt") { androidx.compose.foundation.samples.ControlledScrollableRowSample() },
        MySampleData("androidx.compose.foundation.samples.SimpleInteractionSourceSample", "${samplesDir}/stolen/samples-foundation/InteractionSourceSample.kt") { androidx.compose.foundation.samples.SimpleInteractionSourceSample() },
        MySampleData("androidx.compose.foundation.samples.InteractionSourceFlowSample", "${samplesDir}/stolen/samples-foundation/InteractionSourceSample.kt") { androidx.compose.foundation.samples.InteractionSourceFlowSample() },
        MySampleData("androidx.compose.foundation.samples.IndicationSample", "${samplesDir}/stolen/samples-foundation/IndicationSamples.kt") { androidx.compose.foundation.samples.IndicationSample() },
        MySampleData("androidx.compose.foundation.samples.ClickableText", "${samplesDir}/stolen/samples-foundation/ClickableTextSample.kt") { androidx.compose.foundation.samples.ClickableText() },
        MySampleData("androidx.compose.foundation.samples.DraggableSample", "${samplesDir}/stolen/samples-foundation/DraggableSamples.kt") { androidx.compose.foundation.samples.DraggableSample() },
        MySampleData("androidx.compose.animation.samples.GestureAnimationSample", "${samplesDir}/stolen/samples-animation/TransitionSamples.kt") { androidx.compose.animation.samples.GestureAnimationSample() },
        MySampleData("androidx.compose.animation.samples.InfiniteTransitionSample", "${samplesDir}/stolen/samples-animation/TransitionSamples.kt") { androidx.compose.animation.samples.InfiniteTransitionSample() },
        MySampleData("androidx.compose.animation.samples.AnimateIncrementDecrementSample", "${samplesDir}/stolen/samples-animation/AnimatedContentSamples.kt") { androidx.compose.animation.samples.AnimateIncrementDecrementSample() },
        MySampleData("androidx.compose.animation.samples.SimpleAnimatedContentSample", "${samplesDir}/stolen/samples-animation/AnimatedContentSamples.kt") { androidx.compose.animation.samples.SimpleAnimatedContentSample() },
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
        MySampleData("androidx.compose.animation.samples.AddAnimatedVisibilityToGenericTransitionSample", "${samplesDir}/stolen/samples-animation/AnimatedVisibilitySamples.kt") { androidx.compose.animation.samples.AddAnimatedVisibilityToGenericTransitionSample() },
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
            for (sample in samples) MySampleItem(sample) { selectedSample = it; println(it.path) }
        }
        MyFancyFrame(Modifier.weight(1f), selectedSample.title) {
            selectedSample.code()
        }
    }
}
// endregion Generated Playgrounds from PlaygroundsTemplate

