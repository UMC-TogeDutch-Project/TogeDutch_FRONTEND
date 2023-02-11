package umc.mobile.project.ram.my_application_1

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.internal.ContextUtils.getActivity
import umc.mobile.project.R
import umc.mobile.project.ram.my_application_1.current_application.CurrentApplicationActivity

class ReviewWritePopupDialog(context : Context) {
    private val dlg = Dialog(context)
    private lateinit var btn_close : Button
    private lateinit var btn_sendReview : Button

    private lateinit var badImage : ImageView
    private lateinit var goodImage : ImageView
    private lateinit var bestImage : ImageView

    private var isSelected_bad : Boolean = false
    private var isSelected_happy : Boolean = false
    private var isSelected_empty : Boolean = false

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
        badImage = dlg.findViewById<ImageView>(R.id.sad)
        badImage.setOnClickListener {
            
            isSelected_bad = !isSelected_bad
            badImage.isSelected = isSelected_bad


            if(badImage.isSelected){
                // 선택했을 때 처리
            }
            //////// 다른거 선택 취소!!!!!
            goodImage.isSelected = false
            bestImage.isSelected = false
            // 값 저장

        }


        // GOOD
        goodImage = dlg.findViewById<ImageView>(R.id.good)
        goodImage.setOnClickListener {

            isSelected_empty = !isSelected_empty
            goodImage.isSelected = isSelected_empty
            if(goodImage.isSelected){
                // 선택했을 때 처리
            }

            badImage.isSelected = false
            bestImage.isSelected = false
        }

        // BEST
        bestImage = dlg.findViewById<ImageView>(R.id.best)
        bestImage.setOnClickListener {

            isSelected_happy = !isSelected_happy
            bestImage.isSelected = isSelected_happy
            if(bestImage.isSelected){
                // 선택했을 때 처리
            }

            goodImage.isSelected = false
            badImage.isSelected = false

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