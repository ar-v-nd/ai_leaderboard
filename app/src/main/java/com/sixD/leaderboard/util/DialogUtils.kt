package com.sixD.leaderboard.util

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Utility object for displaying standardized Material Design dialogs throughout the app.
 */
object DialogUtils {

    /**
     * Displays a customizable two-button Material dialog.
     *
     * @param context The UI context to show the dialog in.
     * @param title The title text of the dialog.
     * @param message The main body text of the dialog.
     * @param positiveButtonText Text for the positive (right-side) action button. Defaults to "Retry".
     * @param negativeButtonText Text for the negative (left-side) action button. Defaults to "Close".
     * @param onPositiveButtonClick Callback invoked when the positive button is clicked.
     * @param onNegativeButtonClick Callback invoked when the negative button is clicked.
     */
    fun showTwoButtonDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = "Retry",
        negativeButtonText: String = "Close",
        onPositiveButtonClick: () -> Unit,
        onNegativeButtonClick: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                onPositiveButtonClick()
                dialog.dismiss()
            }
            .setNegativeButton(negativeButtonText) { dialog, _ ->
                onNegativeButtonClick()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    /**
     * Shows a specialized dialog for network connectivity errors.
     *
     * @param context The UI context.
     * @param onRetry Callback for the "Retry" action.
     * @param onClose Callback for the "Close" action.
     */
    fun showNoInternetDialog(context: Context, onRetry: () -> Unit, onClose: () -> Unit) {
        showTwoButtonDialog(
            context = context,
            title = "No Internet Connection",
            message = "Please check your internet connection and try again.",
            positiveButtonText = "Retry",
            negativeButtonText = "Close",
            onPositiveButtonClick = onRetry,
            onNegativeButtonClick = onClose
        )
    }

    /**
     * Shows a specialized dialog for API-related errors.
     *
     * @param context The UI context.
     * @param errorMessage The detailed error message from the API or system.
     * @param onRetry Callback for the "Retry" action.
     * @param onClose Callback for the "Close" action.
     */
    fun showApiErrorDialog(context: Context, errorMessage: String, onRetry: () -> Unit, onClose: () -> Unit) {
        showTwoButtonDialog(
            context = context,
            title = "API Error",
            message = errorMessage,
            positiveButtonText = "Retry",
            negativeButtonText = "Close",
            onPositiveButtonClick = onRetry,
            onNegativeButtonClick = onClose
        )
    }
}
