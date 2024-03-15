package com.binyouwei.appcommunicationassistant.components

import android.content.Context
import androidx.compose.material3.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs

/**
 * @author binjx
 * @date 2024/3/15 15:03
 * @purpose：
 **/
object MyDialog {

    fun showSingleChoiceDialog(
        context: Context,
        isDialogDisplayed: Boolean,
        title: String,
        items: Array<out CharSequence>,
        selectResult: (Int) -> Unit
    ) {
        if (isDialogDisplayed) {
            /*MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setItems(items) { dialog, index ->
                    selectResult(index)
                }.show()*/
        }
    }
}