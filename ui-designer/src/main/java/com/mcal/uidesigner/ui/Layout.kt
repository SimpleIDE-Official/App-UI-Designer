package com.mcal.uidesigner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mcal.uidesigner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    designCanvasViewModel: DesignCanvasViewModel = viewModel()
) {
    val selectedNode by designCanvasViewModel.selectedNode
    var showCodeDialog by remember { mutableStateOf(false) }
    var generatedCode by remember { mutableStateOf("") }
    val dragState = remember { DragState() }

    CompositionLocalProvider(LocalDragState provides dragState) {
        DragLayer(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.app_name)) },
                        actions = {
                            IconButton(onClick = {
                                designCanvasViewModel.root.value?.let {
                                generatedCode = AdvancedCodeGenerator().generateCode(it)
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
                        WidgetPicker()
                    }
                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .dropTarget { widget ->
                                designCanvasViewModel.addWidget(widget)
                            }
                    ) {
                        DesignCanvas(
                            designCanvasViewModel = designCanvasViewModel
                        )
                    }
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        AdvancedPropertyEditor(
                            selectedNode = selectedNode,
                            onAttributeChange = designCanvasViewModel::updateAttribute
                        )
                    }
                }
            }

            if (showCodeDialog) {
                CodeDialog(code = generatedCode, onDismiss = { showCodeDialog = false })
            }
        }
    }
}