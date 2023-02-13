package umc.mobile.project.ram.chat

import Post
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import umc.mobile.project.R
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.Geocoder_location

class PostDetailPopupDialog (context : Context) : PostDetailGetResult {
    private val dlg = Dialog(context)
    private lateinit var btn_close : Button

    private lateinit var dlg_post_title_txt : TextView
    private lateinit var dlg_shop_info_txt : TextView
    private lateinit var dlg_deliver_tip_txt : TextView
    private lateinit var dlg_minimum_txt : TextView
    private lateinit var dlg_direction_txt : TextView
    private lateinit var dlg_time_txt : TextView
    private lateinit var dlg_people_txt : TextView

    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.post_detail_popup_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        btn_close = dlg.findViewById(R.id.close_btn)
        btn_close.setOnClickListener {
            dlg.dismiss()
        }

        val postDetailGetService = PostDetailGetService()
        postDetailGetService.setPostDetailGetResult(this)
        postDetailGetService.getPostDetail(post_id_dialog, user_id_dialog)


        dlg.show()
    }

    override fun getPostUploadSuccess(code: Int, result: Post) {
        dlg_post_title_txt = dlg.findViewById(R.id.dlg_post_title_txt)
        dlg_post_title_txt.text = result.title

        dlg_shop_info_txt = dlg.findViewById(R.id.dlg_shop_info_txt)
        dlg_shop_info_txt.text = result.url

        dlg_deliver_tip_txt = dlg.findViewById(R.id.dlg_deliver_tip_txt)
        dlg_deliver_tip_txt.text = result.delivery_tips.toString()

        dlg_minimum_txt = dlg.findViewById(R.id.dlg_minimum_txt)
        dlg_minimum_txt.text = result.minimum.toString()

        // 위도경도
        dlg_direction_txt = dlg.findViewById(R.id.dlg_direction_txt)
        dlg_direction_txt.text = location_dialog

        // 시간변환
        val txt_time = result.order_time
        var txt_year = txt_time.substring(0 until 4)
        var txt_month = txt_time.substring(5 until 7)
        var txt_day = txt_time.substring(8 until 10)
        var txt_hour = txt_time.substring(11 until 13)
        var txt_minute = txt_time.substring(14 until 16)
        var txt_timestamp_substring = txt_year + "년 " + txt_month + "월 " + txt_day + "일 " + txt_hour+"시 " + txt_minute + "분"
        dlg_time_txt = dlg.findViewById(R.id.dlg_time_txt)
        dlg_time_txt.text = txt_timestamp_substring

        dlg_people_txt = dlg.findViewById(R.id.dlg_people_txt)
        dlg_people_txt.text = result.num_of_recruits.toString()

    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Log.d("dialg post 상세 가져오기 실패", "")
    }

}