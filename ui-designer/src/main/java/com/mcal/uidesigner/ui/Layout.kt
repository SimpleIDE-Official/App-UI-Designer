package com.mcal.uidesigner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mcal.uidesigner.R
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    designCanvasViewModel: DesignCanvasViewModel = viewModel(),
    dragAndDropViewModel: DragAndDropViewModel = viewModel()
) {
    val selectedNode by designCanvasViewModel.selectedNode
    val isDragging by dragAndDropViewModel.isDragging
    val draggedWidget by dragAndDropViewModel.draggedWidget
    val dragPosition by dragAndDropViewModel.dragPosition
    var showCodeDialog by remember { mutableStateOf(false) }
    var generatedCode by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    actions = {
                        IconButton(onClick = {
                            designCanvasViewModel.root.value?.let {
                                generatedCode = CodeGenerator().generateCode(it)
                                showCodeDialog = true
                            }
                        }) {
                            Icon(Icons.Default.Code, contentDescription = "Generate Code")
                        }
                        if (selectedNode != null) {
                            IconButton(onClick = { designCanvasViewModel.removeWidget(selectedNode!!) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    WidgetPicker(dragAndDropViewModel = dragAndDropViewModel)
                }
                Box(
                    modifier = Modifier.weight(2f)
                ) {
                    DesignCanvas(
                        designCanvasViewModel = designCanvasViewModel,
                        dragAndDropViewModel = dragAndDropViewModel
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    PropertyEditor(
                        selectedNode = selectedNode,
                        onAttributeChange = designCanvasViewModel::updateAttribute
                    )
                }
            }
        }

        if (isDragging) {
            draggedWidget?.let { widget ->
                Box(
                    modifier = Modifier
                        .offset { IntOffset(dragPosition.x.roundToInt(), dragPosition.y.roundToInt()) }
                ) {
                    Text(
                        text = widget.name,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        if (showCodeDialog) {
            CodeDialog(code = generatedCode, onDismiss = { showCodeDialog = false })
        }
    }
}