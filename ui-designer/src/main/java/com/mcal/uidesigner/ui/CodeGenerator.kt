package com.mcal.uidesigner.ui

import com.mcal.uidesigner.ui.model.DesignNode
import com.mcal.uidesigner.ui.model.WidgetType

class CodeGenerator {
    fun generateCode(rootNode: DesignNode): String {
        val stringBuilder = StringBuilder()
        val imports = mutableSetOf<String>(
            "import androidx.compose.runtime.Composable",
            "import androidx.compose.material3.*",
            "import androidx.compose.foundation.layout.Column",
            "import androidx.compose.foundation.layout.Row",
            "import androidx.compose.material.icons.Icons",
            "import androidx.compose.material.icons.filled.Add"
        )

        val body = generateNodeCode(rootNode, 1, imports)

        imports.forEach { stringBuilder.append(it).append("\n") }
        stringBuilder.append("\n@Composable\n")
        stringBuilder.append("fun GeneratedUI() {\n")
        stringBuilder.append(body)
        stringBuilder.append("}\n")
        return stringBuilder.toString()
    }

    private fun generateNodeCode(node: DesignNode, indentation: Int, imports: MutableSet<String>): String {
        val indent = "    ".repeat(indentation)
        val stringBuilder = StringBuilder()
        stringBuilder.append("$indent${node.widget.type}(\n")

        val attributes = node.attributes.toMutableMap()

        getWidgetSpecificProps(node, attributes, indent, stringBuilder)

        attributes.forEach { (key, value) ->
            val formattedValue = formatValue(value, imports)
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
        stringBuilder: StringBuilder
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
                stringBuilder.append("$indent    onClick = {},\n")
                val text = attributes.remove("text") ?: ""
                stringBuilder.append("$indent    text = { Text(\"$text\") },\n")
            }
            WidgetType.IconButton -> {
                stringBuilder.append("$indent    onClick = {},\n")
                stringBuilder.append("$indent    content = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },\n")
            }
            WidgetType.FloatingActionButton -> {
                stringBuilder.append("$indent    onClick = {},\n")
                stringBuilder.append("$indent    content = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },\n")
            }
            WidgetType.ExtendedFloatingActionButton -> {
                stringBuilder.append("$indent    onClick = {},\n")
                val text = attributes.remove("text") ?: ""
                stringBuilder.append("$indent    text = { Text(\"$text\") },\n")
                stringBuilder.append("$indent    icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },\n")
            }
            else -> {}
        }
    }

    private fun formatValue(value: String, imports: MutableSet<String>): String {
        return when {
            value.toBooleanStrictOrNull() != null -> value
            value.toIntOrNull() != null -> value
            value.toFloatOrNull() != null -> "${value}f"
            value.startsWith("Color.") -> {
                imports.add("import androidx.compose.ui.graphics.Color")
                value
            }
            value.startsWith("Icons.") -> {
                imports.add("import androidx.compose.material.icons.Icons")
                value
            }
            else -> "\"$value\""
        }
    }
}