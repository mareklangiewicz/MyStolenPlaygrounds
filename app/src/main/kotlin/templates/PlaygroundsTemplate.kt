package pl.mareklangiewicz.playgrounds

import androidx.compose.animation.core.samples.*
import androidx.compose.animation.samples.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.samples.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import pl.mareklangiewicz.myfancyframe.MyFancyFrame


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaygroundsTemplate() {
    Row {
//        val density = Density(1.5f)
        val density = LocalDensity.current
        CompositionLocalProvider(LocalDensity provides density) {
            LazyVerticalGrid(cells = GridCells.Adaptive(128.dp)) {
                item { Text("Some Sample") } // REPLACE
                item { Text("Some Sample") } // REMOVE
                item { Text("Some Sample") } // REMOVE
                for (i in 1..15) item { HelloColumn() } // REMOVE
                for (i in 1..15) item { Text("Some Sample $i") } // REMOVE
            }
        }
    }
}


// BEGIN generated Playgrounds from PlaygroundsTemplate
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Playgrounds() {
    Row {
        CompositionLocalProvider(LocalDensity provides Density(1.5f)) {
            LazyVerticalGrid(cells = GridCells.Adaptive(128.dp)) {
                item { MyFancyFrame("GradientBrushSample") { GradientBrushSample() } }
                item { MyFancyFrame("DrawScopeSample") { DrawScopeSample() } }
                item { MyFancyFrame("DrawScopeBatchedTransformSample") { DrawScopeBatchedTransformSample() } }
                item { MyFancyFrame("DrawScopeOvalBrushSample") { DrawScopeOvalBrushSample() } }
                item { MyFancyFrame("DrawScopeOvalColorSample") { DrawScopeOvalColorSample() } }
                item { MyFancyFrame("StampedPathEffectSample") { StampedPathEffectSample() } }
                item { MyFancyFrame("AnimatableAnimateToGenericsType") { AnimatableAnimateToGenericsType() } }
                item { MyFancyFrame("AlphaAnimationSample") { AlphaAnimationSample() } }
                item { MyFancyFrame("ArbitraryValueTypeTransitionSample") { ArbitraryValueTypeTransitionSample() } }
                item { MyFancyFrame("DpAnimationSample") { DpAnimationSample() } }
                item { MyFancyFrame("InfiniteTransitionSample") { InfiniteTransitionSample() } }
                item { MyFancyFrame("InfiniteTransitionAnimateValueSample") { InfiniteTransitionAnimateValueSample() } }
                item { MyFancyFrame("GestureAnimationSample") { GestureAnimationSample() } }
                item { MyFancyFrame("AnimateFloatSample") { AnimateFloatSample() } }
                item { MyFancyFrame("DoubleTapToLikeSample") { DoubleTapToLikeSample() } }
                item { MyFancyFrame("ColorAnimationSample") { ColorAnimationSample() } }
                item { MyFancyFrame("HorizontalTransitionSample") { HorizontalTransitionSample() } }
                item { MyFancyFrame("SlideTransition") { SlideTransition() } }
                item { MyFancyFrame("FadeTransition") { FadeTransition() } }
                item { MyFancyFrame("FullyLoadedTransition") { FullyLoadedTransition() } }
                item { MyFancyFrame("AnimatedVisibilityWithBooleanVisibleParamNoReceiver") { AnimatedVisibilityWithBooleanVisibleParamNoReceiver() } }
                item { MyFancyFrame("SlideInOutSample") { SlideInOutSample() } }
                item { MyFancyFrame("ExpandShrinkVerticallySample") { ExpandShrinkVerticallySample() } }
                item { MyFancyFrame("ExpandInShrinkOutSample") { ExpandInShrinkOutSample() } }
                item { MyFancyFrame("AnimatedVisibilityLazyColumnSample") { AnimatedVisibilityLazyColumnSample() } }
                item { MyFancyFrame("AnimateContent") { AnimateContent() } }
                item { MyFancyFrame("CrossfadeSample") { CrossfadeSample() } }
                item { MyFancyFrame("GestureAnimationSample") { GestureAnimationSample2() } }
                item { MyFancyFrame("InfiniteTransitionSample") { InfiniteTransitionSample2() } }
            }
        }
    }
}
// END generated Playgrounds from PlaygroundsTemplate


@Preview(showBackground = true)
@Composable
fun PlaygroundsTemplatePreview() {
    PlaygroundsTheme {
        PlaygroundsTemplate()
    }
}


@Composable
private fun HelloColumn() {
    MyFancyFrame("Hello!") {
        val d = LocalDensity.current.density
        Column {
            for (a in 1..5)
                Text(text = "Hello $a d:$d")
        }
    }
}
