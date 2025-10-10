package com.mcal.uidesigner.ui.model

import java.util.UUID

data class DesignNode(
    val id: String = UUID.randomUUID().toString(),
    val widget: ComposeWidget,
    val attributes: Map<String, String> = emptyMap(),
    val children: List<DesignNode> = emptyList()
)