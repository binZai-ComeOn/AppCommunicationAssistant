package com.binyouwei.appcommunicationassistant

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binyouwei.appcommunicationassistant.bluetooth.BluetoothActivity
import com.binyouwei.appcommunicationassistant.model.ButtonModel
import com.binyouwei.appcommunicationassistant.mqtt.MQTTActivity
import com.binyouwei.appcommunicationassistant.serial_port.SerialPortActivity
import com.binyouwei.appcommunicationassistant.ui.theme.AppCommunicationAssistantTheme
import com.binyouwei.appcommunicationassistant.usb.UsbActivity


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCommunicationAssistantTheme {
                Column {
                    TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
                    displayButtons()
                }
            }
        }
    }

    @Composable
    @Preview
    private fun displayButtons() {
        val buttons = listOf(
            ButtonModel(R.string.usb, UsbActivity.newIntent(this)),
            ButtonModel(R.string.mqtt, MQTTActivity.newIntent(this)),
            ButtonModel(R.string.bluetooth, BluetoothActivity.newIntent(this)),
            ButtonModel(R.string.com, SerialPortActivity.newIntent(this))
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            items(buttons.size) {
                Button(
                    modifier = Modifier
                        .size(200.dp, 80.dp)
                        .padding(10.dp),
                    onClick = { this@MainActivity.startActivity(buttons[it].intent); }) {
                    Text(
                        style = TextStyle(fontSize = 12.sp),
                        text = stringResource(id = buttons[it].key)
                    )
                }
            }
        }
    }
}