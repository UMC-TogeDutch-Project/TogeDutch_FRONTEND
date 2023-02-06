package umc.mobile.project.ram.my_application_1


import Post
import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import umc.mobile.project.R
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.Geocoder_location

class ParticipatePopupDialog(context : Context) : PostDetailGetResult {
    private val dlg = Dialog(context)
    private lateinit var btn_close : Button

    lateinit var textTitle: TextView
    lateinit var textUrl: TextView
    lateinit var textDeliveryTip: TextView
    lateinit var minimum: TextView
    lateinit var textLocation: TextView
    lateinit var textTime: TextView
    lateinit var textPeople: TextView
    lateinit var imageView: ImageView

    var context_ = context

    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.participate_popup_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        getPostUpload()

        btn_close = dlg.findViewById(R.id.close_btn)
        btn_close.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    private fun getPostUpload(){
        val postDetailGetService = PostDetailGetService()
        postDetailGetService.setPostDetailGetResult(this)
        postDetailGetService.getPostDetail(post_id_to_detail , user_id_var) // 임의로 지정

    }

    override fun getPostUploadSuccess(code: Int, result: Post) {


        textTitle = dlg.findViewById(R.id.post_title_txt_participate)
        textTitle.text = result.title

        textUrl = dlg.findViewById(R.id.shop_info_txt_participate)
        textUrl.text = result.url

        textDeliveryTip = dlg.findViewById(R.id.deliver_tip_txt_participate)
        textDeliveryTip.text = result.delivery_tips.toString() + " 원"

        minimum = dlg.findViewById(R.id.minimum_txt_participate)
        minimum.text = result.minimum.toString() + " 원"

        val geocoderLocation = Geocoder_location()
        textLocation = dlg.findViewById(R.id.direction_txt_participate)
        textLocation.text = geocoderLocation.calculate_location(context_, result.latitude, result.longitude)


        val txt_time = result.order_time
//            2022-01-23T03:34:56.000+00:00

        var txt_year = txt_time.substring(0 until 4)
        var txt_month = txt_time.substring(5 until 7)
        var txt_day = txt_time.substring(8 until 10)

        var txt_hour = txt_time.substring(11 until 13)
        var txt_minute = txt_time.substring(14 until 17)
        var txt_timestamp_substring = txt_year + "년" + txt_month + "월" + txt_day + "일" + txt_hour+"시" + txt_minute + "분"
        textTime = dlg.findViewById(R.id.time_txt_participate)
        textTime.text = txt_timestamp_substring

        textPeople = dlg.findViewById(R.id.people_txt_participate)
        textPeople.text = result.recruited_num.toString() + "명"
//
//        imageView = dlg.findViewById(R.id.image_participate)
//        Glide.with(context_).load(result.image).into(imageView)
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }


}