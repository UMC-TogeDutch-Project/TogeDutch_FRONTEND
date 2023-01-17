package umc.mobile.project.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.mobile.project.databinding.ActivitySignUpAlarmKeywordBinding

class SignUpAlarmKeywordActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpAlarmKeywordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpAlarmKeywordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnNext.setOnClickListener {
            val intent = Intent(this, SignUpRegionActivity::class.java)
            startActivity(intent)
        }

        viewBinding.btnBack.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            finish()
            startActivity(intent)

        }
    }


}