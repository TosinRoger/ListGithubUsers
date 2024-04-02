package br.com.tosin.listgithubusers.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import br.com.tosin.listgithubusers.R
import br.com.tosin.listgithubusers.ui.utils.onCancelDialog
import br.com.tosin.listgithubusers.ui.utils.onClickedOnPositive

class InformationAlertDialog(
    private val title: String? = null,
    private val msg: CharSequence,
    private val positiveButtonText: String? = null,
    private val delegateConfirmation: onClickedOnPositive? = null,
    private val delegateCancel: onCancelDialog? = null
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dialog = AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positiveButtonText ?: getString(R.string.ok)) { _, _ ->
                    delegateConfirmation?.let {
                        it("")
                    }
                }

            dialog.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCancel(dialog: DialogInterface) {
        delegateCancel?.invoke() ?: {
            super.onCancel(dialog)
        }
    }
}
