package com.mcal.uidesigner.ui.model

enum class PropertyType {
    String,
    Int,
    Float,
    Boolean,
    Color
}

data class Property(
    val name: String,
    val type: PropertyType,
    var value: Any
)