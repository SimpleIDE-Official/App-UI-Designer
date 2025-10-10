package com.mcal.uidesigner.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PropertyEditor(
    selectedNode: DesignNode?,
    onAttributeChange: (DesignNode, String, String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (selectedNode != null) {
            Text(
                "Properties for ${selectedNode.widget.name}",
                style = MaterialTheme.typography.titleLarge
            )
            selectedNode.attributes.forEach { (key, value) ->
                var text by remember(value) { mutableStateOf(value) }
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onAttributeChange(selectedNode, key, it)
                    },
                    label = { Text(key) },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            Text("No widget selected")
        }
    }
}