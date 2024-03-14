package com.binyouwei.appcommunicationassistant.model

import android.content.Intent

/**
 * @author binjx
 * @date 2024/3/14 10:22
 * @purpose：Used for homepage list button
 **/
data class ButtonModel(
    // button name
    val key: Int,
    // Jump address
    val intent: Intent,
)