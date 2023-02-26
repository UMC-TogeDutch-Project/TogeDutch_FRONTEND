package umc.mobile.project

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
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.mobile.project.announcement.Auth.PostPost.PostRecord
import umc.mobile.project.announcement.Auth.PostPost.PostRecordResult
import umc.mobile.project.announcement.Auth.PostPost.PostRecordService
import umc.mobile.project.announcement.Auth.PostPost.Result
import umc.mobile.project.announcement.PlaceSearchActivity
import umc.mobile.project.databinding.ActivityAnnouncePostBinding
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.ram.my_application_1.user_id_var
import java.io.File
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

var latitude_var : Double = 1.0
var longtitude_var : Double = 1.0
var picture_upload_uri_list = ArrayList<Picture_Save>()

class AnnouncePostActivity : AppCompatActivity(), PostRecordResult {
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
    private var editText13: EditText? = null
    private var button: Button? = null

    private var PICK_IMAGE = 1

    lateinit var editTextAnnEtPlace : String
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    val SUBACTIITY_REQUEST_CODE = 100
    var picture : MultipartBody.Part? = null
    var picture_name : String = ""

    private val viewBinding: ActivityAnnouncePostBinding by lazy {
        ActivityAnnouncePostBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        editText1 = viewBinding.annEtTitle
        editText2 = viewBinding.annEtStore
        editText3 = viewBinding.annEtTip
        editText4 = viewBinding.annEtMinimum
        editText5 = viewBinding.annEtPlace
        editText6 = viewBinding.annEtYear
        editText7 = viewBinding.annEtMonth
        editText8 = viewBinding.annEtDay
        editText9 = viewBinding.annEtTime
        editText10 = viewBinding.annEtHour
        editText11 = viewBinding.annEtMinute
        editText12 = viewBinding.annEtPerson
        editText13 = viewBinding.annEtCategory
        button = viewBinding.btnPost

        editText1!!.addTextChangedListener(textWatcher)
        editText2!!.addTextChangedListener(textWatcher)
        editText3!!.addTextChangedListener(textWatcher)
        editText4!!.addTextChangedListener(textWatcher)
        editText5!!.addTextChangedListener(textWatcher)
        editText6!!.addTextChangedListener(textWatcher)
        editText7!!.addTextChangedListener(textWatcher)
        editText8!!.addTextChangedListener(textWatcher)
        editText9!!.addTextChangedListener(textWatcher)
        editText10!!.addTextChangedListener(textWatcher)
        editText11!!.addTextChangedListener(textWatcher)
        editText12!!.addTextChangedListener(textWatcher)
        editText13!!.addTextChangedListener(textWatcher)


        editTextAnnEtPlace = viewBinding.annEtPlace.toString()

        viewBinding.btnPost.setOnClickListener{
            finish()
        }
        viewBinding.backBtn.setOnClickListener{
            finish()
        }
        viewBinding.imageBtnMap.setOnClickListener {
            val intent = Intent(this@AnnouncePostActivity, PlaceSearchActivity::class.java)
            startActivityForResult(intent, SUBACTIITY_REQUEST_CODE)
        }

        viewBinding.imageBtnCamera.setOnClickListener {
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

        viewBinding.btnPost.setOnClickListener {
            save()
        }

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
                && editText10?.text.toString().isNotEmpty()&& editText11?.text.toString().isNotEmpty()&& editText12?.text.toString().isNotEmpty()
                && editText13?.text.toString().isNotEmpty())
            {
                button?.isClickable =  true
                button?.backgroundTintList = ColorStateList.valueOf(color)
//                Toast.makeText(applicationContext, "활성화", Toast.LENGTH_SHORT)
//                    .show()

            } else  {
                button?.isClickable = false
                button?.backgroundTintList = ColorStateList.valueOf(color2)


            }
        }
    }

    ////// 전송부분 ////////////////
    // 데이터 담아주기
    private fun getPostRecord() : PostRecord{
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
        val category : String = editText13?.text.toString()

        Log.d("order_time 값 ==========================", order_time)

        return PostRecord(title, url, delivery_tips, minimum, order_time, num_of_recruits, recruited_num, status, latitude, longitude, category)
    }

    private fun save(){
        val postRecordService = PostRecordService()
        postRecordService.setRecordResult(this)
        Log.d("picture 들어간 값 ==========================", picture.toString())
        postRecordService.sendPost(user_id_logined, getPostRecord(), picture)
    }

    override fun recordSuccess(result: Result) {
        Log.d("post_id 변환 값 ==========================", result.post_id.toString())
        picture_upload_uri_list.add(Picture_Save(result.post_id, picture_name, picture!!)) // 이전에 저장한 사진이랑 다른 거면 리스트에 저장해두기
//        Toast.makeText(this, "공고 등록 성공.", Toast.LENGTH_SHORT).show()
        finish()

    }

    override fun recordFailure() {
        Toast.makeText(this, "공고 등록 실패.", Toast.LENGTH_SHORT).show()
    }

    /////////////////////////////////////////////////

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 돌려받은 resultCode가 정상인지 체크
        if(resultCode == Activity.RESULT_OK){

            if(requestCode == SUBACTIITY_REQUEST_CODE) {
                Log.d("log: ", "log 찍힘")
                if (data != null) {
                    editTextAnnEtPlace = data.getStringExtra("address").toString()
                    viewBinding.annEtPlace.setText(data.getStringExtra("address"))
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


//                Toast.makeText(this, "사진 첨부", Toast.LENGTH_SHORT).show()
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
                viewBinding.imageBtnCamera.setImageBitmap(
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
                )
                viewBinding.cameraBtnTxt.visibility = View.INVISIBLE
            }
        }
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



}