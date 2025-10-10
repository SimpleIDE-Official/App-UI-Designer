package com.mcal.uidesigner.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class ComposeWidget(
    val name: String,
    val category: String,
    val elementName: String,
    val attributes: Map<String, String> = emptyMap(),
    val icon: ImageVector? = null
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WidgetPicker(onWidgetSelected: (ComposeWidget) -> Unit) {
    val grouped = widgets.groupBy { it.category }

    LazyColumn {
        grouped.forEach { (category, widgets) ->
            stickyHeader {
                Text(
                    text = category,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            items(widgets) { widget ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onWidgetSelected(widget) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    widget.icon?.let {
                        Icon(imageVector = it, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    }
                    Text(
                        text = widget.name
                    )
                }
            }
        }
    }
}