package com.mcal.uidesigner.ui.model

import androidx.compose.ui.graphics.vector.ImageVector

enum class WidgetType {
    Button,
    ElevatedButton,
    FilledTonalButton,
    OutlinedButton,
    TextButton,
    IconButton,
    FloatingActionButton,
    ExtendedFloatingActionButton,
    TextField,
    OutlinedTextField,
    Checkbox,
    RadioButton,
    Switch,
    Slider,
    CircularProgressIndicator,
    LinearProgressIndicator,
    Card,
    ElevatedCard,
    OutlinedCard,
    Box,
    Column,
    Row,
    LazyColumn,
    LazyRow,
    NavigationBar,
    NavigationRail,
    ModalNavigationDrawer,
    BottomAppBar,
    TopAppBar,
    Text,
    Icon,
    Image,
    Divider
}

data class ComposeWidget(
    val name: String,
    val category: String,
    val type: WidgetType,
    val attributes: Map<String, String> = emptyMap(),
    val icon: ImageVector? = null
)