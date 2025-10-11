package com.mcal.uidesigner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mcal.uidesigner.ui.model.DesignNode
import com.mcal.uidesigner.ui.model.Property
import com.mcal.uidesigner.ui.model.PropertyType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedPropertyEditor(
    selectedNode: DesignNode?,
    onAttributeChange: (DesignNode, String, String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (selectedNode != null) {
            Text(
                "Properties for ${selectedNode.widget.name}",
                style = MaterialTheme.typography.titleLarge
            )
            val properties = selectedNode.attributes.map { (key, value) ->
                getPropertyType(key, value)
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
                    PropertyType.Int -> {
                        var text by remember(property.value) { mutableStateOf(property.value.toString()) }
                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                text = it
                                onAttributeChange(selectedNode, property.name, it)
                            },
                            label = { Text(property.name) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    PropertyType.Float -> {
                        var text by remember(property.value) { mutableStateOf(property.value.toString()) }
                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                text = it
                                onAttributeChange(selectedNode, property.name, it)
                            },
                            label = { Text(property.name) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    PropertyType.Boolean -> {
                        val checked by remember(property.value) { mutableStateOf(property.value.toString().toBoolean()) }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(property.name)
                            Switch(
                                checked = checked,
                                onCheckedChange = {
                                    onAttributeChange(selectedNode, property.name, it.toString())
                                }
                            )
                        }
                    }
                    PropertyType.Color -> {
                        var showColorPicker by remember { mutableStateOf(false) }
                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .clickable { showColorPicker = true },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(property.name)
                            Spacer(Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = Color(
                                            property.value
                                                .toString()
                                                .removePrefix("Color(0x")
                                                .removeSuffix(")")
                                                .toLong(16)
                                        )
                                    )
                            )
                        }
                        if (showColorPicker) {
                            ColorPickerDialog(
                                onDismissRequest = { showColorPicker = false },
                                onColorSelected = { color ->
                                    onAttributeChange(selectedNode, property.name, "Color(0x${color.value.toULong().toString(16)})")
                                    showColorPicker = false
                                }
                            )
                        }
                    }
                    PropertyType.Size -> {
                        var text by remember(property.value) { mutableStateOf(property.value.toString()) }
                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                text = it
                                onAttributeChange(selectedNode, property.name, it)
                            },
                            label = { Text(property.name) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.padding(top = 8.dp),
                            suffix = { Text("dp") }
                        )
                    }
                    PropertyType.Enum -> {
                        var expanded by remember { mutableStateOf(false) }
                        var selectedOption by remember(property.value) { mutableStateOf(property.value.toString()) }

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedOption,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text(property.name) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                property.options.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            selectedOption = option
                                            onAttributeChange(selectedNode, property.name, option)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
        } else {
            Text("No widget selected")
        }
    }
}

fun getPropertyType(key: String, value: Any): Property<*> {
    return when {
        key.contains("color", ignoreCase = true) -> Property(key, PropertyType.Color, value)
        key.contains("size", ignoreCase = true) -> Property(key, PropertyType.Size, value)
        key == "contentScale" -> Property(key, PropertyType.Enum, value, listOf("None", "Fit", "Crop", "FillBounds", "FillWidth", "FillHeight", "Inside"))
        value is String -> Property(key, PropertyType.String, value)
        value is Int -> Property(key, PropertyType.Int, value)
        value is Float -> Property(key, PropertyType.Float, value)
        value is Boolean -> Property(key, PropertyType.Boolean, value)
        else -> Property(key, PropertyType.String, value)
    }
}

@Composable
fun ColorPicker(
    onColorSelected: (Color) -> Unit
) {
    var red by remember { mutableStateOf(0f) }
    var green by remember { mutableStateOf(0f) }
    var blue by remember { mutableStateOf(0f) }

    Column {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color(red, green, blue))
        )
        Slider(value = red, onValueChange = { red = it })
        Slider(value = green, onValueChange = { green = it })
        Slider(value = blue, onValueChange = { blue = it })
        Button(onClick = { onColorSelected(Color(red, green, blue)) }) {
            Text("Select")
        }
    }
}

@Composable
fun ColorPickerDialog(
    onDismissRequest: () -> Unit,
    onColorSelected: (Color) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select Color") },
        text = { ColorPicker(onColorSelected = onColorSelected) },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("Close")
            }
        }
    )
}