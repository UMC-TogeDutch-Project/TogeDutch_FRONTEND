package umc.mobile.project.announcement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import Post
import umc.mobile.project.databinding.ActivityAnnounceDetailBinding
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.Auth.Post.GetPostUpload.PostUploadGetService
import umc.mobile.project.ram.my_application_1.post_id_to_detail


class AnnounceDetailActivity:AppCompatActivity() {
    lateinit var viewBinding: ActivityAnnounceDetailBinding
    lateinit var editTextAnnEtPlace : String
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    val SUBACTIITY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAnnounceDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        editTextAnnEtPlace = viewBinding.annEtPlace.toString()

        viewBinding.btnSeeNow.setOnClickListener{
            finish()
        }
        viewBinding.backBtn.setOnClickListener{
            finish()
        }
        viewBinding.imageBtnMap.setOnClickListener {
            val intent = Intent(this@AnnounceDetailActivity, PlaceSearchActivity::class.java)
            startActivityForResult(intent, SUBACTIITY_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 돌려받은 resultCode가 정상인지 체크
        if(resultCode == Activity.RESULT_OK){
            Log.d("log: ", "log 찍힘")
            if (data != null) {
                editTextAnnEtPlace = data.getStringExtra("address").toString()
                viewBinding.annEtPlace.setText(data.getStringExtra("address"))
                latitude = data.getDoubleExtra("latitude", 0.0)
                longitude = data.getDoubleExtra("longitude", 0.0)
            }

        }
        Log.d("4: 위치정보",  "주소: ${editTextAnnEtPlace.toString()} 위도: $latitude  경도: $longitude")
    }



}
