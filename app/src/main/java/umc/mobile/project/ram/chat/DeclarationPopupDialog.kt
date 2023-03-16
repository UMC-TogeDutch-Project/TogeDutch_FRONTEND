package umc.mobile.project.ram.chat

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import umc.mobile.project.R
import umc.mobile.project.ram.Auth.Declaration.DeclarationPost.PostDeclarationResult
import umc.mobile.project.ram.Auth.Declaration.DeclarationPost.PostDeclarationService
import umc.mobile.project.ram.Auth.Declaration.DeclarationPost.Result

class DeclarationPopupDialog(context : Context)  {
    private val dlg = Dialog(context)
    private lateinit var btn_close : Button
    private lateinit var btn_send : Button
    lateinit var declaration_reason_edittext : EditText
    private val context = context

    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.declaration_popup_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        declaration_reason_edittext = dlg.findViewById(R.id.declaration_reason_txt)

        btn_close = dlg.findViewById(R.id.close_btn)
        btn_close.setOnClickListener {
            dlg.dismiss()
        }

        btn_send = dlg.findViewById(R.id.send_btn)
        btn_send.setOnClickListener {
            onClickListener.onClicked(declaration_reason_edittext.text.toString())
            dlg.dismiss()
        }

        dlg.show()
    }

    interface ButtonClickListener{
        fun onClicked(text: String)
    }

    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickListener(listener: ButtonClickListener){
        onClickListener = listener
    }
}