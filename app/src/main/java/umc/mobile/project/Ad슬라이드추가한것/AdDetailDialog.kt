package umc.mobile.project.Ad슬라이드추가한것

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import umc.mobile.project.R

class AdDetailDialog  (context : Context){
    private val dlg = Dialog(context)
    private lateinit var btn_close : AppCompatImageButton
    private lateinit var dlg_ad_image : ImageView
    private lateinit var dlg_ad_location : TextView
    private lateinit var dlg_ad_menu : TextView
    private lateinit var dlg_ad_link : TextView
    private lateinit var dlg_ad_request : TextView

    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.ad_detail_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        btn_close = dlg.findViewById(R.id.dlg_ad_btn_close)
        btn_close.setOnClickListener {
            dlg.dismiss()
        }

        dlg_ad_image = dlg.findViewById(R.id.dlg_ad_image)
        dlg_ad_location = dlg.findViewById(R.id.dlg_ad_location)
        dlg_ad_menu = dlg.findViewById(R.id.dlg_ad_menu)
        dlg_ad_link = dlg.findViewById(R.id.dlg_ad_link)
        dlg_ad_request = dlg.findViewById(R.id.dlg_ad_request)

        dlg.show()
    }
}