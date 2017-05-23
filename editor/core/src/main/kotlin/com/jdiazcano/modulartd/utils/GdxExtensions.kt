package com.jdiazcano.modulartd.utils

import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.kotcrab.vis.ui.util.dialog.Dialogs

/**
 * Shows an error dialog with the text and details given. This will post the dialog to the bus so it will be shown in
 * the screen
 */
fun showErrorDialog(text: String, details: String): Dialogs.DetailsDialog {
    val dialog = createErrorDialog(text, details)
    Bus.post(dialog, BusTopic.NEW_DIALOG)
    return dialog
}

/**
 * Creates an error dialog with a translated Error!
 */
fun createErrorDialog(text: String, details: String): Dialogs.DetailsDialog {
    return Dialogs.DetailsDialog(text, translate("error", "Error"), details)
}