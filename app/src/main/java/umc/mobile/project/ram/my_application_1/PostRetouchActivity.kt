package umc.mobile.project.ram.my_application_1

import Post
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.mobile.project.*
import umc.mobile.project.announcement.Auth.PostPost.PostRecord
import umc.mobile.project.announcement.PlaceSearchActivity
import umc.mobile.project.databinding.ActivityMyPostDetailBinding
import umc.mobile.project.databinding.ActivityPostRetouchActivityBinding
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.Auth.Post.PUTRetouch.PutRetouchResult
import umc.mobile.project.ram.Auth.Post.PUTRetouch.PutRetouchService
import umc.mobile.project.ram.Auth.Post.PUTRetouch.Request_put
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.chat.chatRoom_selected_subject
import umc.mobile.project.ram.my_application_1.current_application.CurrentApplicationActivity
import java.io.File

class PostRetouchActivity : AppCompatActivity(), PostDetailGetResult, PutRetouchResult {
    lateinit var binding: ActivityPostRetouchActivityBinding

    lateinit var editTextAnnEtPlace : String
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    val SUBACTIITY_REQUEST_CODE = 100
    var picture : MultipartBody.Part? = null
    var picture_pre : MultipartBody.Part? = null
    var post_id_get = 0
    private var PICK_IMAGE = 1
    var category = ""
    var picture_name : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostRetouchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        post_id_get = intent.getIntExtra("post_id", -1)
        println("post_id_get ===== " + post_id_get.toString())

        editTextAnnEtPlace = binding.textLocation.toString()

        if(post_id_get != -1) {
            getPostUpload()
        }else
            Log.d("-1이래 나도 힘들다", "")

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

        binding.imageBtnCamera.setOnClickListener {
            val status = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (status == PackageManager.PERMISSION_GRANTED) {
                // Permission 허용
                getImage()
            } else {
                // Permission 허용

                // 허용 요청
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    100
                )
            }
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


            // 사진 가져오는 부분
            else if (requestCode == PICK_IMAGE) {
                val imagePath = data!!.data

                val file = File(absolutelyPath(imagePath, this))
                val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

//                pictureNameList.addAll(listOf(file.name)) // 데이터 넣는 부분

                Log.d("파일 생성!! ======== ", file.name)
                picture = body
                picture_name = file.name


                setAdjImgUri(imagePath!!)


                Toast.makeText(this, "사진 첨부", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        Log.d("4: 위치정보",  "주소: ${editTextAnnEtPlace.toString()} 위도: $latitude  경도: $longitude")

    }
    fun absolutelyPath(path: Uri?, context: Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    fun getImage() {
        // val intent = Intent("android.intent.action.GET_CONTENT")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*" // 모든 이미지
        startActivityForResult(intent, PICK_IMAGE)
    }

    private fun setAdjImgUri(imgUri: Uri) {

        //2)Resizing 할 BitmapOption 만들기
        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true
            contentResolver.openInputStream(imgUri)?.use { inputStream ->
                //get img dimension
                BitmapFactory.decodeStream(inputStream, null, this)
            }

            // Determine how much to scale down the image
            val targetW: Int = 1000 //in pixel
            val targetH: Int = 1000 //in pixel
            val scaleFactor: Int = Math.min(outWidth / targetW, outHeight / targetH)

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }

        //3) Bitmap 생성 및 셋팅 (resized + rotated)
        contentResolver.openInputStream(imgUri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream, null, bmOptions)?.also { bitmap ->
                val matrix = Matrix()
                matrix.preRotate(0f, 0f, 0f)
                binding.image.setImageBitmap(
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
                )
            }
        }
    }

    private fun getPostUpload(){
        val postDetailGetService = PostDetailGetService()
        postDetailGetService.setPostDetailGetResult(this)
        postDetailGetService.getPostDetail(post_id_get , user_id_var) // 임의로 지정

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
//        picture_pre = result.image


        category = result.category // 카테고리 저장
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
        val latitude = latitude
        val longitude = longitude


        Log.d("order_time 값 ==========================", order_time)

        return Request_put(title, url, delivery_tips, minimum, order_time, num_of_recruits, recruited_num, status, latitude, longitude, category)
    }

    private fun save(){
        val putRetouchService = PutRetouchService()
        putRetouchService.setPutRetouchResult(this)

        if(picture == null){ // 사진 수정 안 했을 때
            var get_picture_save = picture_upload_uri_list.find { it.post_id == post_id_get }!!
            putRetouchService.putRetouch(post_id_get,  user_id_logined, getRequest(), get_picture_save.file) // 이전에 저장한 거 넣기
        }else {
            var same_picture = picture_upload_uri_list.find { it.file_name.equals(picture_name) } // chatRoom 목록에서 받아온 제목이랑 똑같은 공고 찾기
            if (same_picture != null)
                picture_upload_uri_list.add(Picture_Save(post_id_get, picture_name, picture!!)) // 이전에 저장한 사진이랑 다른 거면 리스트에 저장해두기
            putRetouchService.putRetouch(post_id_get, user_id_logined, getRequest(), picture)
        }
    }

    override fun PutRetouchSuccess(result: umc.mobile.project.ram.Auth.Post.PUTRetouch.Result) {
        Log.d("수정완료","" )

        finish()
    }

    override fun PutRetouchFailure() {
       Log.d("수정하기 실패","" )
    }
}



