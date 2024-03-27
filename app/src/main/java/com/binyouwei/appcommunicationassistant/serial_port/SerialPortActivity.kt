package com.binyouwei.appcommunicationassistant.serial_port

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.constraintlayout.compose.ConstraintLayout
import com.binyouwei.appcommunicationassistant.R
import com.binyouwei.appcommunicationassistant.components.DrawerContent
import com.binyouwei.appcommunicationassistant.components.SerialPortValueType
import com.binyouwei.appcommunicationassistant.ui.theme.AppCommunicationAssistantTheme
import kotlinx.coroutines.launch
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

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    @Preview
    private fun config() {
        var bottomSheetItems by remember { mutableStateOf(arrayOf("")) }

        var spType by remember { mutableStateOf(SerialPortValueType.SerialPort) }
        var mSerialPort by remember { mutableStateOf(SerialPortDataManage.serial_port[0]) }
        var mBaudRate by remember { mutableStateOf(SerialPortDataManage.baud_rate[0]) }
        var mStopBits by remember { mutableStateOf(SerialPortDataManage.stop_bits[0]) }
        var mDataBits by remember { mutableStateOf(SerialPortDataManage.data_bits[0]) }
        var mParity by remember { mutableStateOf("") }
        var mFlowCon by remember { mutableStateOf("") }
        var mFlags by remember { mutableStateOf("") }
        var mSendType by remember { mutableStateOf(SerialPortDataManage.send_type[0]) }
        val coroutineScope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        ModalBottomSheetLayout(
            modifier = Modifier.fillMaxSize(),
            sheetState = sheetState,
            sheetBackgroundColor = MaterialTheme.colorScheme.surface,
            sheetContent = {
                DrawerContent(spType, bottomSheetItems) { spType, _, selectedItem ->
                    coroutineScope.launch { sheetState.hide() }
                    when (spType) {
                        SerialPortValueType.SerialPort -> {
                            mSerialPort = selectedItem
                        }

                        SerialPortValueType.BaudRate -> {
                            mBaudRate = selectedItem
                        }

                        SerialPortValueType.StopBits -> {
                            mStopBits = selectedItem
                        }

                        SerialPortValueType.DataBits -> {
                            mDataBits = selectedItem
                        }

                        SerialPortValueType.CheckBit -> {
                            mParity = selectedItem
                        }

                        SerialPortValueType.FlowCon -> {
                            mFlowCon = selectedItem
                        }

                        SerialPortValueType.Flags -> {
                            mFlags = selectedItem
                        }

                        SerialPortValueType.SendType -> {
                            mSendType = selectedItem
                        }
                    }
                }
            }
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                val (bottomContent, contenticon) = createRefs()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .constrainAs(contenticon) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(bottomContent.top)
                        }
                ) {
                    Text(text = stringResource(id = R.string.stop_bit))
                }
                Column(
                    modifier =
                    Modifier.constrainAs(bottomContent) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                    Row(
                        modifier = Modifier
                            .height(50.dp)
                    ) {
                        Row(
                            Modifier
                                .weight(1f)
                                .padding(start = 50.dp)
                        ) {
                            Text(text = getString(R.string.serial_port_number) + "：")
                            Text(
                                text = mSerialPort,
                                modifier = Modifier.clickable {
                                    coroutineScope.launch {
                                        spType = SerialPortValueType.SerialPort
                                        bottomSheetItems = SerialPortDataManage.serial_port
                                        sheetState.show()
                                    }
                                })
                        }
                        Row(
                            Modifier
                                .weight(1f)
                                .padding(start = 50.dp)
                        ) {
                            Text(text = getString(R.string.baud) + "：")
                            Text(text = mBaudRate, modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    spType = SerialPortValueType.BaudRate
                                    bottomSheetItems = SerialPortDataManage.baud_rate
                                    sheetState.show()
                                }
                            })
                        }
                    }
                    Row(
                        modifier = Modifier.height(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            Modifier
                                .weight(1f)
                                .padding(start = 50.dp)
                        ) {
                            Text(text = getString(R.string.data_bit) + "：")
                            Text(text = mDataBits, modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    spType = SerialPortValueType.DataBits
                                    bottomSheetItems = SerialPortDataManage.data_bits
                                    sheetState.show()
                                }
                            })
                        }
                        Row(
                            Modifier
                                .weight(1f)
                                .padding(start = 50.dp)
                        ) {
                            Text(text = getString(R.string.check_bit) + "：")
                            Text(text = mParity, modifier = Modifier.clickable {

                            })
                        }
                    }
                    Row(
                        modifier = Modifier.height(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            Modifier
                                .weight(1f)
                                .padding(start = 50.dp)
                        ) {
                            Text(text = getString(R.string.stop_bit) + "：")
                            Text(text = mStopBits, modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    spType = SerialPortValueType.StopBits
                                    bottomSheetItems = SerialPortDataManage.stop_bits
                                    sheetState.show()
                                }
                            })
                        }
                        Row(
                            Modifier
                                .weight(1f)
                                .padding(start = 50.dp)
                        ) {
                            Text(text = getString(R.string.flow_control) + "：")
                            Text(text = mFlowCon, modifier = Modifier.clickable {

                            })
                        }
                    }
                    Row(
                        modifier = Modifier.height(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            Modifier
                                .weight(1f)
                                .padding(start = 50.dp)
                        ) {
                            Text(text = getString(R.string.send_type) + "：")
                            Text(text = mSendType, modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    spType = SerialPortValueType.SendType
                                    bottomSheetItems = SerialPortDataManage.send_type
                                    sheetState.show()
                                }
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
    }
}