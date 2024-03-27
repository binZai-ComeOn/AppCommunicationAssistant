package com.binyouwei.appcommunicationassistant.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

/**
 * @author binjx
 * @date 2024/3/15 15:03
 * @purpose：
 **/

enum class SerialPortValueType {
    SerialPort,
    BaudRate,
    StopBits,
    DataBits,
    CheckBit,
    FlowCon,
    Flags,
    SendType
}

@Composable
fun DrawerContent(
    type: SerialPortValueType,
    bottomSheetItems: Array<String>,
    onClick: (SerialPortValueType, Int, String) -> Unit,
) {
    LazyColumn(content = {
        items(bottomSheetItems.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        onClick(type, index, bottomSheetItems[index])
                    }, horizontalArrangement = Arrangement
                    .SpaceBetween
            ) {
                Text(text = bottomSheetItems[index])
            }
        }
    })
}