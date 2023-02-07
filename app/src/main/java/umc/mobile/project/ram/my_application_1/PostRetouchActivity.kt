package umc.mobile.project.ram.my_application_1

import Post
import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.mobile.project.R
import umc.mobile.project.announcement.Auth.PostPost.PostRecord
import umc.mobile.project.announcement.PlaceSearchActivity
import umc.mobile.project.databinding.ActivityMyPostDetailBinding
import umc.mobile.project.databinding.ActivityPostRetouchActivityBinding
import umc.mobile.project.latitude_var
import umc.mobile.project.longtitude_var
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.Auth.Post.PUTRetouch.PutRetouchResult
import umc.mobile.project.ram.Auth.Post.PUTRetouch.PutRetouchService
import umc.mobile.project.ram.Auth.Post.PUTRetouch.Request_put
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.current_application.CurrentApplicationActivity
import java.io.File

class PostRetouchActivity : AppCompatActivity(), PostDetailGetResult, PutRetouchResult {
    lateinit var binding: ActivityPostRetouchActivityBinding

    lateinit var editTextAnnEtPlace : String
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    val SUBACTIITY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostRetouchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPostUpload()

        binding.btnSave.setOnClickListener {
            save()
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.imageBtnMap.setOnClickListener {
            val intent = Intent(this@PostRetouchActivity, PlaceSearchActivity::class.java)
            startActivityForResult(intent, SUBACTIITY_REQUEST_CODE)
        }

        binding.textTitle.addTextChangedListener(textWatcher)
        binding.textUrl.addTextChangedListener(textWatcher)
        binding.textDeliveryTip.addTextChangedListener(textWatcher)
        binding.minimum.addTextChangedListener(textWatcher)
        binding.textLocation.addTextChangedListener(textWatcher)
        binding.txtYear.addTextChangedListener(textWatcher)
        binding.txtMonth.addTextChangedListener(textWatcher)
        binding.txtDay.addTextChangedListener(textWatcher)
        binding.txtTime.addTextChangedListener(textWatcher)
        binding.txtHour.addTextChangedListener(textWatcher)
        binding.txtMinute.addTextChangedListener(textWatcher)
        binding.textPeople.addTextChangedListener(textWatcher)

    }

    //editText 내용 입력시 버튼 활성화
    val textWatcher = object : TextWatcher {

        override
        fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2:Int) {

        }

