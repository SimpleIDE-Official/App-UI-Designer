package com.mcal.uidesigner.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned

fun Modifier.dragSource(
    widget: ComposeWidget,
    viewModel: DragAndDropViewModel
): Modifier = this.pointerInput(Unit) {
    detectDragGestures(
        onDragStart = {
            viewModel.startDrag(widget)
        },
        onDragEnd = {
            viewModel.stopDrag()
        },
        onDrag = { change, dragAmount ->
            change.consume()
            viewModel.updateDragPosition(dragAmount)
        }
    )
}

fun Modifier.dropTarget(
    onDrop: () -> Unit
): Modifier = composed {
    var isHovered by remember { mutableStateOf(false) }
    var bounds by remember { mutableStateOf(Rect.Zero) }

    this
        .onGloballyPositioned {
            bounds = it.getBoundsInRoot()
        }
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    // This is a simplified check, a real implementation would need to
                    // check the drag position from the ViewModel
                    isHovered = event.changes.any { it.pressed && bounds.contains(it.position) }
                    if (!event.changes.any { it.pressed } && isHovered) {
                        onDrop()
                        isHovered = false
                    }
                }
            }
        }
}

private fun androidx.compose.ui.layout.LayoutCoordinates.getBoundsInRoot(): Rect {
    val (x, y) = this.localToRoot(androidx.compose.ui.geometry.Offset.Zero)
    return Rect(x, y, x + this.size.width, y + this.size.height)
}