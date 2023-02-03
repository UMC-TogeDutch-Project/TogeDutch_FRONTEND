package umc.mobile.project.announcement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import Post
import android.content.ContentValues
import com.bumptech.glide.Glide
import umc.mobile.project.announcement.Auth.ApplyPost.ApplyRecordResult
import umc.mobile.project.announcement.Auth.ApplyPost.ApplyRecordService
import umc.mobile.project.announcement.Auth.ApplyPost.Result
import umc.mobile.project.databinding.ActivityAnnounceDetailBinding
import umc.mobile.project.databinding.ActivityMyPostDetailBinding
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.Auth.Post.GetPostUpload.PostUploadGetService
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.current_application.CurrentApplicationActivity
import umc.mobile.project.ram.my_application_1.post_id_to_detail
import umc.mobile.project.ram.my_application_1.user_id_var


class AnnounceDetailActivity:AppCompatActivity() , PostDetailGetResult, ApplyRecordResult{
    lateinit var viewBinding: ActivityMyPostDetailBinding
    lateinit var editTextAnnEtPlace : String
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    val SUBACTIITY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMyPostDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnSeeCurrent.setOnClickListener {
            postApply()
        }
        viewBinding.btnSeeCurrent.text = "신청하기"

        viewBinding.backBtn.setOnClickListener {
            finish()
        }


        getPostUpload()

    }
    private fun postApply(){
        val applyRecordService = ApplyRecordService()
        applyRecordService.setApplyRecordResult(this)
        applyRecordService.sendApply(post_id_to_detail)

    }

    // 입력된 데이터 불러오기
    private fun getPostUpload(){
        val postDetailGetService = PostDetailGetService()
        postDetailGetService.setPostDetailGetResult(this)
        postDetailGetService.getPostDetail(post_id_to_detail , user_id_var) // 임의로 지정

    }

    override fun getPostUploadSuccess(code: Int, result: Post) {
        viewBinding.textTitle.text = result.title
        viewBinding.textUrl.text = result.url
        viewBinding.textDeliveryTip.text = result.delivery_tips.toString() + " 원"
        viewBinding.minimum.text = result.minimum.toString() + " 원"
        val geocoderLocation = Geocoder_location()
        viewBinding.textLocation.text = geocoderLocation.calculate_location(this, result.latitude, result.longitude)

        val txt_time = result.order_time
//            2022-01-23T03:34:56.000+00:00

        var txt_year = txt_time.substring(0 until 4)
        var txt_month = txt_time.substring(5 until 7)
        var txt_day = txt_time.substring(8 until 10)

        var txt_hour = txt_time.substring(11 until 13)
        var txt_minute = txt_time.substring(14 until 17)
        var txt_timestamp_substring = txt_year + "년" + txt_month + "월" + txt_day + "일" + txt_hour+"시" + txt_minute + "분"
        viewBinding.textTime.text = txt_timestamp_substring

        viewBinding.textPeople.text = result.recruited_num.toString() + "명"

        Glide.with(this).load(result.image).into(viewBinding.image)

    }

    override fun getPostUploadFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun applyRecordSuccess(result: Result) {
        Log.d(ContentValues.TAG, "성공")
        finish()
    }

    override fun applyRecordFailure() {
        TODO("Not yet implemented")
    }


}
