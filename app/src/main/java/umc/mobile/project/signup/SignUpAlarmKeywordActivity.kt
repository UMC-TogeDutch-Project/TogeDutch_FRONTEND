package umc.mobile.project.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import umc.mobile.project.databinding.ActivitySignUpAlarmKeywordBinding

class SignUpAlarmKeywordActivity : AppCompatActivity() {
    val TAG: String = "로그"
    private lateinit var viewBinding: ActivitySignUpAlarmKeywordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var name = intent.getStringExtra("name")
        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")
        var phoneNum = intent.getStringExtra("phoneNum")

        viewBinding = ActivitySignUpAlarmKeywordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Log.d(TAG, "onCreate: ${name}, ${email}, ${password}, ${phoneNum}")

        viewBinding.btnNext.setOnClickListener {
            val intent = Intent(this, SignUpRegionActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("phoneNum", phoneNum)
            intent.putExtra("keyWord", viewBinding.etInputAlarmKeyword.text.toString())

            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        viewBinding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)

        }
    }


}