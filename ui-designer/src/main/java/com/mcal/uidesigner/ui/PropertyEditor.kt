package com.mcal.uidesigner.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PropertyEditor(selectedNode: DesignNode?) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (selectedNode != null) {
            Text("Properties for ${selectedNode.widget.name}", style = MaterialTheme.typography.titleLarge)
            selectedNode.attributes.forEach { (key, value) ->
                Text("$key = $value")
            }
        } else {
            Text("No widget selected")
        }
    }
}