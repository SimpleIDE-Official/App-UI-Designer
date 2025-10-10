package com.mcal.uidesigner.ui

import androidx.compose.foundation.background
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
import com.mcal.uidesigner.ui.model.ComposeWidget

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
    viewModel: DragAndDropViewModel,
    onDrop: (ComposeWidget) -> Unit
): Modifier = composed {
    var bounds by remember { mutableStateOf(Rect.Zero) }
    val isHovered = viewModel.isDragging.value && bounds.contains(viewModel.dragPosition.value)

    this
        .onGloballyPositioned {
            bounds = it.getBoundsInRoot()
        }
        .background(if (isHovered) androidx.compose.ui.graphics.Color.LightGray else androidx.compose.ui.graphics.Color.Transparent)
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    if (!event.changes.any { it.pressed } && isHovered) {
                        viewModel.draggedWidget.value?.let {
                            onDrop(it)
                        }
                    }
                }
            }
        }
}

private fun androidx.compose.ui.layout.LayoutCoordinates.getBoundsInRoot(): Rect {
    val (x, y) = this.localToRoot(androidx.compose.ui.geometry.Offset.Zero)
    return Rect(x, y, x + this.size.width, y + this.size.height)
}