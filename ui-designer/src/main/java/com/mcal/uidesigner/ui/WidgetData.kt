package com.mcal.uidesigner.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.mcal.uidesigner.ui.model.ComposeWidget
import com.mcal.uidesigner.ui.model.WidgetType

val widgets = listOf(
    // Buttons
    ComposeWidget("Button", "Buttons", WidgetType.Button, mapOf("text" to "Button"), Icons.Filled.RadioButtonChecked),
    ComposeWidget("Elevated Button", "Buttons", WidgetType.ElevatedButton, mapOf("text" to "Elevated Button"), Icons.Filled.RadioButtonChecked),
    ComposeWidget("Filled Tonal Button", "Buttons", WidgetType.FilledTonalButton, mapOf("text" to "Filled Tonal Button"), Icons.Filled.RadioButtonChecked),
    ComposeWidget("Outlined Button", "Buttons", WidgetType.OutlinedButton, mapOf("text" to "Outlined Button"), Icons.Filled.RadioButtonUnchecked),
    ComposeWidget("Text Button", "Buttons", WidgetType.TextButton, mapOf("text" to "Text Button"), Icons.Filled.TextFields),
    ComposeWidget("Icon Button", "Buttons", WidgetType.IconButton, icon = Icons.Filled.Favorite),
    ComposeWidget("Floating Action Button", "Buttons", WidgetType.FloatingActionButton, icon = Icons.Filled.Add),
    ComposeWidget("Extended Floating Action Button", "Buttons", WidgetType.ExtendedFloatingActionButton, mapOf("text" to "Extended FAB"), icon = Icons.Filled.Add),

    // Text Fields
    ComposeWidget("Text Field", "Text Fields", WidgetType.TextField, mapOf("label" to "Label"), Icons.Filled.TextFields),
    ComposeWidget("Outlined Text Field", "Text Fields", WidgetType.OutlinedTextField, mapOf("label" to "Label"), Icons.Filled.TextFields),

    // Selection
    ComposeWidget("Checkbox", "Selection", WidgetType.Checkbox, icon = Icons.Filled.CheckBox),
    ComposeWidget("Radio Button", "Selection", WidgetType.RadioButton, icon = Icons.Filled.RadioButtonChecked),
    ComposeWidget("Switch", "Selection", WidgetType.Switch, icon = Icons.Filled.ToggleOn),
    ComposeWidget("Slider", "Selection", WidgetType.Slider, icon = Icons.Filled.ShowChart),

    // Progress Indicators
    ComposeWidget("Circular Progress", "Progress", WidgetType.CircularProgressIndicator, icon = Icons.Filled.Refresh),
    ComposeWidget("Linear Progress", "Progress", WidgetType.LinearProgressIndicator, icon = Icons.Filled.ShowChart),

    // Containers
    ComposeWidget("Card", "Containers", WidgetType.Card),
    ComposeWidget("Elevated Card", "Containers", WidgetType.ElevatedCard),
    ComposeWidget("Outlined Card", "Containers", WidgetType.OutlinedCard),
    ComposeWidget("Box", "Containers", WidgetType.Box, icon = Icons.Filled.CheckBoxOutlineBlank),
    ComposeWidget("Column", "Containers", WidgetType.Column, icon = Icons.Filled.ViewStream),
    ComposeWidget("Row", "Containers", WidgetType.Row, icon = Icons.Filled.ViewColumn),
    ComposeWidget("Lazy Column", "Containers", WidgetType.LazyColumn, icon = Icons.Filled.ViewList),
    ComposeWidget("Lazy Row", "Containers", WidgetType.LazyRow, icon = Icons.Filled.ViewModule),

    // Navigation
    ComposeWidget("Navigation Bar", "Navigation", WidgetType.NavigationBar),
    ComposeWidget("Navigation Rail", "Navigation", WidgetType.NavigationRail),
    ComposeWidget("Navigation Drawer", "Navigation", WidgetType.ModalNavigationDrawer),
    ComposeWidget("Bottom App Bar", "Navigation", WidgetType.BottomAppBar),
    ComposeWidget("Top App Bar", "Navigation", WidgetType.TopAppBar),

    // Other
    ComposeWidget("Text", "Other", WidgetType.Text, mapOf("text" to "Text"), Icons.Filled.TextFields),
    ComposeWidget("Icon", "Other", WidgetType.Icon, icon = Icons.Filled.Image),
    ComposeWidget("Image", "Other", WidgetType.Image),
    ComposeWidget("Divider", "Other", WidgetType.Divider)
)