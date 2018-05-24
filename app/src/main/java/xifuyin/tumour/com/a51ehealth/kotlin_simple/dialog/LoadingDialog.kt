package xifuyin.tumour.com.a51ehealth.kotlin_simple.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import xifuyin.tumour.com.a51ehealth.kotlin_simple.R


class LoadingDialog(context: Context) : Dialog(context, R.style.LoadingDialogTheme) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.commom_loading_layout)

    }


}

