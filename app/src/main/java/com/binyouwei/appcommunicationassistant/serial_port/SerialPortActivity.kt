package com.binyouwei.appcommunicationassistant.serial_port

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.binyouwei.appcommunicationassistant.ui.theme.AppCommunicationAssistantTheme

/**
 * @author binjx
 * @date 2024/3/14 10:25
 * @purpose：
 **/
class SerialPortActivity : ComponentActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, SerialPortActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCommunicationAssistantTheme {
                Text(text = "SerialPort")
            }
        }
    }
}