package com.binyouwei.appcommunicationassistant.serial_port

import android.app.ProgressDialog.show
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import com.binyouwei.appcommunicationassistant.R
import com.binyouwei.appcommunicationassistant.components.MyDialog
import com.binyouwei.appcommunicationassistant.ui.theme.AppCommunicationAssistantTheme
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.f1reking.serialportlib.manager.SerialPortDataManage

/**
 * @author binjx
 * @date 2024/3/14 10:25
 * @purpose：
 **/
@OptIn(ExperimentalMaterial3Api::class)
class SerialPortActivity : ComponentActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, SerialPortActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCommunicationAssistantTheme {
                Column {
                    TopAppBar(title = { Text(text = stringResource(id = R.string.com)) })
                    content()
                }
            }
        }
    }

    @Composable
    @Preview
    private fun content() {
        config()
    }

    @Composable
    @Preview
    private fun config() {
        val items = arrayOf("Item 1", "Item 2", "Item 3")
        var showDialog by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf(items[0]) }
        Column {
            Row(
                modifier = Modifier.height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    Text(text = getString(R.string.serial_port_number) + "：")
                    Text(
                        text = getString(R.string.serial_port_number),
                        modifier = Modifier.clickable {
                            showDialog = true
                            MyDialog.showSingleChoiceDialog(
                                applicationContext,
                                showDialog,
                                "选择发送类型",
                                SerialPortDataManage.serial_port
                            ) {
                                selectedItem = items[it]
                                showDialog = false
                            }
                        })
                }
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    Text(text = getString(R.string.baud) + "：")
                    Text(text = "serial port", modifier = Modifier.clickable {})
                }
            }
            Row(
                modifier = Modifier.height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    Text(text = getString(R.string.data_bit) + "：")
                    Text(text = "serial port", modifier = Modifier.clickable {})
                }
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    Text(text = getString(R.string.check_bit) + "：")
                    Text(text = "serial port", modifier = Modifier.clickable {
                        Toast.makeText(this@SerialPortActivity, "click", Toast.LENGTH_SHORT).show()
                    })
                }
            }
            Row(
                modifier = Modifier.height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    Text(text = getString(R.string.stop_bit) + "：")
                    Text(text = "serial port", modifier = Modifier.clickable {})
                }
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    Text(text = getString(R.string.flow_control) + "：")
                    Text(text = "serial port", modifier = Modifier.clickable {
                        Toast.makeText(this@SerialPortActivity, "click", Toast.LENGTH_SHORT).show()
                    })
                }
            }
            Row(
                modifier = Modifier.height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                    Text(text = getString(R.string.send_type) + "：")
                    Text(text = "serial port", modifier = Modifier.clickable {
                        showDialog = true
                    })
                }
                Row(Modifier.weight(1f)) {}
            }
            Row(
                modifier = Modifier
                    .padding(40.dp, 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(modifier = Modifier.weight(1f), onClick = { }) {
                    Text(text = "开启串口")
                }
            }
        }
    }
}