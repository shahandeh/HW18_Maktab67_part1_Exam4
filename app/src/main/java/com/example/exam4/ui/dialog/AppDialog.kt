package com.example.exam4.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.exam4.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AppDialog(
    private val dialogResult: (firstName: String, lastName: String, nationalCode: String) -> Unit
) : DialogFragment() {

    private lateinit var dialogView: View
    lateinit var firstName: TextInputEditText
    lateinit var lastName: TextInputEditText
    lateinit var nationalCode: TextInputEditText
    lateinit var button: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogView = inflater.inflate(R.layout.app_dialog, container, false)

        viewInit()

        button.setOnClickListener {
                dialogResult(
                    firstName.text.toString(),
                    lastName.text.toString(),
                    nationalCode.text.toString()
                )
                dismiss()
            }

        return dialogView
    }

    private fun viewInit() {
        firstName = dialogView.findViewById(R.id.first_name_edit_user_dialog)
        lastName = dialogView.findViewById(R.id.last_name_edit_user_dialog)
        nationalCode = dialogView.findViewById(R.id.national_code_edit_user_dialog)
        button = dialogView.findViewById(R.id.create_user_edit_user_dialog)
    }

}