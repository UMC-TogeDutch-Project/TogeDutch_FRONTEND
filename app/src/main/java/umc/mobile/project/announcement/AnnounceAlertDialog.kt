package umc.mobile.project.announcement

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import umc.mobile.project.R


class AnnounceAlertDialog (context: Context,announceAlertDialogInterface: AnnounceAlertDialogInterface){
        private val dlg = Dialog(context)
    private var announceAlertDialogInterface: AnnounceAlertDialogInterface? = null
    private lateinit var btn_back : ImageButton
    private lateinit var alert_text: TextView
    init {
        this.announceAlertDialogInterface = announceAlertDialogInterface
    }
    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.announce_alert_dialog)
        dlg.setCanceledOnTouchOutside(false)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드

        btn_back = dlg.findViewById(R.id.back_btn)
        btn_back.setOnClickListener {
            this.announceAlertDialogInterface?.btnFinish()
            dlg.dismiss()

        }

        dlg.show()
    }
    fun start2(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.announce_alert_dialog)
        dlg.setCanceledOnTouchOutside(false)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드

        btn_back = dlg.findViewById(R.id.back_btn)
        alert_text = dlg.findViewById(R.id.tv_alert)
        alert_text.text = "이미 신청한 공고입니다."
        btn_back.setOnClickListener {
            this.announceAlertDialogInterface?.btnFinish()
            dlg.dismiss()

        }

        dlg.show()
    }
    fun start3(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.announce_alert_dialog)
        dlg.setCanceledOnTouchOutside(false)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드
        alert_text = dlg.findViewById(R.id.tv_alert)
        alert_text.text = "내가 업로드한 공고입니다."
        btn_back = dlg.findViewById(R.id.back_btn)
        btn_back.setOnClickListener {
            this.announceAlertDialogInterface?.btnFinish()
            dlg.dismiss()

        }

        dlg.show()
    }
    fun start4(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.announce_alert_dialog)
        dlg.setCanceledOnTouchOutside(false)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드
        alert_text = dlg.findViewById(R.id.tv_alert)
        alert_text.text = "신청 불가능한 공고입니다."
        btn_back = dlg.findViewById(R.id.back_btn)
        btn_back.setOnClickListener {
            this.announceAlertDialogInterface?.btnFinish()
            dlg.dismiss()

        }

        dlg.show()
    }
    fun start5(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.announce_alert_dialog)
        dlg.setCanceledOnTouchOutside(false)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드
        alert_text = dlg.findViewById(R.id.tv_alert)
        alert_text.text = "본인의 공고는 관심 목록에 담을 수 없습니다."
        btn_back = dlg.findViewById(R.id.back_btn)
        btn_back.setOnClickListener {
            this.announceAlertDialogInterface?.btnFinish()
            dlg.dismiss()

        }

        dlg.show()
    }
    fun start6(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.announce_alert_dialog)
        dlg.setCanceledOnTouchOutside(false)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드
        alert_text = dlg.findViewById(R.id.tv_alert)
        alert_text.text = "이미 관심목록에 담은 공고입니다."
        btn_back = dlg.findViewById(R.id.back_btn)
        btn_back.setOnClickListener {
            this.announceAlertDialogInterface?.btnFinish()
            dlg.dismiss()

        }

        dlg.show()
    }
}