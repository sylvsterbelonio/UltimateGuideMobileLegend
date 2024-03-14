package com.networksummit.ultimateguide_mobilelegend.ui.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

class CustomDialogBox {
    var onClickEvent = fun(_: Response): Response = Response.None

    enum class DialogStyle { Simple, YesOrNo }
    enum class Response { Yes, No,  None }
    private lateinit var mDialog: androidx.appcompat.app.AlertDialog
    private lateinit var builder: AlertDialog.Builder
    private var response = false


    fun setDialog(context: Context, title: String, message: String, style: DialogStyle=DialogStyle.Simple)
    {
        response = false
            builder = AlertDialog.Builder(context)
            with(builder)
                {
                    setTitle(title)
                    setMessage(message)
                    setCancelable(true)
                }


            when(style)
            {
                DialogStyle.YesOrNo ->
                {
                    builder.setPositiveButton(android.R.string.yes) { dialog, _ ->
                        onClickEvent(Response.Yes)
                        dialog?.dismiss()
                    }
                    builder.setNegativeButton(android.R.string.no) { dialog, _ ->
                        onClickEvent(Response.No)
                        dialog?.dismiss()
                    }

                }
                else -> print("default")

            }

        mDialog = builder.create()

    }

    fun show() {
        mDialog.show()
    }

}