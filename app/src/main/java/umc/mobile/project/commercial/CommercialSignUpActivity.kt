package umc.mobile.project.commercial

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
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.mobile.project.R
import umc.mobile.project.announcement.Auth.PostPost.PostRecord
import umc.mobile.project.announcement.Auth.PostPost.PostRecordService
import umc.mobile.project.announcement.PlaceSearchActivity
import umc.mobile.project.commercial.Auth.CommercialPost.CommercialRecord
import umc.mobile.project.commercial.Auth.CommercialPost.CommercialRecordResult
import umc.mobile.project.commercial.Auth.CommercialPost.CommercialRecordService
import umc.mobile.project.commercial.Auth.CommercialPost.Result
import umc.mobile.project.databinding.ActivityCommercialSignUpBinding
import umc.mobile.project.latitude_var
import umc.mobile.project.longtitude_var
import umc.mobile.project.ram.my_application_1.user_id_logined
import java.io.File

var kakao_url = ""

class CommercialSignUpActivity: AppCompatActivity(), CommercialRecordResult {
    private var editText1: EditText? = null
    private var editText2: EditText? = null
    private var editText3: EditText? = null
    private var editText4: EditText? = null
    private var editText5: EditText? = null
    private var editText6: EditText? = null
    private var editText7: ImageButton? = null

    private var button: Button? = null

    lateinit var editTextComEtPlace : String
    private var PICK_IMAGE = 1
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    private lateinit var viewBinding: ActivityCommercialSignUpBinding
    var picture : MultipartBody.Part? = null
    val SUBACTIITY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCommercialSignUpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        editText1 = viewBinding.comEtTitle
        editText2 = viewBinding.comEtInform
        editText3 = viewBinding.comEtMenu
        editText4 = viewBinding.comEtTip
        editText5 = viewBinding.comEtPlace
        editText6 = viewBinding.comEtRequest
        editText7 = viewBinding.imageBtnCamera
        button = viewBinding.btnSeeNow

        editText1!!.addTextChangedListener(textWatcher)
        editText2!!.addTextChangedListener(textWatcher)
        editText3!!.addTextChangedListener(textWatcher)
        editText4!!.addTextChangedListener(textWatcher)
        editText5!!.addTextChangedListener(textWatcher)
        editText6!!.addTextChangedListener(textWatcher)

        viewBinding.backBtn.setOnClickListener{
            finish()

        }
        viewBinding.imageBtnMap.setOnClickListener{
            val intent = Intent(this@CommercialSignUpActivity, PlaceSearchActivity::class.java)
            startActivityForResult(intent, SUBACTIITY_REQUEST_CODE)

        }
        viewBinding.imageBtnCamera.setOnClickListener {
            val status = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (status == PackageManager.PERMISSION_GRANTED) {
                // Permission ??????
                getImage()
            } else {
                // Permission ??????

                // ?????? ??????
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    100
                )
            }
        }
        viewBinding.btnSeeNow.setOnClickListener {
            save()
        }



    }



    //editText ?????? ????????? ?????? ?????????
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
            val builder = AlertDialog.Builder(this@CommercialSignUpActivity)
            val dialogView = layoutInflater.inflate(R.layout.announce_failure_alert_dialog, null)


            if (editText1?.text.toString().isNotEmpty() && editText2?.text.toString().isNotEmpty() && editText3?.text.toString().isNotEmpty()
                && editText4?.text.toString().isNotEmpty() && editText5?.text.toString().isNotEmpty() && editText6?.text.toString().isNotEmpty())
            {
                button?.isClickable =  true
                button?.backgroundTintList = ColorStateList.valueOf(color)
//                Toast.makeText(applicationContext, "?????????", Toast.LENGTH_SHORT)
//                    .show()
            } else  {
                button?.isClickable = false
                button?.backgroundTintList = ColorStateList.valueOf(color2)


            }
        }
    }

    private fun getCommercialRecord() : CommercialRecord {
        val store : String = editText1?.text.toString()
        val information = editText2?.text.toString()
        val mainMenu = editText3?.text.toString()
        val delivery_tips = editText4?.text.toString().toInt()
        val latitude = latitude_var
        val longitude = longtitude_var
        val request = editText6?.text.toString()
        val userIdx = user_id_logined
        val image = picture


        return CommercialRecord(store, information, mainMenu, delivery_tips, latitude,longitude, request, userIdx)
    }

    private fun save(){
        val commercialRecordService = CommercialRecordService()
        commercialRecordService.setCommercialRecordResult(this)
        Log.d("picture ????????? ??? ==========================", picture.toString())
        commercialRecordService.sendPost(user_id_logined, getCommercialRecord(), picture)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // ???????????? resultCode??? ???????????? ??????
        if(resultCode == Activity.RESULT_OK){

            if(requestCode == SUBACTIITY_REQUEST_CODE) {
                Log.d("log: ", "log ??????")
                if (data != null) {
                    editTextComEtPlace = data.getStringExtra("address").toString()
                    viewBinding.comEtPlace.setText(data.getStringExtra("address"))
                    latitude = data.getDoubleExtra("latitude", 0.0)
                    longitude = data.getDoubleExtra("longitude", 0.0)
                }
            }
            // ?????? ???????????? ??????
            else if (requestCode == PICK_IMAGE) {
                val imagePath = data!!.data

                val file = File(absolutelyPath(imagePath, this))
                val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

//                pictureNameList.addAll(listOf(file.name)) // ????????? ?????? ??????

                Log.d("?????? ??????!! ======== ", file.name)
                picture = body

                setAdjImgUri(imagePath!!)

//
//                Toast.makeText(this, "?????? ??????", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
            }
        }
//        Log.d("4: ????????????",  "??????: ${editTextAnnEtPlace.toString()} ??????: $latitude  ??????: $longitude")
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
        intent.type = "image/*" // ?????? ?????????
        startActivityForResult(intent, PICK_IMAGE)
    }

    private fun setAdjImgUri(imgUri: Uri) {

        //2)Resizing ??? BitmapOption ?????????
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

        //3) Bitmap ?????? ??? ?????? (resized + rotated)
        contentResolver.openInputStream(imgUri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream, null, bmOptions)?.also { bitmap ->
                val matrix = Matrix()
                matrix.preRotate(0f, 0f, 0f)
                viewBinding.imageBtnCamera.setImageBitmap(
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
                )
                viewBinding.tvImg.visibility = View.INVISIBLE
            }
        }
    }


    override fun commercialRecordSuccess(result: Result) {
//        Toast.makeText(this, "?????? ?????? ??????.", Toast.LENGTH_SHORT).show()
        viewBinding.btnSeeNow.text = "????????????"

        viewBinding.btnSeeNow.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(kakao_url))
            startActivity(intent)
        }
    }

    override fun commercialRecordFailure() {
        Toast.makeText(this, "?????? ?????? ??????.", Toast.LENGTH_SHORT).show()

    }

}