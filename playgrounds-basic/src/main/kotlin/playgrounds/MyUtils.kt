package pl.mareklangiewicz.playgrounds

import androidx.compose.ui.geometry.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import kotlin.math.*

val Dp.square get() = DpSize(this, this)
val Int.square get() = IntSize(this, this)

fun Size.roundToIntSize() = IntSize(width.roundToInt(), height.roundToInt())
fun IntSize.toFixedConstraints() = Constraints(width, width, height, height)
fun IntSize.toMaxConstraints() = Constraints(0, width, 0, height)
fun Size.roundToFixedConstraints() = roundToIntSize().toFixedConstraints()

val Any?.str: String
    get() = when (this) {
        is Offset -> "($x,$y)"
        is Rect -> "rect: ${topLeft.str} - ${bottomRight.str}"
        is Placeable -> "placeable: $width x $height, measured = $measuredWidth x $measuredHeight"
        is PlaceableData -> "placeable: $width x $height, measured = $measuredWidth x $measuredHeight"
        is LayoutCoordinatesData -> "coordinates: size = $size, bounds in parent = ${boundsInParent.str}, attached = ${isAttached.markIfTrue()}, parent layout = ${parentLayoutCoordinatesData.markIfNull()}, parent = ${parentCoordinatesData.markIfNull()}"
        is Constraints -> "constraints: min = $minWidth x $minHeight, max = $maxWidth x $maxHeight"
        else -> toString()
    }
private fun Any?.markIfNull(markNotNull: String = "T", markNull: String = "F"): String = if (this != null) markNotNull else markNull
private fun Boolean.markIfTrue(markTrue: String = "T", markFalse: String = "F"): String = if (this) markTrue else markFalse


data class PlaceableData(val width: Int, val height: Int, val measuredWidth: Int = width, val measuredHeight: Int = height)

val Placeable.data get() = PlaceableData(width, height, measuredWidth, measuredHeight)

fun IntSize.toPlaceableData() = PlaceableData(width, height)

data class LayoutCoordinatesData(
    val size: IntSize,
    val parentLayoutCoordinatesData: LayoutCoordinatesData?,
    val parentCoordinatesData: LayoutCoordinatesData?,
    val isAttached: Boolean,

    // computed when creating data class
    val positionInWindow: Offset,
    val positionInRoot: Offset,
    val positionInParent: Offset,
    val boundsInWindow: Rect,
    val boundsInRoot: Rect,
    val boundsInParent: Rect,
)

val LayoutCoordinates.data: LayoutCoordinatesData get() = LayoutCoordinatesData(size = size,
    parentLayoutCoordinatesData = parentLayoutCoordinates?.data,
    parentCoordinatesData = parentCoordinates?.data,
    isAttached = isAttached,
    positionInWindow = positionInWindow(),
    positionInRoot = positionInRoot(),
    positionInParent = positionInParent(),
    boundsInWindow = boundsInWindow(),
    boundsInRoot = boundsInRoot(),
    boundsInParent = boundsInParent(),
)

