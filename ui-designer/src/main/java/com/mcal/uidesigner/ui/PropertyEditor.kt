package com.mcal.uidesigner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mcal.uidesigner.ui.model.DesignNode
import com.mcal.uidesigner.ui.model.Property
import com.mcal.uidesigner.ui.model.PropertyType

@Composable
fun PropertyEditor(
    selectedNode: DesignNode?,
    onAttributeChange: (DesignNode, String, String) -> Unit
) {
    var showColorPicker by remember { mutableStateOf(false) }
    var selectedProperty by remember { mutableStateOf<Property?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        if (selectedNode != null) {
            Text(
                "Properties for ${selectedNode.widget.name}",
                style = MaterialTheme.typography.titleLarge
            )
            val properties = selectedNode.attributes.map { (key, value) ->
                Property(key, getPropertyType(key, value), value)
            }
            properties.forEach { property ->
                when (property.type) {
                    PropertyType.String -> {
                        var text by remember(property.value) { mutableStateOf(property.value.toString()) }
                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                text = it
                                onAttributeChange(selectedNode, property.name, it)
                            },
                            label = { Text(property.name) },
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    PropertyType.Color -> {
                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .clickable {
                                    selectedProperty = property
                                    showColorPicker = true
                                }
                        ) {
                            Text(property.name)
                            Spacer(Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = (property.value as? Color)
                                            ?: Color.Black
                                    )
                            )
                        }
                    }
                    else -> {}
                }
            }
        } else {
            Text("No widget selected")
        }
    }

    if (showColorPicker) {
        ColorPickerDialog(
            onDismissRequest = { showColorPicker = false },
            onColorSelected = { color ->
                selectedProperty?.let {
                    onAttributeChange(selectedNode!!, it.name, "Color(0x${color.value.toULong().toString(16)})")
                }
                showColorPicker = false
            }
        )
    }
}

fun getPropertyType(key: String, value: Any): PropertyType {
    return when {
        key.contains("color", ignoreCase = true) -> PropertyType.Color
        value is String -> PropertyType.String
        value is Int -> PropertyType.Int
        value is Float -> PropertyType.Float
        value is Boolean -> PropertyType.Boolean
        else -> PropertyType.String
    }
}

@Composable
fun ColorPickerDialog(onDismissRequest: () -> Unit, onColorSelected: (Color) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select Color") },
        text = {
            // A real implementation would have a color picker here.
            // For now, we'll just have a few predefined colors.
            Column {
                Row {
                    Box(modifier = Modifier
                        .size(48.dp)
                        .background(Color.Red)
                        .clickable { onColorSelected(Color.Red) })
                    Spacer(Modifier.width(8.dp))
                    Box(modifier = Modifier
                        .size(48.dp)
                        .background(Color.Green)
                        .clickable { onColorSelected(Color.Green) })
                    Spacer(Modifier.width(8.dp))
                    Box(modifier = Modifier
                        .size(48.dp)
                        .background(Color.Blue)
                        .clickable { onColorSelected(Color.Blue) })
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("Close")
            }
        }
    )
}