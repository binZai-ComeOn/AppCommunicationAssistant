package com.binyouwei.appcommunicationassistant.usb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.binyouwei.appcommunicationassistant.ui.theme.AppCommunicationAssistantTheme

class UsbActivity : ComponentActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, UsbActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCommunicationAssistantTheme {
                Text(text = "usb")
            }
        }
    }
}