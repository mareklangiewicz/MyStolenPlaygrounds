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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.samples.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.mareklangiewicz.myfancyframe.MyFancyFrame


@Composable
fun PlaygroundsTemplate() {
    Row {
    //  val density = Density(1.5f)
        val density = LocalDensity.current
        CompositionLocalProvider(LocalDensity provides density) {
            LazyVerticalGrid(cells = GridCells.Adaptive(164.dp)) {
                MyFancyItem("Some Sample") { Text("Some Sample") } // REPLACE
                MyFancyItem("Some Sample") { Text("Some Sample") } // REMOVE
                MyFancyItem("Some Sample") { Text("Some Sample") } // REMOVE
                for (i in 1..15) MyFancyItem("Hello Column") { HelloColumn() } // REMOVE
                for (i in 1..15) MyFancyItem("Another Sample $i") { Text("Some Sample $i") } // REMOVE
            }
        }
    }
}

fun LazyGridScope.MyFancyItem(title: String, content: @Composable () -> Unit) {
    item { MyFancyFrame(Modifier.size(164.dp, 256.dp), title = title) { content() } }
}


// BEGIN generated Playgrounds from PlaygroundsTemplate
@Composable
fun Playgrounds() {
    Row {
    //  val density = Density(1.5f)
        val density = LocalDensity.current
        CompositionLocalProvider(LocalDensity provides density) {
            LazyVerticalGrid(cells = GridCells.Adaptive(164.dp)) {
                MyFancyItem("GradientBrushSample") { GradientBrushSample() }
                MyFancyItem("DrawScopeSample") { DrawScopeSample() }
                MyFancyItem("DrawScopeBatchedTransformSample") { DrawScopeBatchedTransformSample() }
                MyFancyItem("DrawScopeOvalBrushSample") { DrawScopeOvalBrushSample() }
                MyFancyItem("DrawScopeOvalColorSample") { DrawScopeOvalColorSample() }
                MyFancyItem("StampedPathEffectSample") { StampedPathEffectSample() }
                MyFancyItem("AnimatableAnimateToGenericsType") { AnimatableAnimateToGenericsType() }
                MyFancyItem("AlphaAnimationSample") { AlphaAnimationSample() }
                MyFancyItem("ArbitraryValueTypeTransitionSample") { ArbitraryValueTypeTransitionSample() }
                MyFancyItem("DpAnimationSample") { DpAnimationSample() }
                MyFancyItem("InfiniteTransitionSample") { InfiniteTransitionSample() }
                MyFancyItem("InfiniteTransitionAnimateValueSample") { InfiniteTransitionAnimateValueSample() }
                MyFancyItem("GestureAnimationSample") { GestureAnimationSample() }
                MyFancyItem("AnimateFloatSample") { AnimateFloatSample() }
                MyFancyItem("DoubleTapToLikeSample") { DoubleTapToLikeSample() }
                MyFancyItem("ColorAnimationSample") { ColorAnimationSample() }
                MyFancyItem("HorizontalTransitionSample") { HorizontalTransitionSample() }
                MyFancyItem("SlideTransition") { SlideTransition() }
                MyFancyItem("FadeTransition") { FadeTransition() }
                MyFancyItem("FullyLoadedTransition") { FullyLoadedTransition() }
                MyFancyItem("AnimatedVisibilityWithBooleanVisibleParamNoReceiver") { AnimatedVisibilityWithBooleanVisibleParamNoReceiver() }
                MyFancyItem("SlideInOutSample") { SlideInOutSample() }
                MyFancyItem("ExpandShrinkVerticallySample") { ExpandShrinkVerticallySample() }
                MyFancyItem("ExpandInShrinkOutSample") { ExpandInShrinkOutSample() }
                MyFancyItem("AnimatedVisibilityLazyColumnSample") { AnimatedVisibilityLazyColumnSample() }
                MyFancyItem("AnimateContent") { AnimateContent() }
                MyFancyItem("CrossfadeSample") { CrossfadeSample() }
                MyFancyItem("GestureAnimationSample") { GestureAnimationSample() }
                MyFancyItem("InfiniteTransitionSample") { InfiniteTransitionSample() }
            }
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
