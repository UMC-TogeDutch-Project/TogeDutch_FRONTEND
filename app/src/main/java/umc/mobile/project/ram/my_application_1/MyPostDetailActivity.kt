package umc.mobile.project.ram.my_application_1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import Post
import umc.mobile.project.databinding.ActivityMyPostDetailBinding
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.current_application.CurrentApplicationActivity

class MyPostDetailActivity : AppCompatActivity(), PostDetailGetResult {
    lateinit var binding: ActivityMyPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSeeCurrent.setOnClickListener {
            val intent = Intent(this, CurrentApplicationActivity::class.java)
            startActivity(intent)
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        getPostUpload()
    }

    private fun getPostUpload(){
        val postDetailGetService = PostDetailGetService()
        postDetailGetService.setPostDetailGetResult(this)
        postDetailGetService.getPostDetail(post_id_to_detail , user_id_var) // 임의로 지정
    }

    override fun getPostUploadSuccess(code: Int, result: Post) {
        binding.textTitle.text = result.title
        binding.textUrl.text = result.url
        binding.textDeliveryTip.text = result.delivery_tips.toString() + " 원"
        binding.minimum.text = result.minimum.toString() + " 원"
        val geocoderLocation = Geocoder_location()
        binding.textLocation.text = geocoderLocation.calculate_location(this, result.latitude, result.longitude)

        val txt_time = result.order_time
//            2022-01-23T03:34:56.000+00:00
        var txt_year = txt_time.substring(0 until 4)
        var txt_month = txt_time.substring(5 until 7)
        var txt_day = txt_time.substring(8 until 10)
        var txt_hour = txt_time.substring(11 until 13)
        var txt_minute = txt_time.substring(14 until 17)
        var txt_timestamp_substring = txt_year + "년" + txt_month + "월" + txt_day + "일" + txt_hour+"시" + txt_minute + "분"
        binding.textTime.text = txt_timestamp_substring

        binding.textPeople.text = result.recruited_num.toString() + "명"



        Glide.with(this).load(result.image).into(binding.image)

    }

    override fun getPostUploadFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }
}

