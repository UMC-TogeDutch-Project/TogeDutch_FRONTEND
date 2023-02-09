package umc.mobile.project.ram.my_application_1

import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import umc.mobile.project.R

class DeletePostDialog (context : Context) {
    private val dlg = Dialog(context)
    private lateinit var btn_cancel : AppCompatButton
    private lateinit var btn_ok : AppCompatButton

    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.delete_post_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        btn_cancel = dlg.findViewById(R.id.delete_cancel_btn)
        btn_cancel.setOnClickListener {
            dlg.dismiss()
        }

        btn_ok = dlg.findViewById(R.id.delete_ok_btn)
        btn_ok.setOnClickListener {
            isOk = true
            dlg.dismiss()
        }

        dlg.show()
    }



}