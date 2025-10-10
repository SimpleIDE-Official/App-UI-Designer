package com.mcal.uidesigner.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DesignCanvasViewModel : ViewModel() {
    val root = mutableStateOf<DesignNode?>(null)
    val selectedNode = mutableStateOf<DesignNode?>(null)

    fun addWidget(widget: ComposeWidget) {
        val newNode = DesignNode(widget = widget)
        if (root.value == null) {
            root.value = newNode
            selectedNode.value = newNode
        } else {
            val targetNodeId = selectedNode.value?.id ?: root.value?.id
            if (targetNodeId != null) {
                root.value = findAndAddChild(root.value, targetNodeId, newNode)
            }
        }
    }

    private fun findAndAddChild(currentNode: DesignNode?, targetId: String, childToAdd: DesignNode): DesignNode? {
        if (currentNode == null) return null
        if (currentNode.id == targetId) {
            return currentNode.copy(children = currentNode.children + childToAdd)
        }
        val updatedChildren = currentNode.children.map { findAndAddChild(it, targetId, childToAdd) ?: it }
        return currentNode.copy(children = updatedChildren)
    }

    fun removeWidget(nodeToRemove: DesignNode) {
        root.value?.let {
            root.value = removeNode(it, nodeToRemove.id)
        }
        if (selectedNode.value?.id == nodeToRemove.id) {
            selectedNode.value = null
        }
    }

    private fun removeNode(currentNode: DesignNode, idToRemove: String): DesignNode? {
        if (currentNode.id == idToRemove) {
            return null
        }
        val updatedChildren = currentNode.children.mapNotNull { removeNode(it, idToRemove) }
        return currentNode.copy(children = updatedChildren)
    }

    fun selectNode(node: DesignNode) {
        selectedNode.value = node
    }
}