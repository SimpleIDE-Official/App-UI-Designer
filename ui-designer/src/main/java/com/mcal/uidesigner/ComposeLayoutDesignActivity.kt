package com.mcal.uidesigner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mcal.uidesigner.ui.MainLayout
import com.mcal.uidesigner.ui.UIDesignerTheme

class ComposeLayoutDesignActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UIDesignerTheme {
                MainLayout()
            }
        }
    }
}