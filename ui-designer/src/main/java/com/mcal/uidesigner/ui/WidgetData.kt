package com.mcal.uidesigner.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

val widgets = listOf(
    // Buttons
    ComposeWidget("Button", "Buttons", "Button", mapOf("text" to "Button"), Icons.Filled.RadioButtonChecked),
    ComposeWidget("Elevated Button", "Buttons", "ElevatedButton", mapOf("text" to "Elevated Button"), Icons.Filled.RadioButtonChecked),
    ComposeWidget("Filled Tonal Button", "Buttons", "FilledTonalButton", mapOf("text" to "Filled Tonal Button"), Icons.Filled.RadioButtonChecked),
    ComposeWidget("Outlined Button", "Buttons", "OutlinedButton", mapOf("text" to "Outlined Button"), Icons.Filled.RadioButtonUnchecked),
    ComposeWidget("Text Button", "Buttons", "TextButton", mapOf("text" to "Text Button"), Icons.Filled.TextFields),
    ComposeWidget("Icon Button", "Buttons", "IconButton", icon = Icons.Filled.Favorite),
    ComposeWidget("Floating Action Button", "Buttons", "FloatingActionButton", icon = Icons.Filled.Add),
    ComposeWidget("Extended Floating Action Button", "Buttons", "ExtendedFloatingActionButton", mapOf("text" to "Extended FAB"), icon = Icons.Filled.Add),

    // Text Fields
    ComposeWidget("Text Field", "Text Fields", "TextField", mapOf("label" to "Label"), Icons.Filled.TextFields),
    ComposeWidget("Outlined Text Field", "Text Fields", "OutlinedTextField", mapOf("label" to "Label"), Icons.Filled.TextFields),

    // Selection
    ComposeWidget("Checkbox", "Selection", "Checkbox", icon = Icons.Filled.CheckBox),
    ComposeWidget("Radio Button", "Selection", "RadioButton", icon = Icons.Filled.RadioButtonChecked),
    ComposeWidget("Switch", "Selection", "Switch", icon = Icons.Filled.ToggleOn),
    ComposeWidget("Slider", "Selection", "Slider", icon = Icons.Filled.ShowChart),

    // Progress Indicators
    ComposeWidget("Circular Progress", "Progress", "CircularProgressIndicator", icon = Icons.Filled.Refresh),
    ComposeWidget("Linear Progress", "Progress", "LinearProgressIndicator", icon = Icons.Filled.ShowChart),

    // Containers
    ComposeWidget("Card", "Containers", "Card"),
    ComposeWidget("Elevated Card", "Containers", "ElevatedCard"),
    ComposeWidget("Outlined Card", "Containers", "OutlinedCard"),
    ComposeWidget("Box", "Containers", "Box", icon = Icons.Filled.CheckBoxOutlineBlank),
    ComposeWidget("Column", "Containers", "Column", icon = Icons.Filled.ViewStream),
    ComposeWidget("Row", "Containers", "Row", icon = Icons.Filled.ViewColumn),
    ComposeWidget("Lazy Column", "Containers", "LazyColumn", icon = Icons.Filled.ViewList),
    ComposeWidget("Lazy Row", "Containers", "LazyRow", icon = Icons.Filled.ViewModule),

    // Navigation
    ComposeWidget("Navigation Bar", "Navigation", "NavigationBar"),
    ComposeWidget("Navigation Rail", "Navigation", "NavigationRail"),
    ComposeWidget("Navigation Drawer", "Navigation", "ModalNavigationDrawer"),
    ComposeWidget("Bottom App Bar", "Navigation", "BottomAppBar"),
    ComposeWidget("Top App Bar", "Navigation", "TopAppBar"),

    // Other
    ComposeWidget("Text", "Other", "Text", mapOf("text" to "Text"), Icons.Filled.TextFields),
    ComposeWidget("Icon", "Other", "Icon", icon = Icons.Filled.Image),
    ComposeWidget("Image", "Other", "Image"),
    ComposeWidget("Divider", "Other", "Divider")
)