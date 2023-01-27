package umc.mobile.project.ram.chat

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import umc.mobile.project.R

class DeclarationPopupDialog(context : Context) {
    private val dlg = Dialog(context)
    private lateinit var btn_close : Button
    private lateinit var btn_send : Button

    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.declaration_popup_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        btn_close = dlg.findViewById(R.id.close_btn)
        btn_close.setOnClickListener {
            dlg.dismiss()
        }

        btn_send = dlg.findViewById(R.id.send_btn)
        btn_send.setOnClickListener {
            // 신고접수 처리
        }

        dlg.show()
    }

}