package com.mcal.uidesigner.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mcal.uidesigner.ui.model.DesignNode
import com.mcal.uidesigner.ui.model.WidgetType

@Composable
fun WidgetRenderer(node: DesignNode) {
    when (node.widget.type) {
        WidgetType.Text -> Text(text = node.attributes["text"] ?: "")
        WidgetType.Button -> Button(onClick = {}) { Text(text = node.attributes["text"] ?: "") }
        WidgetType.ElevatedButton -> ElevatedButton(onClick = {}) { Text(text = node.attributes["text"] ?: "") }
        WidgetType.FilledTonalButton -> FilledTonalButton(onClick = {}) { Text(text = node.attributes["text"] ?: "") }
        WidgetType.OutlinedButton -> OutlinedButton(onClick = {}) { Text(text = node.attributes["text"] ?: "") }
        WidgetType.TextButton -> TextButton(onClick = {}) { Text(text = node.attributes["text"] ?: "") }
        WidgetType.IconButton -> IconButton(onClick = {}) { Icon(imageVector = Icons.Default.Add, contentDescription = null) }
        WidgetType.FloatingActionButton -> FloatingActionButton(onClick = {}) { Icon(imageVector = Icons.Default.Add, contentDescription = null) }
        WidgetType.ExtendedFloatingActionButton -> ExtendedFloatingActionButton(onClick = {}) { Text(text = node.attributes["text"] ?: "") }
        WidgetType.Card -> Card {
            Column {
                node.children.forEach { child ->
                    WidgetRenderer(node = child)
                }
            }
        }
        WidgetType.ElevatedCard -> ElevatedCard {
            Column {
                node.children.forEach { child ->
                    WidgetRenderer(node = child)
                }
            }
        }
        WidgetType.OutlinedCard -> OutlinedCard {
            Column {
                node.children.forEach { child ->
                    WidgetRenderer(node = child)
                }
            }
        }
        WidgetType.Box -> Box {
            node.children.forEach { child ->
                WidgetRenderer(node = child)
            }
        }
        WidgetType.Column -> {
            Column {
                node.children.forEach { child ->
                    WidgetRenderer(node = child)
                }
            }
        }
        WidgetType.Row -> {
            Row {
                node.children.forEach { child ->
                    WidgetRenderer(node = child)
                }
            }
        }
        WidgetType.LazyColumn -> {
            LazyColumn {
                items(node.children.size) { index ->
                    WidgetRenderer(node = node.children[index])
                }
            }
        }
        WidgetType.LazyRow -> {
            LazyRow {
                items(node.children.size) { index ->
                    WidgetRenderer(node = node.children[index])
                }
            }
        }
        WidgetType.TextField -> {
            val text = remember { mutableStateOf(node.attributes["text"] ?: "") }
            TextField(value = text.value, onValueChange = { text.value = it })
        }
        WidgetType.OutlinedTextField -> {
            val text = remember { mutableStateOf(node.attributes["text"] ?: "") }
            OutlinedTextField(value = text.value, onValueChange = { text.value = it })
        }
        WidgetType.Checkbox -> {
            val checked = remember { mutableStateOf(node.attributes["checked"]?.toBoolean() ?: false) }
            Checkbox(checked = checked.value, onCheckedChange = { checked.value = it })
        }
        WidgetType.RadioButton -> {
            val selected = remember { mutableStateOf(node.attributes["selected"]?.toBoolean() ?: false) }
            RadioButton(selected = selected.value, onClick = { selected.value = !selected.value })
        }
        WidgetType.Switch -> {
            val checked = remember { mutableStateOf(node.attributes["checked"]?.toBoolean() ?: false) }
            Switch(checked = checked.value, onCheckedChange = { checked.value = it })
        }
        WidgetType.Slider -> {
            val value = remember { mutableStateOf(node.attributes["value"]?.toFloat() ?: 0f) }
            Slider(value = value.value, onValueChange = { value.value = it })
        }
        WidgetType.CircularProgressIndicator -> CircularProgressIndicator()
        WidgetType.LinearProgressIndicator -> LinearProgressIndicator()
        WidgetType.Divider -> Divider()
        else -> {
            Text(text = "Unsupported widget: ${node.widget.name}")
        }
    }
}