package com.mcal.uidesigner.ui.model

enum class PropertyType {
    String,
    Int,
    Float,
    Boolean,
    Color,
    Size,
    Enum
}

data class Property<T>(
    val name: String,
    val type: PropertyType,
    var value: T,
    val options: List<String> = emptyList()
)