        override
        fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override
        fun afterTextChanged(editable: Editable?) {

            val color = getColor(R.color.main_color)
            val color2 = getColor(R.color.grey_3)

            if (binding.textTitle.text.toString().isNotEmpty() && binding.textUrl.text.toString().isNotEmpty() && binding.textDeliveryTip.text.toString().isNotEmpty()
                && binding.minimum.text.toString().isNotEmpty() && binding.textLocation.text.toString().isNotEmpty() && binding.txtYear.text.toString().isNotEmpty()
                && binding.txtMonth.text.toString().isNotEmpty()&& binding.txtDay.text.toString().isNotEmpty()&& binding.txtTime.text.toString().isNotEmpty()
                && binding.txtHour.text.toString().isNotEmpty()&& binding.txtMinute?.text.toString().isNotEmpty()&& binding.textPeople.text.toString().isNotEmpty())
            {
                binding.btnSave.isClickable =  true
                binding.btnSave.backgroundTintList = ColorStateList.valueOf(color)

            } else  {
                binding.btnSave.isClickable = false
                binding.btnSave.backgroundTintList = ColorStateList.valueOf(color2)


            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 돌려받은 resultCode가 정상인지 체크
        if(resultCode == Activity.RESULT_OK){

            if(requestCode == SUBACTIITY_REQUEST_CODE) {
                Log.d("log: ", "log 찍힘")
                if (data != null) {
                    editTextAnnEtPlace = data.getStringExtra("address").toString()
                    binding.textLocation.setText(data.getStringExtra("address"))
                    latitude = data.getDoubleExtra("latitude", 0.0)
                    longitude = data.getDoubleExtra("longitude", 0.0)
                }
            }
        }
        Log.d("4: 위치정보",  "주소: ${editTextAnnEtPlace.toString()} 위도: $latitude  경도: $longitude")
    }

    private fun getPostUpload(){
        val postDetailGetService = PostDetailGetService()
        postDetailGetService.setPostDetailGetResult(this)
        postDetailGetService.getPostDetail(post_id_to_detail , user_id_var) // 임의로 지정

    }

    override fun getPostUploadSuccess(code: Int, result: Post) {
        binding.textTitle.text = Editable.Factory.getInstance().newEditable(result.title)
        binding.textUrl.text = Editable.Factory.getInstance().newEditable(result.url)
        binding.textDeliveryTip.text = Editable.Factory.getInstance().newEditable(result.delivery_tips.toString())
        binding.minimum.text = Editable.Factory.getInstance().newEditable(result.minimum.toString())
        val geocoderLocation = Geocoder_location()
        binding.textLocation.text = Editable.Factory.getInstance().newEditable(geocoderLocation.calculate_location(this, result.latitude, result.longitude))

        val txt_time = result.order_time
//            2022-01-23T03:34:56.000+00:00

        var txt_year = txt_time.substring(0 until 4)
        var txt_month = txt_time.substring(5 until 7)
        var txt_day = txt_time.substring(8 until 10)

        var txt_hour = txt_time.substring(11 until 13)
        var txt_minute = txt_time.substring(14 until 16)

        if(txt_hour.toInt() > 12) {
            txt_hour = (txt_hour.toInt() - 12).toString() // 오후 시간대면 이렇게
            binding.txtTime.text = Editable.Factory.getInstance().newEditable("오후")
        }

        binding.txtYear.text = Editable.Factory.getInstance().newEditable(txt_year)
        binding.txtMonth.text = Editable.Factory.getInstance().newEditable(txt_month)
        binding.txtDay.text = Editable.Factory.getInstance().newEditable(txt_day)
        binding.txtHour.text = Editable.Factory.getInstance().newEditable(txt_hour)
        binding.txtMinute.text = Editable.Factory.getInstance().newEditable(txt_minute)

        binding.textPeople.text = Editable.Factory.getInstance().newEditable(result.recruited_num.toString())

        Glide.with(this).load(result.image).into(binding.image)

    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Log.d("getPostUpload 실패", message)
    }

    private fun string_to_timestamp(year :String, month: String, day : String, am_pm : String, hour : String, minute : String) : String{
        var hour_int = 0

        // 01, 02 이런 식으로 들어왔을 때
        if(hour.substring(0).equals("0")){
            // 1의 자리만 substring
            hour_int = hour.substring(1).toInt()
            if(am_pm.equals("오후") && hour.toInt() != 12){
                hour_int = hour.toInt() + 12
            }
            else{
                hour_int = hour.toInt()
            }
        }else{
            if(am_pm.equals("오후") && hour.toInt() != 12){
                hour_int = hour.toInt() + 12
            }
            else{
                hour_int = hour.toInt()
            }
        }

        var set = "2022-01-23T03:34:56.000+00:00"
        var order_time = year + "-" + month + "-" + day + "T" + hour_int + ":" + minute + ":" + "00.000+00:00"

        return order_time
    }

    private fun getRequest() : Request_put {
        val title : String = binding.textTitle.text.toString()
        val url = binding.textUrl.text.toString()
        val delivery_tips = binding.textDeliveryTip.text.toString().toInt()
        val minimum = binding.minimum.text.toString().toInt()

        var order_time = string_to_timestamp(binding.txtYear!!.text.toString(),binding.txtMonth!!.text.toString(), binding.txtDay!!.text.toString(), binding.txtTime.text.toString()
            ,binding.txtHour!!.text.toString(), binding.txtMinute!!.text.toString())
        val num_of_recruits = binding.textPeople.text.toString().toInt()
        val recruited_num = 0
        val status = "모집중"
        val latitude = latitude_var
        val longitude = longtitude_var

        Log.d("order_time 값 ==========================", order_time)

        return Request_put(title, url, delivery_tips, minimum, order_time, num_of_recruits, recruited_num, status, latitude, longitude)
    }

    private fun save(){
        val putRetouchService = PutRetouchService()
        putRetouchService.setPutRetouchResult(this)
        putRetouchService.putRetouch(post_id_to_detail, user_id_logined, getRequest()) // 임의로 지정
    }

    override fun PutRetouchSuccess(result: Post) {
        Log.d("수정완료","" )
        finish()
    }

    override fun PutRetouchFailure() {
       Log.d("수정하기 실패","" )
    }
}



