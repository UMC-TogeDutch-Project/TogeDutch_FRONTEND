package umc.mobile.project.ram.my_application_1

import Post
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
import umc.mobile.project.R
import umc.mobile.project.announcement.Auth.PostPost.PostRecord
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

class PostRetouchActivity : AppCompatActivity(), PostDetailGetResult, PutRetouchResult {
    lateinit var binding: ActivityPostRetouchActivityBinding

    private var editText1: EditText? = null
    private var editText2: EditText? = null
    private var editText3: EditText? = null
    private var editText4: EditText? = null
    private var editText5: EditText? = null
    private var editText6: EditText? = null
    private var editText7: EditText? = null
    private var editText8: EditText? = null
    private var editText9: EditText? = null
    private var editText10: EditText? = null
    private var editText11: EditText? = null
    private var editText12: EditText? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostRetouchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        editText1 = binding.textTitle
        editText2 = binding.textUrl
        editText3 = binding.textDeliveryTip
        editText4 = binding.minimum
        editText5 = binding.textLocation
        editText6 = binding.txtYear
        editText7 = binding.txtMonth
        editText8 = binding.txtDay
        editText9 = binding.txtTime
        editText10 = binding.txtHour
        editText11 = binding.txtMinute
        editText12 = binding.textPeople


        binding.btnSave.setOnClickListener {
            save()
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        getPostUpload()


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

            if (editText1?.text.toString().isNotEmpty() && editText2?.text.toString().isNotEmpty() && editText3?.text.toString().isNotEmpty()
                && editText4?.text.toString().isNotEmpty() && editText5?.text.toString().isNotEmpty() && editText6?.text.toString().isNotEmpty()
                && editText7?.text.toString().isNotEmpty()&& editText8?.text.toString().isNotEmpty()&& editText9?.text.toString().isNotEmpty()
                && editText10?.text.toString().isNotEmpty()&& editText11?.text.toString().isNotEmpty()&& editText12?.text.toString().isNotEmpty())
            {
                binding.btnSave.isClickable =  true
                binding.btnSave.backgroundTintList = ColorStateList.valueOf(color)

            } else  {
                binding.btnSave.isClickable = false
                binding.btnSave.backgroundTintList = ColorStateList.valueOf(color2)


            }
        }
    }

    private fun getPostUpload(){
        val postDetailGetService = PostDetailGetService()
        postDetailGetService.setPostDetailGetResult(this)
        postDetailGetService.getPostDetail(post_id_to_detail , user_id_var) // 임의로 지정

    }

    override fun getPostUploadSuccess(code: Int, result: Post) {
        binding.textTitle.text = Editable.Factory.getInstance().newEditable(result.title)
        binding.textUrl.text = Editable.Factory.getInstance().newEditable(result.url)
        binding.textDeliveryTip.text = Editable.Factory.getInstance().newEditable(result.delivery_tips.toString() + " 원")
        binding.minimum.text = Editable.Factory.getInstance().newEditable(result.minimum.toString() + " 원")
        val geocoderLocation = Geocoder_location()
        binding.textLocation.text = Editable.Factory.getInstance().newEditable(geocoderLocation.calculate_location(this, result.latitude, result.longitude))

        val txt_time = result.order_time
//            2022-01-23T03:34:56.000+00:00

        var txt_year = txt_time.substring(0 until 4)
        var txt_month = txt_time.substring(5 until 7)
        var txt_day = txt_time.substring(8 until 10)

        var txt_hour = txt_time.substring(11 until 13)
        var txt_minute = txt_time.substring(14 until 17)

        binding.txtYear.text = Editable.Factory.getInstance().newEditable(txt_year)
        binding.txtMonth.text = Editable.Factory.getInstance().newEditable(txt_month)
        binding.txtDay.text = Editable.Factory.getInstance().newEditable(txt_day)
        binding.txtHour.text = Editable.Factory.getInstance().newEditable(txt_hour)
        binding.txtMinute.text = Editable.Factory.getInstance().newEditable(txt_minute)

        binding.textPeople.text = Editable.Factory.getInstance().newEditable(result.recruited_num.toString() + "명")

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
        val title : String = editText1?.text.toString()
        val url = editText2?.text.toString()
        val delivery_tips = editText3?.text.toString().toInt()
        val minimum = editText4?.text.toString().toInt()

        var order_time = string_to_timestamp(editText6!!.text.toString(),editText7!!.text.toString(), editText8!!.text.toString(), editText9!!.text.toString()
            ,editText10!!.text.toString(), editText11!!.text.toString())
        val num_of_recruits = editText7?.text.toString().toInt()
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



