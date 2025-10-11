package com.mcal.uidesigner.ui

import com.mcal.uidesigner.ui.model.DesignNode
import com.mcal.uidesigner.ui.model.PropertyType
import com.mcal.uidesigner.ui.model.WidgetType

class AdvancedCodeGenerator {
    fun generateCode(rootNode: DesignNode): String {
        val stringBuilder = StringBuilder()
        val imports = mutableSetOf(
            "import androidx.compose.runtime.Composable",
            "import androidx.compose.ui.Modifier"
        )

        val body = generateNodeCode(rootNode, 1, imports)

        imports.sorted().forEach { stringBuilder.append(it).append("\n") }
        stringBuilder.append("\n@Composable\n")
        stringBuilder.append("fun GeneratedUI() {\n")
        stringBuilder.append(body)
        stringBuilder.append("}\n")
        return stringBuilder.toString()
    }

    private fun generateNodeCode(node: DesignNode, indentation: Int, imports: MutableSet<String>): String {
        val indent = "    ".repeat(indentation)
        val stringBuilder = StringBuilder()
        stringBuilder.append("$indent${node.widget.type.name}(\n")

        val attributes = node.attributes.toMutableMap()
        getWidgetSpecificProps(node, attributes, indent, stringBuilder, imports)

        attributes.forEach { (key, value) ->
            val formattedValue = formatValue(key, value, imports)
            stringBuilder.append("$indent    $key = $formattedValue,\n")
        }

        if (node.children.isNotEmpty()) {
            stringBuilder.append("$indent) {\n")
            node.children.forEach { child ->
                stringBuilder.append(generateNodeCode(child, indentation + 1, imports))
            }
            stringBuilder.append("$indent}\n")
        } else {
            stringBuilder.append("$indent)\n")
        }
        return stringBuilder.toString()
    }

    private fun getWidgetSpecificProps(
        node: DesignNode,
        attributes: MutableMap<String, String>,
        indent: String,
        stringBuilder: StringBuilder,
        imports: MutableSet<String>
    ) {
        when (node.widget.type) {
            WidgetType.Text -> {
                val text = attributes.remove("text") ?: ""
                stringBuilder.append("$indent    text = \"$text\",\n")
            }
            WidgetType.Button,
            WidgetType.ElevatedButton,
            WidgetType.FilledTonalButton,
            WidgetType.OutlinedButton,
            WidgetType.TextButton -> {
                imports.add("import androidx.compose.material3.Text")
                stringBuilder.append("$indent    onClick = {},\n")
                val text = attributes.remove("text") ?: ""
                stringBuilder.append("$indent    text = { Text(\"$text\") },\n")
            }
            WidgetType.IconButton,
            WidgetType.FloatingActionButton -> {
                imports.add("import androidx.compose.material3.Icon")
                imports.add("import androidx.compose.material.icons.Icons")
                imports.add("import androidx.compose.material.icons.filled.Add")
                stringBuilder.append("$indent    onClick = {},\n")
                stringBuilder.append("$indent    content = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },\n")
            }
            WidgetType.ExtendedFloatingActionButton -> {
                imports.add("import androidx.compose.material3.Icon")
                imports.add("import androidx.compose.material3.Text")
                imports.add("import androidx.compose.material.icons.Icons")
                imports.add("import androidx.compose.material.icons.filled.Add")
                stringBuilder.append("$indent    onClick = {},\n")
                val text = attributes.remove("text") ?: ""
                stringBuilder.append("$indent    text = { Text(\"$text\") },\n")
                stringBuilder.append("$indent    icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },\n")
            }
            WidgetType.TextField,
            WidgetType.OutlinedTextField -> {
                imports.add("import androidx.compose.material3.Text")
                val label = attributes.remove("label") ?: ""
                stringBuilder.append("$indent    label = { Text(\"$label\") },\n")
                stringBuilder.append("$indent    onValueChange = {},\n")
            }
            WidgetType.Checkbox,
            WidgetType.Switch -> {
                val checked = attributes.remove("checked") ?: "false"
                stringBuilder.append("$indent    checked = $checked,\n")
                stringBuilder.append("$indent    onCheckedChange = {},\n")
            }
            WidgetType.RadioButton -> {
                val selected = attributes.remove("selected") ?: "false"
                stringBuilder.append("$indent    selected = $selected,\n")
                stringBuilder.append("$indent    onClick = {},\n")
            }
            WidgetType.Slider -> {
                val value = attributes.remove("value") ?: "0.5f"
                stringBuilder.append("$indent    value = $value,\n")
                stringBuilder.append("$indent    onValueChange = {},\n")
            }
            else -> {}
        }
    }

    private fun formatValue(key: String, value: String, imports: MutableSet<String>): String {
        return when (getPropertyType(key, value).type) {
            PropertyType.String -> "\"$value\""
            PropertyType.Int -> value
            PropertyType.Float -> "${value}f"
            PropertyType.Boolean -> value
            PropertyType.Color -> {
                imports.add("import androidx.compose.ui.graphics.Color")
                value
            }
            PropertyType.Size -> {
                imports.add("import androidx.compose.ui.unit.dp")
                "${value}.dp"
            }
            PropertyType.Enum -> {
                val enumClass = getEnumClass(key)
                imports.add("import androidx.compose.ui.layout.ContentScale")
                "${enumClass}.$value"
            }
        }
    }

    private fun getEnumClass(key: String): String {
        return when (key) {
            "contentScale" -> "ContentScale"
            else -> ""
        }
    }
}