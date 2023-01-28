package umc.mobile.project.ram.my_application_1

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import umc.mobile.project.R

class ReviewWritePopupDialog(context : Context) {
    private val dlg = Dialog(context)
    private lateinit var btn_close : Button
    private lateinit var btn_sendReview : Button


    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.review_write_dialog)
        dlg.setCanceledOnTouchOutside(true)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드

        btn_close = dlg.findViewById(R.id.close_btn)
        btn_close.setOnClickListener {
            dlg.dismiss()
        }

        // BAD
        val badImage = dlg.findViewById<ImageView>(R.id.sad)
        badImage.setOnClickListener {
            // 색 바꾸고
//            badImage.isSelected = !badImage.isSelected

//            if(badImage.isSelected) {
//
//            } else {
//
//            }

            // 값 저장

        }


        // GOOD
        val goodImage = dlg.findViewById<ImageView>(R.id.good)
        goodImage.setOnClickListener {

        }

        // BEST
        val bestImage = dlg.findViewById<ImageView>(R.id.best)
        bestImage.setOnClickListener {

        }


        // 후기 보내기 클릭
        btn_sendReview = dlg.findViewById(R.id.btn_sendReview)
        btn_sendReview.setOnClickListener {
            // edittext 내용 받고

            // 저장된 값과 edittext 전달

        }

        dlg.show()
    }

}