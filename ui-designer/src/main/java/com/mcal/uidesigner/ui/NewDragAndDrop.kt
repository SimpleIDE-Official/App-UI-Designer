package com.mcal.uidesigner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toSize
import com.mcal.uidesigner.ui.model.ComposeWidget
import com.mcal.uidesigner.ui.model.DesignNode
import kotlin.math.roundToInt

internal class DragState {
    var isDragging by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var draggedWidget by mutableStateOf<ComposeWidget?>(null)

    fun startDrag(widget: ComposeWidget, position: Offset) {
        isDragging = true
        draggedWidget = widget
        dragPosition = position
    }

    fun stopDrag() {
        isDragging = false
        dragPosition = Offset.Zero
        draggedWidget = null
    }
}

internal val LocalDragState = compositionLocalOf { DragState() }

@Composable
internal fun Modifier.draggable(
    widget: ComposeWidget,
    dragState: DragState = LocalDragState.current
): Modifier = pointerInput(Unit) {
    detectDragGestures(
        onDragStart = { offset ->
            dragState.startDrag(widget, offset)
        },
        onDragEnd = {
            dragState.stopDrag()
        },
        onDrag = { change, dragAmount ->
            change.consume()
            dragState.dragPosition += dragAmount
        }
    )
}

@Composable
internal fun Modifier.dropTarget(
    dragState: DragState = LocalDragState.current,
    onDrop: (ComposeWidget) -> Unit
): Modifier = composed {
    var bounds by remember { mutableStateOf(Rect.Zero) }
    var isHovered by remember { mutableStateOf(false) }

    this
        .onGloballyPositioned {
            bounds = Rect(it.positionInRoot(), it.size.toSize())
        }
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent(PointerEventPass.Main)
                    isHovered = dragState.isDragging && bounds.contains(dragState.dragPosition)

                    if (isHovered && event.changes.any { it.changedToUp() }) {
                        dragState.draggedWidget?.let(onDrop)
                    }
                }
            }
        }
        .background(if (isHovered) androidx.compose.ui.graphics.Color.LightGray else androidx.compose.ui.graphics.Color.Transparent)
}

@Composable
internal fun DragLayer(
    modifier: Modifier = Modifier,
    dragState: DragState = LocalDragState.current,
    content: @Composable () -> Unit
) {
    Box(modifier) {
        content()
        if (dragState.isDragging) {
            dragState.draggedWidget?.let { widget ->
                val previewNode = DesignNode(
                    widget = widget,
                    attributes = widget.attributes
                )
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = dragState.dragPosition.x.roundToInt(),
                                y = dragState.dragPosition.y.roundToInt()
                            )
                        }
                ) {
                    WidgetRenderer(node = previewNode)
                }
            }
        }
    }
}