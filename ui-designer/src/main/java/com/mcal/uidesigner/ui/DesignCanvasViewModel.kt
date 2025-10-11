package com.mcal.uidesigner.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mcal.uidesigner.ui.model.ComposeWidget
import com.mcal.uidesigner.ui.model.DesignNode

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

    fun updateAttribute(node: DesignNode, key: String, value: String) {
        root.value?.let {
            root.value = updateNodeAttribute(it, node.id, key, value)
        }
    }

    private fun updateNodeAttribute(currentNode: DesignNode, nodeId: String, key: String, value: String): DesignNode {
        if (currentNode.id == nodeId) {
            val newAttributes = currentNode.attributes.toMutableMap()
            newAttributes[key] = value
            val updatedNode = currentNode.copy(attributes = newAttributes)
            if (selectedNode.value?.id == updatedNode.id) {
                selectedNode.value = updatedNode
            }
            return updatedNode
        }
        return currentNode.copy(children = currentNode.children.map { updateNodeAttribute(it, nodeId, key, value) })
    }
}