package com.mcal.uidesigner.ui

class CodeGenerator {
    fun generateCode(rootNode: DesignNode): String {
        val stringBuilder = StringBuilder()
        generateNodeCode(rootNode, stringBuilder, 0)
        return stringBuilder.toString()
    }

    private fun generateNodeCode(node: DesignNode, stringBuilder: StringBuilder, indentation: Int) {
        val indent = "    ".repeat(indentation)
        stringBuilder.append("$indent${node.widget.elementName}(\n")

        node.attributes.forEach { (key, value) ->
            stringBuilder.append("$indent    $key = \"$value\",\n")
        }

        if (node.children.isNotEmpty()) {
            stringBuilder.append("$indent) {\n")
            node.children.forEach { child ->
                generateNodeCode(child, stringBuilder, indentation + 1)
            }
            stringBuilder.append("$indent}\n")
        } else {
            stringBuilder.append("$indent)\n")
        }
    }
}