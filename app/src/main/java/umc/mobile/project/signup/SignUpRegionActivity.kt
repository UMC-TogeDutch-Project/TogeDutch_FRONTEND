package umc.mobile.project.signup

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivitySignUpRegionBinding
import java.util.regex.Pattern

class SignUpRegionActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpRegionBinding

    //일단 emailvalidation으로 확인 추후에 수정 필요!!!!!!!!!!!!!!!!!!!!!!!!!!!
    val regionValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpRegionBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var name = intent.getStringExtra("name")
        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")
        var phoneNum = intent.getStringExtra("phoneNum")
        var keyWord = intent.getStringExtra("keyWord")

        Log.d(TAG, "onCreate: ${name}, ${email}, ${password}, ${phoneNum}, ${keyWord}")

        viewBinding.btnNext.setOnClickListener {
            val intent = Intent(this, SignUpUserCategoryActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("phoneNum", phoneNum)
            intent.putExtra("keyWord", keyWord)
            intent.putExtra("region", viewBinding.etInputRegion.text.toString())
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        viewBinding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }

        viewBinding.btnInputRegion.setOnClickListener {
            if(regionFind()){
                viewBinding.tvRegionCheck.setText(" ")
                viewBinding.etInputRegion.setTextColor(Color.parseColor("#FF000000"))
            }
            else{
                viewBinding.tvRegionCheck.setText("오류 메세지")
                viewBinding.etInputRegion.setTextColor(Color.parseColor("#C854FF"))
            }
        }

    }

    fun regionFind():Boolean{
        var email = viewBinding.etInputRegion.text.toString().trim() //공백제거
        val p = Pattern.matches(regionValidation, email) // 서로 패턴이 맞는지?
        if (p) {
            //주소 형태가 정상일 경우
//            viewBinding.tvRegionCheck.setText(" ")
//            viewBinding.etInputAlarmKeyword.setTextColor(Color.parseColor("#FF000000"))

            return true
        } else {
//            viewBinding.tvRegionCheck.setText("오류 메세지")
//            viewBinding.etInputAlarmKeyword.setTextColor(Color.parseColor("#C854FF"))
            //또는 questionEmail.setTextColor(R.color.red.toInt())
            return false
        }
    }
}