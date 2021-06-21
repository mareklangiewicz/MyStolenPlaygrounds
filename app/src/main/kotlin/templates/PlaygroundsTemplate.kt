package pl.mareklangiewicz.playgrounds

import androidx.compose.animation.core.samples.*
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
import pl.mareklangiewicz.playgrounds.ui.theme.PlaygroundsTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaygroundsTemplate() {
    Row {
        CompositionLocalProvider(LocalDensity provides Density(1.5f)) {
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
                item { GradientBrushSample() }
                item { DrawScopeSample() }
                item { DrawScopeBatchedTransformSample() }
                item { DrawScopeOvalBrushSample() }
                item { DrawScopeOvalColorSample() }
                item { StampedPathEffectSample() }
                item { AnimatableAnimateToGenericsType() }
                item { AlphaAnimationSample() }
                item { ArbitraryValueTypeTransitionSample() }
                item { DpAnimationSample() }
                item { InfiniteTransitionSample() }
                item { InfiniteTransitionAnimateValueSample() }
                item { GestureAnimationSample() }
                item { AnimateFloatSample() }
                item { DoubleTapToLikeSample() }
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


@Preview
@Composable
fun HelloMultiDensity() {
    Row {
        HelloColumn()
        CompositionLocalProvider(LocalDensity provides Density(1f)) {
            HelloColumn()
        }
    }
}

@Composable
private fun HelloColumn() {
    Column {
        val d = LocalDensity.current.density
        for (a in 1..5)
            Text(text = "Hello $a d:$d")
    }
}
