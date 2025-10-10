package com.mcal.uidesigner.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mcal.uidesigner.ui.model.DesignNode

@Composable
fun DesignCanvas(
    designCanvasViewModel: DesignCanvasViewModel = viewModel(),
    dragAndDropViewModel: DragAndDropViewModel = viewModel()
) {
    val rootNode by designCanvasViewModel.root
    val selectedNode by designCanvasViewModel.selectedNode

    Box(
        modifier = Modifier
            .fillMaxSize()
            .dropTarget(
                viewModel = dragAndDropViewModel,
                onDrop = { widget ->
                    designCanvasViewModel.addWidget(widget)
                }
            )
    ) {
        if (rootNode == null) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text("Design Canvas")
            }
        } else {
            RenderNode(
                node = rootNode!!,
                selectedNode = selectedNode,
                onNodeSelected = { designCanvasViewModel.selectNode(it) }
            )
        }
    }
}

@Composable
fun RenderNode(
    node: DesignNode,
    selectedNode: DesignNode?,
    onNodeSelected: (DesignNode) -> Unit
) {
    val isSelected = node.id == selectedNode?.id
    val borderColor = if (isSelected) Color.Red else Color.Gray

    Box(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, borderColor)
            .clickable { onNodeSelected(node) }
            .padding(8.dp)
    ) {
        WidgetRenderer(node = node)
    }
}