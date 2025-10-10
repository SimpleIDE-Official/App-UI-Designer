package com.mcal.uidesigner.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.mcal.uidesigner.ui.model.ComposeWidget

class DragAndDropViewModel : ViewModel() {
    val isDragging = mutableStateOf(false)
    val draggedWidget = mutableStateOf<ComposeWidget?>(null)
    val dragPosition = mutableStateOf(Offset.Zero)

    fun startDrag(widget: ComposeWidget) {
        isDragging.value = true
        draggedWidget.value = widget
    }

    fun stopDrag() {
        isDragging.value = false
        draggedWidget.value = null
        dragPosition.value = Offset.Zero
    }

    fun updateDragPosition(position: Offset) {
        dragPosition.value = position
    }
}