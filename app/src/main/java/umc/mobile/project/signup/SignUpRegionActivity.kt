package umc.mobile.project.signup

import android.content.Intent
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivitySignUpRegionBinding
import java.io.IOException
import java.util.regex.Pattern

class SignUpRegionActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpRegionBinding

    //일단 emailvalidation으로 확인 추후에 수정 필요!!!!!!!!!!!!!!!!!!!!!!!!!!!
    val regionValidation =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    val TAG: String = "로그"

    var address: String = ""
    lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpRegionBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var name = intent.getStringExtra("name")
        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")
        var phoneNum = intent.getStringExtra("phoneNum")
        var keyWordIdx: Int = intent.getIntExtra("keyWordIdx", 1)
        var latitude: Double = 50.02
        var longitude: Double = 60.02

        Log.d(TAG, "onCreate: ${name}, ${email}, ${password}, ${phoneNum}, ${keyWordIdx}")

        viewBinding.btnNext.setOnClickListener {
            val intent = Intent(this, SignUpUserCategoryActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("phoneNum", phoneNum)
            intent.putExtra("keyWordIdx", keyWordIdx)
            intent.putExtra("latitude", latitude)
            intent.putExtra("longitude", longitude)
//            intent.putExtra("region", viewBinding.etInputRegion.text.toString())
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        viewBinding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }

        viewBinding.btnInputRegion.setOnClickListener {
            geocoder = Geocoder(this)

            var locationNameList: List<Address>? = null

            address = viewBinding.etInputRegion.text.toString()

            Log.d("editTextaddress: ", viewBinding.etInputRegion.text.toString())
            Log.d("address: ", address)

            try {                                   //지역  , 읽을 개수
                locationNameList = geocoder.getFromLocationName(address, 10)
            } catch (e: IOException) {
                e.printStackTrace()

                viewBinding.tvRegionCheck.text = "오류 메세지"
                viewBinding.etInputRegion.setTextColor(Color.parseColor("#C854FF"))

                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생")
            }
            if (locationNameList != null) {
                if (locationNameList.isEmpty()) {
                    address = ""

                    viewBinding.tvRegionCheck.text = "오류 메세지"
                    viewBinding.etInputRegion.setTextColor(Color.parseColor("#C854FF"))

//                    Toast.makeText(this, "해당되는 주소 정보는 없습니다", Toast.LENGTH_LONG).show()
                } else {
                    latitude = locationNameList!![0].latitude
                    longitude = locationNameList!![0].longitude

                    viewBinding.tvRegionCheck.text = " "
                    viewBinding.etInputRegion.setTextColor(Color.parseColor("#FF000000"))
                    Toast.makeText(this, "${Toast.makeText(this, "해당되는 주소 정보는 없습니다", Toast.LENGTH_LONG).show()} 로 주소가 설정되었습니다.", Toast.LENGTH_LONG).show()
                    Log.d("위치정보", "주소: $address 위도: $latitude  경도: $longitude")
                }

            }

        }

    }
}